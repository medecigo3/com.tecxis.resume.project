package com.tecxis.resume;

import java.io.Serializable;

/**
 * The primary key class for the SUPPLIER database table.
 * 
 */
class SupplierPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;


	private long supplierId;

	private long staffId;

	public SupplierPK(long supplierId, long staffId) {
		this();
		this.supplierId = supplierId;
		this.staffId = staffId;
	}
	
	private SupplierPK() {
		super();
	}
	public long getSupplierId() {
		return this.supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
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
		if (!(other instanceof SupplierPK)) {
			return false;
		}
		SupplierPK castOther = (SupplierPK)other;
		return 
			this.supplierId== castOther.supplierId
			&& (this.staffId == castOther.staffId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + + ((int) (this.supplierId ^ (this.supplierId >>> 32)));
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		
		return hash;
	}
}