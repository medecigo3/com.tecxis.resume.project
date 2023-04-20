package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.BRUSSELS;
import static com.tecxis.resume.domain.Constants.CITY_PARIS_TOTAL_LOCATIONS;
import static com.tecxis.resume.domain.Constants.CLIENT_ARVAL_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_AXELTIS_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_EULER_HERMES_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_HERMES_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_LA_BANQUE_POSTALE_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_MICROPOLE_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_SAGEMCOM_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_SG_ID;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.FRANCE_ID;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
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
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.SchemaUtils.testInitialState;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaCityDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void test_ManyToOne_Update_Country_And_RemoveOrphansWithOrm() {
		/**Find new country to set*/
		Country france = countryRepo.getCountryByName(FRANCE);
		assertNotNull(france);
//		assertEquals(1, france.getCities().size()); // test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
		
		/**Find target City*/	
		City brussels = cityRepo.getCityByName(BRUSSELS);
		assertNotNull(brussels);
		assertEquals(BRUSSELS, brussels.getName());			
//		assertEquals(1, brussels.getLocations().size()); // test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
//		assertEquals(1, brussels.getProjects().size()); // test commented out due un-scheduling entity deletion (DefaultPersistEventListener)
		
		/**Create new City ID*/
		CityId newCityId = new CityId();
		newCityId.setCityId(brussels.getId().getCityId());
		newCityId.setCountryId(france.getId());
		
		update_CityBrussles_With_Country_InJpa(cityRepo-> {
			City newCity = new City();
			newCity.setId(newCityId);
//			newCity.setLocations(brussels.getLocations()); //Cannot set locations for the new City. Setting the new City with references to old Locations generates redundant SQL insert of "old brussels" City. 
			newCity.setName(brussels.getName());
			
			/**Remove old and create new City*/
			cityRepo.delete(brussels);
			cityRepo.flush();           //DELETE statements are executed right at the end of the flush while the INSERT statements are executed towards the beginning. We need to manually flush the delete transaction. In this functional case this isn't a code smell. because we're changing the City's foreign key (not an attribute). For more info about Hibernate flush operation order read this article: https://vladmihalcea.com/hibernate-facts-knowing-flush-operations-order-matters/   
			cityRepo.save(newCity);	
			cityRepo.flush();			//Manually commit the transaction
		}, cityRepo, jdbcTemplateProxy);

		entityManager.clear();
		/**Find old City*/
		CityId oldCityId = brussels.getId();		
		assertFalse(cityRepo.findById(oldCityId).isPresent());			
		/**Find new city with 0 locations*/
		City brusselsFrance = cityRepo.findById(newCityId).get();
		assertNotNull(brusselsFrance);
		assertEquals(SUCCESS, Utils.isCityValid(brusselsFrance, BRUSSELS, FRANCE, new ArrayList <Location> ()));		
	}

	@Sql(
		scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testAdd() {
		/**Insert Country*/
		Country belgium = Utils.insertCountry(BELGIUM, entityManager);
		
		insertCityInJpa(cityRepo ->{
			/**Insert City*/
			City brussels = new City();
			brussels.setName(BRUSSELS);				
			brussels.setCountry(belgium);
			cityRepo.save(brussels);
			cityRepo.flush();	//manually commit the transaction	

		}, cityRepo, jdbcTemplateProxy);

		entityManager.clear(); //Detach managed entities from persistence context to reload new changes
		/**Validate City was inserted with no locations*/
		City brussels = cityRepo.getCityByName(BRUSSELS);
		assertEquals(SUCCESS, Utils.isCityValid(brussels, BRUSSELS, BELGIUM, new ArrayList<Location> ()));
	
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" })
	public void testDelete() {
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
		testInitialState(jdbcTemplateProxy);
		Utils.deleteCityInJpa(cityRepo-> {
			/**Detach entities*/
			entityManager.clear();
			
			/**Find City to remove*/
			City londonOld = cityRepo.getCityByName(LONDON);
			
			cityRepo.delete(londonOld);
			cityRepo.flush();
		}, cityRepo, jdbcTemplateProxy);

		entityManager.clear();
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
	public void testFindAll(){
		List <City> allCities = cityRepo.findAll();
		assertNotNull(allCities);
		assertEquals(5, allCities.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <City> pageableCity = cityRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableCity.getSize());
	}
	
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
		update_CityParis_With_Locations_InJpa( (cityRepo, projectRepo) -> {
				assertTrue(paris.removeLocation(morningstartV1ProjectLocation)); //Update and remove 1 location 
				assertTrue(morningstartV1Project.removeLocation(morningstartV1ProjectLocation));				
				projectRepo.save(morningstartV1Project);
				cityRepo.save(paris);
				cityRepo.flush();

			},cityRepo, projectRepo, jdbcTemplateProxy);

		entityManager.clear();
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
		update_CityParis_With_NullLocations_InJpa(cityRepo -> {
				paris.setLocations(null);
				cityRepo.save(paris);
				cityRepo.flush();				
			},cityRepo, jdbcTemplateProxy);
		
		entityManager.clear();
		/**Validate City after test*/		
		morningstarv1AxeltisLocations = new ArrayList <Location> (); //Paris City has no Locations. 
		City newParis = cityRepo.getCityByName(PARIS);
		assertEquals(SUCCESS, Utils.isCityValid(newParis, PARIS, FRANCE, morningstarv1AxeltisLocations));
	}
	
}
