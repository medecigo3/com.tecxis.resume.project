package com.tecxis.resume.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.repository.SupplyContractRepository;

@Repository("supplyContractDao")
public class JpaSupplyContractDao implements SupplyContractDao {
	
	@Autowired 
	private SupplyContractRepository supplyContractRepo;
	
	@Override
	public void save(SupplyContract supplyContract) {
		supplyContractRepo.save(supplyContract);

	}

	@Override
	public void add(SupplyContract supplyContract) {
		supplyContractRepo.save(supplyContract);

	}

	@Override
	public void delete(SupplyContract supplyContract) {
		supplyContractRepo.delete(supplyContract);

	}

	@Override
	public List<SupplyContract> findAll() {
		return supplyContractRepo.findAll();
	}

	@Override
	public Page<SupplyContract> findAll(Pageable pageable) {
		return supplyContractRepo.findAll(pageable);
	}

	@Override
	public List<SupplyContract> getSupplyContractByStartDate(Date startDate) {
		return supplyContractRepo.getSupplyContractByStartDate(startDate);
	}

	@Override
	public List<SupplyContract> getSupplyContractByEndDate(Date endDate) {
		return supplyContractRepo.getSupplyContractByEndDate(endDate);
	}

	@Override
	public List<SupplyContract> findBySupplierOrderByStartDateAsc(Supplier supplier) {
		return supplyContractRepo.findBySupplierOrderByStartDateAsc(supplier);
	}

	@Override
	public List<SupplyContract> findByStaffOrderByStartDateAsc(Staff staff) {
		return supplyContractRepo.findByStaffOrderByStartDateAsc(staff);
	}

	@Override
	public List<SupplyContract> findByContractOrderByStartDateAsc(Contract contract) {
		return supplyContractRepo.findByContractOrderByStartDateAsc(contract);
	}

	@Override
	public List<SupplyContract> findBySupplierAndStartDateAndEndDateOrderByStartDateAsc(Supplier supplier,	Date startDate, Date endDate) {
		return supplyContractRepo.findBySupplierAndStartDateAndEndDateOrderByStartDateAsc(supplier, startDate, endDate);
	}

	@Override
	public List<SupplyContract> findByClientAndSupplierOrderByStartDateAsc(Client client, Supplier supplier) {
		return supplyContractRepo.findByClientAndSupplierOrderByStartDateAsc(client, supplier) ;
	}

	@Override
	public SupplyContract findByContractAndSupplierAndStaff(Contract contract, Supplier supplier, Staff staff) {
		return supplyContractRepo.findByContractAndSupplierAndStaff(contract, supplier, staff);
	}

	@Override
	public List<SupplyContract> findByContractAndSupplierOrderByStartDateAsc(Contract contract, Supplier supplier) {
		return supplyContractRepo.findByContractAndSupplierOrderByStartDateAsc(contract, supplier) ;
	}

}
