package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the STAFF database table.
 * 
 */
@Entity
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STAFF_STAFFID_GENERATOR", sequenceName="STAFF_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STAFF_STAFFID_GENERATOR")
	@Column(name="STAFF_ID")
	private long staffId;

	@Temporal(TemporalType.DATE)
	@Column(name="BIRTH_DATE")
	private Date birthDate;

	private String lastname;

	private String name;

	/**
	 * bi-directional many-to-many association to Course
	 */
	@ManyToMany
	@JoinTable(
			name="ENROLMENT",
			joinColumns= {
				@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
			}, 
			inverseJoinColumns= {
				@JoinColumn(name="COURSE_ID", referencedColumnName="COURSE_ID")
			}
	)
	private List<Course> courses;

	/**
	 * uni-directional association to Staff
	 * In OO terms, this Staff "has" Interest(s)
	 */
	@OneToMany
	@JoinColumn(name="STAFF_ID")
	private List<Interest> interests;

	/**
	 * uni-directional one-to-many association to Project
	 * In OO terms, this Staff "works on" Projects
	 */
	@OneToMany
	@JoinTable(
		name="STAFF_PROJECT_ASSIGNMENT", joinColumns= {
			@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")	
		}, inverseJoinColumns = {
			@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID"),
			@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
		}
	)
	
	private List<Project> projects;
		
	/**
	 * bi-directional one-to-many association to StaffProjectAssignment
	 * In SQL terms, StaffProjectAssignment is the "owner" of this association with Staff as it contains the relationship's foreign key
	 * In OO terms, this Staff "works on" staff assignments
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")	
	private List<StaffProjectAssignment> staffProjectAssignments;

	/**
	 *  bi-directional many-to-many association to Skill 
	 */
	@ManyToMany
	@JoinTable(
			name="STAFF_SKILL",
			joinColumns= {
				@JoinColumn(name = "STAFF_ID", referencedColumnName="STAFF_ID")				
			},
			inverseJoinColumns={
				@JoinColumn(name="SKILL_ID", referencedColumnName="SKILL_ID")
			}
	)
	private List<Skill> skills;
	
	/**
	 * bi-directional one-to-many association to Supplier
	 * In SQL terms, Supplier is the "owner" of this relationship with Staff as it contains the relationship's foreign key
	 * In OO terms, this Staff "works for" these Suppliers
	 */
	@OneToMany(mappedBy="staff")
	private List<Supplier> suppliers;

	public Staff() {
		this.courses = new ArrayList<>();
		this.interests = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.staffProjectAssignments = new ArrayList<>();
		this.skills = new ArrayList<>();		
	}

	public long getStaffId() {
		return this.staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Interest> getInterests() {
		return this.interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public Interest addInterest(Interest interest) {
		getInterests().add(interest);
		return interest;
	}

	public Interest removeInterest(Interest interest) {
		getInterests().remove(interest);
		return interest;
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
	
	public List<StaffProjectAssignment> getStaffProjectAssignments() {
		return this.staffProjectAssignments;
	}

	public void setStaffProjectAssignment(List<StaffProjectAssignment> staffProjectAssignments) {
		this.staffProjectAssignments = staffProjectAssignments;
	}

	public StaffProjectAssignment addStaffProjectAssignment(Project project, Assignment assignment) {
		/**check if 'project' and 'assignment' aren't in staffProjectAgreements*/
		if ( !Collections.disjoint(this.getStaffProjectAssignments(), project.getStaffProjectAssignments()) )
			if ( !Collections.disjoint(this.getStaffProjectAssignments(), assignment.getStaffProjectAssignments()) )
				throw new EntityExistsException("Entities already exist in 'WORKS ON' association: [" + project + ", " + assignment + "]");
		
		
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment();
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(project, this, assignment);
		staffProjectAssignment.setStaffAssignmentId(id);
		getStaffProjectAssignments().add(staffProjectAssignment);
		return staffProjectAssignment;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Supplier> getSuppliers() {
		return this.suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public Supplier addSupplier(Supplier supplier) {
		getSuppliers().add(supplier);
		return supplier;
	}

	public Supplier removeSupplier(Supplier supplier) {
		getSuppliers().remove(supplier);
		return supplier;
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