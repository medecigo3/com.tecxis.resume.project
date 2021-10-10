package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.AssignmentId;

/**
 * The persistent class for the STAFF_ASSIGNMENT database table.
 * 
 */
@Entity
@Table(name=Assignment.ASSIGNMENT_TABLE)
public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ASSIGNMENT_TABLE = "ASSIGNMENT";
	
	@EmbeddedId
	private AssignmentId id; 
	
	@MapsId("projectId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Project 
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private Project project;
	
	@MapsId("staffId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Staff
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
	
	@MapsId("taskId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})//Do not cascade REMOVE to Task 
	@JoinColumn(name="TASK_ID", referencedColumnName="TASK_ID")
	private Task task;

	
	
	public Assignment() {
		super();
		this.id = new AssignmentId();
	}

	public Assignment(Project project, Staff staff, Task task) {
		this();
		this.getId().setProjectId(project.getId());
		this.getId().setStaffId(staff.getId());
		this.getId().setTaskId(task.getId());
		this.setProject(project);		
		this.setStaff(staff);
		this.setAssignment(task);
	}

	public AssignmentId getId() {
		return id;
	}

	public void setId(AssignmentId id) {
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

	public Task getAssignment() {
		return task;
	}

	public void setAssignment(Task task) {
		this.task = task;
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
		Assignment other = (Assignment) obj;
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
