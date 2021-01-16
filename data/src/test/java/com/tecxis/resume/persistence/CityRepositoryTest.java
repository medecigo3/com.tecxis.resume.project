package com.tecxis.resume.persistence;

import static com.tecxis.resume.CityTest.insertACity;
import static com.tecxis.resume.Constants.BELGIUM;
import static com.tecxis.resume.Constants.BRUSSELS;
import static com.tecxis.resume.Constants.CITY_TABLE;
import static com.tecxis.resume.Constants.COUNTRY_TABLE;
import static com.tecxis.resume.Constants.FRANCE;
import static com.tecxis.resume.Constants.LONDON;
import static com.tecxis.resume.Constants.PARIS;
import static com.tecxis.resume.Constants.UNITED_KINGDOM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.tecxis.resume.CountryTest;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CityRepositoryTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Sql(
		scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country uk = CountryTest.insertACountry(UNITED_KINGDOM, entityManager);
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City london = insertACity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(1, london.getId());
		
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country france = CountryTest.insertACountry(FRANCE, entityManager);
		City paris = insertACity(PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(2, paris.getId());
		
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country belgium = CountryTest.insertACountry(BELGIUM, entityManager);		
		City brussels = insertACity(BRUSSELS, belgium, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(3, brussels.getId());
	}
	
	@Sql(
	    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Test
	public void shouldBeAbleToFindInsertedCity() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country belgium = CountryTest.insertACountry(BELGIUM, entityManager);
		City cityIn = insertACity(BRUSSELS, belgium, entityManager);
		City cityOut = cityRepo.getCityByName(BRUSSELS);		
		assertEquals(cityIn, cityOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCityByName() {
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
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country uk = CountryTest.insertACountry(UNITED_KINGDOM, entityManager);
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City tempCity = insertACity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		cityRepo.delete(tempCity);
		assertNull(cityRepo.getCityByName(LONDON));
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <City> allCities = cityRepo.findAll();
		assertNotNull(allCities);
		assertEquals(5, allCities.size());
	}
	

}
