package com.tecxis.resume;

import static com.tecxis.resume.CityTest.LOCATION_TABLE;
import static com.tecxis.resume.CityTest.insertACity;
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
import static com.tecxis.resume.persistence.CityRepositoryTest.BRUSSELS;
import static com.tecxis.resume.persistence.CityRepositoryTest.CITY_TABLE;
import static com.tecxis.resume.persistence.CityRepositoryTest.LONDON;
import static com.tecxis.resume.persistence.CityRepositoryTest.PARIS;
import static com.tecxis.resume.persistence.CityRepositoryTest.SWINDON;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AGEAS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.CLIENT_TABLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
import static com.tecxis.resume.persistence.CountryRepositoryTest.BELGIUM;
import static com.tecxis.resume.persistence.CountryRepositoryTest.COUNTRY_TABLE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.FRANCE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.insertACountry;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.AOS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.FORTIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.insertAProject;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
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

import com.tecxis.resume.persistence.AssignmentRepository;
import com.tecxis.resume.persistence.CityRepository;
import com.tecxis.resume.persistence.ProjectRepository;
import com.tecxis.resume.persistence.StaffAssignmentRepository;
import com.tecxis.resume.persistence.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ProjectTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private StaffAssignmentRepository staffAssignmentRepo;
	
	@Autowired
	private StaffRepository staffRepo;

	@Test
	public void testGetDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProjectId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClientId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetClientId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDesc() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffAssignments() {
		/**Prepare project*/
		Project morningstarV1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(morningstarV1);
		
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		
		/**Prepare assignments*/		
		Assignment assignment22 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT22);		
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT23);
		Assignment assignment24 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT24);
		Assignment assignment25 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT25);
		Assignment assignment26 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT26);
		Assignment assignment27 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT27);
		Assignment assignment28 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT28);
		Assignment assignment29 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT29);
		Assignment assignment30 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT30);
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT31);
		assertNotNull(assignment22);
		assertNotNull(assignment23);
		assertNotNull(assignment24);
		assertNotNull(assignment25);
		assertNotNull(assignment26);
		assertNotNull(assignment27);
		assertNotNull(assignment28);
		assertNotNull(assignment29);
		assertNotNull(assignment30);
		assertNotNull(assignment31);
				
		/**Prepare staff assignments*/
		StaffAssignment staffAssignment1 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment22)).get();
		StaffAssignment staffAssignment2 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment23)).get();
		StaffAssignment staffAssignment3 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment24)).get();
		StaffAssignment staffAssignment4 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment25)).get();
		StaffAssignment staffAssignment5 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment26)).get();
		StaffAssignment staffAssignment6 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment27)).get();
		StaffAssignment staffAssignment7 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment28)).get();
		StaffAssignment staffAssignment8 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment29)).get();
		StaffAssignment staffAssignment9 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment30)).get();
		StaffAssignment staffAssignment10 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarV1, amt, assignment31)).get();
			
		/**Validate project's staff assignments*/
		List <StaffAssignment> morningstarV1StaffAssignments = morningstarV1.getStaffAssignments();
		assertNotNull(morningstarV1StaffAssignments);
		assertEquals(10, morningstarV1StaffAssignments.size());
		assertThat(morningstarV1StaffAssignments, Matchers.containsInAnyOrder(staffAssignment1, staffAssignment2, staffAssignment3, staffAssignment4, staffAssignment5, staffAssignment6, staffAssignment7, staffAssignment8, staffAssignment9, staffAssignment10));
	}

	@Test
	public void testSetAssignments() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAssignment() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveAssignment() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetCities() {
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetCities() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client belfius = insertAClient(BELFIUS, entityManager);
		Project sherpaProject = insertAProject(SHERPA, VERSION_1, belfius, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country belgium = insertACountry(BELGIUM, entityManager);
		City brussels = insertACity(BRUSSELS, belgium, entityManager);
		Country france = insertACountry(FRANCE, entityManager);
		City paris = insertACity(PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		
		List <City> sherpaProjectCities = new ArrayList <> ();
		sherpaProjectCities.add(brussels);
		sherpaProjectCities.add(paris);
		sherpaProject.setCities(sherpaProjectCities);
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		
		entityManager.merge(sherpaProject);
		entityManager.flush();
		
		assertEquals(2, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddCity() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country uk = insertACountry("United Kingdom", entityManager);
		Country france = insertACountry(FRANCE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));		
		City london = insertACity(LONDON, uk, entityManager);
		City swindon = insertACity(SWINDON, uk, entityManager);
		City paris = insertACity(PARIS, france, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CITY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Client barclays = insertAClient(BARCLAYS, entityManager);		
		Project adirProject = insertAProject(ADIR, VERSION_1, barclays, entityManager);
		Client ageas = insertAClient(AGEAS, entityManager);		
		Project fortisProject = insertAProject(FORTIS, VERSION_1, ageas, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertTrue(adirProject.addCity(london));		
		entityManager.merge(london);
		entityManager.flush();
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		assertTrue(adirProject.addCity(paris));
		entityManager.merge(swindon);
		entityManager.flush();
		assertEquals(2, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
				
		assertTrue(fortisProject.addCity(swindon));
		entityManager.merge(swindon);
		entityManager.flush();
		assertEquals(3, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveCity() {
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));		
		Client belfius = insertAClient(BELFIUS, entityManager);
		Project sherpaProject = insertAProject(SHERPA, VERSION_1, belfius, entityManager);			
		Country belgium = insertACountry(BELGIUM, entityManager);
		City brussels = insertACity(BRUSSELS, belgium, entityManager);
		Country france = insertACountry(FRANCE, entityManager);
		City paris = insertACity(PARIS, france, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		List <City> sherpaProjectCities = new ArrayList <> ();
		sherpaProjectCities.add(brussels);
		sherpaProjectCities.add(paris);
		sherpaProject.setCities(sherpaProjectCities);
		entityManager.merge(sherpaProject);
		entityManager.flush();		
		assertEquals(2, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		
		

		assertTrue(sherpaProject.removeCity(brussels));
		assertEquals(0, brussels.getProjects().size());
		assertEquals(1, sherpaProject.getCities().size());
		entityManager.merge(sherpaProject);							
		entityManager.flush();
		
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		

		assertTrue(sherpaProject.removeCity(paris));
		assertEquals(0, sherpaProject.getCities().size());
		assertEquals(0, paris.getProjects().size());
		entityManager.merge(sherpaProject);
		entityManager.flush();
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	

	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVersion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetVersion() {
		fail("Not yet implemented");
	}

}
