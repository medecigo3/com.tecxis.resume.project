package com.tecxis.resume;

import static com.tecxis.resume.persistence.CityRepositoryTest.LONDON;
import static com.tecxis.resume.persistence.CityRepositoryTest.MANCHESTER;
import static com.tecxis.resume.persistence.CityRepositoryTest.PARIS;
import static com.tecxis.resume.persistence.CityRepositoryTest.SWINDON;
import static com.tecxis.resume.persistence.CountryRepositoryTest.FRANCE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.UNITED_KINGDOM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
		"classpath:test-transaction-context.xml",
		"classpath:validation-api-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CountryTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private Validator validator;
	
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetCities() {
		Country france = countryRepo.getCountryByName(FRANCE);
		france.setCities(new ArrayList<City> ());		
		//To update the Cities in a Country see CityTest.testSetCountry()			
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddCity() {		
		Country france = countryRepo.getCountryByName(FRANCE);
		france.addCity(new City());	
		//To update a City in a Country see CityTest.testSetCountry()
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveCity() {
		Country france = countryRepo.getCountryByName(FRANCE);
		france.removeCity(new City());	
		//To remove or update a City in a Country see CityTest.testSetCountry()
		//The mappedBy is set in the inverse side of the association (non-owing). To remove the non-owing (Country), the parent (Country) has to have the cascading strategy set to REMOVE.
		//The remove SQL operation cascades to the owing association, in this case the City. 
		//Whilst cascading strategy is not set to REMOVE an attempt, delete of a Country will throw a ConstraintViolationException
		
	}
	
	@Test
	public void testNameIsNotNull() {
		Country country = new Country();
		Set<ConstraintViolation<Country>> violations = validator.validate(country);
        assertFalse(violations.isEmpty());
		
	}
	
	@Test
	public void testToString() {
		fail("TODO");
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
