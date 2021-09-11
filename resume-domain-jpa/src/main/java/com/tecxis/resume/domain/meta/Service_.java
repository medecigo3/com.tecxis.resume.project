package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Service;


@StaticMetamodel(Service.class)
public class Service_ {
	public static volatile SingularAttribute<Service, Long> serviceId;
	public static volatile SingularAttribute<Service, String> desc;
	public static volatile SingularAttribute<Service, String> name;
	public static volatile SingularAttribute<Service, Contract> contract;
}
