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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.CustomSequenceGenerator;


/**
 * The persistent class for the SKILL database table.
 * 
 */
@Entity
@NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s")
public class Skill implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;
	
	public static final String SKILL_TABLE = "SKILL";

	private static final String UNSUPPORTED_STAFF_SKILL_OPERATION = "Skill -> Staff association managed by association owner StaffSkill.";
	
	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CustomSequenceGenerator", name="SKILL_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SKILL_SEQ")
	@Column(name="SKILL_ID")
	private long id;

	@NotNull
	private String name;
	
	/**
	 * bi-directional many-to-many association to Staff
	 * Relationship owned by {@code skills} field in {@link Staff} table
	 */
	@ManyToMany (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="STAFF_SKILL",
			joinColumns= {
					@JoinColumn(name="SKILL_ID", referencedColumnName="SKILL_ID")
					},
			inverseJoinColumns={
					@JoinColumn(name = "STAFF_ID", referencedColumnName="STAFF_ID")				
					
			}
	)
	private List<Staff> staff;
	

	public Skill() {
		this.staff = new ArrayList <> ();
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}
	
	public void addStaff(Staff staff) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_SKILL_OPERATION);
	}
	
	public boolean removeStaff(Staff skill) {
		throw new UnsupportedOperationException(UNSUPPORTED_STAFF_SKILL_OPERATION);
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Skill)) {
			return false;
		}
		Skill castOther = (Skill)other;
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