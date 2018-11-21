package com.tecxis.resume;

import static com.tecxis.resume.StaffAssignmentTest.STAFFASSIGNMENT_TABLE;
import static com.tecxis.resume.StaffAssignmentTest.insertAStaffAssignment;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT12;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT23;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT31;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT32;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT33;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT34;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT_TABLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.SAGEMCOM;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EOLIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.TED;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_2;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
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
	private JdbcTemplate jdbcTemplate;
	
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client sagemcom = ClientTest.insertAClient(SAGEMCOM, entityManager);		
		Project ted = ProjectTest.insertAProject(TED, VERSION_1, sagemcom, entityManager);
		assertEquals(1, ted.getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		Assignment assignment12 = AssignmentTest.insertAssignment(ASSIGNMENT12, entityManager);
		assertEquals(1, assignment12.getAssignmentId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getStaffAssignments().size());		
		assertEquals(0, ted.getStaffAssignments().size());
		assertEquals(0, assignment12.getStaffAssignments().size());
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		StaffAssignment amtStaffAssignment = insertAStaffAssignment(ted, amt, assignment12, entityManager);
		ted.addStaffAssignment(amtStaffAssignment);
		amt.addStaffAssignment(amtStaffAssignment);
		assignment12.addStaffAssignment(amtStaffAssignment);
		
		entityManager.merge(ted);
		entityManager.merge(amt);
		entityManager.merge(assignment12);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffAssignments().size());		
		assertEquals(1, ted.getStaffAssignments().size());
		assertEquals(1, assignment12.getStaffAssignments().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffAssignment() {
		Project  ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Assignment assignment12 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT12);		
		StaffAssignmentId id = new StaffAssignmentId(ted, amt, assignment12);	
		assertEquals(62, amt.getStaffAssignments().size());		
		assertEquals(4, ted.getStaffAssignments().size());
		assertEquals(1, assignment12.getStaffAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();

		/**Validate staff assignments*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		StaffAssignment staffAssignment1 = staffAssignmentRepo.findById(id).get();
		assertNotNull(staffAssignment1);
		
		/**Remove staff assignment*/
		entityManager.remove(staffAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Validate staff assignments*/
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		assertNull(entityManager.find(StaffAssignment.class, id));
		ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		amt = staffRepo.getStaffLikeName(AMT_NAME);
		assignment12 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT12);	
		assertEquals(61, amt.getStaffAssignments().size());		
		assertEquals(3, ted.getStaffAssignments().size());
		assertEquals(0, assignment12.getStaffAssignments().size());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaffAssignments() {		
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client sagemcom = ClientTest.insertAClient(SAGEMCOM, entityManager);		
		Project ted = ProjectTest.insertAProject(TED, VERSION_1, sagemcom, entityManager);
		assertEquals(1, ted.getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment12 = AssignmentTest.insertAssignment(ASSIGNMENT12, entityManager);
		assertEquals(1, assignment12.getAssignmentId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		StaffAssignmentId id = new StaffAssignmentId(ted, amt, assignment12);
		assertNull(entityManager.find(StaffAssignment.class, id));
		
		/**Prepare staff assignments*/		
		StaffAssignment amtStaffAssignment = insertAStaffAssignment(ted, amt, assignment12, entityManager);		
		List <StaffAssignment> amtStaffAssignments = new ArrayList <> ();		
		amtStaffAssignments.add(amtStaffAssignment);
		ted.setStaffAssignment(amtStaffAssignments);
		assignment12.setStaffAssignment(amtStaffAssignments);
		amt.setStaffAssignment(amtStaffAssignments);				
		entityManager.merge(ted);
		entityManager.merge(amt);
		entityManager.merge(assignment12);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));	
		assertNotNull(entityManager.find(StaffAssignment.class, id));
	}
	
	
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
