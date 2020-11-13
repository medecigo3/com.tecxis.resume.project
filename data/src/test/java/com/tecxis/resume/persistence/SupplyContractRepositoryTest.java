package com.tecxis.resume.persistence;

import static com.tecxis.resume.SupplyContractTest.insertASupplyContract;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT1_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT2_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT3_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.SUPPLIER_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.commons.persistence.id.SupplyContractId;
import com.tecxis.resume.Client;
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Constants;
import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractTest;
import com.tecxis.resume.Staff;
import com.tecxis.resume.StaffTest;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplierTest;
import com.tecxis.resume.SupplyContract;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SupplyContractRepositoryTest {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private SupplyContractRepository supplyContractRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertSupplyContractRowsAndSetIds() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		Client accenture = ClientTest.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(accenture, CONTRACT1_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);	
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		
		/** Verify SupplyContract*/ 
		assertEquals(accentureContract.getId(), alternaAccentureContract.getContract().getId());
		assertEquals(alterna.getId(), alternaAccentureContract.getSupplier().getId());		
		assertEquals(Constants.CONTRACT1_STARTDATE, alternaAccentureContract.getStartDate());
		assertEquals(Constants.CONTRACT1_ENDDATE, alternaAccentureContract.getEndDate());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedSupplyContract() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		Client accenture = ClientTest.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(accenture, CONTRACT1_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);		
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);	
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		
		alternaAccentureContract = supplyContractRepo.findById(new SupplyContractId (alterna, accentureContract, amt)).get();
		
		/** Verify SupplyContract*/ 
		assertEquals(accentureContract.getId(), alternaAccentureContract.getContract().getId());
		assertEquals(alterna.getId(), alternaAccentureContract.getSupplier().getId());		
		assertEquals(Constants.CONTRACT1_STARTDATE, alternaAccentureContract.getStartDate());
		assertEquals(Constants.CONTRACT1_ENDDATE, alternaAccentureContract.getEndDate());
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSupplyContract() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		Client accenture = ClientTest.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(accenture, CONTRACT9_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);	
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		
		/***Delete SupplyContract */
		entityManager.remove(alternaAccentureContract);
		entityManager.flush();
		
		/**Verify*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));		
		List <SupplyContract> supplyContracts = supplyContractRepo.findAll();
		assertEquals(14, supplyContracts.size());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByContractAndSupplierAndStaff() {
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertNotNull(accenture);
		
		/**Find test SupplyContract(s)*/
		List <SupplyContract> accentureSupplyContracts = supplyContractRepo.findBySupplierOrderByStartDateAsc(accenture);
		/**Validate SupplyContract(s) size*/
		assertEquals(3, accentureSupplyContracts.size());		

		/**Prepare validation test data */
		/**Find Contracts*/
		Contract barlcaysContract = contractRepo.getContractByName(CONTRACT1_NAME);
		Contract ageasContract = contractRepo.getContractByName(CONTRACT2_NAME);
		Contract accentureContract = contractRepo.getContractByName(CONTRACT3_NAME);
		assertNotNull(barlcaysContract);
		assertNotNull(ageasContract);
		assertNotNull(accentureContract);
		/**Find Staff(s)*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertNotNull(amt);
		/**Find target SupplyContract(s)*/
		SupplyContract accentureBarlcaysSupplyContract =  supplyContractRepo.findByContractAndSupplierAndStaff(barlcaysContract, accenture, amt);
		SupplyContract accentureAgeasSupplyContract  =  supplyContractRepo.findByContractAndSupplierAndStaff(ageasContract, accenture, amt);
		SupplyContract accentureAccentureSupplyContract  =  supplyContractRepo.findByContractAndSupplierAndStaff(accentureContract, accenture, amt);
		
		assertNotNull(accentureBarlcaysSupplyContract);
		assertNotNull(accentureAgeasSupplyContract);
		assertNotNull(accentureAccentureSupplyContract);
		
		
		assertEquals(barlcaysContract, accentureBarlcaysSupplyContract.getContract());
		assertEquals(amt, accentureBarlcaysSupplyContract.getStaff());
		assertEquals(accenture, accentureBarlcaysSupplyContract.getSupplier());


		assertEquals(ageasContract, accentureAgeasSupplyContract.getContract());
		assertEquals(amt, accentureAgeasSupplyContract.getStaff());
		assertEquals(accenture, accentureAgeasSupplyContract.getSupplier());

		assertEquals(accentureContract, accentureAccentureSupplyContract.getContract());
		assertEquals(amt, accentureAccentureSupplyContract.getStaff());
		assertEquals(accenture, accentureAccentureSupplyContract.getSupplier());
		 
		/**Validate*/
		assertThat(accentureSupplyContracts, Matchers.containsInRelativeOrder(accentureBarlcaysSupplyContract, accentureAgeasSupplyContract, accentureAccentureSupplyContract));
	}
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContractByStartDate() {
		List <SupplyContract> supplyContracts = supplyContractRepo.getSupplyContractByStartDate(Constants.CONTRACT1_STARTDATE);
		assertEquals(1, supplyContracts.size());
		assertEquals(Constants.CONTRACT1_STARTDATE, supplyContracts.get(0).getStartDate());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContractByEndDate() {
		List <SupplyContract> supplyContracts = supplyContractRepo.getSupplyContractByEndDate(Constants.CONTRACT9_ENDDATE);
		assertEquals(Constants.CONTRACT9_ENDDATE, supplyContracts.get(0).getEndDate());
		
	}
	
	@Test
	public void testFindByStaffOrderByStartDateAsc() {
		fail("TODO");
	}
	
	@Test
	public void testFindByClientOrderByStartDateAsc() {
		fail("TODO");
	}
	
	@Test
	public void testFindBySupplierAndStartDateAndEndDateOrderByStartDateAsc() {
		fail("TODO");
	}
	
	
	@Test
	public void testFindByContractAndSupplierOrderByStartDateAsc() {
		fail("TODO");
	}
	
}
