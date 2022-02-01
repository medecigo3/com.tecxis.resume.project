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
import static com.tecxis.resume.domain.util.Utils.deleteAssignmentInJpa;
import static com.tecxis.resume.domain.util.Utils.insertAssignmentInJpa;
import static com.tecxis.resume.domain.util.Utils.isAssignmentValid;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

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
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class AssignmentTest {

	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
		
	@Autowired
	private ProjectRepository projectRepo;
		
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
		

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment() {
		final long clientId = 1L;
		final long projectId = 1L;
		final long staffId = 1L;
		final long taskId = 1L;
		
		/**Insert Client*/
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);
		assertEquals(clientId, barclays.getId().longValue());	
		
		/**Insert project*/
		Project adir = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(projectId, adir.getId().getProjectId());	
		
		/**Insert staff*/		
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);		
		assertEquals(staffId, amt.getId().longValue());
		
		/**Insert Task*/				
		Task task1 = Utils.insertTask(TASK1, entityManager);
		assertEquals(taskId, task1.getId().longValue());		
		
		/**Insert Assignment*/
		insertAssignmentInJpa(insertAssignmentFunction->{
			Assignment amtAssignment  = new Assignment(adir, amt, task1);
			entityManager.persist(amtAssignment);
			entityManager.flush();	//manually commit the transaction	
			entityManager.clear(); //Detach managed entities from persistence context to reload new changes
		}, entityManager, jdbcTemplateProxy);	
		
		
		/**Validate Assignment is inserted*/
		AssignmentId newAssignmentId = new AssignmentId();
		newAssignmentId.setProjectId(new ProjectId(projectId, clientId));
		newAssignmentId.setStaffId(staffId);
		newAssignmentId.setTaskId(taskId);
		Assignment newAssignment = assignmentRepo.findById(newAssignmentId).get();
		assertEquals(SUCCESS, isAssignmentValid(newAssignment, ADIR, VERSION_1, BARCLAYS, AMT_NAME, AMT_LASTNAME, TASK1));
		
		/**Validate Project has new Assignment*/
		List <Assignment> newProjectAssignments = projectRepo.findByNameAndVersion(ADIR, VERSION_1).getAssignments();
		assertThat(newProjectAssignments).contains(newAssignment);
		/**Validate Staff has new Assignment*/
		List <Assignment> newStaffAssignemnts = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME).getAssignments();
		assertThat(newStaffAssignemnts).contains(newAssignment);
		/**Validate Task has new Assignment*/
		List <Assignment> newTaskAssignments = taskRepo.getTaskByDesc(TASK1).getAssignments();
		assertThat(newTaskAssignments).contains(newAssignment);
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveAssignment() {
		/**Find Project*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		/**Find Task*/
		Task task14 = taskRepo.getTaskByDesc(TASK14);
		
		/**Validate target Assignment*/
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());		
		assertEquals(62, amt.getAssignments().size());		
		assertEquals(6, parcours.getAssignments().size());
		assertEquals(1, task14.getAssignments().size());		
		entityManager.clear(); //Detach entities
		/**Find target Assignment*/
		Assignment staffProjectAssignment1 = assignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);		
	
		deleteAssignmentInJpa(deleteAssignmentFunction->{
			/**Assignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Task */	
			entityManager.remove(staffProjectAssignment1);
			entityManager.flush(); //manually commit the transaction
			entityManager.clear(); //Detach managed entities from persistence context to reload new changes
		}, entityManager, jdbcTemplateProxy);		


		/**Validate target Assignment does not exist*/
		assertNull(entityManager.find(Assignment.class, id));
		parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		/**Validate target Assignment associations*/
		amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		task14 = taskRepo.getTaskByDesc(TASK14);
		/**Validate target Staff -> Assignment(s)*/		
		assertEquals(61, amt.getAssignments().size());
		/**Validate target Project -> Assignment(s)*/
		assertEquals(5, parcours.getAssignments().size());
		/**Validate target Task -> Assignment(s)*/
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
		Assignment staffAssignment3 = assignmentRepo.findById(id2).get();
		assertTrue(staffAssignment2.equals(staffAssignment3));
		assertNotNull(staffAssignment3);
			
		/**The the removed staff Task is referenced by fortis hence the deletion is unscheduled --> see in hibernate trace level logs*/
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		entityManager.remove(staffAssignment2);
		/**Entity deletion is un-scheduled when entity isn't detached from persistence context*/
		entityManager.flush();		
		/**Test entity was not removed*/
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(Assignment.class, id2));
		
		/**Detach entities from persistent context*/
		entityManager.clear();
		staffAssignment3 = assignmentRepo.findById(id2).get();
		assertNotNull(staffAssignment3);
		entityManager.remove(staffAssignment3);
		entityManager.flush();
		assertNull(entityManager.find(Assignment.class, id2));
		/**Test entity was removed*/
		assertEquals(62, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
				

	}
	
	@Test
	public void testToString() {
		Assignment assignment = new Assignment();
		assertThat(assignment.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
		
	}

}
