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

public class ProjectId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROJECT_ID")	
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CustomSequenceGenerator", name="PROJECT_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_SEQ")
	private long id;
	
	@Id
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CLIENT_ID")
	private Client client;

	
	public ProjectId(long projectId, Client client) {
		this();
		this.id = projectId;
		this.client = client;
	}

	/**Hibernate default constructor*/
	public ProjectId() {
		super();
	}
	
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public Client getClient() {
		return this.client;
	}
	public void setClientId(Client client) {
		this.client = client;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProjectId)) {
			return false;
		}
		ProjectId castOther = (ProjectId)other;
		
		if (this.getClient() != null && castOther.getClient() != null)	
			return 	this.getId() == castOther.getId() && 
					this.getClient().equals(castOther.getClient());
		else
			return this.getId() == castOther.getId();
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));
		
		if (this.getClient() != null)
			hash = hash * prime + this.getClient().hashCode();
		
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[id=" + this.getId() + 
				", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + "]]";
	
	}
}