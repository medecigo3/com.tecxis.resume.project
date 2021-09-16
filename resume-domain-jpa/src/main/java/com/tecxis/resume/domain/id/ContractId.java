package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ContractId implements Serializable, Sequence <Long, Long> {

	private static final long serialVersionUID = 1L;
	
	@Column(name="CONTRACT_ID")
	private long contractId;
	
	@Column(name="CLIENT_ID")	
	private long clientId;
	
	public ContractId() {
		super();
	}

	public ContractId(long contractId, long clientId) {
		super();
		this.contractId = contractId;
		this.clientId = clientId;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	@Override
	public Long getSequentialValue() {
		return this.getContractId();
	}
	
	@Override
	public Long setSequentialValue(Long... t) {
		this.setContractId(t[0]);
		return this.getContractId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (clientId ^ (clientId >>> 32));
		result = prime * result + (int) (contractId ^ (contractId >>> 32));
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
		ContractId other = (ContractId) obj;
		if (clientId != other.clientId)
			return false;
		if (contractId != other.contractId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() +
				"[contractId=" + this.getContractId() + 
				", clientId=" + this.getClientId() + 
				"]";
	}

}