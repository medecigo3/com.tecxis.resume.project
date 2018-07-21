package com.tecxis.resume;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-07-20T21:52:58.787+0200")
@StaticMetamodel(City.class)
public class City_ {
	public static volatile SingularAttribute<City, CityPK> id;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, Country> country;
	public static volatile ListAttribute<City, Project> projects;
}
