package com.tecxis.resume.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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

import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml",
		"classpath:validation-api-context.xml"} )
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CourseTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private CourseRepository courseRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Course course = CourseTest.insertACourse(Constants.BW_6_COURSE, entityManager);
		assertThat(course.getId(), Matchers.greaterThan((long)0));		
	}
	
	@Test
	public void testSetId() {
		Course course = new Course();
		assertEquals(0, course.getId());
		course.setId(1);
		assertEquals(1, course.getId());		
	}

	@Test
	public void testGetCredits() {
		Course course = new Course();
		course.setCredits(1);
		assertEquals(new Integer(1), course.getCredits());
		
	}
	
	@Test
	public void testSetCredits() {
		Course course = new Course();
		assertNull(course.getCredits());
		Integer credits = new Integer(1);
		course.setCredits(credits);
		assertEquals(credits, course.getCredits());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetTitle() {
		Course course = courseRepo.getCourseByTitle(Constants.BW_6_COURSE);
		assertNotNull(course);		
		assertEquals(Constants.BW_6_COURSE, course.getTitle());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetTitle() {
		Course course = new Course();
		assertNull(course.getTitle());
		course.setTitle(Constants.BW_6_COURSE);
		assertEquals(Constants.BW_6_COURSE,course.getTitle());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffs() {
		/**Find Course */
		List <Course> courses = courseRepo.getCourseLikeTitle(Constants.SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(Constants.BW_6_COURSE, bwCourse.getTitle());
		
		/**Find staff in the course*/
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName()); 
		
		List <Staff> bwCourseStaff =  bwCourse.getStaffs();
		assertEquals(1, bwCourseStaff.size());
		assertEquals(amt, bwCourseStaff.get(0));
				
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetStaffs() {
		Course bwCourse = courseRepo.getCourseByTitle(Constants.BW_6_COURSE);
		bwCourse.setStaffs(new ArrayList<Staff>());
		//To update a Staff in a Course see EnrolmentTest.testSetStaff()
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddStaff() {
		Course bwCourse = courseRepo.getCourseByTitle(Constants.BW_6_COURSE);
		bwCourse.addStaff(new Staff());
		//To add a staff in a Course see EnrolmentTest.testSetStaff()
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveStaff() {
		Course bwCourse = courseRepo.getCourseByTitle(Constants.BW_6_COURSE);
		bwCourse.removeStaff(new Staff());
		//To remove a staff from a Course see EnrolmentTest.testSetStaff()
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveCourse() {
		
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(Constants.SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(Constants.BW_6_COURSE, bwCourse.getTitle());
		
		/**Test initial state*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));		
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Remove course*/
		entityManager.remove(bwCourse);
		entityManager.flush();
		
		/**Test course was removed*/
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));
		/**Test cascadings*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
	}
	
	@Test
	public void testTitleIsNotNull() {
		Course course = new Course();
		Set<ConstraintViolation<Course>> violations = validator.validate(course);
        assertFalse(violations.isEmpty());
	}
	
	@Test
	public void testToString() {
		Course course = new Course();
		course.toString();
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