package com.tecxis.resume.ver2;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SUPPLIER database table.
 * 
 */
@Embeddable
public class SupplierPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SUPPLIER_ID")
	private String supplierId;

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;

	public SupplierPK() {
	}
	public String getSupplierId() {
		return this.supplierId;
	}
	public void setSupplierId(String supplierId) {
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
			this.supplierId.equals(castOther.supplierId)
			&& (this.staffId == castOther.staffId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.supplierId.hashCode();
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		
		return hash;
	}
}