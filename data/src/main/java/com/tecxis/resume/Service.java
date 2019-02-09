package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;


/**
 * The persistent class for the "SERVICE" database table.
 * 
 */
@Entity
@Table(name="\"SERVICE\"")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@SequenceGenerator(name="SERVICE_SERVICEID_GENERATOR", sequenceName="SERVICE_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICE_SERVICEID_GENERATOR")
	@Column(name="SERVICE_ID")
	private long serviceId;

	@Column(name="\"DESC\"")
	private String desc;

	private String name;
	
	/**
	* bi-directional one-to-many association to ContractServiceAgreement.
	* In OO terms, this Service "provides" to this Contract
	*/	
	@OneToMany(mappedBy = "contractServiceAgreementId.service", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <ContractServiceAgreement> contractServiceAgreements;

	public Service() {
		this.contractServiceAgreements = new ArrayList<> ();
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
	

	public void addContractServiceAgreement(Contract contract) throws EntityExistsException {
		/**Check if 'contract' isn't in this service's ContractServiceAgreements */
		if ( !Collections.disjoint(this.getContractServiceAgreements(), contract.getContractServiceAgreements()))
			throw new EntityExistsException("Entity already exists in this ContractServiceAgreement: " + contract.toString());
				
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(contract);
		contractServiceAgreementId.setService(this);
		ContractServiceAgreement newContractServiceAgreement = new ContractServiceAgreement();
		newContractServiceAgreement.setContractServiceAgreementId(contractServiceAgreementId);
		this.getContractServiceAgreements().add(newContractServiceAgreement);
	
	}
	
	public boolean removeContractServiceAgreement(Contract contract) {		
		Iterator <ContractServiceAgreement> contractServiceAgreementIt =  this.getContractServiceAgreements().iterator();
		
		while(contractServiceAgreementIt.hasNext()) {			
			ContractServiceAgreement tempContractServiceAgreement = contractServiceAgreementIt.next();
			Contract tempContract = tempContractServiceAgreement.getContractServiceAgreementId().getContract();
			if (contract.equals(tempContract)) {
				return this.getContractServiceAgreements().remove(tempContractServiceAgreement);
				
			}
		}
		return false;
	}
	
	public boolean removeContractServiceAgreement(ContractServiceAgreement contractServiceAgreement) {		
		return this.getContractServiceAgreements().remove(contractServiceAgreement);
	}
	

	public List<ContractServiceAgreement> getContractServiceAgreements() {
		return contractServiceAgreements;
	}

	public void setContractServiceAgreements(List<ContractServiceAgreement> contractServiceAgreements) {
		this.contractServiceAgreements = contractServiceAgreements;
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