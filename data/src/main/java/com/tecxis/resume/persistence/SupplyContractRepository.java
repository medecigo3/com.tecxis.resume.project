package com.tecxis.resume.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.Client;
import com.tecxis.resume.Contract;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplyContract;
import com.tecxis.resume.SupplyContract.SupplyContractId;

public interface SupplyContractRepository extends JpaRepository<SupplyContract, SupplyContractId> {
	
	public List <SupplyContract> getSupplyContractByStartDate(Date startDate);
	
	public List <SupplyContract> getSupplyContractByEndDate(Date endDate);
		
	public List <SupplyContract> findBySupplyContractId_SupplierOrderByStartDateAsc(Supplier supplier);
	
	public List <SupplyContract> findBySupplyContractId_StaffOrderByStartDateAsc(Staff staff);
	
	public List <SupplyContract> findBySupplyContractId_ContractOrderByStartDateAsc(Contract contract);
	
	public List <SupplyContract> findBySupplyContractId_SupplierAndStartDateAndEndDateOrderByStartDateAsc(Supplier supplier, Date startDate, Date endDate);

	@Query("select sc from SupplyContract sc WHERE sc.supplyContractId.contract.client = ?1  AND sc.supplyContractId.supplier = ?2 ORDER BY sc.startDate")
	public List <SupplyContract> findByClientAndSupplierOrderByStartDateAsc(Client client, Supplier supplier);
	
	public SupplyContract findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(Contract contract, Supplier supplier, Staff staff);

}
