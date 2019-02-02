package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tecxis.resume.Contract.ContractPK;


/**
 * The persistent class for the CONTRACT database table.
 * 
 */
@Entity
@IdClass(ContractPK.class)
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static class ContractPK implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@Column(name="CONTRACT_ID")	
		@SequenceGenerator(name="CONTRACT_CONTRACTID_GENERATOR", sequenceName="CONTRACT_SEQ", allocationSize=1, initialValue=1)
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_CONTRACTID_GENERATOR")
		private long contractId;
		
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
		private Client client;		

		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")			
		private Supplier supplier;	
		
		public ContractPK(long contractId, Client client,  Supplier supplier) {
			this();
			this.contractId = contractId;
			this.client = client;
			this.supplier = supplier;		
		
		}

		private ContractPK() {
			super();
		}	
				
		public long getContractId() {
			return contractId;
		}

		public void setContractId(long contractId) {
			this.contractId = contractId;
		}

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}

		public Supplier getSupplier() {
			return supplier;
		}

		public void setSupplier(Supplier supplier) {
			this.supplier = supplier;
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
				(this.client.getClientId() == castOther.getClient().getClientId())
				&& (this.supplier.getSupplierId() == castOther.getSupplier().getSupplierId())
				&& (this.contractId == castOther.contractId)
				&& (this.supplier.getStaff().getStaffId() == castOther.getSupplier().getStaff().getStaffId());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.client.getClientId() ^ (this.client.getClientId() >>> 32)));
			hash = hash * prime + ((int) (this.supplier.getSupplierId()  ^ (this.supplier.getSupplierId()  >>> 32)));
			hash = hash * prime + ((int) (this.contractId ^ (this.contractId >>> 32)));
			hash = hash * prime + ((int) (this.supplier.getStaff().getStaffId() ^ (this.supplier.getStaff().getStaffId() >>> 32)));
			
			return hash;
		}
		
		@Override
		public String toString() {
			return "ContractPK=[contractId=" + this.getContractId() + 
					", clientId=" + (this.getClient() != null ? this.getClient().getClientId() : "null") + 
					", supplierId=" + (this.getSupplier() != null ? this.getSupplier().getSupplierId() : " null" ) + 
					", staffId=" + (this.getSupplier() != null ? ( this.getSupplier().getStaff() != null ? this.getSupplier().getStaff().getStaffId() : "null"  ) : " null" ) + "]";
		}

	}
	
	@Id
	@Column(name="CONTRACT_ID")	
	@SequenceGenerator(name="CONTRACT_CONTRACTID_GENERATOR", sequenceName="CONTRACT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_CONTRACTID_GENERATOR")
	private long contractId;
	
	/**
	 * bi-directional many-to-one association to Client. 
	 * In SQL terms, Contract is the "owner" of this relationship with Client as it contains the relationship's foreign key
	 * In OO terms, this Client "signed" this Contract.
	 */
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Client client;		

	/**
	 * bi-directional many-to-one association to Supplier. 
	 * In SQL terms, Contract is the "owner" of this relationship with Supplier as it contains the relationship's foreign keys
	 * In OO terms, this Supplier "holds" this Contract.
	 */
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Supplier supplier;	
			
	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	/**
	 * bi-directional one-to-many association to ContractServiceAgreement
	 * In OO terms, this Contract "engages" these ContractServiceAgreements
	 */
	@OneToMany(mappedBy="contractServiceAgreementId.contract", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <ContractServiceAgreement> contractServiceAgreements;


	public Contract() {
		this.contractServiceAgreements = new ArrayList <> ();
	}
	
	public long getContractId() {
		return this.contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
		
	public List<ContractServiceAgreement> getContractServiceAgreements() {
		return contractServiceAgreements;
	}

	public void setContractServiceAgreements(List<ContractServiceAgreement> contractServiceAgreements) {
		this.contractServiceAgreements = contractServiceAgreements;
	}

	
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