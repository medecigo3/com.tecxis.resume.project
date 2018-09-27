package com.tecxis.resume;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.781+0200")
@StaticMetamodel(Assignment.class)
public class Assignment_ {
	public static volatile SingularAttribute<Assignment, Long> id;
	public static volatile SingularAttribute<Assignment, String> desc;
	public static volatile SingularAttribute<Assignment, BigDecimal> priority;
	public static volatile SingularAttribute<Assignment, Project> project;
}
