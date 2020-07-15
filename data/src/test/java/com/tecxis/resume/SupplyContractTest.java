package com.tecxis.resume;

import static com.tecxis.resume.EmploymentContractTest.CANNOT_UPDATE_PK_MSG;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.EmploymentContractRepositoryTest.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.FASTCONNECT;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.SUPPLIER_TABLE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.SUPPLY_CONTRACT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.SupplyContract.SupplyContractId;
import  com.tecxis.resume.persistence.ContractRepository;
import com.tecxis.resume.persistence.StaffRepository;
import com.tecxis.resume.persistence.SupplierRepository;
import com.tecxis.resume.persistence.SupplyContractRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
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
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
		
		/**Test target SupplyContract's Staff*/
		assertEquals(amt, fastconnectMicropoleSupplyContract.getSupplyContractId().getStaff());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetStaff() {
		LOG.info(CANNOT_UPDATE_PK_MSG);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetSupplier() {
		LOG.info(CANNOT_UPDATE_PK_MSG);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
		
		/**Test target SupplyContract's Supplier*/
		assertEquals(fastconnect, fastconnectMicropoleSupplyContract.getSupplyContractId().getSupplier());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetContract() {
		LOG.info(CANNOT_UPDATE_PK_MSG);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		SupplyContract fastconnectMicropoleSupplyContract = supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(micropoleContract, fastconnect, amt);
		assertNotNull(fastconnectMicropoleSupplyContract);
		
		/**Verify target SupplyContract*/
		assertEquals(micropoleContract, fastconnectMicropoleSupplyContract.getSupplyContractId().getContract());
		assertEquals(amt, fastconnectMicropoleSupplyContract.getSupplyContractId().getStaff());
		assertEquals(fastconnect, fastconnectMicropoleSupplyContract.getSupplyContractId().getSupplier());
		
		/**Detach entities*/
		entityManager.clear();		
		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));	
		fastconnectMicropoleSupplyContract = supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(micropoleContract, fastconnect, amt);
		entityManager.remove(fastconnectMicropoleSupplyContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));					
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		

	}
	
	
	public static SupplyContract insertASupplyContract(Supplier supplier, Contract contract, Staff staff, Date startDate, Date endDate, EntityManager entityManager){
		SupplyContract supplyContract = new SupplyContract( new SupplyContractId(supplier, contract, staff));
		supplyContract.setStartDate(startDate);
		supplyContract.setEndDate(endDate);		
		entityManager.persist(supplyContract);
		entityManager.flush();
		return supplyContract;
		
	}
	

}