package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the PROJECT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "VERSION" , "NAME" }))
@IdClass(ProjectPK.class)
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROJECT_ID")	
	@SequenceGenerator(name="PROJECT_PROJECTID_GENERATOR", sequenceName="PROJECT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_PROJECTID_GENERATOR")
	private long projectId;
	
	@Id
	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;
	
	@Column(name="\"DESC\"")
	private String desc;

//	bi-directional many-to-one association to Assignment 
//	In SQL terms, Assignment is the "owner" of this relationship with Project as it contains the relationship's foreign key
//	@OneToMany(mappedBy="project")
	/**
	 * uni-directional one-to-many association to Assignment.
	 * In OO terms, this Project "is composed of" Assignments
	 * 
	 */	
	@OneToMany
	@JoinTable(
		name="STAFF_ASSIGNMENT", joinColumns= {
				@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID"),
				@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
		}, inverseJoinColumns= {
				@JoinColumn(name="ASSIGNMENT_ID", referencedColumnName="ASSIGNMENT_ID"),
		}
	)
	private List<Assignment> assignments;

	/**
	 * bi-directional many-to-many association to City
	 */
	@ManyToMany (cascade = CascadeType.ALL)
	@JoinTable(
		name="LOCATION", joinColumns={
			@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID"),
			@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CITY_ID", referencedColumnName="CITY_ID"),
			@JoinColumn(name="COUNTRY_ID", referencedColumnName="COUNTRY_ID")
			}
		)
	private List<City> cities;

	private String version;

	private String name;

	//bi-directional many-to-one association to Client
//	@ManyToOne
//	@JoinColumn(name="CLIENT_ID")
//	private Client client;

	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name="STAFF_ID")
//	private Staff staff;

	public Project() {
		this.cities = new ArrayList <> ();
	}

	public String getDesc() {
		return this.desc;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Assignment> getAssignments() {
		return this.assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public Assignment addAssignment(Assignment assignment) {
		getAssignments().add(assignment);
//		assignment.setProject(this);

		return assignment;
	}

	public Assignment removeAssignment(Assignment assignment) {
		getAssignments().remove(assignment);
//		assignment.setProject(null);

		return assignment;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		for (City city : cities) {
			city.getProjects().add(this);
		}
		this.cities = cities;
	}

	public boolean addCity(City city) {
		city.getProjects().add(this);
		return this.getCities().add(city);
		
	}
	
	public boolean removeCity(City city) {
		city.getProjects().remove(this);
		return this.cities.remove(city);
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
//	public Client getClient() {
//		return this.client;
//	}
//
//	public void setClient(Client client) {
//		this.client = client;
//	}

//	public Staff getStaff() {
//		return this.staff;
//	}
//
//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}
	
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