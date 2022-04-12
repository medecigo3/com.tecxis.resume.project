package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.id.CityId;

public interface CityRepository extends JpaRepository<City, CityId> {
		
	public City getCityByName(String name);

}
