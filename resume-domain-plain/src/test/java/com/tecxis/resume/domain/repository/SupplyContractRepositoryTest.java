package com.tecxis.resume.domain.repository;

import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.CONTRACT13_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT13_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT14_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT14_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT7_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT7_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT9_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT9_STARTDATE;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.SupplyContractTest.insertASupplyContract;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.commons.persistence.id.SupplyContractId;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.ClientTest;
import com.tecxis.resume.domain.Constants;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.ContractTest;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffTest;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplierTest;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;


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
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertSupplyContractRowsAndSetIds() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		Client axeltis = ClientTest.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(axeltis, Constants.CONTRACT1_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(Constants.ALTERNA,  entityManager);	
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		Client axeltis = ClientTest.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(axeltis, Constants.CONTRACT1_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(Constants.ALTERNA,  entityManager);		
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);	
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
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
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		Client alextis = ClientTest.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(alextis, Constants.CONTRACT9_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(Constants.ALTERNA,  entityManager);
		Staff amt = StaffTest.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);	
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		
		/***Delete SupplyContract */
		entityManager.remove(alternaAccentureContract);
		entityManager.flush();
		
		/**Verify*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
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
	public void testFindBySupplierOrderByStartDateAsc() {
		Supplier accenture = supplierRepo.getSupplierByName(Constants.ACCENTURE_SUPPLIER);
		assertNotNull(accenture);
		
		/**Find test SupplyContract(s)*/
		List <SupplyContract> accentureSupplyContracts = supplyContractRepo.findBySupplierOrderByStartDateAsc(accenture);
		/**Validate SupplyContract(s) size*/
		assertEquals(3, accentureSupplyContracts.size());		

		/**Prepare validation test data */
		/**Find Contracts*/
		Contract barlcaysContract = contractRepo.getContractByName(Constants.CONTRACT1_NAME);
		Contract ageasContract = contractRepo.getContractByName(Constants.CONTRACT2_NAME);
		Contract accentureContract = contractRepo.getContractByName(Constants.CONTRACT3_NAME);
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
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByStaffOrderByStartDateAsc() {
		/**Test with Staff 'John'*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		assertNotNull(john);
		List <SupplyContract> johnSupplyContracts = supplyContractRepo.findByStaffOrderByStartDateAsc(john);
		assertEquals(1, johnSupplyContracts.size());
		
		SupplyContract johnSupplyContract = johnSupplyContracts.get(0);
		assertEquals(CONTRACT14_STARTDATE, johnSupplyContract.getStartDate());
		
		/**Test with Staff AMT*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertNotNull(amt);		
		List <SupplyContract> amtSupplyContracts = supplyContractRepo.findByStaffOrderByStartDateAsc(amt);
		assertEquals(13, amtSupplyContracts.size());
		
		/**First AMT SupplyContract ordered by StartDate*/
		SupplyContract amtFirstSupplyContract = amtSupplyContracts.get(0);
		assertEquals(CONTRACT1_STARTDATE, amtFirstSupplyContract.getStartDate());
		
		/**Last AMT SupplyContract ordered by EndDate*/
		SupplyContract amtLastSupplyContract = amtSupplyContracts.get(amtSupplyContracts.size()-1);
		assertEquals(CONTRACT13_STARTDATE, amtLastSupplyContract.getStartDate());
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindBySupplierAndStartDateAndEndDateOrderByStartDateAsc() {		
		/**Fetch target Supplier*/
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		/**Validate target Supplier has 2 SupplyContracts*/
		assertEquals(2, alphatress.getSupplyContracts().size());
		
		/**Let's narrow down search*/
		List <SupplyContract> belfiusAmtSupplyContracts = supplyContractRepo.findBySupplierAndStartDateAndEndDateOrderByStartDateAsc(alphatress, CONTRACT13_STARTDATE, CONTRACT13_ENDDATE);
		assertEquals(1, belfiusAmtSupplyContracts.size());
		SupplyContract belfiusAmtSupplyContract = belfiusAmtSupplyContracts.get(0);
		
		/**Validate SupplyContract -> Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);		
		assertEquals(amt, belfiusAmtSupplyContract.getStaff()); 
		/**Validate SupplyContract -> Contract*/
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);
		assertEquals(belfiusContract, belfiusAmtSupplyContract.getContract()); 
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByContractAndSupplierOrderByStartDateAsc() {
		/**Find target Contract*/
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);
		assertNotNull(belfiusContract);
		
		/**Find target Supplier*/
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);		
		assertNotNull(alphatress);
		
		/**Test target SupplyContracts are ordered by StartDate asc.*/
		List <SupplyContract> belfiusAlphatressSupplyContracts = supplyContractRepo.findByContractAndSupplierOrderByStartDateAsc(belfiusContract, alphatress);
		assertEquals(2, belfiusAlphatressSupplyContracts .size());
		
		/**Validate first Belfius's first SupplyContract is ordered by StartDate*/
		SupplyContract belfiusAlphatressSupplyContract0 = belfiusAlphatressSupplyContracts.get(0);
		assertEquals(CONTRACT13_STARTDATE, belfiusAlphatressSupplyContract0.getStartDate());
		assertEquals(CONTRACT13_ENDDATE, belfiusAlphatressSupplyContract0.getEndDate());
		/**Validate second Belfius's first SupplyContract is ordered by StartDate*/
		SupplyContract belfiusAlphatressSupplyContract1 = belfiusAlphatressSupplyContracts.get(1);
		assertEquals(CONTRACT14_STARTDATE, belfiusAlphatressSupplyContract1.getStartDate());
		assertEquals(CONTRACT14_ENDDATE, belfiusAlphatressSupplyContract1.getEndDate());
		
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void findByContractOrderByStartDateAsc() {
		/**Find target Contract*/
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);
		assertNotNull(belfiusContract);
		
		/**Test target SupplyContracts are ordered by StartDate asc.*/
		List <SupplyContract> belfiusAlphatressSupplyContracts = supplyContractRepo.findByContractOrderByStartDateAsc(belfiusContract);
		assertEquals(2, belfiusAlphatressSupplyContracts .size());
		
		/**Validate first Belfius's first SupplyContract is ordered by StartDate*/
		SupplyContract belfiusAlphatressSupplyContract0 = belfiusAlphatressSupplyContracts.get(0);
		assertEquals(CONTRACT13_STARTDATE, belfiusAlphatressSupplyContract0.getStartDate());
		assertEquals(CONTRACT13_ENDDATE, belfiusAlphatressSupplyContract0.getEndDate());
		/**Validate second Belfius's first SupplyContract is ordered by StartDate*/
		SupplyContract belfiusAlphatressSupplyContract1 = belfiusAlphatressSupplyContracts.get(1);
		assertEquals(CONTRACT14_STARTDATE, belfiusAlphatressSupplyContract1.getStartDate());
		assertEquals(CONTRACT14_ENDDATE, belfiusAlphatressSupplyContract1.getEndDate());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByClientAndSupplierOrderByStartDateAsc() {
		/**Fetch target Client*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertNotNull(axeltis);
		
		/**Fetch target Supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT); 
		assertNotNull(fastconnect);
		
		/**Test target SupplyContracts are ordered by StartDate asc.*/
		List <SupplyContract> axeltisFastconnectSupplyContracts = supplyContractRepo.findByClientAndSupplierOrderByStartDateAsc(axeltis, fastconnect);
		assertEquals(2, axeltisFastconnectSupplyContracts .size());
		
		/**Validate first Belfius's first SupplyContract is ordered by StartDate*/
		SupplyContract axeltisFastconnectSupplyContract0 = axeltisFastconnectSupplyContracts.get(0);
		assertEquals(CONTRACT7_STARTDATE, axeltisFastconnectSupplyContract0.getStartDate());
		assertEquals(CONTRACT7_ENDDATE, axeltisFastconnectSupplyContract0.getEndDate());
		/**Validate second Belfius's first SupplyContract is ordered by StartDate*/
		SupplyContract axeltisFastconnectSupplyContract1 = axeltisFastconnectSupplyContracts.get(1);
		assertEquals(CONTRACT9_STARTDATE, axeltisFastconnectSupplyContract1.getStartDate());
		assertEquals(CONTRACT9_ENDDATE, axeltisFastconnectSupplyContract1.getEndDate());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByContractAndSupplierAndStaff() {
		/**Find target Contract*/
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);
		assertNotNull(belfiusContract);
		
		/**Find target Supplier*/
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);
		assertNotNull(alphatress);
		
		/**Fetch target Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertNotNull(amt);		
		
		/**Test target SupplyContracts are ordered by StartDate asc.*/
		SupplyContract belfiusAlphatressAmtSupplyContracts = supplyContractRepo.findByContractAndSupplierAndStaff(belfiusContract, alphatress, amt);
		assertNotNull(belfiusAlphatressAmtSupplyContracts);
		
		/**Validate SupplyContract -> Contract*/
		assertEquals(belfiusContract, belfiusAlphatressAmtSupplyContracts.getContract());	
		
		/**Validate SupplyContract -> Supplier*/
		assertEquals(alphatress, belfiusAlphatressAmtSupplyContracts.getSupplier());
		
		/**Validate SupplyContract -> Staff*/
		assertEquals(amt, belfiusAlphatressAmtSupplyContracts.getStaff());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <SupplyContract> pageableSupplycontract = supplyContractRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableSupplycontract.getSize());
	}
}
