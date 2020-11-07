package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.StaffProjectAssignmentId;

/**
 * The persistent class for the STAFF_ASSIGNMENT database table.
 * 
 */
@Entity
@Table(name="STAFF_PROJECT_ASSIGNMENT")
public class StaffProjectAssignment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private StaffProjectAssignmentId staffProjectAssignmentId;

	public StaffProjectAssignmentId getStaffProjectAssignmentId() {
		return staffProjectAssignmentId;
	}

	public void setStaffAssignmentId(StaffProjectAssignmentId staffProjectAssignmentId) {
		this.staffProjectAssignmentId = staffProjectAssignmentId;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StaffProjectAssignment)) {
			return false;
		}
		StaffProjectAssignment castOther = (StaffProjectAssignment)other;
		
		if(this.getStaffProjectAssignmentId().getAssignment() != null && castOther.getStaffProjectAssignmentId().getAssignment() != null) {
			if (this.getStaffProjectAssignmentId().getProject() != null && castOther.getStaffProjectAssignmentId().getProject() != null) {
				if (this.getStaffProjectAssignmentId().getStaff() != null && castOther.getStaffProjectAssignmentId().getStaff() != null) {
					
					return 	this.getStaffProjectAssignmentId().getAssignment().equals(castOther.getStaffProjectAssignmentId().getAssignment()) &&
							this.getStaffProjectAssignmentId().getProject().equals(castOther.getStaffProjectAssignmentId().getProject()) && 
							this.getStaffProjectAssignmentId().getStaff().equals(castOther.getStaffProjectAssignmentId().getStaff());
				} else return false;
			} else return false;				
		} else return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		StaffProjectAssignmentId staffProjectAssignmentId = this.getStaffProjectAssignmentId();
		if (staffProjectAssignmentId != null) {
			if (staffProjectAssignmentId.getProject() != null)
				hash = hash * prime + staffProjectAssignmentId.getProject().hashCode();
			
			if (staffProjectAssignmentId.getAssignment() != null)
				hash = hash * prime + staffProjectAssignmentId.getAssignment().hashCode();
			
			if (staffProjectAssignmentId.getStaff() != null)
				hash = hash * prime + staffProjectAssignmentId.getStaff().hashCode();
		}

		return hash;
	}
	
	@Override
	public String toString() {
		return  "["+this.getClass().getName()+ "@" + this.getStaffProjectAssignmentId().hashCode()
				+  this.getStaffProjectAssignmentId().toString() + "]";
	}


}
