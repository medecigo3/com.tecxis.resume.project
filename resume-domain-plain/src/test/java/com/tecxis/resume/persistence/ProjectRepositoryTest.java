package com.tecxis.resume.persistence;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.ClientTest;
import com.tecxis.resume.domain.Constants;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.ProjectTest;

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
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private ProjectRepository projectRepo;

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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
			
		Client belfius = ClientTest.insertAClient(Constants.BELFIUS, entityManager);
		Project sherpaProject = ProjectTest.insertAProject(Constants.SHERPA, Constants.VERSION_1, belfius, entityManager);
		assertEquals(2, sherpaProject.getId());
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
				
		Client axeltis = ClientTest.insertAClient(Constants.AXELTIS, entityManager);
		Project morningStarV1Project = ProjectTest.insertAProject(Constants.MORNINGSTAR, Constants.VERSION_1, axeltis, entityManager);
		assertEquals(3, morningStarV1Project.getId());
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		/**Test insert version 2 of project MORNINGSTAR*/
		Project monringstarV2Project = ProjectTest.insertAProject(Constants.MORNINGSTAR, Constants.VERSION_2, axeltis, entityManager);
		assertEquals(4, monringstarV2Project.getId());
		assertEquals(4, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));

	}
	
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedProject() {
		Client euler = ClientTest.insertAClient(Constants.EULER_HERMES, entityManager);
		Project eolisIn = ProjectTest.insertAProject(Constants.EOLIS, Constants.VERSION_1, euler, entityManager);
		Project eolisOut = projectRepo.findByNameAndVersion(Constants.EOLIS, Constants.VERSION_1);
		assertEquals(eolisIn, eolisOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByNameAndVersion() {		
		Client barclays = clientRepo.getClientByName(Constants.BARCLAYS);
		assertNotNull(barclays);
		assertEquals(Constants.BARCLAYS, barclays.getName());
		Project adir = projectRepo.findByNameAndVersion(Constants.ADIR, Constants.VERSION_1);
		assertNotNull(adir);
		assertEquals(Constants.ADIR, adir.getName());
		
		Client belfius = clientRepo.getClientByName(Constants.BELFIUS);
		assertNotNull(belfius);
		assertEquals(Constants.BELFIUS, belfius.getName());
		Project  sherpa = projectRepo.findByNameAndVersion(Constants.SHERPA, Constants.VERSION_1);
		assertNotNull(sherpa);
		assertEquals(Constants.SHERPA, sherpa.getName());
		
		Project morningstarv1 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_1);
		Project morningstarv2 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_2);
		assertNotNull(morningstarv1);
		assertNotNull(morningstarv2);
		assertEquals(Constants.MORNINGSTAR, morningstarv1.getName());
		assertEquals(Constants.VERSION_1, morningstarv1.getVersion());
		assertEquals(Constants.MORNINGSTAR, morningstarv2.getName());
		assertEquals(Constants.VERSION_2, morningstarv2.getVersion());
		assertNotEquals(morningstarv1.getId(), morningstarv2.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByName() {
		Client axeltis = clientRepo.getClientByName(Constants.AXELTIS);
		assertNotNull(axeltis);
		assertEquals(Constants.AXELTIS, axeltis.getName());
		
		List <Project> morningstarList = projectRepo.findByName(Constants.MORNINGSTAR);
		assertNotNull(morningstarList);
		assertEquals(2, morningstarList.size());
		assertEquals(Constants.MORNINGSTAR, morningstarList.get(0).getName());
		assertEquals(Constants.MORNINGSTAR, morningstarList.get(1).getName());
	

	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteProject() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(Constants.SAGEMCOM, entityManager);
		Project tempProject = ProjectTest.insertAProject(Constants.TED, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		projectRepo.delete(tempProject);
		assertNull(projectRepo.findByNameAndVersion(Constants.TED, Constants.VERSION_1));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Project> projects = projectRepo.findAll();
		assertEquals(13, projects.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Project> pageableProject = projectRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableProject.getSize());
	}
}
