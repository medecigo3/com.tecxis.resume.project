package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ContractServiceAgreement;
import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;

public interface ContractServiceAgreementRepository extends JpaRepository<ContractServiceAgreement, ContractServiceAgreementId> {

}
