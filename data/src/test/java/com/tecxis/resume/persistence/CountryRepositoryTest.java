package com.tecxis.resume.persistence;

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
import org.springframework.test.context.transaction.BeforeTransaction;
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
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@BeforeTransaction
	void verifyInitialDatabaseState() {
		// logic to verify the initial state before a transaction is started

		 
	}

	@Before
	public void setUpTestDataWithinTransaction() {
	}



	@After
	public void cleanup() {
	}

	@Test
	@Sql("classpath:SQL/ResumeSchema.sql")
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "Country"));
		insertACountry("France");
		assertEquals(1, countRowsInTable(jdbcTemplate, "COUNTRY"));
		insertACountry("United Kingdom");
		assertEquals(2, countRowsInTable(jdbcTemplate, "COUNTRY"));
		insertACountry("Belgium");
		assertEquals(3, countRowsInTable(jdbcTemplate, "COUNTRY"));
		
	}
	
	@Test
	public void shouldBeAbleToFindInsertedCountry() {
		Country countryIn = insertACountry("France");
		Country countryOut = countryRepo.getCountryByCountryId(countryIn.getCountryId());
		assertEquals(countryIn, countryOut);
	}
	
	@Test
	@Sql("classpath:SQL/ResumeSchema.sql")
	@Sql(scripts="classpath:SQL/ResumeData.sql", executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountryById() {
		Country uk = countryRepo.getCountryByName("United Kingdom");
		assertNotNull(uk);
	}
	
	private Country insertACountry(String name) {
		Country country = new Country();
		country.setName(name);
		assertEquals(0, country.getCountryId());
		countryRepo.save(country);
		assertNotNull(country.getCountryId());
		entityManager.flush();
		return country;
	}
	

}
