package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "SERVICE" database table.
 * 
 */
@Entity
@Table(name="\"SERVICE\"")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SERVICE_SERVICEID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICE_SERVICEID_GENERATOR")
	@Column(name="SERVICE_ID")
	private long serviceId;

	@Column(name="\"DESC\"")
	private String desc;

	private String name;

//	bi-directional many-to-one association to Contract
//	DB terms: Contract is the owner of the relationship as it contains a foreign key to this Service
//	@OneToMany(mappedBy="service")
	/**
	 * uni-directional one-to-many association to Contract.
	 * OO terms: this Service "engages" Contracts 
	 */
	@OneToMany
	private List<Contract> contracts;

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

	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
//		contract.setService(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
//		contract.setService(null);

		return contract;
	}

}