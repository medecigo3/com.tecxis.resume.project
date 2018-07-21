package com.tecxis.resume;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.818+0200")
@StaticMetamodel(Supplier.class)
public class Supplier_ {
	public static volatile SingularAttribute<Supplier, SupplierPK> id;
	public static volatile SingularAttribute<Supplier, String> name;
	public static volatile ListAttribute<Supplier, Contract> contracts;
	public static volatile SingularAttribute<Supplier, Staff> staff;
}
