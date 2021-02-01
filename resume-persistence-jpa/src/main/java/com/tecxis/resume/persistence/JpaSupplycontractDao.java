package com.tecxis.resume.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;

public class JpaSupplycontractDao implements SupplyContractDao {

	@Override
	public void save(SupplyContract k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(SupplyContract k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(SupplyContract k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SupplyContract> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SupplyContract> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplyContract> getSupplyContractByStartDate(Date startDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplyContract> getSupplyContractByEndDate(Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date findBySupplierOrderByStartDateAsc(Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplyContract> findByStaffOrderByStartDateAsc(Staff staff) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplyContract> findByContractOrderByStartDateAsc(Contract contract) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplyContract> findBySupplierAndStartDateAndEndDateOrderByStartDateAsc(Supplier supplier,
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplyContract> findByClientAndSupplierOrderByStartDateAsc(Client client, Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SupplyContract findByContractAndSupplierAndStaff(Contract contract, Supplier supplier, Staff staff) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplyContract> findByContractAndSupplierOrderByStartDateAsc(Contract contract, Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

}
