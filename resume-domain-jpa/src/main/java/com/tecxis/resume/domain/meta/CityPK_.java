package com.tecxis.resume.domain.meta;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tecxis.resume.domain.id.CityId;


@StaticMetamodel(CityId.class)
public class CityPK_ {
	public static volatile SingularAttribute<CityId, Long> cityId;
	public static volatile SingularAttribute<CityId, Long> countryId;
}
