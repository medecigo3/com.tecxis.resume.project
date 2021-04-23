package com.tecxis.resume.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.tecxis.resume.domain.Enrolment;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.id.EnrolmentId;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.EnrolmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class EnrolmentTest {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private EnrolmentRepository enrolmentRepo;
	
	@Autowired 
	private CourseRepository courseRepo;
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaff() {		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
		
		/**Find Course*/
		List <Course> courses = courseRepo.getCourseLikeTitle(Constants.SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(Constants.BW_6_COURSE, bwCourse.getTitle());
		
		/**Find Enrolment to update*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt, bwCourse)).get();
		assertEquals(amt, bwEnrolment.getStaff());
		assertEquals(bwCourse, bwEnrolment.getCourse());
				
		/**Find Staff to set in the new Enrolment*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		assertNotNull(john);
		assertEquals(Constants.JOHN_NAME, john.getFirstName());
		assertEquals(Constants.JOHN_LASTNAME , john.getLastName());
				
		/**Create new Enrolment*/		
		Enrolment newEnrolment = new Enrolment(john, bwCourse);
		
		/**Verify initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Remove old and create Enrolment with new Staff*/		
		entityManager.remove(bwEnrolment);
		entityManager.persist(newEnrolment);		
		entityManager.flush();
		entityManager.clear();		
		
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Find old Enrolment*/
		assertFalse(enrolmentRepo.findById(new EnrolmentId(amt, bwCourse)).isPresent());
		/**Find new Enrolment*/
		assertTrue(enrolmentRepo.findById(new EnrolmentId(john, bwCourse)).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetCourse() {		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
		
		/**Find Course*/
		List <Course> courses = courseRepo.getCourseLikeTitle(Constants.SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(Constants.BW_6_COURSE, bwCourse.getTitle());
		
		/**Find Enrolment to update*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt, bwCourse)).get();
		assertEquals(amt, bwEnrolment.getStaff());
		assertEquals(bwCourse, bwEnrolment.getCourse());
		
		/**Find Course to set in Enrolment*/
		List <Course> javaWsCourses = courseRepo.getCourseLikeTitle(Constants.JAVA_WS);
		assertEquals(1, javaWsCourses.size());
		Course javaWsCourse = javaWsCourses.get(0);
		assertEquals(Constants.JAVA_WS, javaWsCourse.getTitle());
		
		/**Create new Enrolment*/		
		Enrolment newEnrolment = new Enrolment(amt, javaWsCourse);
		
		/**Verify initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Remove old and create Enrolment with new Course*/
		entityManager.remove(bwEnrolment);
		entityManager.persist(newEnrolment);		
		entityManager.flush();
		entityManager.clear();		
		
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Find old Enrolment*/
		assertFalse(enrolmentRepo.findById(new EnrolmentId(amt, bwCourse)).isPresent());
		/**Find new Enrolment*/
		assertTrue(enrolmentRepo.findById(new EnrolmentId(amt, javaWsCourse)).isPresent());
		
	}
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEnrolment() {
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
		
		/**Find Course*/
		List <Course> courses = courseRepo.getCourseLikeTitle(Constants.SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(Constants.BW_6_COURSE, bwCourse.getTitle());
		
		/**Find enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt, bwCourse)).get();
		assertEquals(amt, bwEnrolment.getStaff());
		assertEquals(bwCourse, bwEnrolment.getCourse());
		
		
		/**Verify initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Remove enrolment*/
		entityManager.remove(bwEnrolment);
		entityManager.flush();
		
		/**Verify initial state*/
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.ENROLMENT_TABLE));
		/**Verify cascading*/
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		
	}
	
	@Test
	public void testToString() {
		Enrolment newEnrolment = new Enrolment();
		newEnrolment.toString();
		
	}
	

}
