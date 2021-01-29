package com.tecxis.resume.persistence;


import java.util.List;

import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;

public interface EmploymentContractDao extends Dao<EmploymentContract> {

	public List <EmploymentContract> findByStaff(Staff staff);

	public List <EmploymentContract> findBySupplier(Supplier supplier);

	public List <EmploymentContract> findByStaffAndSupplier(Staff staff, Supplier supplier);

	public EmploymentContract findByIdAndStaffAndSupplier(int id, Staff staff, Supplier supplier);

}
