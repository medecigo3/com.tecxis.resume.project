package com.tecxis.resume;

import static com.tecxis.resume.persistence.CourseRepositoryTest.BW_6_COURSE;
import static com.tecxis.resume.persistence.CourseRepositoryTest.COURSE_TABLE;
import static com.tecxis.resume.persistence.CourseRepositoryTest.SHORT_BW_6_COURSE;
import static com.tecxis.resume.persistence.EnrolmentRepositoryTest.ENROLMENT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
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

import com.tecxis.resume.Enrolment.EnrolmentId;
import com.tecxis.resume.persistence.CourseRepository;
import com.tecxis.resume.persistence.EnrolmentRepository;
import com.tecxis.resume.persistence.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEnrolment() {
		/**Find Staff*/
		Staff amt = staffRepo.getStaffLikeLastname(AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
		
		/**Find Course*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
		
		/**Find enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt, bwCourse)).get();
		assertEquals(amt, bwEnrolment.getEnrolmentId().getStaff());
		assertEquals(bwCourse, bwEnrolment.getEnrolmentId().getCourse());
		
		
		/**Verify initial state*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		/**Remove enrolment*/
		entityManager.remove(bwEnrolment);
		entityManager.flush();
		
		/**Verify initial state*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		/**Verify cascading*/
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		
	}

}
