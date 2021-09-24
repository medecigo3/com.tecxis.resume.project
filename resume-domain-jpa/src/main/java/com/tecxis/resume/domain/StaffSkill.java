package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.StaffSkillId;

/**
 * The persistent class for the STAFF_SKILL database table.
 * 
 */
@Entity
@Table(name=StaffSkill.STAFF_SKILL_TABLE)
public class StaffSkill implements Serializable{
	private static final long serialVersionUID = 1L;
	
	final public static String STAFF_SKILL_TABLE = "STAFF_SKILL";
	
	@EmbeddedId
	private StaffSkillId id;
	
	/**Directional many-to-one association to Skill*/
	@MapsId("skillId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Skill
	@JoinColumn(name="SKILL_ID", referencedColumnName="SKILL_ID")
	private Skill skill;
	
	/**Directional many-to-one association to Staff*/
	@MapsId("staffId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Staff
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
		

	public StaffSkill() {
		super();
		this.id = new StaffSkillId();

	}

	public StaffSkill(Skill skill, Staff staff) {
		this();
		this.getId().setSkillId(skill.getId());
		this.getId().setStaffId(staff.getId());
		this.setSkill(skill);
		this.setStaff(staff);
	}	

	public StaffSkillId getId() {
		return id;
	}

	public void setId(StaffSkillId id) {
		this.id = id;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
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
		StaffSkill other = (StaffSkill) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return  this.getClass().getName() +
				"[" + 
				this.getId() +
				"]";

	
	}
	
}
