package com.tecxis.resume;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
	private ProjectPK id;

	@Column(name="\"DESC\"")
	private String desc;

//	bi-directional many-to-one association to Assignment 
//	DB terms: Assignment is the owner of the relationship as it contains a foreign key to this Project
//	@OneToMany(mappedBy="project")
	/**
	 * uni-directional one-to-many association to Assignment.
	 * OO terms: this Project "is composed of" Assignments
	 * 
	 */	
	@OneToMany
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

	public ProjectPK getId() {
		return this.id;
	}

	public void setId(ProjectPK id) {
		this.id = id;
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

}