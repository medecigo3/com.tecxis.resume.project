package com.tecxis.resume.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.domain.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

	@Query("select s from Service s where s.name LIKE %?1")
	public List <Service> getServiceLikeName(String name);
	
	public Service getServiceByName(String name);
	
}
