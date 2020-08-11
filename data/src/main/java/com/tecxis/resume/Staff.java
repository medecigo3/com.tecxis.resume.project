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
	private static final String UNSUPPORTED_STAFF_INTEREST_OPERATION = "Staff -> Interest association managed by association owner Interest.";

	private static final String UNSUPPORTED_STAFF_SKILLS_OPERATION = "Staff -> Skills association managed by association owner StaffSkill.";

	private static final String UNSUPPORTED_STAFF_COURSE_OPERATION = "Staff -> Course association managed by association owner Enrolment.";
	
	private static final String UNSUPPORTED_STAFF_STAFFPROJECTASSIGNMENT_OPERATION = "Staff -> StaffProjectAssignment association managed by association owner StaffProjectAssignment.";

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

	private String lastName;

	private String firstName;

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
	@OneToMany(mappedBy="staff",  cascade = {CascadeType.ALL})	
	private List<Interest> interests;

	/**
	 * bi-directional many-to-many association to Project
	 */
	@ManyToMany(mappedBy="staff", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
	 * bi-directional one-to-many association to EmploymentContract. 
	 * In SQL terms, EmploymentContract is the "owner" of this relationship with Staff as it contains the relationship's foreign key
	 * In OO terms, this Staff  "IS EMPLOYED" through EmploymentContracts. 
	 */
	@OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<EmploymentContract> employmentContracts;
	
	
	/**
	 * bi-directional one-to-many association to SupplyContract. 
	 * In SQL terms, ContractSupply is the "owner" of this relationship with Staff as it contains the relationship's foreign key
	 * In OO terms, this Staff "WORKS IN" these SupplyContracts. 
	 */
	@OneToMany(mappedBy = "supplyContractId.staff", cascade = CascadeType.ALL, orphanRemoval=true)
	private List <SupplyContract> supplyContracts;

	public Staff() {
		this.courses = new ArrayList<>();
		this.interests = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.staffProjectAssignments = new ArrayList<>();
		this.skills = new ArrayList<>();		
		this.supplyContracts = new ArrayList<>();
		this.employmentContracts = new ArrayList<>();
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

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_COURSE_OPERATION);
	}
	
	public void addCourse(Course course) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_COURSE_OPERATION);
	}
	
	public void removeCourse(Course course) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_COURSE_OPERATION);
	}

	public List<Interest> getInterests() {
		return this.interests;
	}
	
	public void setInterests(List <Interest> interests) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_INTEREST_OPERATION);
	}
	
	public void addInterest(Interest interest) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_INTEREST_OPERATION);
	}
	
	public boolean removeInterest(Interest interest) {
		boolean ret = this.getInterests().remove(interest);
		interest.setStaff(null);
		return ret;
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
	
	public void addStaffProjectAssignment(StaffProjectAssignment staffProjectAssignment) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_STAFFPROJECTASSIGNMENT_OPERATION);
	}
	
	public void removeStaffProjectAssignment(StaffProjectAssignment staffProjectAssignment) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_STAFFPROJECTASSIGNMENT_OPERATION); 
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
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_SKILLS_OPERATION);
	}
	
	public void setStaffSkills(List <StaffSkill> staffSkills) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_SKILLS_OPERATION);
	}

	public List<EmploymentContract> getEmploymentContracts() {
		return employmentContracts;
	}
	
	public void setEmploymentContracts(List<EmploymentContract> employmentContracts) {
		if (employmentContracts != null) {
			this.getEmploymentContracts().clear();
			for (EmploymentContract employmentContract : employmentContracts){
				this.getEmploymentContracts().add(employmentContract);
			}
		} else {
			this.employmentContracts.clear();
		}
	}

	public void addEmploymentContract(EmploymentContract employmentContract) {
		getEmploymentContracts().add(employmentContract);
		employmentContract.setStaff(this);
	
	}

	public void removeEmploymentContract(EmploymentContract employmentContract) {
		this.employmentContracts.remove(employmentContract);
		employmentContract.setStaff(null);
	
	}
		
	public List<SupplyContract> getSupplyContracts() {
		return supplyContracts;
	}

	public void setSupplyContracts(List<SupplyContract> supplyContracts) {
		if(supplyContracts != null) {
			this.getSupplyContracts().clear();
			for (SupplyContract supplyContract: supplyContracts) {
				this.getSupplyContracts().add(supplyContract);
			}
		} else {
			this.supplyContracts.clear();
		}		
	}
	
	public void addSupplyContract(SupplyContract supplyContract) {		
	    this.supplyContracts.add(supplyContract);
	    supplyContract.getSupplyContractId().setStaff(this);
	
	}
	
	public void removeSupplyContract(SupplyContract supplyContract) {
		this.getSupplyContracts().remove(supplyContract);
		supplyContract.getSupplyContractId().setStaff(null);
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