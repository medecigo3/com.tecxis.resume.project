package com.tecxis.resume.domain.meta;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Course;


@StaticMetamodel(Course.class)
public class Course_ {
	public static volatile SingularAttribute<Course, Long> courseId;
	public static volatile SingularAttribute<Course, BigDecimal> credits;
	public static volatile SingularAttribute<Course, String> title;
//	public static volatile ListAttribute<Course, Enrolment> enrolments;
}
