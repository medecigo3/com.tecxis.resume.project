package com.tecxis.resume.domain.meta;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Project;


@StaticMetamodel(Assignment.class)
public class Assignment_ {
	public static volatile SingularAttribute<Assignment, Long> id;
	public static volatile SingularAttribute<Assignment, String> desc;
	public static volatile SingularAttribute<Assignment, BigDecimal> priority;
	public static volatile SingularAttribute<Assignment, Project> project;
}
