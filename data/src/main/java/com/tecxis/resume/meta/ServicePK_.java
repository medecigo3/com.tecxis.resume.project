package com.tecxis.resume.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Service;

@StaticMetamodel(Service.ServicePK.class)
public class ServicePK_ {
	public static volatile SingularAttribute<Service.ServicePK, Long> contractId;
	public static volatile SingularAttribute<Service.ServicePK, Long> clientId;
	public static volatile SingularAttribute<Service.ServicePK, Long> supplierId;	
	public static volatile SingularAttribute<Service.ServicePK, Long> staffId;
}
