package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AGEAS;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.AOS;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.BRUSSELS;
import static com.tecxis.resume.domain.Constants.CITY_PARIS_TOTAL_LOCATIONS;
import static com.tecxis.resume.domain.Constants.CLIENT_ARVAL_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_AXELTIS_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_EULER_HERMES_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_HERMES_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_LA_BANQUE_POSTALE_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_MICROPOLE_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_SAGEMCOM_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_SG_ID;
import static com.tecxis.resume.domain.Constants.EOLIS;
import static com.tecxis.resume.domain.Constants.EULER_HERMES;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.FRANCE_ID;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.MANCHESTER;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.PARIS_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_AOS_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_CENTRE_DES_COMPETENCES_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_DESC;
import static com.tecxis.resume.domain.Constants.PROJECT_EOLIS_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_EUROCLEAR_VERS_CALYPSO_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_MORNINGSTAR_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_MORNINGSTAR_V2_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_PARCOURS_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_SELENIUM_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_TED_V1_ID;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Constants.SELENIUM;
import static com.tecxis.resume.domain.Constants.SHERPA;
import static com.tecxis.resume.domain.Constants.STAFF_AMT_ID;
import static com.tecxis.resume.domain.Constants.SWINDON;
import static com.tecxis.resume.domain.Constants.TASK1;
import static com.tecxis.resume.domain.Constants.TASK14;
import static com.tecxis.resume.domain.Constants.TASK22;
import static com.tecxis.resume.domain.Constants.TASK22_ID;
import static com.tecxis.resume.domain.Constants.TASK23;
import static com.tecxis.resume.domain.Constants.TASK23_ID;
import static com.tecxis.resume.domain.Constants.TASK24;
import static com.tecxis.resume.domain.Constants.TASK24_ID;
import static com.tecxis.resume.domain.Constants.TASK25;
import static com.tecxis.resume.domain.Constants.TASK25_ID;
import static com.tecxis.resume.domain.Constants.TASK26;
import static com.tecxis.resume.domain.Constants.TASK26_ID;
import static com.tecxis.resume.domain.Constants.TASK27;
import static com.tecxis.resume.domain.Constants.TASK27_ID;
import static com.tecxis.resume.domain.Constants.TASK28;
import static com.tecxis.resume.domain.Constants.TASK28_ID;
import static com.tecxis.resume.domain.Constants.TASK29;
import static com.tecxis.resume.domain.Constants.TASK29_ID;
import static com.tecxis.resume.domain.Constants.TASK30;
import static com.tecxis.resume.domain.Constants.TASK30_ID;
import static com.tecxis.resume.domain.Constants.TASK31;
import static com.tecxis.resume.domain.Constants.TASK31_ID;
import static com.tecxis.resume.domain.Constants.TASK32;
import static com.tecxis.resume.domain.Constants.TASK33;
import static com.tecxis.resume.domain.Constants.TASK34;
import static com.tecxis.resume.domain.Constants.TASK37;
import static com.tecxis.resume.domain.Constants.TASK57;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Constants.VERSION_2;
import static com.tecxis.resume.domain.Constants.VERSION_3;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.removeParisMorningstarV1AxeltisLocationInJpa;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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

import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class ProjectTest {
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired 
	private LocationRepository locationRepo;
	
	@Autowired 
	private ClientRepository clientRepo;
	
	@Autowired
	private Validator validator;
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Client sagemcom = Utils.insertClient(SAGEMCOM, entityManager);	
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, entityManager);
		assertThat(ted.getId().getProjectId(), Matchers.greaterThan((long)0));		
	}

	@Test
	public void testSetId() {
		Project project = new Project();
		assertEquals(new ProjectId(0, 0), project.getId());	
		project.setId(new ProjectId());
		assertEquals(new ProjectId(0, 0), project.getId());			
	}	
	
	@Test
	public void testGetDesc() {
		Project project = new Project();
		assertNull(project.getDesc());		
	}
	
	@Test
	public void testSetDesc() {
		Project project = new Project();
		assertNull(project.getDesc());
		project.setDesc(PROJECT_DESC);
		assertEquals(PROJECT_DESC,  project.getDesc());		
	}
	
	@Test
	public void testGetName() {
		Project project = new Project();
		assertNull(project.getName());		
	}

	@Test
	public void testSetName() {
		Project project = new Project();
		assertNull(project.getName());		
		project.setDesc(SAGEMCOM);
		assertEquals(SAGEMCOM,  project.getDesc());
	}

	@Test
	public void testGetVersion() {
		Project project = new Project();
		assertNull(project.getVersion());
		
	}

	@Test
	public void testSetVersion() {
		Project project = new Project();
		assertNull(project.getVersion());
		project.setVersion(VERSION_1);
		assertEquals(VERSION_1,  project.getVersion());		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_ManyToOne_GetClient() {
		/**Find project to test*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Retrieve the Client target*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		assertEquals(axeltis, morningstartV1Project.getClient());
		
		/**Test Project's Client*/
		assertEquals(axeltis, morningstartV1Project.getClient());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_ManyToOne_Update_Client_And_CascadeDelete() {
		/**Find project to test*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		ProjectId morningstartV1ProjectId = morningstartV1Project.getId();
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Test Project -> Client association*/		
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		assertEquals(axeltis, morningstartV1Project.getClient());
		
		/**Test Project -> City association*/
		City paris = cityRepo.getCityByName(PARIS);
		assertThat(morningstartV1Project.getCities(), Matchers.hasItem(paris));
		
		/**Find new Client to set*/
		Client eh = clientRepo.getClientByName(EULER_HERMES);
		assertEquals(EULER_HERMES, eh.getName());
				
		/**Build new Project -> Client association*/
		Project newAxeltisProject = new Project();
		ProjectId id = newAxeltisProject.getId();
		id.setProjectId(morningstartV1Project.getId().getProjectId()); //sets old id to the new Project 
		newAxeltisProject.setName(MORNINGSTAR);
		newAxeltisProject.setVersion(VERSION_3);
		newAxeltisProject.setClient(eh);  //sets new Client
		newAxeltisProject.setCities(morningstartV1Project.getCities());
		
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		entityManager.remove(morningstartV1Project);
		entityManager.persist(newAxeltisProject);
		entityManager.merge(eh);
		entityManager.flush();
		entityManager.clear();
		SchemaUtils.testStateAfterUpdateMorningstartV1ProjectClientUpdate(jdbcTemplateProxy);
		
		
		/**Validate project was updated */
		Project morningstartV3Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_3);
		/**Test Project id is same as old*/
		assertEquals(morningstartV1ProjectId.getProjectId(), morningstartV3Project.getId().getProjectId());
		/**Test new client*/
		assertEquals(eh, morningstartV3Project.getClient());
		/**Test new version*/
		assertEquals(VERSION_3, morningstartV3Project.getVersion());
		/**Test an old referenced city*/
		assertThat(morningstartV3Project.getCities(), Matchers.hasItem(paris));
		
		
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_SetAssignments() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adir = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Prepare Task*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
		Task task1 = Utils.insertTask(TASK1, entityManager);
		assertEquals(1L, task1.getId().longValue());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));	
		AssignmentId id = new AssignmentId(adir.getId(), amt.getId(), task1.getId());		
		assertNull(entityManager.find(Assignment.class, id));
		
		/**Prepare staff assignments*/			
		Assignment amtAssignment = Utils.insertAssignment(adir, amt, task1, entityManager);		
		List <Assignment> amtAssignments = List.of(amtAssignment);		
		adir.setAssignments(amtAssignments);
		task1.setAssignments(amtAssignments);
		amt.setAssignments(amtAssignments);				
		entityManager.merge(adir);
		entityManager.merge(amt);
		entityManager.merge(task1);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(Assignment.class, id));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_AddAssignmentFromScratch() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adir = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Prepare Task*/	
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
		Task assignment1 = Utils.insertTask(TASK1, entityManager);
		assertEquals(1L, assignment1.getId().longValue());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getAssignments().size());		
		assertEquals(0, adir.getAssignments().size());
		assertEquals(0, assignment1.getAssignments().size());
		
		/**Create new Assignment*/
		Assignment newAssignment = new Assignment(adir, amt, assignment1);
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));		
		adir.addAssignment(newAssignment);
		amt.addAssignment(newAssignment);
		assignment1.addAssignment(newAssignment);
		
		entityManager.persist(newAssignment);
		entityManager.merge(adir);
		entityManager.merge(amt);
		entityManager.merge(assignment1);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(1, amt.getAssignments().size());		
		assertEquals(1, adir.getAssignments().size());
		assertEquals(1, assignment1.getAssignments().size());
	}
	
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	public void test_OneToMany_AddAssignment() {		
		/**Fetch Project and validate Project -> Assignments*/
		Project  adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(6, adir.getAssignments().size());
		
		/**Fetch Task and validate Task -> Assignments*/			
		Task task57 = taskRepo.getTaskByDesc(TASK57);
		assertEquals(2, task57.getAssignments().size());
				
		/**Fetch the Staff and validate Staff -> Assignments*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(1, john.getAssignments().size());
		
		/**Create new Assignment */
		Assignment newAssignment = new Assignment(adir, john, task57);
		
		/**Add the new Assignment*/
		john.addAssignment(newAssignment);	
		adir.addAssignment(newAssignment);
		task57.addAssignment(newAssignment);
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
				
		entityManager.persist(newAssignment);
		entityManager.merge(adir);
		entityManager.merge(john);
		entityManager.merge(task57);
		entityManager.flush();
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(64, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Validate Project -> Assignments*/
		adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(7, adir.getAssignments().size());
		
		/**Validate Task -> Assignments*/			
		task57 = taskRepo.getTaskByDesc(TASK57);
		assertEquals(3, task57.getAssignments().size());
				
		/**Validate Staff -> Assignments*/
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(2, john.getAssignments().size());
	}

	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_AddExistingAssignment() {
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
		eolis.addAssignment(newAssignment); /***  <==== Throws EntityExistsException */
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveAssignment() {
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task task14 = taskRepo.getTaskByDesc(TASK14);		
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());	
		assertEquals(62, amt.getAssignments().size());		
		assertEquals(6, parcours.getAssignments().size());
		assertEquals(1, task14.getAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		Assignment assignment1 = assignmentRepo.findById(id).get();
		assertNotNull(assignment1);
		
		/**Remove staff Task*/
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
		parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		task14 = taskRepo.getTaskByDesc(TASK14);	
		assertEquals(61, amt.getAssignments().size());		
		assertEquals(5, parcours.getAssignments().size());
		assertEquals(0, task14.getAssignments().size());

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_GetAssignments() {
		/**Prepare project*/
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);		
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		List <Assignment> morningstarv1Assignments = morningstarv1.getAssignments();
		assertEquals(10, morningstarv1Assignments.size());
		
		
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertNotNull(amt);
		List <Assignment> amtAssignments = amt.getAssignments();
		assertEquals(62, amtAssignments.size());
		
		/**Prepare assignments*/
		Task task22 = taskRepo.getTaskByDesc(TASK22);
		Task task23 = taskRepo.getTaskByDesc(TASK23);	
		Task task24 = taskRepo.getTaskByDesc(TASK24);	
		Task task25 = taskRepo.getTaskByDesc(TASK25);
		Task task26 = taskRepo.getTaskByDesc(TASK26);
		Task task27 = taskRepo.getTaskByDesc(TASK27);
		Task task28 = taskRepo.getTaskByDesc(TASK28);		
		Task task29 = taskRepo.getTaskByDesc(TASK29);			
		Task task30 = taskRepo.getTaskByDesc(TASK30);		
		Task task31 = taskRepo.getTaskByDesc(TASK31);	
		assertEquals(TASK22, task22.getDesc());
		assertEquals(TASK23, task23.getDesc());
		assertEquals(TASK24, task24.getDesc());
		assertEquals(TASK25, task25.getDesc());
		assertEquals(TASK26, task26.getDesc());
		assertEquals(TASK27, task27.getDesc());
		assertEquals(TASK28, task28.getDesc());
		assertEquals(TASK29, task29.getDesc());
		assertEquals(TASK30, task30.getDesc());
		assertEquals(TASK31, task31.getDesc());
		assertEquals(2, task22.getAssignments().size());
		assertEquals(3, task23.getAssignments().size());
		assertEquals(2, task24.getAssignments().size());
		assertEquals(1, task25.getAssignments().size());
		assertEquals(1, task26.getAssignments().size());
		assertEquals(2, task27.getAssignments().size());
		assertEquals(1, task28.getAssignments().size());
		assertEquals(1, task29.getAssignments().size());
		assertEquals(1, task30.getAssignments().size());
		assertEquals(3, task31.getAssignments().size());
		
		/**Prepare staff assignments*/
		Assignment msv1Assignment1 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task22.getId())).get();
		Assignment msv1Assignment2 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task23.getId())).get();
		Assignment msv1Assignment3 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task24.getId())).get();
		Assignment msv1Assignment4 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task25.getId())).get();
		Assignment msv1Assignment5 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task26.getId())).get();
		Assignment msv1Assignment6 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task27.getId())).get();
		Assignment msv1Assignment7 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task28.getId())).get();
		Assignment msv1Assignment8 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task29.getId())).get();
		Assignment msv1Assignment9 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task30.getId())).get();
		Assignment msv1Assignment10 = assignmentRepo.findById(new AssignmentId(morningstarv1.getId(), amt.getId(), task31.getId())).get();
			
		/**Validate project's staff assignments*/
		assertThat(morningstarv1Assignments, Matchers.containsInAnyOrder(msv1Assignment1, msv1Assignment2, msv1Assignment3, msv1Assignment4, msv1Assignment5, msv1Assignment6, msv1Assignment7, msv1Assignment8, msv1Assignment9, msv1Assignment10));
		
		/**TEST 2*/
		
		/**Prepare project*/		
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2); 		
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		List <Assignment> morningstarv2Assignments = morningstarv2.getAssignments();
		assertNotNull(morningstarv2Assignments);
		assertEquals(6, morningstarv2Assignments.size());
		
		/**Prepare assignments*/		
		Task task37 = taskRepo.getTaskByDesc(TASK37);
		assertEquals(TASK37, task37.getDesc());
		assertEquals(1, task37.getAssignments().size());
		
		/**Prepare staff assignments*/
		Assignment mv2Assignment1 = assignmentRepo.findById(new AssignmentId(morningstarv2.getId(), amt.getId(), task22.getId())).get();
		Assignment mv2Assignment2 = assignmentRepo.findById(new AssignmentId(morningstarv2.getId(), amt.getId(), task23.getId())).get();
		Assignment mv2Assignment3 = assignmentRepo.findById(new AssignmentId(morningstarv2.getId(), amt.getId(), task24.getId())).get();
		Assignment mv2Assignment4 = assignmentRepo.findById(new AssignmentId(morningstarv2.getId(), amt.getId(), task27.getId())).get();
		Assignment mv2Assignment5 = assignmentRepo.findById(new AssignmentId(morningstarv2.getId(), amt.getId(), task31.getId())).get();
		Assignment mv2Assignment6 = assignmentRepo.findById(new AssignmentId(morningstarv2.getId(), amt.getId(), task37.getId())).get();
			
		/**Validate project's assignments*/
		assertThat(morningstarv2Assignments, Matchers.containsInAnyOrder(mv2Assignment1, mv2Assignment2, mv2Assignment3, mv2Assignment4, mv2Assignment5, mv2Assignment6 ));


	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_ManyToMany_GetCities() {
		City swindon = cityRepo.getCityByName(SWINDON);
		assertEquals(SWINDON, swindon.getName());
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		assertEquals(AOS, aos.getName());
		assertEquals(VERSION_1, aos.getVersion());
		
		List <City> aosCities = aos.getCities();
		assertNotNull(aosCities);
		assertEquals(2, aosCities.size());
		assertThat(aosCities, Matchers.containsInAnyOrder(swindon, paris));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_SetCities() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client belfius = Utils.insertClient(BELFIUS, entityManager);
		Project sherpaProject = Utils.insertProject(SHERPA, VERSION_1, belfius, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
				
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Country belgium = Utils.insertCountry(BELGIUM, entityManager);
		City brussels = Utils.insertCity(BRUSSELS, belgium, entityManager);
		Country france = Utils.insertCountry(FRANCE, entityManager);
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		
		List <City> sherpaProjectCities = List.of(brussels, paris);
		sherpaProject.setCities(sherpaProjectCities);
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));		
		entityManager.merge(sherpaProject);
		entityManager.flush();
		
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));	
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_AddCity() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Country uk = Utils.insertCountry("United Kingdom", entityManager);
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));		
		City london = Utils.insertCity(LONDON, uk, entityManager);
		City swindon = Utils.insertCity(SWINDON, uk, entityManager);
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		Client ageas = Utils.insertClient(AGEAS, entityManager);		
		Project fortisProject = Utils.insertProject(FORTIS, VERSION_1, ageas, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));	
		assertTrue(adirProject.addCity(london));		
		entityManager.merge(london);
		entityManager.flush();
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		
		assertTrue(adirProject.addCity(paris));
		entityManager.merge(swindon);
		entityManager.flush();
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
				
		assertTrue(fortisProject.addCity(swindon));
		entityManager.merge(swindon);
		entityManager.flush();
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_RemoveCity() {
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));		
		Client belfius = Utils.insertClient(BELFIUS, entityManager);
		Project sherpaProject = Utils.insertProject(SHERPA, VERSION_1, belfius, entityManager);			
		Country belgium = Utils.insertCountry(BELGIUM, entityManager);
		City brussels = Utils.insertCity(BRUSSELS, belgium, entityManager);
		Country france = Utils.insertCountry(FRANCE, entityManager);
		City paris = Utils.insertCity(PARIS, france, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));	
		List <City> sherpaProjectCities = List.of(brussels, paris);		
		sherpaProject.setCities(sherpaProjectCities);
		entityManager.merge(sherpaProject);
		entityManager.flush();		
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));	
		
		

		assertTrue(sherpaProject.removeCity(brussels));
		assertEquals(0, brussels.getProjects().size());
		assertEquals(1, sherpaProject.getCities().size());
		entityManager.merge(sherpaProject);							
		entityManager.flush();
		
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));	
		

		assertTrue(sherpaProject.removeCity(paris));
		assertEquals(0, sherpaProject.getCities().size());
		assertEquals(0, paris.getProjects().size());
		entityManager.merge(sherpaProject);
		entityManager.flush();
		
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));	

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_AddLocation() {		
		/**Find City*/
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		
		/**Find Project & validate to test*/		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getLocations().size());		
		assertEquals(paris,  selenium.getLocations().get(0).getCity());	
		
		/**Find City to add*/
		City manchester = cityRepo.getCityByName(MANCHESTER);
		
		/**Test initial state*/
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		/**Create new Locations*/
		Location newLocation = new Location(manchester, selenium);
		
		/**Add Location to Project*/
		selenium.addLocation(newLocation);
		manchester.addLocation(newLocation);
		entityManager.persist(newLocation);
		entityManager.merge(selenium);
		entityManager.merge(manchester);
		entityManager.flush();
		
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(15, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
						
		selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(2, selenium.getLocations().size());		
		assertThat(selenium.getLocations().get(0).getCity(),  Matchers.oneOf(paris, manchester));
		assertThat(selenium.getLocations().get(1).getCity(),  Matchers.oneOf(paris, manchester));			
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_AddExistingLocation() {
		/**Find City*/
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		
		/**Find Project & validate to test*/		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getLocations().size());		
		assertEquals(paris,  selenium.getLocations().get(0).getCity());	
		
		/**Test initial state*/
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		/**Create new Location*/
		Location newLocation = new Location (paris, selenium);
		
		/**Add existing Location to Project*/ /***  <==== Throws EntityExistsException */		
		selenium.addLocation(newLocation);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveLocation() {
		/**Find City location*/
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());		
		
		/**Find Project location*/		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getLocations().size());		
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Validate initial state*/		
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		/**Remove location*/
		/**Location has to be removed as it is the relation owner between Project <-> Location*/
		Location seleniumLocation = locationRepo.findById(new LocationId(paris.getId(), selenium.getId())).get();
		entityManager.remove(seleniumLocation);
		entityManager.flush();
		
		/**Validate Location was removed*/
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);	
		assertEquals(0, selenium.getLocations().size());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveLocation_by_City() {
		//See example in City.test_OneToMany_RemoveLocation_by_Project
		/**Find and validate Project to test*/
		Project morningstarV1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(morningstarV1);
		/**Fetch target Project -> locations*/
		ProjectId morningstarv1ProjectId = new ProjectId(PROJECT_MORNINGSTAR_V1_ID, CLIENT_AXELTIS_ID);		
		/**Fetch target Project -> assignments*/
		Assignment assignment22 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK22_ID)).get();
		Assignment assignment23 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK23_ID)).get();
		Assignment assignment24 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK24_ID)).get();
		Assignment assignment25 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK25_ID)).get();
		Assignment assignment26 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK26_ID)).get();
		Assignment assignment27 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK27_ID)).get();
		Assignment assignment28 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK28_ID)).get();
		Assignment assignment29 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK29_ID)).get();
		Assignment assignment30 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK30_ID)).get();
		Assignment assignment31 =  assignmentRepo.findById(new AssignmentId(morningstarv1ProjectId, STAFF_AMT_ID, TASK31_ID)).get();
		List <Assignment> morningstarv1AxeltisAssignments = List.of (assignment22,
				assignment23,
				assignment24,
				assignment25,
				assignment26,
				assignment27,
				assignment28,
				assignment29,
				assignment30,
				assignment31);
		/**Fetch & validate city to remove from project*/
		City paris = cityRepo.getCityByName(PARIS);
		/**Fetch 'paris' locations*/
		Location parisSagemcomTedV1Location = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID))).get();
		Location parisParcoursV1MicropoleLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_PARCOURS_V1_ID, CLIENT_MICROPOLE_ID))).get();
		Location parisEuroclearV1LbpLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_EUROCLEAR_VERS_CALYPSO_V1_ID, CLIENT_LA_BANQUE_POSTALE_ID))).get();	
		Location parisMorningstarV1AxeltisLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_MORNINGSTAR_V1_ID, CLIENT_AXELTIS_ID))).get();
		Location parisEolisV1EhLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_EOLIS_V1_ID, CLIENT_EULER_HERMES_ID))).get();
		Location parisMorningstarV2AxeltisLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_MORNINGSTAR_V2_ID, CLIENT_AXELTIS_ID))).get();
		Location parisCdcV1SgLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_CENTRE_DES_COMPETENCES_V1_ID, CLIENT_SG_ID))).get();
		Location parisAosv1ArvalLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_AOS_V1_ID, CLIENT_ARVAL_ID))).get();
		Location parisSeleniumV1HermesLocation = locationRepo.findById(new LocationId(new CityId(PARIS_ID,FRANCE_ID), new ProjectId(PROJECT_SELENIUM_V1_ID, CLIENT_HERMES_ID))).get();			
		List <Location> morningstarv1AxeltisLocations = List.of(parisSagemcomTedV1Location, 
				parisParcoursV1MicropoleLocation, 	
				parisEuroclearV1LbpLocation,		
				parisMorningstarV1AxeltisLocation,
				parisEolisV1EhLocation,	
				parisMorningstarV2AxeltisLocation,
				parisCdcV1SgLocation,
				parisAosv1ArvalLocation,
				parisSeleniumV1HermesLocation );
		assertEquals(CITY_PARIS_TOTAL_LOCATIONS, morningstarv1AxeltisLocations.size());
		/**Validate City before test*/
		assertEquals(SUCCESS, Utils.isCityValid(paris, PARIS, FRANCE, morningstarv1AxeltisLocations));
		/**Fetch target Project -> client*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		/**Validate target project before test*/
		assertEquals(SUCCESS, Utils.isProjectValid(morningstarV1,  MORNINGSTAR, VERSION_1, morningstarv1AxeltisLocations, axeltis, morningstarv1AxeltisAssignments));
		
		/**Remove Project location by city*/	
		removeParisMorningstarV1AxeltisLocationInJpa(deleteLocationFuntion -> {			
			assertTrue(paris.removeLocation(morningstarV1));
			assertTrue(morningstarV1.removeLocation(paris));		
			SchemaUtils.testInitialState(jdbcTemplateProxy);
			entityManager.merge(morningstarV1);
			entityManager.merge(paris);
			entityManager.flush();
			entityManager.clear();
			
		}, entityManager, jdbcTemplateProxy);
		
		/**Validate City after test*/
		morningstarv1AxeltisLocations = List.of(parisSagemcomTedV1Location, 
				parisParcoursV1MicropoleLocation, 	
				parisEuroclearV1LbpLocation,
				parisEolisV1EhLocation,	
				parisMorningstarV2AxeltisLocation,
				parisCdcV1SgLocation,
				parisAosv1ArvalLocation,
				parisSeleniumV1HermesLocation );
		assertEquals(CITY_PARIS_TOTAL_LOCATIONS - 1, morningstarv1AxeltisLocations.size()); //1 location removed
		City newParis = cityRepo.getCityByName(PARIS);
		assertEquals(SUCCESS, Utils.isCityValid(newParis, PARIS, FRANCE, morningstarv1AxeltisLocations));
		/**Validate target  project after test*/
		Project newMorningstarV1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(newMorningstarV1);
		assertEquals(SUCCESS, Utils.isProjectValid(newMorningstarV1,  MORNINGSTAR, VERSION_1, morningstarv1AxeltisLocations, axeltis, morningstarv1AxeltisAssignments));
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_SetLocations() {
		
		/**Find & validate Project to test*/
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertEquals(1, selenium.getLocations().size());
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getCities().size());
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		assertEquals(paris, selenium.getCities().get(0));
		/**Validate Locations*/
		List <Location> seleniumLocations  = selenium.getLocations();
		assertEquals(1, seleniumLocations.size());
		/**Validate the opposite association*/
		List <City> seleniumCities =  selenium.getCities();
		assertEquals(1, seleniumCities.size());
		assertEquals(seleniumCities.get(0), paris);
					
		
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(MANCHESTER, manchester.getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(manchester, manchesterLocations.get(0).getCity());

		/***Validate the Project's current Locations*/
		assertEquals(1, selenium.getLocations().size());		
		Location seleniumLocation = locationRepo.findById(new LocationId(paris.getId(), selenium.getId())).get();		
		assertEquals(seleniumLocation, selenium.getLocations().get(0));
				
		/**Prepare new Locations*/		
		Location manchesterSeleniumLoc = new Location(manchester, selenium);
		List <Location> newLocations = List.of(manchesterSeleniumLoc);		
				
		/**Set new Locations*/
		selenium.setLocations(newLocations);
		
		/**Set new cities*/
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));		
		selenium.setLocations(newLocations);
		entityManager.merge(selenium);
		entityManager.flush();
		entityManager.clear();
		assertEquals(15, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		
		/**Test & validate the new Locations*/
		selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);	
		assertEquals(2, selenium.getLocations().size());
		assertThat(selenium.getLocations().get(0).getCity(), Matchers.oneOf(paris, manchester));
		assertThat(selenium.getLocations().get(1).getCity(), Matchers.oneOf(paris, manchester));
		/**Cities are linked through Location table*/
		assertEquals(2, selenium.getCities().size());
		assertThat(selenium.getCities().get(0), Matchers.oneOf(paris, manchester));
		assertThat(selenium.getCities().get(1), Matchers.oneOf(paris, manchester));
		
		/**Validate the opposite association*/
		manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(2, manchester.getProjects().size());
		//Reopen persistence context otherwise exception is thrown-> LazyInitializationException : failed to lazily initialize 
		//a collection of role:, could not initialise proxy - no Session
		// Read: https://vladmihalcea.com/the-hibernate-enable_lazy_load_no_trans-anti-pattern/
		Project adir =  projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertThat(manchester.getProjects().get(0), Matchers.oneOf(selenium, adir));
		assertThat(manchester.getProjects().get(1), Matchers.oneOf(selenium, adir));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_GetLocations() {
		/**Find & validate Project to test*/
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertEquals(1, selenium.getLocations().size());
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getCities().size());
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		assertEquals(paris, selenium.getCities().get(0));		
		/**Validate the opposite association*/
		List <City> seleniumCities =  selenium.getCities();
		assertEquals(1, seleniumCities.size());
		assertEquals(seleniumCities.get(0), paris);
		
		/**Validate Locations*/
		List <Location> seleniumLocations  = selenium.getLocations();
		assertEquals(1, seleniumLocations.size());
		Location selemiumLocation =  seleniumLocations.get(0);
		assertEquals(paris, selemiumLocation.getCity());
		assertEquals(selenium, selemiumLocation.getProject());
				
	}

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaff() {		
		/**Find & validate Project to test*/
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		List <Staff> seleniumStaff = selenium.getStaff();
		//TODO this assert should be tested to 1. Create a query in ProjectRepo that joins the three tables using a distinct. For instance:
//		select distinct  Staff.firstName,  Staff.lastName 
//		from
//		    PROJECT
//		inner join 
//			ASSIGNMENT 
//		        on PROJECT.PROJECT_ID = ASSIGNMENT.PROJECT_ID AND PROJECT.CLIENT_ID = ASSIGNMENT.CLIENT_ID
//		inner join
//			Staff  
//				on ASSIGNMENT.STAFF_ID=Staff.STAFF_ID 
//		where
//			ASSIGNMENT.PROJECT_ID=13 
//			and ASSIGNMENT.CLIENT_ID=12		
		assertEquals(3, seleniumStaff.size());		 
		
		/**Find target Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertThat(seleniumStaff, Matchers.hasItem(amt));
		 
	}
	
	@Test(expected=NoSuchElementException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveProject() {
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Test Project -> Client association*/		
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		assertEquals(axeltis.getId(), morningstartV1Project.getClient().getId());
		
		/**Test Client -> Project association*/
		List <Project> axeltisProjects = axeltis.getProjects();
		assertEquals(2, axeltisProjects.size());
		assertThat(axeltisProjects, Matchers.hasItem(morningstartV1Project));
		
		/**Test Project -> Location association*/
		List <Location> axeltisV1ProjectLocations = morningstartV1Project.getLocations();
		assertEquals(1, axeltisV1ProjectLocations.size());
		City paris = cityRepo.getCityByName(PARIS); 
		assertEquals(PARIS, paris.getName());
		assertEquals(paris, axeltisV1ProjectLocations.get(0).getCity());
		
		/**Test Location -> Project association*/
		Location axeltisMorningstarv1ProjectLocation =  locationRepo.findById(new LocationId(paris.getId(), morningstartV1Project.getId())).get();
		assertEquals(paris, axeltisMorningstarv1ProjectLocation.getCity());
		assertEquals(morningstartV1Project, axeltisMorningstarv1ProjectLocation.getProject());
		
		/**Test Project -> Assignment association*/
		List <Assignment> morningstartV1Assignments  = morningstartV1Project.getAssignments();
		assertEquals(10, morningstartV1Assignments.size());
					
		/**Detach entities*/		
		entityManager.clear();
		
		/**Find Project to remove again*/
		morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());
		
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Remove Project*/
		entityManager.remove(morningstartV1Project);
		entityManager.flush();
		entityManager.clear();
		SchemaUtils.testStateAfterMorningstartV1ProjectDelete(jdbcTemplateProxy);
		
		/**Test Project was removed*/
		assertEquals(12, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		/**Test orphans */
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(53, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		assertNull(projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1));		
		assertNull(locationRepo.findById(new LocationId(paris.getId(), morningstartV1Project.getId())).get());
	}
	
	@Test
	public void testNameAndVersionAreNotNull() {
		Project project = new Project();
		Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertFalse(violations.isEmpty());
	}

	@Test
	public void testToString() {
		Project project = new Project ();
		assertThat(project.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}

}
