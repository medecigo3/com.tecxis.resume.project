package com.tecxis.resume.domain.meta;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Task;
import com.tecxis.resume.domain.Project;


@StaticMetamodel(Task.class)
public class Assignment_ {
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, String> desc;
	public static volatile SingularAttribute<Task, BigDecimal> priority;
	public static volatile SingularAttribute<Task, Project> project;
}
