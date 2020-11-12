package com.tecxis.resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;
import com.tecxis.resume.Contract.ContractPK;


/**
 * The persistent class for the CONTRACT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= {  "NAME" }))
@IdClass(ContractPK.class)
public class Contract implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;
	
	//TODO Move class to com.tecxis.commons.persistence.id and rename to ContractId
	public static class ContractPK implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@Column(name="CONTRACT_ID")	
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_SEQ")
		@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="CONTRACT_SEQ", 
				 parameters = {
				            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
				            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
		)
		private long id;
		
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
		private Client client;		

				
		public ContractPK(long id, Client client) {
			this();
			this.id = id;
			this.client = client;
			
		}

		public ContractPK() {
			super();
		}	
				
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}
		

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof ContractPK)) {
				return false;
			}
			ContractPK castOther = (ContractPK)other;
			
			if(this.getClient() != null && castOther.getClient() != null)
				return 	this.id == castOther.getId() && 
						this.getClient().equals(castOther.getClient());
			else
				return this.id == castOther.getId();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
			
			if(this.getClient() != null)
				hash = hash * prime + this.getClient().hashCode();		
			
			return hash;
		}
		
		@Override
		public String toString() {
			return "["+ this.getClass().getName() +
					"[id=" + this.getId() + 
					", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + 
					"]]";
		}

	}
	
	@Id
	@Column(name="CONTRACT_ID")	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_SEQ")
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="CONTRACT_SEQ", 
			 parameters = {
			            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
			            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	private long id;
	
	/**
	 * bi-directional many-to-one association to Client. 
	 * In SQL terms, Contract is the "owner" of this relationship with Client as it contains the relationship's foreign key
	 * In OO terms, this Client "signed" this Contract.
	 */
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Client client;		

	/**
	 * bi-directional one-to-many association to SupplyContract. 
	 * In SQL terms, SupplyContract is the "owner" of this relationship with Contract as it contains the relationship's foreign keys
	 * In OO terms, this Contract "COMMITS TO" to these SupplyContracts.
	 */
	@OneToMany(mappedBy="supplyContractId.contract", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	private List <SupplyContract> supplyContracts;
			

	/**
	 * bi-directional one-to-many association to ContractServiceAgreement
	 * In OO terms, this Contract "engages" these ContractServiceAgreements
	 */
	@OneToMany(mappedBy="contract", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <ContractServiceAgreement> contractServiceAgreements;
	
	@NotEmpty
	private String name;


	public Contract() {
		this.contractServiceAgreements = new ArrayList <> ();
		this.supplyContracts = new ArrayList<> ();
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addContractServiceAgreement(ContractServiceAgreement contractServiceAgreement) throws EntityExistsException {
		/**check if 'service' isn't in this contract -> contractServiceAgreements*/
		if ( this.getContractServiceAgreements().contains(contractServiceAgreement))
				throw new EntityExistsException("Service already exists in this Contract -> contractServiceAgreements: " + contractServiceAgreement.toString());
							
		this.getContractServiceAgreements().add(contractServiceAgreement);		
	}
	
	public boolean removeContractServiceAgreement(ContractServiceAgreement contractServiceAgreement) {		
		boolean ret = this.getContractServiceAgreements().remove(contractServiceAgreement);
		contractServiceAgreement.setContract(null);
		return ret;
	
	}
	
	public boolean removeContractServiceAgreement(Service service) {		
		Iterator <ContractServiceAgreement> contractServiceAgreementIt =  this.getContractServiceAgreements().iterator();
	
		while(contractServiceAgreementIt.hasNext()) {			
			ContractServiceAgreement tempContractServiceAgreement = contractServiceAgreementIt.next();
			Service tempService = tempContractServiceAgreement.getService();
			if (service.equals(tempService)) {
				return this.getContractServiceAgreements().remove(tempContractServiceAgreement);
				
			}
		}
		return false;
	}
		
	public List<ContractServiceAgreement> getContractServiceAgreements() {
		return contractServiceAgreements;
	}

	public void setContractServiceAgreements(List<ContractServiceAgreement> contractServiceAgreements) {
		if (contractServiceAgreements != null) {
			this.contractServiceAgreements.clear();
			for (ContractServiceAgreement contractServiceAgreement : contractServiceAgreements) {
				this.contractServiceAgreements.add(contractServiceAgreement);
			}
		} else {
			this.contractServiceAgreements = null;
		}
		
	}

	public List<SupplyContract> getSupplyContracts() {
		return this.supplyContracts;
	}
	
	public void setSupplyContracts(List<SupplyContract> supplyContracts) {
		if (supplyContracts != null ) {
			this.supplyContracts.clear();
			for(SupplyContract supplyContract : supplyContracts) {
				this.supplyContracts.add(supplyContract);
			}			
		} else {
			this.supplyContracts.clear();
		}		

	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Contract)) {
			return false;
		}
		Contract castOther = (Contract)other;
		
		if (this.getClient() != null && this.getClient() != null)
			return 	this.id == castOther.getId() && 
					this.getClient().equals(castOther.getClient());
		else
			return 	this.id == castOther.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;		
		
		hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
		
		if (this.getClient() != null)
			hash = hash * prime + this.getClient().hashCode();
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() + 
				"id=" + this.getId() + 
				", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + 
				"]";
	}
}