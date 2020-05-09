package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Client;
import com.tecxis.resume.Contract;
import com.tecxis.resume.Contract.ContractPK;


public interface ContractRepository extends JpaRepository<Contract, ContractPK> {
			
	public List <Contract> findByClientOrderByIdAsc(Client client);
	
	public Contract getContractByName(String name);
	

}
