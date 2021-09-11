package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.id.ProjectId;


@StaticMetamodel(ProjectId.class)
public class ProjectPK_ {
	public static volatile SingularAttribute<ProjectId, Long> projectId;
	public static volatile SingularAttribute<ProjectId, Long> clientId;
	public static volatile SingularAttribute<ProjectId, Long> staffId;
	public static volatile SingularAttribute<ProjectId, String> name;
	public static volatile SingularAttribute<ProjectId, String> version;
}
