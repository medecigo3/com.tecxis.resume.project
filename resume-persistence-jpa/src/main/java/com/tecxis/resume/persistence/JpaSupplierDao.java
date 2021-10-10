package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.repository.SupplierRepository;

@Repository("supplierDao")
public class JpaSupplierDao implements SupplierDao {
	
	@Autowired
	private SupplierRepository supplierRepo;

	@Override
	public void save(Supplier supplier) {
		supplierRepo.save(supplier);

	}

	@Override
	public void add(Supplier supplier) {
		supplierRepo.save(supplier);

	}

	@Override
	public void delete(Supplier supplier) {
		supplierRepo.delete(supplier);

	}

	@Override
	public List<Supplier> findAll() {
		return supplierRepo.findAll();
	}

	@Override
	public Page<Supplier> findAll(Pageable pageable) {
		return supplierRepo.findAll(pageable);
	}

	@Override
	public Supplier getSupplierByName(String name) {
		return supplierRepo.getSupplierByName(name);
	}

}
