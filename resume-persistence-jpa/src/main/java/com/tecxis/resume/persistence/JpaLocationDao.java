package com.tecxis.resume.persistence;

import java.util.List;

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


	@Override
	public void save(Location location) {
		locationRepo.save(location);

	}

	@Override
	public void add(Location location) {
		locationRepo.save(location);

	}

	@Override
	public void delete(Location location) {
		locationRepo.delete(location);

	}

	@Override
	public List<Location> findAll() {
		return locationRepo.findAll();
	}

	@Override
	public Page<Location> findAll(Pageable pageable) {
		return locationRepo.findAll(pageable);
	}

}
