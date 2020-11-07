package com.tecxis.resume;

import static com.tecxis.resume.CityTest.insertACity;
import static com.tecxis.resume.persistence.CityRepositoryTest.CITY_TABLE;
import static com.tecxis.resume.persistence.CityRepositoryTest.PARIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.CountryRepositoryTest.COUNTRY_TABLE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.FRANCE;
import static com.tecxis.resume.persistence.LocationRepositoryTest.LOCATION_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
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

import com.tecxis.commons.persistence.id.LocationId;
import com.tecxis.resume.persistence.CityRepository;
import com.tecxis.resume.persistence.LocationRepository;
import com.tecxis.resume.persistence.ProjectRepository;

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
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country france = CountryTest.insertACountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(1, france.getId());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City paris = insertACity(PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(1, paris.getId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		LocationTest.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveLocation() {		
		/**Find Project */
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Find a City*/		
		City paris = cityRepo.getCityByName(PARIS);
		
		/**Find a Location*/
		Location morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris, morningstartV1Project)).get();
	
		/**Test Location*/
		assertEquals(paris, morningstartV1ProjectLocation.getLocationId().getCity());
		assertEquals(morningstartV1Project, morningstartV1ProjectLocation.getLocationId().getProject());
		
		/**Detach entities*/		
		entityManager.clear();
		
		/**Find Location to remove again*/
		morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		paris = cityRepo.getCityByName(PARIS);
		morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris, morningstartV1Project)).get();
		
		
		/**Remove location*/
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		entityManager.remove(morningstartV1ProjectLocation);
		entityManager.flush();
		entityManager.clear();
		
		/**Test */		
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}
	
	@Test
	public void testToString() {
		Location location = new Location();
		location.toString();
	}	
	
	public static Location insertLocation(City city, Project project, EntityManager entityManager) {
		LocationId locationId = new LocationId(city, project);
		Location location = new Location(locationId);
		entityManager.persist(location);
		entityManager.flush();
		assertThat(location.getLocationId().getCity().getId(), Matchers.greaterThan((long)0));
		assertThat(location.getLocationId().getProject().getId(), Matchers.greaterThan((long)0));
		return location;
				
	}

}
