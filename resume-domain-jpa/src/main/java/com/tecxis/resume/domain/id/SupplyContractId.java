package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SupplyContractId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="SUPPLIER_ID")	
	private long supplierId;	
			
	private ContractId contractId; // corresponds to PK type of Contract

	@Column(name="STAFF_ID")	
	private long staffId;			
						
	public SupplyContractId() {
	}
	
	public SupplyContractId(long supplierId, ContractId contractId, long staffId) {
		this();
		this.supplierId = supplierId;
		this.contractId = contractId;
		this.staffId = staffId;
	}
	
	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public ContractId getContractId() {
		return contractId;
	}

	public void setContractId(ContractId contractId) {
		this.contractId = contractId;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contractId == null) ? 0 : contractId.hashCode());
		result = prime * result + (int) (staffId ^ (staffId >>> 32));
		result = prime * result + (int) (supplierId ^ (supplierId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplyContractId other = (SupplyContractId) obj;
		if (contractId == null) {
			if (other.contractId != null)
				return false;
		} else if (!contractId.equals(other.contractId))
			return false;
		if (staffId != other.staffId)
			return false;
		if (supplierId != other.supplierId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		ContractId contractId = this.getContractId(); 
		
		return this.getClass().getName() + "@" + this.hashCode() +
				"[supplierId=" + this.getSupplierId() +
				", contractId=" + (contractId != null ? contractId.getContractId() : "null") +
				", clientId=" + (contractId != null ? contractId.getClientId() : "null") +
				", staffId=" + this.getStaffId() + 
				"]";
	}
}
