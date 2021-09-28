package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.City.CITY_TABLE;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Country.COUNTRY_TABLE;
import static com.tecxis.resume.domain.Location.LOCATION_TABLE;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static org.junit.Assert.assertEquals;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
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
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_UNCOMMITTED)
@SqlConfig(dataSource="dataSource")
public class JpaLocationDaoTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertLocationRowsAndSetId() {
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(1, france.getId().longValue());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(1, paris.getId().getCityId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedLocation() {
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(1, france.getId().longValue());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(1, paris.getId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Location adirLocationIn = Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		LocationId adirLocationId = new LocationId(paris.getId(), adirProject.getId());
		Location adirLocationOut = locationRepo.findById(adirLocationId).get();
		assertEquals(adirLocationIn, adirLocationOut);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDeleteLocation() {
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		/**Insert Country*/
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country france = Utils.insertCountry(FRANCE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(1, france.getId().longValue());
		
		/**Insert City*/
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		City paris = Utils.insertCity(PARIS, france, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(1, paris.getId());
		
		/**Insert Project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adirProject.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Insert Location*/
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Location adirLocation = Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		/**Delete location*/
		entityManager.remove(adirLocation);
		entityManager.flush();
		/**Verify location doesn't exist*/
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(14, locationRepo.count());
		List <Location> locations = locationRepo.findAll();
		assertEquals(14, locations.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Location> pageableLocation = locationRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableLocation.getSize());
	}
}
