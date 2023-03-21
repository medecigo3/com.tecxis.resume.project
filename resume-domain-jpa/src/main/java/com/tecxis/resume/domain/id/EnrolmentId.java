package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EnrolmentId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long staffId;//corresponds to PK type of Staff
	

	private long courseId;//corresponds to PK type of Course
	
	
	public EnrolmentId() {		
	}

	public EnrolmentId(long staffId, long courseId) {
		this();
		this.staffId = staffId;
		this.courseId = courseId;
	}
	
	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (courseId ^ (courseId >>> 32));
		result = prime * result + (int) (staffId ^ (staffId >>> 32));
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
		if (courseId != other.courseId)
			return false;
		if (staffId != other.staffId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  this.getClass().getName() + "@" + this.hashCode() + 
				"[staffId=" + this.getStaffId()+ 
				", courseId=" + this.getCourseId()   +
				"]";
	
	}

}
