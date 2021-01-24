package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.CustomSequenceGenerator;


/**
 * The persistent class for the COURSE database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= {"TITLE"}))
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
public class Course implements Serializable, StrongEntity {
	private static final String UNSUPPORTED_COURSE_STAFF_OPERATION = "Course -> Staff association is managed by association owner Enrolment";
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CustomSequenceGenerator", name="COURSE_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COURSE_SEQ")
	@Column(name="COURSE_ID")
	private long id;

	private Integer credits;

	@NotNull
	private String title;

	/**
	 * bi-directional many-to-many association to Staff
	 */
	//Cannot use mappedBy when cascading operations to Enrolment entity 
//	@ManyToMany(mappedBy="courses", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="ENROLMENT",
			joinColumns= {
				@JoinColumn(name="COURSE_ID", referencedColumnName="COURSE_ID")
			}, 
			inverseJoinColumns= {
				@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
				
			}
	)
	private  List<Staff> staffs;
	
	public Course() {
		this.staffs = new ArrayList <> ();
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Integer getCredits() {
		return this.credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<Staff> staffs) {
		throw new UnsupportedOperationException(UNSUPPORTED_COURSE_STAFF_OPERATION);
	}
	
	public void addStaff(Staff staff) {
		throw new UnsupportedOperationException(UNSUPPORTED_COURSE_STAFF_OPERATION);
	}
	
	public void removeStaff(Staff staff) {
		throw new UnsupportedOperationException(UNSUPPORTED_COURSE_STAFF_OPERATION);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Course)) {
			return false;
		}
		Course castOther = (Course)other;
		return 
			(this.getId() == castOther.getId());
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
		return "[" +this.getClass().getName()+ "@" + this.hashCode() + 
				"[id=" + this.getId() + "]]";
	}

}