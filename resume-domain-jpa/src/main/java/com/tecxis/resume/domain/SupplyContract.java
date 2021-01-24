package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="SUPPLY_CONTRACT")
@IdClass(SupplyContractId.class)
public class SupplyContract implements Serializable {

	private static final long serialVersionUID = 1L;
	

	/**
	 * bi-directional many-to-one association to Supplier. 
	 * In SQL terms, ContractSupply is the "owner" of this relationship with Contract as it contains the relationship's foreign keys
	 * In OO terms, the supplier who "AGREES" to this ContractSupply.
	 */
	@Id
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
	private Supplier supplier;	
	
	/**
	 * bi-directional many-to-one association to Contract. 
	 * In SQL terms, ContractSupply is the "owner" of this relationship with Contract as it contains the relationship's foreign key
	 * In OO terms, the client who "COMMITS TO" this SupplyContract.
	 */
	@Id
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Contract contract;
	
	/**
	 * bi-directional many-to-one association to Staff. 
	 * In SQL terms, ContractSupply is the "owner" of this relationship with Staff as it contains the relationship's foreign key
	 * In OO terms, the staff who "WORKS IN"  this SupplyContract.
	 */
	@Id
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
	}

	public SupplyContract(Supplier supplier, Contract contract, Staff staff) {
		super();
		this.supplier = supplier;
		this.contract = contract;
		this.staff = staff;
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
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SupplyContract)) {
			return false;
		}
		SupplyContract castOther = (SupplyContract)other;
		
		if	(this.getSupplier() != null && castOther.getSupplier() != null) {
			if (this.getContract() != null && castOther.getContract() != null) {
				if (this.getStaff()    != null && castOther.getStaff()    != null) {				
			
					return 	this.getSupplier().equals(castOther.getSupplier()) && 
							this.getContract().equals(castOther.getContract()) && 
							this.getStaff().equals(castOther.getStaff());
				} else return false;
			} else return false;			
		}else return false;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;			
		
		if (this.getSupplier() != null)
			hash = hash * prime + getSupplier() .hashCode();
		
		if (this.getContract() != null) 
			hash = hash * prime + getContract().hashCode();			
		
		if  (this.getStaff() != null)
			hash = hash * prime + getStaff().hashCode();
		
		
		return hash;
	}
	
	@Override
	public String toString() {		
		Supplier supplier = null;
		Contract contract = null;
		Client client = null;
		Staff staff = null;
			
		supplier = this.getSupplier();
		contract = this.getContract();
		client = null;
		if (contract != null)
			client = contract.getClient();
		staff = this.getStaff();
		
		
		return "["+ this.getClass().getName() +
				"[supplierId=" + (supplier != null ? supplier.getId() : " null" ) +
				", contractId=" + (contract != null ? contract.getId() : "null") +
				", clientId=" + (client != null ? client.getId() : "null") +
				", staffId=" + (staff != null ? staff.getId() : "null") + 
				"]]";
	}
}
