package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT12;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.domain.Assignment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED) //this test suite is @Transactional but flushes changes manually (see the blog: https://www.marcobehler.com/2014/06/25/should-my-tests-be-transactional)
@SqlConfig(dataSource="dataSource")
public class JpaAssignmentDaoTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AssignmentDao assignmentDao;

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAdd() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		Assignment assignment = new Assignment();
		assignment.setDesc(ASSIGNMENT12);		
		assignmentDao.add(assignment);
		entityManager.flush();
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllPageable() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssignmentLikeDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssignmentByDesc() {
		fail("Not yet implemented");
	}

}
