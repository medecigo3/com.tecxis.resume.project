package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for STAFF_ASSIGNMENT database table
 * */
@Embeddable
public class StaffProjectAssignmentId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ProjectId projectId; // corresponds to PK type of Project
	

	private long staffId; // corresponds to PK type of Staff
	

	private long assignmentId; // corresponds to PK of Assignment

	
	
	public StaffProjectAssignmentId(ProjectId projectId, long staffId, long assignmentId) {
		this();
		this.projectId = projectId;
		this.staffId = staffId;
		this.assignmentId = assignmentId;
	}

	public StaffProjectAssignmentId() {
		super();
	}	

	public ProjectId getProjectId() {
		return projectId;
	}

	public void setProjectId(ProjectId projectId) {
		this.projectId = projectId;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (assignmentId ^ (assignmentId >>> 32));
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
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
		StaffProjectAssignmentId other = (StaffProjectAssignmentId) obj;
		if (assignmentId != other.assignmentId)
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (staffId != other.staffId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName()+ "@" + this.hashCode() + 
				"[" + (this.getProjectId() != null ? this.getProjectId() : "projectId=null") + 
				", staffId=" +  this.getStaffId()   +
				", assignmentId=" + this.getAssignmentId() + "]" ; 
	}

	
	
}
