package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Assignment;

public class JpaAssignmentDao implements AssignmentDao {

	@Override
	public void save(Assignment k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Assignment k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Assignment k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Assignment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Assignment> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
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
