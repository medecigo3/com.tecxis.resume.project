package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void save(Project project) {
		em.merge(project);

	}

	@Override
	public void add(Project project) {
		em.persist(project);

	}

	@Override
	public void delete(Project project) {
		em.remove(project);

	}

	@Override
	public List<Project> findAll() {
		return projectRepo.findAll();
	}

	@Override
	public Page<Project> findAll(Pageable pageable) {
		return findAll(pageable);
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
