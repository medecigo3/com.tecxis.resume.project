package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.EnrolmentId;

/**
 * The persistent class for the ENROLMENT database table.
 * 
 */
@Entity
@Table(name="ENROLMENT")
public class Enrolment implements Serializable{
	private static final long serialVersionUID = 1L;	

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
