package com.tecxis.resume.domain.util;

import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.City.CITY_TABLE;
import static com.tecxis.resume.domain.Client.CLIENT_TABLE;
import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT12;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT14;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.BW_6_COURSE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT1_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT5_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT9_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.HOBBY;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Constants.SHORT_BW_6_COURSE;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.TIBCO;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.domain.Country.COUNTRY_TABLE;
import static com.tecxis.resume.domain.Course.COURSE_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.Enrolment.ENROLMENT_TABLE;
import static com.tecxis.resume.domain.Interest.INTEREST_TABLE;
import static com.tecxis.resume.domain.Location.LOCATION_TABLE;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static com.tecxis.resume.domain.Service.SERVICE_TABLE;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.StaffSkill.STAFF_SKILL_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Enrolment;
import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffProjectAssignment;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.ContractServiceAgreementId;
import com.tecxis.resume.domain.id.EnrolmentId;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.id.StaffSkillId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.EnrolmentRepository;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SkillRepository;
import com.tecxis.resume.domain.repository.StaffProjectAssignmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.StaffSkillRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually (see the blog: https://www.marcobehler.com/2014/06/25/should-my-tests-be-transactional)
@SqlConfig(dataSource="dataSource")
public class UtilsTest {
	
	@PersistenceContext
	private EntityManager entityManager;	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private AssignmentRepository assignmentRepo;
	@Autowired
	private ClientRepository clientRepo;
	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;
	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private CourseRepository courseRepo;
	@Autowired
	private EmploymentContractRepository employmentContractRepo;
	@Autowired
	private SupplierRepository supplierRepo; 
	@Autowired
	private InterestRepository interestRepo;
	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private LocationRepository locationRepo;
	@Autowired
	private ServiceRepository serviceRepo;
	@Autowired
	private SkillRepository skillRepo;
	@Autowired
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
	@Autowired 
	private StaffRepository staffRepo;
	@Autowired 
	private StaffSkillRepository staffSkillRepo;
	@Autowired
	private SupplyContractRepository supplyContractRepo;
	@Autowired
	private EnrolmentRepository enrolmentRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Utils.insertAssignment(ASSIGNMENT12, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Utils.insertAssignment(ASSIGNMENT12, assignmentRepo);		
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCity() {
		/**Prepare test*/
		Country uk = Utils.insertCountry(UNITED_KINGDOM, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		Utils.insertCity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCity_WithSpringJpaRepo() {
		/**Prepare test*/
		Country uk = Utils.insertCountry(UNITED_KINGDOM, countryRepo);		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		Utils.insertCity(LONDON, uk, cityRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertClient() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Utils.insertClient(SAGEMCOM, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertClient_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Utils.insertClient(SAGEMCOM, clientRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContractServiceAgreement() {
		/**Prepare test*/
		Service muleEsbCons = Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);
		Contract accentureBarclaysContract = Utils.insertContract(barclays, CONTRACT1_NAME, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		Utils.insertContractServiceAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContractServiceAgreement_WithSpringJpaRepo() {
		/**Prepare test*/
		Service muleEsbCons = Utils.insertService(MULE_ESB_CONSULTANT, serviceRepo);
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);
		Contract accentureBarclaysContract = Utils.insertContract(barclays, CONTRACT1_NAME, contractRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		Utils.insertContractServiceAgreement(accentureBarclaysContract, muleEsbCons, contractServiceAgreementRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Utils.insertContract(axeltis, CONTRACT9_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContract_WithSpringJpaRepo() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, clientRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Utils.insertContract(axeltis, CONTRACT9_NAME, contractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCountry() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Utils.insertCountry(UNITED_KINGDOM, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCountry_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Utils.insertCountry(UNITED_KINGDOM, countryRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCourse() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		Utils.insertCourse(BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCourse_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		Utils.insertCourse(BW_6_COURSE, courseRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
	}


	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract() {
		/**Prepare test*/
		Supplier alterna = Utils.insertSupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract_WithSpringJpaRepo() {
		/**Prepare test*/
		Supplier alterna = Utils.insertSupplier(ALTERNA,  supplierRepo);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, employmentContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertInterest() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Utils.insertInterest(HOBBY, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertInterest_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Utils.insertInterest(HOBBY, interestRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertLocation() {
		/**Prepare test*/
		Country france = Utils.insertCountry(FRANCE, entityManager);
		City paris = Utils.insertCity(PARIS, france, entityManager);		
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertLocation_WithSpringJpaRepo() {
		/**Prepare test*/
		Country france = Utils.insertCountry(FRANCE, countryRepo);
		City paris = Utils.insertCity(PARIS, france, cityRepo);		
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);		
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, projectRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, locationRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertProject() {
		/**Prepare test*/
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertProject_WithSpringJpaRepo() {
		/**Prepare test*/
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Utils.insertProject(ADIR, VERSION_1, barclays, projectRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertService() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertService_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Utils.insertService(MULE_ESB_CONSULTANT, serviceRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSkill() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Utils.insertSkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSkill_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Utils.insertSkill(TIBCO, skillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffProjectAssignment() {
		/**Prepare test*/
		Client sagemcom = Utils.insertClient(SAGEMCOM, entityManager);		
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, entityManager);
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Assignment assignment12 = Utils.insertAssignment(ASSIGNMENT12, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		Utils.insertStaffProjectAssignment(ted, amt, assignment12, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffProjectAssignment_WithSpringJpaRepo() {
		/**Prepare test*/
		Client sagemcom = Utils.insertClient(SAGEMCOM, clientRepo);		
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, projectRepo);
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Assignment assignment12 = Utils.insertAssignment(ASSIGNMENT12, assignmentRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		Utils.insertStaffProjectAssignment(ted, amt, assignment12, staffProjectAssignmentRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffSkill() {
		/**Prepare test*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Skill tibco = Utils.insertSkill(TIBCO, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffSkill_WithSpringJpaRepo() {
		/**Prepare test*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Skill tibco = Utils.insertSkill(TIBCO, skillRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, staffSkillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaff_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSupplier() {		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Utils.insertSupplier(ALPHATRESS, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplier_WithSpringJpaRepo() {		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Utils.insertSupplier(ALPHATRESS, supplierRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSupplyContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);		
		Contract accentureContract = Utils.insertContract(axeltis, CONTRACT1_NAME, entityManager);
		Supplier alterna = Utils.insertSupplier(ALTERNA,  entityManager);		
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Utils.insertSupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSupplyContract_WithSpringJpaRepo() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, clientRepo);		
		Contract accentureContract = Utils.insertContract(axeltis, CONTRACT1_NAME, contractRepo);
		Supplier alterna = Utils.insertSupplier(ALTERNA,  supplierRepo);		
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Utils.insertSupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, supplyContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEnrolment() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		/**Insert a Staff*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		/**Insert a Course*/
		Course bw6 = Utils.insertCourse(BW_6_COURSE, entityManager);
		/**Insert Enrolment*/
		Utils.insertEnrolment(amt, bw6, entityManager);
		/**Validate*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEnrolment_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		/**Insert a Staff*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		/**Insert a Course*/
		Course bw6 = Utils.insertCourse(BW_6_COURSE, courseRepo);
		/**Insert Enrolment*/
		Utils.insertEnrolment(amt, bw6, enrolmentRepo);
		/**Validate*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAssignment() {
		/**Test init state tables*/
		testStateBeforeDelete(jdbcTemplate);		
		Assignment assignment12 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT12);		
		Utils.removeAssignment(assignment12, entityManager);
		/**Validate table state post-test*/
		testStateAfterAssignment12Delete(jdbcTemplate);	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAssignment_WithSpringJpaRepo() {
		/**Test init state tables*/
		testStateBeforeDelete(jdbcTemplate);		
		Assignment assignment12 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT12);		
		Utils.removeAssignment(assignment12, assignmentRepo);		
		/**Validate table state post-test*/
		testStateAfterAssignment12Delete(jdbcTemplate);		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCity() {
		/**Test init state tables*/
		testStateBeforeDelete(jdbcTemplate);		
		/**Find City to remove*/
		City london = cityRepo.getCityByName(LONDON);
		Utils.removeCity(london, entityManager);
		/**Test post state tables*/
		testStateAfterLondonCityDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCity_WithSpringJpaRepo() {
		/**Test init state tables*/
		testStateBeforeDelete(jdbcTemplate);		
		/**Find City to remove*/
		City london = cityRepo.getCityByName(LONDON);
		Utils.removeCity(london, cityRepo);
		/**Test post state tables*/
		testStateAfterLondonCityDelete(jdbcTemplate);

	}
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteClient() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		Utils.removeClient(axeltis, entityManager);
		/**Test Post state*/
		testStateAfterAxeltisClientDelete(jdbcTemplate);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteClient_WithSpringJpaRepo() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);		
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		Utils.removeClient(axeltis, clientRepo);		
		/**Test Post state*/
		testStateAfterAxeltisClientDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContractServiceAgreement() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find Client*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());		
		/**Find supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());				
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		ContractServiceAgreement axeltisFastConnectContractServiceAgreement = contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
		Utils.removeContractServiceAgreement(axeltisFastConnectContractServiceAgreement, entityManager);
		/**Test post state*/
		testStateAfterAxeltisFastconnectContractServiceAgreementDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContractServiceAgreement_WithSpringJpaRepo() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find Client*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());		
		/**Find supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());				
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		ContractServiceAgreement axeltisFastconnectContractServiceAgreement = contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
		Utils.removeContractServiceAgreement(axeltisFastconnectContractServiceAgreement, contractServiceAgreementRepo);
		/**Test post state*/
		testStateAfterAxeltisFastconnectContractServiceAgreementDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContract() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find and validate Contract to test*/
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Utils.removeContract(fastconnectMicropoleContract, entityManager);
		/**Test post state*/
		testStateAfterContract5Delete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContract_WithSpringJpaRepo() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Utils.removeContract(fastconnectMicropoleContract, contractRepo);
		/**Test post state*/
		testStateAfterContract5Delete(jdbcTemplate);
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCourse() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		/**Remove course*/
		Utils.removeCourse(courses.get(0), entityManager);
		/**Test post state*/
		testStateAfterBw6CourseDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCourse_WithSpringJpaRepo() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		/**Remove course*/
		Utils.removeCourse(courses.get(0), courseRepo);
		/**Test post state*/
		testStateAfterBw6CourseDelete(jdbcTemplate);
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCountry() {
		/***Test inital state before City delete*/ 
		testStateBeforeDelete(jdbcTemplate);
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		Country france = countryRepo.getCountryByName(FRANCE);
		List <City> franceCities = france.getCities();
		
		/** Detaches child references from target parent entity.*/
		franceCities.forEach(city -> {
			/**Remove stale City*/
			Utils.removeCity(city, entityManager); //Probably not the best approach here to delete, then insert new city. //TODO try generate SQL UPDATE City statement for instance with: city.setCountry(uk); uk.addCity(city)
			/**Creates new City with new host Country */
			City newCity = new City();
			CityId id = newCity.getId();
			id.setCityId(city.getId().getCityId()); //sets old id to the new City
			/**Sets city with new host Country*/
			newCity.setCountry(uk);
			newCity.setName(city.getName());			
			entityManager.persist(newCity);
			entityManager.flush();
		});
		
		/***Test inital state before City delete*/ 			
		testStateAfterFranceCountryWithDetachedChildrenDelete(jdbcTemplate);	
		/**Find target Country to remove*/
		entityManager.clear();
		france = countryRepo.getCountryByName(FRANCE);
		/**Test target Country has no Country -> City associations*/
		assertEquals(0, france.getCities().size());
		Utils.removeCountry(france, entityManager);
		/***Test post state after Country delete*/ 
		testStateAfterFranceDelete(jdbcTemplate);
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCountry_WithSpringJpaRepo() {
		/***Test inital state before City delete*/ 
		testStateBeforeDelete(jdbcTemplate);
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		Country france = countryRepo.getCountryByName(FRANCE);
		List <City> franceCities = france.getCities();
		
		/** Detaches child references from the target parent entity.*/
		franceCities.forEach(city -> {
			/**Remove stale City*/
			Utils.removeCity(city, cityRepo); //Probably not the best approach to delete, then insert new city. //TODO try generate SQL UPDATE City statement for instance with: city.setCountry(uk); uk.addCity(city)  or see testRemoveStaff() example
			/**Creates new City with new host Country */
			City newCity = new City();
			CityId id = newCity.getId();
			id.setCityId(city.getId().getCityId()); //sets old id to the new City
			/**Sets city with new host Country*/
			newCity.setCountry(uk);
			newCity.setName(city.getName());			
			entityManager.persist(newCity);
			entityManager.flush();
		});
		
		/***Test inital state before City delete*/ 			
		testStateAfterFranceCountryWithDetachedChildrenDelete(jdbcTemplate);	
		/**Find target Country to remove*/
		entityManager.clear();
		france = countryRepo.getCountryByName(FRANCE);
		/**Test target Country has no Country -> City associations*/
		assertEquals(0, france.getCities().size());
		Utils.removeCountry(france, countryRepo);
		/***Test post state after Country delete*/ 
		testStateAfterFranceDelete(jdbcTemplate);

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEmploymentContract() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find target EmploymentContract to delete*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		Utils.removeEmploymentContract(johnAlhpatressEmploymentContract, entityManager);
		/**Test post state*/
		testStateAfterjohnAlhpatressEmploymentContractDelete(jdbcTemplate);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEmploymentContract_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target EmploymentContract to delete*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		Utils.removeEmploymentContract(johnAlhpatressEmploymentContract, employmentContractRepo);
		testStateAfterjohnAlhpatressEmploymentContractDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteInterest() {
		testStateBeforeDelete(jdbcTemplate);
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);	
		Utils.removeInterest(hobby, entityManager);	
		testStateAfterHobbyDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteInterest_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);	
		Utils.removeInterest(hobby, interestRepo);	
		testStateAfterHobbyDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteLocation() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());			
		/**Find a City*/		
		City paris = cityRepo.getCityByName(PARIS);	
		/**Detach entities*/		
		entityManager.clear();
		/**Find Location to remove again*/
		morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		paris = cityRepo.getCityByName(PARIS);
		Location morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris.getId(), morningstartV1Project.getId())).get();
		/**Remove location*/
		Utils.removeLocation(morningstartV1ProjectLocation, entityManager);
		/**Test post state after delete Location*/
		testStateAfterMorningstartV1ProjectLocationDelete(jdbcTemplate);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteLocation_WithSpringJpaRepo() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		assertEquals(MORNINGSTAR, morningstartV1Project.getName());
		assertEquals(VERSION_1, morningstartV1Project.getVersion());			
		/**Find a City*/		
		City paris = cityRepo.getCityByName(PARIS);	
		/**Detach entities*/		
		entityManager.clear();
		/**Find Location to remove again*/
		morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		paris = cityRepo.getCityByName(PARIS);
		Location morningstartV1ProjectLocation = locationRepo.findById(new LocationId(paris.getId(), morningstartV1Project.getId())).get();
		/**Remove location*/
		Utils.removeLocation(morningstartV1ProjectLocation, locationRepo);
		/**Test post state after delete Location*/
		testStateAfterMorningstartV1ProjectLocationDelete(jdbcTemplate);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteProject() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Utils.removeProject(morningstartV1Project, entityManager);
		/**Test post state after Project*/
		testStateAfterMorningstartV1ProjectDelete(jdbcTemplate);
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteProject_WithSpringJpaRepo() {
		/**Test init state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Utils.removeProject(morningstartV1Project, projectRepo);
		/**Test post state after Project*/
		testStateAfterMorningstartV1ProjectDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteService() {
		testStateBeforeDelete(jdbcTemplate);
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		Utils.removeService(bwService, entityManager);
		testStateAfterTibcoBwConsultantServiceDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteService_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		Utils.removeService(bwService, serviceRepo);
		testStateAfterTibcoBwConsultantServiceDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSkill() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		Utils.removeSkill(tibco, entityManager);
		testStateAfterTibcoSkillDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSkill_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		Utils.removeSkill(tibco, skillRepo);
		testStateAfterTibcoSkillDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffProjectAssignment() {
		/**Test initial state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find target StaffProjectAssignment*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(parcours.getId(), amt.getId(), assignment14.getId());
		StaffProjectAssignment amtParcoursAssignment14 = staffProjectAssignmentRepo.findById(id).get();
		Utils.removeStaffProjectAssignment(amtParcoursAssignment14, entityManager);
		/**Test post state after StaffProjectAssignment*/
		testStateAfterAmtParcoursAssignment14StaffProjectAssignmentDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffProjectAssignment_WithSpringJpaRepo() {
		/**Test initial state*/
		testStateBeforeDelete(jdbcTemplate);
		/**Find target StaffProjectAssignment*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(parcours.getId(), amt.getId(), assignment14.getId());
		StaffProjectAssignment amtParcoursAssignment14 = staffProjectAssignmentRepo.findById(id).get();
		Utils.removeStaffProjectAssignment(amtParcoursAssignment14, staffProjectAssignmentRepo);
		/**Test post state after StaffProjectAssignment*/
		testStateAfterAmtParcoursAssignment14StaffProjectAssignmentDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffSkill() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target StaffSkill*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);	
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		Utils.removeStaffSkill(amtTibco, entityManager);
		testStateAfterAmtTibcoStaffSkillDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffSkill_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target StaffSkill*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);	
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		Utils.removeStaffSkill(amtTibco, staffSkillRepo);
		testStateAfterAmtTibcoStaffSkillDelete(jdbcTemplate);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaff() {
		testStateBeforeDelete(jdbcTemplate);
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Utils.removeStaff(john, entityManager);		
		testStateAfterJohnStaffDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaff_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Utils.removeStaff(john, staffRepo);		
		testStateAfterJohnStaffDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplier() {
		testStateBeforeDelete(jdbcTemplate);
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		Utils.removeSupplier(accenture, entityManager);	
		testStateAfterAccentureSupplierDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplier_WithSpringJpaRepo() {		
		testStateBeforeDelete(jdbcTemplate);
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		Utils.removeSupplier(accenture, supplierRepo);	
		testStateAfterAccentureSupplierDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplyContract() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		Utils.removeSupplyContract(fastconnectMicropoleSupplyContract, entityManager);
		testStateAfterFastconnectMicropoleSupplyContractDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteASupplyContract_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		Utils.removeSupplyContract(fastconnectMicropoleSupplyContract, supplyContractRepo);
		testStateAfterFastconnectMicropoleSupplyContractDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEnrolment() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target Enrolment to remove*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);		
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);		
		Course bwCourse = courses.get(0);
		/**Remove Enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		Utils.removeEnrolment(bwEnrolment, entityManager);
		testStateAfterBwEnrolmentDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEnrolment_WithSpringJpaRepo() {
		testStateBeforeDelete(jdbcTemplate);
		/**Find target Enrolment to remove*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);		
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);		
		Course bwCourse = courses.get(0);
		/**Remove Enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		Utils.removeEnrolment(bwEnrolment, enrolmentRepo);
		testStateAfterBwEnrolmentDelete(jdbcTemplate);
	}
	
	public static void testStateBeforeDelete(JdbcTemplate jdbcTemplate) {			
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));	
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));	
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));			
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));				
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));	
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, STAFF_SKILL_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(54, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 		
		assertEquals(6, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));			
	}
	
	public static void testStateAfterAxeltisClientDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); // 2 Client orphans removed
		assertEquals(47, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE)); // 16 Client orphans removed
		assertEquals(11, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE)); // 2 Client orphans removed 
		assertEquals(11, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); // 2 Client orphans removed
		assertEquals(11	, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); // 2 Client orphans removed
		assertEquals(11, countRowsInTable(jdbcTemplate, CLIENT_TABLE)); // 1 Client parent removed
		
	}
	
	public static void testStateAfterLondonCityDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(4, countRowsInTable(jdbcTemplate, CITY_TABLE)); // 1 City parent removed
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));  
		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 2 child City entities being removed 
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); 
	}
	
	
	public static void testStateAfterAssignment12Delete(JdbcTemplate jdbcTemplate) {
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));  // Cascaded to 1 child Assignmentremoved
		assertEquals(53, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));	//1 Assignment parent entity removed 
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}
	
	public static void testStateAfterAxeltisFastconnectContractServiceAgreementDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(12, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE)); //1 parent entity removed
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		assertEquals(6, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		
	}

	public static void testStateAfterContract5Delete(JdbcTemplate jdbcTemplate) {
		/**See SQL cascadings applied to one-to-many relations*/
		/**CONTRACT 	-> 		SUPPLY_CONTRACT					CascadeType.ALL*/
		/**CONTRACT 	-> 		CONTRACT_SERVICE_AGREEMENT		CascadeType.ALL*/
		
		/*** Cascadings in this sequence*/
		/**  CONTRACT (P) -> SUPPLY_CONTRACT (c) */	
		/**      |                               */
		/**      |                      		 */
		/**      v                               */
		/** CONTRACT_SERVICE_AGREEMENT (c)       */
		
		/**Tests the cascaded parent of the OneToMany association between Contract -> SupplyContract*/		
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//1 child with CONTRACT_ID='5' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Contract -> ContractServiceAgreement */
		assertEquals(12, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE)); //1 child with CONTRACT_ID='5' removed from the CONTRACT_SERVICE_AGREEMENT table. 		
		/**Tests post state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));  //1 child with CONTRACT_ID='5' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the SUPPLIER table. 
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//0 children previously removed from SUPPLIER table. That cascades to 0 children being removed from the EMPLOYMENT_CONTRACT table.		
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //0 children previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.		
	}
	
	public static void testStateAfterBw6CourseDelete(JdbcTemplate jdbcTemplate) {
		/**Test course was removed*/
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE)); //1 parent entity removed
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE)); //Cascaded 1 child Course entity being removed
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}
	
	public static void testStateAfterFranceDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE)); //1 City removed in previous remove transaction, 1 new City inserted = same no. of Cities
		assertEquals(2, countRowsInTable(jdbcTemplate, COUNTRY_TABLE)); // 1 Country parent entity removed
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed in previous remove transaction 
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); //No changes
	}

	public static void testStateAfterParisDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(4, countRowsInTable(jdbcTemplate, CITY_TABLE)); //1 parent entity removed
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed (13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
	}

	public static void testStateAfterFranceCountryWithDetachedChildrenDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(3, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //Cascaded 9 child City entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}
	
	public static void testStateAfterjohnAlhpatressEmploymentContractDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//1 entity removed				
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
	}
	
	public static void testStateAfterHobbyDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE)); //1 parent entity removed
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		
	}
	
	public static void testStateAfterMorningstartV1ProjectLocationDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); //1 entity removed
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
	}
	
	public static void testStateAfterMorningstartV1ProjectDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(53, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));  //Cascaded 10 child Project entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, LOCATION_TABLE)); 
		assertEquals(5, countRowsInTable(jdbcTemplate, CITY_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, PROJECT_TABLE)); //1 parent entity removed
		
	}
	
	public static void testStateAfterTibcoBwConsultantServiceDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(5, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		assertEquals(5, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	//Cascaded 8 child Service entities being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));					
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}	
	
	public static void testStateAfterTibcoSkillDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(6, countRowsInTable(jdbcTemplate, SKILL_TABLE));  //1 parent entity removed
		/***Test Skill DELETE many-to-many cascadings*/
		assertEquals(4, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE)); //Cascaded 1 child Skill entity being removed
		/**Test Staff hasn't changed*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
	}
	
	public static void testStateAfterAmtParcoursAssignment14StaffProjectAssignmentDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));//1 entity removed
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));			
	}
	
	public static void testStateAfterAmtTibcoStaffSkillDelete(JdbcTemplate jdbcTemplate) {		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(4, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE)); //1 entity removed
	}
	
	public static void testStateAfterJohnStaffDelete(JdbcTemplate jdbcTemplate) {		
		/**Tests initial state parent table*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //1 parent entity removed
		/**Tests initial state children tables*/
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE)); // Cascaded to 1 child Staff being removed
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));				
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));  // Cascaded to 1 child Staff being removed
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	// Cascaded to 1 child Employment being removed
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));  //Cascaded to 1 Child SupplyContract being removed
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	//Cascaded to 1 Child StaffProjectAssignment being removed
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		
	}
	
	public static void testStateAfterJohnStaffWithDetachedChildrenDelete(JdbcTemplate jdbcTemplate) {		
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
		
	}
	
	public static void testStateAfterAMtStaffDelete(JdbcTemplate jdbcTemplate) {		
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
	
	public static void testStateAfterAccentureSupplierDelete(JdbcTemplate jdbcTemplate) {	
		/**See SQL cascadings applied to one-to-many relations*/
		/**SUPPLIER 	-> SUPPLY_CONTRACT 				Cascade.REMOVE*/
		/**SUPPLIER 	-> EMPLOYMENT_CONTRACT 			Cascade.REMOVE*/

			
		/**Cascadings in this sequence*/
		/**  SUPPLIER (P) -> SUPPLY_CONTRACT (c) */	
		/**      |                               */
		/**      |                      		 */
		/**      v                               */
		/** EMPLOYMENT_CONTRACT (c)              */
		
		/**Tests post state of Suppliers table (the parent)*/
		assertEquals(4, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(11, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//3 children with SUPPLIER_ID = '1' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//1 child with SUPPLIER_ID = '1' removed from the EMPLOYMENT_CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between Contract -> SupplyContract*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); //3 children with SUPPLIER_ID = '1' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //1 child with with SUPPLIER_ID = '1' previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.
		/**Tests the cascaded children of the OneToMany association between Contract -> ContractServiceAgreement */
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE)); //0 parents previously removed from CONTRACT table. That cascades to 0 children removed from the CONTRACT_SERVICE_AGREEMENT table.
		
	}
	
	public static void testStateAfterFastconnectMicropoleSupplyContractDelete(JdbcTemplate jdbcTemplate) {	
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));//1 parent entity removed	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));			
	}
	
	public static void testStateAfterBwEnrolmentDelete(JdbcTemplate jdbcTemplate) {
		assertEquals(0, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));  //1 parent entity removed	
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //No Cascade REMOVE
		assertEquals(2, countRowsInTable(jdbcTemplate, COURSE_TABLE));	//No cascade REMOVE
		
	}
	
	
}
