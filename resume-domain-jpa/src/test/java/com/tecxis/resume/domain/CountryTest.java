package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class CountryTest {
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private Validator validator;
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Country country = Utils.insertCountry("United Kingdom", entityManager);
		assertThat(country.getId(), Matchers.greaterThan((long)0));
		
	}
	
	@Test
	public void testSetId() {
		Country country = new Country();
		assertEquals(0L, country.getId().longValue());
		country.setId(1L);
		assertEquals(1L, country.getId().longValue());		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetName() {
		Country france = countryRepo.getCountryByName(FRANCE);
		assertEquals(FRANCE, france.getName());
	}
	
	@Test
	public void testSetName() {
		Country country = new Country();
		assertNull(country.getName());
		country.setName(BELGIUM);
		assertEquals(BELGIUM, country.getName());	
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_GetCities() {
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
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_SetCities(){//Impl RES-44
		/**Fetch country to test*/
		Country france = countryRepo.getCountryByName(FRANCE);
		/**Fetch cities to test*/
		City paris = cityRepo.getCityByName(PARIS);

		/**Validate Country*/
		isCountryValid(france, FRANCE, List.of(paris));

		setCountryCitiesAndRemoveOrphansInJpa( em -> {
					/**Build and create new Cities*/
					City bordeaux = buildCity(buildCityId(BORDEAUX_ID, france.getId()), BORDEAUX);
					City lyon = buildCity(buildCityId(LYON_ID, france.getId()), LYON);
					em.persist(bordeaux);
					em.persist(lyon);
					em.flush();
				},
				em -> {
					City bordeaux = cityRepo.getCityByName(BORDEAUX);
					City lyon = cityRepo.getCityByName(LYON);
					List <City> newCities = List.of(bordeaux, lyon);
					france.setCities(newCities);
					em.merge(france);
					em.flush();
					em.clear();
			}, entityManager, jdbcTemplateProxy, SchemaUtils::testInitialState, SchemaUtils::testStateAfter_FranceCountry_Cities_Update);

		/**Test Country with new locations*/
		paris = cityRepo.getCityByName(PARIS);
		City bordeaux = buildCity(buildCityId(BORDEAUX_ID, france.getId()), BORDEAUX);
		City lyon = buildCity(buildCityId(LYON_ID, france.getId()), LYON);
		/**Validate Country*/
		isCountryValid(france, FRANCE, List.of(bordeaux, lyon));
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_AddCity() {		
		Country france = countryRepo.getCountryByName(FRANCE);
		france.addCity(new City());	
		//To update a City in a Country see CityTest.testSetCountry()
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_RemoveCity() {
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
		Country country = new Country();		
		assertThat(country.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Cities_And_RemoveOrhpansWithOrm(){
		//TODO RES-44
	}
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Cities_And_RemoveOrhpansWithOrm_NullSet(){
		//TODO RES-44
	}

}
