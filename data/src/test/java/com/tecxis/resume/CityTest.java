package com.tecxis.resume;

import static com.tecxis.resume.persistence.CityRepositoryTest.CITY_TABLE;
import static com.tecxis.resume.persistence.CityRepositoryTest.LONDON;
import static com.tecxis.resume.persistence.CityRepositoryTest.PARIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.CLIENT_TABLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.insertAClient;
import static com.tecxis.resume.persistence.CountryRepositoryTest.COUNTRY_TABLE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.insertACountry;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.AOS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.CENTRE_DES_COMPETENCES;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.DCSC;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EOLIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EUROCLEAR_VERS_CALYPSO;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.FORTIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PARCOURS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SELENIUM;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.TED;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_2;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.insertAProject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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

import com.tecxis.resume.persistence.CityRepository;
import com.tecxis.resume.persistence.ProjectRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CityTest {
	
	public static String LOCATION_TABLE = "LOCATION";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Test
	public void testGetCityId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCityId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCountryId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCountryId() {
		fail("Not yet implemented");
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
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetProjects() {
		City london = cityRepo.getCityByName(LONDON);
		assertEquals(LONDON, london.getName());
		Project dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);
		assertEquals(DCSC, dcsc.getName());
		assertEquals(VERSION_1, dcsc.getVersion());
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertEquals(FORTIS, fortis.getName());
		assertEquals(VERSION_1, fortis.getVersion());
		List <Project> londonProjects = london.getProjects();
		assertNotNull(londonProjects);
		assertEquals(2, londonProjects.size());
		assertThat(londonProjects, Matchers.containsInAnyOrder(dcsc, fortis));
		
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		Project ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		assertEquals(TED, ted.getName());
		assertEquals(VERSION_1 , ted.getVersion());
		Project parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		assertEquals(PARCOURS, parcours.getName());
		assertEquals(VERSION_1 , parcours.getVersion());
		Project euroclear = projectRepo.findByNameAndVersion(EUROCLEAR_VERS_CALYPSO, VERSION_1);
		assertEquals(EUROCLEAR_VERS_CALYPSO, euroclear.getName());
		assertEquals(VERSION_1 , euroclear.getVersion());
		Project morningstarV1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstarV1.getName());
		assertEquals(VERSION_1 , morningstarV1.getVersion());
		Project morningstarV2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertEquals(MORNINGSTAR, morningstarV2.getName());
		assertEquals(VERSION_2 , morningstarV2.getVersion());
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1 , eolis.getVersion());
		Project cdc = projectRepo.findByNameAndVersion(CENTRE_DES_COMPETENCES, VERSION_1);
		assertEquals(CENTRE_DES_COMPETENCES, cdc.getName());
		assertEquals(VERSION_1 , cdc.getVersion());
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		assertEquals(AOS, aos.getName());
		assertEquals(VERSION_1 , aos.getVersion());
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1 , selenium.getVersion());
		
		List <Project> parisProjects = paris.getProjects();
		assertNotNull(parisProjects);
		assertEquals(9, parisProjects.size());
		assertThat(parisProjects, Matchers.containsInAnyOrder(ted, parcours, euroclear, morningstarV1, morningstarV2, eolis, cdc, aos, selenium));

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetProjects() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country UK = insertACountry("United Kingdom", entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client belfius = insertAClient(BELFIUS, entityManager);
		Project sherpaProject = insertAProject(SHERPA, VERSION_1, belfius, entityManager);
		Client axeltis = insertAClient(AXELTIS, entityManager);
		Project morningStarV1Project = insertAProject(MORNINGSTAR, VERSION_1, axeltis, entityManager);
		Client barclays = insertAClient(BARCLAYS, entityManager);		
		Project adirProject = insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
						
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));		
		City london = insertACity(LONDON, UK, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		
		List <Project> projects = new ArrayList <> ();
		projects.add(adirProject);
		projects.add(morningStarV1Project);
		projects.add(sherpaProject);
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		london.setProjects(projects);
		/**Update the inverse side of the association*/
		List <City> londonCityList = new ArrayList<> ();
		londonCityList.add(london);		
		adirProject.setCities(londonCityList);
		morningStarV1Project.setCities(londonCityList);
		sherpaProject.setCities(londonCityList);
		entityManager.merge(london);	
		entityManager.flush();
		
		assertEquals(3, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddProject() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country UK = insertACountry("United Kingdom", entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client belfius = insertAClient(BELFIUS, entityManager);
		Project sherpaProject = insertAProject(SHERPA, VERSION_1, belfius, entityManager);
		Client axeltis = insertAClient(AXELTIS, entityManager);
		Project morningStarV1Project = insertAProject(MORNINGSTAR, VERSION_1, axeltis, entityManager);
		Client barclays = insertAClient(BARCLAYS, entityManager);		
		Project adirProject = insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
						
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));		
		City london = insertACity(LONDON, UK, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		
		
		
		london.addProject(adirProject);	
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		/**Update the inverse side of the association*/
		adirProject.addCity(london);		
		entityManager.merge(london);
		entityManager.flush();	
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		/**Update the inverse side of the association*/
		morningStarV1Project.addCity(london);
		entityManager.merge(london);
		entityManager.flush();	
		assertEquals(2, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		/**Update the inverse side of the association*/
		sherpaProject.addCity(london);
		entityManager.merge(london);	
		entityManager.flush();		
		assertEquals(3, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
	
	}

	@Test
	public void testRemoveProject() {
		fail("Not yet implemented");
		fail("Test Remove orphan");
	}
		
	public static City insertACity(String name, Country country, EntityManager entityManager) {
		City city = new City();
		city.setName(name);				
		city.setCountryId(country.getCountryId());
		entityManager.persist(city);
		entityManager.flush();
		assertThat(city.getCityId(), Matchers.greaterThan((long)0));		
		return city;
		
	}
	

}
