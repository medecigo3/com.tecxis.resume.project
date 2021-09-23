package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.StaffProjectAssignmentId;

/**
 * The persistent class for the STAFF_ASSIGNMENT database table.
 * 
 */
@Entity
@Table(name=StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE)
@IdClass(StaffProjectAssignmentId.class)
public class StaffProjectAssignment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String STAFF_PROJECT_ASSIGNMENT_TABLE = "STAFF_PROJECT_ASSIGNMENT";
	
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Project project;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ASSIGNMENT_ID", referencedColumnName="ASSIGNMENT_ID")
	private Assignment assignment;

	
	
	public StaffProjectAssignment() {
		super();
	}

	public StaffProjectAssignment(Project project, Staff staff, Assignment assignment) {
		super();
		this.project = project;
		this.staff = staff;
		this.assignment = assignment;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
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
		
		
		
			if (getProject() != null)
				hash = hash * prime + getProject().hashCode();
			
			if (getAssignment() != null)
				hash = hash * prime + getAssignment().hashCode();
			
			if (getStaff() != null)
				hash = hash * prime + getStaff().hashCode();
		

		return hash;
	}
	
	@Override
	public String toString() {
		Project project = this.getProject();		
		Staff staff = this.getStaff();		
		Assignment assignment = this.getAssignment();
		
		
		return  this.getClass().getName()+ "@" + this.hashCode() + 
				"[projectId=" + (project != null ? project.getId() : "null") +
				", staffId=" + (staff != null ? staff.getId() : "null") +
				", assignmentId=" + (assignment != null ? assignment.getId() : "null") + 
				"]";
	}


}
