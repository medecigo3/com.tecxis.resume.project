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
@Table(name="STAFF_ASSIGNMENT")
public class StaffAssignment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private StaffAssignmentId staffAssignmentId;

	public StaffAssignmentId getStaffAssignmentId() {
		return staffAssignmentId;
	}

	public void setStaffAssignmentId(StaffAssignmentId staffAssignmentId) {
		this.staffAssignmentId = staffAssignmentId;
	}
	

}
