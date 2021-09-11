package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.Staff;


@StaticMetamodel(Interest.class)
public class Interest_ {
	public static volatile SingularAttribute<Interest, Long> interestId;
	public static volatile SingularAttribute<Interest, String> desc;
	public static volatile SingularAttribute<Interest, Staff> staff;
}
