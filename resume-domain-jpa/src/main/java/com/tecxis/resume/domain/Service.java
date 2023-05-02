package com.tecxis.resume.domain;

import com.tecxis.resume.domain.id.Identifiable;
import com.tecxis.resume.domain.id.SequenceKeyGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the "SERVICE" database table.
 * 
 */
@Entity
@Table(name="\"SERVICE\"")
public class Service implements Serializable, Identifiable <Long>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceKeyGenerator", name="SERVICE_SEQ", 
	 parameters = {
	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICE_SEQ")
	@Column(name="SERVICE_ID")
	private long id;

	@Column(name="\"DESC\"")
	private String desc;

	@NotNull
	private String name;
	
	/**
	* bi-directional one-to-many association to Agreement.
	* In OO terms, this Service "is provided" to these Agreements
	*/	
	@OneToMany(mappedBy = "service", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <Agreement> agreements;

	public Service() {
		this.agreements = new ArrayList<> ();
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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
	

	
	public void addAgreement(Agreement agreement) throws EntityExistsException {
		/**Check if 'contract' isn't in this service -> Agreements */
		if ( this.getAgreements().contains(agreement))
			throw new EntityExistsException("Contract already exists in this Service -> Agreements: " + agreement.toString());

		this.getAgreements().add(agreement);
	
	}
	
	public boolean removeAgreement(Contract contract) {//RES-43 declarative approach
		return this.getAgreements().remove(getAgreements()
				.stream()
				.filter( agreement -> agreement.getContract().equals(contract))
				.findFirst()
				.orElse(null));
	}
	
	public boolean removeAgreement(Agreement agreement) {		
		 boolean ret = this.getAgreements().remove(agreement);
		 agreement.setService(null);
		 return ret;
	}
	

	public List<Agreement> getAgreements() {
		return agreements;
	}

	public void setAgreements(List<Agreement> agreements) {
		this.agreements = agreements;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Service)) {
			return false;
		}
		Service castOther = (Service)other;
		return 
			(this.getId() == castOther.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));	
		
		return hash;
	}

	@Override
	public String toString() {
		return this.getClass().getName()+ "@" + this.hashCode() +
				"[serviceId=" + this.getId() + "]";
	}

}