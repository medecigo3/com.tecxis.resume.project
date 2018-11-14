package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.CourseRepositoryTest.BW_6_COURSE;
import static com.tecxis.resume.persistence.CourseRepositoryTest.COURSE_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.AOS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.CENTRE_DES_COMPETENCES;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.DCSC;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EOLIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EUROCLEAR_VERS_CALYPSO;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.FORTIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PARCOURS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SELENIUM;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.TED;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static com.tecxis.resume.StaffTest.insertAStaff;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
import com.tecxis.resume.Project;
import com.tecxis.resume.Staff;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class StaffRepositoryTest {
	
	public static final String ENROLMENT_TABLE = "ENROLMENT";
	public static final String STAFF_TABLE = "STAFF";
	public static final String AMT_NAME = "Arturo";
	public static final String AMT_LASTNAME = "Medecigo Tress";
	public static final String JHON_NAME = "Jhon";
	public static final String JHON_LASTNAME = "Smith";
	public static final Date BIRTHDATE = new GregorianCalendar(1982, 10, 06).getTime();
		
			
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedStaff() {
		Staff staffIn = insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		Staff staffOut = staffRepo.getStaffLikeName(AMT_NAME);
		assertEquals(staffIn, staffOut);		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffLikeName() {
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffLikeName("Art%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffLikeLastname() {
		Staff amt = staffRepo.getStaffLikeLastname(AMT_LASTNAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffLikeLastname("Medecigo%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjects() {
		/**Test staff 1*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		List <Project> amtProjects = staffRepo.getStaffProjects(amt);
		assertNotNull(amtProjects);
		assertEquals(13, amtProjects.size());
		Project adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertNotNull(adir);
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertNotNull(fortis);
		Project dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);
		assertNotNull(dcsc);
		Project ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		assertNotNull(ted);
		Project parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		assertNotNull(parcours);
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertNotNull(eolis);
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		assertNotNull(aos);
		Project sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		assertNotNull(sherpa);
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertNotNull(selenium);
		Project cdc = projectRepo.findByNameAndVersion(CENTRE_DES_COMPETENCES, VERSION_1);
		assertNotNull(cdc);
		Project euroclear = projectRepo.findByNameAndVersion(EUROCLEAR_VERS_CALYPSO, VERSION_1);
		assertNotNull(euroclear);
		Project morningstarV1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(morningstarV1);
		Project morningstarV2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertNotNull(morningstarV2);	
		assertThat(amtProjects, Matchers.containsInAnyOrder(adir, fortis, dcsc, ted, parcours, eolis, aos, sherpa, selenium, cdc, euroclear, morningstarV1, morningstarV2));
		
		
		/**Test staff 2*/
		Staff jhonStaff = staffRepo.getStaffLikeName(JHON_NAME);
		assertNotNull(jhonStaff);
		List <Project> jhonProjects = staffRepo.getStaffProjects(jhonStaff);
		assertNotNull(jhonProjects);
		assertEquals(1, jhonProjects.size());
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffCourses() {
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));		
    	
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		
		List <Course> courseList = amt.getCourses();
		assertNotNull(courseList);
		assertEquals(1, courseList.size());
		assertEquals(BW_6_COURSE, courseList.get(0).getTitle());
		
				
		/**SQL test*/		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"select c.course_id, c.title, c.credits from Staff s "+
						" JOIN Enrolment e on s.staff_Id = e.staff_Id " +
						" JOIN Course c on c.course_Id = e.course_Id where s.staff_id = ?", new Object[] { amt.getStaffId() } );
		
		assertEquals(1, rows.size());
		
		courseList = null;
		courseList = new ArrayList<>();
		for (Map<String, Object> row : rows) {
			Course tempCourse = new Course();
			tempCourse.setCourseId(Long.parseLong(String.valueOf(row.get("COURSE_ID"))));
			tempCourse.setTitle(row.get("TITLE") != null ?  String.valueOf(row.get("TITLE")) : null );
			tempCourse.setCredits(row.get("credits") != null ? Integer.parseInt(String.valueOf(row.get("credits"))) : null );            
			courseList.add(tempCourse);
		}
		assertNotNull(courseList.get(0));
		assertEquals(BW_6_COURSE, courseList.get(0).getTitle());
		
		/**JPQL test*/	
//		TypedQuery<Staff> staffQuery = entityManager.createQuery("select s from Staff s", Staff.class);
//		List<Staff> staffs = staffQuery.getResultList();
//		Iterator <Staff> staffIt = staffs.iterator();
		TypedQuery<Course> query = entityManager.createQuery
				("select course from Staff staff  JOIN  staff.courses course where staff = :staff", Course.class);
		query.setParameter("staff", amt);
		courseList = null;
		courseList = query.getResultList();
		assertEquals(1, courseList.size());	
		assertNotNull(courseList.get(0));
		assertEquals(BW_6_COURSE, courseList.get(0).getTitle());
	
		
		/**Criteria API test*/
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Staff> q = cb.createQuery(Staff.class);
//		Root<Staff> s = q.from(Staff.class);		
//		s.fetch(Staff_.courses);				 
//		q.select(s);
//		TypedQuery<Staff> staffQuery = entityManager.createQuery(q);
//		List<Staff> staffs = staffQuery.getResultList();



	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff tempStaff = insertAStaff(AMT_LASTNAME, AMT_LASTNAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		staffRepo.delete(tempStaff);
		assertNull(staffRepo.getStaffLikeName(AMT_NAME));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		fail("TODO");
	}

}
