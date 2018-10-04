package com.tecxis.resume.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
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

import com.tecxis.resume.Country;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CountryRepositoryTest {
	
	public static final String COUNTRY_TABLE = "COUNTRY";
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
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country france = insertACountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(1, france.getCountryId());
		
		Country uk = insertACountry(UNITED_KINGDOM, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(2, uk.getCountryId());
		
		Country belgium = insertACountry(BELGIUM, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(3, belgium.getCountryId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void shouldBeAbleToFindInsertedCountry() {
		Country countryIn = insertACountry(FRANCE, entityManager);
		Country countryOut = countryRepo.getCountryByCountryId(countryIn.getCountryId());
		assertEquals(countryIn, countryOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
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
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteCountryById() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		Country tempCountry = insertACountry("temp", entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		countryRepo.delete(tempCountry);
		assertNull(countryRepo.getCountryByName("temp"));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
	}
	
	
	public static Country insertACountry(String name, EntityManager entityManager) {
		Country country = new Country();
		country.setName(name);
		assertEquals(0, country.getCountryId());
		entityManager.persist(country);		
		entityManager.flush();
		assertThat(country.getCountryId(), Matchers.greaterThanOrEqualTo((long)0));
		return country;
	}
	

}
