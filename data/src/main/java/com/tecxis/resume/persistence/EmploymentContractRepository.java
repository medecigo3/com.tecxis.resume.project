package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.EmploymentContract;
import com.tecxis.resume.EmploymentContract.EmploymentContractId;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;

public interface EmploymentContractRepository extends JpaRepository<EmploymentContract, EmploymentContractId> {
	
	public List <EmploymentContract> findByEmploymentContractId_Staff(Staff staff);
	
	public List <EmploymentContract> findByEmploymentContractId_Supplier(Supplier supplier);
	
	public EmploymentContract findByEmploymentContractId_StaffAndEmploymentContractId_Supplier(Staff staff, Supplier supplier);
}
