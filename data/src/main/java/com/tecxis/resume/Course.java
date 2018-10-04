package com.tecxis.resume;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the COURSE database table.
 * 
 */
@Entity
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COURSE_COURSEID_GENERATOR", sequenceName="COURSE_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COURSE_COURSEID_GENERATOR")
	@Column(name="COURSE_ID")
	private long courseId;

	private int credits;

	private String title;

	//bi-directional many-to-one association to Enrolment --> replaced with many-to-many association to Staff
//	@OneToMany(mappedBy="course")
//	private List<Enrolment> enrolments;
	
	/**
	 * bi-directional many-to-many association to Staff 
	 * Relationship owned by {@code courses} field in {@link} Staff} table.
	 */
	@ManyToMany(mappedBy="courses")
	private  List<Staff> staffs;
	
	public Course() {
	}

	public long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public int getCredits() {
		return this.credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
	}

//	public List<Enrolment> getEnrolments() {
//		return this.enrolments;
//	}
//
//	public void setEnrolments(List<Enrolment> enrolments) {
//		this.enrolments = enrolments;
//	}
//
//	public Enrolment addEnrolment(Enrolment enrolment) {
//		getEnrolments().add(enrolment);
//		enrolment.setCourse(this);
//
//		return enrolment;
//	}
//
//	public Enrolment removeEnrolment(Enrolment enrolment) {
//		getEnrolments().remove(enrolment);
//		enrolment.setCourse(null);
//
//		return enrolment;
//	}
	
	

}