package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CONTRACT database table.
 * 
 */
@Embeddable
public class ContractPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;

	@Column(name="SUPPLIER_ID", insertable=false, updatable=false)
	private String supplierId;

	@Column(name="SERVICE_ID", insertable=false, updatable=false)
	private long serviceId;

	@Column(name="CONTRACT_ID")
	private long contractId;

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;

	public ContractPK() {
	}
	public long getClientId() {
		return this.clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public String getSupplierId() {
		return this.supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public long getServiceId() {
		return this.serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	public long getContractId() {
		return this.contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	public long getStaffId() {
		return this.staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
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
			&& this.supplierId.equals(castOther.supplierId)
			&& (this.serviceId == castOther.serviceId)
			&& (this.contractId == castOther.contractId)
			&& (this.staffId == castOther.staffId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.clientId ^ (this.clientId >>> 32)));
		hash = hash * prime + this.supplierId.hashCode();
		hash = hash * prime + ((int) (this.serviceId ^ (this.serviceId >>> 32)));
		hash = hash * prime + ((int) (this.contractId ^ (this.contractId >>> 32)));
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		
		return hash;
	}
}