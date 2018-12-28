package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Contract;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;

@Generated(value="Dali", date="2018-07-20T21:52:58.818+0200")
@StaticMetamodel(Supplier.class)
public class Supplier_ {
	public static volatile SingularAttribute<Supplier, Supplier.SupplierPK> id;
	public static volatile SingularAttribute<Supplier, String> name;
	public static volatile ListAttribute<Supplier, Contract> contracts;
	public static volatile SingularAttribute<Supplier, Staff> staff;
}
