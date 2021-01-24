package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.City;

public interface CityRepository extends JpaRepository<City, Long> {
		
	public City getCityByName(String name);

}
