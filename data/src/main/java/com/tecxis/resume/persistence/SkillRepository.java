package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

	public Skill getSkillByName(String tibco);

}