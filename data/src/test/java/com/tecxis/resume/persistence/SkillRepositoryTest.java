package com.tecxis.resume.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

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

import com.tecxis.resume.Skill;
import com.tecxis.resume.SkillTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SkillRepositoryTest {
	
	public static final String SKILL_TABLE = "Skill";
	public static final String TIBCO = "TIBCO";
	public static final String ORACLE = "Oracle";
	public static final String JAVA = "Java";
	public static final String SPRING = "Spring";
	public static final String UNIX = "Unix";
	public static final String GIT = "Git";

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SkillRepository skillRepo;
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Skill tibco = SkillTest.insertASkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(1, tibco.getId());
		
		Skill oracle = SkillTest.insertASkill(ORACLE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(2, oracle.getId());
		
		Skill java = SkillTest.insertASkill(JAVA, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(3, java.getId());
		
		Skill spring = SkillTest.insertASkill(SPRING, entityManager);
		assertEquals(4, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(4, spring.getId());
	
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedSkill() {
		Skill skillIn = SkillTest.insertASkill(TIBCO, entityManager);
		Skill skillOut = skillRepo.getSkillByName(TIBCO);
		assertEquals(skillIn, skillOut);
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSkillByName() {
		Skill java = skillRepo.getSkillByName(JAVA);
		assertNotNull(java);
		assertEquals(JAVA, java.getName());
		Skill git = skillRepo.getSkillByName(GIT);
		assertNotNull(git);
		assertEquals(GIT, git.getName());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSkillByName() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Skill tempSkill = SkillTest.insertASkill(ORACLE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		skillRepo.delete(tempSkill);
		assertNull(skillRepo.getSkillByName(ORACLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Skill> skills = skillRepo.findAll();
		assertEquals(6, skills.size());
	}

}
