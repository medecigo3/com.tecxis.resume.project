package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.City.CITY_TABLE;
import static com.tecxis.resume.domain.Client.CLIENT_TABLE;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AGEAS;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.AOS;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT1;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT14;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT22;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT23;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT24;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT25;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT26;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT27;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT28;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT29;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT30;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT31;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT32;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT33;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT34;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT37;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT57;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.BELGIUM;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.BRUSSELS;
import static com.tecxis.resume.domain.Constants.EOLIS;
import static com.tecxis.resume.domain.Constants.EULER_HERMES;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.MANCHESTER;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.PROJECT_DESC;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Constants.SELENIUM;
import static com.tecxis.resume.domain.Constants.SHERPA;
import static com.tecxis.resume.domain.Constants.SWINDON;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Constants.VERSION_2;
import static com.tecxis.resume.domain.Constants.VERSION_3;
import static com.tecxis.resume.domain.Country.COUNTRY_TABLE;
import static com.tecxis.resume.domain.Location.LOCATION_TABLE;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffProjectAssignmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ProjectTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired 
	private LocationRepository locationRepo;
	
	@Autowired 
	private ClientRepository clientRepo;
	
	@Autowired
	private Validator validator;
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Client sagemcom = Utils.insertAClient(SAGEMCOM, entityManager);	
		Project ted = Utils.insertAProject(TED, VERSION_1, sagemcom, entityManager);
		assertThat(ted.getId(), Matchers.greaterThan((long)0));		
	}

	@Test
	public void testSetId() {
		Project project = new Project();
		assertEquals(0, project.getId());
		project.setId(1);
		assertEquals(1, project.getId());		
	}	
	
	@Test
	public void testGetDesc() {
		Project project = new Project();
		assertNull(project.getDesc());		
	}
	
	@Test
	public void testSetDesc() {
		Project project = new Project();
		assertNull(project.getDesc());
		project.setDesc(PROJECT_DESC);
		assertEquals(PROJECT_DESC,  project.getDesc());		
	}
	
	@Test
	public void testGetName() {
		Project project = new Project();
		assertNull(project.getName());		
	}

	@Test
	public void testSetName() {
		Project project = new Project();
		assertNull(project.getName());		
		project.setDesc(SAGEMCOM);
		assertEquals(SAGEMCOM,  project.getDesc());
	}

	@Test
	public void testGetVersion() {
		Project project = new Project();
		assertNull(project.getVersion());
		
	}

	@Test
	public void testSetVersion() {
		Project project = new Project();
		assertNull(project.getVersion());
		project.setVersion(VERSION_1);
		assertEquals(VERSION_1,  project.getVersion());		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetClient() {
		/**Find project to test*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Retrieve the Client target*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		assertEquals(axeltis, morningstartV1Project.getClient());
		
		/**Test Project's Client*/
		assertEquals(axeltis, morningstartV1Project.getClient());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetClient() {
		/**Find project to test*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		long morningstartV1ProjectId = morningstartV1Project.getId();
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Test Project -> Client association*/		
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		assertEquals(axeltis, morningstartV1Project.getClient());
		
		/**Test Project -> City association*/
		City paris = cityRepo.getCityByName(PARIS);
		assertThat(morningstartV1Project.getCities(), Matchers.hasItem(paris));
		
		/**Find new Client to set*/
		Client eh = clientRepo.getClientByName(EULER_HERMES);
		assertEquals(EULER_HERMES, eh.getName());
				
		/**Build new Project -> Client association*/
		Project newAxeltisProject = new Project();
		newAxeltisProject.setId(morningstartV1Project.getId());		
		newAxeltisProject.setName(MORNINGSTAR);
		newAxeltisProject.setVersion(VERSION_3);
		newAxeltisProject.setClient(eh);
		newAxeltisProject.setCities(morningstartV1Project.getCities());
		
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		entityManager.remove(morningstartV1Project);
		entityManager.persist(newAxeltisProject);
		entityManager.merge(eh);
		entityManager.flush();
		entityManager.clear();
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Validate project was updated */
		Project morningstartV3Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_3);
		/**Test id is same as old*/
		assertEquals(morningstartV1ProjectId, morningstartV3Project.getId());
		/**Test new client*/
		assertEquals(eh, morningstartV3Project.getClient());
		/**Test new version*/
		assertEquals(VERSION_3, morningstartV3Project.getVersion());
		/**Test an old referenced city*/
		assertThat(morningstartV3Project.getCities(), Matchers.hasItem(paris));
		
		
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaffProjectAssignments() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);		
		Project adir = Utils.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		Assignment assignment1 = Utils.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adir, amt, assignment1);		
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		
		/**Prepare staff assignments*/			
		StaffProjectAssignment amtStaffProjectAssignment = Utils.insertAStaffProjectAssignment(adir, amt, assignment1, entityManager);		
		List <StaffProjectAssignment> amtStaffProjectAssignments = new ArrayList <> ();		
		amtStaffProjectAssignments.add(amtStaffProjectAssignment);
		adir.setStaffProjectAssignment(amtStaffProjectAssignments);
		assignment1.setStaffProjectAssignment(amtStaffProjectAssignments);
		amt.setStaffProjectAssignment(amtStaffProjectAssignments);				
		entityManager.merge(adir);
		entityManager.merge(amt);
		entityManager.merge(assignment1);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffProjectAssignmentFromScratch() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);		
		Project adir = Utils.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Prepare assignment*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		Assignment assignment1 = Utils.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getStaffProjectAssignments().size());		
		assertEquals(0, adir.getStaffProjectAssignments().size());
		assertEquals(0, assignment1.getStaffProjectAssignments().size());
		
		/**Create new StaffProjectAssignment*/
		StaffProjectAssignment newStaffProjectAssignment = new StaffProjectAssignment(adir, amt, assignment1);
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));		
		adir.addStaffProjectAssignment(newStaffProjectAssignment);
		amt.addStaffProjectAssignment(newStaffProjectAssignment);
		assignment1.addStaffProjectAssignment(newStaffProjectAssignment);
		
		entityManager.persist(newStaffProjectAssignment);
		entityManager.merge(adir);
		entityManager.merge(amt);
		entityManager.merge(assignment1);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffProjectAssignments().size());		
		assertEquals(1, adir.getStaffProjectAssignments().size());
		assertEquals(1, assignment1.getStaffProjectAssignments().size());
	}
	
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	public void testAddStaffProjectAssignment() {		
		/**Fetch Project and validate Project -> StaffProjectAssignments*/
		Project  adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(6, adir.getStaffProjectAssignments().size());
		
		/**Fetch Assignment and validate Assignment -> StaffProjectAssignments*/			
		Assignment assignment57 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT57);
		assertEquals(2, assignment57.getStaffProjectAssignments().size());
				
		/**Fetch the Staff and validate Staff -> StaffProjectAssignments*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(1, john.getStaffProjectAssignments().size());
		
		/**Create new StaffProjectAssignment */
		StaffProjectAssignment newStaffProjectAssignment = new StaffProjectAssignment(adir, john, assignment57);
		
		/**Add the new StaffProjectAssignment*/
		john.addStaffProjectAssignment(newStaffProjectAssignment);	
		adir.addStaffProjectAssignment(newStaffProjectAssignment);
		assignment57.addStaffProjectAssignment(newStaffProjectAssignment);
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
		entityManager.persist(newStaffProjectAssignment);
		entityManager.merge(adir);
		entityManager.merge(john);
		entityManager.merge(assignment57);
		entityManager.flush();
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(64, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Validate Project -> StaffProjectAssignments*/
		adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(7, adir.getStaffProjectAssignments().size());
		
		/**Validate Assignment -> StaffProjectAssignments*/			
		assignment57 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT57);
		assertEquals(3, assignment57.getStaffProjectAssignments().size());
				
		/**Validate Staff -> StaffProjectAssignments*/
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(2, john.getStaffProjectAssignments().size());
	}

	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingStaffProjectAssignment() {
		/**Find projects*/
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);	
		
		/**Validate Projects to test*/
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		
		/**Validate Staff to test*/
		assertEquals(AMT_NAME, amt.getFirstName());
						
		/**Find assignments*/		
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT23);
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT31);		
		Assignment assignment32 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT32);
		Assignment assignment33 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT33);		
		Assignment assignment34 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT34); 
		
		
		/**Validate Assignments to test**/
		assertEquals(ASSIGNMENT23, assignment23.getDesc());
		assertEquals(ASSIGNMENT31, assignment31.getDesc());
		assertEquals(ASSIGNMENT32, assignment32.getDesc());
		assertEquals(ASSIGNMENT33, assignment33.getDesc());
		assertEquals(ASSIGNMENT34, assignment34.getDesc());
		
		
		/**Find StaffProjectAssignments to test*/
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment23)).get();
		StaffProjectAssignment staffProjectAssignment2 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment31)).get();
		StaffProjectAssignment staffProjectAssignment3 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment32)).get();
		StaffProjectAssignment staffProjectAssignment4 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment33)).get();
		StaffProjectAssignment staffProjectAssignment5 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment34)).get();
	
		/**Validate StaffProjectAssignments already exist in Project*/
		List <StaffProjectAssignment>  eolisStaffProjectAssignments = eolis.getStaffProjectAssignments();
		assertEquals(5, eolisStaffProjectAssignments.size());		
		assertThat(eolisStaffProjectAssignments,  Matchers.containsInAnyOrder(staffProjectAssignment1,  staffProjectAssignment2,  staffProjectAssignment3, staffProjectAssignment4, staffProjectAssignment5));
		
		/**Create new StaffProjectAssignment*/
		StaffProjectAssignment newStaffProjectAssignment = new StaffProjectAssignment(eolis, amt, assignment34);
		
		/**Add a duplicate Staff and Project association**/
		eolis.addStaffProjectAssignment(newStaffProjectAssignment); /***  <==== Throws EntityExistsException */
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffProjectAssignment() {
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(parcours, amt, assignment14);	
		assertEquals(62, amt.getStaffProjectAssignments().size());		
		assertEquals(6, parcours.getStaffProjectAssignments().size());
		assertEquals(1, assignment14.getStaffProjectAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
		/**Remove staff assignment*/
		/**StaffProjectAssignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Assignment */
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);	
		assertEquals(61, amt.getStaffProjectAssignments().size());		
		assertEquals(5, parcours.getStaffProjectAssignments().size());
		assertEquals(0, assignment14.getStaffProjectAssignments().size());

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignments() {
		/**Prepare project*/
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);		
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		List <StaffProjectAssignment> morningstarv1StaffProjectAssignments = morningstarv1.getStaffProjectAssignments();
		assertEquals(10, morningstarv1StaffProjectAssignments.size());
		
		
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertNotNull(amt);
		List <StaffProjectAssignment> amtAssignments = amt.getStaffProjectAssignments();
		assertEquals(62, amtAssignments.size());
		
		/**Prepare assignments*/
		Assignment assignment22 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT22);
		Assignment assignment23 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT23);	
		Assignment assignment24 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT24);	
		Assignment assignment25 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT25);
		Assignment assignment26 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT26);
		Assignment assignment27 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT27);
		Assignment assignment28 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT28);		
		Assignment assignment29 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT29);			
		Assignment assignment30 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT30);		
		Assignment assignment31 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT31);	
		assertEquals(ASSIGNMENT22, assignment22.getDesc());
		assertEquals(ASSIGNMENT23, assignment23.getDesc());
		assertEquals(ASSIGNMENT24, assignment24.getDesc());
		assertEquals(ASSIGNMENT25, assignment25.getDesc());
		assertEquals(ASSIGNMENT26, assignment26.getDesc());
		assertEquals(ASSIGNMENT27, assignment27.getDesc());
		assertEquals(ASSIGNMENT28, assignment28.getDesc());
		assertEquals(ASSIGNMENT29, assignment29.getDesc());
		assertEquals(ASSIGNMENT30, assignment30.getDesc());
		assertEquals(ASSIGNMENT31, assignment31.getDesc());
		assertEquals(2, assignment22.getStaffProjectAssignments().size());
		assertEquals(3, assignment23.getStaffProjectAssignments().size());
		assertEquals(2, assignment24.getStaffProjectAssignments().size());
		assertEquals(1, assignment25.getStaffProjectAssignments().size());
		assertEquals(1, assignment26.getStaffProjectAssignments().size());
		assertEquals(2, assignment27.getStaffProjectAssignments().size());
		assertEquals(1, assignment28.getStaffProjectAssignments().size());
		assertEquals(1, assignment29.getStaffProjectAssignments().size());
		assertEquals(1, assignment30.getStaffProjectAssignments().size());
		assertEquals(3, assignment31.getStaffProjectAssignments().size());
		
		/**Prepare staff assignments*/
		StaffProjectAssignment msv1StaffProjectAssignment1 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment22)).get();
		StaffProjectAssignment msv1StaffProjectAssignment2 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment23)).get();
		StaffProjectAssignment msv1StaffProjectAssignment3 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment24)).get();
		StaffProjectAssignment msv1StaffProjectAssignment4 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment25)).get();
		StaffProjectAssignment msv1StaffProjectAssignment5 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment26)).get();
		StaffProjectAssignment msv1StaffProjectAssignment6 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment27)).get();
		StaffProjectAssignment msv1StaffProjectAssignment7 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment28)).get();
		StaffProjectAssignment msv1StaffProjectAssignment8 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment29)).get();
		StaffProjectAssignment msv1StaffProjectAssignment9 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment30)).get();
		StaffProjectAssignment msv1StaffProjectAssignment10 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment31)).get();
			
		/**Validate project's staff assignments*/
		assertThat(morningstarv1StaffProjectAssignments, Matchers.containsInAnyOrder(msv1StaffProjectAssignment1, msv1StaffProjectAssignment2, msv1StaffProjectAssignment3, msv1StaffProjectAssignment4, msv1StaffProjectAssignment5, msv1StaffProjectAssignment6, msv1StaffProjectAssignment7, msv1StaffProjectAssignment8, msv1StaffProjectAssignment9, msv1StaffProjectAssignment10));
		
		/**TEST 2*/
		
		/**Prepare project*/		
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2); 		
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		List <StaffProjectAssignment> morningstarv2Assignments = morningstarv2.getStaffProjectAssignments();
		assertNotNull(morningstarv2Assignments);
		assertEquals(6, morningstarv2Assignments.size());
		
		/**Prepare assignments*/		
		Assignment assignment37 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT37);
		assertEquals(ASSIGNMENT37, assignment37.getDesc());
		assertEquals(1, assignment37.getStaffProjectAssignments().size());
		
		/**Prepare staff assignments*/
		StaffProjectAssignment mv2StaffProjectAssignment1 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment22)).get();
		StaffProjectAssignment mv2StaffProjectAssignment2 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment23)).get();
		StaffProjectAssignment mv2StaffProjectAssignment3 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment24)).get();
		StaffProjectAssignment mv2StaffProjectAssignment4 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment27)).get();
		StaffProjectAssignment mv2StaffProjectAssignment5 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment31)).get();
		StaffProjectAssignment mv2StaffProjectAssignment6 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment37)).get();
			
		/**Validate project's staff assignments*/
		assertThat(morningstarv2Assignments, Matchers.containsInAnyOrder(mv2StaffProjectAssignment1, mv2StaffProjectAssignment2, mv2StaffProjectAssignment3, mv2StaffProjectAssignment4, mv2StaffProjectAssignment5, mv2StaffProjectAssignment6 ));


	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetCities() {
		City swindon = cityRepo.getCityByName(SWINDON);
		assertEquals(SWINDON, swindon.getName());
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		assertEquals(AOS, aos.getName());
		assertEquals(VERSION_1, aos.getVersion());
		
		List <City> aosCities = aos.getCities();
		assertNotNull(aosCities);
		assertEquals(2, aosCities.size());
		assertThat(aosCities, Matchers.containsInAnyOrder(swindon, paris));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetCities() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client belfius = Utils.insertAClient(BELFIUS, entityManager);
		Project sherpaProject = Utils.insertAProject(SHERPA, VERSION_1, belfius, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country belgium = Utils.insertACountry(BELGIUM, entityManager);
		City brussels = Utils.insertACity(BRUSSELS, belgium, entityManager);
		Country france = Utils.insertACountry(FRANCE, entityManager);
		City paris = Utils.insertACity(PARIS, france, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		
		List <City> sherpaProjectCities = new ArrayList <> ();
		sherpaProjectCities.add(brussels);
		sherpaProjectCities.add(paris);
		sherpaProject.setCities(sherpaProjectCities);
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		
		entityManager.merge(sherpaProject);
		entityManager.flush();
		
		assertEquals(2, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddCity() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country uk = Utils.insertACountry("United Kingdom", entityManager);
		Country france = Utils.insertACountry(FRANCE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));		
		City london = Utils.insertACity(LONDON, uk, entityManager);
		City swindon = Utils.insertACity(SWINDON, uk, entityManager);
		City paris = Utils.insertACity(PARIS, france, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CITY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		Client ageas = Utils.insertAClient(AGEAS, entityManager);		
		Project fortisProject = Utils.insertAProject(FORTIS, VERSION_1, ageas, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertTrue(adirProject.addCity(london));		
		entityManager.merge(london);
		entityManager.flush();
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		assertTrue(adirProject.addCity(paris));
		entityManager.merge(swindon);
		entityManager.flush();
		assertEquals(2, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
				
		assertTrue(fortisProject.addCity(swindon));
		entityManager.merge(swindon);
		entityManager.flush();
		assertEquals(3, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveCity() {
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));		
		Client belfius = Utils.insertAClient(BELFIUS, entityManager);
		Project sherpaProject = Utils.insertAProject(SHERPA, VERSION_1, belfius, entityManager);			
		Country belgium = Utils.insertACountry(BELGIUM, entityManager);
		City brussels = Utils.insertACity(BRUSSELS, belgium, entityManager);
		Country france = Utils.insertACountry(FRANCE, entityManager);
		City paris = Utils.insertACity(PARIS, france, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		List <City> sherpaProjectCities = new ArrayList <> ();
		sherpaProjectCities.add(brussels);
		sherpaProjectCities.add(paris);
		sherpaProject.setCities(sherpaProjectCities);
		entityManager.merge(sherpaProject);
		entityManager.flush();		
		assertEquals(2, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		
		

		assertTrue(sherpaProject.removeCity(brussels));
		assertEquals(0, brussels.getProjects().size());
		assertEquals(1, sherpaProject.getCities().size());
		entityManager.merge(sherpaProject);							
		entityManager.flush();
		
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		

		assertTrue(sherpaProject.removeCity(paris));
		assertEquals(0, sherpaProject.getCities().size());
		assertEquals(0, paris.getProjects().size());
		entityManager.merge(sherpaProject);
		entityManager.flush();
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddLocation() {		
		/**Find City*/
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		
		/**Find Project & validate to test*/		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getLocations().size());		
		assertEquals(paris,  selenium.getLocations().get(0).getCity());	
		
		/**Find City to add*/
		City manchester = cityRepo.getCityByName(MANCHESTER);
		
		/**Test initial state*/
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		/**Create new Locations*/
		Location newLocation = new Location(manchester, selenium);
		
		/**Add Location to Project*/
		selenium.addLocation(newLocation);
		manchester.addLocation(newLocation);
		entityManager.persist(newLocation);
		entityManager.merge(selenium);
		entityManager.merge(manchester);
		entityManager.flush();
		
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(15, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
						
		selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(2, selenium.getLocations().size());		
		assertThat(selenium.getLocations().get(0).getCity(),  Matchers.oneOf(paris, manchester));
		assertThat(selenium.getLocations().get(1).getCity(),  Matchers.oneOf(paris, manchester));			
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingLocation() {
		/**Find City*/
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		
		/**Find Project & validate to test*/		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getLocations().size());		
		assertEquals(paris,  selenium.getLocations().get(0).getCity());	
		
		/**Test initial state*/
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		/**Create new Location*/
		Location newLocation = new Location (paris, selenium);
		
		/**Add existing Location to Project*/ /***  <==== Throws EntityExistsException */		
		selenium.addLocation(newLocation);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveLocation() {
		/**Find City location*/
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());		
		
		/**Find Project location*/		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);		
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getLocations().size());		
		
		/**Detach entities*/
		entityManager.clear();
		
		/**Validate initial state*/		
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		/**Remove location*/
		/**Location has to be removed as it is the relation owner between Project <-> Location*/
		Location seleniumLocation = locationRepo.findById(new LocationId(paris, selenium)).get();
		entityManager.remove(seleniumLocation);
		entityManager.flush();
		
		/**Validate Location was removed*/
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));				
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);	
		assertEquals(0, selenium.getLocations().size());		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetLocations() {
		
		/**Find & validate Project to test*/
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertEquals(1, selenium.getLocations().size());
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getCities().size());
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		assertEquals(paris, selenium.getCities().get(0));
		/**Validate Locations*/
		List <Location> seleniumLocations  = selenium.getLocations();
		assertEquals(1, seleniumLocations.size());
		/**Validate the opposite association*/
		List <City> seleniumCities =  selenium.getCities();
		assertEquals(1, seleniumCities.size());
		assertEquals(seleniumCities.get(0), paris);
					
		
		/**Find & validate city to test*/
		City manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(MANCHESTER, manchester.getName());
		List <Location> manchesterLocations = manchester.getLocations();
		assertEquals(manchester, manchesterLocations.get(0).getCity());

		/***Validate the Project's current Locations*/
		assertEquals(1, selenium.getLocations().size());		
		Location seleniumLocation = locationRepo.findById(new LocationId(paris, selenium)).get();		
		assertEquals(seleniumLocation, selenium.getLocations().get(0));
				
		/**Prepare new Locations*/
		List <Location> newLocations = new ArrayList<>();
		Location manchesterSeleniumLoc = new Location(manchester, selenium);		
		newLocations.add(manchesterSeleniumLoc);
				
		/**Set new Locations*/
		selenium.setLocations(newLocations);
		
		/**Set new cities*/
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		
		selenium.setLocations(newLocations);
		entityManager.merge(selenium);
		entityManager.flush();
		entityManager.clear();
		assertEquals(15, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		
		/**Test & validate the new Locations*/
		selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);	
		assertEquals(2, selenium.getLocations().size());
		assertThat(selenium.getLocations().get(0).getCity(), Matchers.oneOf(paris, manchester));
		assertThat(selenium.getLocations().get(1).getCity(), Matchers.oneOf(paris, manchester));
		/**Cities are linked through Location table*/
		assertEquals(2, selenium.getCities().size());
		assertThat(selenium.getCities().get(0), Matchers.oneOf(paris, manchester));
		assertThat(selenium.getCities().get(1), Matchers.oneOf(paris, manchester));
		
		/**Validate the opposite association*/
		manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(2, manchester.getProjects().size());
		//Reopen persistence context otherwise exception is thrown-> LazyInitializationException : failed to lazily initialize 
		//a collection of role:, could not initialise proxy - no Session
		// Read: https://vladmihalcea.com/the-hibernate-enable_lazy_load_no_trans-anti-pattern/
		Project adir =  projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertThat(manchester.getProjects().get(0), Matchers.oneOf(selenium, adir));
		assertThat(manchester.getProjects().get(1), Matchers.oneOf(selenium, adir));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetLocations() {
		/**Find & validate Project to test*/
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertEquals(1, selenium.getLocations().size());
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		assertEquals(1, selenium.getCities().size());
		City paris = cityRepo.getCityByName(PARIS);
		assertEquals(PARIS, paris.getName());
		assertEquals(paris, selenium.getCities().get(0));		
		/**Validate the opposite association*/
		List <City> seleniumCities =  selenium.getCities();
		assertEquals(1, seleniumCities.size());
		assertEquals(seleniumCities.get(0), paris);
		
		/**Validate Locations*/
		List <Location> seleniumLocations  = selenium.getLocations();
		assertEquals(1, seleniumLocations.size());
		Location selemiumLocation =  seleniumLocations.get(0);
		assertEquals(paris, selemiumLocation.getCity());
		assertEquals(selenium, selemiumLocation.getProject());
				
	}

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaff() {		
		/**Find & validate Project to test*/
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		List <Staff> seleniumStaff = selenium.getStaff();
		//TODO this assert should be tested to 1. Create a query in ProjectRepo that joins the three tables using a distinct. For instance:
//		select distinct  Staff.firstName,  Staff.lastName 
//		from
//		    PROJECT
//		inner join 
//			STAFF_PROJECT_ASSIGNMENT 
//		        on PROJECT.PROJECT_ID = STAFF_PROJECT_ASSIGNMENT.PROJECT_ID AND PROJECT.CLIENT_ID = STAFF_PROJECT_ASSIGNMENT.CLIENT_ID
//		inner join
//			Staff  
//				on STAFF_PROJECT_ASSIGNMENT.STAFF_ID=Staff.STAFF_ID 
//		where
//			STAFF_PROJECT_ASSIGNMENT.PROJECT_ID=13 
//			and STAFF_PROJECT_ASSIGNMENT.CLIENT_ID=12		
		assertEquals(3, seleniumStaff.size());		 
		
		/**Find target Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertThat(seleniumStaff, Matchers.hasItem(amt));
		 
	}
	
	@Test(expected=NoSuchElementException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveProject() {
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());	
		
		/**Test Project -> Client association*/		
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		assertEquals(axeltis.getId(), morningstartV1Project.getClient().getId());
		
		/**Test Client -> Project association*/
		List <Project> axeltisProjects = axeltis.getProjects();
		assertEquals(2, axeltisProjects.size());
		assertThat(axeltisProjects, Matchers.hasItem(morningstartV1Project));
		
		/**Test Project -> Location association*/
		List <Location> axeltisV1ProjectLocations = morningstartV1Project.getLocations();
		assertEquals(1, axeltisV1ProjectLocations.size());
		City paris = cityRepo.getCityByName(PARIS); 
		assertEquals(PARIS, paris.getName());
		assertEquals(paris, axeltisV1ProjectLocations.get(0).getCity());
		
		/**Test Location -> Project association*/
		Location axeltisMorningstarv1ProjectLocation =  locationRepo.findById(new LocationId(paris, morningstartV1Project)).get();
		assertEquals(paris, axeltisMorningstarv1ProjectLocation.getCity());
		assertEquals(morningstartV1Project, axeltisMorningstarv1ProjectLocation.getProject());
		
		/**Test Project -> StaffProjectAssignment association*/
		List <StaffProjectAssignment> morningstartV1StaffProjectAssignments  = morningstartV1Project.getStaffProjectAssignments();
		assertEquals(10, morningstartV1StaffProjectAssignments.size());
					
		/**Detach entities*/		
		entityManager.clear();
		
		/**Find Project to remove again*/
		morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());
		
		/**Remove Project*/
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		/**Test orphans initial state*/
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		entityManager.remove(morningstartV1Project);
		entityManager.flush();
		entityManager.clear();
		
		/**Test Project was removed*/
		assertEquals(12, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		/**Test orphans */
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(53, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertNull(projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1));		
		assertNull(locationRepo.findById(new LocationId(paris, morningstartV1Project)).get());
	}
	
	@Test
	public void testNameAndVersionAreNotNull() {
		Project project = new Project();
		Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertFalse(violations.isEmpty());
	}
	
	@Test
	public void testToString() {
		Project project = new Project ();
		project.toString();		
	}

}
