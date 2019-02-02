package com.tecxis.resume;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ServiceTest {

	@Test
	public void testGetServiceId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContracts() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetContracts() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddContract() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveContract() {
		fail("Not yet implemented");
	}

	public static Service insertAService(String name, EntityManager entityManager) {
		Service service = new Service();
		service.setName(name);		
		assertEquals(0, service.getServiceId());
		entityManager.persist(service);
		entityManager.flush();
		assertThat(service.getServiceId(), Matchers.greaterThan((long)0));
		return service;
	}

}
