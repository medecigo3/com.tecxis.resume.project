package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

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
	@SequenceGenerator(name="SKILL_SKILLID_GENERATOR", sequenceName="SKILL_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SKILL_SKILLID_GENERATOR")
	@Column(name="SKILL_ID")
	private long skillId;

	private String name;

	//bi-directional many-to-one association to StaffSkill --> replaced with many-to-many association to Staff
//	@OneToMany(mappedBy="skill")
//	private List<StaffSkill> staffSkills;
	
	/**
	 * bi-directional many-to-many association to Staff
	 * Relationship owned by {@code skills} field in {@link Staff} table
	 */
	@ManyToMany(mappedBy="skills")
	private List<Staff> staffs;
	

	public Skill() {
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

//	public List<StaffSkill> getStaffSkills() {
//		return this.staffSkills;
//	}
//
//	public void setStaffSkills(List<StaffSkill> staffSkills) {
//		this.staffSkills = staffSkills;
//	}
//
//	public StaffSkill addStaffSkill(StaffSkill staffSkill) {
//		getStaffSkills().add(staffSkill);
//		staffSkill.setSkill(this);
//
//		return staffSkill;
//	}
//
//	public StaffSkill removeStaffSkill(StaffSkill staffSkill) {
//		getStaffSkills().remove(staffSkill);
//		staffSkill.setSkill(null);
//
//		return staffSkill;
//	}
	
	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}