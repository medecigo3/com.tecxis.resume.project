package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Service;

public class JpaServiceDao implements ServiceDao {

	@Override
	public void save(Service k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Service k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Service k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Service> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Service> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.awt.List getServiceLikeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service getServiceByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
