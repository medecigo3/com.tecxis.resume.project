package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.EnrolmentId;

/**
 * The persistent class for the ENROLMENT database table.
 * 
 */
@Entity
@Table(name="ENROLMENT")
@IdClass(EnrolmentId.class)
public class Enrolment implements Serializable{
	private static final long serialVersionUID = 1L;	

	/**Directional association many-to-one to Staff*/
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	/**Directional association many-to-one to Course*/
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="COURSE_ID", referencedColumnName="COURSE_ID")
	private Course course;
	
	public Enrolment() {
		super();

	}	
	

	public Enrolment(Staff staff, Course course) {
		super();
		this.staff = staff;
		this.course = course;
	}


	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Enrolment)) {
			return false;
		}
		Enrolment castOther = (Enrolment)other;
	
		if (this.getStaff() != null && castOther.getStaff() !=null) {
			if (this.getCourse() != null && castOther.getCourse() != null) {
				
				return (getStaff().getId() == castOther.getStaff().getId())
				&& (getCourse().getId() == castOther.getCourse().getId());
				
			}else return false;
				
		} else return false;

			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		if (this.getStaff() != null)
			hash = hash * prime + ((int) (this.getStaff().getId() ^ (this.getStaff().getId() >>> 32)));
		
		if (this.getCourse() != null)
		hash = hash * prime + ((int) (this.getCourse().getId()  ^ (this.getCourse().getId() >>> 32)));
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[staffId=" + (getStaff() != null ? this.getStaff().getId() : "null")  + 
				", courseId=" + (getCourse() != null ?  this.getCourse().getId() : "null")  +
				"]]";
	
	}
}
