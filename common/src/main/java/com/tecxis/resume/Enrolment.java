//package com.tecxis.resume;
//
//import java.io.Serializable;
//import javax.persistence.*;
//import java.util.Date;
//
//
///**
// * The persistent class for the ENROLMENT database table.
// * 
// */
//@Entity
//public class Enrolment implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@EmbeddedId
//	private EnrolmentPK id;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="END_DATE")
//	private Date endDate;
//
//	private String grade;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="START_DATE")
//	private Date startDate;
//
//	//bi-directional many-to-one association to Course
//	@ManyToOne
//	@JoinColumn(name="COURSE_ID")
//	private Course course;
//
//	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name="STAFF_ID")
//	private Staff staff;
//
//	public Enrolment() {
//	}
//
//	public EnrolmentPK getId() {
//		return this.id;
//	}
//
//	public void setId(EnrolmentPK id) {
//		this.id = id;
//	}
//
//	public Date getEndDate() {
//		return this.endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//
//	public String getGrade() {
//		return this.grade;
//	}
//
//	public void setGrade(String grade) {
//		this.grade = grade;
//	}
//
//	public Date getStartDate() {
//		return this.startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public Course getCourse() {
//		return this.course;
//	}
//
//	public void setCourse(Course course) {
//		this.course = course;
//	}
//
////	public Staff getStaff() {
////		return this.staff;
////	}
////
////	public void setStaff(Staff staff) {
////		this.staff = staff;
////	}
//
//}