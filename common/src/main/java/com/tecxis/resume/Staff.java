package com.tecxis.resume;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@SequenceGenerator(name="STAFF_STAFFID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STAFF_STAFFID_GENERATOR")
	@Column(name="STAFF_ID")
	private long staffId;

	@Temporal(TemporalType.DATE)
	@Column(name="BIRTH_DATE")
	private Date birthDate;

	private String lastname;

	private String name;

//	bi-directional many-to-one association to Enrolment -----> replaced by many-to-many association with Course
//	DB terms: Enrolment is the owner of the relationship as it contains a foreign key to this Staff
//	@OneToMany(mappedBy="staff")
//	@OneToMany
//	private List<Enrolment> enrolments;

	/**
	 * bi-directional many-to-many association to Course
	 */
	@ManyToMany
	@JoinTable(
			name="ENROLLMENT",
			joinColumns= {
				@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
			}, 
			inverseJoinColumns= {
				@JoinColumn(name="COURSE_ID", referencedColumnName="COURSE_ID")
			}
	)
	private List<Course> courses;

	//bi-directional many-to-one association to Interest
	@OneToMany(mappedBy="staff")
	private List<Interest> interests;

	//bi-directional many-to-one association to Project
//	DB terms: Project is the owner of this association as it contains a foreign key to this Staff
//	@OneToMany(mappedBy="staff")
	/**
	 * uni-directional one-to-many association to Project
	 * OO terms: this Staff "works on" Projects
	 */
	@OneToMany
	private List<Project> projects;

//	//bi-directional many-to-one association to StaffSkill --> replaced by many-to-many association with Skill
//	@OneToMany(mappedBy="staff")
//	private List<StaffSkill> staffSkills;

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
	
	//bi-directional many-to-one association to Supplier
//	DB terms: Supplier is the owner of the relationship as it contains a foreign key to this Staff
//	@OneToMany(mappedBy="staff")
	/**
	 * uni-directional one-to-many association to Supplier
	 * OO terms: this Staff "works for" Suppliers
	 */
	@OneToMany
	private List<Supplier> suppliers;

	public Staff() {
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

//	public List<Enrolment> getEnrolments() {
//		return this.enrolments;
//	}
//
//	public void setEnrolments(List<Enrolment> enrolments) {
//		this.enrolments = enrolments;
//	}

//	public Enrolment addEnrolment(Enrolment enrolment) {
//		getEnrolments().add(enrolment);
//		enrolment.setStaff(this);
//
//		return enrolment;
//	}
//
//	public Enrolment removeEnrolment(Enrolment enrolment) {
//		getEnrolments().remove(enrolment);
//		enrolment.setStaff(null);
//
//		return enrolment;
//	}
	
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
		interest.setStaff(this);

		return interest;
	}

	public Interest removeInterest(Interest interest) {
		getInterests().remove(interest);
		interest.setStaff(null);

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
//		project.setStaff(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
//		project.setStaff(null);

		return project;
	}

//	public List<StaffSkill> getStaffSkills() {
//		return this.staffSkills;
//	}
//
//	public void setStaffSkills(List<StaffSkill> staffSkills) {
//		this.staffSkills = staffSkills;
//	}
//
//	public StaffSkill addStaffSkill(StaffSkill staffSkill) {
//		getStaffSkills().add(staffSkill);
//		staffSkill.setStaff(this);
//
//		return staffSkill;
//	}
//
//	public StaffSkill removeStaffSkill(StaffSkill staffSkill) {
//		getStaffSkills().remove(staffSkill);
//		staffSkill.setStaff(null);
//
//		return staffSkill;
//	}

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
//		supplier.setStaff(this);

		return supplier;
	}

	public Supplier removeSupplier(Supplier supplier) {
		getSuppliers().remove(supplier);
//		supplier.setStaff(null);

		return supplier;
	}

}