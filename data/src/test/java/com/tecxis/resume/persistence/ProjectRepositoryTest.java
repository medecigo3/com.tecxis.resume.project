package com.tecxis.resume.persistence;


import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.EULER_HERMES;
import static com.tecxis.resume.persistence.ClientRepositoryTest.SAGEMCOM;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

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

import com.tecxis.resume.Client;
import com.tecxis.resume.Project;
import com.tecxis.resume.ProjectPK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ProjectRepositoryTest {
	
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
		

	@Sql(
			scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
		@Test
	public void testCreateRowsAndInsertIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = insertAClient(BARCLAYS, clientRepo, entityManager);		
		insertAProject(ADIR, VERSION_1, barclays.getClientId(), projectRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client belfius = insertAClient(BELFIUS, clientRepo, entityManager);
		insertAProject(SHERPA, VERSION_1, belfius.getClientId(), projectRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client axeltis = insertAClient(AXELTIS, clientRepo, entityManager);
		insertAProject(MORNINGSTAR, VERSION_1, axeltis.getClientId(), projectRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		/**Test insert version 2 of project MORNINGSTAR*/
		insertAProject(MORNINGSTAR, VERSION_2, axeltis.getClientId(), projectRepo, entityManager);
		assertEquals(4, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
	}
	
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedProject() {
		Client euler = insertAClient(EULER_HERMES, clientRepo, entityManager);
		Project eolisIn = insertAProject(EOLIS, VERSION_1, euler.getClientId(), projectRepo, entityManager);
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
		insertAProject(ADIR, VERSION_1, barclays.getClientId(), projectRepo, entityManager);
		Project adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertNotNull(adir);
		assertEquals(ADIR, adir.getName());
		
		Client belfius = clientRepo.getClientByName(BELFIUS);
		assertNotNull(belfius);
		assertEquals(BELFIUS, belfius.getName());
		insertAProject(SHERPA, VERSION_1, belfius.getClientId(), projectRepo, entityManager);
		Project  sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		assertNotNull(sherpa);
		assertEquals(SHERPA, sherpa.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByProjectByName() {
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertNotNull(axeltis);
		assertEquals(AXELTIS, axeltis.getName());
		insertAProject(MORNINGSTAR, VERSION_1, axeltis.getClientId(), projectRepo, entityManager);
		insertAProject(MORNINGSTAR, VERSION_2, axeltis.getClientId(), projectRepo, entityManager);
		
		List <Project> morningstarList = projectRepo.findByName(MORNINGSTAR);
		assertNotNull(morningstarList);
		assertEquals(2, morningstarList.size());
		assertEquals(MORNINGSTAR, morningstarList.get(0).getName());
		assertEquals(MORNINGSTAR, morningstarList.get(1).getName());
	
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertNotNull(morningstarv1);
		assertNotNull(morningstarv2);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteProject() {
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = insertAClient(SAGEMCOM, clientRepo, entityManager);
		Project tempProject = insertAProject(TED, VERSION_1, barclays.getClientId(), projectRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		projectRepo.delete(tempProject);
		assertNull(projectRepo.findByNameAndVersion(TED, VERSION_1));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
	}
	
	@Test
	public void testGetProjectAssignments() {
		fail("TODO");
	}
	
	
	public static Project insertAProject(String name, String version, long clientId, ProjectRepository projectRepo, EntityManager entityManager) {
		ProjectPK projectPk = new ProjectPK();
		projectPk.setClientId(clientId);
		Project project = new Project();
		project.setName(name);
		project.setVersion(version);
		project.setProjectPk(projectPk);
		assertEquals(0, projectPk.getProjectId());
		projectRepo.save(project);
		assertNotNull(projectPk.getProjectId());
		projectRepo.flush();
		return project;

	}
	
}
