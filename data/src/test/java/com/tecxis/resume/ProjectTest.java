package com.tecxis.resume;

import static com.tecxis.resume.CityTest.LOCATION_TABLE;
import static com.tecxis.resume.CityTest.insertACity;
import static com.tecxis.resume.StaffProjectAssignmentTest.STAFFPROJECTASSIGNMENT_TABLE;
import static com.tecxis.resume.StaffProjectAssignmentTest.insertAStaffProjectAssignment;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT1;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT14;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT22;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT23;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT24;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT25;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT26;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT27;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT28;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT29;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT30;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT31;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT32;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT33;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT34;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT37;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT_TABLE;
import static com.tecxis.resume.persistence.CityRepositoryTest.BRUSSELS;
import static com.tecxis.resume.persistence.CityRepositoryTest.CITY_TABLE;
import static com.tecxis.resume.persistence.CityRepositoryTest.LONDON;
import static com.tecxis.resume.persistence.CityRepositoryTest.MANCHESTER;
import static com.tecxis.resume.persistence.CityRepositoryTest.PARIS;
import static com.tecxis.resume.persistence.CityRepositoryTest.SWINDON;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AGEAS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.CLIENT_TABLE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.BELGIUM;
import static com.tecxis.resume.persistence.CountryRepositoryTest.COUNTRY_TABLE;
import static com.tecxis.resume.persistence.CountryRepositoryTest.FRANCE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.AOS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EOLIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.FORTIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PARCOURS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SELENIUM;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_2;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
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

import com.tecxis.resume.Location.LocationId;
import com.tecxis.resume.persistence.AssignmentRepository;
import com.tecxis.resume.persistence.CityRepository;
import com.tecxis.resume.persistence.LocationRepository;
import com.tecxis.resume.persistence.ProjectRepository;
import com.tecxis.resume.persistence.StaffProjectAssignmentRepository;
import com.tecxis.resume.persistence.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
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

	@Test
	public void testGetDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProjectId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDesc() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaffProjectAssignments() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adir = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		Assignment assignment1 = AssignmentTest.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));	
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adir, amt, assignment1);		
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		
		/**Prepare staff assignments*/			
		StaffProjectAssignment amtStaffProjectAssignment = insertAStaffProjectAssignment(adir, amt, assignment1, entityManager);		
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
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffProjectAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adir = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		Assignment assignment1 = AssignmentTest.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getStaffProjectAssignments().size());		
		assertEquals(0, adir.getStaffProjectAssignments().size());
		assertEquals(0, assignment1.getStaffProjectAssignments().size());
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));		
		adir.addStaffProjectAssignment(amt, assignment1);
		amt.addStaffProjectAssignment(adir, assignment1);
		assignment1.addStaffProjectAssignment(amt, adir);
		
		entityManager.merge(adir);
		entityManager.merge(amt);
		entityManager.merge(assignment1);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffProjectAssignments().size());		
		assertEquals(1, adir.getStaffProjectAssignments().size());
		assertEquals(1, assignment1.getStaffProjectAssignments().size());
	}

	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingStaffProjectAssignment() {
		/**Find projects*/
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);	
		
		/**Validate Projects to test*/
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		
		/**Validate Staff to test*/
		assertEquals(AMT_NAME, amt.getName());
						
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
		
		/**Add a duplicate Staff and Project association**/
		eolis.addStaffProjectAssignment(amt, assignment34); /***  <==== Throws EntityExistsException */
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffProjectAssignment() {
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(parcours, amt, assignment14);	
		assertEquals(62, amt.getStaffProjectAssignments().size());		
		assertEquals(6, parcours.getStaffProjectAssignments().size());
		assertEquals(1, assignment14.getStaffProjectAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();

		/**Fetch and validate contract to test*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
		/**Remove staff assignment*/
		/**StaffProjectAssignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Assignment */
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Validate staff assignments*/
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		amt = staffRepo.getStaffLikeName(AMT_NAME);
		assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);	
		assertEquals(61, amt.getStaffProjectAssignments().size());		
		assertEquals(5, parcours.getStaffProjectAssignments().size());
		assertEquals(0, assignment14.getStaffProjectAssignments().size());

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignments() {
		/**Prepare project*/
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);		
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		List <StaffProjectAssignment> morningstarv1StaffProjectAssignments = morningstarv1.getStaffProjectAssignments();
		assertEquals(10, morningstarv1StaffProjectAssignments.size());
		
		
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
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
		Client belfius = ClientTest.insertAClient(BELFIUS, entityManager);
		Project sherpaProject = ProjectTest.insertAProject(SHERPA, VERSION_1, belfius, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
				
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Country belgium = CountryTest.insertACountry(BELGIUM, entityManager);
		City brussels = insertACity(BRUSSELS, belgium, entityManager);
		Country france = CountryTest.insertACountry(FRANCE, entityManager);
		City paris = insertACity(PARIS, france, entityManager);
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
		Country uk = CountryTest.insertACountry("United Kingdom", entityManager);
		Country france = CountryTest.insertACountry(FRANCE, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));		
		City london = insertACity(LONDON, uk, entityManager);
		City swindon = insertACity(SWINDON, uk, entityManager);
		City paris = insertACity(PARIS, france, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CITY_TABLE));
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adirProject = ProjectTest.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		Client ageas = ClientTest.insertAClient(AGEAS, entityManager);		
		Project fortisProject = ProjectTest.insertAProject(FORTIS, VERSION_1, ageas, entityManager);
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
		Client belfius = ClientTest.insertAClient(BELFIUS, entityManager);
		Project sherpaProject = ProjectTest.insertAProject(SHERPA, VERSION_1, belfius, entityManager);			
		Country belgium = CountryTest.insertACountry(BELGIUM, entityManager);
		City brussels = insertACity(BRUSSELS, belgium, entityManager);
		Country france = CountryTest.insertACountry(FRANCE, entityManager);
		City paris = insertACity(PARIS, france, entityManager);		
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
	public void testAddLocation() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddExistingLocation() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testRemoveLocation() {
		fail("Not yet implemented");
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
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
		assertEquals(manchester, manchesterLocations.get(0).getLocationId().getCity());

		/***Validate the Project's current Locations*/
		assertEquals(1, selenium.getLocations().size());		
		Location seleniumLocation = locationRepo.findById(new LocationId(paris, selenium)).get();		
		assertEquals(seleniumLocation, selenium.getLocations().get(0));
				
		/**Prepare new Locations*/
		List <Location> newLocations = new ArrayList<>();
		Location manchesterSeleniumLoc = new Location(new LocationId(manchester, selenium));		
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
		assertThat(selenium.getLocations().get(0).getLocationId().getCity(), Matchers.oneOf(paris, manchester));
		assertThat(selenium.getLocations().get(1).getLocationId().getCity(), Matchers.oneOf(paris, manchester));
		/**Cities are linked through Location table*/
		assertEquals(2, selenium.getCities().size());
		assertThat(selenium.getCities().get(0), Matchers.oneOf(paris, manchester));
		assertThat(selenium.getCities().get(1), Matchers.oneOf(paris, manchester));
		
		/**Validate the opposite association*/
		manchester = cityRepo.getCityByName(MANCHESTER);
		assertEquals(2, manchester.getProjects().size());
		//Reopen persistence contest otherwise exception is thrown-> LazyInitializationException : failed to lazily initialize 
		//a collection of role:, could not initialize proxy - no Session
		// Read: https://vladmihalcea.com/the-hibernate-enable_lazy_load_no_trans-anti-pattern/
		Project adir =  projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertThat(manchester.getProjects().get(0), Matchers.oneOf(selenium, adir));
		assertThat(manchester.getProjects().get(1), Matchers.oneOf(selenium, adir));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
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
		assertEquals(paris, selemiumLocation.getLocationId().getCity());
		assertEquals(selenium, selemiumLocation.getLocationId().getProject());
				
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVersion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetVersion() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetStaffs() {
		fail("Not yet implemented");
	}

	public static Project insertAProject(String name, String version, Client client, EntityManager entityManager) {
		Project project = new Project();
		project.setClient(client);		
		project.setName(name);
		project.setVersion(version);
		assertEquals(0, project.getId());
		entityManager.persist(project);
		entityManager.flush();
		assertThat(project.getId(), Matchers.greaterThan((long)0));
		return project;
	
	}

}
