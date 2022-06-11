package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.AOS;
import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.BRUSSELS;
import static com.tecxis.resume.domain.Constants.DCSC;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.SELENIUM;
import static com.tecxis.resume.domain.Constants.SWINDON;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Constants.VERSION_2;
import static com.tecxis.resume.domain.SchemaUtils.testInitialState;
import static com.tecxis.resume.domain.util.Utils.insertCityInJpa;
import static com.tecxis.resume.domain.util.Utils.isCityValid;
import static com.tecxis.resume.domain.util.Utils.setBrusslesToFranceInJpa;
import static com.tecxis.resume.domain.util.Utils.setCityLocationsInJpa;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

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
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
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
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void test_ManyToOne_SaveCountry_WithOrmOrhpanRemoval() {		
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
		
		setBrusslesToFranceInJpa(setCountryInCity -> {
			City newCity = new City();
			newCity.setId(newCityId);
//			newCity.setLocations(brussels.getLocations()); //Cannot set locations for the new City. Setting the new City with references to old Locations generates redundant SQL insert of "old brussels" City. 
			newCity.setName(brussels.getName());
			
			/**Remove old and create new City*/
			cityRepo.delete(brussels);
			cityRepo.flush();           //DELETE statements are executed right at the end of the flush while the INSERT statements are executed towards the beginning. We need to manually flush the delete transaction. In this functional case this isn't a code smell. because we're changing the City's foreign key (not an attribute). For more info about Hibernate flush operation order read this article: https://vladmihalcea.com/hibernate-facts-knowing-flush-operations-order-matters/   
			cityRepo.save(newCity);	
			cityRepo.flush();			//Manually commit the transaction
			entityManager.clear();
			
			
		}, cityRepo, jdbcTemplateProxy);
		
		/**Find old City*/
		CityId oldCityId = brussels.getId();		
		assertFalse(cityRepo.findById(oldCityId).isPresent());			
		/**Find new city with 0 locations*/
		City brusselsFrance = cityRepo.findById(newCityId).get();
		assertNotNull(brusselsFrance);
		assertEquals(SUCCESS, Utils.isCityValid(brusselsFrance, BRUSSELS, FRANCE, new ArrayList <Location> ()));		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void test_OneToMany_SaveLocations() {
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
		List <Location>  newLocations = new  ArrayList<>();
		newLocations.add(londonSeleniumLocation);		
		newLocations.add(londonAosLocation);
		newLocations.add(londonMorningstarv2Location);
				
		/**Set new Locations*/
		setCityLocationsInJpa( setCityLocations->{
			london.setLocations(newLocations);
			assertEquals(3, london.getLocations().size());
			entityManager.merge(london);
			entityManager.flush();
			entityManager.clear();
		}, cityRepo, jdbcTemplateProxy);
		
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
	
	@Sql(
		scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testAdd() {
		/**Insert Country*/
		Country belgium = Utils.insertCountry(BELGIUM, entityManager);
		
		insertCityInJpa(insertCityFunction->{
			/**Insert City*/
			City brussels = new City();
			brussels.setName(BRUSSELS);				
			brussels.setCountry(belgium);
			cityRepo.save(brussels);
			cityRepo.flush();	//manually commit the transaction	
			entityManager.clear(); //Detach managed entities from persistence context to reload new changes
		}, cityRepo, jdbcTemplateProxy);
	
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
		Utils.deleteCityInJpa(deleteCityFunction-> {
			/**Detach entities*/
			entityManager.clear();
			
			/**Find City to remove*/
			City londonOld = cityRepo.getCityByName(LONDON);
			
			entityManager.remove(londonOld);
			entityManager.flush();
			entityManager.clear();	
		}, cityRepo, jdbcTemplateProxy);
		
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
	
}
