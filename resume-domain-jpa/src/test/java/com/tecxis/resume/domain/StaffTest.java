package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMESYS;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.AOS;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT1;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT10;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT11;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT12;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT13;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT14;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT15;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT16;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT17;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT18;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT19;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT2;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT20;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT21;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT22;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT23;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT24;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT25;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT26;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT27;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT28;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT29;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT3;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT30;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT31;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT32;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT33;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT34;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT37;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT39;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT4;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT40;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT41;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT42;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT43;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT44;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT45;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT46;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT47;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT48;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT49;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT5;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT50;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT51;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT52;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT53;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT54;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT55;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT56;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT57;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT6;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT7;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT8;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT9;
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
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffProjectAssignmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void getId() {
		Staff staff = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertThat(staff.getId(), Matchers.greaterThan((long)0));
	}
	
	@Test
	public void setId() {
		Staff staff = new Staff();
		assertEquals(0, staff.getId());
		staff.setId(1);
		assertEquals(1, staff.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());
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
		
		/**Prepare staff -> assignments*/
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
		
		
		/**Validate staff's -> staff assignments*/
		assertThat(amtProjectAssignments, 
		Matchers.containsInAnyOrder(
		staffProjectAssignment1, staffProjectAssignment2, staffProjectAssignment3, staffProjectAssignment4, staffProjectAssignment5, staffProjectAssignment6a, staffProjectAssignment6b, staffProjectAssignment7,  staffProjectAssignment8, staffProjectAssignment9, 
		staffProjectAssignment10, staffProjectAssignment11, staffProjectAssignment12, staffProjectAssignment13, staffProjectAssignment14, staffProjectAssignment15, staffProjectAssignment16, staffProjectAssignment17, staffProjectAssignment18, staffProjectAssignment19, 
		staffProjectAssignment20, staffProjectAssignment21, staffProjectAssignment22a, staffProjectAssignment22b, staffProjectAssignment23a, staffProjectAssignment23b, staffProjectAssignment23c, staffProjectAssignment24a, staffProjectAssignment24b, staffProjectAssignment25, staffProjectAssignment26, staffProjectAssignment27a, staffProjectAssignment27b, staffProjectAssignment28,  staffProjectAssignment29,
		staffProjectAssignment30, staffProjectAssignment31a, staffProjectAssignment31b, staffProjectAssignment31c, staffProjectAssignment32, staffProjectAssignment33, staffProjectAssignment34, staffProjectAssignment37, staffProjectAssignment39, staffProjectAssignment40, staffProjectAssignment41, staffProjectAssignment42, staffProjectAssignment43, staffProjectAssignment44, staffProjectAssignment45, staffProjectAssignment46, staffProjectAssignment47, staffProjectAssignment48, staffProjectAssignment49,
		staffProjectAssignment50, staffProjectAssignment51, staffProjectAssignment52, staffProjectAssignment53, staffProjectAssignment54, staffProjectAssignment55, staffProjectAssignment56, staffProjectAssignment57	));
		
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
		
		/**Validate Projects -> test*/
		assertEquals(EOLIS, eolis.getName());
		assertEquals(VERSION_1, eolis.getVersion());
		
		/**Prepare Staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		
		/**Validate Staff -> test*/
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
		StaffProjectAssignment newStaffProjectAssignment = new StaffProjectAssignment(eolis, amt, assignment23);
		
		/**Add a duplicate Staff and Project association**/		
		amt.addStaffProjectAssignment(newStaffProjectAssignment); /***  <==== Throws EntityExistsException */
	}
	
	
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	public void testRemoveStaffProjectAssignment() {		
		/**Fetch Project and validate Project -> StaffProjectAssignments*/
		Project  adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(6, adir.getStaffProjectAssignments().size());
		
		/**Fetch Assignment and validate Assignment -> StaffProjectAssignments*/			
		Assignment assignment3 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT3);
		assertEquals(1, assignment3.getStaffProjectAssignments().size());
				
		/**Fetch the Staff and validate Staff -> StaffProjectAssignments*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(62, amt.getStaffProjectAssignments().size());
		
		/**Find the target StaffProjectAssignment*/
		StaffProjectAssignment staleStaffProjectAssignment = staffProjectAssignmentRepo.findById(new StaffProjectAssignmentId(adir, amt, assignment3)).get();
		
		/**Validate table state pre-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Remove the target StaffProjectAssignment*/
		assertTrue(amt.removeStaffProjectAssignment(staleStaffProjectAssignment));	
		assertTrue(adir.removeStaffProjectAssignment(staleStaffProjectAssignment));
		assertTrue(assignment3.removeStaffProjectAssignment(staleStaffProjectAssignment));
				
		entityManager.remove(staleStaffProjectAssignment);
		entityManager.flush();
		
		/**Validate table state post-test*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Validate Project -> StaffProjectAssignments*/
		adir = projectRepo.findByNameAndVersion(ADIR, VERSION_1);	
		assertEquals(5, adir.getStaffProjectAssignments().size());
		
		/**Validate Assignment -> StaffProjectAssignments*/			
		assignment3 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT3);
		assertEquals(0, assignment3.getStaffProjectAssignments().size());
				
		/**Validate Staff -> StaffProjectAssignments*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(61, amt.getStaffProjectAssignments().size());		
		
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
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME,  BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, amt.getId());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Validate staff -> assignments*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(adir, amt, assignment1);
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		
		/**Prepare staff -> assignments*/		
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
		
		/**Validate staff -> assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id));
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetProjects() {
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		List <Project> amtProject = amt.getProjects();
		assertEquals(62, amtProject.size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		
		/***Remove Staff*/
		/**Tests initial state parent table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));		
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		  
		/**Detach interest from Staff and remove staff*/
		john.removeInterest(johnInterest);
		entityManager.merge(johnInterest);
		entityManager.remove(john);
		entityManager.flush();
		entityManager.clear();
		
		/**Test Staff is removed**/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		/**Test non-identifying Staff-> Interest children table didn't change*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		/**Tests state of children tables*/		
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));  /**O children for John Staff removed here*/
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE)); 	 /**O children for John Staff removed here*/
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		
		
		/**Test Interest -> Staff non-identifying relationship is set as NULL*/
		johnInterest = interestRepo.getInterestByDesc(JOHN_INTEREST);
		assertNotNull(johnInterest);		
		assertNull(johnInterest.getStaff());
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));		
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
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
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
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetCourses() {				
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setCourses(new ArrayList <Course> ());
		//To set Courses to a Staff see EnrolmentTest.testSetStaff()		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddCourse() {			
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.addCourse(new Course());
		//To add a Course to a Staff see EnrolmentTest.testSetStaff()
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveCourse() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.removeCourse(new Course());
		//To remove a Course to a Staff see EnrolmentTest.testSetStaff()
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void  testSetInterests() {				
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setInterests(new ArrayList <Interest> ());			
		//To set Interest to a Staff see InterestTest.testSetStaff()
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddInterest() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.addInterest(new Interest ());					
		//To add Interest into a Staff see InterestTest.testSetStaff()
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContracts() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		assertEquals(1, john.getSupplyContracts().size());
		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(13, amt.getSupplyContracts().size());
		
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		Contract newContract = new Contract();
		newContract.setName(newContractName);
		newContract.setClient(belfius);		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		/**New SupplyContract*/
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
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	// Target orphans in  SUPPLY_CONTRACT table	
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
		/**This sets new AMT's SupplyContracts and leaves orphans*/
		amt.setSupplyContracts(newSupplyContracts);
		entityManager.persist(newContract);
		entityManager.merge(amt);
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
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); // 1 new contract created in CONTRACT table. 
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		
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
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		Contract newContract = new Contract();
		newContract.setName(newContractName);
		newContract.setClient(belfius);		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		/**New SupplyContract*/
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
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Amend the new SupplyContract*/		
		amt.addSupplyContract(newSupplyContract);
		entityManager.persist(newContract);
		entityManager.merge(amt);
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
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); // 1 new contract created.
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		
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
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSupplyContract() {
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
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
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//1 child removed.	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		
		/**Validate stale SupplyContract doesn't exist*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		List <SupplyContract> supplyContracts = amt.getSupplyContracts();
		assertThat(supplyContracts, Matchers.not(Matchers.hasItem(staleSupplyContract)));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetEmploymentContracts() {
		/**Find target Staff*/		
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		
		assertEquals(5, amt.getEmploymentContracts().size());		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		
		/**Test parent Staff has no EmploymentContracts*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(0, amt.getEmploymentContracts().size());
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	

	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		
		/**Validate parent Staff has new EmploymentContract*/		
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		newEmploymentContracts = john.getEmploymentContracts();
		assertEquals(1, newEmploymentContracts.size());
		newEmploymentContract = newEmploymentContracts.get(0);
		assertEquals(accenture, newEmploymentContract.getSupplier());
		
	}

	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
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
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));			
		
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
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSkills() {
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(5, amt.getSkills().size());

	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaffSkills() {				
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setStaffSkills(new ArrayList <StaffSkill> ());
		//To set StaffSkills to a Staff see StaffSkillTest.testSetStaff()
	}
	
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetSkills() {		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		john.setSkills(new ArrayList <Skill> ());
		//To set Skills to a Staff see StaffSkillTest.testSetSkill()		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddSkill() {	
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		john.addSkill(new Skill());
		//To add Skills to a Staff see StaffSkillTest.testAddStaffSkill
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSkill() {		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		john.removeSkill(new Skill());	
		//To add Skills to a Staff see StaffSkillTest.testRemoveStaffSkill
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //AMT STAFF_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**HAS*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
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
		/**WORKS ON*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE)); // 62 children with STAFF_ID=1 removed from  table. 
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Remove the Staff*/		
		entityManager.remove(amt);
		entityManager.flush();
		entityManager.clear();
		
		/**See SQL cascadings applied to one-to-many relations*/
		/**STAFF 	-> 	ENROLMENT_CONTRACT 			CascadeType.ALL*/
		/**STAFF 	-> 	SUPPLY_CONTRACT 			CascadeType.ALL*/
		/**STAFF 	->	INTEREST (Non-Identifying)  CascadeType.ALL*/
		
		/**See SQL cascadings applied to many-to-many relations*/
		/**STAFF 	-> ENROLEMENT 	-> COURSE*/
		/**STAFF	-> STAFF_SKILL	-> SKILL */
		
		/**Cascadings in this sequence*/
		/**STAFF (P)  ------>  INTEREST (c)
		 *    ¦
		 *    --------------> STAFF_SKILL (c)
		 *    ¦
		 *    --------------> ENROLMENT (c)
		 *    ¦
		 *    --------------> EMPLOYMENT_CONTRACT (c) 
		 *    ¦
		 *    --------------> SUPPLY_CONTRACT (c)
		 *    ¦
		 *    --------------> STAFF_PROJECT_ASSIGNMENT (c)
		 *    
		 */
		
		/**HAS*/
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE)); // 1 child with STAFF_ID=1 removed from INTEREST table.
		/**Tests the initial state of the children table(s) from the Parent table*/
		/**USES*/
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE)); // 5 children with STAFF_ID=1 removed from STAFF_SKILL table.
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));	
		/**ENROLS*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE)); // 1 child with STAFF_ID=1 removed from ENROLMENT table. 
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		/**IS EMPLOYED*/
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE)); // 5 children with STAFF_ID=1 removed from EMPLOYMENT_CONTRACT table.  
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**WORKS IN*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); // 13 children with STAFF_ID=1 removed from SUPPLY_CONTRACT table. 
		/**WORKS ON*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE)); // 62 children with STAFF_ID=1 removed from  table. 
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
		/**Tests the initial state of the children table(s) from the Parent table*/		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));  
		/**Finally the state of Staff table (the parent)*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));  //1 Parent removed
		
	
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
		staff.toString();
	}

}
