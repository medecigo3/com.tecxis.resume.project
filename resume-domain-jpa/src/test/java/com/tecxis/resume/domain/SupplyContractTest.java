package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT5_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.EmploymentContractTest.PK_UPDATE_WARN;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SupplyContractTest {

	private final static Logger LOG = LogManager.getLogger();
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
	public void testGetSupplier() {
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
	public void testSetContract() {
		LOG.info(PK_UPDATE_WARN);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetContract() {
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
	public void testDbRemoveSupplyContract() {
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
		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		fastconnectMicropoleSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(micropoleContract, fastconnect, amt);
		entityManager.remove(fastconnectMicropoleSupplyContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		

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
		supplyContract.toString();
	}
	

}
