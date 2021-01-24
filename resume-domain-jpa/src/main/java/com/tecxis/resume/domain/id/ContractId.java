package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.Client;

public class ContractId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CONTRACT_ID")	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_SEQ")
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CustomSequenceGenerator", name="CONTRACT_SEQ", 
			 parameters = {
			            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
			            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	private long id;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Client client;		

			
	public ContractId(long id, Client client) {
		this();
		this.id = id;
		this.client = client;
		
	}

	public ContractId() {
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
		if (!(other instanceof ContractId)) {
			return false;
		}
		ContractId castOther = (ContractId)other;
		
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