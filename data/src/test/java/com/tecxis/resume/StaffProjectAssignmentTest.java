package com.tecxis.resume;


import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT1;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT53;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT_TABLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.FORTIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import com.tecxis.resume.persistence.StaffProjectAssignmentRepository;
import com.tecxis.resume.persistence.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class StaffProjectAssignmentTest {

	public static final String STAFFPROJECTASSIGNMENT_TABLE = "STAFF_PROJECT_ASSIGNMENT";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
		
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
		

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffProjectAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adir = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
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
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignment amtStaffProjectAssignment = insertAStaffProjectAssignment(adir, amt, assignment1, entityManager);		
		List <StaffProjectAssignment> amtStaffProjectAssignments = new ArrayList <> ();		
		amtStaffProjectAssignments.add(amtStaffProjectAssignment);				
		entityManager.merge(adir);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffProjectAssignment() {
		Project  sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Assignment assignment53 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT53);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(sherpa, amt, assignment53);		
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
		/**StaffProjectAssignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Assignment */
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));		
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		
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
		 * Comment out these two lines above to prove otherwise*/		
		List <StaffProjectAssignment>  fortisStaffAssignments = fortis.getStaffProjectAssignments();
		assertEquals(3, fortisStaffAssignments.size());

		StaffProjectAssignmentId id2 = new StaffProjectAssignmentId(fortis, amt, entityManager.find(Assignment.class, 6L));	
		StaffProjectAssignment staffAssignment2 =	entityManager.find(StaffProjectAssignment.class, id2);
		StaffProjectAssignment staffAssignment3 = staffProjectAssignmentRepo.findById(id2).get();
		assertTrue(staffAssignment2.equals(staffAssignment3));
		assertNotNull(staffAssignment3);
			
		/**The the removed staff assignment is referenced by fortis hence the deletion is unscheduled --> see in hibernate trace level logs*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		entityManager.remove(staffAssignment2);
		/**Entity deletion is un-scheduled when entity isn't detached from persistence context*/
		entityManager.flush();		
		/**Test entity was not removed*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id2));
		
		/**Detach entities from persistent context*/
		entityManager.clear();
		staffAssignment3 = staffProjectAssignmentRepo.findById(id2).get();
		assertNotNull(staffAssignment3);
		entityManager.remove(staffAssignment3);
		entityManager.flush();
		assertNull(entityManager.find(StaffProjectAssignment.class, id2));
		/**Test entity was removed*/
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
				

	}
	
	public static StaffProjectAssignment insertAStaffProjectAssignment(Project project, Staff staff,  Assignment assignment, EntityManager entityManager) {
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment();
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(project, staff, assignment);
		staffProjectAssignment.setStaffAssignmentId(id);
		entityManager.persist(staffProjectAssignment);
		entityManager.flush();
		return staffProjectAssignment;
		
	}

}
