package com.tecxis.resume.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.Country;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CountryRepositoryTest {
	
	public static final String BELGIUM = "Belgium";
	public static final String FRANCE = "France";
	public static final String UNITED_KINGDOM = "United Kingdom";

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CountryRepository countryRepo;
	
	

	@Test
	@Sql("classpath:SQL/ResumeSchema.sql")
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "Country"));
		insertACountry(FRANCE, countryRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, "COUNTRY"));
		insertACountry(UNITED_KINGDOM, countryRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, "COUNTRY"));
		insertACountry(BELGIUM, countryRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, "COUNTRY"));
		
	}
	
	@Test
	public void shouldBeAbleToFindInsertedCountry() {
		Country countryIn = insertACountry(FRANCE, countryRepo, entityManager);
		Country countryOut = countryRepo.getCountryById(countryIn.getId());
		assertEquals(countryIn, countryOut);
	}
	
	@Test
	@Sql("classpath:SQL/ResumeSchema.sql")
	@Sql(scripts="classpath:SQL/CreateResumeData.sql", executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountryByName() {
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		assertNotNull(uk);
		assertEquals(UNITED_KINGDOM, uk.getName());
		Country france = countryRepo.getCountryByName(FRANCE);
		assertNotNull(france);
		assertEquals(FRANCE, france.getName());
		Country belgium = countryRepo.getCountryByName(BELGIUM);
		assertNotNull(belgium);
		assertEquals(BELGIUM, belgium.getName());
	}
	
	@Test
	@Sql("classpath:SQL/ResumeSchema.sql")
	public void testDeleteCountryById() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "Country"));	
		Country tempCountry = insertACountry("temp", countryRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, "Country"));
		countryRepo.delete(tempCountry);
		assertNull(countryRepo.getCountryByName("temp"));
		assertEquals(0, countRowsInTable(jdbcTemplate, "Country"));
	}
	
	
	public static Country insertACountry(String name, CountryRepository countryRepo, EntityManager entityManager) {
		Country country = new Country();
		country.setName(name);
		assertEquals(0, country.getId());
		countryRepo.save(country);
		assertNotNull(country.getId());
		entityManager.flush();
		return country;
	}
	

}
