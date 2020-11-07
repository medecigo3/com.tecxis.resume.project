package com.tecxis.resume;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.tecxis.commons.persistence.id.SupplyContractId;

/**
 * The persistent class for SUPPLY_CONTRACT database table.
 * 
 */
@Entity
@Table(name="SUPPLY_CONTRACT")
public class SupplyContract implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@NotNull
	private SupplyContractId supplyContractId;
	
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
	
	public SupplyContract(SupplyContractId supplyContractId) {
		super();
		this.supplyContractId = supplyContractId;
	}

	public SupplyContractId getSupplyContractId() {
		return supplyContractId;
	}

	public void setSupplyContractId(SupplyContractId supplyContractId) {
		this.supplyContractId = supplyContractId;
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
		
		if	(this.getSupplyContractId().getSupplier() != null && castOther.getSupplyContractId().getSupplier() != null) {
			if (this.getSupplyContractId().getContract() != null && castOther.getSupplyContractId().getContract() != null) {
				if (this.getSupplyContractId().getStaff()    != null && castOther.getSupplyContractId().getStaff()    != null) {				
			
					return 	this.getSupplyContractId().getSupplier().equals(castOther.getSupplyContractId().getSupplier()) && 
							this.getSupplyContractId().getContract().equals(castOther.getSupplyContractId().getContract()) && 
							this.getSupplyContractId().getStaff().equals(castOther.getSupplyContractId().getStaff());
				} else return false;
			} else return false;			
		}else return false;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;		
		
		SupplyContractId supplyContractId = this.getSupplyContractId();
		if (supplyContractId != null) {
			if (supplyContractId.getSupplier() != null)
				hash = hash * prime + supplyContractId.hashCode();
			
			if (this.getSupplyContractId().getContract() != null) 
				hash = hash * prime + supplyContractId.hashCode();			
			
			if  (this.getSupplyContractId().getStaff() != null)
				hash = hash * prime + supplyContractId.hashCode();
		}
		
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[supplierId=" + (this.getSupplyContractId().getSupplier() != null ? this.getSupplyContractId().getSupplier().getId() : " null" ) +
				", contractId=" + (this.getSupplyContractId().getContract() != null ? this.getSupplyContractId().getContract().getId() : "null") +
				", clientId=" + (this.getSupplyContractId().getContract().getClient() != null ? this.getSupplyContractId().getContract().getClient().getId() : "null") +
				", staffId=" + (this.getSupplyContractId().getStaff() != null ? this.getSupplyContractId().getStaff().getId() : "null") + 
				"]]";
	}
}
