package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.TIBCO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.id.StaffSkillId;
import com.tecxis.resume.domain.repository.StaffSkillRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaStaffSkillDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private StaffSkillRepository staffSkillRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
		/**Insert Staff*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1, amt.getId().longValue());
		
		/**Insert Skill*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
		Skill tibco = Utils.insertSkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
		assertEquals(1, tibco.getId().longValue());
		
		/**Insert StaffSkill*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		
		StaffSkill amtTibco =  staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		assertNotNull(amtTibco);
		assertEquals(amt, amtTibco.getStaff());
		assertEquals(tibco, amtTibco.getSkill());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		fail("TODO");
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"})
	public void testDelete() {
		/**Insert Staff*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1, amt.getId().longValue());
		
		/**Insert Skill*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
		Skill tibco = Utils.insertSkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
		assertEquals(1, tibco.getId().longValue());
		
		/**Insert StaffSkill*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		StaffSkill amtTibco =  Utils.insertStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		
		/**Delete StaffSkill*/
		entityManager.remove(amtTibco);
		entityManager.flush();
		/**Verify*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
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
	
	@Test
	public void test_ManyToOne_SaveSkill() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	public void test_ManyToOne_SaveStaff() {
		org.junit.Assert.fail("TODO");
	}
}
