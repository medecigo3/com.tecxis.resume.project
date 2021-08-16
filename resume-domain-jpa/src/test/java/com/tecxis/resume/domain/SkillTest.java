package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.TIBCO;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
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

import com.tecxis.resume.domain.repository.SkillRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Skill skill = Utils.insertASkill(TIBCO, entityManager);
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetName() {
		Skill tibcoSkill = skillRepo.getSkillByName(TIBCO);
		assertEquals(TIBCO, tibcoSkill.getName());		
	}
	
	@Test
	public void testSetName() {
		Skill skill = new Skill();
		assertNull(skill.getName());
		skill.setName(TIBCO);
		assertEquals(TIBCO, skill.getName());
				
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testGetStaff() {
		Skill tibcoSkill = skillRepo.getSkillByName(TIBCO);
		List<Staff> tibcoStaff = tibcoSkill.getStaff();
		
		assertEquals(1, tibcoStaff.size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSkill() {
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		assertEquals(tibco.getName(), TIBCO);
		
		/**Test Skill initial state*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		/***Test Skill many-to-many cascadings*/
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**Test Staff initial state*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		/**Remove Skill*/
		entityManager.remove(tibco);
		entityManager.flush();
		
		/**Test Skill was removed*/
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		/***Test Skill DELETE many-to-many cascadings*/
		assertEquals(4, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**Test Staff hasn't changed*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaff() {
		Skill skill = new Skill();
		skill.addStaff(new Staff());
		//To add Staff to a Skill see StaffSkillTest.testAddStaffSkill
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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

}
