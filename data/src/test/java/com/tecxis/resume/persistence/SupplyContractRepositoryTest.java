package com.tecxis.resume.persistence;

import static com.tecxis.resume.SupplyContractTest.insertASupplyContract;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.CLIENT_TABLE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT1_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT2_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT3_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.SUPPLIER_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.tecxis.resume.Client;
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractTest;
import com.tecxis.resume.Staff;
import com.tecxis.resume.StaffTest;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplierTest;
import com.tecxis.resume.SupplyContract;
import com.tecxis.resume.SupplyContract.SupplyContractId;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SupplyContractRepositoryTest {
	
	final public static String SUPPLY_CONTRACT_TABLE ="SUPPLY_CONTRACT";
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static final Date CONTRACT1_STARTDATE;
	public static final Date CONTRACT1_ENDDATE;
	public static final Date CONTRACT2_STARTDATE;
	public static final Date CONTRACT2_ENDDATE;
	public static final Date CONTRACT3_STARTDATE;
	public static final Date CONTRACT3_ENDDATE;
	public static final Date CONTRACT4_STARTDATE;
	public static final Date CONTRACT4_ENDDATE;
	public static final Date CONTRACT5_STARTDATE;
	public static final Date CONTRACT5_ENDDATE;
	public static final Date CONTRACT6_STARTDATE;
	public static final Date CONTRACT6_ENDDATE;
	public static final Date CONTRACT7_STARTDATE;
	public static final Date CONTRACT7_ENDDATE;
	public static final Date CONTRACT8_STARTDATE;
	public static final Date CONTRACT8_ENDDATE;
	public static final Date CONTRACT9_STARTDATE;
	public static final Date CONTRACT9_ENDDATE;
	public static final Date CONTRACT10_STARTDATE;
	public static final Date CONTRACT10_ENDDATE;
	public static final Date CONTRACT11_STARTDATE;
	public static final Date CONTRACT11_ENDDATE;
	public static final Date CONTRACT12_STARTDATE;
	public static final Date CONTRACT12_ENDDATE;
	public static final Date CONTRACT13_STARTDATE;
	public static final Date CONTRACT13_ENDDATE = null;
	public static final Date CONTRACT14_STARTDATE;
	public static final Date CONTRACT14_ENDDATE;
	public static final Date CURRENT_DATE = new Date();
	
	
	static{
		try {
		CONTRACT1_STARTDATE = sdf.parse("01/01/2007");
		CONTRACT1_ENDDATE = sdf.parse("01/02/2008'");
		CONTRACT2_STARTDATE = sdf.parse("01/03/2008");
		CONTRACT2_ENDDATE = sdf.parse("01/05/2008");
		CONTRACT3_STARTDATE = sdf.parse("01/06/2008");
		CONTRACT3_ENDDATE = sdf.parse("01/07/2008");
		CONTRACT4_STARTDATE = sdf.parse("01/10/2008");
		CONTRACT4_ENDDATE = sdf.parse("01/07/2010");
		CONTRACT5_STARTDATE = sdf.parse("01/07/2010");
		CONTRACT5_ENDDATE = sdf.parse("01/08/2010");
		CONTRACT6_STARTDATE = sdf.parse("01/09/2010");
		CONTRACT6_ENDDATE = sdf.parse("01/10/2010");
		CONTRACT7_STARTDATE = sdf.parse("01/11/2010");
		CONTRACT7_ENDDATE = sdf.parse("01/07/2012");
		CONTRACT8_STARTDATE = sdf.parse("01/07/2012");
		CONTRACT8_ENDDATE = sdf.parse("01/03/2013");
		CONTRACT9_STARTDATE = sdf.parse("01/05/2013");
		CONTRACT9_ENDDATE = sdf.parse("01/10/2013");
		CONTRACT10_STARTDATE = sdf.parse("01/10/2013");
		CONTRACT10_ENDDATE = sdf.parse("01/06/2015");
		CONTRACT11_STARTDATE = sdf.parse("01/06/2015");
		CONTRACT11_ENDDATE = sdf.parse("01/03/2016");
		CONTRACT12_STARTDATE = sdf.parse("01/03/2016");
		CONTRACT12_ENDDATE = sdf.parse("01/08/2016");
		CONTRACT13_STARTDATE = sdf.parse("01/08/2016");
		CONTRACT14_STARTDATE = sdf.parse("01/03/2017");
		CONTRACT14_ENDDATE = sdf.parse("31/12/2019");
		}
		catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
			
	}
	
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
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertSupplyContractRowsAndSetIds() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Client accenture = ClientTest.insertAClient(AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(accenture, CONTRACT1_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);	
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		
		/** Verify SupplyContract*/ 
		assertEquals(accentureContract.getId(), alternaAccentureContract.getSupplyContractId().getContract().getId());
		assertEquals(alterna.getId(), alternaAccentureContract.getSupplyContractId().getSupplier().getId());		
		assertEquals(CONTRACT1_STARTDATE, alternaAccentureContract.getStartDate());
		assertEquals(CONTRACT1_ENDDATE, alternaAccentureContract.getEndDate());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedSupplyContract() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Client accenture = ClientTest.insertAClient(AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(accenture, CONTRACT1_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);		
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);	
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		
		alternaAccentureContract = supplyContractRepo.findById(new SupplyContractId (alterna, accentureContract, amt)).get();
		
		/** Verify SupplyContract*/ 
		assertEquals(accentureContract.getId(), alternaAccentureContract.getSupplyContractId().getContract().getId());
		assertEquals(alterna.getId(), alternaAccentureContract.getSupplyContractId().getSupplier().getId());		
		assertEquals(CONTRACT1_STARTDATE, alternaAccentureContract.getStartDate());
		assertEquals(CONTRACT1_ENDDATE, alternaAccentureContract.getEndDate());
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSupplyContract() {
		/**Insert Client, Supplier, Contract, SupplyContract*/		
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Client accenture = ClientTest.insertAClient(AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(accenture, CONTRACT9_NAME, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(ALTERNA,  entityManager);
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);	
		SupplyContract alternaAccentureContract = insertASupplyContract(alterna, accentureContract, amt, CONTRACT1_STARTDATE, CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		
		/***Delete SupplyContract */
		entityManager.remove(alternaAccentureContract);
		entityManager.flush();
		
		/**Verify*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll() {
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		List <SupplyContract> supplyContracts = supplyContractRepo.findAll();
		assertEquals(14, supplyContracts.size());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindByContractAndSupplierAndStaffOrderByStartDateAsc() {
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		
		/**Find test SupplyContract(s)*/
		List <SupplyContract> accentureSupplyContracts = supplyContractRepo.findBySupplyContractId_SupplierOrderByStartDateAsc(accenture);
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
		Staff amt = StaffTest.insertAStaff(AMT_NAME, AMT_LASTNAME,  entityManager);	 
		assertNotNull(amt);
		/**Find target SupplyContract(s)*/
		SupplyContract accentureBarlcaysSupplyContract =  supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(barlcaysContract, accenture, amt);
		SupplyContract accentureAgeasSupplyContract  =  supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(ageasContract, accenture, amt);
		SupplyContract accentureAccentureSupplyContract  =  supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(accentureContract, accenture, amt);
		//TODO continue here
		assertNotNull(accentureBarlcaysSupplyContract);
		assertNotNull(accentureAgeasSupplyContract);
		assertNotNull(accentureAccentureSupplyContract);
		 
		/**Validate*/
		assertThat(accentureSupplyContracts, Matchers.containsInRelativeOrder(accentureBarlcaysSupplyContract, accentureAgeasSupplyContract, accentureAccentureSupplyContract));
	}
	

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContractByStartDate() {
		List <SupplyContract> supplyContracts = supplyContractRepo.getSupplyContractByStartDate(CONTRACT1_STARTDATE);
		assertEquals(1, supplyContracts.size());
		assertEquals(CONTRACT1_STARTDATE, supplyContracts.get(0).getStartDate());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContractByEndDate() {
		List <SupplyContract> supplyContracts = supplyContractRepo.getSupplyContractByEndDate(CONTRACT9_ENDDATE);
		assertEquals(CONTRACT9_ENDDATE, supplyContracts.get(0).getEndDate());
		
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
