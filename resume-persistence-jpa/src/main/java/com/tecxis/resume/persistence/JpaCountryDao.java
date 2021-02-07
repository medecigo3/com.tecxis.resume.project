package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.repository.CountryRepository;

public class JpaCountryDao implements CountryDao {

	@Autowired
	private CountryRepository countryRepo;
	
	@PersistenceContext
	private EntityManager em;	
	
	@Override
	public void save(Country country) {
		em.merge(country);

	}

	@Override
	public void add(Country country) {
		em.persist(country);

	}

	@Override
	public void delete(Country country) {
		em.remove(country);

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country getCountryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
