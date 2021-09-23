package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.tecxis.resume.domain.id.SupplyContractId;

/**
 * The persistent class for SUPPLY_CONTRACT database table.
 * 
 */
@Entity
@Table(name=SupplyContract.SUPPLY_CONTRACT_TABLE)
public class SupplyContract implements Serializable {

	private static final long serialVersionUID = 1L;
	final public static String SUPPLY_CONTRACT_TABLE ="SUPPLY_CONTRACT";
	
	@EmbeddedId
	private SupplyContractId id;

	/**
	 * bi-directional many-to-one association to Supplier. 
	 * In SQL terms, ContractSupply is the "owner" of this relationship with Contract as it contains the relationship's foreign keys
	 * In OO terms, the supplier who "AGREES" to this ContractSupply.
	 */
	@MapsId("supplierId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
	private Supplier supplier;	
	
	/**
	 * bi-directional many-to-one association to Contract. 
	 * In SQL terms, ContractSupply is the "owner" of this relationship with Contract as it contains the relationship's foreign key
	 * In OO terms, the client who "COMMITS TO" this SupplyContract.
	 */
	@MapsId("contractId")
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Contract contract;
	
	/**
	 * bi-directional many-to-one association to Staff. 
	 * In SQL terms, ContractSupply is the "owner" of this relationship with Staff as it contains the relationship's foreign key
	 * In OO terms, the staff who "WORKS IN"  this SupplyContract.
	 */
	@MapsId("staffId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")				
	private Staff staff;		
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	@NotNull	
	private Date startDate;

	
	public SupplyContract() {
		super();	
		this.id = new SupplyContractId();
	}

	public SupplyContract(Supplier supplier, Contract contract, Staff staff) {
		this();
		this.supplier = supplier;
		this.contract = contract;
		this.staff = staff;
	}

	public SupplyContractId getId() {
		return id;
	}

	public void setId(SupplyContractId id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplyContract other = (SupplyContract) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {		
		return  this.getClass().getName() +
				 "[" + this.getId() + 
				"]";
	}
}
