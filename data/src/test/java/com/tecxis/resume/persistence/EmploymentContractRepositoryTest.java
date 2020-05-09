package com.tecxis.resume.persistence;

import static com.tecxis.resume.EmploymentContractTest.insertEmploymentContract;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.SUPPLIER_TABLE;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.EmploymentContract;
import com.tecxis.resume.EmploymentContract.EmploymentContractId;
import com.tecxis.resume.Staff;
import com.tecxis.resume.StaffTest;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplierTest;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class EmploymentContractRepositoryTest {
	
	public final static String EMPLOYMENT_CONTRACT_TABLE = "EMPLOYMENT_CONTRACT"; 
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmploymentContractRepository employmentContractRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testCreateRowsAndSetIds() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);			
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		EmploymentContract alternaAmtEmploymentContract = insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		
		/**Verify the EmploymentContract*/
		assertEquals(amt.getId(), alternaAmtEmploymentContract.getEmploymentContractId().getStaff().getId());	
		assertEquals(alterna.getId(), alternaAmtEmploymentContract.getEmploymentContractId().getSupplier().getId());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedEmploymentContract() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);			
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		EmploymentContract alternaAmtEmploymentContract = insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		
		alternaAmtEmploymentContract = employmentContractRepo.findById(new EmploymentContractId(amt, alterna)).get();
		
		/**Verify the EmploymentContract*/
		assertEquals(amt.getId(), alternaAmtEmploymentContract.getEmploymentContractId().getStaff().getId());	
		assertEquals(alterna.getId(), alternaAmtEmploymentContract.getEmploymentContractId().getSupplier().getId());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSupplyContract() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);			
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		EmploymentContract alternaAmtEmploymentContract = insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		
		/***Delete SupplyContract */
		entityManager.remove(alternaAmtEmploymentContract);
		entityManager.flush();
		
		/**Verify*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		List <EmploymentContract> employmentContracts = employmentContractRepo.findAll();
		assertEquals(6, employmentContracts.size());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetEmploymentContractByStaff() {		
		/**Test 1*/
		Staff john = staffRepo.getStaffByNameAndLastname(JOHN_NAME, JOHN_LASTNAME);
		List <EmploymentContract> johnEmploymentContracts = employmentContractRepo.findByEmploymentContractId_Staff(john);
		assertEquals(1, johnEmploymentContracts.size());
		/**Test 2*/
		Staff amt = staffRepo.getStaffByNameAndLastname(AMT_NAME, AMT_LASTNAME);
		List <EmploymentContract> amtEmploymentContracts = employmentContractRepo.findByEmploymentContractId_Staff(amt);
		assertEquals(5, amtEmploymentContracts.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetEmploymentContractBySupplier() {
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> alphatressEmploymentcontracts = employmentContractRepo.findByEmploymentContractId_Supplier(alphatress);
		assertEquals(2, alphatressEmploymentcontracts.size());
		
	}
	
	@Test
	public void testGetEmploymentContractByStaffAndSupplier() {
		Staff john = staffRepo.getStaffByNameAndLastname(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		EmploymentContract johnAlhpatressEmploymentContract =  employmentContractRepo.findByEmploymentContractId_StaffAndEmploymentContractId_Supplier(john, alphatress);
		
		/**Verify the EmploymentContract*/
		assertEquals(john.getId(), johnAlhpatressEmploymentContract.getEmploymentContractId().getStaff().getId());	
		assertEquals(alphatress.getId(), johnAlhpatressEmploymentContract.getEmploymentContractId().getSupplier().getId());
	
	}
	
}
