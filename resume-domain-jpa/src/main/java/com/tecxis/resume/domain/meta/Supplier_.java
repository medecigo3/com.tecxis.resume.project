package com.tecxis.resume.domain.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;

@Generated(value="Dali", date="2018-07-20T21:52:58.818+0200")
@StaticMetamodel(Supplier.class)
public class Supplier_ {
	public static volatile SingularAttribute<Supplier, Long> id;
	public static volatile SingularAttribute<Supplier, String> name;
	public static volatile ListAttribute<Supplier, Contract> contracts;
	public static volatile SingularAttribute<Supplier, Staff> staff;
}
