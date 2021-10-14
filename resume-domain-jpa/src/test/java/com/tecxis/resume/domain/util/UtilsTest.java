package com.tecxis.resume.domain.util;

import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
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
import static com.tecxis.resume.domain.Constants.TASK12;
import static com.tecxis.resume.domain.Constants.TASK14;
import static com.tecxis.resume.domain.Constants.TED;
import static com.tecxis.resume.domain.Constants.TIBCO;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.Constants.UNITED_KINGDOM;
import static com.tecxis.resume.domain.Constants.VERSION_1;
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

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Enrolment;
import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.SchemaUtils;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.Task;
import com.tecxis.resume.domain.id.AgreementId;
import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.EnrolmentId;
import com.tecxis.resume.domain.id.LocationId;
import com.tecxis.resume.domain.id.StaffSkillId;
import com.tecxis.resume.domain.repository.AgreementRepository;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.CityRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.CountryRepository;
import com.tecxis.resume.domain.repository.CourseRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.EnrolmentRepository;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.LocationRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SkillRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.StaffSkillRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.repository.TaskRepository;


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
	private TaskRepository taskRepo;
	@Autowired
	private ClientRepository clientRepo;
	@Autowired
	private AgreementRepository agreementRepo;
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
	private AssignmentRepository assignmentRepo;
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
	public void testInsertTask() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));		
		Utils.insertTask(TASK12, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertTask_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));		
		Utils.insertTask(TASK12, taskRepo);		
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.TASK_TABLE));
				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCity() {
		/**Prepare test*/
		Country uk = Utils.insertCountry(UNITED_KINGDOM, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));
		Utils.insertCity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCity_WithSpringJpaRepo() {
		/**Prepare test*/
		Country uk = Utils.insertCountry(UNITED_KINGDOM, countryRepo);		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));
		Utils.insertCity(LONDON, uk, cityRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CITY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertClient() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.CLIENT_TABLE));
		Utils.insertClient(SAGEMCOM, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CLIENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertClient_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.CLIENT_TABLE));
		Utils.insertClient(SAGEMCOM, clientRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CLIENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAgreement() {
		/**Prepare test*/
		Service muleEsbCons = Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);
		Contract accentureBarclaysContract = Utils.insertContract(barclays, CONTRACT1_NAME, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));
		Utils.insertAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAgreement_WithSpringJpaRepo() {
		/**Prepare test*/
		Service muleEsbCons = Utils.insertService(MULE_ESB_CONSULTANT, serviceRepo);
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);
		Contract accentureBarclaysContract = Utils.insertContract(barclays, CONTRACT1_NAME, contractRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));
		Utils.insertAgreement(accentureBarclaysContract, muleEsbCons, agreementRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));
		Utils.insertContract(axeltis, CONTRACT9_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContract_WithSpringJpaRepo() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, clientRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));
		Utils.insertContract(axeltis, CONTRACT9_NAME, contractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));
	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCountry() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		Utils.insertCountry(UNITED_KINGDOM, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCountry_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
		Utils.insertCountry(UNITED_KINGDOM, countryRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COUNTRY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCourse() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		Utils.insertCourse(BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCourse_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		Utils.insertCourse(BW_6_COURSE, courseRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
	}


	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract() {
		/**Prepare test*/
		Supplier alterna = Utils.insertSupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract_WithSpringJpaRepo() {
		/**Prepare test*/
		Supplier alterna = Utils.insertSupplier(ALTERNA,  supplierRepo);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, employmentContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertInterest() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
		Utils.insertInterest(HOBBY, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertInterest_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
		Utils.insertInterest(HOBBY, interestRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
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
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE));		

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
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, locationRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.LOCATION_TABLE));		

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertProject() {
		/**Prepare test*/
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		Utils.insertProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));		
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertProject_WithSpringJpaRepo() {
		/**Prepare test*/
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));
		Utils.insertProject(ADIR, VERSION_1, barclays, projectRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.PROJECT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertService() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));
		Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertService_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));
		Utils.insertService(MULE_ESB_CONSULTANT, serviceRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SERVICE_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSkill() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
		Utils.insertSkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSkill_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
		Utils.insertSkill(TIBCO, skillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SKILL_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment() {
		/**Prepare test*/
		Client sagemcom = Utils.insertClient(SAGEMCOM, entityManager);		
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, entityManager);
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Task assignment12 = Utils.insertTask(TASK12, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
		Utils.insertAssignment(ted, amt, assignment12, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment_WithSpringJpaRepo() {
		/**Prepare test*/
		Client sagemcom = Utils.insertClient(SAGEMCOM, clientRepo);		
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, projectRepo);
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Task task12 = Utils.insertTask(TASK12, taskRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));
		Utils.insertAssignment(ted, amt, task12, assignmentRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ASSIGNMENT_TABLE));	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffSkill() {
		/**Prepare test*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Skill tibco = Utils.insertSkill(TIBCO, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffSkill_WithSpringJpaRepo() {
		/**Prepare test*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Skill tibco = Utils.insertSkill(TIBCO, skillRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, staffSkillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_SKILL_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaff_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSupplier() {		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
		Utils.insertSupplier(ALPHATRESS, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplier_WithSpringJpaRepo() {		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
		Utils.insertSupplier(ALPHATRESS, supplierRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));
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
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		Utils.insertSupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
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
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		Utils.insertSupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, supplyContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEnrolment() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		/**Insert a Staff*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		/**Insert a Course*/
		Course bw6 = Utils.insertCourse(BW_6_COURSE, entityManager);
		/**Insert Enrolment*/
		Utils.insertEnrolment(amt, bw6, entityManager);
		/**Validate*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEnrolment_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
		/**Insert a Staff*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		/**Insert a Course*/
		Course bw6 = Utils.insertCourse(BW_6_COURSE, courseRepo);
		/**Insert Enrolment*/
		Utils.insertEnrolment(amt, bw6, enrolmentRepo);
		/**Validate*/
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.ENROLMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteTask() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplate);		
		Task task12 = taskRepo.getTaskByDesc(TASK12);		
		Utils.removeTask(task12, entityManager);
		/**Validate table state post-test*/
		SchemaUtils.testStateAfterTask12Delete(jdbcTemplate);	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteTask_WithSpringJpaRepo() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplate);		
		Task task12 = taskRepo.getTaskByDesc(TASK12);		
		Utils.removeTask(task12, taskRepo);		
		/**Validate table state post-test*/
		SchemaUtils.testStateAfterTask12Delete(jdbcTemplate);		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCity() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplate);		
		/**Find City to remove*/
		City london = cityRepo.getCityByName(LONDON);
		Utils.removeCity(london, entityManager);
		/**Test post state tables*/
		SchemaUtils.testStateAfterLondonCityDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCity_WithSpringJpaRepo() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplate);		
		/**Find City to remove*/
		City london = cityRepo.getCityByName(LONDON);
		Utils.removeCity(london, cityRepo);
		/**Test post state tables*/
		SchemaUtils.testStateAfterLondonCityDelete(jdbcTemplate);

	}
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteClient() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		Utils.removeClient(axeltis, entityManager);
		/**Test Post state*/
		SchemaUtils.testStateAfterAxeltisClientDelete(jdbcTemplate);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteClient_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);		
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		Utils.removeClient(axeltis, clientRepo);		
		/**Test Post state*/
		SchemaUtils.testStateAfterAxeltisClientDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAgreement() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
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
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
		Utils.removeAgreement(axeltisFastConnectAgreement, entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfterAxeltisFastconnectAgreementDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAgreement_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
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
		Agreement axeltisFastconnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
		Utils.removeAgreement(axeltisFastconnectAgreement, agreementRepo);
		/**Test post state*/
		SchemaUtils.testStateAfterAxeltisFastconnectAgreementDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContract() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find and validate Contract to test*/
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Utils.removeContract(fastconnectMicropoleContract, entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfterContract5Delete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContract_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Utils.removeContract(fastconnectMicropoleContract, contractRepo);
		/**Test post state*/
		SchemaUtils.testStateAfterContract5Delete(jdbcTemplate);
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCourse() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		/**Remove course*/
		Utils.removeCourse(courses.get(0), entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfterBw6CourseDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCourse_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		/**Remove course*/
		Utils.removeCourse(courses.get(0), courseRepo);
		/**Test post state*/
		SchemaUtils.testStateAfterBw6CourseDelete(jdbcTemplate);
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCountry() {
		/***Test inital state before City delete*/ 
		SchemaUtils.testInitialState(jdbcTemplate);
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
		SchemaUtils.testStateAfterFranceCountryWithDetachedChildrenDelete(jdbcTemplate);	
		/**Find target Country to remove*/
		entityManager.clear();
		france = countryRepo.getCountryByName(FRANCE);
		/**Test target Country has no Country -> City associations*/
		assertEquals(0, france.getCities().size());
		Utils.removeCountry(france, entityManager);
		/***Test post state after Country delete*/ 
		SchemaUtils.testStateAfterFranceDelete(jdbcTemplate);
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCountry_WithSpringJpaRepo() {
		/***Test inital state before City delete*/ 
		SchemaUtils.testInitialState(jdbcTemplate);
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
		SchemaUtils.testStateAfterFranceCountryWithDetachedChildrenDelete(jdbcTemplate);	
		/**Find target Country to remove*/
		entityManager.clear();
		france = countryRepo.getCountryByName(FRANCE);
		/**Test target Country has no Country -> City associations*/
		assertEquals(0, france.getCities().size());
		Utils.removeCountry(france, countryRepo);
		/***Test post state after Country delete*/ 
		SchemaUtils.testStateAfterFranceDelete(jdbcTemplate);

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEmploymentContract() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target EmploymentContract to delete*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		Utils.removeEmploymentContract(johnAlhpatressEmploymentContract, entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfterJohnAlhpatressEmploymentContractDelete(jdbcTemplate);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEmploymentContract_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target EmploymentContract to delete*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		Utils.removeEmploymentContract(johnAlhpatressEmploymentContract, employmentContractRepo);
		SchemaUtils.testStateAfterJohnAlhpatressEmploymentContractDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteInterest() {
		SchemaUtils.testInitialState(jdbcTemplate);
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);	
		Utils.removeInterest(hobby, entityManager);	
		SchemaUtils.testStateAfterHobbyDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteInterest_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);	
		Utils.removeInterest(hobby, interestRepo);	
		SchemaUtils.testStateAfterHobbyDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteLocation() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
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
		SchemaUtils.testStateAfterMorningstartV1ProjectLocationDelete(jdbcTemplate);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteLocation_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
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
		SchemaUtils.testStateAfterMorningstartV1ProjectLocationDelete(jdbcTemplate);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteProject() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Utils.removeProject(morningstartV1Project, entityManager);
		/**Test post state after Project*/
		SchemaUtils.testStateAfterMorningstartV1ProjectDelete(jdbcTemplate);
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteProject_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Utils.removeProject(morningstartV1Project, projectRepo);
		/**Test post state after Project*/
		SchemaUtils.testStateAfterMorningstartV1ProjectDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteService() {
		SchemaUtils.testInitialState(jdbcTemplate);
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		Utils.removeService(bwService, entityManager);
		SchemaUtils.testStateAfterTibcoBwConsultantServiceDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteService_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		Utils.removeService(bwService, serviceRepo);
		SchemaUtils.testStateAfterTibcoBwConsultantServiceDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSkill() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		Utils.removeSkill(tibco, entityManager);
		SchemaUtils.testStateAfterTibcoSkillDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSkill_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		Utils.removeSkill(tibco, skillRepo);
		SchemaUtils.testStateAfterTibcoSkillDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAssignment() {
		/**Test initial state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Assignment*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task task14 = taskRepo.getTaskByDesc(TASK14);		
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());
		Assignment amtParcoursAssignment14 = assignmentRepo.findById(id).get();
		Utils.removeAssignment(amtParcoursAssignment14, entityManager);
		/**Test post state after Assignment*/
		SchemaUtils.testStateAfterAmtParcoursAssignment14AssignmentDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAssignment_WithSpringJpaRepo() {
		/**Test initial state*/
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Assignment*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task task14 = taskRepo.getTaskByDesc(TASK14);		
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());
		Assignment amtParcoursAssignment14 = assignmentRepo.findById(id).get();
		Utils.removeAssignment(amtParcoursAssignment14, assignmentRepo);
		/**Test post state after Assignment*/
		SchemaUtils.testStateAfterAmtParcoursAssignment14AssignmentDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffSkill() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target StaffSkill*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);	
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		Utils.removeStaffSkill(amtTibco, entityManager);
		SchemaUtils.testStateAfterAmtTibcoStaffSkillDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffSkill_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target StaffSkill*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);	
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		Utils.removeStaffSkill(amtTibco, staffSkillRepo);
		SchemaUtils.testStateAfterAmtTibcoStaffSkillDelete(jdbcTemplate);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaff() {
		SchemaUtils.testInitialState(jdbcTemplate);
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Utils.removeStaff(john, entityManager);		
		SchemaUtils.testStateAfterJohnStaffDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaff_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Utils.removeStaff(john, staffRepo);		
		SchemaUtils.testStateAfterJohnStaffDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplier() {
		SchemaUtils.testInitialState(jdbcTemplate);
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		Utils.removeSupplier(accenture, entityManager);	
		SchemaUtils.testStateAfterAccentureSupplierDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplier_WithSpringJpaRepo() {		
		SchemaUtils.testInitialState(jdbcTemplate);
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		Utils.removeSupplier(accenture, supplierRepo);	
		SchemaUtils.testStateAfterAccentureSupplierDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplyContract() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		Utils.removeSupplyContract(fastconnectMicropoleSupplyContract, entityManager);
		SchemaUtils.testStateAfterFastconnectMicropoleSupplyContractDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteASupplyContract_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		Utils.removeSupplyContract(fastconnectMicropoleSupplyContract, supplyContractRepo);
		SchemaUtils.testStateAfterFastconnectMicropoleSupplyContractDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEnrolment() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Enrolment to remove*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);		
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);		
		Course bwCourse = courses.get(0);
		/**Remove Enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		Utils.removeEnrolment(bwEnrolment, entityManager);
		SchemaUtils.testStateAfterBwEnrolmentDelete(jdbcTemplate);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEnrolment_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplate);
		/**Find target Enrolment to remove*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);		
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);		
		Course bwCourse = courses.get(0);
		/**Remove Enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		Utils.removeEnrolment(bwEnrolment, enrolmentRepo);
		SchemaUtils.testStateAfterBwEnrolmentDelete(jdbcTemplate);
	}
	
	
}
