package com.tecxis.resume.domain;

import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.util.Utils;
import org.hamcrest.Matchers;
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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually (see the blog: https://www.marcobehler.com/2014/06/25/should-my-tests-be-transactional)
@SqlConfig(dataSource="dataSourceHelper")
public class TaskTest {
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {			
		Task assignment12 = Utils.insertTask(TASK12, Integer.valueOf(0), entityManager);//RES-72
		assertThat(assignment12.getId(), Matchers.greaterThan((long)0));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Add_Assignment1() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client sagemcom = Utils.insertClient(SAGEMCOM, entityManager);		
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, null, entityManager);
		assertEquals(1, ted.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Prepare Task*/	
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
		Task assignment12 = Utils.insertTask(TASK12, Integer.valueOf(0), entityManager);//RES-72
		assertEquals(1L, assignment12.getId().longValue());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getAssignments().size());		
		assertEquals(0, ted.getAssignments().size());
		assertEquals(0, assignment12.getAssignments().size());
		
		/**Create new Assignment*/
		Assignment newAssignment = new Assignment(ted,amt,assignment12);
	
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));		
		ted.addAssignment(newAssignment);
		amt.addAssignment(newAssignment);
		assignment12.addAssignment(newAssignment);
		
		entityManager.persist(newAssignment);
		entityManager.merge(ted);
		entityManager.merge(amt);
		entityManager.merge(assignment12);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(1, amt.getAssignments().size());		
		assertEquals(1, ted.getAssignments().size());
		assertEquals(1, assignment12.getAssignments().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Add_Assignment2() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client arval = Utils.insertClient(ARVAL, entityManager);		
		Project aos = Utils.insertProject(AOS, VERSION_1, arval, null, entityManager);
		assertEquals(1, aos.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Prepare Task*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		Task assignment47 = Utils.insertTask(TASK47, Integer.valueOf(0), entityManager);//RES-72
		assertEquals(1L, assignment47.getId().longValue());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
		
		/**Validate staff -> assignments*/		
		assertEquals(0, amt.getAssignments().size());		
		assertEquals(0, aos.getAssignments().size());
		assertEquals(0, assignment47.getAssignments().size());
		
		/**Create new Assignment*/
		Assignment newAssignment = new Assignment(aos, amt, assignment47);
		
		/**Prepare staff -> assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		aos.addAssignment(newAssignment);
		amt.addAssignment(newAssignment);
		assignment47.addAssignment(newAssignment);
		
		entityManager.persist(newAssignment);
		entityManager.merge(aos);
		entityManager.merge(amt);
		entityManager.merge(assignment47);
		entityManager.flush();
		
		/**Validate staff -> assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(1, amt.getAssignments().size());		
		assertEquals(1, aos.getAssignments().size());
		assertEquals(1, assignment47.getAssignments().size());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_Add_Existing_Assignment() {
		
		/**Find projects*/
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);	
		
		/**Validate Projects to test*/
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		
		/**Validate Staff to test*/
		assertEquals(AMT_NAME, amt.getFirstName());
						
		/**Find assignments*/		
		Task task23 = taskRepo.getTaskByDesc(TASK23);
		Task task31 = taskRepo.getTaskByDesc(TASK31);		
		Task task32 = taskRepo.getTaskByDesc(TASK32);
		Task task33 = taskRepo.getTaskByDesc(TASK33);		
		Task task34 = taskRepo.getTaskByDesc(TASK34); 
		
		
		/**Validate Assignments to test**/
		assertEquals(TASK23, task23.getDesc());
		assertEquals(TASK31, task31.getDesc());
		assertEquals(TASK32, task32.getDesc());
		assertEquals(TASK33, task33.getDesc());
		assertEquals(TASK34, task34.getDesc());
		
		
		/**Find Assignments to test*/
		Assignment assignment1 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task23.getId())).get();
		Assignment assignment2 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task31.getId())).get();
		Assignment assignment3 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task32.getId())).get();
		Assignment assignment4 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task33.getId())).get();
		Assignment assignment5 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task34.getId())).get();
	
		/**Validate Assignments already exist in Project*/
		List <Assignment>  eolisAssignments = eolis.getAssignments();
		assertEquals(5, eolisAssignments.size());		
		assertThat(eolisAssignments,  Matchers.containsInAnyOrder(assignment1,  assignment2,  assignment3, assignment4, assignment5));
		
		
		/**Create new Assignment*/
		Assignment newAssignment = new Assignment(eolis, amt, task34);
		
		/**Add a duplicate Staff and Project association**/		
		task34.addAssignment(newAssignment); /***  <==== Throws EntityExistsException */
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Remove_Assignment() {
		Project  ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task task12 = taskRepo.getTaskByDesc(TASK12);		
		AssignmentId id = new AssignmentId(ted.getId(), amt.getId(), task12.getId());	
		assertEquals(62, amt.getAssignments().size());		
		assertEquals(4, ted.getAssignments().size());
		assertEquals(1, task12.getAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		Assignment assignment1 = assignmentRepo.findById(id).get();
		assertNotNull(assignment1);
		
		/**Remove staff project Task*/
		/**Assignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Task */
		entityManager.remove(assignment1);
		entityManager.flush();
		entityManager.clear();
	
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
				
		assertNull(entityManager.find(Assignment.class, id));
		ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		task12 = taskRepo.getTaskByDesc(TASK12);	
		assertEquals(61, amt.getAssignments().size());		
		assertEquals(3, ted.getAssignments().size());
		assertEquals(0, task12.getAssignments().size());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Assignments_And_RemoveOrhpansWithOrm() {//RES-7
		Task task12 = taskRepo.findById(TASK12_ID).get();
		//Fetch task12 assignments
		Assignment task12Assignment = assignmentRepo.findById(new AssignmentId(new ProjectId( PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID), STAFF_AMT_ID, TASK12_ID)).get();
		//TODO RES-70 Impl. AssignmentValidator here
		//Test task is valid
		assertEquals(SUCCESS, isTaskValid(task12, TASK12, null, List.of(task12Assignment))); //RES-7

		//Build new Assignment to set
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Staff amt = staffRepo.findById(STAFF_AMT_ID).get();
		Assignment morningStartv1ProjectAmtTask12Assignment = buildAssignment(morningstartV1Project, amt, task12);

		update_Task12_With_Assignments_InJpa( em -> {
				//0 orphan(s) removed, add 1 new assignment.
				task12.setAssignments(List.of(morningStartv1ProjectAmtTask12Assignment));
				em.merge(task12);
				em.flush();
				em.clear();
			},
		entityManager, jdbcTemplateProxy);
		//Validate new Task
		Task newTask12 = taskRepo.findById(TASK12_ID).get();
		Assignment newTask12Assignment = assignmentRepo.findById(new AssignmentId(new ProjectId( PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID), STAFF_AMT_ID, TASK12_ID)).get();
		assertEquals(SUCCESS, isTaskValid(newTask12, TASK12, null, List.of(task12Assignment, newTask12Assignment)));//RES-7
	}
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Assignments_And_RemoveOrhpansWithOrm_NullSet() {//RES-7
		Task task12 = taskRepo.findById(TASK12_ID).get();
		//Fetch task12 assignments
		Assignment task12Assignment = assignmentRepo.findById(new AssignmentId(new ProjectId( PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID), STAFF_AMT_ID, TASK12_ID)).get();
		//TODO RES-70 Impl. AssignmentValidator here
		//Test task is valid
		assertEquals(SUCCESS, isTaskValid(task12, TASK12, null, List.of(task12Assignment)));//RES-7

		update_Task12_With_NullAssignments_InJpa( em -> {
					//0 orphan(s) removed
					task12.setAssignments(null);
					em.merge(task12);
					em.flush();
					em.clear();
				},
				entityManager, jdbcTemplateProxy);
		//Validate new Task
		Task newTask12 = taskRepo.findById(TASK12_ID).get();
		assertEquals(SUCCESS, isTaskValid(newTask12, TASK12, null, List.of(task12Assignment)));//RES-7
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Get_Assignments() {
		/**Prepare projects*/
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		List <Assignment>  eolisAssignments = eolis.getAssignments();
		assertEquals(5, eolisAssignments.size());
		
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertNotNull(amt);
		List <Assignment> amtAssignments = amt.getAssignments();
		assertEquals(62, amt.getProjects().size());
		assertEquals(62, amtAssignments.size());
		
		/**Prepare assignments*/		
		Task task23 = taskRepo.getTaskByDesc(TASK23);		
		Task task31 = taskRepo.getTaskByDesc(TASK31);
		Task task32 = taskRepo.getTaskByDesc(TASK32);
		Task task33 = taskRepo.getTaskByDesc(TASK33);	
		Task task34 = taskRepo.getTaskByDesc(TASK34);		
		List <Assignment> assignment23Assignments = task23.getAssignments();
		assertEquals(3, assignment23Assignments.size());		
		assertEquals(3, task31.getAssignments().size());
		assertEquals(1, task32.getAssignments().size());
		assertEquals(1, task33.getAssignments().size());
		assertEquals(1, task34.getAssignments().size());
	

		/**Prepare staff assignments*/
		Assignment assignment1 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task23.getId())).get();
		Assignment assignment2 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task23.getId())).get();
		Assignment assignment3 = assignmentRepo.findById(new AssignmentId(morningstarv2.getId(), amt.getId(), task23.getId())).get();

		
		/**Validate assignments's staff assignments*/
		assertThat(assignment23Assignments, Matchers.containsInAnyOrder(assignment1, assignment2, assignment3));
				
	}
	
	@Test
	public void testDescIsNotNull() {
		Task task = new Task();
		Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertFalse(violations.isEmpty());
		
	}

	@Test
	public void testToString() {
		Task task = new Task();
		assertThat(task.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}

}
