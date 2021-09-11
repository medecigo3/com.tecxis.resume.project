package com.tecxis.resume.domain.meta;

import java.util.Date;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Service;


@StaticMetamodel(Contract.class)
public class Contract_ {
	public static volatile SingularAttribute <Contract, Long> clientId;
	public static volatile SingularAttribute <Contract, Long> contractId;	
	public static volatile SingularAttribute <Contract, Long> staffId ;
	public static volatile SingularAttribute <Contract, Long> supplierId;		
	public static volatile SingularAttribute<Contract, Date> endDate;
	public static volatile SingularAttribute<Contract, Date> startDate;
	public static volatile CollectionAttribute<Contract, Service> services;

}
