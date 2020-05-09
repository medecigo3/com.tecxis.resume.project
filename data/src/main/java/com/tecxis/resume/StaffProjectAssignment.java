package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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

	public StaffProjectAssignmentId getStaffAssignmentId() {
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
		
		if (castOther.getStaffAssignmentId() == null)
			return false;
		
		return 
			(this.getStaffAssignmentId().getProject().getClient().getId() 			== castOther.getStaffAssignmentId().getProject().getClient().getId())
			&& (this.getStaffAssignmentId().getProject().getId() 		== castOther.getStaffAssignmentId().getProject().getId())
			&& (this.getStaffAssignmentId().getAssignment().getId()	== castOther.getStaffAssignmentId().getAssignment().getId())
			&& (this.getStaffAssignmentId().getStaff().getId()				== castOther.getStaffAssignmentId().getStaff().getId());
			
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getStaffAssignmentId().getProject().getClient().getId() 		^ (this.getStaffAssignmentId().getProject().getClient().getId() )));
		hash = hash * prime + ((int) (this.getStaffAssignmentId().getProject().getId()    	^ (this.getStaffAssignmentId().getProject().getId()  )));
		hash = hash * prime + ((int) (this.getStaffAssignmentId().getAssignment().getId() ^ (this.getStaffAssignmentId().getAssignment().getId()  >>> 32)));
		hash = hash * prime + ((int) (this.getStaffAssignmentId().getStaff().getId()		 	^ (this.getStaffAssignmentId().getStaff().getId()	>>> 32)));
		
		return hash;
	}
	
	@Override
	public String toString() {
		return  "["+this.getClass().getName()+ "@" + this.getStaffAssignmentId().hashCode()
				+  this.getStaffAssignmentId().toString() + "]";
	}


}
