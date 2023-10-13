package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.Identifiable;
import com.tecxis.resume.domain.id.SequenceKeyGenerator;


/**
 * The persistent class for the TASK database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "\"DESC\"" }))
public class Task implements Serializable, Identifiable <Long> {	
	private static final long serialVersionUID = 1L;
	@NotNull
	@Column(name="\"DESC\"")
	private String desc;

	private Integer priority;//RES-72

	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceKeyGenerator", name="ASSIGNMENT_SEQ", 
	 parameters = {
	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TASK_SEQ")
	@Column(name="TASK_ID")
	private long id;
	
	/**
	 * bi-directional one-to-many association to Assignment
	 * In SQL terms, AssignmentAssignment is the "owner" of this association with Task as it contains the relationship's foreign key
	 * In OO terms, this Task "is executed" by a staff assignment
	 * */	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private List <Assignment> assignments;

	public Task() {
		this.assignments = new ArrayList<>();
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getPriority() {//RES-72
		return this.priority;
	}

	public void setPriority(Integer priority) {//RES-72
		this.priority = priority;
	}

	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Assignment> getAssignments() {
		return this.assignments;
	}
	/**
	 * 
	 * @param assignments orphans are removed when set to null.
	 */
	public void setAssignments(List<Assignment> assignments) {
		this.assignments.clear();
		if (assignments != null)//RES-7
			this.assignments.addAll(assignments);
	}

	public Assignment addAssignment(Assignment assignment) {
		/**check if 'staff' and 'project' aren't in Assignments*/
		if ( this.getAssignments().contains(assignment))	
			throw new EntityExistsException("Entity already exists in this association:" + assignment.toString());

		getAssignments().add(assignment);
		return assignment;
	}
	
	public boolean removeAssignment(Assignment assignment) {
		boolean ret = this.getAssignments().remove(assignment);
		assignment.setStaff(null);
		assignment.setProject(null);
		assignment.setAssignment(null);
		return ret;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Task)) {
			return false;
		}
		Task castOther = (Task)other;
		return 
			this.getId() == castOther.getId();
			
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));
				
		return hash;
	}

	@Override
	public String toString() {
		return  this.getClass().getName() + "@" + this.hashCode() +
				"[taskId=" + this.getId() + "]"; 
	}
}