package com.tecxis.resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;
import com.tecxis.resume.Contract.ContractPK;
import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;


/**
 * The persistent class for the CONTRACT database table.
 * 
 */
@Entity
@IdClass(ContractPK.class)
public class Contract implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;
	
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

		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")			
		private Supplier supplier;	
		
		public ContractPK(long id, Client client,  Supplier supplier) {
			this();
			this.id = id;
			this.client = client;
			this.supplier = supplier;		
		
		}

		private ContractPK() {
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

		public Supplier getSupplier() {
			return supplier;
		}

		public void setSupplier(Supplier supplier) {
			this.supplier = supplier;
		}

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof ContractPK)) {
				return false;
			}
			ContractPK castOther = (ContractPK)other;
			return 
				(this.client.getId() == castOther.getClient().getId())
				&& (this.supplier.getId() == castOther.getSupplier().getId())
				&& (this.id == castOther.id)
				&& (this.supplier.getStaff().getId() == castOther.getSupplier().getStaff().getId());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.client.getId() ^ (this.client.getId() >>> 32)));
			hash = hash * prime + ((int) (this.supplier.getId()  ^ (this.supplier.getId()  >>> 32)));
			hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
			hash = hash * prime + ((int) (this.supplier.getStaff().getId() ^ (this.supplier.getStaff().getId() >>> 32)));
			
			return hash;
		}
		
		@Override
		public String toString() {
			return "["+ this.getClass().getName() +
					"[id=" + this.getId() + 
					", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + 
					", supplierId=" + (this.getSupplier() != null ? this.getSupplier().getId() : " null" ) + 
					", staffId=" + (this.getSupplier() != null ? ( this.getSupplier().getStaff() != null ? this.getSupplier().getStaff().getId() : "null"  ) : " null" ) + "]]";
		}

	}
	
	@Id
	@Column(name="CONTRACT_ID")	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_SEQ")
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="CONTRACT_SEQ")
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
	 * bi-directional many-to-one association to Supplier. 
	 * In SQL terms, Contract is the "owner" of this relationship with Supplier as it contains the relationship's foreign keys
	 * In OO terms, this Supplier "holds" this Contract.
	 */
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="SUPPLIER_ID", referencedColumnName="SUPPLIER_ID")
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Supplier supplier;	
			
	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	/**
	 * bi-directional one-to-many association to ContractServiceAgreement
	 * In OO terms, this Contract "engages" these ContractServiceAgreements
	 */
	@OneToMany(mappedBy="contractServiceAgreementId.contract", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <ContractServiceAgreement> contractServiceAgreements;


	public Contract() {
		this.contractServiceAgreements = new ArrayList <> ();
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
		
	public void addContractServiceAgreement(Service service) throws EntityExistsException {
		/**check if 'service' isn't in this contract -> contractServiceAgreements*/
		if ( !Collections.disjoint(this.getContractServiceAgreements(), service.getContractServiceAgreements() ))
				throw new EntityExistsException("Service already exists in this Contract -> contractServiceAgreements: " + service.toString());
		
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(this);
		contractServiceAgreementId.setService(service);
		ContractServiceAgreement newContractServiceAgreement = new ContractServiceAgreement();
		newContractServiceAgreement.setContractServiceAgreementId(contractServiceAgreementId);
		this.getContractServiceAgreements().add(newContractServiceAgreement);		
	}
	
	public boolean removeContractServiceAgreement(ContractServiceAgreement contractServiceAgreement) {
		return this.getContractServiceAgreements().remove(contractServiceAgreement);
	}
	
	public boolean removeContractServiceAgreement(Service service) {		
		Iterator <ContractServiceAgreement> contractServiceAgreementIt =  this.getContractServiceAgreements().iterator();
	
		while(contractServiceAgreementIt.hasNext()) {			
			ContractServiceAgreement tempContractServiceAgreement = contractServiceAgreementIt.next();
			Service tempService = tempContractServiceAgreement.getContractServiceAgreementId().getService();
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
		this.contractServiceAgreements.clear();
		for (ContractServiceAgreement contractServiceAgreement : contractServiceAgreements) {
			this.contractServiceAgreements.add(contractServiceAgreement);
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
		return 
			(this.client.getId() == castOther.getClient().getId())
			&& (this.supplier.getId() == castOther.getSupplier().getId())
			&& (this.id == castOther.getId())
			&& (this.supplier.getStaff().getId() == castOther.getSupplier().getStaff().getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.client.getId() ^ (this.client.getId() >>> 32)));
		hash = hash * prime + ((int) (this.supplier.getId()  ^ (this.supplier.getId()  >>> 32)));
		hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
		hash = hash * prime + ((int) (this.supplier.getStaff().getId() ^ (this.supplier.getStaff().getId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() + "[" + Contract.ContractPK.class.getName() + 
				"[id=" + this.getId() + 
				", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + 
				", supplierId=" + (this.getSupplier() != null ? this.getSupplier().getId() : " null" ) + 
				", staffId=" + (this.getSupplier() != null ? ( this.getSupplier().getStaff() != null ? this.getSupplier().getStaff().getId() : "null"  ) : " null" ) + "]]]";
	}
}