package com.tecxis.resume.meta;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Course;
import com.tecxis.resume.Interest;
import com.tecxis.resume.Project;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;

@Generated(value="Dali", date="2018-07-20T21:52:58.812+0200")
@StaticMetamodel(Staff.class)
public class Staff_ {
	public static volatile SingularAttribute<Staff, Long> staffId;
	public static volatile SingularAttribute<Staff, Date> birthDate;
	public static volatile SingularAttribute<Staff, String> lastname;
	public static volatile SingularAttribute<Staff, String> name;
	public static volatile ListAttribute<Staff, Course> courses;
	public static volatile ListAttribute<Staff, Interest> interests;
	public static volatile ListAttribute<Staff, Project> projects;
//	public static volatile ListAttribute<Staff, StaffSkill> staffSkills;
	public static volatile ListAttribute<Staff, Supplier> suppliers;
}
