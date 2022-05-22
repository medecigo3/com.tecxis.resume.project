package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.SequenceKeyGenerator;
import com.tecxis.resume.domain.id.Identifiable;


/**
 * The persistent class for the STAFF database table.
 * 
 */
@Entity
public class Staff implements Serializable, Identifiable <Long>{
	private static final String UNSUPPORTED_STAFF_INTEREST_OPERATION = "Staff -> Interest association managed by association owner Interest.";

	private static final String UNSUPPORTED_STAFF_SKILLS_OPERATION = "Staff -> Skills association managed by association owner StaffSkill.";

	private static final String UNSUPPORTED_STAFF_COURSE_OPERATION = "Staff -> Course association managed by association owner Enrolment.";
	
	private static final String UNSUPPORTED_STAFF_SKILL_OPERATION = "Staff -> Skill association managed by association owner StaffSkill.";

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceKeyGenerator", name="STAFF_SEQ", 
	 parameters = {
	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STAFF_SEQ")
	@Column(name="STAFF_ID")
	private long id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="BIRTH_DATE")
	private Date birthDate;

	@NotNull
	private String lastName;

	@NotNull
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
	@OneToMany(mappedBy="staff",  cascade = {CascadeType.ALL})	//Although REMOVE cascade type is set, this is non-identifying relationship hence REMOVE won't cascade. 
	private List<Interest> interests;

	/**
	 * bi-directional many-to-many association to Project
	 */
	@ManyToMany(mappedBy="staff", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Project> projects;
		
	/**
	 * bi-directional one-to-many association to Assignment
	 * In SQL terms, Assignment is the "owner" of this association with Staff as it contains the relationship's foreign key
	 * In OO terms, this Staff "works on" staff assignments
	 */
	@OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
	private List<Assignment> assignments;

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
	@OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval=true)
	private List <SupplyContract> supplyContracts;

	public Staff() {
		this.courses = new ArrayList<>();
		this.interests = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.assignments = new ArrayList<>();
		this.skills = new ArrayList<>();		
		this.supplyContracts = new ArrayList<>();
		this.employmentContracts = new ArrayList<>();
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
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
	
	public List<Assignment> getAssignments() {
		return this.assignments;
	}

	public void setAssignment(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	
	public boolean removeAssignment(Assignment assignment) {
		boolean ret = this.getAssignments().remove(assignment);		
		assignment.setStaff(null);
		assignment.setProject(null);
		assignment.setAssignment(null);
		return ret;
	}

	public Assignment addAssignment(Assignment assignment) {
		/**check if 'project' and 'Task' aren't in Assignments*/
		if ( this.getAssignments().contains(assignment))			
				throw new EntityExistsException("Entity already exist in Staff 'WORKS ON' association: " + assignment);
		
		getAssignments().add(assignment);
		return assignment;
	}

	public List<Skill> getSkills() {
		return skills;
	}
	
	public void addSkill(Skill skill) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_SKILL_OPERATION);
	}
	
	public boolean removeSkill(Skill skill) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_SKILL_OPERATION);
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
	    supplyContract.setStaff(this);
	
	}
	
	public void removeSupplyContract(SupplyContract supplyContract) {
		this.getSupplyContracts().remove(supplyContract);
		supplyContract.setStaff(null);
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
		return this.getClass().getName()+ "@" + this.hashCode() +
				"[staffId=" + this.getId() + 
				"]";
	}

}