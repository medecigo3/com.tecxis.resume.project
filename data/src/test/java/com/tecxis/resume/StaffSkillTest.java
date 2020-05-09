package com.tecxis.resume;

import static com.tecxis.resume.persistence.SkillRepositoryTest.SKILL_TABLE;
import static com.tecxis.resume.persistence.SkillRepositoryTest.TIBCO;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static com.tecxis.resume.persistence.StaffSkillRepositoryTest.STAFF_SKILL_TABLE;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.StaffSkill.StaffSkillId;
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
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveStaffSkill() {
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		assertEquals(tibco.getName(), TIBCO);
		/***Find StaffSkill*/
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		assertEquals(amt, amtTibco.getStaffSkillId().getStaff());
		assertEquals(tibco, amtTibco.getStaffSkillId().getSkill());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Remove StaffSkill*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		amtTibco = staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		entityManager.remove(amtTibco);
		entityManager.flush();
		
		/**Test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		
	}
	
	public static StaffSkill insertAStaffSkill(Staff staff, Skill skill, EntityManager entityManager) {
		StaffSkill staffSkill = new StaffSkill(new StaffSkillId(staff, skill));
		entityManager.persist(staffSkill);
		entityManager.flush();
		return staffSkill;
		
	}
}
