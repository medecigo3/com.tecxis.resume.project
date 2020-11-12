package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.commons.persistence.id.ProjectId;
import com.tecxis.resume.Assignment;
import com.tecxis.resume.City;
import com.tecxis.resume.Client;
import com.tecxis.resume.Project;
import com.tecxis.resume.Staff;

@Generated(value="Dali", date="2018-07-20T21:52:58.803+0200")
@StaticMetamodel(Project.class)
public class Project_ {
	public static volatile SingularAttribute<Project, ProjectId> projectId;
	public static volatile SingularAttribute<Project, ProjectId> clientId;
	public static volatile SingularAttribute<Project, String> desc;
	public static volatile ListAttribute<Project, Assignment> assignments;
	public static volatile ListAttribute<Project, City> cities;
	public static volatile SingularAttribute<Project, Client> client;
	public static volatile SingularAttribute<Project, Staff> staff;
}
