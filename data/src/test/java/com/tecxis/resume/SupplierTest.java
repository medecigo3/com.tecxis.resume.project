package com.tecxis.resume;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class SupplierTest {

	@Test
	public void testGetSupplierId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStaffId() {
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

	public static Supplier insertASupplier(Staff staff, String name, EntityManager entityManager) {
		Supplier supplier = new Supplier();
		supplier.setName(name);
		supplier.setStaffId(staff.getStaffId());
		entityManager.persist(supplier);
		entityManager.flush();
		assertThat(supplier.getSupplierId(), Matchers.greaterThan((long)0));
		return supplier;
	}

}
