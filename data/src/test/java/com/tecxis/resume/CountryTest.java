package com.tecxis.resume;

import static com.tecxis.resume.persistence.CountryRepositoryTest.*;
import static com.tecxis.resume.persistence.CityRepositoryTest.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.persistence.CityRepository;
import com.tecxis.resume.persistence.CountryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CountryTest {
	
	private static Logger log = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private CityRepository cityRepo;
	
	@Test
	public void testGetCountryId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetCities() {
		/**Find Cities to test*/		
		City london = cityRepo.getCityByName(LONDON);
		City manchester = cityRepo.getCityByName(MANCHESTER);
		City swindon = cityRepo.getCityByName(SWINDON);
		City paris = cityRepo.getCityByName(PARIS);		
		
		/**Find a Country to test*/
		Country france = countryRepo.getCountryByName(FRANCE);
		assertEquals(FRANCE, france.getName());
		assertEquals(1, france.getCities().size());
		assertEquals(paris, france.getCities().get(0));		
		
		/**Find another Country to test*/
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		assertEquals(UNITED_KINGDOM, uk.getName());
		assertEquals(3, uk.getCities().size());
		assertThat(uk.getCities().get(0), Matchers.oneOf(london, manchester, swindon));
		assertThat(uk.getCities().get(1), Matchers.oneOf(london, manchester, swindon));
		assertThat(uk.getCities().get(2), Matchers.oneOf(london, manchester, swindon));
		
	}

	@Test
	public void testSetCities() {
		log.info("Country -> City association is managed through of the relationship owner (City).");
		//To update the Cities in a Country see CityTest.testSetCountry()			
	}

	@Test
	public void testAddCity() {
		log.info("Country -> City association is managed through of the relationship owner (City).");
		//To update a City in a Country see CityTest.testSetCountry()
	}

	@Test
	public void testRemoveCity() {
		log.info("Country -> City association is managed through of the relationship owner (City).");
		//To remove or update a City in a Country see CityTest.testSetCountry()
		//The mappedBy is set in the inverse side of the association (non-owing). Removing the Country (non-owing) works when cascading strategy is set to ALL (in the Country). 
		// Delete operation cascades to the owing association, in this case is City. 
		// Whilst cascading strategy is not set to ALL an attempt, delete of a Country will throw a ConstraintViolationException
	}

	public static Country insertACountry(String name, EntityManager entityManager) {
		Country country = new Country();
		country.setName(name);
		assertEquals(0, country.getId());
		entityManager.persist(country);		
		entityManager.flush();
		assertThat(country.getId(), Matchers.greaterThanOrEqualTo((long)0));
		return country;
	}

}
