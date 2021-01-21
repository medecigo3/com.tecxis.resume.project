package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.ContractId;
import com.tecxis.resume.Client;
import com.tecxis.resume.Contract;


public interface ContractRepository extends JpaRepository<Contract, ContractId> {
			
	public List <Contract> findByClientOrderByIdAsc(Client client);
	
	public Contract getContractByName(String name);
	

}
