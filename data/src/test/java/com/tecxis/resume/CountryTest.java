package com.tecxis.resume;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class CountryTest {

	@Test
	public void testGetCountryId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCities() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCities() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCity() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveCity() {
		fail("Not yet implemented");
	}

	public static Country insertACountry(String name, EntityManager entityManager) {
		Country country = new Country();
		country.setName(name);
		assertEquals(0, country.getCountryId());
		entityManager.persist(country);		
		entityManager.flush();
		assertThat(country.getCountryId(), Matchers.greaterThanOrEqualTo((long)0));
		return country;
	}

}
