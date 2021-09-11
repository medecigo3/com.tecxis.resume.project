package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Skill;


@StaticMetamodel(Skill.class)
public class Skill_ {
	public static volatile SingularAttribute<Skill, String> skillId;
	public static volatile SingularAttribute<Skill, String> name;
//	public static volatile ListAttribute<Skill, StaffSkill> staffSkills;
}
