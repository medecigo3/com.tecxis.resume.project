package com.tecxis.resume;

import static com.tecxis.resume.EmploymentContractTest.PK_UPDATE_WARN;
import static com.tecxis.resume.persistence.StaffSkillRepositoryTest.STAFF_SKILL_TABLE;
import static org.junit.Assert.assertEquals;
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
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(Constants.TIBCO);
		assertEquals(tibco.getName(), Constants.TIBCO);
		/***Find StaffSkill*/
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		assertEquals(amt, amtTibco.getStaffSkillId().getStaff());
		assertEquals(tibco, amtTibco.getStaffSkillId().getSkill());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Remove StaffSkill*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		amtTibco = staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		entityManager.remove(amtTibco);
		entityManager.flush();
		
		/**Test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		
	}
	
	@Test
	public void testToString() {
		StaffSkill staffSkill = new StaffSkill();
		staffSkill.toString();
	}
	
	public static StaffSkill insertAStaffSkill(Staff staff, Skill skill, EntityManager entityManager) {
		StaffSkill staffSkill = new StaffSkill(new StaffSkillId(staff, skill));
		entityManager.persist(staffSkill);
		entityManager.flush();
		return staffSkill;
		
	}
}
