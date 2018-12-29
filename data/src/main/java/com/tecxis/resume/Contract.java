package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
	
	@Id
	@Column(name="CONTRACT_ID")	
	@SequenceGenerator(name="CONTRACT_CONTRACTID_GENERATOR", sequenceName="CONTRACT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_CONTRACTID_GENERATOR")
	private long contractId;
	
	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;
		
	@Column(name="SUPPLIER_ID", insertable=false, updatable=false)
	private long supplierId;
	
	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;
			
	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	/**
	 * bi-directional one-to-many association to Service
	 * In OO terms, this Contract "engages" Services
	 */
	@OneToMany(mappedBy="contract")
	private List <Service> services;


	public Contract() {
	}
	
	public long getContractId() {
		return this.contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	public long getClientId() {
		return this.clientId;
	}
	
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	public long getStaffId() {
		return this.staffId;
	}
	
	public long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	
	
	public void setStaffId(long staffId) {
		this.staffId = staffId;
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
	
	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	public Service addService(Service service) {
		getServices().add(service);
		return service;
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