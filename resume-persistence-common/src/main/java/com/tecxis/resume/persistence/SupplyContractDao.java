package com.tecxis.resume.persistence;

import java.util.List;
import java.util.Date;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.SupplyContract;

public interface SupplyContractDao extends Dao<SupplyContract> {

	public List<SupplyContract> getSupplyContractByStartDate(Date startDate);

	public List<SupplyContract> getSupplyContractByEndDate(Date endDate);

	public Date findBySupplierOrderByStartDateAsc(Supplier supplier);

	public List<SupplyContract> findByStaffOrderByStartDateAsc(Staff staff);

	public List<SupplyContract> findByContractOrderByStartDateAsc(Contract contract);

	public List<SupplyContract> findBySupplierAndStartDateAndEndDateOrderByStartDateAsc(Supplier supplier, Date startDate, 	Date endDate);

	public List<SupplyContract> findByClientAndSupplierOrderByStartDateAsc(Client client, Supplier supplier);

	public SupplyContract findByContractAndSupplierAndStaff(Contract contract, Supplier supplier, Staff staff);

	public List<SupplyContract> findByContractAndSupplierOrderByStartDateAsc(Contract contract, Supplier supplier);

}
