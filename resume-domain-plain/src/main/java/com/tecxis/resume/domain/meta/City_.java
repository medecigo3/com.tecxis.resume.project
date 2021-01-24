package com.tecxis.resume.domain.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.commons.persistence.id.CityId;
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Project;

@Generated(value="Dali", date="2018-07-20T21:52:58.787+0200")
@StaticMetamodel(City.class)
public class City_ {
	public static volatile SingularAttribute<City, CityId> id;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, Country> country;
	public static volatile ListAttribute<City, Project> projects;
}
