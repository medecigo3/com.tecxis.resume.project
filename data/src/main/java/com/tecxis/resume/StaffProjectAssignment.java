package com.tecxis.resume;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

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
	public String toString() {
		return reflectionToString(this) + "[" + staffProjectAssignmentId.toString() + "]";
	}


}
