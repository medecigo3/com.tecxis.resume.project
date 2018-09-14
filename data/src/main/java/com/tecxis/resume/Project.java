package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


/**
 * The persistent class for the PROJECT database table.
 * 
 */
@Entity
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProjectPK projectPk;

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
	@JoinColumns({
		@JoinColumn(name="NAME", referencedColumnName="NAME"),
		@JoinColumn(name="VERSION", referencedColumnName="VERSION"),
		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID"),
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	})
	private List<Assignment> assignments;

	/**
	 * bi-directional many-to-many association to City
	 */
	@ManyToMany
	@JoinTable(
		name="LOCATION"
		, joinColumns={
			@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID"),
			@JoinColumn(name="NAME", referencedColumnName="NAME"),
			@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID"),
			@JoinColumn(name="VERSION", referencedColumnName="VERSION"),
			}
		, inverseJoinColumns={
			@JoinColumn(name="CITY_ID", referencedColumnName="CITY_ID"),
			@JoinColumn(name="COUNTRY_ID", referencedColumnName="COUNTRY_ID")
			}
		)
	private List<City> cities;

	//bi-directional many-to-one association to Client
//	@ManyToOne
//	@JoinColumn(name="CLIENT_ID")
//	private Client client;

	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name="STAFF_ID")
//	private Staff staff;

	public Project() {
	}

	public ProjectPK getProjectPk() {
		return this.projectPk;
	}

	public void setProjectPk(ProjectPK projectPk) {
		this.projectPk = projectPk;
	}

	public String getDesc() {
		return this.desc;
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
		this.cities = cities;
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