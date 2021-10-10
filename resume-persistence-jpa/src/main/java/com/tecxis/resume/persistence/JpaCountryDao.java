package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.repository.CountryRepository;

@Repository("countryDao")
public class JpaCountryDao implements CountryDao {

	@Autowired
	private CountryRepository countryRepo;
	
	@Override
	public void save(Country country) {
		countryRepo.save(country);

	}

	@Override
	public void add(Country country) {
		countryRepo.save(country);

	}

	@Override
	public void delete(Country country) {
		countryRepo.delete(country);

	}

	@Override
	public List<Country> findAll() {
		return countryRepo.findAll();
	}

	@Override
	public Page<Country> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public Country getCountryById(int id) {
		return countryRepo.getCountryById(id);
	}

	@Override
	public Country getCountryByName(String name) {
		return countryRepo.getCountryByName(name);
	}

}
