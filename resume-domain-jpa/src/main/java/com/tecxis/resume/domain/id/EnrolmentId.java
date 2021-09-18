package com.tecxis.resume.domain.id;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((staff == null) ? 0 : staff.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrolmentId other = (EnrolmentId) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (staff == null) {
			if (other.staff != null)
				return false;
		} else if (!staff.equals(other.staff))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  this.getClass().getName() + "@" + this.hashCode() + 
				"[staffId=" + (this.getStaff() != null ? this.getStaff().getId() : "null") + 
				", courseId=" + (this.getCourse() !=null ? this.getCourse().getId() : "null")  +
				"]";
	
	}

}
