package com.tecxis.resume.ver2;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SKILL database table.
 * 
 */
@Entity
@NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s")
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SKILL_SKILLID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SKILL_SKILLID_GENERATOR")
	@Column(name="SKILL_ID")
	private String skillId;

	private String name;

	//bi-directional many-to-one association to StaffSkill
	@OneToMany(mappedBy="skill")
	private List<StaffSkill> staffSkills;

	public Skill() {
	}

	public String getSkillId() {
		return this.skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StaffSkill> getStaffSkills() {
		return this.staffSkills;
	}

	public void setStaffSkills(List<StaffSkill> staffSkills) {
		this.staffSkills = staffSkills;
	}

	public StaffSkill addStaffSkill(StaffSkill staffSkill) {
		getStaffSkills().add(staffSkill);
		staffSkill.setSkill(this);

		return staffSkill;
	}

	public StaffSkill removeStaffSkill(StaffSkill staffSkill) {
		getStaffSkills().remove(staffSkill);
		staffSkill.setSkill(null);

		return staffSkill;
	}

}