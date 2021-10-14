package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.TASK1;
import static com.tecxis.resume.domain.Constants.TASK14;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
public class AssignmentTest {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private AssignmentRepository staffProjectAssignmentRepo;
		
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private TaskRepository taskRepo;
		

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adir = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Prepare Task*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));		
		Task assignment1 = Utils.insertTask(TASK1, entityManager);
		assertEquals(1L, assignment1.getId().longValue());
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
		Assignment amtAssignment = Utils.insertAssignment(adir, amt, assignment1, entityManager);		
		List <Assignment> amtAssignments = new ArrayList <> ();		
		amtAssignments.add(amtAssignment);				
		entityManager.merge(adir);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveAssignment() {
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task task14 = taskRepo.getTaskByDesc(TASK14);		
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());	
		assertEquals(62, amt.getAssignments().size());		
		assertEquals(6, parcours.getAssignments().size());
		assertEquals(1, task14.getAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();

		/**Validate staff -> assignments*/
		assertEquals(63, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
		Assignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
	
		/**Tests initial state parent table
		* STAFF_TABLE
		* Tests initial state children tables
		* INTEREST_TABLE)
		* StaffSkill.STAFF_SKILL_TABLE
		* ENROLMENT_TABLE
		* EMPLOYMENT_CONTRACT_TABLE	
		* SUPPLY_CONTRACT_TABLE
		* ASSIGNMENT_TABLE
		* Test other parents for control
		* SKILL_TABLE
		* SUPPLIER_TABLE		
		* CONTRACT_TABLE*/ 
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Assignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Task */		
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Tests tables post state*/
		SchemaUtils.testStateAfterAmtParcoursAssignment14AssignmentDelete(jdbcTemplate);	
		
		/**Validate staff -> assignments*/		
		assertNull(entityManager.find(Assignment.class, id));
		parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		task14 = taskRepo.getTaskByDesc(TASK14);	
		assertEquals(61, amt.getAssignments().size());		
		assertEquals(5, parcours.getAssignments().size());
		assertEquals(0, task14.getAssignments().size());
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testCascadedDeletion() {
		/**Retrieve staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		
		/**Retrieve from project*/
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertNotNull(fortis);
		assertEquals(FORTIS, fortis.getName());
		assertEquals(VERSION_1, fortis.getVersion());
		
		/**Here persist operation will be cascaded from fortis (parent) to StaffAssignments (child) 
		 * When we delete the staff Task instance referenced by fortis which is also loaded in the persistence context 
		 * the staff association will not be removed from DB
		 * Comment out these two lines above to prove otherwise*/		
		List <Assignment>  fortisStaffAssignments = fortis.getAssignments();
		assertEquals(3, fortisStaffAssignments.size());

		AssignmentId id2 = new AssignmentId(fortis.getId(), amt.getId(), entityManager.find(Task.class, 6L).getId());	
		Assignment staffAssignment2 =	entityManager.find(Assignment.class, id2);
		Assignment staffAssignment3 = staffProjectAssignmentRepo.findById(id2).get();
		assertTrue(staffAssignment2.equals(staffAssignment3));
		assertNotNull(staffAssignment3);
			
		/**The the removed staff Task is referenced by fortis hence the deletion is unscheduled --> see in hibernate trace level logs*/
		assertEquals(63, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
		entityManager.remove(staffAssignment2);
		/**Entity deletion is un-scheduled when entity isn't detached from persistence context*/
		entityManager.flush();		
		/**Test entity was not removed*/
		assertEquals(63, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(Assignment.class, id2));
		
		/**Detach entities from persistent context*/
		entityManager.clear();
		staffAssignment3 = staffProjectAssignmentRepo.findById(id2).get();
		assertNotNull(staffAssignment3);
		entityManager.remove(staffAssignment3);
		entityManager.flush();
		assertNull(entityManager.find(Assignment.class, id2));
		/**Test entity was removed*/
		assertEquals(62, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
				

	}
	
	@Test
	public void testToString() {
		Assignment assignment = new Assignment();
		assertThat(assignment.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
		
	}

}
