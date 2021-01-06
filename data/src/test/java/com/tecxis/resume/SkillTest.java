package com.tecxis.resume;

import static com.tecxis.resume.persistence.StaffSkillRepositoryTest.STAFF_SKILL_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.persistence.SkillRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml",	
		"classpath:validation-api-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SkillTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SkillRepository skillRepo;
	
	@Autowired
	private Validator validator;

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Skill skill = insertASkill(Constants.TIBCO, entityManager);
		assertThat(skill.getId(), Matchers.greaterThan((long)0));		
	}
	
	@Test
	public void testSetId() {
		Skill skill = new Skill();
		assertEquals(0, skill.getId());
		skill.setId(1);
		assertEquals(1, skill.getId());		
	}	

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetName() {
		Skill tibcoSkill = skillRepo.getSkillByName(Constants.TIBCO);
		assertEquals(Constants.TIBCO, tibcoSkill.getName());		
	}
	
	@Test
	public void testSetName() {
		Skill skill = new Skill();
		assertNull(skill.getName());
		skill.setName(Constants.TIBCO);
		assertEquals(Constants.TIBCO, skill.getName());
				
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testGetStaff() {
		Skill tibcoSkill = skillRepo.getSkillByName(Constants.TIBCO);
		List<Staff> tibcoStaff = tibcoSkill.getStaff();
		
		assertEquals(1, tibcoStaff.size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSkill() {
		assertEquals(7, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(Constants.TIBCO);
		assertEquals(tibco.getName(), Constants.TIBCO);
		
		/**Test Skill initial state*/
		assertEquals(7, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		/***Test Skill many-to-many cascadings*/
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		/**Test Staff initial state*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Remove Skill*/
		entityManager.remove(tibco);
		entityManager.flush();
		
		/**Test Skill was removed*/
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		/***Test Skill DELETE many-to-many cascadings*/
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		/**Test Staff hasn't changed*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaff() {
		Skill skill = new Skill();
		skill.addStaff(new Staff());
		//To add Staff to a Skill see StaffSkillTest.testAddStaffSkill
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaff() {	
		Skill skill = new Skill();
		skill.removeStaff(new Staff());
		//To add Staff to a Skill see StaffSkillTest.testRemoveStaffSkill
	}
	
	@Test
	public void testNameIsNotNull() {
		Skill skill = new Skill();
		Set<ConstraintViolation<Skill>> violations = validator.validate(skill);
        assertFalse(violations.isEmpty());
	}
	
	@Test
	public void testToString() {
		Skill skill = new Skill();
		skill.toString();
	}

	public static Skill insertASkill(String name, EntityManager entityManager) {
		Skill skill = new Skill();
		skill.setName(name);
		assertEquals(0, skill.getId());
		entityManager.persist(skill);		
		entityManager.flush();
		assertThat(skill.getId(), Matchers.greaterThan((long)0));
		return skill;
	}

}
