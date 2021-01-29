package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.City;

public interface CityDao extends Dao<City> {

	public City getCityByName(String name);

}
