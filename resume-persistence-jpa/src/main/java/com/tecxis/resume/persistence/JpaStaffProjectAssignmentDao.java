package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.StaffProjectAssignment;
import com.tecxis.resume.domain.repository.StaffProjectAssignmentRepository;

public class JpaStaffProjectAssignmentDao implements StaffProjectAssignmentDao {

	@Autowired
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
	
	@PersistenceContext
	private EntityManager em;
		
	@Override
	public void save(StaffProjectAssignment staffProjectAssignment) {
		em.merge(staffProjectAssignment);

	}

	@Override
	public void add(StaffProjectAssignment staffProjectAssignment) {
		em.persist(staffProjectAssignment);

	}

	@Override
	public void delete(StaffProjectAssignment staffProjectAssignment) {
		em.remove(staffProjectAssignment);

	}

	@Override
	public List<StaffProjectAssignment> findAll() {
		return staffProjectAssignmentRepo.findAll();
	}

	@Override
	public Page<StaffProjectAssignment> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
