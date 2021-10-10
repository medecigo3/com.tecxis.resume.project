package com.tecxis.resume.persistence;

import java.util.List;

import com.tecxis.resume.domain.Service;

public interface ServiceDao extends Dao<Service> {

	public List<Service> getServiceLikeName(String name);

	public Service getServiceByName(String name);

}
