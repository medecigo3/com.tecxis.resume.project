package com.tecxis.resume;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class InterestTest {

	@Test
	public void testGetInterestId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDesc() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetStaff() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetStaff() {
		fail("Not yet implemented");
	}

	public static Interest insertAnInterest(String desc, EntityManager entityManager) {
		Interest interest = new Interest();
		interest.setDesc(desc);
		assertEquals(0, interest.getId());
		entityManager.persist(interest);
		entityManager.flush();
		assertThat(interest.getId(), Matchers.greaterThan((long)0));
		return interest;
	}

}
