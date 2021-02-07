package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.repository.AssignmentRepository;

public class JpaAssignmentDao implements AssignmentDao {
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Assignment assignment) {
		em.merge(assignment);

	}

	@Override
	public void add(Assignment assignment) {
		em.persist(assignment);

	}

	@Override
	public void delete(Assignment assignment) {
		em.remove(assignment);

	}

	@Override
	public List<Assignment> findAll() {
		return assignmentRepo.findAll();
	}

	@Override
	public Page<Assignment> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public List<Assignment> getAssignmentLikeDesc(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Assignment getAssignmentByDesc(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
