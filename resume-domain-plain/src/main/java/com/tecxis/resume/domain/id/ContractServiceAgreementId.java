package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Service;

public class ContractServiceAgreementId implements Serializable{

		private static final long serialVersionUID = 1L;
		
		@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
		private Contract contract;
				
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="SERVICE_ID", referencedColumnName="SERVICE_ID")
		private Service service;

		public ContractServiceAgreementId(Contract contract, Service service) {
			super();
			this.contract = contract;
			this.service = service;
		}

		public ContractServiceAgreementId() {
			super();
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
			if (!(other instanceof ContractServiceAgreementId)) {
				return false;
			}
			ContractServiceAgreementId castOther = (ContractServiceAgreementId)other;
			
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
			
			if (this.getContract() != null) 
				hash = hash * prime + this.getContract().hashCode();
			
			if (this.getService() != null)
				hash = hash * prime + this.getService().hashCode();
			
			return hash;
		}

		@Override
		public String toString() {
			return "[" + this.getClass().getName() + 
					"[contractId=" + (this.getContract() != null ? this.getContract().getId() : "null") + 
					", clientId="+   (this.getContract() != null ?  (this.getContract().getClient() != null ? this.getContract().getClient().getId() : "null") : "null") +					
					", serviceId= "+ (this.getService() != null ? this.getService().getId() : "null") + "]]";		
		}

}
