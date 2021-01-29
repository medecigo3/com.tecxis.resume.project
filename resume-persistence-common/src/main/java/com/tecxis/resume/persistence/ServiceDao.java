package com.tecxis.resume.persistence;

import java.awt.List;
import com.tecxis.resume.domain.Service;

public interface ServiceDao extends Dao<Service> {

	public List getServiceLikeName(String name);

	public Service getServiceByName(String name);

}
