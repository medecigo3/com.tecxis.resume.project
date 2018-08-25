package com.tecxis.resume.persistence;


import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.EULER_HERMES;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JHON_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JHON_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.insertAStaff;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.tecxis.resume.Staff;

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
	public static String MORNINGSTAR = "Morningstar";
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
	private StaffRepository staffRepo;
	

	@Sql(
			scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
		@Test
	public void testCreateRowsAndInsertIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = insertAClient(BARCLAYS, clientRepo, entityManager);
		Staff amt= insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		insertAProject(ADIR, VERSION_1, barclays.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client belfius = insertAClient(BELFIUS, clientRepo, entityManager);
		insertAProject(SHERPA, VERSION_1, belfius.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client axeltis = insertAClient(AXELTIS, clientRepo, entityManager);
		insertAProject(MORNINGSTAR, VERSION_1, axeltis.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		/**Test insert version 2 of project MORNINGSTAR*/
		insertAProject(MORNINGSTAR, VERSION_2, axeltis.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		assertEquals(4, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
	}
	
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedProject() {
		Client euler = insertAClient(EULER_HERMES, clientRepo, entityManager);
		Staff jhon = insertAStaff(JHON_NAME, JHON_LASTNAME, staffRepo, entityManager);
		Project eolisIn = insertAProject(EOLIS, VERSION_1, euler.getClientId(), jhon.getStaffId(), projectRepo, entityManager);
		Project eolisOut = projectRepo.findByProjectPk_NameAndProjectPk_Version(EOLIS, VERSION_1);
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
		Staff amt = staffRepo.getStaffByName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		insertAProject(ADIR, VERSION_1, barclays.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		Project adir = projectRepo.findByProjectPk_NameAndProjectPk_Version(ADIR, VERSION_1);
		assertNotNull(adir);
		assertEquals(ADIR, adir.getProjectPk().getName());
		
		Client belfius = clientRepo.getClientByName(BELFIUS);
		assertNotNull(belfius);
		assertEquals(BELFIUS, belfius.getName());
		insertAProject(SHERPA, VERSION_1, belfius.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		Project  sherpa = projectRepo.findByProjectPk_NameAndProjectPk_Version(SHERPA, VERSION_1);
		assertNotNull(sherpa);
		assertEquals(SHERPA, sherpa.getProjectPk().getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByProjectByName() {
		Staff amt = staffRepo.getStaffByName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertNotNull(axeltis);
		assertEquals(AXELTIS, axeltis.getName());
		insertAProject(MORNINGSTAR, VERSION_1, axeltis.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		insertAProject(MORNINGSTAR, VERSION_2, axeltis.getClientId(), amt.getStaffId(), projectRepo, entityManager);
		
		List <Project> morningstarList = projectRepo.findByProjectPk_Name(MORNINGSTAR);
		assertNotNull(morningstarList);
		assertEquals(2, morningstarList.size());
		assertEquals(MORNINGSTAR, morningstarList.get(0).getProjectPk().getName());
		assertEquals(MORNINGSTAR, morningstarList.get(1).getProjectPk().getName());
	
		Project morningstarv1 = projectRepo.findByProjectPk_NameAndProjectPk_Version(MORNINGSTAR, VERSION_1);
		Project morningstarv2 = projectRepo.findByProjectPk_NameAndProjectPk_Version(MORNINGSTAR, VERSION_2);
		assertNotNull(morningstarv1);
		assertNotNull(morningstarv2);
		assertEquals(MORNINGSTAR, morningstarv1.getProjectPk().getName());
		assertEquals(VERSION_1, morningstarv1.getProjectPk().getVersion());
		assertEquals(MORNINGSTAR, morningstarv2.getProjectPk().getName());
		assertEquals(VERSION_2, morningstarv2.getProjectPk().getVersion());
	}
	
	public static Project insertAProject(String name, String version, long clientId, long staffId, ProjectRepository projectRepo, EntityManager entityManager) {
		ProjectPK projectPk = new ProjectPK();
		projectPk.setName(name);
		projectPk.setVersion(version);
		projectPk.setClientId(clientId);
		projectPk.setStaffId(staffId);		
		Project project = new Project();
		project.setProjectPk(projectPk);
		projectRepo.save(project);
		projectRepo.flush();
		return project;

	}
	
}
