package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.CityTest.insertACity;
import static org.junit.Assert.assertEquals;
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

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class LocationTest {
	
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Sql(
			scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
	@Test
	public void testInsertLocation() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		Country france = CountryTest.insertACountry(Constants.FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.COUNTRY_TABLE));
		assertEquals(1, france.getId());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		City paris = insertACity(Constants.PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(1, paris.getId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		LocationTest.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveLocation() {		
		/**Find Project */
		Project morningstartV1Project = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_1);
		assertEquals(Constants.MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(Constants.VERSION_1, morningstartV1Project.getVersion());	
		
		/**Find a City*/		
		City paris = cityRepo.getCityByName(Constants.PARIS);
		
		/**Find a Location*/
		Location morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris, morningstartV1Project)).get();
	
		/**Test Location*/
		assertEquals(paris, morningstartV1ProjectLocation.getCity());
		assertEquals(morningstartV1Project, morningstartV1ProjectLocation.getProject());
		
		/**Detach entities*/		
		entityManager.clear();
		
		/**Find Location to remove again*/
		morningstartV1Project = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_1);
		paris = cityRepo.getCityByName(Constants.PARIS);
		morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris, morningstartV1Project)).get();
		
		
		/**Remove location*/
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		entityManager.remove(morningstartV1ProjectLocation);
		entityManager.flush();
		entityManager.clear();
		
		/**Test */		
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.CITY_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		
	}
	
	@Test
	public void testToString() {
		Location location = new Location();
		location.toString();
	}	
	
	public static Location insertLocation(City city, Project project, EntityManager entityManager) {		
		Location location = new Location(city, project);
		entityManager.persist(location);
		entityManager.flush();
		assertThat(location.getCity().getId(), Matchers.greaterThan((long)0));
		assertThat(location.getProject().getId(), Matchers.greaterThan((long)0));
		return location;
				
	}

}
