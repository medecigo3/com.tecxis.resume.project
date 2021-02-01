package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Location;

public class JpaLocationDap implements LocationDao {

	@Override
	public void save(Location k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Location k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Location k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Location> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Location> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
