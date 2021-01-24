package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.ContractId;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;


public interface ContractRepository extends JpaRepository<Contract, ContractId> {
			
	public List <Contract> findByClientOrderByIdAsc(Client client);
	
	public Contract getContractByName(String name);
	

}
