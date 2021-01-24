package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	public Supplier getSupplierByName(String name);
}
