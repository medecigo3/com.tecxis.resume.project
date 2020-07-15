package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.EmploymentContract;
import com.tecxis.resume.EmploymentContract.EmploymentContractPK;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;

public interface EmploymentContractRepository extends JpaRepository<EmploymentContract, EmploymentContractPK> {
	
	public List <EmploymentContract> findByStaff(Staff staff);
	
	public List <EmploymentContract> findBySupplier(Supplier supplier);
	
	public List <EmploymentContract> findByStaffAndSupplier(Staff staff, Supplier supplier);
	
	public EmploymentContract findByIdAndStaffAndSupplier(long id, Staff staff, Supplier supplier);
}
