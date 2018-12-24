package com.tecxis.resume;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;

/**
 * The primary key class for the CONTRACT database table.
 * 
 */
public class ContractPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private long clientId;
	
	private long contractId;
	
	private long staffId;

	private long supplierId;	
	
	public ContractPK(long clientId, long contractId, long staffId, long supplierId) {
		this();
		this.contractId = contractId;
		this.clientId = clientId;
		this.supplierId = supplierId;		
		this.staffId = staffId;
	}

	private ContractPK() {
		super();
	}	
	
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}	

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	
	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContractPK)) {
			return false;
		}
		ContractPK castOther = (ContractPK)other;
		return 
			(this.clientId == castOther.clientId)
			&& (this.supplierId == castOther.supplierId)
			&& (this.contractId == castOther.contractId)
			&& (this.staffId == castOther.staffId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.clientId ^ (this.clientId >>> 32)));
		hash = hash * prime + ((int) (this.supplierId ^ (this.supplierId >>> 32)));
		hash = hash * prime + ((int) (this.contractId ^ (this.contractId >>> 32)));
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		
		return hash;
	}
	
	@Override
	public String toString() {		
		return reflectionToString(this);
	}
}