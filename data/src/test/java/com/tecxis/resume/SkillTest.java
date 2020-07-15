package com.tecxis.resume;

import static com.tecxis.resume.persistence.SkillRepositoryTest.SKILL_TABLE;
import static com.tecxis.resume.persistence.SkillRepositoryTest.TIBCO;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static com.tecxis.resume.persistence.StaffSkillRepositoryTest.STAFF_SKILL_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SkillTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SkillRepository skillRepo;

	@Test
	public void testGetSkillId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testGetStaff() {
		Skill tibcoSkill = skillRepo.getSkillByName(TIBCO);
		List<Staff> tibcoStaff = tibcoSkill.getStaff();
		
		assertEquals(1, tibcoStaff.size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSkill() {
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		/**Find Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		assertEquals(tibco.getName(), TIBCO);
		
		/**Test Skill initial state*/
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		/***Test Skill many-to-many cascadings*/
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		/**Test Staff initial state*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		/**Remove Skill*/
		entityManager.remove(tibco);
		entityManager.flush();
		
		/**Test Skill was removed*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		/***Test Skill DELETE many-to-many cascadings*/
		assertEquals(4, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		/**Test Staff hasn't changed*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
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