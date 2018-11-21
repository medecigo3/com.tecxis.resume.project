package com.tecxis.resume;

import static com.tecxis.resume.StaffAssignmentTest.STAFFASSIGNMENT_TABLE;
import static com.tecxis.resume.StaffAssignmentTest.insertAStaffAssignment;
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
import static com.tecxis.resume.persistence.ProjectRepositoryTest.insertAProject;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
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

import com.tecxis.resume.persistence.AssignmentRepository;
import com.tecxis.resume.persistence.ProjectRepository;
import com.tecxis.resume.persistence.StaffAssignmentRepository;
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
	private StaffAssignmentRepository staffAssignmentRepo;
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffAssignments() {
		/**Prepare projects*/
		Project adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);
		assertNotNull(adir);
		assertEquals(ADIR, adir.getName());
		assertEquals(VERSION_1, adir.getVersion());		
		List <StaffAssignment> adirStaffAssignments = adir.getStaffAssignments();
		assertEquals(6, adirStaffAssignments.size());
		
		Project aos = projectRepo.findByNameAndVersion(AOS, VERSION_1);
		assertNotNull(aos);
		assertEquals(AOS, aos.getName());
		assertEquals(VERSION_1, aos.getVersion());		
		List <StaffAssignment> aosStaffAssignments = aos.getStaffAssignments();
		assertEquals(6, aosStaffAssignments.size());
		
		Project coc = projectRepo.findByNameAndVersion(CENTRE_DES_COMPETENCES, VERSION_1);
		assertNotNull(coc);
		assertEquals(CENTRE_DES_COMPETENCES, coc.getName());
		assertEquals(VERSION_1, coc.getVersion());
		
		List <StaffAssignment> cocStaffAssignments = coc.getStaffAssignments();
		assertEquals(5, cocStaffAssignments.size());
		
		Project dcsc = projectRepo.findByNameAndVersion(DCSC, VERSION_1);
		assertNotNull(dcsc);
		assertEquals(DCSC, dcsc.getName());
		assertEquals(VERSION_1, dcsc.getVersion());		
		List <StaffAssignment> dcscStaffAssignments = dcsc.getStaffAssignments();
		assertEquals(1, dcscStaffAssignments.size());
		
		Project eolis = projectRepo.findByNameAndVersion(EOLIS, VERSION_1);
		assertNotNull(eolis);
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		List <StaffAssignment>  eolisStaffAssignments = eolis.getStaffAssignments();
		assertEquals(5, eolisStaffAssignments.size());
				
		Project euroclear = projectRepo.findByNameAndVersion(EUROCLEAR_VERS_CALYPSO, VERSION_1);
		assertNotNull(euroclear);
		assertEquals(EUROCLEAR_VERS_CALYPSO, euroclear.getName());
		assertEquals(VERSION_1, euroclear.getVersion());
		List <StaffAssignment>  euroclearStaffAssignments = euroclear.getStaffAssignments();
		assertEquals(2, euroclearStaffAssignments.size());
		
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertNotNull(fortis);
		assertEquals(FORTIS, fortis.getName());
		assertEquals(VERSION_1, fortis.getVersion());
		List <StaffAssignment>  fortisStaffAssignments = fortis.getStaffAssignments();
		assertEquals(3, fortisStaffAssignments.size());
				
		Project morningstarv1 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertNotNull(morningstarv1);
		assertEquals(MORNINGSTAR, morningstarv1.getName());
		assertEquals(VERSION_1, morningstarv1.getVersion());
		List <StaffAssignment>  morningstarv1StaffAssignments = morningstarv1.getStaffAssignments();
		assertEquals(10, morningstarv1StaffAssignments.size());
		
		Project morningstarv2 = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_2);
		assertNotNull(morningstarv2);
		assertEquals(MORNINGSTAR, morningstarv2.getName());
		assertEquals(VERSION_2, morningstarv2.getVersion());
		List <StaffAssignment>  morningstarv2StaffAssignments = morningstarv2.getStaffAssignments();
		assertEquals(6, morningstarv2StaffAssignments.size());
		
		Project parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		assertNotNull(parcours);
		assertEquals(PARCOURS, parcours.getName());
		assertEquals(VERSION_1, parcours.getVersion());
		List <StaffAssignment>  parcoursStaffAssignments = parcours.getStaffAssignments();
		assertEquals(6, parcoursStaffAssignments.size());
		
		Project selenium = projectRepo.findByNameAndVersion(SELENIUM, VERSION_1);
		assertNotNull(selenium);
		assertEquals(SELENIUM, selenium.getName());
		assertEquals(VERSION_1, selenium.getVersion());
		List <StaffAssignment>  seleniumStaffAssignments = selenium.getStaffAssignments();
		assertEquals(3, seleniumStaffAssignments.size());
		
		Project sherpa = projectRepo.findByNameAndVersion(SHERPA, VERSION_1);
		assertNotNull(sherpa);
		assertEquals(SHERPA, sherpa.getName());
		assertEquals(VERSION_1, sherpa.getVersion());
		List <StaffAssignment>  sherpaStaffAssignments = sherpa.getStaffAssignments();
		assertEquals(6, sherpaStaffAssignments.size());
	
		Project ted = projectRepo.findByNameAndVersion(TED, VERSION_1);
		assertNotNull(ted);
		assertEquals(TED, ted.getName());
		assertEquals(VERSION_1, ted.getVersion());
		List <StaffAssignment>  tedStaffAssignments = ted.getStaffAssignments();
		assertEquals(4, tedStaffAssignments.size());
				
		/**Prepare staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertNotNull(amt);
		List <Project> amtProjects = amt.getProjects();
		assertEquals(62, amtProjects.size());
		List <StaffAssignment> amtAssignments = amt.getStaffAssignments();
		assertEquals(62, amtAssignments.size());
		
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
		assertEquals(1, assignment1.getStaffAssignments().size());
		assertEquals(1, assignment2.getStaffAssignments().size());
		assertEquals(1, assignment3.getStaffAssignments().size());
		assertEquals(1, assignment4.getStaffAssignments().size());
		assertEquals(1, assignment5.getStaffAssignments().size());
		assertEquals(2, assignment6.getStaffAssignments().size());
		assertEquals(1, assignment7.getStaffAssignments().size());
		assertEquals(1, assignment8.getStaffAssignments().size());
		assertEquals(1, assignment9.getStaffAssignments().size());
		assertEquals(1, assignment10.getStaffAssignments().size());
		assertEquals(1, assignment11.getStaffAssignments().size());
		assertEquals(1, assignment12.getStaffAssignments().size());
		assertEquals(1, assignment13.getStaffAssignments().size());
		assertEquals(1, assignment14.getStaffAssignments().size());
		assertEquals(1, assignment15.getStaffAssignments().size());
		assertEquals(1, assignment16.getStaffAssignments().size());
		assertEquals(1, assignment17.getStaffAssignments().size());
		assertEquals(1, assignment18.getStaffAssignments().size());
		assertEquals(1, assignment19.getStaffAssignments().size());
		assertEquals(1, assignment20.getStaffAssignments().size());
		assertEquals(1, assignment21.getStaffAssignments().size());		
		assertEquals(2, assignment22.getStaffAssignments().size());
		assertEquals(3, assignment23.getStaffAssignments().size());
		assertEquals(2, assignment24.getStaffAssignments().size());
		assertEquals(1, assignment25.getStaffAssignments().size());
		assertEquals(1, assignment26.getStaffAssignments().size());
		assertEquals(2, assignment27.getStaffAssignments().size());
		assertEquals(1, assignment28.getStaffAssignments().size());
		assertEquals(1, assignment29.getStaffAssignments().size());
		assertEquals(1, assignment30.getStaffAssignments().size());
		assertEquals(3, assignment31.getStaffAssignments().size());
		assertEquals(1, assignment32.getStaffAssignments().size());
		assertEquals(1, assignment33.getStaffAssignments().size());
		assertEquals(1, assignment34.getStaffAssignments().size());
		assertEquals(1, assignment37.getStaffAssignments().size());
		assertEquals(1, assignment39.getStaffAssignments().size());
		assertEquals(1, assignment40.getStaffAssignments().size());
		assertEquals(1, assignment41.getStaffAssignments().size());
		assertEquals(1, assignment42.getStaffAssignments().size());
		assertEquals(1, assignment43.getStaffAssignments().size());
		assertEquals(1, assignment44.getStaffAssignments().size());
		assertEquals(1, assignment45.getStaffAssignments().size());
		assertEquals(1, assignment46.getStaffAssignments().size());
		assertEquals(1, assignment47.getStaffAssignments().size());
		assertEquals(1, assignment48.getStaffAssignments().size());
		assertEquals(1, assignment49.getStaffAssignments().size());
		assertEquals(1, assignment50.getStaffAssignments().size());
		assertEquals(1, assignment51.getStaffAssignments().size());
		assertEquals(1, assignment52.getStaffAssignments().size());
		assertEquals(1, assignment53.getStaffAssignments().size());
		assertEquals(1, assignment54.getStaffAssignments().size());
		assertEquals(1, assignment55.getStaffAssignments().size());
		assertEquals(1, assignment56.getStaffAssignments().size());
		assertEquals(2, assignment57.getStaffAssignments().size());
		
		/**Prepare staff assignments*/
		StaffAssignment staffAssignment1 = staffAssignmentRepo.findById(new StaffAssignmentId(adir, amt, assignment1)).get();
		StaffAssignment staffAssignment2 = staffAssignmentRepo.findById(new StaffAssignmentId(adir, amt, assignment2)).get();
		StaffAssignment staffAssignment3 = staffAssignmentRepo.findById(new StaffAssignmentId(adir, amt, assignment3)).get();
		StaffAssignment staffAssignment4 = staffAssignmentRepo.findById(new StaffAssignmentId(adir, amt, assignment4)).get();
		StaffAssignment staffAssignment5 = staffAssignmentRepo.findById(new StaffAssignmentId(adir, amt, assignment5)).get();
		StaffAssignment staffAssignment6a = staffAssignmentRepo.findById(new StaffAssignmentId(adir, amt, assignment6)).get();
		StaffAssignment staffAssignment6b = staffAssignmentRepo.findById(new StaffAssignmentId(fortis, amt, assignment6)).get();
		StaffAssignment staffAssignment7 = staffAssignmentRepo.findById(new StaffAssignmentId(fortis, amt, assignment7)).get();
		StaffAssignment staffAssignment8 = staffAssignmentRepo.findById(new StaffAssignmentId(fortis, amt, assignment8)).get();
		StaffAssignment staffAssignment9 = staffAssignmentRepo.findById(new StaffAssignmentId(dcsc, amt, assignment9)).get();
		StaffAssignment staffAssignment10 = staffAssignmentRepo.findById(new StaffAssignmentId(ted, amt, assignment10)).get();
		StaffAssignment staffAssignment11 = staffAssignmentRepo.findById(new StaffAssignmentId(ted, amt, assignment11)).get();
		StaffAssignment staffAssignment12 = staffAssignmentRepo.findById(new StaffAssignmentId(ted, amt, assignment12)).get();
		StaffAssignment staffAssignment13 = staffAssignmentRepo.findById(new StaffAssignmentId(ted, amt, assignment13)).get();
		StaffAssignment staffAssignment14 = staffAssignmentRepo.findById(new StaffAssignmentId(parcours, amt, assignment14)).get();
		StaffAssignment staffAssignment15 = staffAssignmentRepo.findById(new StaffAssignmentId(parcours, amt, assignment15)).get();
		StaffAssignment staffAssignment16 = staffAssignmentRepo.findById(new StaffAssignmentId(parcours, amt, assignment16)).get();
		StaffAssignment staffAssignment17 = staffAssignmentRepo.findById(new StaffAssignmentId(parcours, amt, assignment17)).get();
		StaffAssignment staffAssignment18 = staffAssignmentRepo.findById(new StaffAssignmentId(parcours, amt, assignment18)).get();
		StaffAssignment staffAssignment19 = staffAssignmentRepo.findById(new StaffAssignmentId(parcours, amt, assignment19)).get();
		StaffAssignment staffAssignment20 = staffAssignmentRepo.findById(new StaffAssignmentId(euroclear, amt, assignment20)).get();
		StaffAssignment staffAssignment21 = staffAssignmentRepo.findById(new StaffAssignmentId(euroclear, amt, assignment21)).get();
		StaffAssignment staffAssignment22a = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment22)).get();
		StaffAssignment staffAssignment22b = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment22)).get();
		StaffAssignment staffAssignment23a = staffAssignmentRepo.findById(new StaffAssignmentId(eolis, amt, assignment23)).get();
		StaffAssignment staffAssignment23b = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment23)).get();
		StaffAssignment staffAssignment23c = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment23)).get();
		StaffAssignment staffAssignment24a = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment24)).get();
		StaffAssignment staffAssignment24b = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment24)).get();
		StaffAssignment staffAssignment25 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment25)).get();
		StaffAssignment staffAssignment26 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment26)).get();
		StaffAssignment staffAssignment27a = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment27)).get();
		StaffAssignment staffAssignment27b = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment27)).get();
		StaffAssignment staffAssignment28 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment28)).get();
		StaffAssignment staffAssignment29 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment29)).get();
		StaffAssignment staffAssignment30 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment30)).get();
		StaffAssignment staffAssignment31a = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv1, amt, assignment31)).get();
		StaffAssignment staffAssignment31b = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment31)).get();
		StaffAssignment staffAssignment31c = staffAssignmentRepo.findById(new StaffAssignmentId(eolis, amt, assignment31)).get();
		StaffAssignment staffAssignment32 = staffAssignmentRepo.findById(new StaffAssignmentId(eolis, amt, assignment32)).get();
		StaffAssignment staffAssignment33 = staffAssignmentRepo.findById(new StaffAssignmentId(eolis, amt, assignment33)).get();
		StaffAssignment staffAssignment34 = staffAssignmentRepo.findById(new StaffAssignmentId(eolis, amt, assignment34)).get();
		StaffAssignment staffAssignment37 = staffAssignmentRepo.findById(new StaffAssignmentId(morningstarv2, amt, assignment37)).get();		
		StaffAssignment staffAssignment39 = staffAssignmentRepo.findById(new StaffAssignmentId(coc, amt, assignment39)).get();		
		StaffAssignment staffAssignment40 = staffAssignmentRepo.findById(new StaffAssignmentId(coc, amt, assignment40)).get();
		StaffAssignment staffAssignment41 = staffAssignmentRepo.findById(new StaffAssignmentId(coc, amt, assignment41)).get();
		StaffAssignment staffAssignment42 = staffAssignmentRepo.findById(new StaffAssignmentId(coc, amt, assignment42)).get();
		StaffAssignment staffAssignment43 = staffAssignmentRepo.findById(new StaffAssignmentId(coc, amt, assignment43)).get();
		StaffAssignment staffAssignment44 = staffAssignmentRepo.findById(new StaffAssignmentId(aos, amt, assignment44)).get();
		StaffAssignment staffAssignment45 = staffAssignmentRepo.findById(new StaffAssignmentId(aos, amt, assignment45)).get();
		StaffAssignment staffAssignment46 = staffAssignmentRepo.findById(new StaffAssignmentId(aos, amt, assignment46)).get();
		StaffAssignment staffAssignment47 = staffAssignmentRepo.findById(new StaffAssignmentId(aos, amt, assignment47)).get();
		StaffAssignment staffAssignment48 = staffAssignmentRepo.findById(new StaffAssignmentId(aos, amt, assignment48)).get();
		StaffAssignment staffAssignment49 = staffAssignmentRepo.findById(new StaffAssignmentId(aos, amt, assignment49)).get();		
		StaffAssignment staffAssignment50 = staffAssignmentRepo.findById(new StaffAssignmentId(selenium, amt, assignment50)).get();
		StaffAssignment staffAssignment51 = staffAssignmentRepo.findById(new StaffAssignmentId(selenium, amt, assignment51)).get();
		StaffAssignment staffAssignment52 = staffAssignmentRepo.findById(new StaffAssignmentId(selenium, amt, assignment52)).get();
		StaffAssignment staffAssignment53 = staffAssignmentRepo.findById(new StaffAssignmentId(sherpa, amt, assignment53)).get();
		StaffAssignment staffAssignment54 = staffAssignmentRepo.findById(new StaffAssignmentId(sherpa, amt, assignment54)).get();
		StaffAssignment staffAssignment55 = staffAssignmentRepo.findById(new StaffAssignmentId(sherpa, amt, assignment55)).get();
		StaffAssignment staffAssignment56 = staffAssignmentRepo.findById(new StaffAssignmentId(sherpa, amt, assignment56)).get();
		StaffAssignment staffAssignment57 = staffAssignmentRepo.findById(new StaffAssignmentId(sherpa, amt, assignment57)).get();
		
		
		/**Validate staff's staff assignments*/
		assertThat(amtAssignments, 
		Matchers.containsInAnyOrder(
		staffAssignment1, staffAssignment2, staffAssignment3, staffAssignment4, staffAssignment5, staffAssignment6a, staffAssignment6b, staffAssignment7,  staffAssignment8, staffAssignment9, 
		staffAssignment10, staffAssignment11, staffAssignment12, staffAssignment13, staffAssignment14, staffAssignment15, staffAssignment16, staffAssignment17, staffAssignment18, staffAssignment19, 
		staffAssignment20, staffAssignment21, staffAssignment22a, staffAssignment22b, staffAssignment23a, staffAssignment23b, staffAssignment23c, staffAssignment24a, staffAssignment24b, staffAssignment25, staffAssignment26, staffAssignment27a, staffAssignment27b, staffAssignment28,  staffAssignment29,
		staffAssignment30, staffAssignment31a, staffAssignment31b, staffAssignment31c, staffAssignment32, staffAssignment33, staffAssignment34, staffAssignment37, staffAssignment39, staffAssignment40, staffAssignment41, staffAssignment42, staffAssignment43, staffAssignment44, staffAssignment45, staffAssignment46, staffAssignment47, staffAssignment48, staffAssignment49,
		staffAssignment50, staffAssignment51, staffAssignment52, staffAssignment53, staffAssignment54, staffAssignment55, staffAssignment56, staffAssignment57	));
		
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddStaffAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client arval = ClientTest.insertAClient(ARVAL, entityManager);		
		Project aos = insertAProject(AOS, VERSION_1, arval, entityManager);
		assertEquals(1, aos.getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment47 = AssignmentTest.insertAssignment(ASSIGNMENT47, entityManager);
		assertEquals(1, assignment47.getAssignmentId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/		
		assertEquals(0, amt.getStaffAssignments().size());		
		assertEquals(0, aos.getStaffAssignments().size());
		assertEquals(0, assignment47.getStaffAssignments().size());
		
		/**Prepare staff assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		StaffAssignment amtStaffAssignment = insertAStaffAssignment(aos, amt, assignment47, entityManager);
		aos.addStaffAssignment(amtStaffAssignment);
		amt.addStaffAssignment(amtStaffAssignment);
		assignment47.addStaffAssignment(amtStaffAssignment);
				
		entityManager.merge(aos);
		entityManager.merge(amt);
		entityManager.merge(assignment47);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		assertEquals(1, amt.getStaffAssignments().size());		
		assertEquals(1, aos.getStaffAssignments().size());
		assertEquals(1, assignment47.getStaffAssignments().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffAssignment() {
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);		
		StaffAssignmentId id = new StaffAssignmentId(parcours, amt, assignment14);	
		assertEquals(62, amt.getStaffAssignments().size());		
		assertEquals(6, parcours.getStaffAssignments().size());
		assertEquals(1, assignment14.getStaffAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();

		/**Validate staff assignments*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		StaffAssignment staffAssignment1 = staffAssignmentRepo.findById(id).get();
		assertNotNull(staffAssignment1);
		
		/**Remove staff assignment*/
		entityManager.remove(staffAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Validate staff assignments*/
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		assertNull(entityManager.find(StaffAssignment.class, id));
		parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		amt = staffRepo.getStaffLikeName(AMT_NAME);
		assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);	
		assertEquals(61, amt.getStaffAssignments().size());		
		assertEquals(5, parcours.getStaffAssignments().size());
		assertEquals(0, assignment14.getStaffAssignments().size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaffAssignments() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Project adir = insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getStaffId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = AssignmentTest.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getAssignmentId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));
		StaffAssignmentId id = new StaffAssignmentId(adir, amt, assignment1);
		assertNull(entityManager.find(StaffAssignment.class, id));
		
		/**Prepare staff assignments*/		
		StaffAssignment amtStaffAssignment = insertAStaffAssignment(adir, amt, assignment1, entityManager);		
		List <StaffAssignment> amtStaffAssignments = new ArrayList <> ();		
		amtStaffAssignments.add(amtStaffAssignment);
		adir.setStaffAssignment(amtStaffAssignments);
		assignment1.setStaffAssignment(amtStaffAssignments);
		amt.setStaffAssignment(amtStaffAssignments);				
		entityManager.merge(adir);
		entityManager.merge(amt);
		entityManager.merge(assignment1);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFFASSIGNMENT_TABLE));	
		assertNotNull(entityManager.find(StaffAssignment.class, id));
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
