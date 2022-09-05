package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.Constants.CLIENT_AXELTIS_ID;
import static com.tecxis.resume.domain.Constants.CLIENT_SAGEMCOM_ID;
import static com.tecxis.resume.domain.Constants.FRANCE_ID;
import static com.tecxis.resume.domain.Constants.LONDON_ID;
import static com.tecxis.resume.domain.Constants.PARIS_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_EOLIS_V1_ID;
import static com.tecxis.resume.domain.Constants.PROJECT_MORNINGSTAR_V1_ID;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM_ID;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class LocationIdTest {
	
	@Test
	public void testLocationIdToString() {
		LocationId locationId = new LocationId();
		locationId.toString();
		assertThat(locationId.toString()).matches(DEFAULT_ID_REGEX);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testEquals() {
	{	/**Test same LocationId instances*/	
		CityId cityId1 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 1	
		ProjectId projectId1 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);		
		LocationId locationId1 = Utils.buildLocationId(cityId1, projectId1);		
		LocationId test = locationId1; //Same City instances			
		assertTrue(locationId1.equals(test));
	}
	{	/**Tests different LocationId instances with same primary keys*/			
		CityId cityId1 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 1	
		ProjectId projectId1 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);		
		LocationId locationId1 = Utils.buildLocationId(cityId1, projectId1);
		
		CityId cityId2 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 1	
		ProjectId projectId2 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);			
		LocationId locationId2 = new LocationId(cityId2, projectId2); //Paris LocationId instance 2
		assertTrue(cityId1.equals(cityId2));
		assertTrue(projectId1.equals(projectId2));
		assertTrue(locationId1.equals(locationId2));	
	}
	{	
		/**Tests LocationId instances with different CityId*/				
		CityId cityId1 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 1	
		ProjectId projectId1 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);				
		LocationId locationId1 = Utils.buildLocationId(cityId1, projectId1);  //Paris LocationId instance 1	
		
		CityId cityId2 = Utils.buildCityId(LONDON_ID, FRANCE_ID); //'paris' instance 2	
		ProjectId projectId2 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);			
		LocationId locationId2 = new LocationId(cityId2, projectId2); //Paris LocationId instance 2	
		assertFalse(cityId1.equals(cityId2));
		assertTrue(projectId1.equals(projectId2));
		assertFalse(locationId1.equals(locationId2));	
	}
	{	
		/**Tests LocationId instances with different CountryId*/				
		CityId cityId1 = Utils.buildCityId(PARIS_ID, UNITED_KINGDOM_ID); //'paris' instance 1	
		ProjectId projectId1 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);				
		LocationId locationId1 = Utils.buildLocationId(cityId1, projectId1);  //Paris LocationId instance 1	
		
		CityId cityId2 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 2	
		ProjectId projectId2 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);			
		LocationId locationId2 = new LocationId(cityId2, projectId2); //Paris LocationId instance 2
		assertFalse(cityId1.equals(cityId2));
		assertTrue(projectId1.equals(projectId2));
		assertFalse(locationId1.equals(locationId2));
		
	}
	{
		/**Tests LocationId instances with different ClientId*/
		CityId cityId1 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 1	
		ProjectId projectId1 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);				
		LocationId locationId1 = Utils.buildLocationId(cityId1, projectId1);  //Paris LocationId instance 1	
		
		CityId cityId2 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 2	
		ProjectId projectId2 = Utils.buildProjectId(CLIENT_SAGEMCOM_ID, PROJECT_MORNINGSTAR_V1_ID);			
		LocationId locationId2 = new LocationId(cityId2, projectId2); //Paris LocationId instance 2
		assertTrue(cityId1.equals(cityId2));
		assertFalse(projectId1.equals(projectId2));
		assertFalse(locationId1.equals(locationId2));
		
	}
	{
		/**Tests LocationId instances with different ProjectId*/
		CityId cityId1 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 1	
		ProjectId projectId1 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_MORNINGSTAR_V1_ID);				
		LocationId locationId1 = Utils.buildLocationId(cityId1, projectId1);  //Paris LocationId instance 1	
		
		CityId cityId2 = Utils.buildCityId(PARIS_ID, FRANCE_ID); //'paris' instance 2	
		ProjectId projectId2 = Utils.buildProjectId(CLIENT_AXELTIS_ID, PROJECT_EOLIS_V1_ID);			
		LocationId locationId2 = new LocationId(cityId2, projectId2); //Paris LocationId instance 2
		assertTrue(cityId1.equals(cityId2));
		assertFalse(projectId1.equals(projectId2));
		assertFalse(locationId1.equals(locationId2));
		
	}
	}

}
