package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Project;

public class JpaProjectDao implements ProjectDao {

	@Override
	public void save(Project k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Project k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Project k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Project> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Project> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project findByNameAndVersion(String name, String version) {
		// TODO Auto-generated method stub
		return null;
	}

}
