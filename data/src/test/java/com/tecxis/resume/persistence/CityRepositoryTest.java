package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.CountryRepositoryTest.insertACountry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Before;
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

import com.tecxis.resume.City;
import com.tecxis.resume.Country;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CityRepositoryTest {
	
	
	public static final String CITY_TABLE = "CITY";
	public static final String BRUSSELS = "Brussels";
	public static final String PARIS = "Paris";
	public static final String LONDON = "London";
	private static Country FRANCE;
	private static Country UK;
	private static Country BELGIUM;
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	

	@Before
	public void setUpTestData() {
		Country uk = countryRepo.getCountryByName(CountryRepositoryTest.UNITED_KINGDOM);
		if (uk != null)
			UK = uk;
		else
			UK = insertACountry("United Kingdom", entityManager);
		
		Country france = countryRepo.getCountryByName(CountryRepositoryTest.FRANCE);
		if (france != null) 
			FRANCE = france;
		else
			FRANCE = insertACountry("France", entityManager);
		
		Country belgium = countryRepo.getCountryByName(CountryRepositoryTest.BELGIUM);
		if (belgium != null)
			BELGIUM = belgium;
		else
			BELGIUM = insertACountry("Belgium", entityManager);		

	}
	
	
	@Sql(
		scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City london = insertACity(LONDON, UK, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(1, london.getCityId());
		
		City paris = insertACity(PARIS, FRANCE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(2, paris.getCityId());
		
		City brussels = insertACity(BRUSSELS, BELGIUM, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(3, brussels.getCityId());
	}
	
	@Sql(
	    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	public void shouldBeAbleToFindInsertedCity() {
		City cityIn = insertACity(BRUSSELS, BELGIUM, entityManager);
		City cityOut = cityRepo.getCityByName(BRUSSELS);		
		assertEquals(cityIn, cityOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindCityByName() {
		City london = cityRepo.getCityByName(LONDON);
		assertNotNull(london);
		assertEquals(LONDON, london.getName());
		City paris = cityRepo.getCityByName(PARIS);
		assertNotNull(paris);
		assertEquals(PARIS, paris.getName());
		City brussels = cityRepo.getCityByName(BRUSSELS);
		assertNotNull(brussels);
		assertEquals(BRUSSELS, brussels.getName());
	}
		
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteCity() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City tempCity = insertACity(LONDON, UK, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		cityRepo.delete(tempCity);
		assertNull(cityRepo.getCityByName(LONDON));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
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
