//package com.tecxis.resume;
//
//import java.io.Serializable;
//import javax.persistence.*;
//import java.math.BigDecimal;
//
//
///**
// * The persistent class for the STAFF_SKILL database table.
// * 
// */
//@Entity
//@Table(name="STAFF_SKILL")
//public class StaffSkill implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@EmbeddedId
//	private StaffSkillPK id;
//
//	private BigDecimal endorsement;
//
//	@Column(name="YEARS_OF_EXPERIENCE")
//	private BigDecimal yearsOfExperience;
//
//	//bi-directional many-to-one association to Skill
//	@ManyToOne
//	@JoinColumn(name="SKILL_ID")
//	private Skill skill;
//
//	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name="STAFF_ID")
//	private Staff staff;
//
//	public StaffSkill() {
//	}
//
//	public StaffSkillPK getId() {
//		return this.id;
//	}
//
//	public void setId(StaffSkillPK id) {
//		this.id = id;
//	}
//
//	public BigDecimal getEndorsement() {
//		return this.endorsement;
//	}
//
//	public void setEndorsement(BigDecimal endorsement) {
//		this.endorsement = endorsement;
//	}
//
//	public BigDecimal getYearsOfExperience() {
//		return this.yearsOfExperience;
//	}
//
//	public void setYearsOfExperience(BigDecimal yearsOfExperience) {
//		this.yearsOfExperience = yearsOfExperience;
//	}
//
//	public Skill getSkill() {
//		return this.skill;
//	}
//
//	public void setSkill(Skill skill) {
//		this.skill = skill;
//	}
//
//	public Staff getStaff() {
//		return this.staff;
//	}
//
//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}
//
//}