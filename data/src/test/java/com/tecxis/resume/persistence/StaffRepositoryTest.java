package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT1;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT2;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT3;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT4;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT5;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT6;
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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.Assignment;
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
	
	@Autowired
	private AssignmentRepository assignmentRepo;

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedStaff() {
		Staff staffIn = insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		Staff staffOut = staffRepo.getStaffByName(AMT_NAME);
		assertEquals(staffIn, staffOut);		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffByName() {
		Staff amt = staffRepo.getStaffByName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffByName("Art%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffByLastname() {
		Staff amt = staffRepo.getStaffByLastname(AMT_LASTNAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffByLastname("Medecigo%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testgetStaffAssignments() {
		Staff amt = staffRepo.getStaffByName(AMT_NAME);
		assertNotNull(amt);
		List <Project> amtProjects = amt.getProjects();
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
		assertThat(amt.getProjects(), Matchers.containsInAnyOrder(adir, fortis, dcsc, ted, parcours, eolis, aos, sherpa, selenium, cdc, euroclear, morningstarV1, morningstarV2));
		List <Assignment> adirAssignments = adir.getAssignments();
		assertEquals(6, adirAssignments.size());
		Assignment assignment1 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT1);
		assertNotNull(assignment1);
		Assignment assignment2 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT2);
		assertNotNull(assignment2);
		Assignment assignment3 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT3);
		assertNotNull(assignment3);
		Assignment assignment4 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT4);
		assertNotNull(assignment4);
		Assignment assignment5 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT5);
		assertNotNull(assignment5);
		Assignment assignment6 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT6);
		assertNotNull(assignment6);
		assertThat(adirAssignments, Matchers.containsInAnyOrder(assignment1, assignment2, assignment3, assignment4, assignment5, assignment6));
		
	
	}
	
	@Test
	public void testGetStaffProjects() {
		fail("TODO");
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff tempStaff = insertAStaff(AMT_LASTNAME, AMT_LASTNAME, staffRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		staffRepo.delete(tempStaff);
		assertNull(staffRepo.getStaffByName(AMT_NAME));
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}

	
	public static Staff insertAStaff(String firstName, String lastName, StaffRepository staffRepo, EntityManager entityManager) {
		Staff staff = new Staff();
		staff.setName(firstName);
		staff.setLastname(lastName);
		assertEquals(0, staff.getStaffId());
		staffRepo.save(staff);
		assertNotNull(staff.getStaffId());
		entityManager.flush();
		return staff;
		
	}

}
