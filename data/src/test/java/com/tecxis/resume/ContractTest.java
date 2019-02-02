package com.tecxis.resume;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ContractTest {

	@Test
	public void testGetClientId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSupplierId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetServiceId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContractId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStaffId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEndDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStartDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetServices() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetServices() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddService() {
		fail("Not yet implemented");
	}

	public static Contract insertAContract(Client client, Supplier supplier, Date startDate, Date endDate, EntityManager entityManager) {
		Contract contract  = new Contract();
		contract.setClient(client);	
		contract.setSupplier(supplier);
		contract.setStartDate(startDate);
		contract.setEndDate(endDate);
		entityManager.persist(contract);
		entityManager.flush();
		assertThat(contract.getContractId(), Matchers.greaterThan((long)0));
		return contract;
		
	}

}
