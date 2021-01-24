package com.tecxis.resume.domain.repository;

import static com.tecxis.resume.domain.StaffSkillTest.insertAStaffSkill;
import static com.tecxis.resume.domain.StaffTest.insertAStaff;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Constants;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.SkillTest;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.id.StaffSkillId;
import com.tecxis.resume.domain.repository.StaffSkillRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class StaffSkillRepositoryTest {
	
	final public static String STAFF_SKILL_TABLE = "STAFF_SKILL";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffSkillRepository staffSkillRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedStaffSkill() {
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
		
		StaffSkill amtTibco =  staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		assertNotNull(amtTibco);
		assertEquals(amt, amtTibco.getStaff());
		assertEquals(tibco, amtTibco.getSkill());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteStaffSkill() {
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
		StaffSkill amtTibco =  insertAStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		
		/**Delete StaffSkill*/
		entityManager.remove(amtTibco);
		entityManager.flush();
		/**Verify*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		List <StaffSkill> staffSkills = staffSkillRepo.findAll();
		assertEquals(5, staffSkills.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <StaffSkill> pageableStaffSkill =  staffSkillRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableStaffSkill.getSize());
	}
}
