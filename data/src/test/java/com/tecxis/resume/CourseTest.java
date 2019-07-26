package com.tecxis.resume;

import static com.tecxis.resume.persistence.CourseRepositoryTest.BW_6_COURSE;
import static com.tecxis.resume.persistence.CourseRepositoryTest.COURSE_TABLE;
import static com.tecxis.resume.persistence.CourseRepositoryTest.SHORT_BW_6_COURSE;
import static com.tecxis.resume.persistence.EnrolmentRepositoryTest.ENROLMENT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.tecxis.resume.persistence.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class CourseTest {
	
	private static Logger log = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private CourseRepository courseRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
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
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffs() {
		/**Find Course */
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
		
		/**Find staff in the course*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertEquals(AMT_NAME, amt.getName()); 
		
		List <Staff> bwCourseStaff =  bwCourse.getStaffs();
		assertEquals(1, bwCourseStaff.size());
		assertEquals(amt, bwCourseStaff.get(0));
				
	}

	@Test
	public void testSetStaffs() {
		log.info("Course -> Staff association is managed through of the relationship owner (Enrolment).");
		//To update a Staff in a Course see EnrolmentTest.testSetStaff()
	}
	
	@Test
	public void testAddStaff() {
		log.info("Course -> Stafff association is managed through of the relationship owner (Enrolment).");	
		//To add a staff in a Course see EnrolmentTest.testSetStaff()
	}
	
	@Test
	public void testRemoveStaff() {
		log.info("Course -> Stafff association is managed through of the relationship owner (Enrolment).");
		//To remove a staff from a Course see EnrolmentTest.testSetStaff()
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
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));		
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		/**Remove course*/
		entityManager.remove(bwCourse);
		entityManager.flush();
		
		/**Test course was removed*/
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
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
