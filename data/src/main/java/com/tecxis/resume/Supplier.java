package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.tecxis.resume.Supplier.SupplierPK;


/**
 * The persistent class for the SUPPLIER database table.
 * 
 */
@Entity
@IdClass(SupplierPK.class)
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class SupplierPK implements Serializable {

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
	
	@Id
	@Column(name="SUPPLIER_ID")
	@SequenceGenerator(name="SUPPLIER_SUPPLIERID_GENERATOR", sequenceName="SUPPLIER_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUPPLIER_SUPPLIERID_GENERATOR")
	private long supplierId;

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;

	private String name;

//	bi-directional many-to-one association to Contract. 
//	In SQL terms, Contract is the "owner" of this relationship with Supplier as it contains the relationship's foreign key
//	@OneToMany(mappedBy="supplier")
	/**
	 * uni-directional one-to-many association to Contract. 
	 * In OO terms, this Supplier "holds" Contracts.
	 */
	@OneToMany
	@JoinColumns({
		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID"),
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	})	
	private List<Contract> contracts;

	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name="STAFF_ID")
//	private Staff staff;

	public Supplier() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
//		contract.setSupplier(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
//		contract.setSupplier(null);

		return contract;
	}

//	public Staff getStaff() {
//		return this.staff;
//	}
//
//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}
	
	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}