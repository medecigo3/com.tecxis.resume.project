package com.tecxis.resume.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(SupplyContract supplyContract) {
		em.merge(supplyContract);

	}

	@Override
	public void add(SupplyContract supplyContract) {
		em.persist(supplyContract);

	}

	@Override
	public void delete(SupplyContract supplyContract) {
		em.remove(supplyContract);

	}

	@Override
	public List<SupplyContract> findAll() {
		return supplyContractRepo.findAll();
	}

	@Override
	public Page<SupplyContract> findAll(Pageable pageable) {
		return findAll(pageable);
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
