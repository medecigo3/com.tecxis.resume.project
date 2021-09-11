package com.tecxis.resume.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.tecxis.resume.domain.id.CustomSequenceGenerator;
import com.tecxis.resume.domain.id.Identifiable;


/**
 * The persistent class for the ASSIGNMENT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "\"DESC\"" }))
public class Assignment implements Serializable, Identifiable <Long> {	
	private static final long serialVersionUID = 1L;
	public static final String ASSIGNMENT_TABLE = "ASSIGNMENT";

	@NotNull
	@Column(name="\"DESC\"")
	private String desc;

	private BigDecimal priority;

	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CustomSequenceGenerator", name="ASSIGNMENT_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSIGNMENT_SEQ")
	@Column(name="ASSIGNMENT_ID")
	private long id;
	
	/**
	 * bi-directional one-to-many association to StaffProjectAssignment
	 * In SQL terms, AssignmentAssignment is the "owner" of this association with Assignment as it contains the relationship's foreign key
	 * In OO terms, this Assignment "is assigned" to staff assignments
	 * */	
	@OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
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

	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<StaffProjectAssignment> getStaffProjectAssignments() {
		return this.staffProjectAssignments;
	}

	public void setStaffProjectAssignment(List<StaffProjectAssignment> staffProjectAssignment) {
		this.staffProjectAssignments = staffProjectAssignment;
	}

	public StaffProjectAssignment addStaffProjectAssignment(StaffProjectAssignment staffProjectAssignment) {
		/**check if 'staff' and 'project' aren't in staffProjectAgreements*/
		if ( this.getStaffProjectAssignments().contains(staffProjectAssignment))	
			throw new EntityExistsException("Entity already exist in 'ASSIGNS' association:" + staffProjectAssignment.toString());

		getStaffProjectAssignments().add(staffProjectAssignment);
		return staffProjectAssignment;
	}
	
	public boolean removeStaffProjectAssignment(StaffProjectAssignment staffProjectAssignment) {
		boolean ret = this.getStaffProjectAssignments().remove(staffProjectAssignment);
		staffProjectAssignment.setStaff(null);
		staffProjectAssignment.setProject(null);
		staffProjectAssignment.setAssignment(null);
		return ret;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Assignment)) {
			return false;
		}
		Assignment castOther = (Assignment)other;
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
		return "[" +this.getClass().getName() + "@" + this.hashCode() +
				"[id=" + this.getId() + "]]"; 
	}
}