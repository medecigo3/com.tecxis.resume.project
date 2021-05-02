package com.tecxis.resume.domain.repository;

import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.BRUSSELS;
import static com.tecxis.resume.domain.Constants.CITY_TABLE;
import static com.tecxis.resume.domain.Constants.COUNTRY_TABLE;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class JpaCityDaoTest {
	
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
		Country uk = Utils.insertACountry(UNITED_KINGDOM, entityManager);
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City london = Utils.insertACity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(1, london.getId());
		
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country france = Utils.insertACountry(FRANCE, entityManager);
		City paris = Utils.insertACity(PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(2, paris.getId());
		
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country belgium = Utils.insertACountry(BELGIUM, entityManager);		
		City brussels = Utils.insertACity(BRUSSELS, belgium, entityManager);
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
		Country belgium = Utils.insertACountry(BELGIUM, entityManager);
		City cityIn = Utils.insertACity(BRUSSELS, belgium, entityManager);
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
		Country uk = Utils.insertACountry(UNITED_KINGDOM, entityManager);
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City tempCity = Utils.insertACity(LONDON, uk, entityManager);
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
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <City> pageableCity = cityRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableCity.getSize());
	}
}
