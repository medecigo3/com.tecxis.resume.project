package com.tecxis.resume.persistence;


import static com.tecxis.resume.persistence.AssignmentRepositoryTest.*;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.EULER_HERMES;
import static com.tecxis.resume.persistence.ClientRepositoryTest.SAGEMCOM;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
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
	public void testGetProjectAssignments() {
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);		
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		List <Assignment> morningstarv1Assignments = morningstarv1.getAssignments();
		assertNotNull(morningstarv1Assignments);
		assertEquals(10, morningstarv1Assignments.size());
		Assignment assignment26 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT26);
		assertEquals(ASSIGNMENT26, assignment26.getDesc());
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT31);
		assertEquals(ASSIGNMENT31, assignment31.getDesc());
		Assignment assignment27 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT27);
		assertEquals(ASSIGNMENT27, assignment27.getDesc());
		Assignment assignment28 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT28);
		assertEquals(ASSIGNMENT28, assignment28.getDesc());
		Assignment assignment22 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT22);
		assertEquals(ASSIGNMENT22, assignment22.getDesc());
		Assignment assignment24 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT24);
		assertEquals(ASSIGNMENT24, assignment24.getDesc());
		Assignment assignment30 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT30);
		assertEquals(ASSIGNMENT30, assignment30.getDesc());
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT23);
		assertEquals(ASSIGNMENT23, assignment23.getDesc());
		Assignment assignment29 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT29);
		assertEquals(ASSIGNMENT29, assignment29.getDesc());
		Assignment assignment25 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT25);
		assertEquals(ASSIGNMENT25, assignment25.getDesc());
		assertThat(morningstarv1Assignments, Matchers.containsInAnyOrder(assignment26, assignment31, assignment27, assignment28, assignment22, assignment24, assignment30, assignment23, assignment29, assignment25));

		
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2); 		
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		List <Assignment> morningstarv2Assignments = morningstarv2.getAssignments();
		assertNotNull(morningstarv2Assignments);
		assertEquals(6, morningstarv2Assignments.size());
		Assignment assignment37 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT37);
		assertEquals(ASSIGNMENT37, assignment37.getDesc());		
		assertThat(morningstarv2Assignments, Matchers.containsInAnyOrder(assignment22, assignment31, assignment27, assignment24, assignment37, assignment23 ));


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
