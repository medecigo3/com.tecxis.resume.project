package com.tecxis.resume;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.persistence.StaffAssignmentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class StaffAssignmentTest {

	public static final String STAFFASSIGNMENT_TABLE = "STAFF_ASSIGNMENT";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffAssignmentRepository staffAssignmentRepository;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public static StaffAssignment insertAStaffAssignment(Project project, Staff staff,  Assignment assignment, EntityManager entityManager) {
		StaffAssignment staffAssignment = new StaffAssignment();
		StaffAssignmentId id = new StaffAssignmentId(project, staff, assignment);
		staffAssignment.setStaffAssignmentId(id);
		entityManager.persist(staffAssignment);
		entityManager.flush();
		return staffAssignment;
		
	}

}
