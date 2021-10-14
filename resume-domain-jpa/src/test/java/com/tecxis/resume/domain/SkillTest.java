package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.TIBCO;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.repository.SkillRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
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
		Skill skill = Utils.insertSkill(TIBCO, entityManager);
		assertThat(skill.getId(), Matchers.greaterThan((long)0));		
	}
	
	@Test
	public void testSetId() {
		Skill skill = new Skill();
		assertEquals(0L, skill.getId().longValue());
		skill.setId(1L);
		assertEquals(1L, skill.getId().longValue());		
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
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		assertEquals(tibco.getName(), TIBCO);
		
		/**Test Skill initial state*/
		SchemaUtils.testInitialState(jdbcTemplate);		
		/**Remove Skill*/
		entityManager.remove(tibco);
		entityManager.flush();		
		/**Test Skill was removed*/
		SchemaUtils.testStateAfterTibcoSkillDelete(jdbcTemplate);
		
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
		assertThat(skill.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}

}
