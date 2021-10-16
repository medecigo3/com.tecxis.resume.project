package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.BW_6_COURSE;
import static com.tecxis.resume.domain.Constants.SHORT_BW_6_COURSE;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaCourseDaoTest {

	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired 
	private CourseRepository courseRepo;
	
	@Sql(
			scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
			)
	@Test
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		Course bw6 = Utils.insertCourse(BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		assertEquals(1, bw6.getId().longValue());
		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testAdd() {
		Course courseIn = Utils.insertCourse(BW_6_COURSE, entityManager);
		Course courseOut = courseRepo.getCourseByTitle(BW_6_COURSE);
		assertEquals(courseIn, courseOut);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		Course tempCourse = Utils.insertCourse(BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		courseRepo.delete(tempCourse);
		assertNull(courseRepo.getCourseByTitle(BW_6_COURSE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Course> courses = courseRepo.findAll();
		assertEquals(2, courses.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Course> pageableCourse = courseRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableCourse.getSize());
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCourseLikeTitle() {
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		assertEquals(1, courses.size());
		Course bwCourse = courses.get(0);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
		
	}
		
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetCourseByTitle() {
		Course bwCourse = courseRepo.getCourseByTitle(BW_6_COURSE);
		assertNotNull(bwCourse);
		assertEquals(BW_6_COURSE, bwCourse.getTitle());
	}
	

}
