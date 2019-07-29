package com.tecxis.resume;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;


/**
 * The persistent class for the STAFF database table.
 * 
 */
@Entity
public class Staff implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="STAFF_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STAFF_SEQ")
	@Column(name="STAFF_ID")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="BIRTH_DATE")
	private Date birthDate;

	private String lastname;

	private String name;

	/**
	 * bi-directional many-to-many association to Course
	 */
	@ManyToMany (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
	 * bi-directional association to Staff
	 * In SQL terms, Interest is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this Staff "has" Interest(s)
	 */
	@OneToMany(mappedBy="staff",  cascade = CascadeType.ALL)	
	private List<Interest> interests;

	/**
	 * bi-directional many-to-many association to Project
	 */
	@ManyToMany(mappedBy="staffs", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Project> projects;
		
	/**
	 * bi-directional one-to-many association to StaffProjectAssignment
	 * In SQL terms, StaffProjectAssignment is the "owner" of this association with Staff as it contains the relationship's foreign key
	 * In OO terms, this Staff "works on" staff assignments
	 */
	@OneToMany(mappedBy = "staffProjectAssignmentId.staff", cascade = CascadeType.ALL)
	private List<StaffProjectAssignment> staffProjectAssignments;

	/**
	 *  bi-directional many-to-many association to Skill 
	 */
	@ManyToMany (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
	@OneToMany(mappedBy="staff", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Supplier> suppliers;

	public Staff() {
		this.courses = new ArrayList<>();
		this.interests = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.staffProjectAssignments = new ArrayList<>();
		this.skills = new ArrayList<>();		
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
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

	public List<Project> getProjects() {
		return this.projects;
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
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Staff)) {
			return false;
		}
		Staff castOther = (Staff)other;
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
				"[id=" + this.getId() + "]]";
	}

}