package com.tecxis.resume.domain.id;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CityIdTest {

	@Test
	public void testToString() {
		CityId cityId = new CityId();
		cityId.toString();
	}
	
	@Test
	public void testEquals() {
		CityId cityId1 = new CityId();
		CityId cityId2 = new CityId();
		assertTrue(cityId1.equals(cityId2));
	}
	
	@Test
	public void testEqualsWithContructor() {
		CityId cityId1 = new CityId(4,1);
		CityId cityId2 = new CityId(4,1);
		assertTrue(cityId1.equals(cityId2));
	}
	
	@Test
	public void testEqualsWithContructor2() {
		CityId cityId1 = new CityId(4,1);
		CityId cityId2 = new CityId(1,4);
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
	public void testContructor() {
		CityId cityId = new CityId(4,1);
		assertEquals(cityId.getCityId(), 4);
		assertEquals(cityId.getCountryId(), 1);
	}
	
	@Test
	public void testHashCodeDefaultConstructor() {
		CityId cityId = new CityId();
		assertThat(cityId.hashCode()).isGreaterThan(1);
	}

	@Test
	public void testHashCode() {
		CityId cityId = new CityId(4,1);
		assertThat(cityId.getCityId()).isGreaterThan(1);
	}
	
	@Test
	public void testComapreHashCode() {
		CityId cityId1 = new CityId();
		CityId cityId2 = new CityId();
		assertEquals(cityId1.hashCode(), cityId2.hashCode());
		
		cityId1 = new CityId(4, 1);
		cityId2 = new CityId(4, 1);
		assertEquals(cityId1.hashCode(), cityId2.hashCode());
		
		cityId1 = new CityId(4, 1);
		cityId2 = new CityId(1, 4);
		assertNotEquals(cityId1.hashCode(), cityId2.hashCode());
		
	}
	

}
