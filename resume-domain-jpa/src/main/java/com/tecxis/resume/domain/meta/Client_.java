package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Project;


@StaticMetamodel(Client.class)
public class Client_ {
	public static volatile SingularAttribute<Client, Long> clientId;
	public static volatile SingularAttribute<Client, String> name;
	public static volatile SingularAttribute<Client, String> website;
	public static volatile ListAttribute<Client, Contract> contracts;
	public static volatile ListAttribute<Client, Project> projects;
}
