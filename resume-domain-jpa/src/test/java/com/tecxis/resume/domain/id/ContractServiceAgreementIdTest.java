package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_NESTED_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ContractServiceAgreementIdTest {

	@Test
	public void testToString() {
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		assertThat(contractServiceAgreementId.toString()).matches(DEFAULT_NESTED_ID_REGEX);
		
	}
	
	@Test
	public void testEquals() {
		ContractServiceAgreementId contractServiceAgreementId1 = new ContractServiceAgreementId();
		ContractServiceAgreementId contractServiceAgreementId2 = new ContractServiceAgreementId();
		assertTrue(contractServiceAgreementId1.equals(contractServiceAgreementId2));
		
		contractServiceAgreementId1 = new ContractServiceAgreementId(new ContractId(1,1), 3);
		contractServiceAgreementId2 = new ContractServiceAgreementId(new ContractId(1,1), 3);
		assertTrue(contractServiceAgreementId1.equals(contractServiceAgreementId2));
		
		contractServiceAgreementId1 = new ContractServiceAgreementId(new ContractId(1,1), 1);
		contractServiceAgreementId2 = new ContractServiceAgreementId(new ContractId(1,1), 3);		
		assertFalse(contractServiceAgreementId1.equals(contractServiceAgreementId2));	
	}
	
	@Test
	public void testHashCode() {
		ContractServiceAgreementId contractServiceAgreementId1 = new ContractServiceAgreementId();
		ContractServiceAgreementId contractServiceAgreementId2 = new ContractServiceAgreementId();		
		assertThat(contractServiceAgreementId1.hashCode()).isGreaterThan(1);		
		assertEquals(contractServiceAgreementId1.hashCode(), contractServiceAgreementId2.hashCode());
		
		contractServiceAgreementId1 = new ContractServiceAgreementId(new ContractId(1,1), 3);
		contractServiceAgreementId2 = new ContractServiceAgreementId(new ContractId(1,1), 3);		
		assertThat(contractServiceAgreementId1.hashCode()).isGreaterThan(1);		
		assertEquals(contractServiceAgreementId1.hashCode(), contractServiceAgreementId2.hashCode());
		
		contractServiceAgreementId1 = new ContractServiceAgreementId(new ContractId(1,1), 1);
		contractServiceAgreementId2 = new ContractServiceAgreementId(new ContractId(1,1), 3);		
		assertThat(contractServiceAgreementId1.hashCode()).isGreaterThan(1);
		assertThat(contractServiceAgreementId2.hashCode()).isGreaterThan(1);
		assertNotEquals(contractServiceAgreementId1.hashCode(), contractServiceAgreementId2.hashCode());		
	}
	
		
	@Test
	public final void testSetGetContractId() {
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		assertEquals(0, contractServiceAgreementId.getContractId().getContractId());
		assertEquals(0, contractServiceAgreementId.getContractId().getClientId());
		
		contractServiceAgreementId.setContractId(new ContractId(1,2));
		assertEquals(1, contractServiceAgreementId.getContractId().getContractId());
		assertEquals(2, contractServiceAgreementId.getContractId().getClientId());
		
	}


	@Test
	public final void testSetGetServiceId() {
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		assertEquals(0, contractServiceAgreementId.getServiceId());
		
		contractServiceAgreementId.setServiceId(1);
		assertEquals(1, contractServiceAgreementId.getServiceId());
	}
	
	@Test
	public void testContructors() {
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		assertEquals(0, contractServiceAgreementId.getContractId().getContractId());
		assertEquals(0, contractServiceAgreementId.getContractId().getClientId());
		assertEquals(0, contractServiceAgreementId.getServiceId());
		
		contractServiceAgreementId = new ContractServiceAgreementId(new ContractId(1,2), 3);
		assertEquals(1, contractServiceAgreementId.getContractId().getContractId());
		assertEquals(2, contractServiceAgreementId.getContractId().getClientId());
		assertEquals(3, contractServiceAgreementId.getServiceId());
	}

}
