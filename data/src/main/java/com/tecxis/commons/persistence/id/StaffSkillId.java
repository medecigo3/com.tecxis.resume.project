package com.tecxis.commons.persistence.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tecxis.resume.Skill;
import com.tecxis.resume.Staff;

@Embeddable
public class StaffSkillId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**Directional many-to-one association to Skill*/
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SKILL_ID", referencedColumnName="SKILL_ID")
	private Skill skill;
	
	/**Directional many-to-one association to Staff*/
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
	private Staff staff;

	public StaffSkillId() {
		super();
	}
	
	public StaffSkillId(Staff staff, Skill skill) {
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
		if (!(other instanceof StaffSkillId)) {
			return false;
		}
		StaffSkillId castOther = (StaffSkillId)other;
		
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
		if (this.getSkill() != null)
			hash = hash * prime + this.getSkill().hashCode();
		
		if (this.getStaff() != null)
			hash = hash * prime + this.getStaff().hashCode();
		
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[skillId=" + (this.getSkill() != null ? this.getSkill().getId() : "null") + 
				", staffId=" + (this.getStaff() != null ? this.getStaff().getId() : "null")  +
				"]]";
	
	}

}
