package com.tecxis.resume.ver2;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.794+0200")
@StaticMetamodel(Country.class)
public class Country_ {
	public static volatile SingularAttribute<Country, Long> countryId;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile ListAttribute<Country, City> cities;
}
