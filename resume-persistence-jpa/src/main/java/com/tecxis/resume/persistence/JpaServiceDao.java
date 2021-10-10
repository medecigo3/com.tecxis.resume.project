package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.repository.ServiceRepository;

@Repository("serviceDao")
public class JpaServiceDao implements ServiceDao {
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Override
	public void save(Service service) {
		serviceRepo.save(service);

	}

	@Override
	public void add(Service service) {
		serviceRepo.save(service);

	}

	@Override
	public void delete(Service service) {
		serviceRepo.delete(service);

	}

	@Override
	public List<Service> findAll() {
		return serviceRepo.findAll();
	}

	@Override
	public Page<Service> findAll(Pageable pageable) {
		return serviceRepo.findAll(pageable);
	}

	@Override
	public List<Service> getServiceLikeName(String name) {
		return serviceRepo.getServiceLikeName(name);
	}

	@Override
	public Service getServiceByName(String name) {
		return serviceRepo.getServiceByName(name);
	}

}
