package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;


@StaticMetamodel(Country.class)
public class Country_ {
	public static volatile SingularAttribute<Country, Long> countryId;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile ListAttribute<Country, City> cities;
}
