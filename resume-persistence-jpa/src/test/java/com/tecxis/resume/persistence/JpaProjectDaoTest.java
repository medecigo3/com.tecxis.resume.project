package com.tecxis.resume.persistence;


import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.Constants.MANCHESTER;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.AfterClass;
import org.junit.Before;
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
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.SchemaUtils;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Task;
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
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaProjectDaoTest {
	
    /** For Log4j2 dependencies >= 2.10 set this system property to configure ANSI Styling for Windows 
     * Unix-based operating systems such as Linux and Mac OS X support ANSI color codes by default. 
     * See https://logging.apache.org/log4j/2.x/manual/layouts.html#enable-jansi*/
	private static final String LOG4J_SKIP_JANSI = "log4j.skipJansi";
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private TaskRepository taskRepo;

    @AfterClass
    public static void afterClass() {
        System.clearProperty(LOG4J_SKIP_JANSI);
    }
    
    @Before
    public void before() {
    	System.clearProperty(LOG4J_SKIP_JANSI);
        System.setProperty(LOG4J_SKIP_JANSI, "false");
        
    }

	
	@Sql(
			scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
		@Test
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, null, entityManager);
		assertEquals(1, adirProject.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
			
		Client belfius = Utils.insertClient(BELFIUS, entityManager);
		Project sherpaProject = Utils.insertProject(SHERPA, VERSION_1, belfius, null, entityManager);
		assertEquals(2, sherpaProject.getId().getProjectId());
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
				
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);
		Project morningStarV1Project = Utils.insertProject(MORNINGSTAR, VERSION_1, axeltis, null, entityManager);
		assertEquals(3, morningStarV1Project.getId().getProjectId());
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Test insert version 2 of project MORNINGSTAR*/
		Project monringstarV2Project = Utils.insertProject(MORNINGSTAR, VERSION_2, axeltis, null, entityManager);
		assertEquals(4, monringstarV2Project.getId().getProjectId());
		assertEquals(4, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));

	}
	
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testAdd() {
		Client euler = Utils.insertClient(EULER_HERMES, entityManager);
		Project eolisIn = Utils.insertProject(EOLIS, VERSION_1, euler, null, entityManager);
		Project eolisOut = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertEquals(eolisIn, eolisOut);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(SAGEMCOM, entityManager);
		Project tempProject = Utils.insertProject(TED, VERSION_1, barclays, null, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		projectRepo.delete(tempProject);
		assertNull(projectRepo.findByNameAndVersion(TED, VERSION_1));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Project> projects = projectRepo.findAll();
		assertEquals(13, projects.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Project> pageableProject = projectRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableProject.getSize());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByName() {
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertNotNull(axeltis);
		assertEquals(AXELTIS, axeltis.getName());
		
		List <Project> morningstarList = projectRepo.findByName(MORNINGSTAR);
		assertNotNull(morningstarList);
		assertEquals(2, morningstarList.size());
		assertEquals(MORNINGSTAR, morningstarList.get(0).getName());
		assertEquals(MORNINGSTAR, morningstarList.get(1).getName());
	

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByNameAndVersion() {		
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		assertNotNull(barclays);
		assertEquals(BARCLAYS, barclays.getName());
		Project adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertNotNull(adir);
		assertEquals(ADIR, adir.getName());
		
		Client belfius = clientRepo.getClientByName(BELFIUS);
		assertNotNull(belfius);
		assertEquals(BELFIUS, belfius.getName());
		Project  sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		assertNotNull(sherpa);
		assertEquals(SHERPA, sherpa.getName());
		
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertNotNull(morningstarv1);
		assertNotNull(morningstarv2);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		assertNotEquals(morningstarv1.getId(), morningstarv2.getId());
	}
	
	@Test
	public void test_ManyToOne_Update_Client() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Assignments_And_RemoveOrhpansWithOrm() {		
		/**Find project to test*/
		Project adirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);		
		assertEquals(ADIR, adirV1.getName());
		assertEquals(VERSION_1, adirV1.getVersion());
				
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);		
			
		/**Find task*/
		Task task1 = taskRepo.getTaskByDesc(TASK1);
		Task task2 = taskRepo.getTaskByDesc(TASK2);
		Task task3 = taskRepo.getTaskByDesc(TASK3);
		Task task4 = taskRepo.getTaskByDesc(TASK4);
		Task task5 = taskRepo.getTaskByDesc(TASK5);
		Task task6 = taskRepo.getTaskByDesc(TASK6);
		
		/**Find Project Client*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		
		
		/**Find Project locations*/
		LocationId adirV1LocationId = new LocationId(new CityId(MANCHESTER_ID, UNITED_KINGDOM_ID), new ProjectId(PROJECT_ADIR_V1_ID, CLIENT_BARCLAYS_ID));
		Location adirV1Location = locationRepo.findById(adirV1LocationId).get();
		List <Location> adirV1Locations = List.of(adirV1Location);
		
		/**Find Project Assignments*/
		AssignmentId assignmentId1 = new AssignmentId(adirV1.getId(), amt.getId(), task1.getId());
		Assignment assignment1 = assignmentRepo.findById(assignmentId1).get();		
		assertNotNull(assignment1);
		AssignmentId assignmentId2 = new AssignmentId(adirV1.getId(), amt.getId(), task2.getId());
		Assignment assignment2 = assignmentRepo.findById(assignmentId2).get();
		assertNotNull(assignment2);
		
		AssignmentId assignmentId3 = new AssignmentId(adirV1.getId(), amt.getId(), task3.getId());
		Assignment assignment3 = assignmentRepo.findById(assignmentId3).get();
		assertNotNull(assignment3);
		
		AssignmentId assignmentId4 = new AssignmentId(adirV1.getId(), amt.getId(), task4.getId());
		Assignment assignment4 = assignmentRepo.findById(assignmentId4).get();
		assertNotNull(assignment4);
		
		AssignmentId assignmentId5 = new AssignmentId(adirV1.getId(), amt.getId(), task5.getId());
		Assignment assignment5 = assignmentRepo.findById(assignmentId5).get();
		assertNotNull(assignment5);
		
		AssignmentId assignmentId6 = new AssignmentId(adirV1.getId(), amt.getId(), task6.getId());
		Assignment assignment6 = assignmentRepo.findById(assignmentId6).get();
		assertNotNull(assignment6);	
		
		/**Validate Project assignments*/
		List <Assignment> amtAssignments = List.of(assignment1,assignment2,assignment3,assignment4,assignment5,assignment6);
		assertEquals(SUCCESS, isProjectValid(adirV1, ADIR, VERSION_1, adirV1Locations, barclays, amtAssignments));	
		
		/**Project-> assignments assocs. does not cascade on REMOVE*/
		update_ProjectAdirV1_With_Assignments_InJpa(
			(locationRepo, em) -> {
				/**Deletes ADIR v1 locations*/
				em.clear();
				Location currentAdirV1Location = locationRepo.findById(adirV1LocationId).get();
				locationRepo.delete(currentAdirV1Location);
			}, 
			(assignmentRepo, em)-> {
				/**Deletes ADIR v1 assignments**/
				em.clear();				
				Project oldAdirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
				oldAdirV1.getAssignments().forEach(assignment -> {
					assignmentRepo.delete(assignment);}
				);
				
			},
			(projectRepo,em )-> {
				/**Delete ADIR v1 project*/
				em.clear();				
				Project oldAdirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
				projectRepo.delete(oldAdirV1);
			},
			(projectRepo, assignmentRepo)-> {
				/**Creates new ADIR Project*/
				Project newAdirV1 = new Project();				
				newAdirV1.setId( new ProjectId(PROJECT_ADIR_V1_ID, CLIENT_BARCLAYS_ID));
				newAdirV1.setName(ADIR);
				newAdirV1.setVersion(VERSION_1);
				/**Build new Assignments*/
				Staff recentAmt = staffRepo.getStaffLikeFirstName(AMT_NAME);
				Task recentTask1 = taskRepo.getTaskByDesc(TASK1);
				Assignment newAssignment = new Assignment(newAdirV1, recentAmt, recentTask1);
				List <Assignment> newAmtAssignments = List.of(newAssignment);	
				/**Set new Assignments*/
				newAdirV1.setAssignments(newAmtAssignments);			
				assignment1.setProject(newAdirV1);				
				projectRepo.save(newAdirV1);				
				assignmentRepo.save(assignment1);
				projectRepo.flush();
				
		}, projectRepo, locationRepo, assignmentRepo, entityManager, jdbcTemplateProxy);
		
		entityManager.clear();
		/**Validate Project has new assignments changed*/
		Project newAdirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);		
		Staff recentAmt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task recentTask1 = taskRepo.getTaskByDesc(TASK1);
		Assignment newAssignment = new Assignment(newAdirV1, recentAmt, recentTask1);
		List <Assignment> newAmtAssignments = List.of(newAssignment);		
		assertEquals(SUCCESS, isProjectValid(newAdirV1, ADIR, VERSION_1, List.of(), barclays, newAmtAssignments));

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Assignments_And_RemoveOrhpansWithOrm_NullSet(){
		/**Find project to test*/
		Project adirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);		
		assertEquals(ADIR, adirV1.getName());
		assertEquals(VERSION_1, adirV1.getVersion());
				
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);		
			
		/**Find task*/
		Task task1 = taskRepo.getTaskByDesc(TASK1);
		Task task2 = taskRepo.getTaskByDesc(TASK2);
		Task task3 = taskRepo.getTaskByDesc(TASK3);
		Task task4 = taskRepo.getTaskByDesc(TASK4);
		Task task5 = taskRepo.getTaskByDesc(TASK5);
		Task task6 = taskRepo.getTaskByDesc(TASK6);
		
		/**Find Project Client*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		
		/**Find Project locations*/
		LocationId adirV1LocationId = new LocationId(new CityId(MANCHESTER_ID, UNITED_KINGDOM_ID), new ProjectId(PROJECT_ADIR_V1_ID, CLIENT_BARCLAYS_ID));
		Location adirV1Location = locationRepo.findById(adirV1LocationId).get();
		List <Location> adirV1Locations = List.of(adirV1Location);
		
		/**Find Project Assignments*/
		AssignmentId assignmentId1 = new AssignmentId(adirV1.getId(), amt.getId(), task1.getId());
		Assignment assignment1 = assignmentRepo.findById(assignmentId1).get();		
		assertNotNull(assignment1);
		AssignmentId assignmentId2 = new AssignmentId(adirV1.getId(), amt.getId(), task2.getId());
		Assignment assignment2 = assignmentRepo.findById(assignmentId2).get();
		assertNotNull(assignment2);
		
		AssignmentId assignmentId3 = new AssignmentId(adirV1.getId(), amt.getId(), task3.getId());
		Assignment assignment3 = assignmentRepo.findById(assignmentId3).get();
		assertNotNull(assignment3);
		
		AssignmentId assignmentId4 = new AssignmentId(adirV1.getId(), amt.getId(), task4.getId());
		Assignment assignment4 = assignmentRepo.findById(assignmentId4).get();
		assertNotNull(assignment4);
		
		AssignmentId assignmentId5 = new AssignmentId(adirV1.getId(), amt.getId(), task5.getId());
		Assignment assignment5 = assignmentRepo.findById(assignmentId5).get();
		assertNotNull(assignment5);
		
		AssignmentId assignmentId6 = new AssignmentId(adirV1.getId(), amt.getId(), task6.getId());
		Assignment assignment6 = assignmentRepo.findById(assignmentId6).get();
		assertNotNull(assignment6);
		List <Assignment> adirV1Assignments = List.of(assignment1, assignment2, assignment3, assignment4, assignment5, assignment6);
		
		/**Validate Project assignments*/		
		isProjectValid(adirV1, ADIR, VERSION_1, adirV1Locations, barclays, adirV1Assignments);
		
		/**Project-> assignments assoc. set to: orphanRemoval=false*/		
		update_ProjectAdirV1_With_NullAssignments_InJpa(
			em -> {
				em.clear();
			},
			(projectRepo, assignmentRepo) -> {
						
				Project currentAdirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);						
				currentAdirV1.setAssignments(null);			
				/**Set orphans ADIR v1 assignment associations**/						
				currentAdirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
				currentAdirV1.getAssignments().forEach(assignment -> {
						assignment.setProject(null);
					}
				);		
				projectRepo.save(currentAdirV1);
				currentAdirV1.getAssignments().forEach(assignment -> {
					assignmentRepo.save(assignment);
					}
				);
								
			},
			em -> {
				entityManager.flush();
				entityManager.clear();
			},
			projectRepo, assignmentRepo, entityManager, jdbcTemplateProxy);
		
		/**Validate Project hasn't changed*/
		Project newAdirV1 = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		isProjectValid(newAdirV1, ADIR, VERSION_1, adirV1Locations, barclays, adirV1Assignments);
	}
	
	@Test
	public void test_OneToMany_Update_Locations_And_RemoveOrphansWithOrm() {
		org.junit.Assert.fail("TODO");
	}

	public void test_OneToMany_Update_Locations_And_RemoveOrphansWithOrm_NullSet() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Delete_Location_by_City() {
		/**Find and validate Project to test*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(morningstartV1Project);
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
		assertEquals(SUCCESS, Utils.isProjectValid(morningstartV1Project,  MORNINGSTAR, VERSION_1, morningstarv1AxeltisLocations, axeltis, morningstarv1AxeltisAssignments));
		
		/**Remove Project location by city*/	
		Utils.deleteParisMorningstarV1AxeltisLocationInJpa(em -> {
			assertTrue(paris.removeLocation(morningstartV1Project));
			assertTrue(morningstartV1Project.removeLocation(paris));		
			SchemaUtils.testInitialState(jdbcTemplateProxy);
			em.merge(morningstartV1Project);
			em.merge(paris);
			em.flush();
			em.clear();
			
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
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_Update_Cities_And_RemoveOrphansWithOrm() {//RES-48
		/**Fetch target project*/
		ProjectId sherpaV1Id = new ProjectId(PROJECT_SHERPA_V1_ID, CLIENT_BELFIUS_ID);
		final Project sherpaV1 = projectRepo.findById(sherpaV1Id).get();
		assertNotNull(sherpaV1);
		/**Fetch Project's tasks*/
		Task task53 = taskRepo.getTaskByDesc(TASK53);
		Task task54 = taskRepo.getTaskByDesc(TASK54);
		Task task55 = taskRepo.getTaskByDesc(TASK55);
		Task task56 = taskRepo.getTaskByDesc(TASK56);
		Task task57 = taskRepo.getTaskByDesc(TASK57);
		/**Fetch Project's Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		/**Fetch Project's Assignments*/
		Assignment assignment53 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task53.getId())).get();
		Assignment assignment54 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task54.getId())).get();
		Assignment assignment55 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task55.getId())).get();
		Assignment assignment56 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task56.getId())).get();
		Assignment assignment57 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task57.getId())).get();

		/**Fetch Project's client */
		Client belfius = clientRepo.getClientByName(BELFIUS);
		/**Fetch Project City*/
		final City brussels = cityRepo.getCityByName(BRUSSELS);
		/**Fetch Project -> Locations*/
		Location sherpaV1ProjectLocationBrussles = locationRepo.findById(new LocationId(brussels.getId(), sherpaV1.getId())).get();
		/**Validate target Project*/
		isProjectValid(sherpaV1, SHERPA,VERSION_1, List.of(sherpaV1ProjectLocationBrussles), belfius, List.of(assignment53, assignment54, assignment55, assignment56, assignment57));

		update_ProjectSherpaV1_With_Cities_InJpa( projectRepo->{
			City paris = cityRepo.getCityByName(PARIS);
			City manchester = cityRepo.getCityByName(MANCHESTER);
			/**Build new Project cities*/
			List <City> sherpaProjectCities = List.of(manchester, paris);
			sherpaV1.setCities(sherpaProjectCities);
			projectRepo.save(sherpaV1);
			projectRepo.flush();

		}, projectRepo, jdbcTemplateProxy);

		entityManager.clear();
		Project newSherpaV1 = projectRepo.findById(sherpaV1Id).get();
		City manchester = cityRepo.getCityByName(MANCHESTER);
		City paris = cityRepo.getCityByName(PARIS);
		Location sherpaV1ProjectLocationParis= locationRepo.findById(new LocationId(paris.getId(), newSherpaV1.getId())).get();
		Location sherpaV1ProjectLocationManchester = locationRepo.findById(new LocationId(manchester.getId(), newSherpaV1.getId())).get();
		isProjectValid(newSherpaV1, SHERPA,VERSION_1, List.of(sherpaV1ProjectLocationParis, sherpaV1ProjectLocationManchester), belfius, List.of(assignment53, assignment54, assignment55, assignment56, assignment57));
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_Update_Cities_And_RemoveOrphansWithOrm_NullSet(){
		/**Fetch target project*/
		ProjectId sherpaV1Id = new ProjectId(PROJECT_SHERPA_V1_ID, CLIENT_BELFIUS_ID);
		final Project sherpaV1 = projectRepo.findById(sherpaV1Id).get();
		assertNotNull(sherpaV1);
		/**Fetch Project's tasks*/
		Task task53 = taskRepo.getTaskByDesc(TASK53);
		Task task54 = taskRepo.getTaskByDesc(TASK54);
		Task task55 = taskRepo.getTaskByDesc(TASK55);
		Task task56 = taskRepo.getTaskByDesc(TASK56);
		Task task57 = taskRepo.getTaskByDesc(TASK57);
		/**Fetch Project's Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		/**Fetch Project's Assignments*/
		Assignment assignment53 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task53.getId())).get();
		Assignment assignment54 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task54.getId())).get();
		Assignment assignment55 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task55.getId())).get();
		Assignment assignment56 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task56.getId())).get();
		Assignment assignment57 = assignmentRepo.findById(new AssignmentId	(sherpaV1.getId(), 		amt.getId(), task57.getId())).get();

		/**Fetch Project's client */
		Client belfius = clientRepo.getClientByName(BELFIUS);
		/**Fetch Project City*/
		final City brussels = cityRepo.getCityByName(BRUSSELS);
		/**Fetch Project -> Locations*/
		Location sherpaV1ProjectLocationBrussles = locationRepo.findById(new LocationId(brussels.getId(), sherpaV1.getId())).get();
		/**Validate target Project*/
		isProjectValid(sherpaV1, SHERPA,VERSION_1, List.of(sherpaV1ProjectLocationBrussles), belfius, List.of(assignment53, assignment54, assignment55, assignment56, assignment57));

		update_ProjectSherpaV1_With_NullCities_InJpa( projectRepo->{
			sherpaV1.setCities(null);
			projectRepo.save(sherpaV1);
			projectRepo.flush();
		}, projectRepo, jdbcTemplateProxy);

		entityManager.clear();
		Project newSherpaV1 = projectRepo.findById(sherpaV1Id).get();
		isProjectValid(newSherpaV1, SHERPA,VERSION_1, null, belfius, List.of(assignment53, assignment54, assignment55, assignment56, assignment57));
	}
}
