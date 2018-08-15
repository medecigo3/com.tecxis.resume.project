package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.CountryRepositoryTest.*;
import static com.tecxis.resume.persistence.CountryRepositoryTest.FRANCE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.UNITED_KINGDOM;
import static com.tecxis.resume.persistence.CountryRepositoryTest.insertACountry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
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
import com.tecxis.resume.CityPK;


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
	private static long FRANCE_ID;
	private static long UK_ID;
	private static long BELGIUM_ID;

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
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		insertACountry("France", countryRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		insertACountry("United Kingdom", countryRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		insertACountry("Belgium", countryRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		UK_ID = countryRepo.getCountryByName(UNITED_KINGDOM).getId();
		FRANCE_ID = countryRepo.getCountryByName(FRANCE).getId();
		BELGIUM_ID = countryRepo.getCountryByName(BELGIUM).getId();
	}



	@After
	public void cleanup() {
		cityRepo.deleteAll();
	}
	
	
	@Sql(
		scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		insertACity(LONDON, UK_ID, cityRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		insertACity(PARIS, FRANCE_ID, cityRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));
		insertACity(BRUSSELS, BELGIUM_ID, cityRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CITY_TABLE));
	}
	
	@Sql(
	    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	public void shouldBeAbleToFindInsertedCity() {
		City cityIn = insertACity(BRUSSELS, BELGIUM_ID, cityRepo, entityManager);
		City cityOut = cityRepo.getCityByName(BRUSSELS);
		assertEquals(cityIn, cityOut);
	}
	
	
//	@Test
//	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
//	public void testDeleteCityByName() {
//		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
//	}
	
	
	public static City insertACity(String name, long countryId, CityRepository cityRepo, EntityManager entityManager) {
		CityPK cityPk = new CityPK();
		cityPk.setCountryId(countryId);
		assertEquals(0, cityPk.getCityId());		
		City city = new City();
		city.setName(name);
		city.setId(cityPk);		
		cityRepo.save(city);
		assertNotNull(city.getId());
		entityManager.flush();
		return city;
		
	}
	

}
