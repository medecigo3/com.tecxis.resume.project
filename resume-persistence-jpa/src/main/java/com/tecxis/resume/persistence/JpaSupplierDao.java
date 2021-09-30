package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Supplier supplier) {
		em.merge(supplier);

	}

	@Override
	public void add(Supplier supplier) {
		em.persist(supplier);

	}

	@Override
	public void delete(Supplier supplier) {
		em.remove(supplier);

	}

	@Override
	public List<Supplier> findAll() {
		return supplierRepo.findAll();
	}

	@Override
	public Page<Supplier> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public Supplier getSupplierByName(Supplier name) {
		// TODO Auto-generated method stub
		return null;
	}

}
