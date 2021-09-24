package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.StaffProjectAssignmentId;

/**
 * The persistent class for the STAFF_ASSIGNMENT database table.
 * 
 */
@Entity
@Table(name=StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE)
public class StaffProjectAssignment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String STAFF_PROJECT_ASSIGNMENT_TABLE = "STAFF_PROJECT_ASSIGNMENT";
	
	@EmbeddedId
	private StaffProjectAssignmentId id; 
	
	@MapsId("projectId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Project 
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Project project;
	
	@MapsId("staffId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Staff
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	@MapsId("assignmentId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})//Do not cascade REMOVE to Assignment 
	@JoinColumn(name="ASSIGNMENT_ID", referencedColumnName="ASSIGNMENT_ID")
	private Assignment assignment;

	
	
	public StaffProjectAssignment() {
		super();
		this.id = new StaffProjectAssignmentId();
	}

	public StaffProjectAssignment(Project project, Staff staff, Assignment assignment) {
		this();
		this.getId().setProjectId(project.getId());
		this.getId().setStaffId(staff.getId());
		this.getId().setAssignmentId(assignment.getId());
		this.setProject(project);		
		this.setStaff(staff);
		this.setAssignment(assignment);
	}

	public StaffProjectAssignmentId getId() {
		return id;
	}

	public void setId(StaffProjectAssignmentId id) {
		this.id = id;
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
		return  this.getClass().getName() + "[" +
				this.getId() + 
				"]";
	}


}
