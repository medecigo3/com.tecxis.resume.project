package com.tecxis.resume.persistence;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	
	public Contract getContractByStartDate(Date startDate);
	
	public Contract getContractByEndDate(Date endDate);

}
