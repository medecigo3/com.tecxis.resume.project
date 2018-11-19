package com.tecxis.resume;


import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT1;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT53;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT6;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT_TABLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.FORTIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.insertAProject;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
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
public class StaffAssignmentTest {

	public static final String STAFFASSIGNMENT_TABLE = "STAFF_ASSIGNMENT";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private StaffAssignmentRepository staffAssignmentRepo;
		
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
		

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = insertAClient(BARCLAYS, entityManager);		
		Project adir = insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = AssignmentTest.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getAssignmentId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		StaffAssignment amtStaffAssignment = insertAStaffAssignment(adir, amt, assignment1, entityManager);		
		List <StaffAssignment> amtStaffAssignments = new ArrayList <> ();		
		amtStaffAssignments.add(amtStaffAssignment);				
		entityManager.merge(adir);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffAssignment() {
		Project  sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Assignment assignment53 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT53);		
		StaffAssignmentId id = new StaffAssignmentId(sherpa, amt, assignment53);		
		StaffAssignment staffAssignment1 = staffAssignmentRepo.findById(id).get();
		assertNotNull(staffAssignment1);
		
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));		
		entityManager.remove(staffAssignment1);
		entityManager.flush();
		entityManager.clear();
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		assertNull(entityManager.find(StaffAssignment.class, id));
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testCascadedDeletion() {
		/**Retrieve staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		
		/**Retrieve from project*/
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertNotNull(fortis);
		assertEquals(FORTIS, fortis.getName());
		assertEquals(VERSION_1, fortis.getVersion());
		
		/**Here persist operation will be cascaded from fortis (parent) to StaffAssignments (child) 
		 * When we delete the staff assignment instance referenced by fortis which is also loaded in the persistence context 
		 * the staff association will not be removed from DB
		 * Comment out these to lines above to prove otherwise*/		
		List <StaffAssignment>  fortisStaffAssignments = fortis.getStaffAssignments();
		assertEquals(3, fortisStaffAssignments.size());

		StaffAssignmentId id2 = new StaffAssignmentId(fortis, amt, entityManager.find(Assignment.class, 6L));	
		StaffAssignment staffAssignment2 =	entityManager.find(StaffAssignment.class, id2);
		StaffAssignment staffAssignment3 = staffAssignmentRepo.findById(id2).get();
		assertTrue(staffAssignment2.equals(staffAssignment3));
		assertNotNull(staffAssignment3);
			
		/**The the removed staff assignment is referenced by fortis hence the deletion is unscheduled --> see in hibernate trace level logs*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		entityManager.remove(staffAssignment2);
		entityManager.flush();		
		/**Un-schedule entity deletion*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(StaffAssignment.class, id2));
				
		entityManager.clear();
		staffAssignment3 = staffAssignmentRepo.findById(id2).get();
		assertNotNull(staffAssignment3);
		entityManager.remove(staffAssignment3);
		entityManager.flush();
		assertNull(entityManager.find(StaffAssignment.class, id2));		
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
				

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
