package com.tecxis.resume;

import static com.tecxis.resume.StaffProjectAssignmentTest.STAFFPROJECTASSIGNMENT_TABLE;
import static com.tecxis.resume.StaffProjectAssignmentTest.insertAStaffProjectAssignment;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT1;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT10;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT11;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT12;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT13;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT14;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT15;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT16;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT17;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT18;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT19;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT2;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT20;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT21;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT22;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT23;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT24;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT25;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT26;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT27;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT28;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT29;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT3;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT30;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT31;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT32;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT33;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT34;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT37;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT39;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT4;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT40;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT41;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT42;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT43;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT44;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT45;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT46;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT47;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT48;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT49;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT5;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT50;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT51;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT52;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT53;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT54;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT55;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT56;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT57;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT6;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT7;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT8;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT9;
import static com.tecxis.resume.persistence.AssignmentRepositoryTest.ASSIGNMENT_TABLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.ARVAL;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.ADIR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.AOS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.CENTRE_DES_COMPETENCES;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.DCSC;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EOLIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.EUROCLEAR_VERS_CALYPSO;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.FORTIS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PARCOURS;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SELENIUM;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.SHERPA;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.TED;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_1;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.VERSION_2;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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

import com.tecxis.resume.persistence.AssignmentRepository;
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
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffProjectAssignments() {
		/**Prepare projects*/
		Project adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertNotNull(adir);
		assertEquals(ADIR, adir.getName());
		assertEquals(VERSION_1, adir.getVersion());		
		List <StaffProjectAssignment> adirStaffProjectAssignments = adir.getStaffProjectAssignments();
		assertEquals(6, adirStaffProjectAssignments.size());
		
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		assertNotNull(aos);
		assertEquals(AOS, aos.getName());
		assertEquals(VERSION_1, aos.getVersion());		
		List <StaffProjectAssignment> aosStaffProjectAssignments = aos.getStaffProjectAssignments();
		assertEquals(6, aosStaffProjectAssignments.size());
		
		Project coc = projectRepo.findByNameAndVersion(CENTRE_DES_COMPETENCES, VERSION_1);
		assertNotNull(coc);
		assertEquals(CENTRE_DES_COMPETENCES, coc.getName());
		assertEquals(VERSION_1, coc.getVersion());
		
		List <StaffProjectAssignment> cocStaffProjectAssignments = coc.getStaffProjectAssignments();
		assertEquals(5, cocStaffProjectAssignments.size());
		
		Project dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);
		assertNotNull(dcsc);
		assertEquals(DCSC, dcsc.getName());
		assertEquals(VERSION_1, dcsc.getVersion());		
		List <StaffProjectAssignment> dcscStaffProjectAssignments = dcsc.getStaffProjectAssignments();
		assertEquals(1, dcscStaffProjectAssignments.size());
		
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertNotNull(eolis);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		List <StaffProjectAssignment>  eolisStaffProjectAssignments = eolis.getStaffProjectAssignments();
		assertEquals(5, eolisStaffProjectAssignments.size());
				
		Project euroclear = projectRepo.findByNameAndVersion(EUROCLEAR_VERS_CALYPSO, VERSION_1);
		assertNotNull(euroclear);
		assertEquals(EUROCLEAR_VERS_CALYPSO, euroclear.getName());
		assertEquals(VERSION_1, euroclear.getVersion());
		List <StaffProjectAssignment>  euroclearStaffProjectAssignments = euroclear.getStaffProjectAssignments();
		assertEquals(2, euroclearStaffProjectAssignments.size());
		
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertNotNull(fortis);
		assertEquals(FORTIS, fortis.getName());
		assertEquals(VERSION_1, fortis.getVersion());
		List <StaffProjectAssignment>  fortisStaffProjectAssignments = fortis.getStaffProjectAssignments();
		assertEquals(3, fortisStaffProjectAssignments.size());
				
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(morningstarv1);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		List <StaffProjectAssignment>  morningstarv1StaffProjectAssignments = morningstarv1.getStaffProjectAssignments();
		assertEquals(10, morningstarv1StaffProjectAssignments.size());
		
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertNotNull(morningstarv2);
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		List <StaffProjectAssignment>  morningstarv2StaffProjectAssignments = morningstarv2.getStaffProjectAssignments();
		assertEquals(6, morningstarv2StaffProjectAssignments.size());
		
		Project parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		assertNotNull(parcours);
		assertEquals(PARCOURS, parcours.getName());
		assertEquals(VERSION_1, parcours.getVersion());
		List <StaffProjectAssignment>  parcoursStaffProjectAssignments = parcours.getStaffProjectAssignments();
		assertEquals(6, parcoursStaffProjectAssignments.size());
		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertNotNull(selenium);
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		List <StaffProjectAssignment>  seleniumStaffProjectAssignments = selenium.getStaffProjectAssignments();
		assertEquals(3, seleniumStaffProjectAssignments.size());
		
		Project sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		assertNotNull(sherpa);
		assertEquals(SHERPA, sherpa.getName());
		assertEquals(VERSION_1, sherpa.getVersion());
		List <StaffProjectAssignment>  sherpaStaffProjectAssignments = sherpa.getStaffProjectAssignments();
		assertEquals(6, sherpaStaffProjectAssignments.size());
	
		Project ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		assertNotNull(ted);
		assertEquals(TED, ted.getName());
		assertEquals(VERSION_1, ted.getVersion());
		List <StaffProjectAssignment>  tedStaffProjectAssignments = ted.getStaffProjectAssignments();
		assertEquals(4, tedStaffProjectAssignments.size());
				
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		List <Project> amtProjects = amt.getProjects();
		assertEquals(62, amtProjects.size());
		List <StaffProjectAssignment> amtProjectAssignments = amt.getStaffProjectAssignments();
		assertEquals(62, amtProjectAssignments.size());
		
		/**Prepare assignments*/	
		Assignment assignment1 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT1);
		Assignment assignment2 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT2);
		Assignment assignment3 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT3);
		Assignment assignment4 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT4);
		Assignment assignment5 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT5);
		Assignment assignment6 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT6);
		Assignment assignment7 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT7);
		Assignment assignment8 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT8);
		Assignment assignment9 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT9);		
		Assignment assignment10 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT10);
		Assignment assignment11 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT11);
		Assignment assignment12 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT12);
		Assignment assignment13 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT13);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);
		Assignment assignment15 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT15);
		Assignment assignment16 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT16);
		Assignment assignment17 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT17);
		Assignment assignment18 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT18);
		Assignment assignment19 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT19);		
		Assignment assignment20 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT20);
		Assignment assignment21 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT21);
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
		Assignment assignment32 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT32);
		Assignment assignment33 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT33);
		Assignment assignment34 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT34);
		Assignment assignment37 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT37);	
		Assignment assignment39 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT39);		
		Assignment assignment40 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT40);
		Assignment assignment41 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT41);
		Assignment assignment42 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT42);
		Assignment assignment43 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT43);
		Assignment assignment44 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT44);
		Assignment assignment45 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT45);
		Assignment assignment46 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT46);
		Assignment assignment47 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT47);
		Assignment assignment48 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT48);
		Assignment assignment49 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT49);		
		Assignment assignment50 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT50);
		Assignment assignment51 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT51);
		Assignment assignment52 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT52);
		Assignment assignment53 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT53);
		Assignment assignment54 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT54);
		Assignment assignment55 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT55);
		Assignment assignment56 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT56);
		Assignment assignment57 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT57);		
		assertEquals(1, assignment1.getStaffProjectAssignments().size());
		assertEquals(1, assignment2.getStaffProjectAssignments().size());
		assertEquals(1, assignment3.getStaffProjectAssignments().size());
		assertEquals(1, assignment4.getStaffProjectAssignments().size());
		assertEquals(1, assignment5.getStaffProjectAssignments().size());
		assertEquals(2, assignment6.getStaffProjectAssignments().size());
		assertEquals(1, assignment7.getStaffProjectAssignments().size());
		assertEquals(1, assignment8.getStaffProjectAssignments().size());
		assertEquals(1, assignment9.getStaffProjectAssignments().size());
		assertEquals(1, assignment10.getStaffProjectAssignments().size());
		assertEquals(1, assignment11.getStaffProjectAssignments().size());
		assertEquals(1, assignment12.getStaffProjectAssignments().size());
		assertEquals(1, assignment13.getStaffProjectAssignments().size());
		assertEquals(1, assignment14.getStaffProjectAssignments().size());
		assertEquals(1, assignment15.getStaffProjectAssignments().size());
		assertEquals(1, assignment16.getStaffProjectAssignments().size());
		assertEquals(1, assignment17.getStaffProjectAssignments().size());
		assertEquals(1, assignment18.getStaffProjectAssignments().size());
		assertEquals(1, assignment19.getStaffProjectAssignments().size());
		assertEquals(1, assignment20.getStaffProjectAssignments().size());
		assertEquals(1, assignment21.getStaffProjectAssignments().size());		
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
		assertEquals(1, assignment32.getStaffProjectAssignments().size());
		assertEquals(1, assignment33.getStaffProjectAssignments().size());
		assertEquals(1, assignment34.getStaffProjectAssignments().size());
		assertEquals(1, assignment37.getStaffProjectAssignments().size());
		assertEquals(1, assignment39.getStaffProjectAssignments().size());
		assertEquals(1, assignment40.getStaffProjectAssignments().size());
		assertEquals(1, assignment41.getStaffProjectAssignments().size());
		assertEquals(1, assignment42.getStaffProjectAssignments().size());
		assertEquals(1, assignment43.getStaffProjectAssignments().size());
		assertEquals(1, assignment44.getStaffProjectAssignments().size());
		assertEquals(1, assignment45.getStaffProjectAssignments().size());
		assertEquals(1, assignment46.getStaffProjectAssignments().size());
		assertEquals(1, assignment47.getStaffProjectAssignments().size());
		assertEquals(1, assignment48.getStaffProjectAssignments().size());
		assertEquals(1, assignment49.getStaffProjectAssignments().size());
		assertEquals(1, assignment50.getStaffProjectAssignments().size());
		assertEquals(1, assignment51.getStaffProjectAssignments().size());
		assertEquals(1, assignment52.getStaffProjectAssignments().size());
		assertEquals(1, assignment53.getStaffProjectAssignments().size());
		assertEquals(1, assignment54.getStaffProjectAssignments().size());
		assertEquals(1, assignment55.getStaffProjectAssignments().size());
		assertEquals(1, assignment56.getStaffProjectAssignments().size());
		assertEquals(2, assignment57.getStaffProjectAssignments().size());
		
		/**Prepare staff assignments*/
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(adir, amt, assignment1)).get();
		StaffProjectAssignment staffProjectAssignment2 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(adir, amt, assignment2)).get();
		StaffProjectAssignment staffProjectAssignment3 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(adir, amt, assignment3)).get();
		StaffProjectAssignment staffProjectAssignment4 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(adir, amt, assignment4)).get();
		StaffProjectAssignment staffProjectAssignment5 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(adir, amt, assignment5)).get();
		StaffProjectAssignment staffProjectAssignment6a = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(adir, amt, assignment6)).get();
		StaffProjectAssignment staffProjectAssignment6b = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(fortis, amt, assignment6)).get();
		StaffProjectAssignment staffProjectAssignment7 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(fortis, amt, assignment7)).get();
		StaffProjectAssignment staffProjectAssignment8 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(fortis, amt, assignment8)).get();
		StaffProjectAssignment staffProjectAssignment9 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(dcsc, amt, assignment9)).get();
		StaffProjectAssignment staffProjectAssignment10 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(ted, amt, assignment10)).get();
		StaffProjectAssignment staffProjectAssignment11 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(ted, amt, assignment11)).get();
		StaffProjectAssignment staffProjectAssignment12 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(ted, amt, assignment12)).get();
		StaffProjectAssignment staffProjectAssignment13 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(ted, amt, assignment13)).get();
		StaffProjectAssignment staffProjectAssignment14 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(parcours, amt, assignment14)).get();
		StaffProjectAssignment staffProjectAssignment15 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(parcours, amt, assignment15)).get();
		StaffProjectAssignment staffProjectAssignment16 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(parcours, amt, assignment16)).get();
		StaffProjectAssignment staffProjectAssignment17 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(parcours, amt, assignment17)).get();
		StaffProjectAssignment staffProjectAssignment18 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(parcours, amt, assignment18)).get();
		StaffProjectAssignment staffProjectAssignment19 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(parcours, amt, assignment19)).get();
		StaffProjectAssignment staffProjectAssignment20 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(euroclear, amt, assignment20)).get();
		StaffProjectAssignment staffProjectAssignment21 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(euroclear, amt, assignment21)).get();
		StaffProjectAssignment staffProjectAssignment22a = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment22)).get();
		StaffProjectAssignment staffProjectAssignment22b = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment22)).get();
		StaffProjectAssignment staffProjectAssignment23a = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment23)).get();
		StaffProjectAssignment staffProjectAssignment23b = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment23)).get();
		StaffProjectAssignment staffProjectAssignment23c = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment23)).get();
		StaffProjectAssignment staffProjectAssignment24a = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment24)).get();
		StaffProjectAssignment staffProjectAssignment24b = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment24)).get();
		StaffProjectAssignment staffProjectAssignment25 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment25)).get();
		StaffProjectAssignment staffProjectAssignment26 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment26)).get();
		StaffProjectAssignment staffProjectAssignment27a = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment27)).get();
		StaffProjectAssignment staffProjectAssignment27b = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment27)).get();
		StaffProjectAssignment staffProjectAssignment28 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment28)).get();
		StaffProjectAssignment staffProjectAssignment29 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment29)).get();
		StaffProjectAssignment staffProjectAssignment30 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment30)).get();
		StaffProjectAssignment staffProjectAssignment31a = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv1, amt, assignment31)).get();
		StaffProjectAssignment staffProjectAssignment31b = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment31)).get();
		StaffProjectAssignment staffProjectAssignment31c = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment31)).get();
		StaffProjectAssignment staffProjectAssignment32 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment32)).get();
		StaffProjectAssignment staffProjectAssignment33 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment33)).get();
		StaffProjectAssignment staffProjectAssignment34 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(eolis, amt, assignment34)).get();
		StaffProjectAssignment staffProjectAssignment37 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(morningstarv2, amt, assignment37)).get();		
		StaffProjectAssignment staffProjectAssignment39 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(coc, amt, assignment39)).get();		
		StaffProjectAssignment staffProjectAssignment40 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(coc, amt, assignment40)).get();
		StaffProjectAssignment staffProjectAssignment41 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(coc, amt, assignment41)).get();
		StaffProjectAssignment staffProjectAssignment42 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(coc, amt, assignment42)).get();
		StaffProjectAssignment staffProjectAssignment43 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(coc, amt, assignment43)).get();
		StaffProjectAssignment staffProjectAssignment44 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(aos, amt, assignment44)).get();
		StaffProjectAssignment staffProjectAssignment45 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(aos, amt, assignment45)).get();
		StaffProjectAssignment staffProjectAssignment46 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(aos, amt, assignment46)).get();
		StaffProjectAssignment staffProjectAssignment47 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(aos, amt, assignment47)).get();
		StaffProjectAssignment staffProjectAssignment48 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(aos, amt, assignment48)).get();
		StaffProjectAssignment staffProjectAssignment49 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(aos, amt, assignment49)).get();		
		StaffProjectAssignment staffProjectAssignment50 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(selenium, amt, assignment50)).get();
		StaffProjectAssignment staffProjectAssignment51 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(selenium, amt, assignment51)).get();
		StaffProjectAssignment staffProjectAssignment52 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(selenium, amt, assignment52)).get();
		StaffProjectAssignment staffProjectAssignment53 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(sherpa, amt, assignment53)).get();
		StaffProjectAssignment staffProjectAssignment54 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(sherpa, amt, assignment54)).get();
		StaffProjectAssignment staffProjectAssignment55 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(sherpa, amt, assignment55)).get();
		StaffProjectAssignment staffProjectAssignment56 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(sherpa, amt, assignment56)).get();
		StaffProjectAssignment staffProjectAssignment57 = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(sherpa, amt, assignment57)).get();
		
		
		/**Validate staff's staff assignments*/
		assertThat(amtProjectAssignments, 
		Matchers.containsInAnyOrder(
		staffProjectAssignment1, staffProjectAssignment2, staffProjectAssignment3, staffProjectAssignment4, staffProjectAssignment5, staffProjectAssignment6a, staffProjectAssignment6b, staffProjectAssignment7,  staffProjectAssignment8, staffProjectAssignment9, 
		staffProjectAssignment10, staffProjectAssignment11, staffProjectAssignment12, staffProjectAssignment13, staffProjectAssignment14, staffProjectAssignment15, staffProjectAssignment16, staffProjectAssignment17, staffProjectAssignment18, staffProjectAssignment19, 
		staffProjectAssignment20, staffProjectAssignment21, staffProjectAssignment22a, staffProjectAssignment22b, staffProjectAssignment23a, staffProjectAssignment23b, staffProjectAssignment23c, staffProjectAssignment24a, staffProjectAssignment24b, staffProjectAssignment25, staffProjectAssignment26, staffProjectAssignment27a, staffProjectAssignment27b, staffProjectAssignment28,  staffProjectAssignment29,
		staffProjectAssignment30, staffProjectAssignment31a, staffProjectAssignment31b, staffProjectAssignment31c, staffProjectAssignment32, staffProjectAssignment33, staffProjectAssignment34, staffProjectAssignment37, staffProjectAssignment39, staffProjectAssignment40, staffProjectAssignment41, staffProjectAssignment42, staffProjectAssignment43, staffProjectAssignment44, staffProjectAssignment45, staffProjectAssignment46, staffProjectAssignment47, staffProjectAssignment48, staffProjectAssignment49,
		staffProjectAssignment50, staffProjectAssignment51, staffProjectAssignment52, staffProjectAssignment53, staffProjectAssignment54, staffProjectAssignment55, staffProjectAssignment56, staffProjectAssignment57	));
		
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffProjectAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client arval = ClientTest.insertAClient(ARVAL, entityManager);		
		Project aos = ProjectTest.insertAProject(AOS, VERSION_1, arval, entityManager);
		assertEquals(1, aos.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment47 = AssignmentTest.insertAssignment(ASSIGNMENT47, entityManager);
		assertEquals(1, assignment47.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getStaffProjectAssignments().size());		
		assertEquals(0, aos.getStaffProjectAssignments().size());
		assertEquals(0, assignment47.getStaffProjectAssignments().size());
		
		/**Prepare staff assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		aos.addStaffProjectAssignment(amt, assignment47);
		amt.addStaffProjectAssignment(aos, assignment47);
		assignment47.addStaffProjectAssignment(amt, aos);
				
		entityManager.merge(aos);
		entityManager.merge(amt);
		entityManager.merge(assignment47);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFPROJECTASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffProjectAssignments().size());		
		assertEquals(1, aos.getStaffProjectAssignments().size());
		assertEquals(1, assignment47.getStaffProjectAssignments().size());
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
		amt.addStaffProjectAssignment(eolis, assignment23); /***  <==== Throws EntityExistsException */
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

		/**Validate staff assignments*/
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
	public void testGetProjects() {
		fail("Not yet implemented");
	}
	
	public static Staff insertAStaff(String firstName, String lastName, EntityManager entityManager) {
		Staff staff = new Staff();
		staff.setName(firstName);
		staff.setLastname(lastName);
		assertEquals(0, staff.getStaffId());
		entityManager.persist(staff);
		entityManager.flush();
		assertThat(staff.getStaffId(), Matchers.greaterThan((long)0));
		return staff;
		
	}

}
