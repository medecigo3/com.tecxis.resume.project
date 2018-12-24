package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	public List <Supplier> getSuppliersByName(String name);
	
	public Supplier getSupplierByNameAndStaffId(String name, long staffId);
}
