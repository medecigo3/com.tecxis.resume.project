package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.StaffSkillId;

/**
 * The persistent class for the STAFF_SKILL database table.
 * 
 */
@Entity
@Table(name="STAFF_SKILL")
@IdClass(StaffSkillId.class)
public class StaffSkill implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**Directional many-to-one association to Skill*/
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SKILL_ID", referencedColumnName="SKILL_ID")
	private Skill skill;
	
	/**Directional many-to-one association to Staff*/
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;
		

	public StaffSkill() {
		super();

	}

	public StaffSkill(Skill skill, Staff staff) {
		super();
		this.skill = skill;
		this.staff = staff;
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
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StaffSkill)) {
			return false;
		}
		
		StaffSkill castOther = (StaffSkill)other;
		
		if (this.getSkill() != null && castOther.getSkill() != null) {
			if (this.getStaff() != null && castOther.getStaff() != null) {
				
				return 	this.getSkill().equals(castOther.getSkill()) &&
						this.getStaff().equals(castOther.getStaff());
			} else return false;
		} else return false;
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
	
		
		if (getSkill() != null)
			hash = hash * prime + this.getSkill().hashCode();
		
		if (getStaff() != null)
			hash = hash * prime + this.getStaff().hashCode();
		
		return hash;
	}
	
	@Override
	public String toString() {
	
		Skill skill = this.getSkill();;
		Staff staff = this.getStaff();
		
		return "["+ this.getClass().getName() +
					"[skillId=" + (skill != null ?  skill.getId() : "null") + 
					", staffId=" + (staff != null ? staff.getId() : "null") +
					"]]";

	
	}
	
}
