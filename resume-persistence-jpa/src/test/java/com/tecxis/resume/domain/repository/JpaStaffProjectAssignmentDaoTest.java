package com.tecxis.resume.domain.repository;

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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Constants;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffProjectAssignment;
import com.tecxis.resume.domain.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
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
		scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testShouldCreateRowsAndSetIds() {
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(Constants.ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment staffProjectAssignment = Utils.insertAStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		assertNotNull(staffProjectAssignment);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertStaffProjectAssignment() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(Constants.ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment inStaffProjectAssignment = Utils.insertAStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adirProject, amt, assignment1);	
		StaffProjectAssignment outStaffAssignment = staffProjectAssignmentRepo.findById(id).get();
		assertEquals(inStaffProjectAssignment, outStaffAssignment);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignmentById() {
		Project  sherpa = projectRepo.findByNameAndVersion(Constants.SHERPA, Constants.VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		Assignment assignment53 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT53);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(sherpa, amt, assignment53);		
		StaffProjectAssignment staffProjectAssignment = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(Constants.ASSIGNMENT1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment tempStaffProjectAssignment = Utils.insertAStaffProjectAssignment(adirProject, amt, assignment1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		staffProjectAssignmentRepo.delete(tempStaffProjectAssignment);
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adirProject, amt, assignment1);	
		assertFalse(staffProjectAssignmentRepo.findById(id).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <StaffProjectAssignment> staffProjectAssignments = staffProjectAssignmentRepo.findAll();
		assertEquals(63, staffProjectAssignments.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <StaffProjectAssignment> pageableStaffProjectAssignment = staffProjectAssignmentRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableStaffProjectAssignment.getSize());
	}
}
