package com.tecxis.resume.persistence;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

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

import com.tecxis.resume.City;
import com.tecxis.resume.Constants;
import com.tecxis.resume.Country;
import com.tecxis.resume.CountryTest;


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
	
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testShouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		Country france = CountryTest.insertACountry(Constants.FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(1, france.getId());
		
		Country uk = CountryTest.insertACountry(Constants.UNITED_KINGDOM, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(2, uk.getId());
		
		Country belgium = CountryTest.insertACountry(Constants.BELGIUM, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(3, belgium.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void shouldBeAbleToFindInsertedCountry() {
		Country countryIn = CountryTest.insertACountry(Constants.FRANCE, entityManager);
		Country countryOut = countryRepo.getCountryById(countryIn.getId());
		assertEquals(countryIn, countryOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountryByName() {
		Country uk = countryRepo.getCountryByName(Constants.UNITED_KINGDOM);
		assertNotNull(uk);
		assertEquals(Constants.UNITED_KINGDOM, uk.getName());
		Country france = countryRepo.getCountryByName(Constants.FRANCE);
		assertNotNull(france);
		assertEquals(Constants.FRANCE, france.getName());
		Country belgium = countryRepo.getCountryByName(Constants.BELGIUM);
		assertNotNull(belgium);
		assertEquals(Constants.BELGIUM, belgium.getName());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteCountryById() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));	
		Country tempCountry = CountryTest.insertACountry("temp", entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		countryRepo.delete(tempCountry);
		assertNull(countryRepo.getCountryByName("temp"));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountyCities() {
		Country uk = countryRepo.getCountryByName(Constants.UNITED_KINGDOM);
		assertNotNull(uk);
		assertEquals(Constants.UNITED_KINGDOM, uk.getName());
		assertEquals(3, uk.getCities().size());
		City city1 = uk.getCities().get(0);
		assertNotNull(city1);
		assertThat(city1.getName(), Matchers.oneOf(Constants.LONDON, Constants.MANCHESTER, Constants.SWINDON));
		City city2 = uk.getCities().get(1);
		assertThat(city2.getName(), Matchers.oneOf(Constants.LONDON, Constants.MANCHESTER, Constants.SWINDON));
		City city3 = uk.getCities().get(2);
		assertThat(city3.getName(), Matchers.oneOf(Constants.LONDON, Constants.MANCHESTER, Constants.SWINDON));
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountryByCountryId() {
		Country country = countryRepo.getCountryById(1L);
		assertEquals(1L, country.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Country> countries = countryRepo.findAll();
		assertEquals(3, countries.size());
	}
	

}
