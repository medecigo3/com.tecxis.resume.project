package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.CompositeIdentifiable;
import com.tecxis.resume.domain.id.ContractId;
import com.tecxis.resume.domain.id.SequenceKeyGenerator;


/**
 * The persistent class for the CONTRACT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= {  "NAME" }))
public class Contract implements Serializable, CompositeIdentifiable <ContractId>{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceCompositeKeyGenerator", name="CONTRACT_SEQ", 
			 parameters = {
			            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
			            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_SEQ")
	private ContractId id;
	
	/**
	 * bi-directional many-to-one association to Client. 
	 * In SQL terms, Contract is the "owner" of this relationship with Client as it contains the relationship's foreign key
	 * In OO terms, this Client "signed" this Contract.
	 */
	@MapsId("clientId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Client client;		

	/**
	 * bi-directional one-to-many association to SupplyContract. 
	 * In SQL terms, SupplyContract is the "owner" of this relationship with Contract as it contains the relationship's foreign keys
	 * In OO terms, this Contract "COMMITS TO" to these SupplyContracts.
	 */
	@OneToMany(mappedBy="contract", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	private List <SupplyContract> supplyContracts;
			

	/**
	 * bi-directional one-to-many association to Agreement
	 * In OO terms, this Contract "engages" these Agreements
	 */
	@OneToMany(mappedBy="contract", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <Agreement> agreements;
	
	@NotEmpty
	private String name;


	public Contract() {
		this.id = new ContractId();
		this.agreements = new ArrayList <> ();
		this.supplyContracts = new ArrayList<> ();
	}
	
	@Override
	public ContractId getId() {
		return this.id;
	}
	
	@Override
	public void setId(ContractId id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
		this.getId().setClientId(client.getId());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAgreement(Agreement agreement) throws EntityExistsException {
		/**check if 'service' isn't in this contract -> Agreements*/
		if ( this.getAgreements().contains(agreement))
				throw new EntityExistsException("Service already exists in this Contract -> Agreements: " + agreement.toString());
							
		this.getAgreements().add(agreement);		
	}
	
	public boolean removeAgreement(Agreement agreement) {		
		boolean ret = this.getAgreements().remove(agreement);
		agreement.setContract(null);
		return ret;
	
	}
	
	public boolean removeAgreement(Service service) {
		return this.getAgreements().remove(this.getAgreements()
				.stream()				
				.filter(agreement -> agreement.getService().equals(service))
				.findFirst()
				.orElse(null));
	}
		
	public List<Agreement> getAgreements() {
		return agreements;
	}

	public void setAgreements(List<Agreement> agreements) {
		this.agreements.clear();
		this.agreements.addAll(agreements);		
	}

	public List<SupplyContract> getSupplyContracts() {
		return this.supplyContracts;
	}
	
	public void setSupplyContracts(List<SupplyContract> supplyContracts) {
		this.supplyContracts.clear();
		this.supplyContracts.addAll(supplyContracts);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" +
				this.getId() +
				"]";
	}
}