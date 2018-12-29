package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the "SERVICE" database table.
 * 
 */
@Entity
@Table(name="\"SERVICE\"")
@IdClass(Service.ServicePK.class)
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static class ServicePK implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private long contractId;		
		private long clientId;		
		private long supplierId;		
		private long staffId;	
		private long serviceId;

		public ServicePK(long contractId, long clientId, long supplierId, long staffId, long serviceId) {
			this();
			this.contractId = contractId;
			this.clientId = clientId;
			this.supplierId = supplierId;
			this.staffId = staffId;
			this.serviceId = serviceId;
		}

		public ServicePK() {
			super();
		}
		
		public long getContractId() {
			return contractId;
		}

		public void setContractId(long contractId) {
			this.contractId = contractId;
		}

		public long getClientId() {
			return clientId;
		}

		public void setClientId(long clientId) {
			this.clientId = clientId;
		}

		public long getSupplierId() {
			return supplierId;
		}

		public void setSupplierId(long supplierId) {
			this.supplierId = supplierId;
		}

		public long getServiceId() {
			return serviceId;
		}

		public void setServiceId(long serviceId) {
			this.serviceId = serviceId;
		}

		public long getStaffId() {
			return staffId;
		}

		public void setStaffId(long staffId) {
			this.staffId = staffId;
		}

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof Contract.ContractPK)) {
				return false;
			}
			ServicePK castOther = (ServicePK)other;
			return 
				(this.clientId == castOther.clientId)
				&& (this.supplierId == castOther.supplierId)
				&& (this.serviceId == castOther.serviceId)
				&& (this.contractId == castOther.contractId)
				&& (this.staffId == castOther.staffId);
		}
		
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.clientId ^ (this.clientId >>> 32)));
			hash = hash * prime + ((int) (this.supplierId ^ (this.supplierId >>> 32)));
			hash = hash * prime + ((int) (this.serviceId ^ (this.serviceId >>> 32)));
			hash = hash * prime + ((int) (this.contractId ^ (this.contractId >>> 32)));
			hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
			
			return hash;
		}
		
		@Override
		public String toString() {		
			return reflectionToString(this);
		}
		
	}

	@Id
	@SequenceGenerator(name="SERVICE_SERVICEID_GENERATOR", sequenceName="SERVICE_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICE_SERVICEID_GENERATOR")
	@Column(name="SERVICE_ID")
	private long serviceId;
	
	@Column(name="CONTRACT_ID", insertable=false, updatable=false)
	private long contractId;		
	
	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;		
	
	@Column(name="SUPPLIER_ID", insertable=false, updatable=false)
	private long supplierId;		

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;		

	@Column(name="\"DESC\"")
	private String desc;

	private String name;

					
	/**
	 * bi-directional many-to-one association to Contract.
	 * In SQL terms, Service is the "owner" of this relationship with Contract as it contains the relationship's foreign key
	 * In OO terms, this Service "engages" Contracts 
	 */	
	@ManyToOne(fetch=FetchType.EAGER)
	/**Hibernate BasicExtractor returns component values in alphabetical order. 
	 * The ascending column order below allows the AbstractComponentTuplizer properly
	 * set the component's id property values*/
	@JoinColumn(name="CLIENT_ID", insertable=false, updatable=false)
	@JoinColumn(name="CONTRACT_ID", insertable=false, updatable=false)
	@JoinColumn(name="STAFF_ID", insertable=false, updatable=false)
	@JoinColumn(name="SUPPLIER_ID", insertable=false, updatable=false)			
	private Contract contract;

	public Service() {
	}

	public long getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contractId = contract.getContractId();
		this.clientId = contract.getClientId();
		this.staffId = contract.getStaffId();
		this.supplierId = contract.getSupplierId();
		this.contract = contract;
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