package com.tecxis.resume;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.814+0200")
@StaticMetamodel(StaffSkill.class)
public class StaffSkill_ {
	public static volatile SingularAttribute<StaffSkill, StaffSkillPK> id;
	public static volatile SingularAttribute<StaffSkill, BigDecimal> endorsement;
	public static volatile SingularAttribute<StaffSkill, BigDecimal> yearsOfExperience;
	public static volatile SingularAttribute<StaffSkill, Skill> skill;
	public static volatile SingularAttribute<StaffSkill, Staff> staff;
}
