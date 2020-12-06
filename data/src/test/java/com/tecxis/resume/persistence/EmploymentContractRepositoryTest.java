package com.tecxis.resume.persistence;

import static com.tecxis.resume.EmploymentContractTest.insertEmploymentContract;
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

import com.tecxis.commons.persistence.id.EmploymentContractId;
import com.tecxis.resume.Constants;
import com.tecxis.resume.EmploymentContract;
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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = SupplierTest.insertASupplier(Constants.ALTERNA,  entityManager);			
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		EmploymentContract alternaAmtEmploymentContract = insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));
		
		/**Verify the EmploymentContract*/
		assertEquals(amt.getId(), alternaAmtEmploymentContract.getStaff().getId());	
		assertEquals(alterna.getId(), alternaAmtEmploymentContract.getSupplier().getId());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedEmploymentContract() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = SupplierTest.insertASupplier(Constants.ALTERNA,  entityManager);			
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		EmploymentContract alternaAmtEmploymentContract = insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	
		
		alternaAmtEmploymentContract = employmentContractRepo.findById(new EmploymentContractId((long)1, amt, alterna)).get();
		
		/**Verify the EmploymentContract*/
		assertEquals(amt.getId(), alternaAmtEmploymentContract.getStaff().getId());	
		assertEquals(alterna.getId(), alternaAmtEmploymentContract.getSupplier().getId());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSupplyContract() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = SupplierTest.insertASupplier(Constants.ALTERNA,  entityManager);			
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		EmploymentContract alternaAmtEmploymentContract = insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));
		
		/***Delete SupplyContract */
		entityManager.remove(alternaAmtEmploymentContract);
		entityManager.flush();
		
		/**Verify*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));		
		List <EmploymentContract> employmentContracts = employmentContractRepo.findAll();
		assertEquals(6, employmentContracts.size());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByStaff() {		
		/**Test 1*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		List <EmploymentContract> johnEmploymentContracts = employmentContractRepo.findByStaff(john);
		assertEquals(1, johnEmploymentContracts.size());
		/**Test 2*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		List <EmploymentContract> amtEmploymentContracts = employmentContractRepo.findByStaff(amt);
		assertEquals(5, amtEmploymentContracts.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindBySupplier() {
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
		List <EmploymentContract> alphatressEmploymentcontracts = employmentContractRepo.findBySupplier(alphatress);
		assertEquals(2, alphatressEmploymentcontracts.size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByStaffAndSupplier() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0); 
		
		/**Verify the EmploymentContract*/
		assertEquals(john.getId(), johnAlhpatressEmploymentContract.getStaff().getId());	
		assertEquals(alphatress.getId(), johnAlhpatressEmploymentContract.getSupplier().getId());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByIdAndStaffAndSupplier() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(Constants.ALPHATRESS);
		EmploymentContract johnAlhpatressEmploymentContract =  employmentContractRepo.findByIdAndStaffAndSupplier((long)6, john, alphatress);
		
		/**Verify the EmploymentContract*/
		assertEquals(john.getId(), johnAlhpatressEmploymentContract.getStaff().getId());	
		assertEquals(alphatress.getId(), johnAlhpatressEmploymentContract.getSupplier().getId());
	
	}
	
}
