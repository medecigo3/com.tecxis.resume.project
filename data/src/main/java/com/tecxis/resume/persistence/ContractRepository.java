package com.tecxis.resume.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Client;
import com.tecxis.resume.Contract;
import com.tecxis.resume.Contract.ContractPK;
import com.tecxis.resume.Supplier;

public interface ContractRepository extends JpaRepository<Contract, ContractPK> {
	
	public Contract getContractByStartDate(Date startDate);
	
	public Contract getContractByEndDate(Date endDate);
	
	public List <Contract> findByClientOrderByStartDateAsc(Client client);
	
	public List <Contract> findBySupplierOrderByStartDateAsc(Supplier contract);
	
	public List <Contract> findByClientAndSupplierOrderByStartDateAsc(Client client, Supplier supplier);

}
