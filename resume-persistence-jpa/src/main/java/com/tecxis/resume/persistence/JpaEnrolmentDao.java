package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Enrolment;
import com.tecxis.resume.domain.repository.EnrolmentRepository;

@Repository("enrolmentDao")
public class JpaEnrolmentDao implements EnrolmentDao {
	
	@Autowired 
	private EnrolmentRepository enrolmentRepo;

	@Override
	public void save(Enrolment enrolment) {
		enrolmentRepo.save(enrolment);
		
	}

	@Override
	public void add(Enrolment enrolment) {
		enrolmentRepo.save(enrolment);
		
	}

	@Override
	public void delete(Enrolment enrolment) {
		enrolmentRepo.delete(enrolment);
		
	}

	@Override
	public List<Enrolment> findAll() {
		return enrolmentRepo.findAll();
	}

	@Override
	public Page<Enrolment> findAll(Pageable pageable) {
		return enrolmentRepo.findAll(pageable);
	}

}
