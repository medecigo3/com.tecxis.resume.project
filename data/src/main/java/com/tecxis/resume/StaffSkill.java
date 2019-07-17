package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the STAFF_SKILL database table.
 * 
 */
@Entity
@Table(name="STAFF_SKILL")
public class StaffSkill implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static class StaffSkillId implements Serializable{
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
			return
				(this.getSkill().getId() == castOther.getSkill().getId())
				&& (this.getStaff().getId() == castOther.getStaff().getId());
				
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.getSkill().getId() ^ (this.getSkill().getId() >>> 32)));
			hash = hash * prime + ((int) (this.getStaff().getId()  ^ (this.getStaff().getId() >>> 32)));
			return hash;
		}
		
		@Override
		public String toString() {
			return "["+ this.getClass().getName() +
					"[skillId=" + this.getSkill().getId() + 
					", staffId=" + this.getStaff().getId()  +
					"]]";
		
		}
	}
	
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
		if (!(other instanceof StaffSkillId)) {
			return false;
		}
		StaffSkill castOther = (StaffSkill)other;
		return
			(this.getStaffSkillId().getSkill().getId() == castOther.getStaffSkillId().getSkill().getId())
			&& (this.getStaffSkillId().getStaff().getId() == castOther.getStaffSkillId().getStaff().getId());
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getStaffSkillId().getSkill().getId() ^ (this.getStaffSkillId().getSkill().getId() >>> 32)));
		hash = hash * prime + ((int) (this.getStaffSkillId().getStaff().getId()  ^ (this.getStaffSkillId().getStaff().getId() >>> 32)));
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[skillId=" + this.getStaffSkillId().getSkill().getId() + 
				", staffId=" + this.getStaffSkillId().getStaff().getId()  +
				"]]";
	
	}
	
}
