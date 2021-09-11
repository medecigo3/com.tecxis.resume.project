package com.tecxis.resume.domain.util;

import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.City.CITY_TABLE;
import static com.tecxis.resume.domain.Client.CLIENT_TABLE;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT12;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.BW_6_COURSE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT1_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT9_NAME;
import static com.tecxis.resume.domain.Constants.FRANCE;
import static com.tecxis.resume.domain.Constants.HOBBY;
import static com.tecxis.resume.domain.Constants.LONDON;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.PARIS;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.TIBCO;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.Country.COUNTRY_TABLE;
import static com.tecxis.resume.domain.Course.COURSE_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.Interest.INTEREST_TABLE;
import static com.tecxis.resume.domain.Location.LOCATION_TABLE;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static com.tecxis.resume.domain.Service.SERVICE_TABLE;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
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
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
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
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
@Commit
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_UNCOMMITTED)
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
	private CourseRepository courseRepository;
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
	public void testInsertAssignmentSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Utils.insertAssignment(ASSIGNMENT12, assignmentRepo);		
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACity() {
		/**Prepare test*/
		Country uk = Utils.insertACountry(UNITED_KINGDOM, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		Utils.insertACity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACityWithSpringJpaRepo() {
		/**Prepare test*/
		Country uk = Utils.insertACountry(UNITED_KINGDOM, countryRepo);		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		Utils.insertACity(LONDON, uk, cityRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAClient() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Utils.insertAClient(SAGEMCOM, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAClientWithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Utils.insertAClient(SAGEMCOM, clientRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContractServiceAgreement() {
		/**Prepare test*/
		Service muleEsbCons = Utils.insertAService(MULE_ESB_CONSULTANT, entityManager);
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);
		Contract accentureBarclaysContract = Utils.insertAContract(barclays, CONTRACT1_NAME, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		Utils.insertAContractServiceAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
	}

//	@Test
//	@Sql(
//		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
//		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContractServiceAgreementWithSpringJpaRepo() {
		/**Prepare test*/
		Service muleEsbCons = Utils.insertAService(MULE_ESB_CONSULTANT, serviceRepo);
		Client barclays = Utils.insertAClient(BARCLAYS, clientRepo);
		Contract accentureBarclaysContract = Utils.insertAContract(barclays, CONTRACT1_NAME, contractRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		Utils.insertAContractServiceAgreement(accentureBarclaysContract, muleEsbCons, contractServiceAgreementRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertAClient(AXELTIS, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Utils.insertAContract(axeltis, CONTRACT9_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
	
	}
	
//	@Test
//	@Sql(
//		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
//		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContractWithSpringJpaRepo() {
		/**Prepare test*/
		Client axeltis = Utils.insertAClient(AXELTIS, clientRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Utils.insertAContract(axeltis, CONTRACT9_NAME, contractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACountry() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Utils.insertACountry(UNITED_KINGDOM, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACountryWithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Utils.insertACountry(UNITED_KINGDOM, countryRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACourse() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		Utils.insertACourse(BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACourseWithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		Utils.insertACourse(BW_6_COURSE, courseRepository);
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
	}


	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract() {
		/**Prepare test*/
		Supplier alterna = Utils.insertASupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContractWithSpringJpaRepo() {
		/**Prepare test*/
		Supplier alterna = Utils.insertASupplier(ALTERNA,  supplierRepo);			
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, employmentContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAnInterest() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Utils.insertAnInterest(HOBBY, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAnInterestWithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Utils.insertAnInterest(HOBBY, interestRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertLocation() {
		/**Prepare test*/
		Country france = Utils.insertACountry(FRANCE, entityManager);
		City paris = Utils.insertACity(PARIS, france, entityManager);		
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertLocationWithSpringJpaRepo() {
		/**Prepare test*/
		Country france = Utils.insertACountry(FRANCE, countryRepo);
		City paris = Utils.insertACity(PARIS, france, cityRepo);		
		Client barclays = Utils.insertAClient(BARCLAYS, clientRepo);		
		Project adirProject = Utils.insertAProject(ADIR, VERSION_1, barclays, projectRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, locationRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAProject() {
		/**Prepare test*/
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Utils.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAProjectWithSpringJpaRepo() {
		/**Prepare test*/
		Client barclays = Utils.insertAClient(BARCLAYS, clientRepo);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Utils.insertAProject(ADIR, VERSION_1, barclays, projectRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAService() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Utils.insertAService(MULE_ESB_CONSULTANT, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAServiceWithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Utils.insertAService(MULE_ESB_CONSULTANT, serviceRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASkill() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Utils.insertASkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASkillWithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Utils.insertASkill(TIBCO, skillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffProjectAssignment() {
		/**Prepare test*/
		Client sagemcom = Utils.insertAClient(SAGEMCOM, entityManager);		
		Project ted = Utils.insertAProject(TED, VERSION_1, sagemcom, entityManager);
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Assignment assignment12 = Utils.insertAssignment(ASSIGNMENT12, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		Utils.insertAStaffProjectAssignment(ted, amt, assignment12, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
	}
	
//	@Test
//	@Sql(
//		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
//		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffProjectAssignmentWithSpringJpaRepo() {
		/**Prepare test*/
		Client sagemcom = Utils.insertAClient(SAGEMCOM, clientRepo);		
		Project ted = Utils.insertAProject(TED, VERSION_1, sagemcom, projectRepo);
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Assignment assignment12 = Utils.insertAssignment(ASSIGNMENT12, assignmentRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		Utils.insertAStaffProjectAssignment(ted, amt, assignment12, staffProjectAssignmentRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffSkill() {
		/**Prepare test*/
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Skill tibco = Utils.insertASkill(TIBCO, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		Utils.insertAStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
	}
	
//	@Test
//	@Sql(
//		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
//		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffSkillWithSpringJpaRepo() {
		/**Prepare test*/
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Skill tibco = Utils.insertASkill(TIBCO, skillRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		Utils.insertAStaffSkill(amt, tibco, staffSkillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffWithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplier() {		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Utils.insertASupplier(ALPHATRESS, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplierWithSpringJpaRepo() {		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Utils.insertASupplier(ALPHATRESS, supplierRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplyContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertAClient(AXELTIS, entityManager);		
		Contract accentureContract = Utils.insertAContract(axeltis, CONTRACT1_NAME, entityManager);
		Supplier alterna = Utils.insertASupplier(ALTERNA,  entityManager);		
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Utils.insertASupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
	}
	
//	@Test
//	@Sql(
//		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
//		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplyContractWithSpringJpaRepo() {
		/**Prepare test*/
		Client axeltis = Utils.insertAClient(AXELTIS, clientRepo);		
		Contract accentureContract = Utils.insertAContract(axeltis, CONTRACT1_NAME, contractRepo);
		Supplier alterna = Utils.insertASupplier(ALTERNA,  supplierRepo);		
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Utils.insertASupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, supplyContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
	}

}
