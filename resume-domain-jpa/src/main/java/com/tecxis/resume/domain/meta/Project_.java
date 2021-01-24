package com.tecxis.resume.domain.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.id.ProjectId;

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
