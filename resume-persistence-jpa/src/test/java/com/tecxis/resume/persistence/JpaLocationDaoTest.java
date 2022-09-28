package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
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
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.util.Utils.updateLocationInJpa;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.SchemaUtils;
import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaLocationDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
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
		
		updateLocationInJpa( setLocationFunction -> {				
				assertTrue(paris.removeLocation(morningstartV1ProjectLocation));
				assertTrue(morningstartV1Project.removeLocation(morningstartV1ProjectLocation));		
				SchemaUtils.testInitialState(jdbcTemplateProxy);
				locationRepo.save(morningstartV1ProjectLocation);				
				locationRepo.flush();			
			},locationRepo, jdbcTemplateProxy);
		
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
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		assertEquals(1, france.getId().longValue());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(1, paris.getId().getCityId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		Location adirLocationIn = Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		
		LocationId adirLocationId = new LocationId(paris.getId(), adirProject.getId());
		Location adirLocationOut = locationRepo.findById(adirLocationId).get();
		assertEquals(adirLocationIn, adirLocationOut);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		assertEquals(1, france.getId().longValue());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(1, paris.getId().getCityId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		Location adirLocation = Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		
		/**Delete location*/
		entityManager.remove(adirLocation);
		entityManager.flush();
		/**Verify location doesn't exist*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(14, locationRepo.count());
		List <Location> locations = locationRepo.findAll();
		assertEquals(14, locations.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Location> pageableLocation = locationRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableLocation.getSize());
	}
	
	@Test
	public void test_ManyToOne_SaveCity() {
		org.junit.Assert.fail("TODO");
	}	
	
	@Test
	public void test_ManyToOne_SaveProject() {
		org.junit.Assert.fail("TODO");
	}
}
