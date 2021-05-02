package com.tecxis.resume.domain.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Constants;
import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class JpaCourseDaoTest {

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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));
		Course bw6 = Utils.insertACourse(Constants.BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));
		assertEquals(1, bw6.getId());
		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedCourse() {
		Course courseIn = Utils.insertACourse(Constants.BW_6_COURSE, entityManager);
		Course courseOut = courseRepo.getCourseByTitle(Constants.BW_6_COURSE);
		assertEquals(courseIn, courseOut);
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCourseLikeTitle() {
		List <Course> courses = courseRepo.getCourseLikeTitle(Constants.SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(Constants.BW_6_COURSE, bwCourse.getTitle());
		
	}
		
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCourseByTitle() {
		Course bwCourse = courseRepo.getCourseByTitle(Constants.BW_6_COURSE);
		assertNotNull(bwCourse);
		assertEquals(Constants.BW_6_COURSE, bwCourse.getTitle());
	}
	

	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteCourse() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));
		Course tempCourse = Utils.insertACourse(Constants.BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));
		courseRepo.delete(tempCourse);
		assertNull(courseRepo.getCourseByTitle(Constants.BW_6_COURSE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Course> courses = courseRepo.findAll();
		assertEquals(2, courses.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Course> pageableCourse = courseRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableCourse.getSize());
	}
}
