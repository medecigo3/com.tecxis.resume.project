package com.tecxis.resume.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.City;
import com.tecxis.resume.Country;

@Generated(value="Dali", date="2018-07-20T21:52:58.794+0200")
@StaticMetamodel(Country.class)
public class Country_ {
	public static volatile SingularAttribute<Country, Long> countryId;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile ListAttribute<Country, City> cities;
}
