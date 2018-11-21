package com.tecxis.resume;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.Test;

public class CourseTest {

	@Test
	public void testGetCourseId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCredits() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTitle() {
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

	public static Course insertACourse(String title,  EntityManager entityManager) {
		Course course = new Course();
		course.setTitle(title);
		entityManager.persist(course);
		entityManager.flush();
		assertThat(course.getCourseId(), Matchers.greaterThan((long)0));
		return course;
	}

}
