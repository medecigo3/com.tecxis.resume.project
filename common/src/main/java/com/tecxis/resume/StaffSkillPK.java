//package com.tecxis.resume;
//
//import java.io.Serializable;
//import javax.persistence.*;
//
///**
// * The primary key class for the STAFF_SKILL database table.
// * 
// */
//@Embeddable
//public class StaffSkillPK implements Serializable {
//	//default serial version id, required for serializable classes.
//	private static final long serialVersionUID = 1L;
//
//	@Column(name="STAFF_ID", insertable=false, updatable=false)
//	private long staffId;
//
//	@Column(name="SKILL_ID", insertable=false, updatable=false)
//	private String skillId;
//
//	public StaffSkillPK() {
//	}
//	public long getStaffId() {
//		return this.staffId;
//	}
//	public void setStaffId(long staffId) {
//		this.staffId = staffId;
//	}
//	public String getSkillId() {
//		return this.skillId;
//	}
//	public void setSkillId(String skillId) {
//		this.skillId = skillId;
//	}
//
//	public boolean equals(Object other) {
//		if (this == other) {
//			return true;
//		}
//		if (!(other instanceof StaffSkillPK)) {
//			return false;
//		}
//		StaffSkillPK castOther = (StaffSkillPK)other;
//		return 
//			(this.staffId == castOther.staffId)
//			&& this.skillId.equals(castOther.skillId);
//	}
//
//	public int hashCode() {
//		final int prime = 31;
//		int hash = 17;
//		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
//		hash = hash * prime + this.skillId.hashCode();
//		
//		return hash;
//	}
//}