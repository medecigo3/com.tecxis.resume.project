package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AOS;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.BRUSSELS;
import static com.tecxis.resume.domain.Constants.CENTRE_DES_COMPETENCES;
import static com.tecxis.resume.domain.Constants.CITY_PARIS_TOTAL_LOCATIONS;
import static com.tecxis.resume.domain.Constants.CLIENT_ARVAL_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_AXELTIS_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_EULER_HERMES_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_HERMES_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_LA_BANQUE_POSTALE_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_MICROPOLE_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_SAGEMCOM_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_SG_ID;
import static com.tecxis.resume.domain.Constants.DCSC;
import static com.tecxis.resume.domain.Constants.EOLIS;
import static com.tecxis.resume.domain.Constants.EUROCLEAR_VERS_CALYPSO;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.FRANCE_ID;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.MANCHESTER;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.PARIS_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_AOS_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_CENTRE_DES_COMPETENCES_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_EOLIS_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_EUROCLEAR_VERS_CALYPSO_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_MORNINGSTAR_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_MORNINGSTAR_V2_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_PARCOURS_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_SELENIUM_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_TED_V1_ID;
import static com.tecxis.resume.domain.Constants.SELENIUM;
import static com.tecxis.resume.domain.Constants.SHERPA;
import static com.tecxis.resume.domain.Constants.SWINDON;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Constants.VERSION_2;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_COMPOSITE_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.deleteCityInJpa;
import static com.tecxis.resume.domain.util.Utils.insertCityInJpa;
import static com.tecxis.resume.domain.util.Utils.isCityValid;
import static com.tecxis.resume.domain.util.Utils.isCountryValid;
import static com.tecxis.resume.domain.util.Utils.setCityLocationsInJpa;
import static com.tecxis.resume.domain.util.Utils.setLondonToFranceInJpa;
import static com.tecxis.resume.domain.util.Utils.updateParisLocationInJpa;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED) //this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class CityTest {

	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCity() {
		/**Insert Country*/
		Country belgium = Utils.insertCountry(BELGIUM, entityManager);
		
		insertCityInJpa(insertCityFunction->{
			/**Insert City*/
			City brussels = new City();
			brussels.setName(BRUSSELS);				
			brussels.setCountry(belgium);
			entityManager.persist(brussels);
			entityManager.flush();	//manually commit the transaction	
			entityManager.clear(); //Detach managed entities from persistence context to reload new changes
		}, entityManager, jdbcTemplateProxy);
	
		/**Validate City was inserted*/
		City brussels = cityRepo.getCityByName(BRUSSELS);
		assertEquals(SUCCESS, Utils.isCityValid(brussels, BRUSSELS, BELGIUM, new ArrayList<Location> ()));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Country belgium = Utils.insertCountry(BELGIUM, entityManager);
		City city = Utils.insertCity(BRUSSELS, belgium, entityManager);
		assertThat(city.getId().getCityId(), Matchers.greaterThan((long)0));		
	}

	@Test
	public void testSetId() {
		City city = new City();
		assertEquals(0L, city.getId().getCityId());
		city.getId().setCityId(1L);
		assertEquals(1L, city.getId().getCityId());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToOne_GetCountry() {
		/**Find City*/
		City london = cityRepo.getCityByName(LONDON);		
		/**Validate City -> Country*/
		assertEquals(UNITED_KINGDOM, london.getCountry().getName());
		
		/**Find City*/
		City paris = cityRepo.getCityByName(PARIS);		
		/**Validate City -> Country*/
		assertEquals(FRANCE, paris.getCountry().getName());
				
		/**Find brussels*/
		City brussels = cityRepo.getCityByName(BRUSSELS);		
		/**Validate City -> Country*/
		assertEquals(BELGIUM, brussels.getCountry().getName());
	}

	/**See equivalent unit test in ContractTest.testSetClientWithOrmOrhpanRemoval */
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToOne_Update_Country_And_RemoveOrhpansWithOrm() {		
		/**Find new country to set*/
		Country france = countryRepo.getCountryByName(FRANCE);
		assertEquals(FRANCE, france.getName()); 
//		assertEquals(1, france.getCities().size()); // test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
		
		
		/**Find target City*/
		City oldLondon = cityRepo.getCityByName(LONDON);
		assertNotNull(oldLondon);
		assertEquals(UNITED_KINGDOM, oldLondon.getCountry().getName());		
		
		CityId newCityId = new CityId();
		newCityId.setCityId(oldLondon.getId().getCityId()); //sets old id to the new City
		newCityId.setCountryId(france.getId());
		
		setLondonToFranceInJpa(setCountryInCity -> {
			/**Create new City with new host Country*/
			City newLondon =  new City();		
			newLondon.setId(newCityId);		
			newLondon.setName(oldLondon.getName());           
//			newLondon.setLocations(currentLondon.getLocations()); //Cannot set locations for the new City. Setting the new City with references to old Locations generates redundant SQL insert of "oldLondon" City.			
		
			/**Remove old and create new City*/
			entityManager.remove(oldLondon);
			entityManager.flush();           //DELETE statements are executed right at the end of the flush while the INSERT statements are executed towards the beginning. We need to manually flush the delete transaction. In this functional case this isn't a code smell. because we're changing the City's foreign key (not an attribute). For more info about Hibernate flush operation order read this article: https://vladmihalcea.com/hibernate-facts-knowing-flush-operations-order-matters/   
			entityManager.persist(newLondon);	
			entityManager.flush();			//Manually commit the transaction
			entityManager.clear();
			
			
		}, entityManager, jdbcTemplateProxy);		
	
			
		/**Find old city*/
		CityId oldCityId = oldLondon.getId();
		assertFalse(cityRepo.findById(oldCityId).isPresent());
		/**Find new City*/
		City londonFrance = cityRepo.findById(newCityId).get();
		assertNotNull(londonFrance);
		/**Test new City has 0 locations */
		assertEquals(SUCCESS, isCityValid(londonFrance, LONDON, FRANCE, new ArrayList<Location> () ));
		/**Test Country has now Paris and London*/
		france = countryRepo.getCountryByName(FRANCE);
		assertEquals(2, france.getCities().size());
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(SUCCESS, isCountryValid(france, FRANCE, List.of(paris, londonFrance)));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_AddLocation() {
		/**Find & validate city to test*/
		City london = cityRepo.getCityByName(LONDON);	
		assertEquals(UNITED_KINGDOM, london.getCountry().getName());
		assertEquals(2, london.getLocations().size());
				
		/**Find & validate country to insert*/
		Country france = countryRepo.getCountryByName(FRANCE);
		assertEquals(FRANCE, france.getName());
		assertEquals(1, france.getCities().size());
		
		/**Find & validate project to insert*/
		Client belfius = clientRepo.getClientByName(BELFIUS);
		assertNotNull(belfius);
		assertEquals(BELFIUS, belfius.getName());
		Project  sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);		
		assertNotNull(sherpa);		
		assertEquals(SHERPA, sherpa.getName());
		assertEquals(1, sherpa.getLocations().size());
				
		/**Validate pre-test state of Location*/
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		LocationId locationId = new LocationId(london.getId(), sherpa.getId());
		assertFalse(locationRepo.findById(locationId).isPresent());
		
		/**Validate state of current City -> Locations */
		List <Location> londonLocations = london.getLocations();
		assertEquals(2, londonLocations.size());		
		Location fortisLocation = locationRepo.findById(new LocationId(london.getId(), projectRepo.findByNameAndVersion(FORTIS, VERSION_1).getId())).get();
		Location dcscLocation = locationRepo.findById(new LocationId(london.getId(), projectRepo.findByNameAndVersion(DCSC, VERSION_1).getId())).get();
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
		assertEquals(15, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(3, london.getLocations().size());
		assertEquals(2, sherpa.getLocations().size());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_AddExistingLocation() {
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(MANCHESTER);		
		assertEquals(UNITED_KINGDOM, manchester.getCountry().getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(1, manchesterLocations.size());
		
		/**Validate projects of the city to test*/		
		Project currentAdir = manchesterLocations.get(0).getProject();
		assertNotNull(currentAdir);
		assertEquals(ADIR, currentAdir.getName());
		assertEquals(1, currentAdir.getLocations().size());
		
		/**Find duplicate Project to insert*/
		Project duplicateAdir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertNotNull(duplicateAdir);
		assertEquals(ADIR, duplicateAdir.getName());
		assertEquals(1, duplicateAdir.getLocations().size());
		
		/**Test that duplicate project exists in the list of Manchester-ADIR locations*/
		List <Location> manchesterAdirLocations = manchester.getLocations();
		Location duplicateAdirLocations =  duplicateAdir.getLocations().get(0);
		assertTrue(manchesterAdirLocations.contains(duplicateAdirLocations));
		
		/**Add Project duplicate to city: expect error*/
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(duplicateAdir, currentAdir);
		
		/**Create duplicate location*/
		Location newLocation = new Location (manchester, duplicateAdir);
		
		/**Add duplicate Location*/
		manchester.addLocation(newLocation);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveLocation() {
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(UNITED_KINGDOM, manchester.getCountry().getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(1, manchesterLocations.size());
		
		/**Validate projects of the city to test*/
		assertEquals(1, manchesterLocations.size());
		Project currentAdir = manchesterLocations.get(0).getProject();
		assertNotNull(currentAdir);
		assertEquals(ADIR, currentAdir.getName());
		assertEquals(1, currentAdir.getLocations().size());
		
		/**Remove the Location*/
		Location manchesterLocation = manchester.getLocations().get(0);
		assertTrue(manchester.removeLocation(manchesterLocation));
		assertTrue(currentAdir.removeLocation(manchesterLocation));
		
		/**Find the Location*/
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		entityManager.merge(manchester);
		entityManager.merge(currentAdir);			
		entityManager.flush();
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(0, manchester.getLocations().size());
		assertEquals(0, currentAdir.getLocations().size());
		LocationId locaitonId = new LocationId(manchester.getId(), currentAdir.getId());
		assertFalse(locationRepo.findById(locaitonId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveLocation_by_Project() {
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(UNITED_KINGDOM, manchester.getCountry().getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(1, manchesterLocations.size());
		
		/**Validate projects of the city to test*/
		assertEquals(1, manchesterLocations.size());
		Project currentAdir = manchesterLocations.get(0).getProject();
		assertNotNull(currentAdir);
		assertEquals(ADIR, currentAdir.getName());
		assertEquals(1, currentAdir.getLocations().size());
		
		/**Remove the Location*/
		Location manchesterLocation = manchester.getLocations().get(0);
		assertTrue(manchester.removeLocation(currentAdir));
		assertTrue(currentAdir.removeLocation(manchesterLocation));
		
		/**Find the Location*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		entityManager.merge(manchester);
		entityManager.merge(currentAdir);			
		entityManager.flush();
		SchemaUtils.testStateAfterManchesterCityDeleteAdirProject(jdbcTemplateProxy);
		
		assertEquals(0, manchester.getLocations().size());
		assertEquals(0, currentAdir.getLocations().size());
		LocationId locaitonId = new LocationId(manchester.getId(), currentAdir.getId());
		assertFalse(locationRepo.findById(locaitonId).isPresent());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveLocation_by_Unrelated_Project() {
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(UNITED_KINGDOM, manchester.getCountry().getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(1, manchesterLocations.size());
		
		/**Validate projects of the city to test*/
		assertEquals(1, manchesterLocations.size());
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1); //query a non-related project		
		assertNotNull(eolis);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(1, eolis.getLocations().size());
		
		/**Remove the Location*/
		Location manchesterLocation = manchester.getLocations().get(0);
		assertFalse(manchester.removeLocation(eolis));
		assertFalse(eolis.removeLocation(manchesterLocation));
		
		/**Location was not removed*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		entityManager.merge(manchester);
		entityManager.merge(eolis);			
		entityManager.flush();
		SchemaUtils.testStateAfterManchesterByUnrelatedProject(jdbcTemplateProxy);
			
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_GetLocations() {
		/**Find & validate city to test*/
		City london = cityRepo.getCityByName(LONDON);
		assertEquals(UNITED_KINGDOM, london.getCountry().getName());
		List <Location> londonLocations = london.getLocations();
		assertEquals(2, londonLocations.size());
		
		/**Validate Locations*/		
		assertEquals(london, londonLocations.get(0).getCity());
		assertEquals(london, londonLocations.get(1).getCity());		
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		Project dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);
		assertThat(londonLocations.get(0).getProject(), Matchers.oneOf(fortis, dcsc));
		assertThat(londonLocations.get(1).getProject(), Matchers.oneOf(fortis, dcsc));	
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_SetLocations() {
		/**Find & validate City to test*/
		final City london = cityRepo.getCityByName(LONDON);
		assertEquals(UNITED_KINGDOM, london.getCountry().getName());
		List <Location> londonLocations = london.getLocations();
		assertEquals(2, londonLocations.size());
		/**Validate opposite associations*/
		Location location1 = london.getLocations().get(0);
		Location location2 = london.getLocations().get(1);		
		assertEquals(london, location1.getCity());
		assertEquals(london, location2.getCity());		
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		Project dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);		
		assertThat(location1.getProject(), Matchers.oneOf(fortis, dcsc));
		assertThat(location2.getProject(), Matchers.oneOf(fortis, dcsc));		
		
	
		/**Find & validate Projects to test*/
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());		
		assertEquals(AOS, aos.getName());
		assertEquals(VERSION_1, aos.getVersion());		
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		/**Validate opposite association*/
		City paris = cityRepo.getCityByName(PARIS);
		City swindon = cityRepo.getCityByName(SWINDON);		
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
		fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);
		assertThat(londonLocations.get(0).getProject(), Matchers.oneOf(fortis, dcsc));
		assertThat(londonLocations.get(1).getProject(), Matchers.oneOf(fortis, dcsc));	
		
		/**Prepare new Locations*/
		Location londonSeleniumLocation =  new Location (london, selenium);
		Location londonAosLocation = new Location(london, aos);
		Location londonMorningstarv2Location = new Location(london, morningstarv2);
		List <Location>  newLocations = List.of(londonSeleniumLocation,		
												londonAosLocation,
												londonMorningstarv2Location);
				
		/**Set new Locations*/
		setCityLocationsInJpa( setCityLocations->{
			london.setLocations(newLocations);
			assertEquals(3, london.getLocations().size());
			entityManager.merge(london);
			entityManager.flush();
			entityManager.clear();
		}, entityManager, jdbcTemplateProxy);
		
		/**Validate new City*/
		City newLondon = cityRepo.getCityByName(LONDON);
		assertEquals(SUCCESS, isCityValid(newLondon, LONDON, UNITED_KINGDOM, newLocations));
		
		/**Test the opposite association*/
		selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		/**Test selenium Project has all Cities*/ //Don't use ProjectValidator. We only want to test City -> Project assoc. 
		assertEquals(2, selenium.getLocations().size());
		paris = cityRepo.getCityByName(PARIS);
		assertThat(selenium.getLocations().get(0).getCity(), Matchers.oneOf(paris, newLondon));
		assertThat(selenium.getLocations().get(1).getCity(), Matchers.oneOf(paris, newLondon));
		/**Test aos Project has all Cities*/ //Don't use ProjectValidator. We only want to test City -> Project assoc. 
		assertEquals(3, aos.getLocations().size());
		swindon = cityRepo.getCityByName(SWINDON);
		assertThat(aos.getLocations().get(0).getCity(), Matchers.oneOf(paris, newLondon, swindon));
		assertThat(aos.getLocations().get(1).getCity(), Matchers.oneOf(paris, newLondon, swindon));
		assertThat(aos.getLocations().get(2).getCity(), Matchers.oneOf(paris, newLondon, swindon));
		/**Test morningstar v2 Project has all Cities*/		//Don't use ProjectValidator. We only want to test City -> Project assoc. 
		assertEquals(2, morningstarv2.getLocations().size());
		assertThat(morningstarv2.getLocations().get(0).getCity(), Matchers.oneOf(paris, newLondon));
		assertThat(morningstarv2.getLocations().get(1).getCity(), Matchers.oneOf(paris, newLondon));
	}	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_Update_Locations_And_RemoveOrphansWithOrm() {
		/**Find Project */
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Find a City*/		
		City paris = cityRepo.getCityByName(PARIS);
		/**Validate City before test*/
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
		assertEquals(SUCCESS, Utils.isCityValid(paris, PARIS, FRANCE, morningstarv1AxeltisLocations));
		
		/**Find a Location*/
		Location morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris.getId(), morningstartV1Project.getId())).get();
		updateParisLocationInJpa( setLocationFunction -> {				
				assertTrue(paris.removeLocation(morningstartV1ProjectLocation)); //Update and remove 1 location 
				assertTrue(morningstartV1Project.removeLocation(morningstartV1ProjectLocation));				
				entityManager.merge(morningstartV1Project);
				entityManager.merge(paris);
				entityManager.flush();
				entityManager.clear();
			},entityManager, jdbcTemplateProxy);
		
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
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Locations_And_RemoveOrphansWithOrm_NullSet() {		
		/**Find Project */
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Find a City*/		
		City paris = cityRepo.getCityByName(PARIS);
		/**Validate City before test*/
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
		assertEquals(SUCCESS, Utils.isCityValid(paris, PARIS, FRANCE, morningstarv1AxeltisLocations));
		Utils.setParisLocationAndRemoveOphansInJpa( SetCityWithNullLocationFunction -> {				
				paris.setLocations(null);
				entityManager.merge(paris);
				entityManager.flush();
				entityManager.clear();
			},entityManager, jdbcTemplateProxy);
		
		/**Validate City after test*/		
		morningstarv1AxeltisLocations = new ArrayList <Location> (); //Paris City has no Locations. 
		City newParis = cityRepo.getCityByName(PARIS);
		assertEquals(SUCCESS, Utils.isCityValid(newParis, PARIS, FRANCE, morningstarv1AxeltisLocations)); 
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveCity() {
		/**Find City to remove*/
		City london = cityRepo.getCityByName(LONDON);		
		
		/**Validate association City -> Country */
		assertEquals(LONDON,  london.getName());
		assertEquals(UNITED_KINGDOM, london.getCountry().getName());
		
		 /**Validate association Country -> City*/
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		assertEquals(UNITED_KINGDOM, uk.getName());
		assertEquals(3, uk.getCities().size());
		assertThat(uk.getCities(), Matchers.hasItems(london));
		
		/**Remove city*/	
		deleteCityInJpa(deleteCityFunction-> {
			/**Detach entities*/
			entityManager.clear();
			
			/**Find City to remove*/
			City londonOld = cityRepo.getCityByName(LONDON);
			
			entityManager.remove(londonOld);
			entityManager.flush();
			entityManager.clear();	
		}, entityManager, jdbcTemplateProxy);
		
		
		/**Test city was removed*/
		assertNull(cityRepo.getCityByName(LONDON));
		uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		assertEquals(UNITED_KINGDOM, uk.getName());
		assertEquals(2, uk.getCities().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_GetProjects() {
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_AddProject() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Country uk = Utils.insertCountry(UNITED_KINGDOM, entityManager);
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client belfius = Utils.insertClient(BELFIUS, entityManager);
		Project sherpaProject = Utils.insertProject(SHERPA, VERSION_1, belfius, entityManager);
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);
		Project morningStarV1Project = Utils.insertProject(MORNINGSTAR, VERSION_1, axeltis, entityManager);
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
								
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));		
		City london = Utils.insertCity(LONDON, uk, entityManager);
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
				
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));	
		/**Set London city to Adir project*/
		assertTrue(adirProject.addCity(london));		
		entityManager.merge(london);
		entityManager.flush();	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		/**Set London city to Morningstar project*/
		assertTrue(morningStarV1Project.addCity(london));
		entityManager.merge(london);
		entityManager.flush();	
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		/**Set London city to Sherpa project*/
		assertTrue(sherpaProject.addCity(london));
		entityManager.merge(london);	
		entityManager.flush();		
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		/**Set Paris city to Adir project*/
		assertTrue(adirProject.addCity(paris));
		entityManager.merge(paris);
		entityManager.flush();	
		assertEquals(4, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		/**Set Paris city to Sherpa project*/
		assertTrue(sherpaProject.addCity(paris));
		entityManager.merge(paris);
		entityManager.flush();	
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
			
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_ManyToMany_RemoveProject() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));		
		Country UK = Utils.insertCountry(UNITED_KINGDOM, entityManager);
		Country france = Utils.insertCountry(FRANCE, entityManager);
		Client belfius = Utils.insertClient(BELFIUS, entityManager);
		Project sherpaProject = Utils.insertProject(SHERPA, VERSION_1, belfius, entityManager);
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);
		Project morningStarV1Project = Utils.insertProject(MORNINGSTAR, VERSION_1, axeltis, entityManager);
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);			
		City london = Utils.insertCity(LONDON, UK, entityManager);		
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));	
		
		adirProject.addCity(london);
		morningStarV1Project.addCity(london);
		sherpaProject.addCity(london);
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		/**Set London city to Adir project*/
		List <City> adirCityList = List.of (london);		
		adirProject.setCities(adirCityList);
		/**Set London & Paris cities to Morningstar project*/
		List <City> morningStarV1CityList = List.of(
											london, 		
											paris);
		morningStarV1Project.setCities(morningStarV1CityList);
		/**Set London & Paris cities to Sherpa project*/
		List <City> sherpaCityList = List.of(
										london,	
										paris);	
		sherpaProject.setCities(sherpaCityList);		
		entityManager.merge(adirProject);	
		entityManager.merge(morningStarV1Project);
		entityManager.merge(sherpaProject);
		entityManager.flush();		
		assertEquals(5, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
				
		assertTrue(adirProject.removeCity(london));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(2, morningStarV1Project.getCities().size());
		assertEquals(2, sherpaProject.getCities().size());
		entityManager.merge(adirProject);
		entityManager.flush();		
		assertEquals(4, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));	


		assertTrue(morningStarV1Project.removeCity(london));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(1, morningStarV1Project.getCities().size());
		assertEquals(2, sherpaProject.getCities().size());
		entityManager.merge(morningStarV1Project);
		entityManager.flush();		
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		assertTrue(sherpaProject.removeCity(london));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(1, morningStarV1Project.getCities().size());
		assertEquals(1, sherpaProject.getCities().size());
		entityManager.merge(sherpaProject);
		entityManager.flush();	
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));		
		
		assertTrue(morningStarV1Project.removeCity(paris));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(0, morningStarV1Project.getCities().size());
		assertEquals(1, sherpaProject.getCities().size());
		entityManager.merge(morningStarV1Project);
		entityManager.flush();		
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		assertTrue(sherpaProject.removeCity(paris));
		assertEquals(0, adirProject.getCities().size());
		assertEquals(0, morningStarV1Project.getCities().size());
		assertEquals(0, sherpaProject.getCities().size());
		entityManager.merge(sherpaProject);
		entityManager.flush();	
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));

		
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
		assertThat(city.toString()).matches(DEFAULT_ENTITY_WITH_COMPOSITE_ID_REGEX);
		
	}
	

}
