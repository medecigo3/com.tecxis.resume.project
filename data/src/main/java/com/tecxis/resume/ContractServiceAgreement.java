package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.ContractServiceAgreementId;

/**
 * 
 * Persistence class for CONTRACT_SERVICE_AGREEMENT table
 * 
 * */
@Entity
@Table(name="CONTRACT_SERVICE_AGREEMENT")
@IdClass(ContractServiceAgreementId.class)
public class ContractServiceAgreement implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	/**
	 * bi-directional many-to-one association to Contract.
	 * In SQL terms, ContractServiceAgreement is the "owner" of the relationship with Contract as it contains the relationship's foreign key
	 * In OO terms, this ContractServiceAgreement "engages" this Contract
	 *
	 * Hibernate BasicExtractor returns component values in alphabetical order when Contract entity is has @Column annotations for column specifications.
	 * That allows the AbstractComponentTuplizer to properly set the component's id property values in alphabetical order.
	 * 
	 * However because associations in Contract entity are declared with @JoinColumn annotations, the order of these annotations has to be respected for all associated entities. 
	 * That allows the AbstractComponentTuplizer to properly set the component's id property values in the sequential order of the @JoinColumn annotations below.
	 */	
	@Id
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Contract contract;
			

	/**
	 * bi-directional many-to-one association to Service.
	 * In SQL terms, ContractServiceAgreement is the "owner" of the relationship with Service as it contains the relationship's foreign key
	 * In OO terms, this ContractServiceAgreement "provides" to this Contract
	 */	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SERVICE_ID", referencedColumnName="SERVICE_ID")
	private Service service;
	
	public ContractServiceAgreement() {
		super();
	}
	

	public ContractServiceAgreement(Contract contract, Service service) {
		super();
		this.contract = contract;
		this.service = service;
	}


	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}


	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContractServiceAgreement)) {
			return false;
		}
		ContractServiceAgreement castOther = (ContractServiceAgreement)other;
		
		if (this.getContract() != null && castOther.getContract() != null) {
			if (this.getService() != null && castOther.getService() != null) {
				
				return 	this.getContract().equals(castOther.getContract()) &&
						this.getService().equals(castOther.getService());
			} else return false;
		} else return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;

			if (getContract() != null) 
				hash = hash * prime + getContract().hashCode();
						
			if (getService() != null)
				hash = hash * prime + getService().hashCode();
		
		return hash;
	}

	@Override
	public String toString() {
		return  "["+this.getClass().getName() + 
				"[contractId=" + (this.getContract() != null ? this.getContract().getId() : "null") +
				", serviceId=" + (this.getService() != null ? this.getService().getId() : "null") + 
				"]]";
	}
	
	
	
}
