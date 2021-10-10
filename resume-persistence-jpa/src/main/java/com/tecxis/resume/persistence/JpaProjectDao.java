package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.repository.ProjectRepository;

@Repository("projectDao")
public class JpaProjectDao implements ProjectDao {

	@Autowired
	private ProjectRepository projectRepo;	
	
	@Override
	public void save(Project project) {
		projectRepo.save(project);

	}

	@Override
	public void add(Project project) {
		projectRepo.save(project);

	}

	@Override
	public void delete(Project project) {
		projectRepo.delete(project);

	}

	@Override
	public List<Project> findAll() {
		return projectRepo.findAll();
	}

	@Override
	public Page<Project> findAll(Pageable pageable) {
		return projectRepo.findAll(pageable);
	}

	@Override
	public List<Project> findByName(String name) {
		return projectRepo.findByName(name);
	}

	@Override
	public Project findByNameAndVersion(String name, String version) {
		return projectRepo.findByNameAndVersion(name, version);
	}

}
