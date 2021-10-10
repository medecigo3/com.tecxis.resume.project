package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AgreementId implements Serializable{

		private static final long serialVersionUID = 1L;
			
		private ContractId contractId; //corresponds to the PK type of Contract
				
		private long serviceId; //Corresponds to the PK of Service

		public AgreementId(ContractId contractId, long serviceId) {
			this();
			this.contractId = contractId;
			this.serviceId = serviceId;
		}

		public AgreementId() {
			super();
			this.contractId = new ContractId();
		}

		public ContractId getContractId() {
			return contractId;
		}

		public void setContractId(ContractId contractId) {
			this.contractId = contractId;
		}

		public long getServiceId() {
			return serviceId;
		}

		public void setServiceId(long serviceId) {
			this.serviceId = serviceId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((contractId == null) ? 0 : contractId.hashCode());
			result = prime * result + (int) (serviceId ^ (serviceId >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AgreementId other = (AgreementId) obj;
			if (contractId == null) {
				if (other.contractId != null)
					return false;
			} else if (!contractId.equals(other.contractId))
				return false;
			if (serviceId != other.serviceId)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return  this.getClass().getName() + "@" + this.hashCode() + 
					"[" +
					(this.getContractId() != null ? this.getContractId().toString() + ", " : "contractId=null, ") +										
					"serviceId="+  this.getServiceId() + 
					"]";		
		}

}
