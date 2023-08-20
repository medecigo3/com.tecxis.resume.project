package com.tecxis.resume.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.id.SupplyContractId;

public interface SupplyContractRepository extends JpaRepository<SupplyContract, SupplyContractId> {
	
	public List <SupplyContract> getSupplyContractByStartDate(Date startDate);
	
	public List <SupplyContract> getSupplyContractByEndDate(Date endDate);
		
	public List <SupplyContract> findBySupplierOrderByStartDateAsc(Supplier supplier);
	
	public List <SupplyContract> findByStaffOrderByStartDateAsc(Staff staff);
	
	public List <SupplyContract> findByContractOrderByStartDateAsc(Contract contract);
	
	public List <SupplyContract> findBySupplierAndStartDateAndEndDateOrderByStartDateAsc(Supplier supplier, Date startDate, Date endDate);

	@Query("select sc from SupplyContract sc WHERE sc.contract.client = ?1  AND sc.supplier = ?2 ORDER BY sc.startDate")
	public List <SupplyContract> findByClientAndSupplierOrderByStartDateAsc(Client client, Supplier supplier);
	
	public SupplyContract findByContractAndSupplierAndStaff(Contract contract, Supplier supplier, Staff staff);
	
	public List <SupplyContract> findByContractAndSupplierOrderByStartDateAsc(Contract contract, Supplier supplier);

	SupplyContract findByStaffAndSupplierAndContract(Staff staff, Supplier supplier, Contract contract);//RES-52
}
