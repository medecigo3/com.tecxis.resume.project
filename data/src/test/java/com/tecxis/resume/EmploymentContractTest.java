package com.tecxis.resume;

import static com.tecxis.resume.Constants.AMT_ALTERNA_EMPLOYMENT_ENDDATE;
import static com.tecxis.resume.Constants.AMT_ALTERNA_EMPLOYMENT_STARTDATE;
import static com.tecxis.resume.Constants.JOHN_ALPHATRESS_EMPLOYMENT_ENDDATE;
import static com.tecxis.resume.Constants.JOHN_ALPHATRESS_EMPLOYMENT_STARTDATE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.tecxis.resume.persistence.EmploymentContractRepository;
import com.tecxis.resume.persistence.StaffRepository;
import com.tecxis.resume.persistence.SupplierRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml",
		"classpath:validation-api-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class EmploymentContractTest {

	private final static Logger LOG = LogManager.getLogger();
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Supplier alterna = SupplierTest.insertASupplier(Constants.ALTERNA,  entityManager);			
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertNotNull(alterna);
		assertNotNull(amt);
		EmploymentContract alternaAmtEmploymentContract = insertEmploymentContract(alterna, amt, entityManager);
		assertThat(alternaAmtEmploymentContract.getId(), Matchers.greaterThan((long)0));
	}
	
	@Test
	public void testSetId() {
		EmploymentContract employmentContract = new EmploymentContract();
		assertEquals(0, employmentContract.getId());
		employmentContract.setId(1);
		assertEquals(1, employmentContract.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStartDate(){
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetEndDate(){
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaff() {		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		
		/**Test Supplier's Staff*/
		assertEquals(john, johnAlhpatressEmploymentContract.getStaff());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplier() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDbRemoveEmploymentContract() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0);
		
		/**Verify target EmploymentContract*/
		assertNotNull(johnAlhpatressEmploymentContract);
				
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		entityManager.remove(johnAlhpatressEmploymentContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));		
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
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
		employmentContract.toString();
	}
		
	public static EmploymentContract insertEmploymentContract(Supplier supplier, Staff staff, EntityManager entityManager){
		EmploymentContract employmentContract = new EmploymentContract(staff, supplier);
		employmentContract.setStartDate(new Date());
		entityManager.persist(employmentContract);
		entityManager.flush();
		assertThat(employmentContract.getId(), Matchers.greaterThan((long)0));
		return employmentContract;
		
	}

}
