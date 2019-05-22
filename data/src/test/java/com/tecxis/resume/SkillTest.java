package com.tecxis.resume;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class SkillTest {

	@Test
	public void testGetSkillId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStaffs() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStaffs() {
		fail("Not yet implemented");
	}

	public static Skill insertASkill(String name, EntityManager entityManager) {
		Skill skill = new Skill();
		skill.setName(name);
		assertEquals(0, skill.getId());
		entityManager.persist(skill);		
		entityManager.flush();
		assertThat(skill.getId(), Matchers.greaterThan((long)0));
		return skill;
	}

}
