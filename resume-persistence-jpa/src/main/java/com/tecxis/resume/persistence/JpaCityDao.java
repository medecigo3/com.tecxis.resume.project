package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.City;

public class JpaCityDao implements CityDao {

	@Override
	public void save(City k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(City k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(City k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<City> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<City> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City getCityByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
