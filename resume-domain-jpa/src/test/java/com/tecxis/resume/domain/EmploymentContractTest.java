package com.tecxis.resume.domain;
 
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMT_ALTERNA_EMPLOYMENT_ENDDATE;
import static com.tecxis.resume.domain.Constants.AMT_ALTERNA_EMPLOYMENT_STARTDATE;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.JOHN_ALPHATRESS_EMPLOYMENT_ENDDATE;
import static com.tecxis.resume.domain.Constants.JOHN_ALPHATRESS_EMPLOYMENT_STARTDATE;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
@Commit
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_UNCOMMITTED)
@SqlConfig(dataSource="dataSource")
public class EmploymentContractTest {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	public final static String PK_UPDATE_WARN = "Cannot update the inverse side (the owning) in a PK. Remove then create a new instance. See EnrolmentTest.testSetStaff, EnrolmentTest.testSetCourse, StaffSkillTest.testSetStaff, StaffSkillTest.testSetSkill";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private EmploymentContractRepository employmentContractRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Supplier alterna = Utils.insertASupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertNotNull(alterna);
		assertNotNull(amt);
		EmploymentContract alternaAmtEmploymentContract = Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertThat(alternaAmtEmploymentContract.getId(), Matchers.greaterThan((long)0));
	}
	
	@Test
	public void testSetId() {
		EmploymentContract employmentContract = new EmploymentContract();
		assertEquals(0, employmentContract.getId().longValue());
		employmentContract.setId(new Long(1));
		assertEquals(1, employmentContract.getId().longValue());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStartDate(){
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		assertEquals(JOHN_ALPHATRESS_EMPLOYMENT_STARTDATE, johnAlhpatressEmploymentContract.getStartDate());

	}
	
	@Test
	public void testSetStartDate(){
		EmploymentContract employmentContract = new EmploymentContract();
		assertNull(employmentContract.getStartDate());
		employmentContract.setStartDate(AMT_ALTERNA_EMPLOYMENT_STARTDATE);
		assertEquals(AMT_ALTERNA_EMPLOYMENT_STARTDATE, employmentContract.getStartDate());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetEndDate(){
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		assertEquals(JOHN_ALPHATRESS_EMPLOYMENT_ENDDATE, johnAlhpatressEmploymentContract.getEndDate());
	}
	
	@Test
	public void testSetEndDate(){
		EmploymentContract employmentContract = new EmploymentContract();
		assertNull(employmentContract.getEndDate());
		employmentContract.setEndDate(AMT_ALTERNA_EMPLOYMENT_ENDDATE);
		assertEquals(AMT_ALTERNA_EMPLOYMENT_ENDDATE, employmentContract.getEndDate());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaff() {		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		
		/**Test Supplier's Staff*/
		assertEquals(john, johnAlhpatressEmploymentContract.getStaff());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplier() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		
		/**Test EmploymentContract's Supplier*/
		assertEquals(alphatress, johnAlhpatressEmploymentContract.getSupplier());
	}
	
	@Test
	public void testSetStaff() {	
		LOG.info(PK_UPDATE_WARN);
	}
	
	@Test
	public void testSetSupplier() {
		LOG.info(PK_UPDATE_WARN);		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDbRemoveEmploymentContract() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		
		/**Verify target EmploymentContract*/
		assertNotNull(johnAlhpatressEmploymentContract);
				
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
		entityManager.remove(johnAlhpatressEmploymentContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
	}
	
	@Test
	public void testNameIsNotNull() {
		EmploymentContract employmentContract = new EmploymentContract();
		Set<ConstraintViolation<EmploymentContract>> violations = validator.validate(employmentContract);
        assertFalse(violations.isEmpty());
		
	}

	@Test
	public void testToString() {
		EmploymentContract employmentContract = new EmploymentContract();
		assertThat(employmentContract.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}

}
