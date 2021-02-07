package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Enrolment;
import com.tecxis.resume.domain.repository.EnrolmentRepository;

public class JpaEnrolmentDao implements EnrolmentDao {
	
	@Autowired 
	private EnrolmentRepository enrolmentRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Enrolment enrolment) {
		em.merge(enrolment);
		
	}

	@Override
	public void add(Enrolment enrolment) {
		em.persist(enrolment);
		
	}

	@Override
	public void delete(Enrolment enrolment) {
		em.remove(enrolment);
		
	}

	@Override
	public List<Enrolment> findAll() {
		return enrolmentRepo.findAll();
	}

	@Override
	public Page<Enrolment> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
