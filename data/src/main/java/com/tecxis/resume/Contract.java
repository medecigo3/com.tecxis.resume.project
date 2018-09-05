package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CONTRACT database table.
 * 
 */
@Entity
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContractPK id;

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

	public ContractPK getId() {
		return this.id;
	}

	public void setId(ContractPK id) {
		this.id = id;
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