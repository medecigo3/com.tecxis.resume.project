package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
