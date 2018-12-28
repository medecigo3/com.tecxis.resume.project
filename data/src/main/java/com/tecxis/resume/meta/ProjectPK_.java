package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Project;

@Generated(value="Dali", date="2018-07-20T21:52:58.806+0200")
@StaticMetamodel(Project.ProjectPK.class)
public class ProjectPK_ {
	public static volatile SingularAttribute<Project.ProjectPK, Long> projectId;
	public static volatile SingularAttribute<Project.ProjectPK, Long> clientId;
	public static volatile SingularAttribute<Project.ProjectPK, Long> staffId;
	public static volatile SingularAttribute<Project.ProjectPK, String> name;
	public static volatile SingularAttribute<Project.ProjectPK, String> version;
}
