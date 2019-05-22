package com.tecxis.resume.persistence;

import static com.tecxis.resume.AssignmentTest.insertAssignment;
import static com.tecxis.resume.StaffProjectAssignmentTest.STAFFPROJECTASSIGNMENT_TABLE;
import static com.tecxis.resume.StaffProjectAssignmentTest.insertAStaffProjectAssignment;
import static com.tecxis.resume.StaffTest.insertAStaff;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT1;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT53;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT_TABLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

import com.tecxis.resume.Assignment;
import com.tecxis.resume.Client;
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Project;
import com.tecxis.resume.ProjectTest;
import com.tecxis.resume.Staff;
import com.tecxis.resume.StaffProjectAssignment;
import com.tecxis.resume.StaffProjectAssignmentId;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class StaffProjectAssignmentRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Sql(
		scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testShouldCreateRowsAndSetIds() {
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignment staffProjectAssignment = insertAStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		assertNotNull(staffProjectAssignment);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertStaffProjectAssignment() {
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignment inStaffProjectAssignment = insertAStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adirProject, amt, assignment1);	
		StaffProjectAssignment outStaffAssignment = staffProjectAssignmentRepo.findById(id).get();
		assertEquals(inStaffProjectAssignment, outStaffAssignment);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignmentById() {
		Project  sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Assignment assignment53 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT53);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(sherpa, amt, assignment53);		
		StaffProjectAssignment staffProjectAssignment = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignment tempStaffProjectAssignment = insertAStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		staffProjectAssignmentRepo.delete(tempStaffProjectAssignment);
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adirProject, amt, assignment1);	
		assertFalse(staffProjectAssignmentRepo.findById(id).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <StaffProjectAssignment> staffProjectAssignments = staffProjectAssignmentRepo.findAll();
		assertEquals(63, staffProjectAssignments.size());
	}

}
