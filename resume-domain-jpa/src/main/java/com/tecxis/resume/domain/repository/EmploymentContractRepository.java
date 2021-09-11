package com.tecxis.resume.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;

public interface EmploymentContractRepository extends JpaRepository<EmploymentContract, Long> {
	
	public List <EmploymentContract> findByStaff(Staff staff);
	
	public List <EmploymentContract> findBySupplier(Supplier supplier);
	
	public List <EmploymentContract> findByStaffAndSupplier(Staff staff, Supplier supplier);
	
	public EmploymentContract findByIdAndStaffAndSupplier(long id, Staff staff, Supplier supplier);
}
