package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.id.CityId;

@StaticMetamodel(City.class)
public class City_ {
	public static volatile SingularAttribute<City, CityId> id;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, Country> country;
	public static volatile ListAttribute<City, Project> projects;
}
