package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.repository.CityRepository;

public class JpaCityDao implements CityDao {
	
	@Autowired
	private CityRepository cityRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(City city) {
		em.merge(city);

	}

	@Override
	public void add(City city) {
		em.persist(city);

	}

	@Override
	public void delete(City city) {
		em.remove(city);

	}

	@Override
	public List<City> findAll() {
		return cityRepo.findAll();
	}

	@Override
	public Page<City> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public City getCityByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
