package com.tecxis.commons.persistence.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;


public class SupplyContractId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
	private Supplier supplier;	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Contract contract;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")				
	private Staff staff;			
						
	public SupplyContractId() {
		super();
	}
		

	public SupplyContractId(Supplier supplier, Contract contract, Staff staff) {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SupplyContractId)) {
			return false;
		}
	
		
		SupplyContractId castOther = (SupplyContractId)other;
		
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
			hash = hash * prime + this.getSupplier().hashCode();
		
		if (this.getContract() != null) {
			hash = hash * prime + this.getContract().hashCode();
		}
		
		if  (this.getStaff() != null)
			hash = hash * prime + this.getStaff().hashCode();
		
		return hash;
	}
	
	@Override
	public String toString() {
		
		Supplier supplier = this.getSupplier();
		Contract contract = this.getContract();
		Client client = null;
		if (contract != null)
			client = contract.getClient();
		Staff staff = this.getStaff();
		
		return "["+ this.getClass().getName() +
				"[supplierId=" + (supplier != null ? supplier.getId() : " null" ) +
				", contractId=" + (contract != null ? contract.getId() : "null") +
				", clientId=" + (client != null ? client.getId() : "null") +
				", staffId=" + (staff != null ? staff.getId() : "null") + 
				"]]";
	}
}
