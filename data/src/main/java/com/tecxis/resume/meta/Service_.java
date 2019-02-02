package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Contract;
import com.tecxis.resume.Service;

@Generated(value="Dali", date="2018-07-20T21:52:58.808+0200")
@StaticMetamodel(Service.class)
public class Service_ {
	public static volatile SingularAttribute<Service, Long> serviceId;
	public static volatile SingularAttribute<Service, String> desc;
	public static volatile SingularAttribute<Service, String> name;
	public static volatile SingularAttribute<Service, Contract> contract;
}
