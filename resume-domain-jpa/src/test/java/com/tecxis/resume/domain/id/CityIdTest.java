package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityIdTest {
	
	private final  Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testToString() {
		CityId cityId = new CityId();
		LOG.debug(DEFAULT_ID_REGEX);
		assertThat(cityId.toString()).matches(DEFAULT_ID_REGEX);
	}
	
	@Test
	public void testEquals() {
		CityId cityId1 = new CityId();
		CityId cityId2 = new CityId();
		assertTrue(cityId1.equals(cityId2));
		
		cityId1 = new CityId(4,1);
		cityId2 = new CityId(4,1);
		assertTrue(cityId1.equals(cityId2));
		
		cityId1 = new CityId(4,1);
		cityId2 = new CityId(1,4);
		assertFalse(cityId1.equals(cityId2));
	}	
	
	@Test
	public void testSetGetCityId() {
		CityId cityId = new CityId();
		assertEquals(cityId.getCityId(), 0);
		cityId.setCityId(1);
		assertEquals(cityId.getCityId(), 1);
		
	}
	
	@Test
	public void testSetGetCountryId() {
		CityId cityId = new CityId();
		assertEquals(cityId.getCountryId(), 0);
		cityId.setCountryId(1);
		assertEquals(cityId.getCountryId(), 1);
		
	}
	
	@Test
	public void testContructors() {
		CityId cityId = new CityId();
		assertEquals(cityId.getCityId(), 0);
		assertEquals(cityId.getCountryId(), 0);
		
		cityId = new CityId(4,1);
		assertEquals(cityId.getCityId(), 4);
		assertEquals(cityId.getCountryId(), 1);
	}
	

	@Test
	public void testHashCode() {
		CityId cityId1 = new CityId();
		CityId cityId2 = new CityId();
		assertThat(cityId1.hashCode()).isGreaterThan(1);
		assertEquals(cityId1.hashCode(), cityId2.hashCode());
		
		cityId1 = new CityId(4, 1);
		cityId2 = new CityId(4, 1);
		assertThat(cityId1.hashCode()).isGreaterThan(1);
		assertEquals(cityId1.hashCode(), cityId2.hashCode());
		
		cityId1 = new CityId(4, 1);
		cityId2 = new CityId(1, 4);
		assertThat(cityId1.hashCode()).isGreaterThan(1);
		assertNotEquals(cityId1.hashCode(), cityId2.hashCode());
		
	}
	

}
