package com.tecxis.resume;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for SUPPLY_CONTRACT database table.
 * 
 */
@Entity
@Table(name="SUPPLY_CONTRACT")
public class SupplyContract implements Serializable {
	

	private static final long serialVersionUID = 1L;

	public static class SupplyContractId implements Serializable {
		
		private static final long serialVersionUID = 1L;


		/**
		 * bi-directional many-to-one association to Supplier. 
		 * In SQL terms, ContractSupply is the "owner" of this relationship with Contract as it contains the relationship's foreign keys
		 * In OO terms, the supplier who "AGREES" to this ContractSupply.
		 */
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
		private Supplier supplier;	
		
		/**
		 * bi-directional many-to-one association to Contract. 
		 * In SQL terms, ContractSupply is the "owner" of this relationship with Contract as it contains the relationship's foreign key
		 * In OO terms, the client who "COMMITS TO" this SupplyContract.
		 */
		@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
		private Contract contract;
		
		/**
		 * bi-directional many-to-one association to Staff. 
		 * In SQL terms, ContractSupply is the "owner" of this relationship with Staff as it contains the relationship's foreign key
		 * In OO terms, the staff who "WORKS IN"  this SupplyContract.
		 */
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
			return
				(this.supplier.getId() == castOther.getSupplier().getId()) &&
				(this.contract.getId() == castOther.getContract().getId()) &&
				(this.contract.getClient().getId() == castOther.getContract().getClient().getId()) &&
				(this.getStaff().getId() == castOther.getStaff().getId());				
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;			
			hash = hash * prime + ((int) (this.supplier.getId()  ^ (this.supplier.getId()  >>> 32)));
			hash = hash * prime + ((int) (this.contract.getId() ^ (this.contract.getId() >>> 32)));
			hash = hash * prime + ((int) (this.contract.getClient().getId() ^ (this.contract.getClient().getId() >>> 32)));
			hash = hash * prime + ((int) (this.getStaff().getId() ^ (this.getStaff().getId() >>> 32)));
			return hash;
		}
		
		@Override
		public String toString() {
			return "["+ this.getClass().getName() +
					"[supplierId=" + (this.getSupplier() != null ? this.getSupplier().getId() : " null" ) +
					", contractId=" + (this.contract != null ? this.contract.getId() : "null") +
					", clientId=" + (this.contract.getClient() != null ? this.contract.getClient().getId() : "null") +
					", staffId=" + (this.staff != null ? this.staff.getId() : "null") + 
					"]]";
		}
		
	}
	


	
	@EmbeddedId
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
		return
			(this.getSupplyContractId().getSupplier().getId() == castOther.getSupplyContractId().getSupplier().getId()) &&
			(this.getSupplyContractId().getContract().getId() == castOther.getSupplyContractId().getContract().getId()) &&
			(this.getSupplyContractId().getContract().getClient().getId() == castOther.getSupplyContractId().getContract().getClient().getId()) && 
			(this.getSupplyContractId().getStaff().getId() == castOther.getSupplyContractId().getStaff().getId());			
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;			
		hash = hash * prime + ((int) (this.getSupplyContractId().getSupplier().getId()  ^ (this.getSupplyContractId().getSupplier().getId()  >>> 32)));
		hash = hash * prime + ((int) (this.getSupplyContractId().getContract().getId() ^ (this.getSupplyContractId().getContract().getId() >>> 32)));
		hash = hash * prime + ((int) (this.getSupplyContractId().getContract().getClient().getId() ^ (this.getSupplyContractId().getContract().getClient().getId() >>> 32)));
		hash = hash * prime + ((int) (this.getSupplyContractId().getStaff().getId() ^ (this.getSupplyContractId().getStaff().getId() >>> 32)));
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
