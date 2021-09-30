package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.repository.LocationRepository;

@Repository("locationDao")
public class JpaLocationDao implements LocationDao {
	
	@Autowired
	private LocationRepository locationRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Location location) {
		em.merge(location);

	}

	@Override
	public void add(Location location) {
		em.persist(location);

	}

	@Override
	public void delete(Location location) {
		em.remove(location);

	}

	@Override
	public List<Location> findAll() {
		return locationRepo.findAll();
	}

	@Override
	public Page<Location> findAll(Pageable pageable) {
		return findAll(pageable);
	}

}
