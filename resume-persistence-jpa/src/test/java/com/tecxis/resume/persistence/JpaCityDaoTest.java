package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.BRUSSELS;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
import static com.tecxis.resume.domain.util.Utils.insertCityInJpa;
import static com.tecxis.resume.domain.util.Utils.setBrusslesToFranceInJpa;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
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
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testSave_UpdateCountry_WithOrmOrhpanRemoval() {		
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
		/**Find new city*/
		City brusselsFrance = cityRepo.findById(newCityId).get();
		assertNotNull(brusselsFrance);
		assertEquals(SUCCESS, Utils.isCityValid(brusselsFrance, BRUSSELS, FRANCE));		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testSave_UpdateLocation() {
		//TODO
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
	
		/**Validate City was inserted*/
		City brussels = cityRepo.getCityByName(BRUSSELS);
		assertEquals(SUCCESS, Utils.isCityValid(brussels, BRUSSELS, BELGIUM));
	
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Country uk = Utils.insertCountry(UNITED_KINGDOM, entityManager);
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		City tempCity = Utils.insertCity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		cityRepo.delete(tempCity);
		assertNull(cityRepo.getCityByName(LONDON));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
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
