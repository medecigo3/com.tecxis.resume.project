package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CLIENT database table.
 * 
 */
@Entity
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CLIENT_CLIENTID_GENERATOR", sequenceName="CLIENT_SEQ", allocationSize=1, initialValue=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENT_CLIENTID_GENERATOR")
	@Column(name="CLIENT_ID")
	private long clientId;

	private String name;

	private String website;

//	bi-directional many-to-one association to Contract
//	In SQL terms, Contract is the "owner" of this relationship with Client as it contains the relationship's foreign key
//	@OneToMany(mappedBy="client")
	/**
	 * uni-directional one-to-many association to Contract
	 * In OO terms, this Client "signs" Contracts
	 */
	@OneToMany
	@JoinColumn(name="CLIENT_ID")
	private List<Contract> contracts;

	//bi-directional many-to-one association to Project
	//In SQL terms, Project is the "owner" of this relationship with Client as it contains the relationship's foreign key
//	@OneToMany(mappedBy="client")
	/**
	 * uni-directional one-to-many association to Client.
	 * In OO terms, this Client "controls" projects
	 */
	@OneToMany
	private List<Project> projects;

	public Client() {
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

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
//		contract.setClient(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
//		contract.setClient(null);

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
//		project.setClient(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
//		project.setClient(null);

		return project;
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