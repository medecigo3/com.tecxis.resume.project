package com.tecxis.resume;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.tecxis.resume.StaffSkill.StaffSkillId;

public class StaffSkillTest {
		

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public static StaffSkill insertAStaffSkill(Staff staff, Skill skill, EntityManager entityManager) {
		StaffSkill staffSkill = new StaffSkill(new StaffSkillId(staff, skill));
		entityManager.persist(staffSkill);
		entityManager.flush();
		return staffSkill;
		
	}
}
