package com.tecxis.commons.persistence.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.Staff;

public class EnrolmentId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="COURSE_ID", referencedColumnName="COURSE_ID")
	private Course course;
	
	
	public EnrolmentId() {
		super();
		
	}

	public EnrolmentId(Staff staff, Course course) {
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
		if (!(other instanceof EnrolmentId)) {
			return false;
		}
		EnrolmentId castOther = (EnrolmentId)other;
		return
			(this.getStaff().getId() == castOther.getStaff().getId())
			&& (this.getStaff().getId() == castOther.getStaff().getId());
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getStaff().getId() ^ (this.getStaff().getId() >>> 32)));
		hash = hash * prime + ((int) (this.getCourse().getId()  ^ (this.getCourse().getId() >>> 32)));
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[staffId=" + (this.getStaff() != null ? this.getStaff().getId() : "null") + 
				", courseId=" + (this.getCourse() !=null ? this.getCourse().getId() : "null")  +
				"]]";
	
	}

}
