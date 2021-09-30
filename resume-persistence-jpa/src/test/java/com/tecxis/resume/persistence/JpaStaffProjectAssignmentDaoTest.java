package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT1;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT53;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.SHERPA;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
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
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
public class JpaStaffProjectAssignmentDaoTest {

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
		scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testShouldCreateRowsAndSetIds() {
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment staffProjectAssignment = Utils.insertStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		assertNotNull(staffProjectAssignment);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertStaffProjectAssignment() {
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment inStaffProjectAssignment = Utils.insertStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adirProject.getId(), amt.getId(), assignment1.getId());	
		StaffProjectAssignment outStaffAssignment = staffProjectAssignmentRepo.findById(id).get();
		assertEquals(inStaffProjectAssignment, outStaffAssignment);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignmentById() {
		Project  sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Assignment assignment53 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT53);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(sherpa.getId(), amt.getId(), assignment53.getId());		
		StaffProjectAssignment staffProjectAssignment = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment tempStaffProjectAssignment = Utils.insertStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		staffProjectAssignmentRepo.delete(tempStaffProjectAssignment);
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adirProject.getId(), amt.getId(), assignment1.getId());	
		assertFalse(staffProjectAssignmentRepo.findById(id).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <StaffProjectAssignment> staffProjectAssignments = staffProjectAssignmentRepo.findAll();
		assertEquals(63, staffProjectAssignments.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <StaffProjectAssignment> pageableStaffProjectAssignment = staffProjectAssignmentRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableStaffProjectAssignment.getSize());
	}
}
