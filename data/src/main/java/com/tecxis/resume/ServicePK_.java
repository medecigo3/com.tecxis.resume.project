package com.tecxis.resume;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Service.ServicePK.class)
public class ServicePK_ {
	public static volatile SingularAttribute<Service.ServicePK, Long> contractId;
	public static volatile SingularAttribute<Service.ServicePK, Long> clientId;
	public static volatile SingularAttribute<Service.ServicePK, Long> supplierId;	
	public static volatile SingularAttribute<Service.ServicePK, Long> staffId;
}
