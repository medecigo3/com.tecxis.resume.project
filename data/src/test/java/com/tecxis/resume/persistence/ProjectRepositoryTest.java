package com.tecxis.resume.persistence;


import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT22;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT23;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT24;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT25;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT26;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT27;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT28;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT29;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT30;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT31;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT37;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.EULER_HERMES;
import static com.tecxis.resume.persistence.ClientRepositoryTest.SAGEMCOM;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

import com.tecxis.resume.Assignment;
import com.tecxis.resume.Client;
import com.tecxis.resume.Project;
import com.tecxis.resume.Staff;
import com.tecxis.resume.StaffAssignment;
import com.tecxis.resume.StaffAssignmentId;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ProjectRepositoryTest {
	
    /** For Log4j2 dependencies >= 2.10 set this system property to configure ANSI Styling for Windows 
     * Unix-based operating systems such as Linux and Mac OS X support ANSI color codes by default. 
     * See https://logging.apache.org/log4j/2.x/manual/layouts.html#enable-jansi*/
	private static final String LOG4J_SKIP_JANSI = "log4j.skipJansi";
	public static String PROJECT_TABLE = "Project";
	public static String ADIR = "ADIR";
	public static String FORTIS = "FORTIS";
	public static String DCSC = "DCSC";
	public static String TED = "TED";
	public static String PARCOURS = "PARCOURS";
	public static String EOLIS = "EOLIS";
	public static String AOS = "AOS";
	public static String SHERPA = "SHERPA";
	public static String SELENIUM = "SELENIUM";
	public static String MORNINGSTAR = "MORNINGSTAR";
	public static String CENTRE_DES_COMPETENCES = "CENTRE_DES_COMPETENCES";
	public static String EUROCLEAR_VERS_CALYPSO = "EUROCLEAR_VERS_CALYPSO";
	public static String VERSION_1 = "1.0";
	public static String VERSION_2 = "2.0";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private StaffAssignmentRepository staffAssignmentRepo;

    @AfterClass
    public static void afterClass() {
        System.clearProperty(LOG4J_SKIP_JANSI);
    }

    @BeforeClass
    public static void beforeClass() {
    	Logger log = LogManager.getLogger();
		System.clearProperty(LOG4J_SKIP_JANSI);
		System.setProperty(LOG4J_SKIP_JANSI, "false");
		log.debug("Starting class unit testing");
    }
	
    @Before
    public void before() {
    	System.clearProperty(LOG4J_SKIP_JANSI);
        System.setProperty(LOG4J_SKIP_JANSI, "false");
        
    }

	
	@Sql(
			scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
		@Test
	public void testCreateRowsAndInsertIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = insertAClient(BARCLAYS, entityManager);		
		Project adirProject = insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
			
		Client belfius = insertAClient(BELFIUS, entityManager);
		Project sherpaProject = insertAProject(SHERPA, VERSION_1, belfius, entityManager);
		assertEquals(2, sherpaProject.getProjectId());
		assertEquals(2, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
		Client axeltis = insertAClient(AXELTIS, entityManager);
		Project morningStarV1Project = insertAProject(MORNINGSTAR, VERSION_1, axeltis, entityManager);
		assertEquals(3, morningStarV1Project.getProjectId());
		assertEquals(3, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Test insert version 2 of project MORNINGSTAR*/
		Project monringstarV2Project = insertAProject(MORNINGSTAR, VERSION_2, axeltis, entityManager);
		assertEquals(4, monringstarV2Project.getProjectId());
		assertEquals(4, countRowsInTable(jdbcTemplate, PROJECT_TABLE));

	}
	
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedProject() {
		Client euler = insertAClient(EULER_HERMES, entityManager);
		Project eolisIn = insertAProject(EOLIS, VERSION_1, euler, entityManager);
		Project eolisOut = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertEquals(eolisIn, eolisOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByProjectByNameAndVersion() {		
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
		assertNotEquals(morningstarv1.getProjectId(), morningstarv2.getProjectId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByProjectByName() {
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
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteProject() {
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = insertAClient(SAGEMCOM, entityManager);
		Project tempProject = insertAProject(TED, VERSION_1, barclays, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		projectRepo.delete(tempProject);
		assertNull(projectRepo.findByNameAndVersion(TED, VERSION_1));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
	}
	
	@Test
	public void testGetStaffAssignments() {
		/**Prepare project*/
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);		
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		List <Project> amtProjects = amt.getProjects();
		assertEquals(62, amtProjects.size());
		
		/**Prepare assignments*/	
		List <StaffAssignment> morningstarv1Assignments = morningstarv1.getStaffAssignments();
		assertNotNull(morningstarv1Assignments);
		assertEquals(10, morningstarv1Assignments.size());
		Assignment assignment26 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT26);		
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT31);		
		Assignment assignment27 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT27);
		Assignment assignment28 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT28);		
		Assignment assignment22 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT22);		
		Assignment assignment24 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT24);		
		Assignment assignment30 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT30);		
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT23);		
		Assignment assignment29 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT29);		
		Assignment assignment25 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT25);
		assertEquals(ASSIGNMENT22, assignment22.getDesc());
		assertEquals(ASSIGNMENT23, assignment23.getDesc());
		assertEquals(ASSIGNMENT24, assignment24.getDesc());
		assertEquals(ASSIGNMENT25, assignment25.getDesc());
		assertEquals(ASSIGNMENT26, assignment26.getDesc());
		assertEquals(ASSIGNMENT27, assignment27.getDesc());
		assertEquals(ASSIGNMENT28, assignment28.getDesc());
		assertEquals(ASSIGNMENT29, assignment29.getDesc());
		assertEquals(ASSIGNMENT30, assignment30.getDesc());
		assertEquals(ASSIGNMENT31, assignment31.getDesc());
		
		/**Prepare staff assignments*/
		StaffAssignment msv1StaffAssignment1 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment22)).get();
		StaffAssignment msv1StaffAssignment2 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment23)).get();
		StaffAssignment msv1StaffAssignment3 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment24)).get();
		StaffAssignment msv1StaffAssignment4 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment25)).get();
		StaffAssignment msv1StaffAssignment5 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment26)).get();
		StaffAssignment msv1StaffAssignment6 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment27)).get();
		StaffAssignment msv1StaffAssignment7 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment28)).get();
		StaffAssignment msv1StaffAssignment8 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment29)).get();
		StaffAssignment msv1StaffAssignment9 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment30)).get();
		StaffAssignment msv1StaffAssignment10 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment31)).get();
			
		/**Validate project's staff assignments*/
		assertThat(morningstarv1Assignments, Matchers.containsInAnyOrder(msv1StaffAssignment1, msv1StaffAssignment2, msv1StaffAssignment3, msv1StaffAssignment4, msv1StaffAssignment5, msv1StaffAssignment6, msv1StaffAssignment7, msv1StaffAssignment8, msv1StaffAssignment9, msv1StaffAssignment10));
		
		/**TEST 2*/
		
		/**Prepare project*/		
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2); 		
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		
		/**Prepare assignments*/
		List <StaffAssignment> morningstarv2Assignments = morningstarv2.getStaffAssignments();
		assertNotNull(morningstarv2Assignments);
		assertEquals(6, morningstarv2Assignments.size());		
		Assignment assignment37 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT37);
		assertEquals(ASSIGNMENT37, assignment37.getDesc());
		
		StaffAssignment mv2StaffAssignment1 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment22)).get();
		StaffAssignment mv2StaffAssignment2 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment23)).get();
		StaffAssignment mv2StaffAssignment3 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment24)).get();
		StaffAssignment mv2StaffAssignment4 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment27)).get();
		StaffAssignment mv2StaffAssignment5 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment31)).get();
		StaffAssignment mv2StaffAssignment6 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment37)).get();
			
		/**Validate project's staff assignments*/
		assertThat(morningstarv2Assignments, Matchers.containsInAnyOrder(mv2StaffAssignment1, mv2StaffAssignment2, mv2StaffAssignment3, mv2StaffAssignment4, mv2StaffAssignment5, mv2StaffAssignment6 ));


	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		fail("TODO");
	}
	
	public static Project insertAProject(String name, String version, Client client, EntityManager entityManager) {
		Project project = new Project();
		project.setClientId(client.getClientId());		
		project.setName(name);
		project.setVersion(version);
		assertEquals(0, project.getProjectId());
		entityManager.persist(project);
		entityManager.flush();
		assertThat(project.getProjectId(), Matchers.greaterThan((long)0));
		return project;

	}
	
}
