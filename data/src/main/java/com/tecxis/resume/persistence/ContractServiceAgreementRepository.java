package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractServiceAgreement;
import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;
import com.tecxis.resume.Service;

public interface ContractServiceAgreementRepository extends JpaRepository<ContractServiceAgreement, ContractServiceAgreementId> {
	
	public ContractServiceAgreement findByContractServiceAgreementId_contractAndContractServiceAgreementId_Service(Contract contract, Service service);

}
