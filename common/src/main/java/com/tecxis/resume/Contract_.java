package com.tecxis.resume;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.792+0200")
@StaticMetamodel(Contract.class)
public class Contract_ {
	public static volatile SingularAttribute<Contract, ContractPK> id;
	public static volatile SingularAttribute<Contract, Date> endDate;
	public static volatile SingularAttribute<Contract, Date> startDate;
	public static volatile SingularAttribute<Contract, Client> client;
	public static volatile SingularAttribute<Contract, Service> service;
	public static volatile SingularAttribute<Contract, Supplier> supplier;
}
