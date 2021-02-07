package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.repository.ServiceRepository;

public class JpaServiceDao implements ServiceDao {
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Service service) {
		em.merge(service);

	}

	@Override
	public void add(Service service) {
		em.persist(service);

	}

	@Override
	public void delete(Service service) {
		em.remove(service);

	}

	@Override
	public List<Service> findAll() {
		return serviceRepo.findAll();
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
