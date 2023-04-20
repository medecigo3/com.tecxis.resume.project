package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT5_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.EmploymentContractTest.PK_UPDATE_WARN;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class SupplyContractTest {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private ContractRepository contracRepo;
	
	@Autowired 
	private SupplyContractRepository supplyContractRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetStaff() {
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		/**Find target Contract*/
		Contract micropoleContract = contracRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(CONTRACT5_NAME, micropoleContract.getName());
				
		/**Find target Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());		
		
		/**Find target SupplyContract*/
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
		
		/**Test target SupplyContract's Staff*/
		assertEquals(amt, fastconnectMicropoleSupplyContract.getStaff());
		
	}

	@Test
	public void test_ManyToOne_Update_Staff() {
		LOG.info(PK_UPDATE_WARN);
	}
	
	@Test
	public void test_ManyToOne_Update_Supplier() {
		LOG.info(PK_UPDATE_WARN);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_ManyToOne_Get_Supplier() {
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		/**Find target Contract*/
		Contract micropoleContract = contracRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(CONTRACT5_NAME, micropoleContract.getName());
				
		/**Find target Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());		
		
		/**Find target SupplyContract*/
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
		
		/**Test target SupplyContract's Supplier*/
		assertEquals(fastconnect, fastconnectMicropoleSupplyContract.getSupplier());
	}
	
	@Test
	public void test_ManyToOne_Update_Contract() {
		LOG.info(PK_UPDATE_WARN);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_ManyToOne_Get_Contract() {
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		/**Find target Contract*/
		Contract micropoleContract = contracRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(CONTRACT5_NAME, micropoleContract.getName());
				
		/**Find target Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());		
		
		/**Find target SupplyContract*/
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_Remove_SupplyContract() {
		/**Find target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		/**Find target Contract*/
		Contract micropoleContract = contracRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(CONTRACT5_NAME, micropoleContract.getName());
				
		/**Find target Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());		
		
		/**Find target SupplyContract*/
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
		
		/**Verify target SupplyContract*/
		assertEquals(micropoleContract, fastconnectMicropoleSupplyContract.getContract());
		assertEquals(amt, fastconnectMicropoleSupplyContract.getStaff());
		assertEquals(fastconnect, fastconnectMicropoleSupplyContract.getSupplier());
		
		/**Detach entities*/
		entityManager.clear();		
		
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		entityManager.remove(fastconnectMicropoleSupplyContract);
		entityManager.flush();
		entityManager.clear();
		SchemaUtils.testStateAfter_FastconnectMicropoleSupplyContract_Delete(jdbcTemplateProxy);	

	}
	
	@Test
	public void testNameIsNotNull() {
		SupplyContract supplyContract = new SupplyContract();
		Set<ConstraintViolation<SupplyContract>> violations = validator.validate(supplyContract);
        assertFalse(violations.isEmpty());
		
	}	

	@Test
	public void testToString() {
		SupplyContract supplyContract = new SupplyContract();
		assertThat(supplyContract.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}
	

}
