package com.tecxis.resume.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.commons.persistence.id.SupplyContractId;
import com.tecxis.resume.Client;
import com.tecxis.resume.Contract;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplyContract;

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

}
