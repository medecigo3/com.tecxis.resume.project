package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.repository.SkillRepository;

public class JpaSkillDao implements SkillDao {
	
	@Autowired
	private SkillRepository skillRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Skill skill) {
		em.merge(skill);

	}

	@Override
	public void add(Skill skill) {
		em.persist(skill);

	}

	@Override
	public void delete(Skill skill) {
		em.remove(skill);

	}

	@Override
	public List<Skill> findAll() {
		return skillRepo.findAll();
	}

	@Override
	public Page<Skill> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Skill getSkillByName(String tibco) {
		// TODO Auto-generated method stub
		return null;
	}

}
