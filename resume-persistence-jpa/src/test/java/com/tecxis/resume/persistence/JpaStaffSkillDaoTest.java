package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.TIBCO;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
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

import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.id.StaffSkillId;
import com.tecxis.resume.domain.repository.StaffSkillRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class JpaStaffSkillDaoTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffSkillRepository staffSkillRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedStaffSkill() {
		/**Insert Staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Insert Skill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Skill tibco = Utils.insertASkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(1, tibco.getId());
		
		/**Insert StaffSkill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		Utils.insertAStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
		StaffSkill amtTibco =  staffSkillRepo.findById(new StaffSkillId(amt, tibco)).get();
		assertNotNull(amtTibco);
		assertEquals(amt, amtTibco.getStaff());
		assertEquals(tibco, amtTibco.getSkill());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDeleteStaffSkill() {
		/**Insert Staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Insert Skill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Skill tibco = Utils.insertASkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(1, tibco.getId());
		
		/**Insert StaffSkill*/
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		StaffSkill amtTibco =  Utils.insertAStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
		/**Delete StaffSkill*/
		entityManager.remove(amtTibco);
		entityManager.flush();
		/**Verify*/
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		List <StaffSkill> staffSkills = staffSkillRepo.findAll();
		assertEquals(5, staffSkills.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <StaffSkill> pageableStaffSkill =  staffSkillRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableStaffSkill.getSize());
	}
}
