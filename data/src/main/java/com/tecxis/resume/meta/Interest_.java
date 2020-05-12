package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.Interest;
import com.tecxis.resume.Staff;

@Generated(value="Dali", date="2018-07-20T21:52:58.801+0200")
@StaticMetamodel(Interest.class)
public class Interest_ {
	public static volatile SingularAttribute<Interest, Long> interestId;
	public static volatile SingularAttribute<Interest, String> desc;
	public static volatile SingularAttribute<Interest, Staff> staff;
}
