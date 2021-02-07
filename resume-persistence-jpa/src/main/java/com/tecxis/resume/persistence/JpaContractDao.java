package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.repository.ContractRepository;

public class JpaContractDao implements ContractDao{
	
	@Autowired
	private  ContractRepository contractRepo;

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Contract contract) {
		em.merge(contract);
		
	}

	@Override
	public void add(Contract contract) {
		em.persist(contract);
		
	}

	@Override
	public void delete(Contract contract) {
		em.remove(contract);
		
	}

	@Override
	public List<Contract> findAll() {
		return contractRepo.findAll();
	}

	@Override
	public Page<Contract> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public List<Contract> findByClientOrderByIdAsc(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contract getContractByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
