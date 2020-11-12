package com.tecxis.resume.persistence;

import static com.tecxis.resume.AssignmentTest.insertAssignment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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

import com.tecxis.resume.Assignment;
import com.tecxis.resume.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class AssignmentRepositoryTest {
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		Assignment assignment1 = insertAssignment(Constants.ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
						
		Assignment assignment2 = insertAssignment(Constants.ASSIGNMENT2, entityManager);
		assertEquals(2, assignment2.getId());
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedAssingmnet() {		
		Assignment assignmentIn = insertAssignment(Constants.ASSIGNMENT1, entityManager);
		Assignment assignmentOut = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT1);
		assertEquals(assignmentIn, assignmentOut);
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAssignmentByLikeDesc() {
		/**Test query by name with LIKE expression*/
		List <Assignment> assignments1 = assignmentRepo.getAssignmentLikeDesc(Constants.DEV_ASSIGNMENT_WILDCARD);
		assertNotNull(assignments1);
		assertEquals(8, assignments1.size());
		Assignment assignment1 = assignments1.get(0);		
		assertThat(assignment1.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
		Assignment assignment2 = assignments1.get(1);
		assertThat(assignment2.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
		Assignment assignment3 = assignments1.get(2);
		assertThat(assignment3.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
		Assignment assignment4 = assignments1.get(3);
		assertThat(assignment4.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
		Assignment assignment5 = assignments1.get(4);
		assertThat(assignment5.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
		Assignment assignment6 = assignments1.get(5);
		assertThat(assignment6.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
		Assignment assignment7 = assignments1.get(6);
		assertThat(assignment7.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
		Assignment assignment8 = assignments1.get(7);
		assertThat(assignment8.getDesc(), Matchers.is(Matchers.oneOf(Constants.ASSIGNMENT45, Constants.ASSIGNMENT46, Constants.ASSIGNMENT47, Constants.ASSIGNMENT50, Constants.ASSIGNMENT51, Constants.ASSIGNMENT54, Constants.ASSIGNMENT55, Constants.ASSIGNMENT56)));
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetAssignmentByDesc() {
		List <Assignment> assignments = assignmentRepo.findAll();
		assertEquals(54, assignments.size());
		Assignment assignment1 =  assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT1);
		assertNotNull(assignment1);	
		assertEquals(Constants.ASSIGNMENT1, assignment1.getDesc());	
		Assignment assignment2 =  assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT2);
		assertNotNull(assignment2);	
		assertEquals(Constants.ASSIGNMENT2, assignment2.getDesc());	
		Assignment assignment20 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT20);
		assertNotNull(assignment20);
		assertEquals(Constants.ASSIGNMENT20, assignment20.getDesc());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		Assignment tempAssignment = insertAssignment(Constants.ASSIGNMENT57, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		assignmentRepo.delete(tempAssignment);
		assertNull(assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT57));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Assignment> assignments =  assignmentRepo.findAll();
		assertEquals(54, assignments.size());
	}

}
