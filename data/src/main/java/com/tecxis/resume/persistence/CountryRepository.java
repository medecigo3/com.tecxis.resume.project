package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
