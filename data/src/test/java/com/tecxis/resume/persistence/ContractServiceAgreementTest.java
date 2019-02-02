package com.tecxis.resume.persistence;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractServiceAgreement;
import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;
import com.tecxis.resume.Service;

public class ContractServiceAgreementTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	public static ContractServiceAgreement insertAContractServiceAgreement(Contract contract, Service service, EntityManager entityManager) {
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(contract);
		contractServiceAgreementId.setService(service);
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement();
		contractServiceAgreement.setContractServiceAgreementId(contractServiceAgreementId);
		entityManager.persist(contractServiceAgreement);
		entityManager.flush();
		return contractServiceAgreement;
	}

}
