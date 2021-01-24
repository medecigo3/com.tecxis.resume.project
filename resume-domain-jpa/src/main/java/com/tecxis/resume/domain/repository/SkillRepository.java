package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

	public Skill getSkillByName(String tibco);

}
