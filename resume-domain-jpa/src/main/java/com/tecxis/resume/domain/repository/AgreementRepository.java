package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.id.AgreementId;

public interface AgreementRepository extends JpaRepository<Agreement, AgreementId> {
	
	public Agreement findByContractAndService(Contract contract, Service service);

}
