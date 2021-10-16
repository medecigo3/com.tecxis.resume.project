package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.FRANCE;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaCountryDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CountryRepository countryRepo;
	
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		assertEquals(1, france.getId().longValue());
		
		Country uk = Utils.insertCountry(UNITED_KINGDOM, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		assertEquals(2, uk.getId().longValue());
		
		Country belgium = Utils.insertCountry(BELGIUM, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		assertEquals(3, belgium.getId().longValue());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		Country countryIn = Utils.insertCountry(FRANCE, entityManager);
		Country countryOut = countryRepo.getCountryById(countryIn.getId());
		assertEquals(countryIn, countryOut);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));	
		Country tempCountry = Utils.insertCountry("temp", entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		countryRepo.delete(tempCountry);
		assertNull(countryRepo.getCountryByName("temp"));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Country> countries = countryRepo.findAll();
		assertEquals(3, countries.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Country> pageableCountry = countryRepo.findAll(PageRequest.of(1, 1)); 
		assertEquals(1, pageableCountry.getSize());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCountryById() {
		Country country = countryRepo.getCountryById(1L);
		assertEquals(1L, country.getId().longValue());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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

}
