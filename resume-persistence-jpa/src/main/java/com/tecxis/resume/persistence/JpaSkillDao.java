package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.repository.SkillRepository;

@Repository("skillDao")
public class JpaSkillDao implements SkillDao {
	
	@Autowired
	private SkillRepository skillRepo;

	@Override
	public void save(Skill skill) {
		skillRepo.save(skill);

	}

	@Override
	public void add(Skill skill) {
		skillRepo.save(skill);

	}

	@Override
	public void delete(Skill skill) {
		skillRepo.delete(skill);

	}

	@Override
	public List<Skill> findAll() {
		return skillRepo.findAll();
	}

	@Override
	public Page<Skill> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public Skill getSkillByName(String skill) {
		return skillRepo.getSkillByName(skill);
	}

}
