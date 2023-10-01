package com.tecxis.resume.domain.util;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually (see the blog: https://www.marcobehler.com/2014/06/25/should-my-tests-be-transactional)
@SqlConfig(dataSource="dataSourceHelper")
public class UtilsTest {
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;	
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
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		Utils.insertTask(TASK12, Integer.valueOf(0), entityManager);//RES-72
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertTask_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));		
		Utils.insertTask(TASK12, Integer.valueOf(0), taskRepo);//RES-72
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.TASK_TABLE));
				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCity() {
		/**Prepare test*/
		Country uk = Utils.insertCountry(UNITED_KINGDOM, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		Utils.insertCity(LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCity_WithSpringJpaRepo() {
		/**Prepare test*/
		Country uk = Utils.insertCountry(UNITED_KINGDOM, countryRepo);		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
		Utils.insertCity(LONDON, uk, cityRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CITY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertClient() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		Utils.insertClient(SAGEMCOM, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertClient_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
		Utils.insertClient(SAGEMCOM, clientRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CLIENT_TABLE));
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
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
		Utils.insertAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));		
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
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
		Utils.insertAgreement(accentureBarclaysContract, muleEsbCons, agreementRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		Utils.insertContract(axeltis, CONTRACT9_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertContract_WithSpringJpaRepo() {
		/**Prepare test*/
		Client axeltis = Utils.insertClient(AXELTIS, clientRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		Utils.insertContract(axeltis, CONTRACT9_NAME, contractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCountry() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Utils.insertCountry(UNITED_KINGDOM, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCountry_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
		Utils.insertCountry(UNITED_KINGDOM, countryRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COUNTRY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCourse() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		Utils.insertCourse(BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertCourse_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		Utils.insertCourse(BW_6_COURSE, courseRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
	}


	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract() {
		/**Prepare test*/
		Supplier alterna = Utils.insertSupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract_WithSpringJpaRepo() {
		/**Prepare test*/
		Supplier alterna = Utils.insertSupplier(ALTERNA,  supplierRepo);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, employmentContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertInterest() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.INTEREST_TABLE));
		Utils.insertInterest(HOBBY, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.INTEREST_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertInterest_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.INTEREST_TABLE));
		Utils.insertInterest(HOBBY, interestRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.INTEREST_TABLE));
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
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, null, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));		

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
		Project adirProject = Utils.insertProject(ADIR, VERSION_1, barclays, null, projectRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, locationRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.LOCATION_TABLE));		

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertProject() {
		/**Prepare test*/
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Utils.insertProject(ADIR, VERSION_1, barclays, null, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));		
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertProject_WithSpringJpaRepo() {
		/**Prepare test*/
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));
		Utils.insertProject(ADIR, VERSION_1, barclays, null, projectRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.PROJECT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertService() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
		Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertService_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
		Utils.insertService(MULE_ESB_CONSULTANT, serviceRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSkill() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
		Utils.insertSkill(TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSkill_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
		Utils.insertSkill(TIBCO, skillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SKILL_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment() {
		/**Prepare test*/
		Client sagemcom = Utils.insertClient(SAGEMCOM, entityManager);		
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, null, entityManager);
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Task assignment12 = Utils.insertTask(TASK12, Integer.valueOf(0), entityManager);//RES-72
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		Utils.insertAssignment(ted, amt, assignment12, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment_WithSpringJpaRepo() {
		/**Prepare test*/
		Client sagemcom = Utils.insertClient(SAGEMCOM, clientRepo);		
		Project ted = Utils.insertProject(TED, VERSION_1, sagemcom, null, projectRepo);
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Task task12 = Utils.insertTask(TASK12, Integer.valueOf(0), taskRepo);//RES-72
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		Utils.insertAssignment(ted, amt, task12, assignmentRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffSkill() {
		/**Prepare test*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		Skill tibco = Utils.insertSkill(TIBCO, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffSkill_WithSpringJpaRepo() {
		/**Prepare test*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		Skill tibco = Utils.insertSkill(TIBCO, skillRepo);
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		Utils.insertStaffSkill(amt, tibco, staffSkillRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_SKILL_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaff_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertSupplier() {		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
		Utils.insertSupplier(ALPHATRESS, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplier_WithSpringJpaRepo() {		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
		Utils.insertSupplier(ALPHATRESS, supplierRepo);	
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
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
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		Utils.insertSupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLY_CONTRACT_TABLE));
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
		
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		Utils.insertSupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, supplyContractRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLY_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEnrolment() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ENROLMENT_TABLE));
		/**Insert a Staff*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		/**Insert a Course*/
		Course bw6 = Utils.insertCourse(BW_6_COURSE, entityManager);
		/**Insert Enrolment*/
		Utils.insertEnrolment(amt, bw6, entityManager);
		/**Validate*/
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ENROLMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEnrolment_WithSpringJpaRepo() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ENROLMENT_TABLE));
		/**Insert a Staff*/
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);
		/**Insert a Course*/
		Course bw6 = Utils.insertCourse(BW_6_COURSE, courseRepo);
		/**Insert Enrolment*/
		Utils.insertEnrolment(amt, bw6, enrolmentRepo);
		/**Validate*/
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.COURSE_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ENROLMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteTask() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);		
		Task task12 = taskRepo.getTaskByDesc(TASK12);		
		Utils.deleteTask(task12, entityManager);
		/**Validate table state post-test*/
		SchemaUtils.testStateAfter_Task_Delete(jdbcTemplateProxy);	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteTask_WithSpringJpaRepo() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);		
		Task task12 = taskRepo.getTaskByDesc(TASK12);		
		Utils.deleteTask(task12, taskRepo);		
		/**Validate table state post-test*/
		SchemaUtils.testStateAfter_Task_Delete(jdbcTemplateProxy);		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCity() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);		
		/**Find City to remove*/
		City london = cityRepo.getCityByName(LONDON);
		Utils.deleteCity(london, entityManager);
		/**Test post state tables*/
		SchemaUtils.testStateAfter_LondonCity_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCity_WithSpringJpaRepo() {
		/**Test init state tables*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);		
		/**Find City to remove*/
		City london = cityRepo.getCityByName(LONDON);
		Utils.deleteCity(london, cityRepo);
		/**Test post state tables*/
		SchemaUtils.testStateAfter_LondonCity_Delete(jdbcTemplateProxy);

	}
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteClient() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		Utils.deleteClient(axeltis, entityManager);
		/**Test Post state*/
		SchemaUtils.testStateAfter_ClientAxeltis_Delete(jdbcTemplateProxy);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteClient_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);		
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		Utils.deleteClient(axeltis, clientRepo);		
		/**Test Post state*/
		SchemaUtils.testStateAfter_ClientAxeltis_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAgreement() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
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
		Utils.deleteAgreement(axeltisFastConnectAgreement, entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfter_AgreementAxeltisFastconnect_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAgreement_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
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
		Utils.deleteAgreement(axeltisFastconnectAgreement, agreementRepo);
		/**Test post state*/
		SchemaUtils.testStateAfter_AgreementAxeltisFastconnect_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContract() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find and validate Contract to test*/
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Utils.deleteContract(fastconnectMicropoleContract, entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfter_ContractFastconnectMicropole_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteContract_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Utils.deleteContract(fastconnectMicropoleContract, contractRepo);
		/**Test post state*/
		SchemaUtils.testStateAfter_ContractFastconnectMicropole_Delete(jdbcTemplateProxy);
	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCourse() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		/**Remove course*/
		Utils.deleteCourse(courses.get(0), entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfter_CourseBw6_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCourse_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find course to remove*/
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);
		/**Remove course*/
		Utils.deleteCourse(courses.get(0), courseRepo);
		/**Test post state*/
		SchemaUtils.testStateAfter_CourseBw6_Delete(jdbcTemplateProxy);
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCountry() {
		/***Test inital state before City delete*/ 
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		Country france = countryRepo.getCountryByName(FRANCE);
		List <City> franceCities = france.getCities();
		
		/** Detaches child references from target parent entity.*/
		franceCities.forEach(city -> {
			/**Remove stale City*/
			Utils.deleteCity(city, entityManager); //Probably not the best approach here to delete, then insert new city. //TODO try generate SQL UPDATE City statement for instance with: city.setCountry(uk); uk.addCity(city)
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
		SchemaUtils.testStateAfter_CountryFrance_Delete_WithDetachedChildren(jdbcTemplateProxy);
		/**Find target Country to remove*/
		entityManager.clear();
		france = countryRepo.getCountryByName(FRANCE);
		/**Test target Country has no Country -> City associations*/
		assertEquals(0, france.getCities().size());
		Utils.deleteCountry(france, entityManager);
		/***Test post state after Country delete*/ 
		SchemaUtils.testStateAfter_CountryFrance_Delete(jdbcTemplateProxy);
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteCountry_WithSpringJpaRepo() {
		/***Test inital state before City delete*/ 
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Country uk = countryRepo.getCountryByName(UNITED_KINGDOM);
		Country france = countryRepo.getCountryByName(FRANCE);
		List <City> franceCities = france.getCities();
		
		/** Detaches child references from the target parent entity.*/
		franceCities.forEach(city -> {
			/**Remove stale City*/
			Utils.deleteCity(city, cityRepo); //Probably not the best approach to delete, then insert new city. //TODO try generate SQL UPDATE City statement for instance with: city.setCountry(uk); uk.addCity(city)  or see testRemoveStaff() example
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
		SchemaUtils.testStateAfter_CountryFrance_Delete_WithDetachedChildren(jdbcTemplateProxy);
		/**Find target Country to remove*/
		entityManager.clear();
		france = countryRepo.getCountryByName(FRANCE);
		/**Test target Country has no Country -> City associations*/
		assertEquals(0, france.getCities().size());
		Utils.deleteCountry(france, countryRepo);
		/***Test post state after Country delete*/ 
		SchemaUtils.testStateAfter_CountryFrance_Delete(jdbcTemplateProxy);

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEmploymentContract() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target EmploymentContract to delete*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		Utils.deleteEmploymentContract(johnAlhpatressEmploymentContract, entityManager);
		/**Test post state*/
		SchemaUtils.testStateAfter_EmploymentContractJohnAlhpatress_Delete(jdbcTemplateProxy);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEmploymentContract_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target EmploymentContract to delete*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		Utils.deleteEmploymentContract(johnAlhpatressEmploymentContract, employmentContractRepo);
		SchemaUtils.testStateAfter_EmploymentContractJohnAlhpatress_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteInterest() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);	
		Utils.deleteInterest(hobby, entityManager);	
		SchemaUtils.testStateAfter_Hobby_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteInterest_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);	
		Utils.deleteInterest(hobby, interestRepo);	
		SchemaUtils.testStateAfter_Hobby_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteLocation() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
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
		Utils.deleteLocation(morningstartV1ProjectLocation, entityManager);
		/**Test post state after delete Location*/
		SchemaUtils.testStateAfter_MorningstarV1Project_Locations_Delete(jdbcTemplateProxy);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteLocation_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
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
		Utils.deleteLocation(morningstartV1ProjectLocation, locationRepo);
		/**Test post state after delete Location*/
		SchemaUtils.testStateAfter_MorningstarV1Project_Locations_Delete(jdbcTemplateProxy);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteProject() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Utils.deleteProject(morningstartV1Project, entityManager);
		/**Test post state after Project*/
		SchemaUtils.testStateAfter_MorningstarV1Project_Delete(jdbcTemplateProxy);
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteProject_WithSpringJpaRepo() {
		/**Test init state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find a Project to remove*/
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Utils.deleteProject(morningstartV1Project, projectRepo);
		/**Test post state after Project*/
		SchemaUtils.testStateAfter_MorningstarV1Project_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteService() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		Utils.deleteService(bwService, entityManager);
		SchemaUtils.testStateAfter_ServiceTibcoBwConsultant_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteService_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		Utils.deleteService(bwService, serviceRepo);
		SchemaUtils.testStateAfter_ServiceTibcoBwConsultant_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSkill() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		Utils.deleteSkill(tibco, entityManager);
		SchemaUtils.testStateAfter_SkillTibco_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSkill_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Skill*/
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		Utils.deleteSkill(tibco, skillRepo);
		SchemaUtils.testStateAfter_SkillTibco_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAssignment() {
		/**Test initial state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Assignment*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task task14 = taskRepo.getTaskByDesc(TASK14);		
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());
		Assignment amtParcoursAssignment14 = assignmentRepo.findById(id).get();
		Utils.deleteAssignment(amtParcoursAssignment14, entityManager);
		/**Test post state after Assignment*/
		SchemaUtils.testStateAfter_AssignmentParcoursAmtTask14_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteAssignment_WithSpringJpaRepo() {
		/**Test initial state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Assignment*/
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Task task14 = taskRepo.getTaskByDesc(TASK14);		
		AssignmentId id = new AssignmentId(parcours.getId(), amt.getId(), task14.getId());
		Assignment amtParcoursAssignment14 = assignmentRepo.findById(id).get();
		Utils.deleteAssignment(amtParcoursAssignment14, assignmentRepo);
		/**Test post state after Assignment*/
		SchemaUtils.testStateAfter_AssignmentParcoursAmtTask14_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffSkill() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target StaffSkill*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);	
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		Utils.deleteStaffSkill(amtTibco, entityManager);
		SchemaUtils.testStateAfter_StaffSkillAmtTibco_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaffSkill_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target StaffSkill*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);	
		Skill tibco = skillRepo.getSkillByName(TIBCO);
		StaffSkill amtTibco = staffSkillRepo.findById(new StaffSkillId(amt.getId(), tibco.getId())).get();
		Utils.deleteStaffSkill(amtTibco, staffSkillRepo);
		SchemaUtils.testStateAfter_StaffSkillAmtTibco_Delete(jdbcTemplateProxy);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaff() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Utils.deleteStaff(john, entityManager);		
		SchemaUtils.testStateAfter_StaffJohn_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStaff_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Utils.deleteStaff(john, staffRepo);		
		SchemaUtils.testStateAfter_StaffJohn_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplier() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		Utils.deleteSupplier(accenture, entityManager);	
		SchemaUtils.testStateAfter_SupplierAccenture_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplier_WithSpringJpaRepo() {		
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		Utils.deleteSupplier(accenture, supplierRepo);	
		SchemaUtils.testStateAfter_SupplierAccenture_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteSupplyContract() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		Utils.deleteSupplyContract(fastconnectMicropoleSupplyContract, entityManager);
		SchemaUtils.testStateAfter_SupplyContractFastconnectMicropole_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteASupplyContract_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		Utils.deleteSupplyContract(fastconnectMicropoleSupplyContract, supplyContractRepo);
		SchemaUtils.testStateAfter_SupplyContractFastconnectMicropole_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEnrolment() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Enrolment to remove*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);		
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);		
		Course bwCourse = courses.get(0);
		/**Remove Enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		Utils.deleteEnrolment(bwEnrolment, entityManager);
		SchemaUtils.testStateAfter_EnrolmentBw_Delete(jdbcTemplateProxy);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteEnrolment_WithSpringJpaRepo() {
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Find target Enrolment to remove*/
		Staff amt = staffRepo.getStaffLikeLastName(AMT_LASTNAME);		
		List <Course> courses = courseRepo.getCourseLikeTitle(SHORT_BW_6_COURSE);		
		Course bwCourse = courses.get(0);
		/**Remove Enrolment*/
		Enrolment bwEnrolment = enrolmentRepo.findById(new EnrolmentId(amt.getId(), bwCourse.getId())).get();
		Utils.deleteEnrolment(bwEnrolment, enrolmentRepo);
		SchemaUtils.testStateAfter_EnrolmentBw_Delete(jdbcTemplateProxy);
	}
	
	@Test
	public void testIsAgreementValid() {			
		Contract contract = buildContract(CONTRACT_BARCLAYS_ID, buildClient(BARCLAYS, CLIENT_BARCLAYS_ID), CONTRACT1_NAME);//RES-10
		Service service = buildService(MULE_ESB_CONSULTANT);
		Agreement agreement = buildAgreement(contract, service);
		assertEquals(SUCCESS, isAgreementValid(agreement, CONTRACT1_NAME, MULE_ESB_CONSULTANT));
		assertEquals(CONTRACT_NAME_IS_NOT_VALID, isAgreementValid(agreement, "test", MULE_ESB_CONSULTANT));
		assertEquals(SERVICE_NAME_IS_NOT_VALID, isAgreementValid(agreement, CONTRACT1_NAME, "test"));
	}
	
	@Test
	public void testIsAssignmentValid() {
		Client client = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);		
		Project project = buildProject(PROJECT_ADIR_V1_ID, ADIR, VERSION_1, client, null, null);//RES-11
		Staff staff = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, BIRTHDATE);//RES-13
		Task task = buildTask(TASK1_ID, TASK1, Integer.valueOf(0));//RES-72
		Assignment assignment = buildAssignment(project, staff, task);
		assertEquals(SUCCESS, isAssignmentValid(assignment, ADIR, VERSION_1, BARCLAYS, AMT_NAME, AMT_LASTNAME, TASK1));
		assertNotEquals(SUCCESS, isAssignmentValid(assignment, "Test", VERSION_1, BARCLAYS, AMT_NAME, AMT_LASTNAME, TASK1));
		assertNotEquals(SUCCESS, isAssignmentValid(assignment, ADIR, "Test", BARCLAYS, AMT_NAME, AMT_LASTNAME, TASK1));
		assertNotEquals(SUCCESS, isAssignmentValid(assignment, ADIR, VERSION_1, BARCLAYS, "Test", AMT_LASTNAME, TASK1));
		assertNotEquals(SUCCESS, isAssignmentValid(assignment, ADIR, VERSION_1, BARCLAYS, AMT_NAME, "Test", TASK1));
		assertEquals(TASK_DESC_IS_NOT_VALID, isAssignmentValid(assignment, ADIR, VERSION_1, BARCLAYS, AMT_NAME, AMT_LASTNAME, "Test"));
		
	}


	@Test
	public void testIsCityValid(){//RES-65
		/**Build City*/
		City paris = buildCity(buildCityId(PARIS_ID, FRANCE_ID), PARIS);
		City testCity = buildCity(buildCityId(MANCHESTER_ID, UNITED_KINGDOM_ID), MANCHESTER);
		/**Build Country*/
		Country france = buildCountry(FRANCE_ID, FRANCE);
		paris.setCountry(france);
		Country testCountry = buildCountry(UNITED_KINGDOM_ID, UNITED_KINGDOM);
		testCity.setCountry(testCountry);
		/**Build Barclays client*/
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		Client testClient = buildClient(EULER_HERMES, CLIENT_HERMES_ID);
		/**Build Project*/
		Project adir = buildProject(PROJECT_ADIR_V1_ID, ADIR, VERSION_1, barclays, null, null);//RES-11
		Project testProject = buildProject(PROJECT_AOS_V1_ID, EOLIS, VERSION_1, testClient, null, null);
		/**Build Locations*/
		Location parisLocation = buildLocation(paris, adir);
		paris.setLocations(List.of(parisLocation));
		Location testLocation = buildLocation(testCity, testProject);

		assertEquals(SUCCESS, isCityValid(paris, PARIS, FRANCE, List.of(parisLocation)));
		assertEquals(CITY_NAME_IS_NOT_VALID, isCityValid(paris, MANCHESTER, FRANCE, List.of(parisLocation)));
		assertEquals(CITY_COUNTRY_IS_NOT_VALID, isCityValid(paris, PARIS, UNITED_KINGDOM, List.of(parisLocation)));
		assertEquals(CITY_LOCATIONS_ARE_NOT_VALID, isCityValid(paris, PARIS, FRANCE, List.of(testLocation)));
		assertEquals(CITY_SIZE_LOCATIONS_ARE_DIFFERENT, isCityValid(paris, PARIS, FRANCE, List.of(parisLocation, testLocation)));
		assertEquals(CITY_LOCATIONS_ARE_NULL, isCityValid(paris, PARIS, FRANCE, null));
	}

	@Test
	public void testIsClientValid(){//RES-64
		/**Build Client*/
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		Client testClient = buildClient(ARVAL, CLIENT_ARVAL_ID);
		/***Build Contracts*/
		Contract barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
		barclays.setContracts(List.of(barclaysContract1));
		Contract testContract = buildContract(CONTRACT_ARVAL_ID, testClient, CONTRACT11_NAME);
		testClient.setContracts(List.of(testContract));

		assertEquals(SUCCESS, isClientValid(barclays, BARCLAYS, List.of(barclaysContract1)));
		assertEquals(CLIENT_NAME_IS_NOT_VALID, isClientValid(barclays, ARVAL, List.of(barclaysContract1)));
		assertEquals(CLIENT_CONTRACTS_ARE_NOT_VALID, isClientValid(barclays, BARCLAYS, List.of(testContract)));
		assertEquals(CLIENT_CONTRACTS_ARE_NULL, isClientValid(barclays, BARCLAYS, null));
		assertEquals(CLIENT_SIZE_CONTRACTS_ARE_DIFFERENT, isClientValid(barclays, BARCLAYS, List.of(barclaysContract1, testContract)));

	}

	@Test
	public void testIsContractValid(){//RES-63
		/**Build Client*/
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		/**Build target Contract*/
		Contract barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
		barclays.setContracts(List.of(barclaysContract1));
		/**Build test Contract*/
		Contract testContract = buildContract(CONTRACT_ARVAL_ID, barclays, CONTRACT1_NAME);
		/**Build Services*/
		Service muleService = buildService(MULE_ESB_CONSULTANT);
		Service scmService = buildService(SCM_ASSOCIATE_DEVELOPPER);
		/**Build Agreements*/
		Agreement barclaysContractv1MuleAgreement = new Agreement(barclaysContract1, muleService);
		Agreement barclaysContractv1ScmAgreement = new Agreement(barclaysContract1, scmService);
		barclaysContract1.setAgreements(List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement));
		/**Build Supplier*/
		Supplier accenture = buildSupplier(SUPPLIER_ACCENTURE_ID, ACCENTURE_SUPPLIER);
		Supplier fastconnect = buildSupplier(SUPPLIER_FASTCONNECT_ID, FASTCONNECT);
		/**Build Staff*/
		Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, null);
		/**Build ContractSupplier*/
		SupplyContract barclaysContract1AmtAccentureContractSupplier = buildSupplyContract(barclaysContract1,amt, accenture);
		SupplyContract barclaysContract1AmtFastConnectContractSupplier =  buildSupplyContract(barclaysContract1, amt, fastconnect);
		barclaysContract1.setSupplyContracts(List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier));
		/**Build test SupplyContracts*/
		SupplyContract testContractSupplier =  buildSupplyContract(testContract, amt, fastconnect);


		/**Build test Client*/
		Client arval = buildClient(ARVAL, CLIENT_ARVAL_ID);
		/**Build test contract*/
		Contract arvalContract11 = buildContract(CONTRACT_ARVAL_ID, arval, CONTRACT11_NAME);
		/**Build test Services*/
		Service tibcoService = buildService(TIBCO_BW_CONSULTANT);
		Service j2eeService = buildService(J2EE_DEVELOPPER);
		/**Build test Agreements*/
		Agreement arvalContract11TibcoAgreement = new Agreement(arvalContract11, tibcoService);
		Agreement arvalContract11J2eeAgreement = new Agreement(arvalContract11, j2eeService);
		/**Build test Suppliers*/
		Supplier amesys = buildSupplier(SUPPLIER_AMESYS_ID, AMESYS);
		Supplier alterna = buildSupplier(SUPPLIER_ALTERNA_ID, ALTERNA);
		/**Build test SupplyContracts*/
		SupplyContract barclaysContract1AmtAmesysContractSupplier = buildSupplyContract(arvalContract11, amt, amesys);
		SupplyContract barclaysContract1AmtAlternaContractSupplier = buildSupplyContract(arvalContract11, amt, alterna);
		arvalContract11.setSupplyContracts(List.of(barclaysContract1AmtAmesysContractSupplier, barclaysContract1AmtAlternaContractSupplier));

		//Test Contract is valid
		assertEquals(SUCCESS, isContractValid(barclaysContract1, CONTRACT_BARCLAYS_ID, barclays, List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement), List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier)));
		assertEquals(CONTRACT_ID_IS_NOT_VALID, isContractValid(barclaysContract1, CONTRACT_AGEAS_ID, barclays, List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement), List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier)));
		assertEquals(CONTRACT_CLIENT_IS_NOT_VALID, isContractValid(barclaysContract1, CONTRACT_BARCLAYS_ID, arval, List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement), List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier)));
		assertEquals(CONTRACT_AGREEMENTS_ARE_NOT_VALID, isContractValid(barclaysContract1, CONTRACT_BARCLAYS_ID, barclays, List.of(arvalContract11J2eeAgreement, arvalContract11TibcoAgreement), List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier)));
		assertEquals(CONTRACT_AGREEMENTS_ARE_NULL, isContractValid(barclaysContract1, CONTRACT_BARCLAYS_ID, barclays, null, List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier)));
		assertEquals(CONTRACT_SIZE_AGREEMENTS_ARE_DIFFERENT, isContractValid(barclaysContract1, CONTRACT_BARCLAYS_ID, barclays, List.of(barclaysContractv1ScmAgreement), List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier)));
		assertEquals(CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID, isContractValid(barclaysContract1, CONTRACT_BARCLAYS_ID, barclays, List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement), List.of(testContractSupplier, barclaysContract1AmtAccentureContractSupplier)));
		assertEquals(CONTRACT_SUPPLYCONTRACTS_ARE_NULL, isContractValid(barclaysContract1, CONTRACT_BARCLAYS_ID, barclays, List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement), null));

	}
	@Test
	public void testIsCountryValid(){//RES-62
		/**Build Barclays client*/
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		/**Build ADIR project*/
		Project adir = buildProject(PROJECT_ADIR_V1_ID, ADIR, VERSION_1, barclays, null, null);//RES-11
		/**Build London city*/
		City london = buildCity(buildCityId(LONDON_ID, UNITED_KINGDOM_ID), LONDON);
		Country uk = buildCountry(UNITED_KINGDOM_ID, UNITED_KINGDOM);
		london.setCountry(uk);
		Location londonLocation = buildLocation(london, adir);
		london.setLocations(List.of(londonLocation));
		/**Build Paris city*/
		City paris = buildCity(buildCityId(PARIS_ID, FRANCE_ID), PARIS);
		Country france = buildCountry(FRANCE_ID, FRANCE);
		france.setCities(List.of(paris));
		paris.setCountry(france);
		Location parisLocation = buildLocation(paris, adir);
		paris.setLocations(List.of(parisLocation));

		assertEquals(SUCCESS, isCountryValid(france, FRANCE, List.of(paris)));
		assertEquals(COUNTRY_NAME_IS_NOT_VALID, isCountryValid(france, UNITED_KINGDOM, List.of(paris)));
		assertEquals(COUNTRY_CITIES_ARE_NOT_VALID, isCountryValid(france, FRANCE, List.of(london)));
		assertEquals(COUNTRY_SIZE_CITIES_ARE_DIFFERENT, isCountryValid(france, FRANCE, List.of(paris, london)));
		assertEquals(COUNTRY_CITIES_ARE_NULL, isCountryValid(france, FRANCE, null));
	}
	@Test
	public void testIsProjectValid(){//RES-61
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		Client testClient  = buildClient(AGEAS, CLIENT_AGEAS_ID);
		Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, BIRTHDATE);//RES-13
		Task task1 = buildTask(TASK1_ID, TASK1, Integer.valueOf(0));//RES-72
		Task task2 = buildTask(TASK2_ID, TASK2, Integer.valueOf(0));//RES-72
		Task testTask1 = buildTask(TASK3_ID, TASK3, Integer.valueOf(0));//RES-72
		Task testTask2 = buildTask(TASK4_ID, TASK4, Integer.valueOf(0));//RES-72
		Project adir = buildProject(PROJECT_ADIR_V1_ID, ADIR, VERSION_1, barclays, null, null);//RES-11
		Assignment assignment1 = buildAssignment(adir, amt, task1);
		Assignment assignment2 = buildAssignment(adir, amt, task2);
		adir.setAssignments(List.of(assignment1, assignment2));
		Assignment testAssignment1 = buildAssignment(adir, amt, testTask1);
		Assignment testAssignment2 = buildAssignment(adir, amt, testTask2);

		City manchester = buildCity(new CityId(MANCHESTER_ID, UNITED_KINGDOM_ID), MANCHESTER);
		Location manchesterLocation = buildLocation(manchester, adir);
		adir.setLocations(List.of(manchesterLocation));
		City testCity = buildCity(new CityId(PARIS_ID, FRANCE_ID), PARIS);
		Location testLocation = buildLocation(testCity, adir);


		assertEquals(SUCCESS, isProjectValid(adir, ADIR, VERSION_1, List.of(manchesterLocation), barclays, List.of(assignment1, assignment2)));
		assertEquals(PROJECT_NAME_IS_NOT_VALID, isProjectValid(adir, FORTIS, VERSION_1, List.of(manchesterLocation), barclays, List.of(assignment1, assignment2)));
		assertEquals(PROJECT_VERSION_IS_NOT_VALID, isProjectValid(adir, ADIR, VERSION_2, List.of(manchesterLocation), barclays, List.of(assignment1, assignment2)));
		assertEquals(PROJECT_CLIENT_IS_NOT_VALID, isProjectValid(adir, ADIR, VERSION_1, List.of(manchesterLocation), testClient, List.of(assignment1, assignment2)));
		assertEquals(PROJECT_LOCATIONS_ARE_NOT_VALID, isProjectValid(adir, ADIR, VERSION_1, List.of(testLocation), barclays, List.of(assignment1, assignment2)));
		assertEquals(PROJECT_LOCATIONS_ARE_NULL, isProjectValid(adir, ADIR, VERSION_1, null, barclays, List.of(assignment1, assignment2)));
		assertEquals(PROJECT_SIZE_LOCATIONS_ARE_DIFFERENT, isProjectValid(adir, ADIR, VERSION_1, List.of(manchesterLocation, testLocation), barclays, List.of(assignment1, assignment2)));
		assertEquals(PROJECT_ASSIGNMENTS_ARE_NULL, isProjectValid(adir, ADIR, VERSION_1, List.of(manchesterLocation), barclays, null));
		assertEquals(PROJECT_SIZE_ASSIGNMENTS_ARE_DIFFERENT, isProjectValid(adir, ADIR, VERSION_1, List.of(manchesterLocation), barclays, List.of(assignment1)));
		assertEquals(PROJECT_ASSIGNMENTS_ARE_NOT_VALID, isProjectValid(adir, ADIR, VERSION_1, List.of(manchesterLocation), barclays, List.of(testAssignment1, testAssignment2)));
	}
	@Test
	public void testIsSupplierValid(){//RES-58
		Supplier accenture = buildSupplier(SUPPLIER_ACCENTURE_ID, ACCENTURE_SUPPLIER);
		Supplier fastconnect = buildSupplier(SUPPLIER_FASTCONNECT_ID, FASTCONNECT);//Test supplier
		/**Build Staff*/
		Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, null);
		Staff testStaff = buildStaff(STAFF_JOHN_ID, JOHN_NAME, JOHN_LASTNAME, null);//Test Staff
		/**Build Client*/
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		Client testClient = buildClient(AXELTIS, CLIENT_AXELTIS_ID);
		/**Build Contract*/
		Contract barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
		Contract testContract1= buildContract(CONTRACT_AXELTIS_ID1, testClient, CONTRACT7_NAME);//Test Contract

		/**Build ContractSupplier*/
		SupplyContract barclaysContract1AmtAccentureContractSupplier = buildSupplyContract(barclaysContract1, amt, accenture);
		SupplyContract barclaysContract1AmtFastConnectContractSupplier = buildSupplyContract(barclaysContract1, amt, fastconnect);
		accenture.setSupplyContracts(List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier));
		SupplyContract testSupplyContract1 =  buildSupplyContract(barclaysContract1, testStaff, fastconnect); //Test SupplyContract
		SupplyContract testSupplyContract2 =  buildSupplyContract(testContract1, testStaff, fastconnect);//Test SupplyContract
		/**Build EmploymentContract*/
		EmploymentContract amtAccentureEmploymentContract = buildEmploymentContract(AMT_ACCENTURE_EMPLOYMENT_CONTRACTID, accenture, amt, AMT_ACCENTURE_EMPLOYMENT_STARTDATE);
		accenture.setEmploymentContracts(List.of(amtAccentureEmploymentContract));
		EmploymentContract  testEmploymentContract = buildEmploymentContract(AMT_ALTERNA_EMPLOYMENT_CONTRACT_ID, fastconnect, testStaff, JOHN_ALPHATRESS_EMPLOYMENT_ENDDATE); //Test EmploymentContract

		assertEquals(SUCCESS, isSupplierValid(accenture, ACCENTURE_SUPPLIER,  List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier), List.of(amtAccentureEmploymentContract)));
		assertEquals(SUPPLIER_NAME_IS_NOT_VALID, isSupplierValid(accenture, FASTCONNECT,  List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier), List.of(amtAccentureEmploymentContract)));
		assertEquals(SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NOT_VALID, isSupplierValid(accenture, ACCENTURE_SUPPLIER,  List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier), List.of(testEmploymentContract)));
		assertEquals(SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NULL, isSupplierValid(accenture, ACCENTURE_SUPPLIER,  List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier), null));
		assertEquals(SUPPLIER_SIZE_EMPLOYMENTCONTRACTS_ARE_DIFFERENT, isSupplierValid(accenture, ACCENTURE_SUPPLIER,  List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier), List.of(amtAccentureEmploymentContract, testEmploymentContract)));
		assertEquals(SUPPLIER_SUPPLYCONTRACTS_ARE_NOT_VALID, isSupplierValid(accenture, ACCENTURE_SUPPLIER,  List.of(testSupplyContract1, testSupplyContract2), List.of(amtAccentureEmploymentContract)));
		assertEquals(SUPPLIER_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT, isSupplierValid(accenture, ACCENTURE_SUPPLIER,  List.of(barclaysContract1AmtFastConnectContractSupplier), List.of(amtAccentureEmploymentContract)));


	}
	@Test
	public void testIsSupplyContractValid(){//RES-60
		/**Build Clients*/
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		/**BuildContracts*/
		Contract barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
		Contract testContract = buildContract(CONTRACT_ARVAL_ID, barclays, CONTRACT1_NAME);
		/**Build Staff*/
		Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, null);
		/**Build Suppliers*/
		Supplier fastconnect = buildSupplier(SUPPLIER_FASTCONNECT_ID, FASTCONNECT);
		Supplier accenture = buildSupplier(SUPPLIER_ACCENTURE_ID, ACCENTURE_SUPPLIER);
		/**Build ContractSuppliers*/
		SupplyContract barclaysContract1AmtAccentureContractSupplier = buildSupplyContract(barclaysContract1,amt, accenture);
		assertEquals(SUCCESS, isSupplyContractValid(barclaysContract1AmtAccentureContractSupplier, accenture, barclaysContract1, null, null));
		barclaysContract1AmtAccentureContractSupplier.setStartDate(CONTRACT1_STARTDATE);
		assertEquals(SUCCESS, isSupplyContractValid(barclaysContract1AmtAccentureContractSupplier, accenture, barclaysContract1, CONTRACT1_STARTDATE, null));
		barclaysContract1AmtAccentureContractSupplier.setEndDate(CONTRACT2_ENDDATE);
		assertEquals(SUCCESS, isSupplyContractValid(barclaysContract1AmtAccentureContractSupplier, accenture, barclaysContract1, CONTRACT1_STARTDATE, CONTRACT2_ENDDATE));
		assertEquals(SUPPLYCONTRACT_CONTRACT_IS_NOT_VALID, isSupplyContractValid(barclaysContract1AmtAccentureContractSupplier, accenture, testContract, CONTRACT1_STARTDATE, CONTRACT2_ENDDATE));
		assertEquals(SUPPLYCONTRACT_SUPPLIER_IS_NOT_VALID, isSupplyContractValid(barclaysContract1AmtAccentureContractSupplier, fastconnect, barclaysContract1, CONTRACT1_STARTDATE, CONTRACT2_ENDDATE));
		assertEquals(SUPPLYCONTRACT_STARTDATE_NOT_VALID, isSupplyContractValid(barclaysContract1AmtAccentureContractSupplier, accenture, barclaysContract1, CONTRACT2_STARTDATE, CONTRACT2_ENDDATE));
		assertEquals(SUPPLYCONTRACT_ENDDATE_NOT_VALID, isSupplyContractValid(barclaysContract1AmtAccentureContractSupplier, accenture, barclaysContract1, CONTRACT1_STARTDATE, CONTRACT3_ENDDATE));
	}
	
}
