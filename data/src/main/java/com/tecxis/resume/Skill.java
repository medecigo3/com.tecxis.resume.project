package com.tecxis.resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;


/**
 * The persistent class for the SKILL database table.
 * 
 */
@Entity
@NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s")
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="SKILL_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SKILL_SEQ")
	@Column(name="SKILL_ID")
	private long skillId;

	private String name;
	
	/**
	 * bi-directional many-to-many association to Staff
	 * Relationship owned by {@code skills} field in {@link Staff} table
	 */
	@ManyToMany(mappedBy="skills")
	private List<Staff> staffs;
	

	public Skill() {
		this.staffs = new ArrayList <> ();
	}

	public long getSkillId() {
		return this.skillId;
	}

	public void setSkillId(long skillId) {
		this.skillId = skillId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Staff> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
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
			(this.getSkillId() == castOther.getSkillId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getSkillId() ^ (this.getSkillId() >>> 32)));	
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() +
				"[skillId=" + this.getSkillId() + "]]";
	}
}