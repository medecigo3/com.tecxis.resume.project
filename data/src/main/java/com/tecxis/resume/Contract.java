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


/**
 * The persistent class for the CONTRACT database table.
 * 
 */
@Entity
@IdClass(ContractPK.class)
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CONTRACT_ID")	
	@SequenceGenerator(name="CONTRACT_CONTRACTID_GENERATOR", sequenceName="CONTRACT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_CONTRACTID_GENERATOR")
	private long contractId;
	
	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;

	@Column(name="SUPPLIER_ID", insertable=false, updatable=false)
	private long supplierId;

	@Column(name="SERVICE_ID", insertable=false, updatable=false)
	private long serviceId;

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	//bi-directional many-to-one association to Client
//	@ManyToOne
//	@JoinColumn(name="CLIENT_ID")
//	private Client client;

	//bi-directional many-to-one association to Service
//	In SQL terms, Service is the "Owner" of this relationship with Contract as it contains the relationship's foreign key
//	@ManyToOne
//	@JoinColumn(name="SERVICE_ID")
//	private Service service;
	/**
	 * uni-directional one-to-many association to Service
	 * In OO terms, this Contract "engages" Services
	 */
	@OneToMany
	private List <Service> services;

	//bi-directional many-to-one association to Supplier
//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID"),
//		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")
//		})
//	private Supplier supplier;

	public Contract() {
	}

	public long getClientId() {
		return this.clientId;
	}
	
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	public long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(long supplierId) {
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
//
//	public Client getClient() {
//		return this.client;
//	}
//
//	public void setClient(Client client) {
//		this.client = client;
//	}

//	public Service getService() {
//		return this.service;
//	}
//
//	public void setService(Service service) {
//		this.service = service;
//	}

//	public Supplier getSupplier() {
//		return this.supplier;
//	}
//
//	public void setSupplier(Supplier supplier) {
//		this.supplier = supplier;
//	}
	

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