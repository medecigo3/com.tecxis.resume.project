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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		StaffProjectAssignment other = (StaffProjectAssignment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  this.getClass().getName() + "[" +
				this.getId() + 
				"]";
	}


}
