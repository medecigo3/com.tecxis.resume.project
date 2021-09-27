package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.DUMMY_SKILL;
import static com.tecxis.resume.domain.Constants.TIBCO;
import static com.tecxis.resume.domain.EmploymentContractTest.PK_UPDATE_WARN;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.StaffSkillId;
import com.tecxis.resume.domain.repository.SkillRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.StaffSkillRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_UNCOMMITTED)
@SqlConfig(dataSource="dataSource")
public class StaffSkillTest {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private SkillRepository skillRepo;
	
	@Autowired
	private StaffSkillRepository staffSkillRepo;
	
	@Test
	public void testSetStaff() {
		/**Find Staff*/
		
		/**Find Skill*/
		
		/**Find StaffSkill to update*/
		
		/**Find Staff to set in the new StaffSkill*/
		
		/**Create new StaffKill*/
		
		/**Verify initial state*/
		
		/**Remove old and create StaffSkill with new Staff*/
		
		/**Find old StaffSkill*/
		
		/**Find new StaffSkill*/
		
		LOG.info(PK_UPDATE_WARN);
	}
	
	@Test
	public void testSetSkill() {
		/**Find Staff*/
		/**Find Skill*/
		/**Find StaffSkill to update*/
		
		/**Find Staff to set in the new StaffSkill*/
		
		/**Create new StaffKill*/
		
		/**Verify initial state*/
		
		/**Remove old and create StaffSkill wiht new Skill*/
		
		/**Find old StaffSkill*/
		
		/**Find new StaffSkill*/
		
		LOG.info(PK_UPDATE_WARN);
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveStaffSkill() {
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);	
		assertEquals(5, amt.getSkills().size());
		
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		assertEquals(1, tibco.getStaff().size());
		
		
		/***Find StaffSkill*/
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		assertEquals(amt, amtTibco.getStaff());
		assertEquals(tibco, amtTibco.getSkill());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Remove StaffSkill*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		entityManager.remove(amtTibco);
		entityManager.flush();
		
		/**Test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
		/**Validate StaffSkill*/
		assertFalse(staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).isPresent());
				
		/**Validate Staff -> Skills*/
		amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);
		assertEquals(4, amt.getSkills().size());
		
		tibco = skillRepo.getSkillByName(TIBCO);
		assertEquals(0, tibco.getStaff().size());
		
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertStaffSkillRowsAndSetIds() {
		/**Insert Staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Insert Skill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Skill tibco = Utils.insertSkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(1L, tibco.getId().longValue());
		
		/**Insert StaffSkill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
		/**Validate StaffSkill*/
		StaffSkill amtTibcoSkill = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		assertEquals(amt, amtTibcoSkill.getStaff());
		assertEquals(tibco, amtTibcoSkill.getSkill());
		
		/**Detach entities to clear cache*/
		entityManager.clear();
		
		/**Validate Staff -> Skills*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(1, amt.getSkills().size());
		
		/**Validate Skill -> Staff*/
		tibco= skillRepo.getSkillByName(TIBCO);
		assertEquals(1, tibco.getStaff().size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAddStaffSkill() {
		/**Fetch Staff and validate*/	
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(5, amt.getSkills().size());
		
		/**Create new Skill for testing purposes*/
		Skill testSkill = skillRepo.getSkillByName(DUMMY_SKILL);
		/**Create new TestSkill*/
		StaffSkill newStaffSkill = new StaffSkill(testSkill, amt);
		
		/**Validate state of tables pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		/**Insert new StaffSkill*/
		entityManager.persist(newStaffSkill);		
		entityManager.flush();
		
		/**Detach entities to clear cache*/
		entityManager.clear();
		
		/**Validate state of tables post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
		/**Validate new StaffSkill*/
		newStaffSkill = staffSkillRepo.findById(new StaffSkillId(amt.getId(), testSkill.getId())).get();
		assertEquals(amt, newStaffSkill.getStaff());
		assertEquals(testSkill, newStaffSkill.getSkill());
		
		/**Validate Skill -> Staff*/
		testSkill= skillRepo.getSkillByName(DUMMY_SKILL);
		assertEquals(1, testSkill.getStaff().size());
		
		/**Validate Staff -> Skills*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(6, amt.getSkills().size());
				
	}

	@Test
	public void testToString() {
		StaffSkill staffSkill = new StaffSkill();
		assertThat(staffSkill.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}
}
