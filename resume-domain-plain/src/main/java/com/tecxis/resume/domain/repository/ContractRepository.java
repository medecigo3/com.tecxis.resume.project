package com.tecxis.resume.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.id.ContractId;


public interface ContractRepository extends JpaRepository<Contract, ContractId> {
			
	public List <Contract> findByClientOrderByIdAsc(Client client);
	
	public Contract getContractByName(String name);
	

}
