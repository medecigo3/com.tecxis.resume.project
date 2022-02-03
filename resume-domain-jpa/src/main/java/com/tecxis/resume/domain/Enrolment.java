package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.EnrolmentId;

/**
 * The persistent class for the ENROLMENT database table.
 * 
 */
@Entity
@Table(name=SchemaConstants.ENROLMENT_TABLE)
public class Enrolment implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	@EmbeddedId
	private EnrolmentId id;

	/**Directional association many-to-one to Staff*/
	@MapsId("staffId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //DO not cascade REMOVE to Staff
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	/**Directional association many-to-one to Course*/
	@MapsId("courseId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Course
	@JoinColumn(name="COURSE_ID", referencedColumnName="COURSE_ID")
	private Course course;
	
	public Enrolment() {
		super();
		this.id = new EnrolmentId();

	}		

	public Enrolment(Staff staff, Course course) {
		this();
		this.getId().setStaffId(staff.getId());
		this.getId().setCourseId(course.getId());
		this.setStaff(staff);
		this.setCourse(course);
	}

	public EnrolmentId getId() {
		return id;
	}


	public void setId(EnrolmentId id) {
		this.id = id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Enrolment other = (Enrolment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return  this.getClass().getName() + "[" +
				this.getId() +
				"]";
	
	}
}
