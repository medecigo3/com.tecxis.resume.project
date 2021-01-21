package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.commons.persistence.id.ProjectId;

@Generated(value="Dali", date="2018-07-20T21:52:58.806+0200")
@StaticMetamodel(ProjectId.class)
public class ProjectPK_ {
	public static volatile SingularAttribute<ProjectId, Long> projectId;
	public static volatile SingularAttribute<ProjectId, Long> clientId;
	public static volatile SingularAttribute<ProjectId, Long> staffId;
	public static volatile SingularAttribute<ProjectId, String> name;
	public static volatile SingularAttribute<ProjectId, String> version;
}
