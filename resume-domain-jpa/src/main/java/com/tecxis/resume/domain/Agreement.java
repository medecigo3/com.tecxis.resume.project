package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.SchemaConstants.AGREEMENT_TABLE;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.AgreementId;

/**
 * 
 * Persistence class for AGREEMENT table. <br><br>
*  Note about the order of associations are declared in this entity:<br>
*  When Contract entity has @Column annotation declarations for column specifications, the Hibernate {@link org.hibernate.type.descriptor.sql.BasicExtractor BasicExtractor} returns component values in alphabetical order.<br>
*  That allows the {@link org.hibernate.tuple.component.AbstractComponentTuplizer AbstractComponentTuplizer} to properly set the component's id property values in alphabetical order.<br><br>
*
*  However,  associations in {@link com.tecxis.resume.domain.Contract Contract} entity are rather declared with @JoinColumn annotations. The order of these declarations has to be respected amongst associated entities.<br> 
*  Consequently, @JoinColumn annotations in this class respect the order declaration in {@link com.tecxis.resume.domain.Contract Contract}, {@link com.tecxis.resume.domain.Service Service} and vice-versa. <br> 
*  {@link org.hibernate.tuple.component.AbstractComponentTuplizer AbstractComponentTuplizer} will accurately set the id values in the sequence order dictated between these associations.
 * 
 * */
@Entity
@Table(name=AGREEMENT_TABLE)
public class Agreement implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AgreementId id;
	/**
	 * bi-directional many-to-one association to Contract.
	 * In SQL terms, Agreement is the "owner" of the relationship with Contract as it contains the relationship's foreign key
	 * In OO terms, this Agreement "engages" this Contract
	 *
	 */	
	@MapsId("contractId")
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CONTRACT_ID", referencedColumnName="CONTRACT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Contract contract;
			

	/**
	 * bi-directional many-to-one association to Service.
	 * In SQL terms, Agreement is the "owner" of the relationship with Service as it contains the relationship's foreign key
	 * In OO terms, this Agreement "provides" to this Contract
	 */	
	@MapsId("serviceId")
	@ManyToOne(cascade =  {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SERVICE_ID", referencedColumnName="SERVICE_ID")
	private Service service;
	
	public Agreement() {
		this.id = new AgreementId();
	}
	

	public Agreement(Contract contract, Service service) {
		this();
		this.getId().setContractId(contract.getId());
		this.getId().setServiceId(service.getId());
		this.setContract(contract);
		this.setService(service);
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
	
	public AgreementId getId() {
		return id;
	}


	public void setId(AgreementId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Agreement other = (Agreement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return  this.getClass().getName() + "[" +
				this.getId() + 
				"]";
	}
	
}
