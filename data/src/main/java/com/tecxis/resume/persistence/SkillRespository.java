package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Skill;

public interface SkillRespository extends JpaRepository<Skill, Long> {

}
