package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;

@Repository("contractServiceAgreementDao")
public class JpaContractServiceAgreementDao implements ContractServiceAgreementDao{

	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;
	
	@PersistenceContext
	private EntityManager em;
		
	
	@Override
	public void save(ContractServiceAgreement contractServiceAgreement) {
		em.merge(contractServiceAgreement);
		
	}

	@Override
	public void add(ContractServiceAgreement contractServiceAgreement) {
		em.persist(contractServiceAgreement);
		
	}

	@Override
	public void delete(ContractServiceAgreement contractServiceAgreement) {
		em.remove(contractServiceAgreement);
		
	}

	@Override
	public List<ContractServiceAgreement> findAll() {
		return contractServiceAgreementRepo.findAll();
	}

	@Override
	public Page<ContractServiceAgreement> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public ContractServiceAgreement findByContractAndService(Contract contract, Service service) {
		// TODO Auto-generated method stub
		return null;
	}

}
