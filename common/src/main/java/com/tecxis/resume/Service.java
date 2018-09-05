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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the "SERVICE" database table.
 * 
 */
@Entity
@Table(name="\"SERVICE\"")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SERVICE_SEQ" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICE_SERVICEID_GENERATOR")
	@Column(name="SERVICE_ID")
	private long serviceId;

	@Column(name="\"DESC\"")
	private String desc;

	private String name;

//	bi-directional many-to-one association to Contract
//	In SQL terms, Contract is the "owner" of this relationship with Service as it contains the relationship's foreign key
//	@OneToMany(mappedBy="service")
	/**
	 * uni-directional one-to-many association to Contract.
	 * In OO terms, this Service "engages" Contracts 
	 */
	@OneToMany
	@JoinColumn(name="SERVICE_ID")
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