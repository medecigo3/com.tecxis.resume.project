package com.tecxis.resume;

import static com.tecxis.resume.Constants.BELGIUM;
import static com.tecxis.resume.Constants.BRUSSELS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.commons.persistence.id.LocationId;
import com.tecxis.resume.persistence.CityRepository;
import com.tecxis.resume.persistence.ClientRepository;
import com.tecxis.resume.persistence.CountryRepository;
import com.tecxis.resume.persistence.LocationRepository;
import com.tecxis.resume.persistence.ProjectRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml",
		"classpath:validation-api-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CityTest {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Country belgium = CountryTest.insertACountry(BELGIUM, entityManager);
		City city = insertACity(BRUSSELS, belgium, entityManager);
		assertThat(city.getId(), Matchers.greaterThan((long)0));		
	}

	@Test
	public void testSetId() {
		City city = new City();
		assertEquals(0, city.getId());
		city.setId(1);
		assertEquals(1, city.getId());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetName() {
		City city = cityRepo.getCityByName(BRUSSELS);
		assertEquals(BRUSSELS, city.getName());		
	}

	@Test
	public void testSetName() {
		City city = new City();
		assertNull(city.getName());
		city.setName(BRUSSELS);
		assertEquals(BRUSSELS, city.getName());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountry() {
		/**Find City*/
		City london = cityRepo.getCityByName(Constants.LONDON);		
		/**Validate City -> Country*/
		assertEquals(Constants.UNITED_KINGDOM, london.getCountry().getName());
		
		/**Find City*/
		City paris = cityRepo.getCityByName(Constants.PARIS);		
		/**Validate City -> Country*/
		assertEquals(Constants.FRANCE, paris.getCountry().getName());
				
		/**Find brussels*/
		City brussels = cityRepo.getCityByName(Constants.BRUSSELS);		
		/**Validate City -> Country*/
		assertEquals(Constants.BELGIUM, brussels.getCountry().getName());
	}

	/**See equivalent unit test in ContractTest.testSetClientWithOrmOrhpanRemoval */
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetCountryWithOrmOrhpanRemoval() {
		/**Find City*/
		City currentLondon = cityRepo.getCityByName(Constants.LONDON);		
		
		/**Validate City -> Country*/
		assertEquals(Constants.UNITED_KINGDOM, currentLondon.getCountry().getName());
				
		/**Find new country to set*/
		Country france = countryRepo.getCountryByName(Constants.FRANCE);
		assertEquals(Constants.FRANCE, france.getName());
		assertEquals(1, france.getCities().size());
		
		/**Create new City with new Country*/
		City newLondon =  new City();
		newLondon.setId(currentLondon.getId());
		newLondon.setCountry(france);		
		newLondon.setName(currentLondon.getName());
				
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		entityManager.remove(currentLondon);
		entityManager.flush();		
		assertEquals(4, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE)); //2 orphans removed
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));					
		entityManager.persist(newLondon);	
		entityManager.flush();			
		entityManager.clear();
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE)); // 1 new child inserted
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		/**Validate the new City*/		
		newLondon = null;
		newLondon = cityRepo.getCityByName(Constants.LONDON);		
		assertEquals(currentLondon.getId(), newLondon.getId());
		france = countryRepo.getCountryByName(Constants.FRANCE);
		assertEquals(france, newLondon.getCountry());
		
		/**Validate the new Country -> City*/		
		assertEquals(2, france.getCities().size());
		assertThat(france.getCities(), Matchers.hasItem(newLondon));		
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddLocation() {
		/**Find & validate city to test*/
		City london = cityRepo.getCityByName(Constants.LONDON);	
		assertEquals(Constants.UNITED_KINGDOM, london.getCountry().getName());
		assertEquals(2, london.getLocations().size());
				
		/**Find & validate country to insert*/
		Country france = countryRepo.getCountryByName(Constants.FRANCE);
		assertEquals(Constants.FRANCE, france.getName());
		assertEquals(1, france.getCities().size());
		
		/**Find & validate project to insert*/
		Client belfius = clientRepo.getClientByName(Constants.BELFIUS);
		assertNotNull(belfius);
		assertEquals(Constants.BELFIUS, belfius.getName());
		Project  sherpa = projectRepo.findByNameAndVersion(Constants.SHERPA, Constants.VERSION_1);		
		assertNotNull(sherpa);		
		assertEquals(Constants.SHERPA, sherpa.getName());
		assertEquals(1, sherpa.getLocations().size());
				
		/**Validate pre-test state of Location*/
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		LocationId locationId = new LocationId(london, sherpa);
		assertFalse(locationRepo.findById(locationId).isPresent());
		
		/**Validate state of current City -> Locations */
		List <Location> londonLocations = london.getLocations();
		assertEquals(2, londonLocations.size());		
		Location fortisLocation = locationRepo.findById(new LocationId(london, projectRepo.findByNameAndVersion(Constants.FORTIS, Constants.VERSION_1))).get();
		Location dcscLocation = locationRepo.findById(new LocationId(london, projectRepo.findByNameAndVersion(Constants.DCSC, Constants.VERSION_1))).get();
		assertThat(londonLocations, Matchers.containsInAnyOrder(fortisLocation, dcscLocation)); 
		
		/**Create new Location*/
		Location newLocation = new Location (london, sherpa);
		
		/***Add new Location*/		
		london.addLocation(newLocation);
		/**Add new Location to the inverse association*/
		sherpa.addLocation(newLocation);
		entityManager.persist(newLocation);
		entityManager.merge(london);
		entityManager.merge(sherpa);		
		entityManager.flush();
			
		/**Test Location table post test state*/	
		assertEquals(15, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(3, london.getLocations().size());
		assertEquals(2, sherpa.getLocations().size());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingLocation() {
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(Constants.MANCHESTER);		
		assertEquals(Constants.UNITED_KINGDOM, manchester.getCountry().getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(1, manchesterLocations.size());
		
		/**Validate projects of the city to test*/		
		Project currentAdir = manchesterLocations.get(0).getProject();
		assertNotNull(currentAdir);
		assertEquals(Constants.ADIR, currentAdir.getName());
		assertEquals(1, currentAdir.getLocations().size());
		
		/**Find duplicate Project to insert*/
		Project duplicateAdir = projectRepo.findByNameAndVersion(Constants.ADIR, Constants.VERSION_1);
		assertNotNull(duplicateAdir);
		assertEquals(Constants.ADIR, duplicateAdir.getName());
		assertEquals(1, duplicateAdir.getLocations().size());
		
		/**Test that duplicate project exists in the list of Manchester-ADIR locations*/
		List <Location> manchesterAdirLocations = manchester.getLocations();
		Location duplicateAdirLocations =  duplicateAdir.getLocations().get(0);
		assertTrue(manchesterAdirLocations.contains(duplicateAdirLocations));
		
		/**Add Project duplicate to city: expect error*/
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(duplicateAdir, currentAdir);
		
		/**Create duplicate location*/
		Location newLocation = new Location (manchester, duplicateAdir);
		
		/**Add duplicate Location*/
		manchester.addLocation(newLocation);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveLocation() {
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(Constants.MANCHESTER);
		assertEquals(Constants.UNITED_KINGDOM, manchester.getCountry().getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(1, manchesterLocations.size());
		
		/**Validate projects of the city to test*/
		assertEquals(1, manchesterLocations.size());
		Project currentAdir = manchesterLocations.get(0).getProject();
		assertNotNull(currentAdir);
		assertEquals(Constants.ADIR, currentAdir.getName());
		assertEquals(1, currentAdir.getLocations().size());
		
		/**Remove the Location*/
		Location manchesterLocation = manchester.getLocations().get(0);
		assertTrue(manchester.removeLocation(manchesterLocation));
		assertTrue(currentAdir.removeLocation(manchesterLocation));
		
		/**Find the Location*/
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		entityManager.merge(manchester);
		entityManager.merge(currentAdir);
		entityManager.flush();
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(0, manchester.getLocations().size());
		assertEquals(0, currentAdir.getLocations().size());
		LocationId locaitonId = new LocationId(manchester, currentAdir);
		assertFalse(locationRepo.findById(locaitonId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetLocations() {
		/**Find & validate city to test*/
		City london = cityRepo.getCityByName(Constants.LONDON);
		assertEquals(Constants.UNITED_KINGDOM, london.getCountry().getName());
		List <Location> londonLocations = london.getLocations();
		assertEquals(2, londonLocations.size());
		
		/**Validate Locations*/		
		assertEquals(london, londonLocations.get(0).getCity());
		assertEquals(london, londonLocations.get(1).getCity());		
		Project fortis = projectRepo.findByNameAndVersion(Constants.FORTIS, Constants.VERSION_1);
		Project dcsc = projectRepo.findByNameAndVersion(Constants.DCSC, Constants.VERSION_1);
		assertThat(londonLocations.get(0).getProject(), Matchers.oneOf(fortis, dcsc));
		assertThat(londonLocations.get(1).getProject(), Matchers.oneOf(fortis, dcsc));	
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetLocations() {
		/**Find & validate City to test*/
		City london = cityRepo.getCityByName(Constants.LONDON);
		assertEquals(Constants.UNITED_KINGDOM, london.getCountry().getName());
		List <Location> londonLocations = london.getLocations();
		assertEquals(2, londonLocations.size());
		/**Validate opposite associations*/
		Location location1 = london.getLocations().get(0);
		Location location2 = london.getLocations().get(1);		
		assertEquals(london, location1.getCity());
		assertEquals(london, location2.getCity());		
		Project fortis = projectRepo.findByNameAndVersion(Constants.FORTIS, Constants.VERSION_1);
		Project dcsc = projectRepo.findByNameAndVersion(Constants.DCSC, Constants.VERSION_1);		
		assertThat(location1.getProject(), Matchers.oneOf(fortis, dcsc));
		assertThat(location2.getProject(), Matchers.oneOf(fortis, dcsc));		
		
	
		/**Find & validate Projects to test*/
		Project selenium = projectRepo.findByNameAndVersion(Constants.SELENIUM, Constants.VERSION_1);
		Project aos = projectRepo.findByNameAndVersion(Constants.AOS, Constants.VERSION_1);
		Project morningstarv2 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_2);
		assertEquals(Constants.SELENIUM, selenium.getName());
		assertEquals(Constants.VERSION_1, selenium.getVersion());		
		assertEquals(Constants.AOS, aos.getName());
		assertEquals(Constants.VERSION_1, aos.getVersion());		
		assertEquals(Constants.MORNINGSTAR, morningstarv2.getName());
		assertEquals(Constants.VERSION_2, morningstarv2.getVersion());
		/**Validate opposite association*/
		City paris = cityRepo.getCityByName(Constants.PARIS);
		City swindon = cityRepo.getCityByName(Constants.SWINDON);		
		List <Location> seleniumLocations = selenium.getLocations();
		List <Location> aosLocations = aos.getLocations();
		assertEquals(1, seleniumLocations.size());
		assertEquals(2, aosLocations.size());
		Location seleniumLocation = seleniumLocations.get(0);
		assertEquals(paris, seleniumLocation.getCity());
		assertThat(aosLocations.get(0).getCity(), Matchers.oneOf(paris, swindon));
		assertThat(aosLocations.get(1).getCity(), Matchers.oneOf(paris, swindon));
				
		/**Validate current Locations*/		
		assertEquals(london, londonLocations.get(0).getCity());
		fortis = projectRepo.findByNameAndVersion(Constants.FORTIS, Constants.VERSION_1);
		dcsc = projectRepo.findByNameAndVersion(Constants.DCSC, Constants.VERSION_1);
		assertThat(londonLocations.get(0).getProject(), Matchers.oneOf(fortis, dcsc));
		assertThat(londonLocations.get(1).getProject(), Matchers.oneOf(fortis, dcsc));	
		
		/**Prepare new Locations*/
		Location londonSeleniumLocation =  new Location (london, selenium);
		Location londonAosLocation = new Location(london, aos);
		Location londonMorningstarv2Location = new Location(london, morningstarv2);
		List <Location>  newLocations = new  ArrayList<>();
		newLocations.add(londonSeleniumLocation);		
		newLocations.add(londonAosLocation);
		newLocations.add(londonMorningstarv2Location);
				
		/**Set new Locations*/
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));		
		london.setLocations(newLocations);
		assertEquals(3, london.getLocations().size());
		entityManager.merge(london);
		entityManager.flush();
		entityManager.clear();
		assertEquals(15, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));		
		
		/**Validate test*/
		london = cityRepo.getCityByName(Constants.LONDON);
		assertEquals(3, london.getLocations().size());
		location1 = london.getLocations().get(0);
		location2 = london.getLocations().get(1);
		Location location3 = london.getLocations().get(2);
		assertEquals(london, location1.getCity());
		assertEquals(london, location2.getCity());
		assertEquals(london, location3.getCity());
		
		assertThat(location1.getProject().getName(), Matchers.oneOf(Constants.SELENIUM, Constants.AOS, Constants.MORNINGSTAR));
		assertThat(location2.getProject().getName(), Matchers.oneOf(Constants.SELENIUM, Constants.AOS, Constants.MORNINGSTAR));
		assertThat(location3.getProject().getName(), Matchers.oneOf(Constants.SELENIUM, Constants.AOS, Constants.MORNINGSTAR));
		
		assertThat(location1.getProject(), Matchers.oneOf(selenium, aos, morningstarv2));
		assertThat(location2.getProject(), Matchers.oneOf(selenium, aos, morningstarv2));
		assertThat(location3.getProject(), Matchers.oneOf(selenium, aos, morningstarv2));
		
		/**Test the opposite association*/
		selenium = projectRepo.findByNameAndVersion(Constants.SELENIUM, Constants.VERSION_1);
		aos = projectRepo.findByNameAndVersion(Constants.AOS, Constants.VERSION_1);
		morningstarv2 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_2);
		/**Test selenium Project has all Cities*/
		assertEquals(2, selenium.getLocations().size());
		paris = cityRepo.getCityByName(Constants.PARIS);
		assertThat(selenium.getLocations().get(0).getCity(), Matchers.oneOf(paris, london));
		assertThat(selenium.getLocations().get(1).getCity(), Matchers.oneOf(paris, london));
		/**Test aos Project has all Cities*/
		assertEquals(3, aos.getLocations().size());
		swindon = cityRepo.getCityByName(Constants.SWINDON);
		assertThat(aos.getLocations().get(0).getCity(), Matchers.oneOf(paris, london, swindon));
		assertThat(aos.getLocations().get(1).getCity(), Matchers.oneOf(paris, london, swindon));
		assertThat(aos.getLocations().get(2).getCity(), Matchers.oneOf(paris, london, swindon));
		/**Test morningstar v2 Project has all Cities*/		
		assertEquals(2, morningstarv2.getLocations().size());
		assertThat(morningstarv2.getLocations().get(0).getCity(), Matchers.oneOf(paris, london));
		assertThat(morningstarv2.getLocations().get(1).getCity(), Matchers.oneOf(paris, london));
		
	}	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveCity() {
		/**Find City to remove*/
		City london = cityRepo.getCityByName(Constants.LONDON);		
		
		/**Validate association City -> Country */
		assertEquals(Constants.LONDON,  london.getName());
		assertEquals(Constants.UNITED_KINGDOM, london.getCountry().getName());
		
		 /**Validate association Country -> City*/
		Country uk = countryRepo.getCountryByName(Constants.UNITED_KINGDOM);
		assertEquals(Constants.UNITED_KINGDOM, uk.getName());
		assertEquals(3, uk.getCities().size());
		assertThat(uk.getCities(), Matchers.hasItems(london));
				
		/**Detach entities*/
		entityManager.clear();
		
		/**Find City to remove*/
		london = cityRepo.getCityByName(Constants.LONDON);		
		
		/**Remove city*/
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		entityManager.remove(london);
		entityManager.flush();
		entityManager.clear();
		
		/**Test city was removed*/
		assertEquals(4, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertNull(cityRepo.getCityByName(Constants.LONDON));
		uk = countryRepo.getCountryByName(Constants.UNITED_KINGDOM);
		assertEquals(Constants.UNITED_KINGDOM, uk.getName());
		assertEquals(2, uk.getCities().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetProjects() {
		City london = cityRepo.getCityByName(Constants.LONDON);
		assertEquals(Constants.LONDON, london.getName());
		Project dcsc = projectRepo.findByNameAndVersion(Constants.DCSC, Constants.VERSION_1);
		assertEquals(Constants.DCSC, dcsc.getName());
		assertEquals(Constants.VERSION_1, dcsc.getVersion());
		Project fortis = projectRepo.findByNameAndVersion(Constants.FORTIS, Constants.VERSION_1);
		assertEquals(Constants.FORTIS, fortis.getName());
		assertEquals(Constants.VERSION_1, fortis.getVersion());
		List <Project> londonProjects = london.getProjects();
		assertNotNull(londonProjects);
		assertEquals(2, londonProjects.size());
		assertThat(londonProjects, Matchers.containsInAnyOrder(dcsc, fortis));
		
		City paris = cityRepo.getCityByName(Constants.PARIS);
		assertEquals(Constants.PARIS, paris.getName());
		Project ted = projectRepo.findByNameAndVersion(Constants.TED, Constants.VERSION_1);
		assertEquals(Constants.TED, ted.getName());
		assertEquals(Constants.VERSION_1 , ted.getVersion());
		Project parcours = projectRepo.findByNameAndVersion(Constants.PARCOURS, Constants.VERSION_1);
		assertEquals(Constants.PARCOURS, parcours.getName());
		assertEquals(Constants.VERSION_1 , parcours.getVersion());
		Project euroclear = projectRepo.findByNameAndVersion(Constants.EUROCLEAR_VERS_CALYPSO, Constants.VERSION_1);
		assertEquals(Constants.EUROCLEAR_VERS_CALYPSO, euroclear.getName());
		assertEquals(Constants.VERSION_1 , euroclear.getVersion());
		Project morningstarV1 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_1);
		assertEquals(Constants.MORNINGSTAR, morningstarV1.getName());
		assertEquals(Constants.VERSION_1 , morningstarV1.getVersion());
		Project morningstarV2 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_2);
		assertEquals(Constants.MORNINGSTAR, morningstarV2.getName());
		assertEquals(Constants.VERSION_2 , morningstarV2.getVersion());
		Project eolis = projectRepo.findByNameAndVersion(Constants.EOLIS, Constants.VERSION_1);
		assertEquals(Constants.EOLIS, eolis.getName());
		assertEquals(Constants.VERSION_1 , eolis.getVersion());
		Project cdc = projectRepo.findByNameAndVersion(Constants.CENTRE_DES_COMPETENCES, Constants.VERSION_1);
		assertEquals(Constants.CENTRE_DES_COMPETENCES, cdc.getName());
		assertEquals(Constants.VERSION_1 , cdc.getVersion());
		Project aos = projectRepo.findByNameAndVersion(Constants.AOS, Constants.VERSION_1);
		assertEquals(Constants.AOS, aos.getName());
		assertEquals(Constants.VERSION_1 , aos.getVersion());
		Project selenium = projectRepo.findByNameAndVersion(Constants.SELENIUM, Constants.VERSION_1);
		assertEquals(Constants.SELENIUM, selenium.getName());
		assertEquals(Constants.VERSION_1 , selenium.getVersion());
		
		List <Project> parisProjects = paris.getProjects();
		assertNotNull(parisProjects);
		assertEquals(9, parisProjects.size());
		assertThat(parisProjects, Matchers.containsInAnyOrder(ted, parcours, euroclear, morningstarV1, morningstarV2, eolis, cdc, aos, selenium));

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddProject() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		Country uk = CountryTest.insertACountry(Constants.UNITED_KINGDOM, entityManager);
		Country france = CountryTest.insertACountry(Constants.FRANCE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client belfius = ClientTest.insertAClient(Constants.BELFIUS, entityManager);
		Project sherpaProject = ProjectTest.insertAProject(Constants.SHERPA, Constants.VERSION_1, belfius, entityManager);
		Client axeltis = ClientTest.insertAClient(Constants.AXELTIS, entityManager);
		Project morningStarV1Project = ProjectTest.insertAProject(Constants.MORNINGSTAR, Constants.VERSION_1, axeltis, entityManager);
		Client barclays = ClientTest.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
								
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));		
		City london = insertACity(Constants.LONDON, uk, entityManager);
		City paris = insertACity(Constants.PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
				
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));	
		/**Set London city to Adir project*/
		assertTrue(adirProject.addCity(london));		
		entityManager.merge(london);
		entityManager.flush();	
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		/**Set London city to Morningstar project*/
		assertTrue(morningStarV1Project.addCity(london));
		entityManager.merge(london);
		entityManager.flush();	
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		/**Set London city to Sherpa project*/
		assertTrue(sherpaProject.addCity(london));
		entityManager.merge(london);	
		entityManager.flush();		
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		/**Set Paris city to Adir project*/
		assertTrue(adirProject.addCity(paris));
		entityManager.merge(paris);
		entityManager.flush();	
		assertEquals(4, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		/**Set Paris city to Sherpa project*/
		assertTrue(sherpaProject.addCity(paris));
		entityManager.merge(paris);
		entityManager.flush();	
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
			
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveProject() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));		
		Country UK = CountryTest.insertACountry(Constants.UNITED_KINGDOM, entityManager);
		Country france = CountryTest.insertACountry(Constants.FRANCE, entityManager);
		Client belfius = ClientTest.insertAClient(Constants.BELFIUS, entityManager);
		Project sherpaProject = ProjectTest.insertAProject(Constants.SHERPA, Constants.VERSION_1, belfius, entityManager);
		Client axeltis = ClientTest.insertAClient(Constants.AXELTIS, entityManager);
		Project morningStarV1Project = ProjectTest.insertAProject(Constants.MORNINGSTAR, Constants.VERSION_1, axeltis, entityManager);
		Client barclays = ClientTest.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);			
		City london = insertACity(Constants.LONDON, UK, entityManager);		
		City paris = insertACity(Constants.PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));	
		
		adirProject.addCity(london);
		morningStarV1Project.addCity(london);
		sherpaProject.addCity(london);
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		/**Set London city to Adir project*/
		List <City> adirCityList = new ArrayList<> ();
		adirCityList.add(london);		
		adirProject.setCities(adirCityList);
		/**Set London & Paris cities to Morningstar project*/
		List <City> morningStarV1CityList = new ArrayList<> ();
		morningStarV1CityList.add(london);		
		morningStarV1CityList.add(paris);
		morningStarV1Project.setCities(morningStarV1CityList);
		/**Set London & Paris cities to Sherpa project*/
		List <City> sherpaCityList = new ArrayList<> ();
		sherpaCityList.add(london);	
		sherpaCityList.add(paris);	
		sherpaProject.setCities(sherpaCityList);		
		entityManager.merge(adirProject);	
		entityManager.merge(morningStarV1Project);
		entityManager.merge(sherpaProject);
		entityManager.flush();		
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
				
		assertTrue(adirProject.removeCity(london));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(2, morningStarV1Project.getCities().size());
		assertEquals(2, sherpaProject.getCities().size());
		entityManager.merge(adirProject);
		entityManager.flush();		
		assertEquals(4, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));	


		assertTrue(morningStarV1Project.removeCity(london));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(1, morningStarV1Project.getCities().size());
		assertEquals(2, sherpaProject.getCities().size());
		entityManager.merge(morningStarV1Project);
		entityManager.flush();		
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		assertTrue(sherpaProject.removeCity(london));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(1, morningStarV1Project.getCities().size());
		assertEquals(1, sherpaProject.getCities().size());
		entityManager.merge(sherpaProject);
		entityManager.flush();	
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));		
		
		assertTrue(morningStarV1Project.removeCity(paris));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(0, morningStarV1Project.getCities().size());
		assertEquals(1, sherpaProject.getCities().size());
		entityManager.merge(morningStarV1Project);
		entityManager.flush();		
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		assertTrue(sherpaProject.removeCity(paris));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(0, morningStarV1Project.getCities().size());
		assertEquals(0, sherpaProject.getCities().size());
		entityManager.merge(sherpaProject);
		entityManager.flush();	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));

		
	}
	
	@Test
	public void testNameIsNotNull() {
		City city = new City();
		Set<ConstraintViolation<City>> violations = validator.validate(city);
        assertFalse(violations.isEmpty());
		
	}
	
	@Test
	public void testToString() {
		City city = new City();
		city.toString();
	}
		
	public static City insertACity(String name, Country country, EntityManager entityManager) {
		City city = new City();
		city.setName(name);				
		city.setCountry(country);
		entityManager.persist(city);
		entityManager.flush();
		assertThat(city.getId(), Matchers.greaterThan((long)0));		
		return city;
		
	}
	

}
