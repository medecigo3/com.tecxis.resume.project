package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;


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
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="ASSIGNMENT_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSIGNMENT_SEQ")
	@Column(name="ASSIGNMENT_ID")	
	private long assignmentId;
	
	/**
	 * bi-directional one-to-many association to StaffProjectAssignment
	 * In SQL terms, AssignmentAssignment is the "owner" of this association with Assignment as it contains the relationship's foreign key
	 * In OO terms, this Assignment "is assigned" to staff assignments
	 * */	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ASSIGNMENT_ID", referencedColumnName="ASSIGNMENT_ID")
	private List <StaffProjectAssignment> staffProjectAssignments;

	public Assignment() {
		this.staffProjectAssignments = new ArrayList<>();
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

	public long getAssignmentId() {
		return this.assignmentId;
	}
	
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}
	
	public List<StaffProjectAssignment> getStaffProjectAssignments() {
		return this.staffProjectAssignments;
	}

	public void setStaffProjectAssignment(List<StaffProjectAssignment> staffProjectAssignment) {
		this.staffProjectAssignments = staffProjectAssignment;
	}

	public StaffProjectAssignment addStaffProjectAssignment(Staff staff, Project project) {
		/**check if 'staff' and 'project' aren't in staffProjectAgreements*/
		if ( !Collections.disjoint(this.getStaffProjectAssignments(), staff.getStaffProjectAssignments()) )
			if ( !Collections.disjoint(this.getStaffProjectAssignments(), project.getStaffProjectAssignments()) )
				throw new EntityExistsException("Entities already exist in 'ASSIGNS' association: [" + staff + ", " + project + "]");
		
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment();
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(project, staff, this);
		staffProjectAssignment.setStaffAssignmentId(id);
		getStaffProjectAssignments().add(staffProjectAssignment);
		return staffProjectAssignment;
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