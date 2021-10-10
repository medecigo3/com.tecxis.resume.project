package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_NESTED_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AgreementIdTest {

	@Test
	public void testToString() {
		AgreementId agreementId = new AgreementId();
		assertThat(agreementId.toString()).matches(DEFAULT_NESTED_ID_REGEX);
		
	}
	
	@Test
	public void testEquals() {
		AgreementId contractServiceAgreementId1 = new AgreementId();
		AgreementId contractServiceAgreementId2 = new AgreementId();
		assertTrue(contractServiceAgreementId1.equals(contractServiceAgreementId2));
		
		contractServiceAgreementId1 = new AgreementId(new ContractId(1,1), 3);
		contractServiceAgreementId2 = new AgreementId(new ContractId(1,1), 3);
		assertTrue(contractServiceAgreementId1.equals(contractServiceAgreementId2));
		
		contractServiceAgreementId1 = new AgreementId(new ContractId(1,1), 1);
		contractServiceAgreementId2 = new AgreementId(new ContractId(1,1), 3);		
		assertFalse(contractServiceAgreementId1.equals(contractServiceAgreementId2));	
	}
	
	@Test
	public void testHashCode() {
		AgreementId contractServiceAgreementId1 = new AgreementId();
		AgreementId contractServiceAgreementId2 = new AgreementId();		
		assertThat(contractServiceAgreementId1.hashCode()).isGreaterThan(1);		
		assertEquals(contractServiceAgreementId1.hashCode(), contractServiceAgreementId2.hashCode());
		
		contractServiceAgreementId1 = new AgreementId(new ContractId(1,1), 3);
		contractServiceAgreementId2 = new AgreementId(new ContractId(1,1), 3);		
		assertThat(contractServiceAgreementId1.hashCode()).isGreaterThan(1);		
		assertEquals(contractServiceAgreementId1.hashCode(), contractServiceAgreementId2.hashCode());
		
		contractServiceAgreementId1 = new AgreementId(new ContractId(1,1), 1);
		contractServiceAgreementId2 = new AgreementId(new ContractId(1,1), 3);		
		assertThat(contractServiceAgreementId1.hashCode()).isGreaterThan(1);
		assertThat(contractServiceAgreementId2.hashCode()).isGreaterThan(1);
		assertNotEquals(contractServiceAgreementId1.hashCode(), contractServiceAgreementId2.hashCode());		
	}
	
		
	@Test
	public final void testSetGetContractId() {
		AgreementId agreementId = new AgreementId();
		assertEquals(0, agreementId.getContractId().getContractId());
		assertEquals(0, agreementId.getContractId().getClientId());
		
		agreementId.setContractId(new ContractId(1,2));
		assertEquals(1, agreementId.getContractId().getContractId());
		assertEquals(2, agreementId.getContractId().getClientId());
		
	}


	@Test
	public final void testSetGetServiceId() {
		AgreementId agreementId = new AgreementId();
		assertEquals(0, agreementId.getServiceId());
		
		agreementId.setServiceId(1);
		assertEquals(1, agreementId.getServiceId());
	}
	
	@Test
	public void testContructors() {
		AgreementId agreementId = new AgreementId();
		assertEquals(0, agreementId.getContractId().getContractId());
		assertEquals(0, agreementId.getContractId().getClientId());
		assertEquals(0, agreementId.getServiceId());
		
		agreementId = new AgreementId(new ContractId(1,2), 3);
		assertEquals(1, agreementId.getContractId().getContractId());
		assertEquals(2, agreementId.getContractId().getClientId());
		assertEquals(3, agreementId.getServiceId());
	}

}
