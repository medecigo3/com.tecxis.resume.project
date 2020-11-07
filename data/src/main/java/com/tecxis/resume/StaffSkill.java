package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.StaffSkillId;

/**
 * The persistent class for the STAFF_SKILL database table.
 * 
 */
@Entity
@Table(name="STAFF_SKILL")
public class StaffSkill implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private StaffSkillId staffSkillId;

	public StaffSkill() {
		super();
	}

	public StaffSkill(StaffSkillId staffSkillId) {
		super();
		this.staffSkillId = staffSkillId;
	}

	public StaffSkillId getStaffSkillId() {
		return staffSkillId;
	}

	public void setStaffSkillId(StaffSkillId staffSkillId) {
		this.staffSkillId = staffSkillId;
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
		
		if (this.getStaffSkillId().getSkill() != null && castOther.getStaffSkillId().getSkill() != null) {
			if (this.getStaffSkillId().getStaff() != null && castOther.getStaffSkillId().getStaff() != null) {
				
				return 	this.getStaffSkillId().getSkill().equals(castOther.getStaffSkillId().getSkill()) &&
						this.getStaffSkillId().getStaff().equals(castOther.getStaffSkillId().getStaff());
			} else return false;
		} else return false;
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		StaffSkillId staffSkillId = this.getStaffSkillId();
		if (staffSkillId != null) {
			if (staffSkillId.getSkill() != null)
				hash = hash * prime + this.getStaffSkillId().getSkill().hashCode();
			
			if (staffSkillId.getStaff() != null)
				hash = hash * prime + this.getStaffSkillId().getStaff().hashCode();
		}
		return hash;
	}
	
	@Override
	public String toString() {
		StaffSkillId staffSkillId = this.getStaffSkillId();
		Skill skill = null;
		Staff staff = null;
		
		if (staffSkillId != null) {
			
			skill = staffSkillId.getSkill();
			staff = staffSkillId.getStaff();
		
			return "["+ this.getClass().getName() +
					"[skillId=" + (skill != null ?  skill.getId() : "null") + 
					", staffId=" + (staff != null ? staff.getId() : "null") +
					"]]";
		}
		else {
			return "["+ this.getClass().getName() +
					"[skillId= null" + 
					", staffId= null"  +
					"]]";
		}
	
	}
	
}
