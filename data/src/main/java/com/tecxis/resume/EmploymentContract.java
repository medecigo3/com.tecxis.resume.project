package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the EMPLOYMENT_CONTRACT database table.
 * 
 */
@Entity
@Table(name="EMPLOYMENT_CONTRACT")
public class EmploymentContract implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	public static class EmploymentContractId implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")	
		private Staff staff;
		
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
		private Supplier supplier;

		public EmploymentContractId() {
			super();
		}

		public EmploymentContractId(Staff staff, Supplier supplier) {
			super();
			this.staff = staff;
			this.supplier = supplier;
		}

		public Staff getStaff() {
			return staff;
		}

		public void setStaff(Staff staff) {
			this.staff = staff;
		}

		public Supplier getSupplier() {
			return supplier;
		}

		public void setSupplier(Supplier supplier) {
			this.supplier = supplier;			
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof EmploymentContractId)) {
				return false;
			}
			EmploymentContractId castOther = (EmploymentContractId)other;
			return
				(this.supplier.getId() == castOther.getSupplier().getId()) &&				
				(this.getStaff().getId() == castOther.getStaff().getId());	
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;			
			hash = hash * prime + ((int) (this.supplier.getId()  ^ (this.supplier.getId()  >>> 32)));
			hash = hash * prime + ((int) (this.getStaff().getId() ^ (this.getStaff().getId() >>> 32)));
			return hash;
		}

		@Override
		public String toString() {
			return "["+ this.getClass().getName() +
					"[supplierId=" + (this.getSupplier() != null ? this.getSupplier().getId() : " null" ) +
					", staffId=" + (this.staff != null ? this.staff.getId() : "null") + 
					"]]";
		}
				
	}
	
	@EmbeddedId
	private EmploymentContractId employmentContractId;

	public EmploymentContract() {
		super();
	}
	
	public EmploymentContract(EmploymentContractId employmentContractId) {
		super();
		this.employmentContractId = employmentContractId;
	}

	public EmploymentContractId getEmploymentContractId() {
		return employmentContractId;
	}

	public void setEmploymentContractId(EmploymentContractId employmentContractId) {
		this.employmentContractId = employmentContractId;
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmploymentContract)) {
			return false;
		}
		EmploymentContract castOther = (EmploymentContract)other;
		return
			(this.getEmploymentContractId().supplier.getId() == castOther.getEmploymentContractId().getSupplier().getId()) &&				
			(this.getEmploymentContractId().getStaff().getId() == castOther.getEmploymentContractId().getStaff().getId());	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;			
		hash = hash * prime + ((int) (this.getEmploymentContractId().getSupplier().getId()  ^ (this.getEmploymentContractId().getSupplier().getId()  >>> 32)));
		hash = hash * prime + ((int) (this.getEmploymentContractId().getStaff().getId() ^ (this.getEmploymentContractId().getStaff().getId() >>> 32)));
		return hash;
	}

	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[supplierId=" + (this.getEmploymentContractId().getSupplier() != null ? this.getEmploymentContractId().getSupplier().getId() : " null" ) +
				", staffId=" + (this.getEmploymentContractId().getStaff() != null ? this.getEmploymentContractId().getStaff().getId() : "null") + 
				"]]";
	}
}
