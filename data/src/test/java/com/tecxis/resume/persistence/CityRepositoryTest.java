package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.CountryRepositoryTest.BELGIUM;
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
	
	public static final String BRUSSELS = "Brussels";
	public static final String PARIS = "Paris";
	public static final String LONDON = "London";

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private CountryRepository countryRepo;
	
	
	@Before
	public void setUpTestDataWithinTransaction() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "Country"));
		insertACountry("France", countryRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, "COUNTRY"));
		insertACountry("United Kingdom", countryRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, "COUNTRY"));
		insertACountry("Belgium", countryRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, "COUNTRY"));
	}



	@After
	public void cleanup() {
		cityRepo.deleteAll();
	}
	
	
	@Sql(
		    scripts = "classpath:SQL/ResumeSchema.sql",
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "City"));
		insertACity(LONDON, countryRepo.getCountryByName(UNITED_KINGDOM).getId(), cityRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, "City"));
		insertACity(PARIS, countryRepo.getCountryByName(FRANCE).getId(), cityRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, "City"));
		insertACity(BRUSSELS, countryRepo.getCountryByName(BELGIUM).getId(), cityRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, "City"));
	}
	
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
