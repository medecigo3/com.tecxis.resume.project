package com.tecxis.resume;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.808+0200")
@StaticMetamodel(Service.class)
public class Service_ {
	public static volatile SingularAttribute<Service, Service.ServicePK> serviceId;
	public static volatile SingularAttribute<Service, String> desc;
	public static volatile SingularAttribute<Service, String> name;
	public static volatile SingularAttribute<Service, Contract> contract;
}
