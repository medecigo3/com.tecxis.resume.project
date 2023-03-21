package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class StaffSkillId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long skillId; //corresponds to PK type of Skill 
	

	private long staffId; //Corresponds to PK type of Staff

	public StaffSkillId() {
	}
	
	public StaffSkillId(long staffId, long skillId) {
		this();
		this.skillId = skillId;
		this.staffId = staffId;
	}

	public long getSkillId() {
		return skillId;
	}

	public void setSkillId(long skillId) {
		this.skillId = skillId;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (skillId ^ (skillId >>> 32));
		result = prime * result + (int) (staffId ^ (staffId >>> 32));
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
		StaffSkillId other = (StaffSkillId) obj;
		if (skillId != other.skillId)
			return false;
		if (staffId != other.staffId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return 	this.getClass().getName() + "@" + this.hashCode() + 
				"[skillId=" + this.getSkillId()  + 
				", staffId=" + this.getStaffId() +
				"]";
	
	}

}
