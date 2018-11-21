package com.tecxis.resume;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ClientTest {

	@Test
	public void testGetClientId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWebsite() {
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

	@Test
	public void testGetProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveProject() {
		fail("Not yet implemented");
	}

	public static Client insertAClient(String name, EntityManager entityManager) {
		Client client = new Client();
		client.setName(name);
		assertEquals(0, client.getClientId());
		entityManager.persist(client);		
		entityManager.flush();
		assertThat(client.getClientId(), Matchers.greaterThan((long)0));
		return client;
		
	}

}
