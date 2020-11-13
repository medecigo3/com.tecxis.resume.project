package com.tecxis.resume;


import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.StaffSkillRepositoryTest.STAFF_SKILL_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.SUPPLIER_TABLE;
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

import com.tecxis.commons.persistence.id.StaffProjectAssignmentId;
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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adir = ProjectTest.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));		
		Assignment assignment1 = AssignmentTest.insertAssignment(Constants.ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ASSIGNMENT_TABLE));
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment amtStaffProjectAssignment = insertAStaffProjectAssignment(adir, amt, assignment1, entityManager);		
		List <StaffProjectAssignment> amtStaffProjectAssignments = new ArrayList <> ();		
		amtStaffProjectAssignments.add(amtStaffProjectAssignment);				
		entityManager.merge(adir);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffProjectAssignment() {
		Project  parcours = projectRepo.findByNameAndVersion(Constants.PARCOURS, Constants.VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT14);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(parcours, amt, assignment14);	
		assertEquals(62, amt.getStaffProjectAssignments().size());		
		assertEquals(6, parcours.getStaffProjectAssignments().size());
		assertEquals(1, assignment14.getStaffProjectAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();

		/**Validate staff -> assignments*/
		assertEquals(63, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
		/**Remove staff -> assignment*/
		/**Tests initial state parent table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));		
		/**Test other parents for control*/ 
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 	
	
		/**StaffProjectAssignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Assignment */		
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Tests post state parent table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));		
		/**Test other parents for control*/ 
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
		/**Validate staff -> assignments*/		
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		parcours = projectRepo.findByNameAndVersion(Constants.PARCOURS, Constants.VERSION_1);
		amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assignment14 = assignmentRepo.getAssignmentByDesc(Constants.ASSIGNMENT14);	
		assertEquals(61, amt.getStaffProjectAssignments().size());		
		assertEquals(5, parcours.getStaffProjectAssignments().size());
		assertEquals(0, assignment14.getStaffProjectAssignments().size());
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testCascadedDeletion() {
		/**Retrieve staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		
		/**Retrieve from project*/
		Project fortis = projectRepo.findByNameAndVersion(Constants.FORTIS, Constants.VERSION_1);
		assertNotNull(fortis);
		assertEquals(Constants.FORTIS, fortis.getName());
		assertEquals(Constants.VERSION_1, fortis.getVersion());
		
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
		assertEquals(63, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		entityManager.remove(staffAssignment2);
		/**Entity deletion is un-scheduled when entity isn't detached from persistence context*/
		entityManager.flush();		
		/**Test entity was not removed*/
		assertEquals(63, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id2));
		
		/**Detach entities from persistent context*/
		entityManager.clear();
		staffAssignment3 = staffProjectAssignmentRepo.findById(id2).get();
		assertNotNull(staffAssignment3);
		entityManager.remove(staffAssignment3);
		entityManager.flush();
		assertNull(entityManager.find(StaffProjectAssignment.class, id2));
		/**Test entity was removed*/
		assertEquals(62, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
				

	}
	
	@Test
	public void testToString() {
		fail("TODO");
	}
	
	public static StaffProjectAssignment insertAStaffProjectAssignment(Project project, Staff staff,  Assignment assignment, EntityManager entityManager) {
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment(project, staff, assignment);
		entityManager.persist(staffProjectAssignment);
		entityManager.flush();
		return staffProjectAssignment;
		
	}

}
