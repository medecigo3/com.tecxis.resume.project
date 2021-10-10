package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.Skill;

public interface SkillDao extends Dao<Skill> {

	public Skill getSkillByName(String skill);
}
