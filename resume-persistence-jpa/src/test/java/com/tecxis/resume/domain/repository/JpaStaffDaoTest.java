package com.tecxis.resume.domain.repository;

import static com.tecxis.resume.domain.Constants.ENROLMENT_TABLE;
import static com.tecxis.resume.domain.StaffTest.insertAStaff;
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

import com.tecxis.resume.domain.Constants;
import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;


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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)public void testGetStaffByFirstNameAndLastName() {
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME, amt.getLastName());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff amt = insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, amt.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedStaff() {
		Staff staffIn = insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		Staff staffOut = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assertEquals(staffIn, staffOut);		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffLikeFirstName() {
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assertNotNull(amt);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffLikeFirstName("Art%");
		assertNotNull(amt);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffLikeLastName() {
		Staff amt = staffRepo.getStaffLikeLastName(Constants.AMT_LASTNAME);
		assertNotNull(amt);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffLikeLastName("Medecigo%");
		assertNotNull(amt);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME , amt.getLastName());
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjects() {
		/**Test staff 1*/
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assertNotNull(amt);
		List <Project> amtProjects = staffRepo.getStaffProjects(amt);
		assertNotNull(amtProjects);
		assertEquals(13, amtProjects.size());
		Project adir = projectRepo.findByNameAndVersion(Constants.ADIR, Constants.VERSION_1);
		assertNotNull(adir);
		Project fortis = projectRepo.findByNameAndVersion(Constants.FORTIS, Constants.VERSION_1);
		assertNotNull(fortis);
		Project dcsc = projectRepo.findByNameAndVersion(Constants.DCSC, Constants.VERSION_1);
		assertNotNull(dcsc);
		Project ted = projectRepo.findByNameAndVersion(Constants.TED, Constants.VERSION_1);
		assertNotNull(ted);
		Project parcours = projectRepo.findByNameAndVersion(Constants.PARCOURS, Constants.VERSION_1);
		assertNotNull(parcours);
		Project eolis = projectRepo.findByNameAndVersion(Constants.EOLIS, Constants.VERSION_1);
		assertNotNull(eolis);
		Project aos = projectRepo.findByNameAndVersion(Constants.AOS, Constants.VERSION_1);
		assertNotNull(aos);
		Project sherpa = projectRepo.findByNameAndVersion(Constants.SHERPA, Constants.VERSION_1);
		assertNotNull(sherpa);
		Project selenium = projectRepo.findByNameAndVersion(Constants.SELENIUM, Constants.VERSION_1);
		assertNotNull(selenium);
		Project cdc = projectRepo.findByNameAndVersion(Constants.CENTRE_DES_COMPETENCES, Constants.VERSION_1);
		assertNotNull(cdc);
		Project euroclear = projectRepo.findByNameAndVersion(Constants.EUROCLEAR_VERS_CALYPSO, Constants.VERSION_1);
		assertNotNull(euroclear);
		Project morningstarV1 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_1);
		assertNotNull(morningstarV1);
		Project morningstarV2 = projectRepo.findByNameAndVersion(Constants.MORNINGSTAR, Constants.VERSION_2);
		assertNotNull(morningstarV2);	
		assertThat(amtProjects, Matchers.containsInAnyOrder(adir, fortis, dcsc, ted, parcours, eolis, aos, sherpa, selenium, cdc, euroclear, morningstarV1, morningstarV2));
		
		
		/**Test staff 2*/
		Staff jhonStaff = staffRepo.getStaffLikeFirstName(Constants.JOHN_NAME);
		assertNotNull(jhonStaff);
		List <Project> jhonProjects = staffRepo.getStaffProjects(jhonStaff);
		assertNotNull(jhonProjects);
		assertEquals(1, jhonProjects.size());
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffCourses() {
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));		
    	
		Staff amt = staffRepo.getStaffLikeFirstName(Constants.AMT_NAME);
		assertNotNull(amt);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		
		List <Course> courseList = amt.getCourses();
		assertNotNull(courseList);
		assertEquals(1, courseList.size());
		assertEquals(Constants.BW_6_COURSE, courseList.get(0).getTitle());
		
				
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
		assertEquals(Constants.BW_6_COURSE, courseList.get(0).getTitle());
		
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
		assertEquals(Constants.BW_6_COURSE, courseList.get(0).getTitle());
	
		
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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		Staff tempStaff = insertAStaff(Constants.AMT_LASTNAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		staffRepo.delete(tempStaff);
		assertNull(staffRepo.getStaffLikeFirstName(Constants.AMT_NAME));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Staff> staffs = staffRepo.findAll();
		assertEquals(2, staffs.size());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Staff> pageableStaff = staffRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableStaff.getSize());
	}
}
