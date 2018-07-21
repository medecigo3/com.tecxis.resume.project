package com.tecxis.resume;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.796+0200")
@StaticMetamodel(Course.class)
public class Course_ {
	public static volatile SingularAttribute<Course, Long> courseId;
	public static volatile SingularAttribute<Course, BigDecimal> credits;
	public static volatile SingularAttribute<Course, String> title;
	public static volatile ListAttribute<Course, Enrolment> enrolments;
}
