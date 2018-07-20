package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Skill;

public interface SkillRespository extends JpaRepository<Skill, Long> {

}
