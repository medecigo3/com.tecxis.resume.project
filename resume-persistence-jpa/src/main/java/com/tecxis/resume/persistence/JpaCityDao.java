package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.repository.CityRepository;

@Repository("cityDao")
public class JpaCityDao implements CityDao {
	
	@Autowired
	private CityRepository cityRepo;

	@Override
	public void save(City city) {
		cityRepo.save(city);

	}

	@Override
	public void add(City city) {
		cityRepo.save(city);

	}

	@Override
	public void delete(City city) {
		cityRepo.delete(city);

	}

	@Override
	public List<City> findAll() {
		return cityRepo.findAll();
	}

	@Override
	public Page<City> findAll(Pageable pageable) {
		return cityRepo.findAll(pageable);
	}

	@Override
	public City getCityByName(String name) {
		return cityRepo.getCityByName(name);
	}

}
