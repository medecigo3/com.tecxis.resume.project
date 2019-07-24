package com.tecxis.resume;

import static com.tecxis.resume.persistence.CourseRepositoryTest.BW_6_COURSE;
import static com.tecxis.resume.persistence.CourseRepositoryTest.COURSE_TABLE;
import static com.tecxis.resume.persistence.CourseRepositoryTest.SHORT_BW_6_COURSE;
import static com.tecxis.resume.persistence.EnrolmentRepositoryTest.ENROLMENT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

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

import com.tecxis.resume.persistence.CourseRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CourseTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private CourseRepository courseRepo;
	

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

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveCourse() {
		
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
		
		/**Test initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));		
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		/**Remove course*/
		entityManager.remove(bwCourse);
		entityManager.flush();
		
		/**Test course was removed*/
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**Test cascadings*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}
	public static Course insertACourse(String title,  EntityManager entityManager) {
		Course course = new Course();
		course.setTitle(title);
		entityManager.persist(course);
		entityManager.flush();
		assertThat(course.getId(), Matchers.greaterThan((long)0));
		return course;
	}

}
