package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;

public class JpaEmploymentContractDao implements EmploymentContractDao{

	@Autowired
	private EmploymentContractRepository employmentContractRepo;

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(EmploymentContract employmentContract) {
		em.merge(employmentContract);
		
	}

	@Override
	public void add(EmploymentContract employmentContract) {
		em.persist(employmentContract);
		
	}

	@Override
	public void delete(EmploymentContract employmentContract) {
		em.remove(employmentContract);
		
	}

	@Override
	public List<EmploymentContract> findAll() {
		return employmentContractRepo.findAll();
	}

	@Override
	public Page<EmploymentContract> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmploymentContract> findByStaff(Staff staff) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmploymentContract> findBySupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmploymentContract> findByStaffAndSupplier(Staff staff, Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmploymentContract findByIdAndStaffAndSupplier(int id, Staff staff, Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

}
