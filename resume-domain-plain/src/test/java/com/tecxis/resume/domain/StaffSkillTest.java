package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.EmploymentContractTest.PK_UPDATE_WARN;
import static com.tecxis.resume.domain.StaffTest.insertAStaff;
import static com.tecxis.resume.persistence.StaffSkillRepositoryTest.STAFF_SKILL_TABLE;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.tecxis.commons.persistence.id.StaffSkillId;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.persistence.SkillRepository;
import com.tecxis.resume.persistence.StaffRepository;
import com.tecxis.resume.persistence.StaffSkillRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class StaffSkillTest {
	
	private final static Logger LOG = LogManager.getLogger();
	
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveStaffSkill() {
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(Constants.AMT_LASTNAME);	
		assertEquals(5, amt.getSkills().size());
		
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(Constants.TIBCO);
		assertEquals(1, tibco.getStaff().size());
		
		
		/***Find StaffSkill*/
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		assertEquals(amt, amtTibco.getStaff());
		assertEquals(tibco, amtTibco.getSkill());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Remove StaffSkill*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		amtTibco = staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		entityManager.remove(amtTibco);
		entityManager.flush();
		
		/**Test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		
		/**Validate StaffSkill*/
		assertFalse(staffSkillRepo.findById(new StaffSkillId(amt, tibco)).isPresent());
				
		/**Validate Staff -> Skills*/
		amt = staffRepo.getStaffLikeLastName(Constants.AMT_LASTNAME);
		assertEquals(4, amt.getSkills().size());
		
		tibco = skillRepo.getSkillByName(Constants.TIBCO);
		assertEquals(0, tibco.getStaff().size());
		
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertStaffSkillRowsAndSetIds() {
		/**Insert Staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Insert Skill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		Skill tibco = SkillTest.insertASkill(Constants.TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(1, tibco.getId());
		
		/**Insert StaffSkill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		insertAStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		
		/**Validate StaffSkill*/
		StaffSkill amtTibcoSkill = staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		assertEquals(amt, amtTibcoSkill.getStaff());
		assertEquals(tibco, amtTibcoSkill.getSkill());
		
		/**Detach entities to clear cache*/
		entityManager.clear();
		
		/**Validate Staff -> Skills*/
		amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(1, amt.getSkills().size());
		
		/**Validate Skill -> Staff*/
		tibco= skillRepo.getSkillByName(Constants.TIBCO);
		assertEquals(1, tibco.getStaff().size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAddStaffSkill() {
		/**Fetch Staff and validate*/	
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(5, amt.getSkills().size());
		
		/**Create new Skill for testing purposes*/
		Skill testSkill = skillRepo.getSkillByName(Constants.DUMMY_SKILL);
		/**Create new TestSkill*/
		StaffSkill newStaffSkill = new StaffSkill(testSkill, amt);
		
		/**Validate state of tables pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		/**Insert new StaffSkill*/
		entityManager.persist(newStaffSkill);		
		entityManager.flush();
		
		/**Detach entities to clear cache*/
		entityManager.clear();
		
		/**Validate state of tables post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		
		/**Validate new StaffSkill*/
		newStaffSkill = staffSkillRepo.findById(new StaffSkillId(amt, testSkill)).get();
		assertEquals(amt, newStaffSkill.getStaff());
		assertEquals(testSkill, newStaffSkill.getSkill());
		
		/**Validate Skill -> Staff*/
		testSkill= skillRepo.getSkillByName(Constants.DUMMY_SKILL);
		assertEquals(1, testSkill.getStaff().size());
		
		/**Validate Staff -> Skills*/
		amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(6, amt.getSkills().size());
				
	}
	
	@Test
	public void testToString() {
		StaffSkill staffSkill = new StaffSkill();
		staffSkill.toString();
	}
	
	public static StaffSkill insertAStaffSkill(Staff staff, Skill skill, EntityManager entityManager) {
		StaffSkill staffSkill = new StaffSkill(skill, staff);
		entityManager.persist(staffSkill);
		entityManager.flush();
		return staffSkill;
		
	}
}
