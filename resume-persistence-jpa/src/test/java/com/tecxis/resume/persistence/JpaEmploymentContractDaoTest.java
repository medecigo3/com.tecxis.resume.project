package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMT_ALTERNA_EMPLOYMENT_CONTRACT_ID;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaEmploymentContractDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
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
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testSave() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = Utils.insertSupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		EmploymentContract alternaAmtEmploymentContract = Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		
		/**Verify the EmploymentContract*/
		assertEquals(amt.getId(), alternaAmtEmploymentContract.getStaff().getId());	
		assertEquals(alterna.getId(), alternaAmtEmploymentContract.getSupplier().getId());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = Utils.insertSupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		EmploymentContract alternaAmtEmploymentContract = Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		
		alternaAmtEmploymentContract = employmentContractRepo.findByIdAndStaffAndSupplier(AMT_ALTERNA_EMPLOYMENT_CONTRACT_ID, amt, alterna);
		
		/**Verify the EmploymentContract*/
		assertEquals(amt.getId(), alternaAmtEmploymentContract.getStaff().getId());	
		assertEquals(alterna.getId(), alternaAmtEmploymentContract.getSupplier().getId());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));	
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		Supplier alterna = Utils.insertSupplier(ALTERNA,  entityManager);			
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		EmploymentContract alternaAmtEmploymentContract = Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
		
		/***Delete SupplyContract */
		entityManager.remove(alternaAmtEmploymentContract);
		entityManager.flush();
		
		/**Verify*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));	
		assertEquals(1, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));		
		List <EmploymentContract> employmentContracts = employmentContractRepo.findAll();
		assertEquals(6, employmentContracts.size());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <EmploymentContract> pageableEmploymentContractRepository = employmentContractRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableEmploymentContractRepository.getSize());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByStaff() {		
		/**Test 1*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		List <EmploymentContract> johnEmploymentContracts = employmentContractRepo.findByStaff(john);
		assertEquals(1, johnEmploymentContracts.size());
		/**Test 2*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		List <EmploymentContract> amtEmploymentContracts = employmentContractRepo.findByStaff(amt);
		assertEquals(5, amtEmploymentContracts.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindBySupplier() {
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> alphatressEmploymentcontracts = employmentContractRepo.findBySupplier(alphatress);
		assertEquals(2, alphatressEmploymentcontracts.size());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByStaffAndSupplier() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		List <EmploymentContract> johnAlhpatressEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(john, alphatress);
		assertEquals(1, johnAlhpatressEmploymentContracts.size());
		EmploymentContract johnAlhpatressEmploymentContract = johnAlhpatressEmploymentContracts.get(0); 
		
		/**Verify the EmploymentContract*/
		assertEquals(john.getId(), johnAlhpatressEmploymentContract.getStaff().getId());	
		assertEquals(alphatress.getId(), johnAlhpatressEmploymentContract.getSupplier().getId());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByIdAndStaffAndSupplier() {
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		EmploymentContract johnAlhpatressEmploymentContract =  employmentContractRepo.findByIdAndStaffAndSupplier((long)6, john, alphatress);
		
		/**Verify the EmploymentContract*/
		assertEquals(john.getId(), johnAlhpatressEmploymentContract.getStaff().getId());	
		assertEquals(alphatress.getId(), johnAlhpatressEmploymentContract.getSupplier().getId());
	
	}
	

}