package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.Supplier;

public interface SupplierDao extends Dao<Supplier> {

	public Supplier getSupplierByName(String name);

}
