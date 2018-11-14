package com.tecxis.resume;

import static com.tecxis.resume.persistence.AssignmentRepositoryTest.*;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.*;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.persistence.AssignmentRepository;
import com.tecxis.resume.persistence.ProjectRepository;
import com.tecxis.resume.persistence.StaffAssignmentRepository;
import com.tecxis.resume.persistence.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)

public class AssignmentTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private StaffAssignmentRepository staffAssignmentRepo;

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffAssignments() {
		/**Prepare projects*/
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		List <StaffAssignment>  eolisStaffAssignments = eolis.getStaffAssignments();
		assertEquals(5, eolisStaffAssignments.size());
		
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		List <StaffAssignment> amtStaffAssignments = amt.getStaffAssignments();
		assertEquals(62, amt.getProjects().size());
		assertEquals(62, amtStaffAssignments.size());
		
		/**Prepare assignments*/		
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT23);		
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT31);
		Assignment assignment32 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT32);
		Assignment assignment33 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT33);
		Assignment assignment34 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT34);		
		List <StaffAssignment> assignment23StaffAssignments = assignment23.getStaffAssignments();
		assertEquals(3, assignment23StaffAssignments.size());		
		assertEquals(3, assignment31.getStaffAssignments().size());
		assertEquals(1, assignment32.getStaffAssignments().size());
		assertEquals(1, assignment33.getStaffAssignments().size());
		assertEquals(1, assignment34.getStaffAssignments().size());
	

		/**Prepare staff assignments*/
		StaffAssignment staffAssignment1 = staffAssignmentRepo.findById(new StaffAssignmentId(eolis, amt, assignment23)).get();
		StaffAssignment staffAssignment2 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment23)).get();
		StaffAssignment staffAssignment3 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment23)).get();

		
		/**Validate assignments's staff assignments*/
		assertThat(assignment23StaffAssignments, Matchers.containsInAnyOrder(staffAssignment1, staffAssignment2, staffAssignment3));
		
		
	}

	public static Assignment insertAssignment(String desc, EntityManager entityManager) {
		Assignment assignment = new Assignment();
		assignment.setDesc(desc);
		assertEquals(0, assignment.getAssignmentId());
		entityManager.persist(assignment);
		assertThat(assignment.getAssignmentId(), Matchers.greaterThan((long)0));
		entityManager.flush();
		return assignment;
	}

}
