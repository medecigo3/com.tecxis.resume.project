package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
