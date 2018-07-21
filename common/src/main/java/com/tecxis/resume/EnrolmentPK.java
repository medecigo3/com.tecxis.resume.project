package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLMENT database table.
 * 
 */
@Embeddable
public class EnrolmentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="COURSE_ID", insertable=false, updatable=false)
	private long courseId;

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;

	public EnrolmentPK() {
	}
	public long getCourseId() {
		return this.courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public long getStaffId() {
		return this.staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrolmentPK)) {
			return false;
		}
		EnrolmentPK castOther = (EnrolmentPK)other;
		return 
			(this.courseId == castOther.courseId)
			&& (this.staffId == castOther.staffId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.courseId ^ (this.courseId >>> 32)));
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		
		return hash;
	}
}