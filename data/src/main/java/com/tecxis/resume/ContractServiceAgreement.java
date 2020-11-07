package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.ContractServiceAgreementId;

/**
 * 
 * Persistence class for CONTRACT_SERVICE_AGREEMENT table
 * 
 * */
@Entity
@Table(name="CONTRACT_SERVICE_AGREEMENT")
public class ContractServiceAgreement implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	public ContractServiceAgreement() {
		super();
	}
	
	public ContractServiceAgreement(ContractServiceAgreementId contractServiceAgreementId) {
		super();
		this.contractServiceAgreementId = contractServiceAgreementId;
	}
	
	@EmbeddedId
	private ContractServiceAgreementId contractServiceAgreementId;

	public ContractServiceAgreementId getContractServiceAgreementId() {
		return contractServiceAgreementId;
	}

	public void setContractServiceAgreementId(ContractServiceAgreementId contractServiceAgreementId) {
		this.contractServiceAgreementId = contractServiceAgreementId;
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
		
		if (this.getContractServiceAgreementId().getContract() != null && castOther.getContractServiceAgreementId().getContract() != null) {
			if (this.getContractServiceAgreementId().getService() != null && castOther.getContractServiceAgreementId().getService() != null) {
				
				return 	this.getContractServiceAgreementId().getContract().equals(castOther.getContractServiceAgreementId().getContract()) &&
						this.getContractServiceAgreementId().getService().equals(castOther.getContractServiceAgreementId().getService());
			} else return false;
		} else return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		ContractServiceAgreementId contractServiceAgreementId = this.getContractServiceAgreementId();
		if (contractServiceAgreementId != null) {
			if (contractServiceAgreementId.getContract() != null) 
				hash = hash * prime + contractServiceAgreementId.getContract().hashCode();
						
			if (contractServiceAgreementId.getService() != null)
				hash = hash * prime + contractServiceAgreementId.getService().hashCode();
		}
		return hash;
	}

	@Override
	public String toString() {
		return  "["+this.getClass().getName() + 
				"[contractId=" + (this.getContractServiceAgreementId() != null ? this.getContractServiceAgreementId().getContract().getId() : "null") +
				", serviceId= " + (this.getContractServiceAgreementId() != null ? this.getContractServiceAgreementId().getService().getId() : "null") + 
				"]]";
	}
	
	
	
}
