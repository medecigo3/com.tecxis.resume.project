package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Task.TASK_TABLE;
import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMESYS;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.AOS;
import static com.tecxis.resume.domain.Constants.TASK1;
import static com.tecxis.resume.domain.Constants.TASK10;
import static com.tecxis.resume.domain.Constants.TASK11;
import static com.tecxis.resume.domain.Constants.TASK12;
import static com.tecxis.resume.domain.Constants.TASK13;
import static com.tecxis.resume.domain.Constants.TASK14;
import static com.tecxis.resume.domain.Constants.TASK15;
import static com.tecxis.resume.domain.Constants.TASK16;
import static com.tecxis.resume.domain.Constants.TASK17;
import static com.tecxis.resume.domain.Constants.TASK18;
import static com.tecxis.resume.domain.Constants.TASK19;
import static com.tecxis.resume.domain.Constants.TASK2;
import static com.tecxis.resume.domain.Constants.TASK20;
import static com.tecxis.resume.domain.Constants.TASK21;
import static com.tecxis.resume.domain.Constants.TASK22;
import static com.tecxis.resume.domain.Constants.TASK23;
import static com.tecxis.resume.domain.Constants.TASK24;
import static com.tecxis.resume.domain.Constants.TASK25;
import static com.tecxis.resume.domain.Constants.TASK26;
import static com.tecxis.resume.domain.Constants.TASK27;
import static com.tecxis.resume.domain.Constants.TASK28;
import static com.tecxis.resume.domain.Constants.TASK29;
import static com.tecxis.resume.domain.Constants.TASK3;
import static com.tecxis.resume.domain.Constants.TASK30;
import static com.tecxis.resume.domain.Constants.TASK31;
import static com.tecxis.resume.domain.Constants.TASK32;
import static com.tecxis.resume.domain.Constants.TASK33;
import static com.tecxis.resume.domain.Constants.TASK34;
import static com.tecxis.resume.domain.Constants.TASK37;
import static com.tecxis.resume.domain.Constants.TASK39;
import static com.tecxis.resume.domain.Constants.TASK4;
import static com.tecxis.resume.domain.Constants.TASK40;
import static com.tecxis.resume.domain.Constants.TASK41;
import static com.tecxis.resume.domain.Constants.TASK42;
import static com.tecxis.resume.domain.Constants.TASK43;
import static com.tecxis.resume.domain.Constants.TASK44;
import static com.tecxis.resume.domain.Constants.TASK45;
import static com.tecxis.resume.domain.Constants.TASK46;
import static com.tecxis.resume.domain.Constants.TASK47;
import static com.tecxis.resume.domain.Constants.TASK48;
import static com.tecxis.resume.domain.Constants.TASK49;
import static com.tecxis.resume.domain.Constants.TASK5;
import static com.tecxis.resume.domain.Constants.TASK50;
import static com.tecxis.resume.domain.Constants.TASK51;
import static com.tecxis.resume.domain.Constants.TASK52;
import static com.tecxis.resume.domain.Constants.TASK53;
import static com.tecxis.resume.domain.Constants.TASK54;
import static com.tecxis.resume.domain.Constants.TASK55;
import static com.tecxis.resume.domain.Constants.TASK56;
import static com.tecxis.resume.domain.Constants.TASK57;
import static com.tecxis.resume.domain.Constants.TASK6;
import static com.tecxis.resume.domain.Constants.TASK7;
import static com.tecxis.resume.domain.Constants.TASK8;
import static com.tecxis.resume.domain.Constants.TASK9;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.CENTRE_DES_COMPETENCES;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.DCSC;
import static com.tecxis.resume.domain.Constants.EOLIS;
import static com.tecxis.resume.domain.Constants.EUROCLEAR_VERS_CALYPSO;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.JOHN_INTEREST;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.SELENIUM;
import static com.tecxis.resume.domain.Constants.SHERPA;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Constants.VERSION_2;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.Course.COURSE_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.Enrolment.ENROLMENT_TABLE;
import static com.tecxis.resume.domain.Interest.INTEREST_TABLE;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hamcrest.Matchers;
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

import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.util.Utils;
import com.tecxis.resume.domain.util.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
public class StaffTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private InterestRepository interestRepo;
	
	@Autowired
	private ClientRepository clientRepo;	
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private SupplyContractRepository supplyContractRepo;
	
	@Autowired
	private EmploymentContractRepository employmentContractRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void getId() {
		Staff staff = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertThat(staff.getId(), Matchers.greaterThan((long)0));
	}
	
	@Test
	public void setId() {
		Staff staff = new Staff();
		assertEquals(0L, staff.getId().longValue());
		staff.setId(1L);
		assertEquals(1L, staff.getId().longValue());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetAssignments() {
		/**Prepare projects*/
		Project adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertNotNull(adir);
		assertEquals(ADIR, adir.getName());
		assertEquals(VERSION_1, adir.getVersion());		
		List <Assignment> adirAssignments = adir.getAssignments();
		assertEquals(6, adirAssignments.size());
		
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		assertNotNull(aos);
		assertEquals(AOS, aos.getName());
		assertEquals(VERSION_1, aos.getVersion());		
		List <Assignment> aosAssignments = aos.getAssignments();
		assertEquals(6, aosAssignments.size());
		
		Project coc = projectRepo.findByNameAndVersion(CENTRE_DES_COMPETENCES, VERSION_1);
		assertNotNull(coc);
		assertEquals(CENTRE_DES_COMPETENCES, coc.getName());
		assertEquals(VERSION_1, coc.getVersion());
		
		List <Assignment> cocAssignments = coc.getAssignments();
		assertEquals(5, cocAssignments.size());
		
		Project dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);
		assertNotNull(dcsc);
		assertEquals(DCSC, dcsc.getName());
		assertEquals(VERSION_1, dcsc.getVersion());		
		List <Assignment> dcscAssignments = dcsc.getAssignments();
		assertEquals(1, dcscAssignments.size());
		
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertNotNull(eolis);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		List <Assignment>  eolisAssignments = eolis.getAssignments();
		assertEquals(5, eolisAssignments.size());
				
		Project euroclear = projectRepo.findByNameAndVersion(EUROCLEAR_VERS_CALYPSO, VERSION_1);
		assertNotNull(euroclear);
		assertEquals(EUROCLEAR_VERS_CALYPSO, euroclear.getName());
		assertEquals(VERSION_1, euroclear.getVersion());
		List <Assignment>  euroclearAssignments = euroclear.getAssignments();
		assertEquals(2, euroclearAssignments.size());
		
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertNotNull(fortis);
		assertEquals(FORTIS, fortis.getName());
		assertEquals(VERSION_1, fortis.getVersion());
		List <Assignment>  fortisAssignments = fortis.getAssignments();
		assertEquals(3, fortisAssignments.size());
				
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(morningstarv1);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		List <Assignment>  morningstarv1Assignments = morningstarv1.getAssignments();
		assertEquals(10, morningstarv1Assignments.size());
		
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertNotNull(morningstarv2);
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		List <Assignment>  morningstarv2Assignments = morningstarv2.getAssignments();
		assertEquals(6, morningstarv2Assignments.size());
		
		Project parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		assertNotNull(parcours);
		assertEquals(PARCOURS, parcours.getName());
		assertEquals(VERSION_1, parcours.getVersion());
		List <Assignment>  parcoursAssignments = parcours.getAssignments();
		assertEquals(6, parcoursAssignments.size());
		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertNotNull(selenium);
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		List <Assignment>  seleniumAssignments = selenium.getAssignments();
		assertEquals(3, seleniumAssignments.size());
		
		Project sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		assertNotNull(sherpa);
		assertEquals(SHERPA, sherpa.getName());
		assertEquals(VERSION_1, sherpa.getVersion());
		List <Assignment>  sherpaAssignments = sherpa.getAssignments();
		assertEquals(6, sherpaAssignments.size());
	
		Project ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		assertNotNull(ted);
		assertEquals(TED, ted.getName());
		assertEquals(VERSION_1, ted.getVersion());
		List <Assignment>  tedAssignments = ted.getAssignments();
		assertEquals(4, tedAssignments.size());
				
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());
		List <Project> amtProjects = amt.getProjects();
		assertEquals(62, amtProjects.size());
		List <Assignment> amtProjectAssignments = amt.getAssignments();
		assertEquals(62, amtProjectAssignments.size());
		
		/**Prepare assignments*/	
		Task task1 = taskRepo.getTaskByDesc(TASK1);
		Task task2 = taskRepo.getTaskByDesc(TASK2);
		Task task3 = taskRepo.getTaskByDesc(TASK3);
		Task task4 = taskRepo.getTaskByDesc(TASK4);
		Task task5 = taskRepo.getTaskByDesc(TASK5);
		Task task6 = taskRepo.getTaskByDesc(TASK6);
		Task task7 = taskRepo.getTaskByDesc(TASK7);
		Task task8 = taskRepo.getTaskByDesc(TASK8);
		Task task9 = taskRepo.getTaskByDesc(TASK9);		
		Task task10 = taskRepo.getTaskByDesc(TASK10);
		Task task11 = taskRepo.getTaskByDesc(TASK11);
		Task task12 = taskRepo.getTaskByDesc(TASK12);
		Task task13 = taskRepo.getTaskByDesc(TASK13);
		Task task14 = taskRepo.getTaskByDesc(TASK14);
		Task task15 = taskRepo.getTaskByDesc(TASK15);
		Task task16 = taskRepo.getTaskByDesc(TASK16);
		Task task17 = taskRepo.getTaskByDesc(TASK17);
		Task task18 = taskRepo.getTaskByDesc(TASK18);
		Task task19 = taskRepo.getTaskByDesc(TASK19);		
		Task task20 = taskRepo.getTaskByDesc(TASK20);
		Task task21 = taskRepo.getTaskByDesc(TASK21);
		Task task22 = taskRepo.getTaskByDesc(TASK22);
		Task task23 = taskRepo.getTaskByDesc(TASK23);
		Task task24 = taskRepo.getTaskByDesc(TASK24);
		Task task25 = taskRepo.getTaskByDesc(TASK25);
		Task task26 = taskRepo.getTaskByDesc(TASK26);
		Task task27 = taskRepo.getTaskByDesc(TASK27);
		Task task28 = taskRepo.getTaskByDesc(TASK28);
		Task task29 = taskRepo.getTaskByDesc(TASK29);		
		Task task30 = taskRepo.getTaskByDesc(TASK30);
		Task task31 = taskRepo.getTaskByDesc(TASK31);
		Task task32 = taskRepo.getTaskByDesc(TASK32);
		Task task33 = taskRepo.getTaskByDesc(TASK33);
		Task task34 = taskRepo.getTaskByDesc(TASK34);
		Task task37 = taskRepo.getTaskByDesc(TASK37);	
		Task task39 = taskRepo.getTaskByDesc(TASK39);		
		Task task40 = taskRepo.getTaskByDesc(TASK40);
		Task task41 = taskRepo.getTaskByDesc(TASK41);
		Task task42 = taskRepo.getTaskByDesc(TASK42);
		Task task43 = taskRepo.getTaskByDesc(TASK43);
		Task task44 = taskRepo.getTaskByDesc(TASK44);
		Task task45 = taskRepo.getTaskByDesc(TASK45);
		Task task46 = taskRepo.getTaskByDesc(TASK46);
		Task task47 = taskRepo.getTaskByDesc(TASK47);
		Task task48 = taskRepo.getTaskByDesc(TASK48);
		Task task49 = taskRepo.getTaskByDesc(TASK49);		
		Task task50 = taskRepo.getTaskByDesc(TASK50);
		Task task51 = taskRepo.getTaskByDesc(TASK51);
		Task task52 = taskRepo.getTaskByDesc(TASK52);
		Task task53 = taskRepo.getTaskByDesc(TASK53);
		Task task54 = taskRepo.getTaskByDesc(TASK54);
		Task task55 = taskRepo.getTaskByDesc(TASK55);
		Task task56 = taskRepo.getTaskByDesc(TASK56);
		Task task57 = taskRepo.getTaskByDesc(TASK57);		
		assertEquals(1, task1.getAssignments().size());
		assertEquals(1, task2.getAssignments().size());
		assertEquals(1, task3.getAssignments().size());
		assertEquals(1, task4.getAssignments().size());
		assertEquals(1, task5.getAssignments().size());
		assertEquals(2, task6.getAssignments().size());
		assertEquals(1, task7.getAssignments().size());
		assertEquals(1, task8.getAssignments().size());
		assertEquals(1, task9.getAssignments().size());
		assertEquals(1, task10.getAssignments().size());
		assertEquals(1, task11.getAssignments().size());
		assertEquals(1, task12.getAssignments().size());
		assertEquals(1, task13.getAssignments().size());
		assertEquals(1, task14.getAssignments().size());
		assertEquals(1, task15.getAssignments().size());
		assertEquals(1, task16.getAssignments().size());
		assertEquals(1, task17.getAssignments().size());
		assertEquals(1, task18.getAssignments().size());
		assertEquals(1, task19.getAssignments().size());
		assertEquals(1, task20.getAssignments().size());
		assertEquals(1, task21.getAssignments().size());		
		assertEquals(2, task22.getAssignments().size());
		assertEquals(3, task23.getAssignments().size());
		assertEquals(2, task24.getAssignments().size());
		assertEquals(1, task25.getAssignments().size());
		assertEquals(1, task26.getAssignments().size());
		assertEquals(2, task27.getAssignments().size());
		assertEquals(1, task28.getAssignments().size());
		assertEquals(1, task29.getAssignments().size());
		assertEquals(1, task30.getAssignments().size());
		assertEquals(3, task31.getAssignments().size());
		assertEquals(1, task32.getAssignments().size());
		assertEquals(1, task33.getAssignments().size());
		assertEquals(1, task34.getAssignments().size());
		assertEquals(1, task37.getAssignments().size());
		assertEquals(1, task39.getAssignments().size());
		assertEquals(1, task40.getAssignments().size());
		assertEquals(1, task41.getAssignments().size());
		assertEquals(1, task42.getAssignments().size());
		assertEquals(1, task43.getAssignments().size());
		assertEquals(1, task44.getAssignments().size());
		assertEquals(1, task45.getAssignments().size());
		assertEquals(1, task46.getAssignments().size());
		assertEquals(1, task47.getAssignments().size());
		assertEquals(1, task48.getAssignments().size());
		assertEquals(1, task49.getAssignments().size());
		assertEquals(1, task50.getAssignments().size());
		assertEquals(1, task51.getAssignments().size());
		assertEquals(1, task52.getAssignments().size());
		assertEquals(1, task53.getAssignments().size());
		assertEquals(1, task54.getAssignments().size());
		assertEquals(1, task55.getAssignments().size());
		assertEquals(1, task56.getAssignments().size());
		assertEquals(2, task57.getAssignments().size());
		
		/**Prepare staff -> assignments*/
		Assignment assignment1 = assignmentRepo.findById(new AssignmentId	(adir.getId(), 			amt.getId(), task1.getId())).get();
		Assignment assignment2 = assignmentRepo.findById(new AssignmentId	(adir.getId(), 			amt.getId(), task2.getId())).get();
		Assignment assignment3 = assignmentRepo.findById(new AssignmentId	(adir.getId(), 			amt.getId(), task3.getId())).get();
		Assignment assignment4 = assignmentRepo.findById(new AssignmentId	(adir.getId(), 			amt.getId(), task4.getId())).get();
		Assignment aAssignment5 = assignmentRepo.findById(new AssignmentId	(adir.getId(), 			amt.getId(), task5.getId())).get();
		Assignment assignment6a = assignmentRepo.findById(new AssignmentId	(adir.getId(), 			amt.getId(), task6.getId())).get();
		Assignment assignment6b = assignmentRepo.findById(new AssignmentId	(fortis.getId(), 		amt.getId(), task6.getId())).get();
		Assignment assignment7 = assignmentRepo.findById(new AssignmentId	(fortis.getId(), 		amt.getId(), task7.getId())).get();
		Assignment assignment8 = assignmentRepo.findById(new AssignmentId	(fortis.getId(), 		amt.getId(), task8.getId())).get();
		Assignment assignment9 = assignmentRepo.findById(new AssignmentId	(dcsc.getId(), 			amt.getId(), task9.getId())).get();
		Assignment assignment10 = assignmentRepo.findById(new AssignmentId	(ted.getId(), 			amt.getId(), task10.getId())).get();
		Assignment assignment11 = assignmentRepo.findById(new AssignmentId	(ted.getId(), 			amt.getId(), task11.getId())).get();
		Assignment assignment12 = assignmentRepo.findById(new AssignmentId	(ted.getId(), 			amt.getId(), task12.getId())).get();
		Assignment assignment13 = assignmentRepo.findById(new AssignmentId	(ted.getId(), 			amt.getId(), task13.getId())).get();
		Assignment assignment14 = assignmentRepo.findById(new AssignmentId	(parcours.getId(), 		amt.getId(), task14.getId())).get();
		Assignment assignment15 = assignmentRepo.findById(new AssignmentId	(parcours.getId(), 		amt.getId(), task15.getId())).get();
		Assignment assignment16 = assignmentRepo.findById(new AssignmentId	(parcours.getId(), 		amt.getId(), task16.getId())).get();
		Assignment assignment17 = assignmentRepo.findById(new AssignmentId	(parcours.getId(), 		amt.getId(), task17.getId())).get();
		Assignment assignment18 = assignmentRepo.findById(new AssignmentId	(parcours.getId(), 		amt.getId(), task18.getId())).get();
		Assignment assignment19 = assignmentRepo.findById(new AssignmentId	(parcours.getId(), 		amt.getId(), task19.getId())).get();
		Assignment assignment20 = assignmentRepo.findById(new AssignmentId	(euroclear.getId(), 	amt.getId(), task20.getId())).get();
		Assignment assignment21 = assignmentRepo.findById(new AssignmentId	(euroclear.getId(), 	amt.getId(), task21.getId())).get();
		Assignment assignment22a = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task22.getId())).get();
		Assignment assignment22b = assignmentRepo.findById(new AssignmentId	(morningstarv2.getId(), amt.getId(), task22.getId())).get();
		Assignment assignment23a = assignmentRepo.findById(new AssignmentId	(eolis.getId(), 		amt.getId(), task23.getId())).get();
		Assignment assignment23b = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task23.getId())).get();
		Assignment assignment23c = assignmentRepo.findById(new AssignmentId	(morningstarv2.getId(), amt.getId(), task23.getId())).get();
		Assignment assignment24a = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task24.getId())).get();
		Assignment assignment24b = assignmentRepo.findById(new AssignmentId	(morningstarv2.getId(), amt.getId(), task24.getId())).get();
		Assignment assignment25 = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task25.getId())).get();
		Assignment assignment26 = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task26.getId())).get();
		Assignment assignment27a = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task27.getId())).get();
		Assignment assignment27b = assignmentRepo.findById(new AssignmentId	(morningstarv2.getId(), amt.getId(), task27.getId())).get();
		Assignment assignment28 = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task28.getId())).get();
		Assignment assignment29 = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task29.getId())).get();
		Assignment assignment30 = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task30.getId())).get();
		Assignment assignment31a = assignmentRepo.findById(new AssignmentId	(morningstarv1.getId(), amt.getId(), task31.getId())).get();
		Assignment assignment31b = assignmentRepo.findById(new AssignmentId	(morningstarv2.getId(), amt.getId(), task31.getId())).get();
		Assignment assignment31c = assignmentRepo.findById(new AssignmentId	(eolis.getId(), 		amt.getId(), task31.getId())).get();
		Assignment assignment32 = assignmentRepo.findById(new AssignmentId	(eolis.getId(), 		amt.getId(), task32.getId())).get();
		Assignment assignment33 = assignmentRepo.findById(new AssignmentId	(eolis.getId(), 		amt.getId(), task33.getId())).get();
		Assignment assignment34 = assignmentRepo.findById(new AssignmentId	(eolis.getId(), 		amt.getId(), task34.getId())).get();
		Assignment assignment37 = assignmentRepo.findById(new AssignmentId	(morningstarv2.getId(), amt.getId(), task37.getId())).get();		
		Assignment assignment39 = assignmentRepo.findById(new AssignmentId	(coc.getId(), 			amt.getId(), task39.getId())).get();		
		Assignment assignment40 = assignmentRepo.findById(new AssignmentId	(coc.getId(), 			amt.getId(), task40.getId())).get();
		Assignment assignment41 = assignmentRepo.findById(new AssignmentId	(coc.getId(), 			amt.getId(), task41.getId())).get();
		Assignment assignment42 = assignmentRepo.findById(new AssignmentId	(coc.getId(), 			amt.getId(), task42.getId())).get();
		Assignment assignment43 = assignmentRepo.findById(new AssignmentId	(coc.getId(), 			amt.getId(), task43.getId())).get();
		Assignment assignment44 = assignmentRepo.findById(new AssignmentId	(aos.getId(), 			amt.getId(), task44.getId())).get();
		Assignment assignment45 = assignmentRepo.findById(new AssignmentId	(aos.getId(), 			amt.getId(), task45.getId())).get();
		Assignment assignment46 = assignmentRepo.findById(new AssignmentId	(aos.getId(), 			amt.getId(), task46.getId())).get();
		Assignment assignment47 = assignmentRepo.findById(new AssignmentId	(aos.getId(), 			amt.getId(), task47.getId())).get();
		Assignment assignment48 = assignmentRepo.findById(new AssignmentId	(aos.getId(), 			amt.getId(), task48.getId())).get();
		Assignment assignment49 = assignmentRepo.findById(new AssignmentId	(aos.getId(), 			amt.getId(), task49.getId())).get();		
		Assignment assignment50 = assignmentRepo.findById(new AssignmentId	(selenium.getId(), 		amt.getId(), task50.getId())).get();
		Assignment assignment51 = assignmentRepo.findById(new AssignmentId	(selenium.getId(), 		amt.getId(), task51.getId())).get();
		Assignment assignment52 = assignmentRepo.findById(new AssignmentId	(selenium.getId(), 		amt.getId(), task52.getId())).get();
		Assignment assignment53 = assignmentRepo.findById(new AssignmentId	(sherpa.getId(), 		amt.getId(), task53.getId())).get();
		Assignment assignment54 = assignmentRepo.findById(new AssignmentId	(sherpa.getId(), 		amt.getId(), task54.getId())).get();
		Assignment assignment55 = assignmentRepo.findById(new AssignmentId	(sherpa.getId(), 		amt.getId(), task55.getId())).get();
		Assignment assignment56 = assignmentRepo.findById(new AssignmentId	(sherpa.getId(), 		amt.getId(), task56.getId())).get();
		Assignment assignment57 = assignmentRepo.findById(new AssignmentId	(sherpa.getId(), 		amt.getId(), task57.getId())).get();
		
		
		/**Validate staff's -> staff assignments*/
		assertThat(amtProjectAssignments, 
		Matchers.containsInAnyOrder(
		assignment1, assignment2, assignment3, assignment4, aAssignment5, assignment6a, assignment6b, assignment7,  assignment8, assignment9, 
		assignment10, assignment11, assignment12, assignment13, assignment14, assignment15, assignment16, assignment17, assignment18, assignment19, 
		assignment20, assignment21, assignment22a, assignment22b, assignment23a, assignment23b, assignment23c, assignment24a, assignment24b, assignment25, assignment26, assignment27a, assignment27b, assignment28,  assignment29,
		assignment30, assignment31a, assignment31b, assignment31c, assignment32, assignment33, assignment34, assignment37, assignment39, assignment40, assignment41, assignment42, assignment43, assignment44, assignment45, assignment46, assignment47, assignment48, assignment49,
		assignment50, assignment51, assignment52, assignment53, assignment54, assignment55, assignment56, assignment57	));
		
	}
	
	
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	public void testAddAssignment() {		
		/**Fetch Project and validate Project -> Assignments*/
		Project  adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(6, adir.getAssignments().size());
		
		/**Fetch Task and validate Task -> Assignments*/			
		Task task57 = taskRepo.getTaskByDesc(TASK57);
		assertEquals(2, task57.getAssignments().size());
				
		/**Fetch the Staff and validate Staff -> Assignments*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(1, john.getAssignments().size());
		
		/**Create new Assignment */
		Assignment assignment = new Assignment(adir, john, task57);
		
		/**Add the new Assignment*/
		john.addAssignment(assignment);	
		adir.addAssignment(assignment);
		task57.addAssignment(assignment);
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
		entityManager.persist(assignment);
		entityManager.merge(adir);
		entityManager.merge(john);
		entityManager.merge(task57);
		entityManager.flush();
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(64, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Validate Project -> Assignments*/
		adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(7, adir.getAssignments().size());
		
		/**Validate Task -> Assignments*/			
		task57 = taskRepo.getTaskByDesc(TASK57);
		assertEquals(3, task57.getAssignments().size());
				
		/**Validate Staff -> Assignments*/
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(2, john.getAssignments().size());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingAssignment() {
		/**Find projects*/
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);	
		
		/**Validate Projects -> test*/
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		
		/**Validate Staff -> test*/
		assertEquals(AMT_NAME, amt.getFirstName());
						
		/**Find assignments*/		
		Task task23 = taskRepo.getTaskByDesc(TASK23);
		Task task31 = taskRepo.getTaskByDesc(TASK31);		
		Task task32 = taskRepo.getTaskByDesc(TASK32);
		Task task33 = taskRepo.getTaskByDesc(TASK33);		
		Task task34 = taskRepo.getTaskByDesc(TASK34); 
		
		
		/**Validate Assignments to test**/
		assertEquals(TASK23, task23.getDesc());
		assertEquals(TASK31, task31.getDesc());
		assertEquals(TASK32, task32.getDesc());
		assertEquals(TASK33, task33.getDesc());
		assertEquals(TASK34, task34.getDesc());
		
		
		/**Find Assignments to test*/
		Assignment assignment1 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task23.getId())).get();
		Assignment assignment2 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task31.getId())).get();
		Assignment assignment3 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task32.getId())).get();
		Assignment assignment4 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task33.getId())).get();
		Assignment assignment5 = assignmentRepo.findById(new AssignmentId(eolis.getId(), amt.getId(), task34.getId())).get();
	
		/**Validate Assignments already exist in Project*/
		List <Assignment>  eolisAssignments = eolis.getAssignments();
		assertEquals(5, eolisAssignments.size());		
		assertThat(eolisAssignments,  Matchers.containsInAnyOrder(assignment1,  assignment2,  assignment3, assignment4, assignment5));
		
		/**Create new Assignment*/
		Assignment newAssignment = new Assignment(eolis, amt, task23);
		
		/**Add a duplicate Staff and Project association**/		
		amt.addAssignment(newAssignment); /***  <==== Throws EntityExistsException */
	}
	
	
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	public void testRemoveAssignment() {		
		/**Fetch Project and validate Project -> Assignments*/
		Project  adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(6, adir.getAssignments().size());
		
		/**Fetch Task and validate Task -> Assignments*/			
		Task task3 = taskRepo.getTaskByDesc(TASK3);
		assertEquals(1, task3.getAssignments().size());
				
		/**Fetch the Staff and validate Staff -> Assignments*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(62, amt.getAssignments().size());
		
		/**Find the target Assignment*/
		Assignment staleAssignment = assignmentRepo.findById(new AssignmentId(adir.getId(), amt.getId(), task3.getId())).get();
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Remove the target Assignment*/
		assertTrue(amt.removeAssignment(staleAssignment));	
		assertTrue(adir.removeAssignment(staleAssignment));
		assertTrue(task3.removeAssignment(staleAssignment));
				
		entityManager.remove(staleAssignment);
		entityManager.flush();
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, TASK_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Validate Project -> Assignments*/
		adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(5, adir.getAssignments().size());
		
		/**Validate Task -> Assignments*/			
		task3 = taskRepo.getTaskByDesc(TASK3);
		assertEquals(0, task3.getAssignments().size());
				
		/**Validate Staff -> Assignments*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(61, amt.getAssignments().size());		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetAssignments() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adir = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME,  BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Prepare Task*/
		assertEquals(0, countRowsInTable(jdbcTemplate, TASK_TABLE));		
		Task assignment1 = Utils.insertTask(TASK1, entityManager);
		assertEquals(1L, assignment1.getId().longValue());
		assertEquals(1, countRowsInTable(jdbcTemplate, TASK_TABLE));
		
		/**Validate staff -> assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		AssignmentId id = new AssignmentId(adir.getId(), amt.getId(), assignment1.getId());
		assertNull(entityManager.find(Assignment.class, id));
		
		/**Prepare staff -> assignments*/		
		Assignment amtAssignment = Utils.insertAssignment(adir, amt, assignment1, entityManager);		
		List <Assignment> amtAssignments = new ArrayList <> ();		
		amtAssignments.add(amtAssignment);
		adir.setAssignment(amtAssignments);
		assignment1.setAssignment(amtAssignments);
		amt.setAssignment(amtAssignments);				
		entityManager.merge(adir);
		entityManager.merge(amt);
		entityManager.merge(assignment1);
		entityManager.flush();
		
		/**Validate staff -> assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertNotNull(entityManager.find(Assignment.class, id));
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetProjects() {
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		List <Project> amtProject = amt.getProjects();
		assertEquals(62, amtProject.size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDetachInterestAndDbRemoveStaffWithCascadings() {
		/**Find Staff to test*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		assertEquals(JOHN_NAME, john.getFirstName());
		assertEquals(JOHN_LASTNAME, john.getLastName());	
		
		/**Get Staff -> SupplyContracts**/
		assertEquals(1, john.getSupplyContracts().size());
		SupplyContract johnSupplyContract = john.getSupplyContracts().get(0);
		
		/**SupplyContract -> Contract*/
		Contract johnContract = johnSupplyContract.getContract();	
		assertEquals(CONTRACT13_NAME, johnContract.getName());
		
		/**Validate Contract -> Client */
		Client belfius = clientRepo.getClientByName(BELFIUS);		
		assertEquals(belfius, johnContract.getClient());
		
		/**Test Interest -> Staff*/
		Interest johnInterest= interestRepo.getInterestByDesc(JOHN_INTEREST);
		assertNotNull(johnInterest);		
		assertEquals(john, johnInterest.getStaff());
			
		/**Detach entities*/		
		entityManager.clear();		
		
		/**Find detached Staff entity*/
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);		
		
		
		/**Tests initial state parent table
		STAFF_TABLE
		/**Tests initial state children tables
		INTEREST_TABLE
		StaffSkill.STAFF_SKILL_TABLE
		ENROLMENT_TABLE
		EMPLOYMENT_CONTRACT_TABLE
		SUPPLY_CONTRACT_TABLE
		ASSIGNMENT_TABLE
		/**Test other parents for control
		SKILL_TABLE
		SUPPLIER_TABLE
		CONTRACT_TABLE
		*/
		UtilsTest.testSchemaInitialState(jdbcTemplate);
		/**Detach interest from Staff and remove staff*/
		john.removeInterest(johnInterest);
		entityManager.merge(johnInterest);
		entityManager.remove(john);
		entityManager.flush();
		entityManager.clear();		
		/**Test state after remove*/
		UtilsTest.testStateAfterJohnStaffWithDetachedChildrenDelete(jdbcTemplate);		
		
		/**Test Interest -> Staff non-identifying relationship is set as NULL*/
		johnInterest = interestRepo.getInterestByDesc(JOHN_INTEREST);
		assertNotNull(johnInterest);		
		assertNull(johnInterest.getStaff());
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveInterest() {
		/**Find Staff to test*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		assertEquals(JOHN_NAME, john.getFirstName());
		assertEquals(JOHN_LASTNAME, john.getLastName());
	
		
		/**Get Staff -> SupplyContracts**/
		assertEquals(1, john.getSupplyContracts().size());
		SupplyContract johnSupplyContract = john.getSupplyContracts().get(0);
		
		/**SupplyContract -> Contract*/
		Contract johnContract = johnSupplyContract.getContract();	
		assertEquals(CONTRACT13_NAME, johnContract.getName());
		
		/**Validate Contract -> Client */
		Client belfius = clientRepo.getClientByName(BELFIUS);		
		assertEquals(belfius, johnContract.getClient());
		
				
		/**Test Interest -> Staff*/
		Interest johnInterest = interestRepo.getInterestByDesc(JOHN_INTEREST);
		assertNotNull(johnInterest);		
		assertEquals(john, johnInterest.getStaff());
			
		/**Detach entities*/		
		entityManager.clear();		
		
		/**Find detached Staff entity*/
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);		
		
		/***Remove Staff*/
		/**Tests initial state parent table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		  
		/**Detach interest from Staff */
		john.removeInterest(johnInterest);
		entityManager.merge(johnInterest);
		entityManager.merge(john);
		entityManager.flush();
		entityManager.clear();
		
		/**Test Staff parent table**/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		/**Test non-identifying Staff-> Interest children table didn't change*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		/**Tests state of children tables*/		
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(63, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		
		
		/**Test Interest -> Staff non-identifying relationship is set as NULL*/
		johnInterest = interestRepo.getInterestByDesc(JOHN_INTEREST);
		assertNotNull(johnInterest);		
		assertNull(johnInterest.getStaff());
		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetCourses() {				
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setCourses(new ArrayList <Course> ());
		//To set Courses to a Staff see EnrolmentTest.testSetStaff()		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddCourse() {			
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.addCourse(new Course());
		//To add a Course to a Staff see EnrolmentTest.testSetStaff()
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveCourse() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.removeCourse(new Course());
		//To remove a Course to a Staff see EnrolmentTest.testSetStaff()
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void getInterests() {
		/**Find Staff to test*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);		
		assertEquals(JOHN_NAME, john.getFirstName());
		assertEquals(JOHN_LASTNAME, john.getLastName());
				
		/**Test Staff -> Interests*/
		assertEquals(1, john.getInterests().size());
		assertEquals(JOHN_INTEREST, john.getInterests().get(0).getDesc());
		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void  testSetInterests() {				
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setInterests(new ArrayList <Interest> ());			
		//To set Interest to a Staff see InterestTest.testSetStaff()
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddInterest() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.addInterest(new Interest ());					
		//To add Interest into a Staff see InterestTest.testSetStaff()
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContracts() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		assertEquals(1, john.getSupplyContracts().size());
		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetSupplyContractsWithOrmOrphanRemove() {	
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(5, amt.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(1, amt.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings*/
		assertEquals(5, amt.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Create new SupplyContract to set to parent Staff*/
		Client belfius = clientRepo.getClientByName(BELFIUS);
		final String newContractName = "AccentureBelfiusContract";	
		
		/**New Contract*/
		Contract newContract = new Contract(); //new sequence id will be generated
		newContract.setName(newContractName);
		newContract.setClient(belfius);
		
		/**Persists new Contract*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));	
		entityManager.persist(newContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); // 1 new contract created in CONTRACT table. 
		
		/**New SupplyContract*/
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		SupplyContract newSupplyContract = new SupplyContract (accenture, newContract, amt);
		newSupplyContract.setStartDate(new Date());
		List <SupplyContract> newSupplyContracts = new ArrayList <>();
		newSupplyContracts.add(newSupplyContract);		
				
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //AMT STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	// Targeted orphans in SUPPLY_CONTRACT table	
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		/**This sets new AMT's SupplyContracts and leaves orphans*/
		amt.setSupplyContracts(newSupplyContracts);
		entityManager.merge(amt); //New Contract was previously created otherwise this error generates: org.hibernate.HibernateException: Flush during cascade is dangerous
		entityManager.flush();
		entityManager.clear();	
		
		/**Test post update state of Staff table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//13 orphans removed and 1 new child created in SUPPLY_CONTRACT table. 	
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		
		/**Validate parent Staff has new SupplyContract*/		
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		List <SupplyContract> amtSupplyContracts = amt.getSupplyContracts();		
		assertEquals(1, amtSupplyContracts.size());
		SupplyContract amtSupplyContract = amtSupplyContracts.get(0);
		assertEquals(newContract, amtSupplyContract.getContract());
		assertEquals(accenture, amtSupplyContract.getSupplier());
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddSupplyContract() {
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(5, amt.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(1, amt.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings*/
		assertEquals(5, amt.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Create new SupplyContract to set to parent Staff*/
		Client belfius = clientRepo.getClientByName(BELFIUS);
		final String newContractName = "AccentureBelfiusContract";
		
		/**New Contract*/
		Contract newContract = new Contract(); //new sequence id will be generated
		newContract.setName(newContractName);
		newContract.setClient(belfius);
		
		/**Persists new Contract*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		entityManager.persist(newContract);
		entityManager.flush();
		entityManager.clear();	
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); // 1 new contract created in CONTRACT table.
		
		/**New SupplyContract*/
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		SupplyContract newSupplyContract = new SupplyContract (accenture, newContract, amt);
		newSupplyContract.setStartDate(new Date());
		
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //AMT STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		/**Amend the new SupplyContract*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		amt.addSupplyContract(newSupplyContract);		
		entityManager.merge(amt); //New Contract was previously created otherwise this error generates: org.hibernate.HibernateException: Flush during cascade is dangerous
		entityManager.flush();
		entityManager.clear();	
		
		/**Test post update state of Staff table*/

		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(15, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//1 new child created.	
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		
		/**Validate parent Staff has new SupplyContract*/		
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		List <SupplyContract> amtSupplyContracts = amt.getSupplyContracts();		
		assertEquals(14, amtSupplyContracts.size());		
		assertThat(amtSupplyContracts, Matchers.hasItem(newSupplyContract));
		
		/**Validate inverse assoc. SupplyContract -> Staff*/
		assertNotNull(supplyContractRepo.findByContractAndSupplierAndStaff(newContract, accenture, amt));
				
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSupplyContractWithOrmOrphanRemove() {
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(5, amt.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(1, amt.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings*/
		assertEquals(5, amt.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Chose a SupplyContract to remove*/
		SupplyContract staleSupplyContract = amt.getSupplyContracts().get(0);
		
		/**Detach entities*/
		entityManager.clear();
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //AMT STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		/**Remove the SupplyContract*/
		amt.removeSupplyContract(staleSupplyContract);
		entityManager.merge(amt);
		entityManager.flush();
		entityManager.clear();
		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//1 orphan child is removed.	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		
		/**Validate stale SupplyContract doesn't exist*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		List <SupplyContract> supplyContracts = amt.getSupplyContracts();
		assertThat(supplyContracts, Matchers.not(Matchers.hasItem(staleSupplyContract)));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetEmploymentContracts() {
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		
		assertEquals(5, amt.getEmploymentContracts().size());		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEmploymentContractsWithNullSet() {
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(5, amt.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(1, amt.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings*/
		assertEquals(5, amt.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Detach entities*/
		entityManager.clear();		
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //AMT STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//Target orphan table is EMPLOYMENT_CONTRACT table
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		
		/**This sets current Staff with EmploymenContracts as orphans*/
		amt.setEmploymentContracts(null);
		entityManager.merge(amt);
		entityManager.flush();
		entityManager.clear();				
		
		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //AMT STAFF_ID='1'	
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));		
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	// 5 orphans removed in EMPLOYMENT_CONTRACT table. Other tables ramain unchanged.
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));				
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		
		
		/**Test parent Staff has no EmploymentContracts*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(0, amt.getEmploymentContracts().size());
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEmploymentContractWithCascadings() {				
		/**Find target Staff*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(1, john.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(0, john.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(0, john.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(1, john.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings */
		assertEquals(1, john.getSupplyContracts().size());
		
		/**Find EmploymentContract to remove*/
		EmploymentContract staleEmploymentcontract = john.getEmploymentContracts().get(0);
		
		/**Detach entities*/
		entityManager.clear();
		john =  staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //John STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		/**Remove EmploymentContract*/
		john.removeEmploymentContract(staleEmploymentcontract);
		entityManager.merge(john);
		entityManager.flush();
		entityManager.clear();
		
		/**Test post update state of Staff table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE)); 
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEmploymentContractWithNullSet() {
		/**Find target Staff*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(1, john.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(0, john.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(0, john.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(1, john.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings */
		assertEquals(1, john.getSupplyContracts().size());
		
		/**Detach entities*/
		entityManager.clear();
		john =  staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //John STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		/**Sets currents John's EmploymentContracts as orphans*/
		john.setEmploymentContracts(null);
		entityManager.merge(john);
		entityManager.flush();
		entityManager.clear();
		
		/**Test post update state of Staff table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE)); 
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	

	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetEmploymentContractsWithOrmOrphanRemove() {
		/**Find target Staff*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(1, john.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(0, john.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(0, john.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings*/
		assertEquals(1, john.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(1, john.getSupplyContracts().size());
		
		/**Create new EmploymentContract to set to parent Staff*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		/**New SupplyContract*/
		EmploymentContract newEmploymentContract = new EmploymentContract (john, accenture);
		newEmploymentContract.setStartDate(new Date());
		List <EmploymentContract> newEmploymentContracts = new ArrayList <>();
		newEmploymentContracts.add(newEmploymentContract);		
				
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //John STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		/**This sets new AMT's SupplyContracts and leaves orphans*/
		john.setEmploymentContracts(newEmploymentContracts);		
		entityManager.merge(john);
		entityManager.flush();
		entityManager.clear();	
		
		/**Test post update state of Staff table*/

		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		//1 orphan removed and 1 new child created in EMPLOYMENT_CONTRACT table. 
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		
		/**Validate parent Staff has new EmploymentContract*/		
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		newEmploymentContracts = john.getEmploymentContracts();
		assertEquals(1, newEmploymentContracts.size());
		newEmploymentContract = newEmploymentContracts.get(0);
		assertEquals(accenture, newEmploymentContract.getSupplier());
		
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddEmploymentContract() {
	
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills*/
		assertEquals(5, amt.getSkills().size());
		
		/**Verify parent Staff -> Course/
		assertEquals(1, amt.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract*/
		assertEquals(5, amt.getEmploymentContracts().size());		
		
		/**Verify parent Staff -> SupplyContract*/
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Create the new EmploymentContract*/			
		Supplier amesys = supplierRepo.getSupplierByName(AMESYS);		
		EmploymentContract newEmploymentContract = new EmploymentContract(amt, amesys);
		newEmploymentContract.setStartDate(new Date());
		/**Test the existing state for 'amt' and 'amesys' EmploymentContract */
		List <EmploymentContract> amtAmesysEmploymentContracts = employmentContractRepo.findByStaffAndSupplier(amt, amesys);
		assertEquals(1, amtAmesysEmploymentContracts.size());
		
		/**Test initial state of Staff table (the parent)*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //AMT STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		/**ENROLS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		/**Amend the new EmploymentContract*/
		amt.addEmploymentContract(newEmploymentContract);
		entityManager.merge(amt);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		//1 child created.
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));			
		
		amtAmesysEmploymentContracts = employmentContractRepo.findByStaffAndSupplier(amt, amesys);
		assertEquals(2, amtAmesysEmploymentContracts.size());
		
		/**Validate that both EmploymentContracts have same Staff and Supplier*/		
		EmploymentContract amtAmesysEmploymentContract1 =  amtAmesysEmploymentContracts.get(0);
		EmploymentContract amtAmesysEmploymentContract2 =  amtAmesysEmploymentContracts.get(1);
		assertEquals(amt, amtAmesysEmploymentContract1.getStaff());		
		assertEquals(amt, amtAmesysEmploymentContract2.getStaff());
		assertEquals(amesys, amtAmesysEmploymentContract2.getSupplier());
		assertEquals(amesys, amtAmesysEmploymentContract1.getSupplier());
		
		/**However both EmploymentContracts have different id*/
		assertThat(amtAmesysEmploymentContract1.getId(), Matchers.not(amtAmesysEmploymentContract2.getId()));	
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSkills() {
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(5, amt.getSkills().size());

	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaffSkills() {				
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setStaffSkills(new ArrayList <StaffSkill> ());
		//To set StaffSkills to a Staff see StaffSkillTest.testSetStaff()
	}
	
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetSkills() {		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setSkills(new ArrayList <Skill> ());
		//To set Skills to a Staff see StaffSkillTest.testSetSkill()		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddSkill() {	
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		john.addSkill(new Skill());
		//To add Skills to a Staff see StaffSkillTest.testAddStaffSkill
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSkill() {		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		john.removeSkill(new Skill());	
		//To add Skills to a Staff see StaffSkillTest.testRemoveStaffSkill
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDbRemoveStaffWithCascadings() {
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Verify parent Staff -> Skills is a many-to-many with no REMOVE cascadings*/
		assertEquals(5, amt.getSkills().size());
		
		/**Verify parent Staff -> Course is a many-to-many with no REMOVE cascadings*/
		assertEquals(1, amt.getCourses().size());
		
		/**Verify parent Staff -> EmploymentContract with REMOVE cascadings*/
		assertEquals(5, amt.getEmploymentContracts().size());
		
		/**Verify parent Staff -> SupplyContract with REMOVE cascadings <- impacted relationship*/
		assertEquals(13, amt.getSupplyContracts().size());
		
		/**Detach entities*/
		entityManager.clear();
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		
		/**Test initial state of Staff table (the parent)*/
		/**STAFF_TABLE  //AMT STAFF_ID='1'
		*Tests the initial state of the children table(s) from the Parent table
		* HAS
		* INTEREST_TABLE
		* USES
		* SKILL_TABLE
		* StaffSkill.STAFF_SKILL_TABLE
		* ENROLS
		* ENROLMENT_TABLE
		* COURSE_TABLE
		* IS EMPLOYED
		* EMPLOYMENT_CONTRACT_TABLE
		* SUPPLIER_TABLE
		* WORKS IN
	    * SUPPLY_CONTRACT_TABLE
		* WORKS ON
		* ASSIGNMENT_TABLE 62 children with STAFF_ID=1 removed from  table. 
		* ASSIGNMENT_TABLE
		* PROJECT_TABLE
		* Tests the initial state of the children table(s) from the Parent table
		* Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.
		* CONTRACT_TABLE
		* Tests the initial state of the children table(s) from previous Parent table(s)
		*CONTRACT_SERVICE_AGREEMENT_TABLE
		*/
		UtilsTest.testSchemaInitialState(jdbcTemplate);
		/**Remove the Staff*/		
		entityManager.remove(amt);
		entityManager.flush();
		entityManager.clear();
		
		/**Test Staff delete DB post state*/
		UtilsTest.testStateAfterAMtStaffDelete(jdbcTemplate);	
	
	}
	
	@Test
	public void testBirthDateIsNotNull() {
		//TODO could be impl. with JUnit 5 see example of davidxxx answered Feb 22 '18 at 23:49 in https://stackoverflow.com/questions/29069956/how-to-test-validation-annotations-of-a-class-using-junit
		Staff staff = createValidStaff();
		staff.setBirthDate(null);
		Set<ConstraintViolation<Staff>> violations = validator.validate(staff);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
	}
	
	@Test
	public void testFirstNameIsNotNull() {
		//TODO could be impl. with JUnit 5 see example of davidxxx answered Feb 22 '18 at 23:49 in https://stackoverflow.com/questions/29069956/how-to-test-validation-annotations-of-a-class-using-junit
		Staff staff = createValidStaff();
		staff.setFirstName(null);		
		Set<ConstraintViolation<Staff>> violations = validator.validate(staff);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
	}
	
	@Test
	public void testLastNameDateIsNotNull() {
		//TODO could be impl. with JUnit 5 see example of davidxxx answered Feb 22 '18 at 23:49 in https://stackoverflow.com/questions/29069956/how-to-test-validation-annotations-of-a-class-using-junit
		Staff staff = createValidStaff();
		staff.setLastName(null);
		Set<ConstraintViolation<Staff>> violations = validator.validate(staff);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
	}
	
	public static Staff createValidStaff(){
		Staff staff = new Staff();
		staff.setFirstName(AMT_NAME);
		staff.setLastName(AMT_LASTNAME);
		staff.setBirthDate(new Date());
		return staff;
	}

	@Test
	public void testToString() {
		Staff staff = new Staff();
		assertThat(staff.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}

}
