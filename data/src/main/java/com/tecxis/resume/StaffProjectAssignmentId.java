package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The primary key class for STAFF_ASSIGNMENT database table
 * */
@Embeddable
public class StaffProjectAssignmentId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Project project;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ASSIGNMENT_ID", referencedColumnName="ASSIGNMENT_ID")
	private Assignment assignment;

	
	
	public StaffProjectAssignmentId(Project project, Staff staff, Assignment assignment) {
		this();
		this.project = project;
		this.staff = staff;
		this.assignment = assignment;
	}

	public StaffProjectAssignmentId() {
		super();
	}	

	public Project getProject() {
		return project;
	}

	public Staff getStaff() {
		return staff;
	}

	public Assignment getAssignment() {
		return assignment;
	}
		
	public void setProject(Project project) {
		this.project = project;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StaffProjectAssignmentId)) {
			return false;
		}
		StaffProjectAssignmentId castOther = (StaffProjectAssignmentId)other;
		return 
			(this.project.getClientId() == castOther.getProject().getClientId())
			&& (this.project.getProjectId() == castOther.getProject().getProjectId())
			&& (this.assignment.getAssignmentId() == castOther.getAssignment().getAssignmentId())
			&& (this.staff.getStaffId() == castOther.getStaff().getStaffId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.project.getClientId() ^ (this.project.getClientId() >>> 32)));
		hash = hash * prime + ((int) (this.project.getProjectId() ^ (this.project.getProjectId() >>> 32)));
		hash = hash * prime + ((int) (this.assignment.getAssignmentId() ^ (this.assignment.getAssignmentId() >>> 32)));
		hash = hash * prime + ((int) (this.staff.getStaffId() ^ (this.staff.getStaffId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "staffAssignmentId=[projectId=" + (this.getProject() != null ? this.getProject().getProjectId() : "null") + ", staffId=" + (this.staff != null ? this.staff.getStaffId() : "null")  + ", assignmentId=" + (this.assignment != null ? this.assignment.getAssignmentId() : "null" )+ "]" ; 
	}

	
	
}
