package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


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

	//bi-directional many-to-one association to Enrolment
	@OneToMany(mappedBy="staff")
	private List<Enrolment> enrolments;

	//bi-directional many-to-one association to Interest
	@OneToMany(mappedBy="staff")
	private List<Interest> interests;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="staff")
	private List<Project> projects;

	//bi-directional many-to-one association to StaffSkill
	@OneToMany(mappedBy="staff")
	private List<StaffSkill> staffSkills;

	//bi-directional many-to-one association to Supplier
	@OneToMany(mappedBy="staff")
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

	public List<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(List<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	public Enrolment addEnrolment(Enrolment enrolment) {
		getEnrolments().add(enrolment);
		enrolment.setStaff(this);

		return enrolment;
	}

	public Enrolment removeEnrolment(Enrolment enrolment) {
		getEnrolments().remove(enrolment);
		enrolment.setStaff(null);

		return enrolment;
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
		project.setStaff(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setStaff(null);

		return project;
	}

	public List<StaffSkill> getStaffSkills() {
		return this.staffSkills;
	}

	public void setStaffSkills(List<StaffSkill> staffSkills) {
		this.staffSkills = staffSkills;
	}

	public StaffSkill addStaffSkill(StaffSkill staffSkill) {
		getStaffSkills().add(staffSkill);
		staffSkill.setStaff(this);

		return staffSkill;
	}

	public StaffSkill removeStaffSkill(StaffSkill staffSkill) {
		getStaffSkills().remove(staffSkill);
		staffSkill.setStaff(null);

		return staffSkill;
	}

	public List<Supplier> getSuppliers() {
		return this.suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public Supplier addSupplier(Supplier supplier) {
		getSuppliers().add(supplier);
		supplier.setStaff(this);

		return supplier;
	}

	public Supplier removeSupplier(Supplier supplier) {
		getSuppliers().remove(supplier);
		supplier.setStaff(null);

		return supplier;
	}

}