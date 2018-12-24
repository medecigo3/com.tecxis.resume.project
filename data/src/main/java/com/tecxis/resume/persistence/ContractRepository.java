package com.tecxis.resume.persistence;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractPK;

public interface ContractRepository extends JpaRepository<Contract, ContractPK> {
	
	public Contract getContractByStartDate(Date startDate);
	
	public Contract getContractByEndDate(Date endDate);

}
