package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.StaffProjectAssignmentTest.insertAStaffProjectAssignment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffProjectAssignment;
import com.tecxis.resume.domain.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffProjectAssignmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml",
		"classpath:validation-api-context.xml"})
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
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffProjectAssignment1() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client sagemcom = ClientTest.insertAClient(Constants.SAGEMCOM, entityManager);		
		Project ted = ProjectTest.insertAProject(Constants.TED, Constants.VERSION_1, sagemcom, entityManager);
		assertEquals(1, ted.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Prepare assignment*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		Assignment assignment12 = AssignmentTest.insertAssignment(Constants.ASSIGNMENT12, entityManager);
		assertEquals(1, assignment12.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getStaffProjectAssignments().size());		
		assertEquals(0, ted.getStaffProjectAssignments().size());
		assertEquals(0, assignment12.getStaffProjectAssignments().size());
		
		/**Create new StaffProjectAssignment*/
		StaffProjectAssignment newStaffProjectAssignment = new StaffProjectAssignment(ted,amt,assignment12);
	
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));		
		ted.addStaffProjectAssignment(newStaffProjectAssignment);
		amt.addStaffProjectAssignment(newStaffProjectAssignment);
		assignment12.addStaffProjectAssignment(newStaffProjectAssignment);
		
		entityManager.persist(newStaffProjectAssignment);
		entityManager.merge(ted);
		entityManager.merge(amt);
		entityManager.merge(assignment12);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffProjectAssignments().size());		
		assertEquals(1, ted.getStaffProjectAssignments().size());
		assertEquals(1, assignment12.getStaffProjectAssignments().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffProjectAssignment2() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client arval = ClientTest.insertAClient(Constants.ARVAL, entityManager);		
		Project aos = ProjectTest.insertAProject(Constants.AOS, Constants.VERSION_1, arval, entityManager);
		assertEquals(1, aos.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		Assignment assignment47 = AssignmentTest.insertAssignment(Constants.ASSIGNMENT47, entityManager);
		assertEquals(1, assignment47.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
		/**Validate staff -> assignments*/		
		assertEquals(0, amt.getStaffProjectAssignments().size());		
		assertEquals(0, aos.getStaffProjectAssignments().size());
		assertEquals(0, assignment47.getStaffProjectAssignments().size());
		
		/**Create new StaffProjectAssignment*/
		StaffProjectAssignment newStaffProjectAssignment = new StaffProjectAssignment(aos, amt, assignment47);
		
		/**Prepare staff -> assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		aos.addStaffProjectAssignment(newStaffProjectAssignment);
		amt.addStaffProjectAssignment(newStaffProjectAssignment);
		assignment47.addStaffProjectAssignment(newStaffProjectAssignment);
		
		entityManager.persist(newStaffProjectAssignment);
		entityManager.merge(aos);
		entityManager.merge(amt);
		entityManager.merge(assignment47);
		entityManager.flush();
		
		/**Validate staff -> assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffProjectAssignments().size());		
		assertEquals(1, aos.getStaffProjectAssignments().size());
		assertEquals(1, assignment47.getStaffProjectAssignments().size());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingStaffProjectAssignment() {
		
		/**Find projects*/
		Project eolis = projectRepo.findByNameAndVersion(Constants.EOLIS, Constants.VERSION_1);	
		
		/**Validate Projects to test*/
		assertEquals(Constants.EOLIS, eolis.getName());
		assertEquals(Constants.VERSION_1, eolis.getVersion());
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		
		/**Validate Staff to test*/
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
						
		/**Find assignments*/		
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT23);
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT31);		
		Assignment assignment32 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT32);
		Assignment assignment33 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT33);		
		Assignment assignment34 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT34); 
		
		
		/**Validate Assignments to test**/
		assertEquals(Constants.ASSIGNMENT23, assignment23.getDesc());
		assertEquals(Constants.ASSIGNMENT31, assignment31.getDesc());
		assertEquals(Constants.ASSIGNMENT32, assignment32.getDesc());
		assertEquals(Constants.ASSIGNMENT33, assignment33.getDesc());
		assertEquals(Constants.ASSIGNMENT34, assignment34.getDesc());
		
		
		/**Find StaffProjectAssignments to test*/
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment23)).get();
		StaffProjectAssignment staffProjectAssignment2 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment31)).get();
		StaffProjectAssignment staffProjectAssignment3 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment32)).get();
		StaffProjectAssignment staffProjectAssignment4 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment33)).get();
		StaffProjectAssignment staffProjectAssignment5 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment34)).get();
	
		/**Validate StaffProjectAssignments already exist in Project*/
		List <StaffProjectAssignment>  eolisStaffProjectAssignments = eolis.getStaffProjectAssignments();
		assertEquals(5, eolisStaffProjectAssignments.size());		
		assertThat(eolisStaffProjectAssignments,  Matchers.containsInAnyOrder(staffProjectAssignment1,  staffProjectAssignment2,  staffProjectAssignment3, staffProjectAssignment4, staffProjectAssignment5));
		
		
		/**Create new StaffProjectAssignment*/
		StaffProjectAssignment newStaffProjectAssignment = new StaffProjectAssignment(eolis, amt, assignment34);
		
		/**Add a duplicate Staff and Project association**/		
		assignment34.addStaffProjectAssignment(newStaffProjectAssignment); /***  <==== Throws EntityExistsException */
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffProjectAssignment() {
		Project  ted = projectRepo.findByNameAndVersion(Constants.TED, Constants.VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		Assignment assignment12 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT12);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(ted, amt, assignment12);	
		assertEquals(62, amt.getStaffProjectAssignments().size());		
		assertEquals(4, ted.getStaffProjectAssignments().size());
		assertEquals(1, assignment12.getStaffProjectAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
		/**Remove staff project assignment*/
		/**StaffProjectAssignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Assignment */
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
	
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
				
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		ted = projectRepo.findByNameAndVersion(Constants.TED, Constants.VERSION_1);
		amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assignment12 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT12);	
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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client sagemcom = ClientTest.insertAClient(Constants.SAGEMCOM, entityManager);		
		Project ted = ProjectTest.insertAProject(Constants.TED, Constants.VERSION_1, sagemcom, entityManager);
		assertEquals(1, ted.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		Assignment assignment12 = AssignmentTest.insertAssignment(Constants.ASSIGNMENT12, entityManager);
		assertEquals(1, assignment12.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
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
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));	
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id));
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignments() {
		/**Prepare projects*/
		Project eolis = projectRepo.findByNameAndVersion(Constants.EOLIS, Constants.VERSION_1);
		assertEquals(Constants.EOLIS, eolis.getName());
		assertEquals(Constants.VERSION_1, eolis.getVersion());
		List <StaffProjectAssignment>  eolisStaffProjectAssignments = eolis.getStaffProjectAssignments();
		assertEquals(5, eolisStaffProjectAssignments.size());
		
		Project morningstarv1 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_1);
		assertEquals(Constants.MORNINGSTAR, morningstarv1.getName());
		assertEquals(Constants.VERSION_1, morningstarv1.getVersion());
		Project morningstarv2 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_2);
		assertEquals(Constants.MORNINGSTAR, morningstarv2.getName());
		assertEquals(Constants.VERSION_2, morningstarv2.getVersion());
		
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assertNotNull(amt);
		List <StaffProjectAssignment> amtStaffProjectAssignments = amt.getStaffProjectAssignments();
		assertEquals(62, amt.getProjects().size());
		assertEquals(62, amtStaffProjectAssignments.size());
		
		/**Prepare assignments*/		
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT23);		
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT31);
		Assignment assignment32 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT32);
		Assignment assignment33 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT33);
		Assignment assignment34 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT34);		
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
	
	@Test
	public void testDescIsNotNull() {
		Assignment assignment = new Assignment();
		Set<ConstraintViolation<Assignment>> violations = validator.validate(assignment);
        assertFalse(violations.isEmpty());
		
	}
	
	@Test
	public void testToString() {
		Assignment assignment = new Assignment();
		assignment.toString();
	}

	public static Assignment insertAssignment(String desc, EntityManager entityManager) {
		Assignment assignment = new Assignment();
		assignment.setDesc(desc);
		assertEquals(0, assignment.getId());
		entityManager.persist(assignment);
		assertThat(assignment.getId(), Matchers.greaterThan((long)0));
		entityManager.flush();
		return assignment;
	}

}
