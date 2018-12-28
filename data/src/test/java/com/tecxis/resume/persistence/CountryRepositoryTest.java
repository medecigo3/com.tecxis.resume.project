package com.tecxis.resume.persistence;
import static com.tecxis.resume.persistence.CityRepositoryTest.LONDON;
import static com.tecxis.resume.persistence.CityRepositoryTest.MANCHESTER;
import static com.tecxis.resume.persistence.CityRepositoryTest.SWINDON;
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
		Country france = CountryTest.insertACountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(1, france.getCountryId());
		
		Country uk = CountryTest.insertACountry(UNITED_KINGDOM, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(2, uk.getCountryId());
		
		Country belgium = CountryTest.insertACountry(BELGIUM, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(3, belgium.getCountryId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void shouldBeAbleToFindInsertedCountry() {
		Country countryIn = CountryTest.insertACountry(FRANCE, entityManager);
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
		Country tempCountry = CountryTest.insertACountry("temp", entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		countryRepo.delete(tempCountry);
		assertNull(countryRepo.getCountryByName("temp"));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountyCities() {
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		assertNotNull(uk);
		assertEquals(UNITED_KINGDOM, uk.getName());
		assertEquals(3, uk.getCities().size());
		City city1 = uk.getCities().get(0);
		assertNotNull(city1);
		assertThat(city1.getName(), Matchers.oneOf(LONDON, MANCHESTER, SWINDON));
		City city2 = uk.getCities().get(1);
		assertThat(city2.getName(), Matchers.oneOf(LONDON, MANCHESTER, SWINDON));
		City city3 = uk.getCities().get(2);
		assertThat(city3.getName(), Matchers.oneOf(LONDON, MANCHESTER, SWINDON));
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountryByCountryId() {
		Country country = countryRepo.getCountryByCountryId(1L);
		assertEquals(1L, country.getCountryId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Country> countries = countryRepo.findAll();
		assertEquals(3, countries.size());
	}
	

}
