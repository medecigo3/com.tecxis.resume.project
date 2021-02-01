package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Country;

public class JpaCountryDao implements CountryDao {

	@Override
	public void save(Country k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Country k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Country k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Country> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Country> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country getCountryById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country getCountryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
