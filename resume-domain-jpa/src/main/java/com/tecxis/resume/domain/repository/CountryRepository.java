package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	public Country getCountryById(long id);
	
	public Country getCountryByName(String name);
}
