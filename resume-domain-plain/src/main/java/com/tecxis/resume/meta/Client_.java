package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Client;
import com.tecxis.resume.Contract;
import com.tecxis.resume.Project;

@Generated(value="Dali", date="2018-07-20T21:52:58.790+0200")
@StaticMetamodel(Client.class)
public class Client_ {
	public static volatile SingularAttribute<Client, Long> clientId;
	public static volatile SingularAttribute<Client, String> name;
	public static volatile SingularAttribute<Client, String> website;
	public static volatile ListAttribute<Client, Contract> contracts;
	public static volatile ListAttribute<Client, Project> projects;
}
