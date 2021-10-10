package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.repository.ContractRepository;

@Repository("contractDao")
public class JpaContractDao implements ContractDao{
	
	@Autowired
	private  ContractRepository contractRepo;

	@Override
	public void save(Contract contract) {
		contractRepo.save(contract);
		
	}

	@Override
	public void add(Contract contract) {
		contractRepo.save(contract);
		
	}

	@Override
	public void delete(Contract contract) {
		contractRepo.delete(contract);
		
	}

	@Override
	public List<Contract> findAll() {
		return contractRepo.findAll();
	}

	@Override
	public Page<Contract> findAll(Pageable pageable) {
		return contractRepo.findAll(pageable);
	}

	@Override
	public List<Contract> findByClientOrderByIdAsc(Client client) {
		return contractRepo.findByClientOrderByIdAsc(client);
	}

	@Override
	public Contract getContractByName(String name) {
		return contractRepo.getContractByName(name);
	}

}
