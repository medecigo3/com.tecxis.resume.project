package com.tecxis.resume;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Persistence class for CONTRACT_SERVICE_AGREEMENT table
 * 
 * */
@Entity
@Table(name="CONTRACT_SERVICE_AGREEMENT")
public class ContractServiceAgreement implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	
	public static class ContractServiceAgreementId implements Serializable{
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
		@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
		@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")	
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")		
		private Contract contract;
				
		/**
		 * bi-directional many-to-one association to Service.
		 * In SQL terms, ContractServiceAgreement is the "owner" of the relationship with Service as it contains the relationship's foreign key
		 * In OO terms, this ContractServiceAgreement "provides" to this Contract
		 */	
		@ManyToOne(cascade = CascadeType.ALL)
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
			return 			
				(this.contract.getContractId() == castOther.getContract().getContractId())	
				&& (this.service.getServiceId() == castOther.getService().getServiceId());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;		
			hash = hash * prime + ((int) (this.contract.getContractId() ^ (this.contract.getContractId() >>> 32)));
			hash = hash * prime + ((int) (this.service.getServiceId() ^ (this.service.getServiceId() >>> 32)));			
			return hash;
		}

		@Override
		public String toString() {
			return "ContractServiceAgreementId=[contractId=" + (this.getContract() != null ? this.getContract().getContractId() : "null") + 					
					", serviceId= "+ (this.getService() != null ? this.getService().getServiceId() : "null") + "]";		
		}
		
		
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
	public String toString() {
		return reflectionToString(this) + "[" + contractServiceAgreementId.toString() + "]";
	}
	
	
	
}
