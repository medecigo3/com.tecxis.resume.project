package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BW_6_COURSE;
import static com.tecxis.resume.domain.Constants.JAVA_WS;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.SHORT_BW_6_COURSE;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.EnrolmentId;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.EnrolmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaff() {		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
		
		/**Find Course*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
		
		/**Find Enrolment to update*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		assertEquals(amt, bwEnrolment.getStaff());
		assertEquals(bwCourse, bwEnrolment.getCourse());
				
		/**Find Staff to set in the new Enrolment*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertNotNull(john);
		assertEquals(JOHN_NAME, john.getFirstName());
		assertEquals(JOHN_LASTNAME , john.getLastName());
				
		/**Create new Enrolment*/		
		Enrolment newEnrolment = new Enrolment(john, bwCourse);
		
		/**Verify initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
		/**Remove old and create Enrolment with new Staff*/		
		entityManager.remove(bwEnrolment);
		entityManager.persist(newEnrolment);		
		entityManager.flush();
		entityManager.clear();		
		
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
		/**Find old Enrolment*/
		assertFalse(enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).isPresent());
		/**Find new Enrolment*/
		assertTrue(enrolmentRepo.findById(new EnrolmentId(john.getId(), bwCourse.getId())).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetCourse() {		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
		
		/**Find Course*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
		
		/**Find Enrolment to update*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		assertEquals(amt, bwEnrolment.getStaff());
		assertEquals(bwCourse, bwEnrolment.getCourse());
		
		/**Find Course to set in Enrolment*/
		List <Course> javaWsCourses = courseRepo.getCourseLikeTitle(JAVA_WS);
		assertEquals(1, javaWsCourses.size());
		Course javaWsCourse = javaWsCourses.get(0);
		assertEquals(JAVA_WS, javaWsCourse.getTitle());
		
		/**Create new Enrolment*/		
		Enrolment newEnrolment = new Enrolment(amt, javaWsCourse);
		
		/**Verify initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
		/**Remove old and create Enrolment with new Course*/
		entityManager.remove(bwEnrolment);
		entityManager.persist(newEnrolment);		
		entityManager.flush();
		entityManager.clear();		
		
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
		/**Find old Enrolment*/
		assertFalse(enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).isPresent());
		/**Find new Enrolment*/
		assertTrue(enrolmentRepo.findById(new EnrolmentId(amt.getId(), javaWsCourse.getId())).isPresent());
		
	}
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEnrolment() {
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
		
		/**Find Course*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
		
		/**Find enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		assertEquals(amt, bwEnrolment.getStaff());
		assertEquals(bwCourse, bwEnrolment.getCourse());
		
		
		/**Verify initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
		/**Remove enrolment*/
		entityManager.remove(bwEnrolment);
		entityManager.flush();
		
		/**Verify initial state*/
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		/**Verify cascading*/
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
		
	}

	@Test
	public void testToString() {
		Enrolment newEnrolment = new Enrolment();
		assertThat(newEnrolment.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
		
	}
	

}
