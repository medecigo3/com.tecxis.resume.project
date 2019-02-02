package com.tecxis.resume;

import static com.tecxis.resume.StaffProjectAssignmentTest.STAFFPROJECTASSIGNMENT_TABLE;
import static com.tecxis.resume.StaffProjectAssignmentTest.insertAStaffProjectAssignment;
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
import com.tecxis.resume.persistence.StaffProjectAssignmentRepository;
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
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffProjectAssignment() {
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
		assertEquals(0, amt.getStaffProjectAssignments().size());		
		assertEquals(0, ted.getStaffProjectAssignments().size());
		assertEquals(0, assignment12.getStaffProjectAssignments().size());
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignment amtStaffProjectAssignment = insertAStaffProjectAssignment(ted, amt, assignment12, entityManager);
		ted.addStaffProjectAssignment(amtStaffProjectAssignment);
		amt.addStaffProjectAssignment(amtStaffProjectAssignment);
		assignment12.addStaffProjectAssignment(amtStaffProjectAssignment);
		
		entityManager.merge(ted);
		entityManager.merge(amt);
		entityManager.merge(assignment12);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffProjectAssignments().size());		
		assertEquals(1, ted.getStaffProjectAssignments().size());
		assertEquals(1, assignment12.getStaffProjectAssignments().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffProjectAssignment() {
		Project  ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Assignment assignment12 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT12);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(ted, amt, assignment12);	
		assertEquals(62, amt.getStaffProjectAssignments().size());		
		assertEquals(4, ted.getStaffProjectAssignments().size());
		assertEquals(1, assignment12.getStaffProjectAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();

		/**Validate staff assignments*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
		/**Remove staff project assignment*/
		/**StaffProjectAssignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Assignment */
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Validate staff assignments*/
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		amt = staffRepo.getStaffLikeName(AMT_NAME);
		assignment12 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT12);	
		assertEquals(61, amt.getStaffProjectAssignments().size());		
		assertEquals(3, ted.getStaffProjectAssignments().size());
		assertEquals(0, assignment12.getStaffProjectAssignments().size());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaffProjectAssignments() {		
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
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(ted, amt, assignment12);
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		
		/**Prepare staff assignments*/		
		StaffProjectAssignment amtStaffProjectAssignment = insertAStaffProjectAssignment(ted, amt, assignment12, entityManager);		
		List <StaffProjectAssignment> amtStaffAssignments = new ArrayList <> ();		
		amtStaffAssignments.add(amtStaffProjectAssignment);
		ted.setStaffProjectAssignment(amtStaffAssignments);
		assignment12.setStaffProjectAssignment(amtStaffAssignments);
		amt.setStaffProjectAssignment(amtStaffAssignments);				
		entityManager.merge(ted);
		entityManager.merge(amt);
		entityManager.merge(assignment12);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));	
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id));
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignments() {
		/**Prepare projects*/
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		List <StaffProjectAssignment>  eolisStaffProjectAssignments = eolis.getStaffProjectAssignments();
		assertEquals(5, eolisStaffProjectAssignments.size());
		
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		List <StaffProjectAssignment> amtStaffProjectAssignments = amt.getStaffProjectAssignments();
		assertEquals(62, amt.getProjects().size());
		assertEquals(62, amtStaffProjectAssignments.size());
		
		/**Prepare assignments*/		
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT23);		
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT31);
		Assignment assignment32 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT32);
		Assignment assignment33 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT33);
		Assignment assignment34 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT34);		
		List <StaffProjectAssignment> assignment23StaffProjectAssignments = assignment23.getStaffProjectAssignments();
		assertEquals(3, assignment23StaffProjectAssignments.size());		
		assertEquals(3, assignment31.getStaffProjectAssignments().size());
		assertEquals(1, assignment32.getStaffProjectAssignments().size());
		assertEquals(1, assignment33.getStaffProjectAssignments().size());
		assertEquals(1, assignment34.getStaffProjectAssignments().size());
	

		/**Prepare staff assignments*/
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment23)).get();
		StaffProjectAssignment staffProjectAssignment2 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment23)).get();
		StaffProjectAssignment staffProjectAssignment3 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment23)).get();

		
		/**Validate assignments's staff assignments*/
		assertThat(assignment23StaffProjectAssignments, Matchers.containsInAnyOrder(staffProjectAssignment1, staffProjectAssignment2, staffProjectAssignment3));
				
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
