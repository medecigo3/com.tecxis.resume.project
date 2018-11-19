package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the ASSIGNMENT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "\"DESC\"" }))
public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="\"DESC\"")
	private String desc;

	private BigDecimal priority;

	@Id
	@SequenceGenerator(name="ASSIGNMENT_ASSIGNMENTID_GENERATOR", sequenceName="ASSIGNMENT_SEQ", allocationSize=1,  initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSIGNMENT_ASSIGNMENTID_GENERATOR")
	@Column(name="ASSIGNMENT_ID")	
	private long assignmentId;
	
	/**
	 * bi-directional one-to-many association to StaffAssignment
	 * In SQL terms, AssignmentAssignment is the "owner" of this association with Assignment as it contains the relationship's foreign key
	 * In OO terms, this Assignment "is assigned" to staff assignments
	 * */	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ASSIGNMENT_ID", referencedColumnName="ASSIGNMENT_ID")
	private List <StaffAssignment> staffAssignments;
	
	//bi-directional many-to-one association to Project
//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID"),
//		@JoinColumn(name="NAME", referencedColumnName="NAME"),
//		@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID"),
//		@JoinColumn(name="VERSION", referencedColumnName="VERSION")
//		})
//	private Project project;

	public Assignment() {
		this.staffAssignments = new ArrayList<>();
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getPriority() {
		return this.priority;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}

//	public Project getProject() {
//		return this.project;
//	}
//
//	public void setProject(Project project) {
//		this.project = project;
//	}

	public long getAssignmentId() {
		return this.assignmentId;
	}
	
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}
	
	public List<StaffAssignment> getStaffAssignments() {
		return this.staffAssignments;
	}

	public void setStaffAssignment(List<StaffAssignment> staffAssignment) {
		this.staffAssignments = staffAssignment;
	}

	public StaffAssignment addStaffAssignment(StaffAssignment staffAssignment) {
		getStaffAssignments().add(staffAssignment);
		return staffAssignment;
	}

	public StaffAssignment removeStaffAssignment(StaffAssignment staffAssignment) {
		getStaffAssignments().remove(staffAssignment);
		return staffAssignment;
	}
	
	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}