package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class LocationTest {
	
	
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Sql(
			scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
	@Test
	public void testInsertLocation() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		assertEquals(1L, france.getId().longValue());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		assertEquals(1L, paris.getId().getCityId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveLocation() {		
		/**Find Project */
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Find a City*/		
		City paris = cityRepo.getCityByName(PARIS);
		
		/**Find a Location*/
		Location morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris.getId(), morningstartV1Project.getId())).get();
	
		/**Test Location*/
		assertEquals(paris, morningstartV1ProjectLocation.getCity());
		assertEquals(morningstartV1Project, morningstartV1ProjectLocation.getProject());
		
		/**Detach entities*/		
		entityManager.clear();
		
		/**Find Location to remove again*/
		morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		paris = cityRepo.getCityByName(PARIS);
		morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris.getId(), morningstartV1Project.getId())).get();
		
		
		/**Remove location*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		entityManager.remove(morningstartV1ProjectLocation);
		entityManager.flush();
		entityManager.clear();
		
		/**Test */		
		SchemaUtils.testStateAfterMorningstartV1ProjectLocationDelete(jdbcTemplateProxy);
		
	}
	
	@Test
	public void testToString() {
		Location location = new Location();
		assertThat(location.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}
	
	public void test_ManyToOne_SetCity() {
		//TODO
	}
	
	public void test_ManyToOne_SetProject() {
		//TODO
	}
}
