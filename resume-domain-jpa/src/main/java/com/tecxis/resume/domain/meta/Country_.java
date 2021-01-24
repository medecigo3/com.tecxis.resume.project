package com.tecxis.resume.domain.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;

@Generated(value="Dali", date="2018-07-20T21:52:58.794+0200")
@StaticMetamodel(Country.class)
public class Country_ {
	public static volatile SingularAttribute<Country, Long> countryId;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile ListAttribute<Country, City> cities;
}
