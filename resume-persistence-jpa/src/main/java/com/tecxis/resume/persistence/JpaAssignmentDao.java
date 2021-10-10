package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.repository.AssignmentRepository;

@Repository("assignmentDao")
public class JpaAssignmentDao implements AssignmentDao {

	@Autowired
	private AssignmentRepository assignmentRepo;
		
	@Override
	public void save(Assignment assignment) {
		assignmentRepo.save(assignment);

	}

	@Override
	public void add(Assignment assignment) {
		assignmentRepo.save(assignment);

	}

	@Override
	public void delete(Assignment assignment) {
		assignmentRepo.save(assignment);

	}

	@Override
	public List<Assignment> findAll() {
		return assignmentRepo.findAll();
	}

	@Override
	public Page<Assignment> findAll(Pageable pageable) {
		return assignmentRepo.findAll(pageable);
	}

}
