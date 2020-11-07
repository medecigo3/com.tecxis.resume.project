package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the ENROLMENT database table.
 * 
 */
@Entity
@Table(name="ENROLMENT")
public class Enrolment implements Serializable{
	private static final long serialVersionUID = 1L;

	public static class EnrolmentId implements Serializable{
		private static final long serialVersionUID = 1L;

		/**Directional association many-to-one to Staff*/
		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
		private Staff staff;
		
		/**Directional association many-to-one to Course*/
		@ManyToOne(cascade = CascadeType.ALL)
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
	

	@EmbeddedId
	private EnrolmentId enrolmentId;
	
	public Enrolment() {
		super();

	}	
	
	public Enrolment(EnrolmentId enrolmentId) {
		super();
		this.enrolmentId = enrolmentId;
	}
	
	public EnrolmentId getEnrolmentId() {
		return enrolmentId;
	}

	public void setEnrolmentId(EnrolmentId enrolmentId) {
		this.enrolmentId = enrolmentId;
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
		return
			(this.getEnrolmentId().getStaff().getId() == castOther.getEnrolmentId().getStaff().getId())
			&& (this.getEnrolmentId().getStaff().getId() == castOther.getEnrolmentId().getStaff().getId());
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getEnrolmentId().getStaff().getId() ^ (this.getEnrolmentId().getStaff().getId() >>> 32)));
		hash = hash * prime + ((int) (this.getEnrolmentId().getCourse().getId()  ^ (this.getEnrolmentId().getCourse().getId() >>> 32)));
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[staffId=" + (this.getEnrolmentId() != null ? this.getEnrolmentId().getStaff().getId() : "null")  + 
				", courseId=" + (this.getEnrolmentId() != null ?  this.getEnrolmentId().getCourse().getId() : "null")  +
				"]]";
	
	}
}
