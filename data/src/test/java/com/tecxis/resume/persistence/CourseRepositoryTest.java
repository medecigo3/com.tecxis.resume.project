package com.tecxis.resume.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.Course;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CourseRepositoryTest {

	public static String COURSE_TABLE = "COURSE";
	public static String BW_6_COURSE = "BW618: TIBCO ActiveMatrix BusinessWorks™ 6.x Developer Boot Camp";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private CourseRepository courseRepo;
	
	@Sql(
			scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
	@Test
	public void testCreateAndInsertIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		Course bw6 = insertACourse(BW_6_COURSE, 1, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(1, bw6.getCourseId());
		
	}
	
	private Course insertACourse(String title, int credits, EntityManager entityManager) {
		Course course = new Course();
		course.setTitle(title);
		course.setCredits(credits);
		entityManager.persist(course);
		entityManager.flush();
		assertThat(course.getCourseId(), Matchers.greaterThan((long)0));
		return course;
	}

	@Test
	public void shouldBeAbleToFindInsertedCourse() {
		fail("TODO");
	}
	
	@Test
	public void shouldBeAbleToFindCourseByTitle() {
		fail("TODO");
	}
	
	@Test
	public void testGetCourseStaff() {
		fail("TODO");
	}
	
	@Test
	public void testDeleteCourse() {
		fail("TODO");
	}
	
	
}
