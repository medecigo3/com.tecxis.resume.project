package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;

@Repository("employmentDao")
public class JpaEmploymentContractDao implements EmploymentContractDao{

	@Autowired
	private EmploymentContractRepository employmentContractRepo;
	
	@Override
	public void save(EmploymentContract employmentContract) {
		employmentContractRepo.save(employmentContract);
		
	}

	@Override
	public void add(EmploymentContract employmentContract) {
		employmentContractRepo.save(employmentContract);
		
	}

	@Override
	public void delete(EmploymentContract employmentContract) {
		employmentContractRepo.delete(employmentContract);
		
	}

	@Override
	public List<EmploymentContract> findAll() {
		return employmentContractRepo.findAll();
	}

	@Override
	public Page<EmploymentContract> findAll(Pageable pageable) {
		return employmentContractRepo.findAll(pageable);
	}

	@Override
	public List<EmploymentContract> findByStaff(Staff staff) {
		return employmentContractRepo.findByStaff(staff);
	}

	@Override
	public List<EmploymentContract> findBySupplier(Supplier supplier) {
		return employmentContractRepo.findBySupplier(supplier);
	}

	@Override
	public List<EmploymentContract> findByStaffAndSupplier(Staff staff, Supplier supplier) {
		return employmentContractRepo.findByStaffAndSupplier(staff, supplier);
	}

	@Override
	public EmploymentContract findByIdAndStaffAndSupplier(int id, Staff staff, Supplier supplier) {
		return employmentContractRepo.findByIdAndStaffAndSupplier(id, staff, supplier);
	}

}
