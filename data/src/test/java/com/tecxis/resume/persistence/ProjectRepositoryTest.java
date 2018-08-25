package com.tecxis.resume.persistence;


import static com.tecxis.resume.persistence.ClientRepositoryTest.*;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
import static com.tecxis.resume.persistence.StaffRepositoryTest.*;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.insertAStaff;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

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
		Project eolisIn = insertAProject(EOLIS, VERSION_2, euler.getClientId(), jhon.getStaffId(), projectRepo, entityManager);
		Project eolisOut = projectRepo.findByProjectPk_Name(EOLIS);
		assertEquals(eolisIn, eolisOut);
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
