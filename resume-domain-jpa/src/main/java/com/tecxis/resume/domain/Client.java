package com.tecxis.resume.domain;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.SequenceKeyGenerator;
import com.tecxis.resume.domain.id.Identifiable;


/**
 * The persistent class for the CLIENT database table.
 * 
 */
@Entity
public class Client implements Serializable, Identifiable <Long>{
	private static final String UNSUPPORTED_CLIENT_CONTRACT_OPERATION = "Client -> Contract association managed by association owner Contract.";
	
	private static final String UNSUPPORTED_CLIENT_PROJECT_OPERATION = "Client -> Project association managed through by association owner Project.";
	
	public static final String CLIENT_TABLE =	"CLIENT";
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceKeyGenerator", name="CLIENT_SEQ", 
	 parameters = {
	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENT_SEQ")
	@Column(name="CLIENT_ID")
	private long id;

	@NotNull
	private String name;

	private String website;


	/**
	 * bi-directional one-to-many association to Contract
	 * In SQL terms, Contract is the "owner" of this relationship with Client as it contains the relationship's foreign key
	 * In OO terms, this Client "signs" these Contracts
	 */
	@OneToMany(mappedBy="client", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval=true)
	private List<Contract> contracts;


	/**
	 * bi-directional one-to-many association to Project.
	 * In SQL terms, Project is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this Client "controls" projects
	 */
	@OneToMany(mappedBy="client", orphanRemoval=true)
	private List<Project> projects;

	public Client() {
		this.contracts = new ArrayList<> ();
		this.projects = new ArrayList<> ();
	}
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
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
	
	public void setContracts(List <Contract> contracts) {
		throw new UnsupportedOperationException(UNSUPPORTED_CLIENT_CONTRACT_OPERATION);
	}
	
	public void addContract(Contract contract) {
		throw new UnsupportedOperationException(UNSUPPORTED_CLIENT_CONTRACT_OPERATION);
	}

	public void removeContract(Contract contract) {
		throw new UnsupportedOperationException(UNSUPPORTED_CLIENT_CONTRACT_OPERATION);
	}
	
	public List<Project> getProjects() {
		return this.projects;
	}
	
	public void setProjects(List <Project> projects) {
		throw new UnsupportedOperationException(UNSUPPORTED_CLIENT_PROJECT_OPERATION);
	}
	
	public void addProject(Project project) {
		throw new UnsupportedOperationException(UNSUPPORTED_CLIENT_PROJECT_OPERATION);
	}

	public void removeProject(Project project) {
		throw new UnsupportedOperationException(UNSUPPORTED_CLIENT_PROJECT_OPERATION);
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
		return "[" +this.getClass().getName()+ "@" + this.hashCode() + 
				", name=" + this.getName()+ 
				"[" + this.getId() +  "]]";
				
	}
}