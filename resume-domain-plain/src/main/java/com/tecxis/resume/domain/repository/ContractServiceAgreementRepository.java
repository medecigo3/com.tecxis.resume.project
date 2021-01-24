package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.id.ContractServiceAgreementId;

public interface ContractServiceAgreementRepository extends JpaRepository<ContractServiceAgreement, ContractServiceAgreementId> {
	
	public ContractServiceAgreement findByContractAndService(Contract contract, Service service);

}
