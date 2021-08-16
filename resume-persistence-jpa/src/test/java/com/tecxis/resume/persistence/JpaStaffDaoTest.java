package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.AOS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.BW_6_COURSE;
import static com.tecxis.resume.domain.Constants.CENTRE_DES_COMPETENCES;
import static com.tecxis.resume.domain.Constants.DCSC;
import static com.tecxis.resume.domain.Constants.EOLIS;
import static com.tecxis.resume.domain.Constants.EUROCLEAR_VERS_CALYPSO;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.SELENIUM;
import static com.tecxis.resume.domain.Constants.SHERPA;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Constants.VERSION_2;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.Course.COURSE_TABLE;
import static com.tecxis.resume.domain.Enrolment.ENROLMENT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hamcrest.Matchers;
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

import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class JpaStaffDaoTest {
	
	
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)public void testGetStaffByFirstNameAndLastName() {
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedStaff() {
		Staff staffIn = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Staff staffOut = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertEquals(staffIn, staffOut);		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffLikeFirstName() {
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffLikeFirstName("Art%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffLikeLastName() {
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffLikeLastName("Medecigo%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME , amt.getLastName());
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjects() {
		/**Test staff 1*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
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
		Staff jhonStaff = staffRepo.getStaffLikeFirstName(JOHN_NAME);
		assertNotNull(jhonStaff);
		List <Project> jhonProjects = staffRepo.getStaffProjects(jhonStaff);
		assertNotNull(jhonProjects);
		assertEquals(1, jhonProjects.size());
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffCourses() {
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));		
    	
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getFirstName());
		
		List <Course> courseList = amt.getCourses();
		assertNotNull(courseList);
		assertEquals(1, courseList.size());
		assertEquals(BW_6_COURSE, courseList.get(0).getTitle());
		
				
		/**SQL test*/		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"select c.course_id, c.title, c.credits from Staff s "+
						" JOIN Enrolment e on s.staff_Id = e.staff_Id " +
						" JOIN Course c on c.course_Id = e.course_Id where s.staff_id = ?", new Object[] { amt.getId() } );
		
		assertEquals(1, rows.size());
		
		courseList = null;
		courseList = new ArrayList<>();
		for (Map<String, Object> row : rows) {
			Course tempCourse = new Course();
			tempCourse.setId(Long.parseLong(String.valueOf(row.get("COURSE_ID"))));
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
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDeleteStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff tempStaff = Utils.insertAStaff(AMT_LASTNAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		staffRepo.delete(tempStaff);
		assertNull(staffRepo.getStaffLikeFirstName(AMT_NAME));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Staff> staffs = staffRepo.findAll();
		assertEquals(2, staffs.size());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Staff> pageableStaff = staffRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableStaff.getSize());
	}
}
