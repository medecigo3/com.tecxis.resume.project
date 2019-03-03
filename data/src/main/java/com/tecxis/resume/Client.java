package com.tecxis.resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;


/**
 * The persistent class for the CLIENT database table.
 * 
 */
@Entity
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="CLIENT_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENT_SEQ")
	@Column(name="CLIENT_ID")
	private long clientId;

	private String name;

	private String website;


	/**
	 * bi-directional one-to-many association to Contract
	 * In SQL terms, Contract is the "owner" of this relationship with Client as it contains the relationship's foreign key
	 * In OO terms, this Client "signs" these Contracts
	 */
	@OneToMany(mappedBy="client", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Contract> contracts;


	/**
	 * bi-directional one-to-many association to Project.
	 * In SQL terms, Project is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this Client "controls" projects
	 */
	@OneToMany(mappedBy="client")
	private List<Project> projects;

	public Client() {
		this.contracts = new ArrayList<> ();
		this.projects = new ArrayList<> ();
	}

	public long getClientId() {
		return this.clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
		return contract;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		return project;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Client)) {
			return false;
		}
		Client castOther = (Client)other;
		return 
			(this.getClientId() == castOther.getClientId());
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getClientId() ^ (this.getClientId() >>> 32)));	
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() + 
				", name=" + this.getName()+ 
				"[clientId=" + this.getClientId() +
				 "]]";
				
	}
}