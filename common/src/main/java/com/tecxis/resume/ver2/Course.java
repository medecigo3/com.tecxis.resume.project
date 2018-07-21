package com.tecxis.resume.ver2;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the COURSE database table.
 * 
 */
@Entity
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COURSE_COURSEID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COURSE_COURSEID_GENERATOR")
	@Column(name="COURSE_ID")
	private long courseId;

	private BigDecimal credits;

	private String title;

	//bi-directional many-to-one association to Enrolment
	@OneToMany(mappedBy="course")
	private List<Enrolment> enrolments;

	public Course() {
	}

	public long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public BigDecimal getCredits() {
		return this.credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(List<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	public Enrolment addEnrolment(Enrolment enrolment) {
		getEnrolments().add(enrolment);
		enrolment.setCourse(this);

		return enrolment;
	}

	public Enrolment removeEnrolment(Enrolment enrolment) {
		getEnrolments().remove(enrolment);
		enrolment.setCourse(null);

		return enrolment;
	}

}