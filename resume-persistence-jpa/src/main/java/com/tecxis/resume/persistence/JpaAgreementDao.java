package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.repository.AgreementRepository;

@Repository("agreementDao")
public class JpaAgreementDao implements AgreementDao{

	@Autowired
	private AgreementRepository agreementRepo;
	
	@PersistenceContext
	private EntityManager em;
		
	
	@Override
	public void save(Agreement agreement) {
		em.merge(agreement);
		
	}

	@Override
	public void add(Agreement agreement) {
		em.persist(agreement);
		
	}

	@Override
	public void delete(Agreement agreement) {
		em.remove(agreement);
		
	}

	@Override
	public List<Agreement> findAll() {
		return agreementRepo.findAll();
	}

	@Override
	public Page<Agreement> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public Agreement findByContractAndService(Contract contract, Service service) {
		// TODO Auto-generated method stub
		return null;
	}

}
