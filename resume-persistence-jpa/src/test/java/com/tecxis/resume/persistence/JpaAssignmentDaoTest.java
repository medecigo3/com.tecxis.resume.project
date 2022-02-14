package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.MICROPOLE;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.TASK1;
import static com.tecxis.resume.domain.Constants.TASK14;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.util.Utils.deleteAssignmentInJpa;
import static com.tecxis.resume.domain.util.Utils.insertAssignmentInJpa;
import static com.tecxis.resume.domain.util.Utils.isAssignmentValid;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
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
import com.tecxis.resume.domain.Task;
import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.util.Utils;
import com.tecxis.resume.domain.util.function.ValidationResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaAssignmentDaoTest {

	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private ClientRepository clientRepo;	
	
	@Autowired
	private AssignmentDao assignmentDao;	

	@Sql(
		scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testSave_UpdateStaff() {
		/**Find Project*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
//		assertEquals(6, parcours.getAssignments().size()); // test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
//		assertEquals(62, amt.getAssignments().size());	//test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
		/**Find Task*/
		Task task14 = taskRepo.getTaskByDesc(TASK14);
//		assertEquals(1, task14.getAssignments().size());//test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
		/**Find new Staff to set*/
		Staff john = staffRepo.getStaffLikeFirstName(JOHN_NAME);
//		assertEquals(1, john.getAssignments().size());	//test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
							
		/**Find target Assignment*/
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());		
		Assignment staffProjectAssignment1 = assignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);		
		
		/**Create new Assignment ID*/
		AssignmentId newAssignmentId = new AssignmentId();
		newAssignmentId.setProjectId(parcours.getId());
		newAssignmentId.setTaskId(task14.getId());
		newAssignmentId.setStaffId(john.getId()); // set new Staff id.
		
		Utils.setAssignmentAssociationInJpa(setStaffAssignment ->{
			/**Create new Assignment*/
			Assignment newAssignment = new Assignment();
			newAssignment.setId(newAssignmentId);
			newAssignment.setStaff(john);
			newAssignment.setProject(parcours);
			newAssignment.setTask(task14);		

			/**Remove old and create new Agreement*/
			assignmentRepo.delete(staffProjectAssignment1);
			assignmentRepo.save(newAssignment);
			assignmentRepo.flush();
			entityManager.clear();			
			
		}, assignmentRepo, jdbcTemplateProxy);
		
		/**Find old Assignment*/
		assertFalse(assignmentRepo.findById(id).isPresent());
		/**Find new Assignment*/
		Assignment newParcoursTask14JohnAssignment = assignmentRepo.findById(newAssignmentId).get();		
		/**Validates new Agreement*/		
		assertEquals(ValidationResult.SUCCESS, isAssignmentValid(newParcoursTask14JohnAssignment, PARCOURS, VERSION_1, MICROPOLE, JOHN_NAME, JOHN_LASTNAME, TASK14));	
		
	}

	@Sql(
		scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testSave_UpdateProject() {
		//TODO  Create SetAssignmentAssociationFunction, see example JpaAgreementTest.testSave_UpdateService()
		Assert.fail("TODO");
		
	}
	
	@Sql(
		scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testSave_UpdateTask() {
		//TODO Create SetTaskAssignmentFunction, see example JpaAgreementTest.testSave_UpdateService()
		Assert.fail("TODO");
		
	}	
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		final long clientId = 1L;
		final long projectId = 1L;
		final long staffId = 1L;
		final long taskId = 1L;
		
		/**Insert Client*/
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);
		assertEquals(clientId, barclays.getId().longValue());	
		
		/**Insert project*/
		Project adir = Utils.insertProject(ADIR, VERSION_1, barclays, projectRepo);
		assertEquals(projectId, adir.getId().getProjectId());	
		
		/**Insert staff*/		
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);		
		assertEquals(staffId, amt.getId().longValue());
		
		/**Insert Task*/				
		Task task1 = Utils.insertTask(TASK1, taskRepo);
		assertEquals(taskId, task1.getId().longValue());		
		
		/**Insert Assignment*/
		insertAssignmentInJpa(insertAssignmentFunction->{
			Assignment amtAssignment  = new Assignment(adir, amt, task1);
			assignmentDao.add(amtAssignment);
			assignmentRepo.flush();	 //manually commit the transaction			
			entityManager.clear();   //Detach managed entities from persistence context to reload new changes
		}, assignmentRepo, jdbcTemplateProxy);
		
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
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"})
	public void testDelete() {
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
		}, assignmentRepo, jdbcTemplateProxy);		

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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		assertEquals(63, assignmentRepo.count());
		List <Assignment> assignments =  assignmentRepo.findAll();
		assertEquals(63, assignments.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Assignment> pageableAgreement = assignmentRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableAgreement.getSize());
	}
}
