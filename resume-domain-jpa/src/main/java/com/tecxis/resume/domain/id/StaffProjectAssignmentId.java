package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;

/**
 * The primary key class for STAFF_ASSIGNMENT database table
 * */

public class StaffProjectAssignmentId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Project project;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
		
		if(this.getAssignment() != null && castOther.getAssignment() != null) {
			if (this.getProject() != null && castOther.getProject() != null) {
				if (this.getStaff() != null && castOther.getStaff() != null) {
					
					return 	this.getAssignment().equals(castOther.getAssignment()) &&
							this.getProject().equals(castOther.getProject()) && 
							this.getStaff().equals(castOther.getStaff());
				} else return false;
			} else return false;				
		} else return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		if (this.getAssignment() != null) 
			hash = hash * prime + this.getAssignment().hashCode();
		
		if (this.getProject() != null)
			hash = hash * prime + this.getProject().hashCode();
		
		if (this.getStaff() != null)
			hash = hash * prime + this.getStaff().hashCode();
				
		return hash;
	}

	@Override
	public String toString() {
		return this.getClass().getName()+ "@" + this.hashCode() + 
				"[projectId=" + (this.getProject() != null ? this.getProject().getId() : "null") + 
				", staffId=" + (this.staff != null ? this.staff.getId() : "null")  +
				", assignmentId=" + (this.assignment != null ? this.assignment.getId() : "null" )+ "]" ; 
	}

	
	
}
