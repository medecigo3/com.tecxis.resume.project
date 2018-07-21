package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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
	@ManyToOne
	@JoinColumn(name="CLIENT_ID")
	private Client client;

	//bi-directional many-to-one association to Service
	@ManyToOne
	@JoinColumn(name="SERVICE_ID")
	private Service service;

	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID"),
		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")
		})
	private Supplier supplier;

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

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}