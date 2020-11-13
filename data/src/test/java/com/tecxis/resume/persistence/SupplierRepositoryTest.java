package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT11_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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

import com.tecxis.resume.Constants;
import com.tecxis.resume.Contract;
import com.tecxis.resume.Staff;
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
public class SupplierRepositoryTest {
	
	public static final String SUPPLIER_TABLE = "Supplier";
	public static final String ACCENTURE = "ACCENTURE";
	public static final String AMESYS = "AMESYS";
	public static final String FASTCONNECT = "FASTCONNECT";
	public static final String ALTERNA = "ALTERNA";
	public static final String ALPHATRESS = "ALPHATRESS";
			
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private SupplyContractRepository supplyContractRepo;
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		
		Supplier accenture = SupplierTest.insertASupplier(ACCENTURE,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		assertEquals(1, accenture.getId());
		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void shouldBeAbleToFindInsertedSupplier() {		
		Supplier supplierIn = SupplierTest.insertASupplier(ALPHATRESS, entityManager);
		Supplier supplierOut = supplierRepo.getSupplierByName(ALPHATRESS);
		assertEquals(supplierIn, supplierOut);
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindSupplierByName() {
		Supplier accenture= supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());
		
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		Supplier alterna = supplierRepo.getSupplierByName(ALTERNA);
		assertEquals(ALTERNA, alterna.getName());
		
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);		
		assertEquals(ALPHATRESS, alphatress.getName());
		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindSupplierByNameAndStaffId() {
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertNotNull(accenture);
		assertEquals(ACCENTURE, accenture.getName());
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertNotNull(fastconnect);
		assertEquals(FASTCONNECT, fastconnect.getName());
		Supplier alterna = supplierRepo.getSupplierByName(ALTERNA);
		assertNotNull(alterna);
		assertEquals(ALTERNA, alterna.getName());
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindSupplierContracts() {
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertNotNull(fastconnect.getSupplyContracts());
		assertEquals(6, fastconnect.getSupplyContracts().size());
		List <SupplyContract> fastconnectSupplyContracts = fastconnect.getSupplyContracts();
		SupplyContract fastconnectContract1 = fastconnectSupplyContracts.get(0);		
		assertThat(fastconnectContract1.getStartDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_STARTDATE, Constants.CONTRACT6_STARTDATE, Constants.CONTRACT7_STARTDATE, Constants.CONTRACT8_STARTDATE, Constants.CONTRACT9_STARTDATE, Constants.CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract1.getEndDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_ENDDATE, Constants.CONTRACT6_ENDDATE, Constants.CONTRACT7_ENDDATE, Constants.CONTRACT8_ENDDATE, Constants.CONTRACT9_ENDDATE, Constants.CONTRACT10_ENDDATE)));
		SupplyContract fastconnectContract2 = fastconnectSupplyContracts.get(0);		
		assertThat(fastconnectContract2.getStartDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_STARTDATE, Constants.CONTRACT6_STARTDATE, Constants.CONTRACT7_STARTDATE, Constants.CONTRACT8_STARTDATE, Constants.CONTRACT9_STARTDATE, Constants.CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract2.getEndDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_ENDDATE, Constants.CONTRACT6_ENDDATE, Constants.CONTRACT7_ENDDATE, Constants.CONTRACT8_ENDDATE, Constants.CONTRACT9_ENDDATE, Constants.CONTRACT10_ENDDATE)));
		SupplyContract fastconnectContract3 = fastconnectSupplyContracts.get(0);		
		assertThat(fastconnectContract3.getStartDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_STARTDATE, Constants.CONTRACT6_STARTDATE, Constants.CONTRACT7_STARTDATE, Constants.CONTRACT8_STARTDATE, Constants.CONTRACT9_STARTDATE, Constants.CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract3.getEndDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_ENDDATE, Constants.CONTRACT6_ENDDATE, Constants.CONTRACT7_ENDDATE, Constants.CONTRACT8_ENDDATE, Constants.CONTRACT9_ENDDATE, Constants.CONTRACT10_ENDDATE)));
		SupplyContract fastconnectContract4 = fastconnectSupplyContracts.get(0);		
		assertThat(fastconnectContract4.getStartDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_STARTDATE, Constants.CONTRACT6_STARTDATE, Constants.CONTRACT7_STARTDATE, Constants.CONTRACT8_STARTDATE, Constants.CONTRACT9_STARTDATE, Constants.CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract4.getEndDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_ENDDATE, Constants.CONTRACT6_ENDDATE, Constants.CONTRACT7_ENDDATE, Constants.CONTRACT8_ENDDATE, Constants.CONTRACT9_ENDDATE, Constants.CONTRACT10_ENDDATE)));
		SupplyContract fastconnectContract5 = fastconnectSupplyContracts.get(0);		
		assertThat(fastconnectContract5.getStartDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_STARTDATE, Constants.CONTRACT6_STARTDATE, Constants.CONTRACT7_STARTDATE, Constants.CONTRACT8_STARTDATE, Constants.CONTRACT9_STARTDATE, Constants.CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract5.getEndDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_ENDDATE, Constants.CONTRACT6_ENDDATE, Constants.CONTRACT7_ENDDATE, Constants.CONTRACT8_ENDDATE, Constants.CONTRACT9_ENDDATE, Constants.CONTRACT10_ENDDATE)));
		SupplyContract fastconnectContract6 = fastconnectSupplyContracts.get(0);		
		assertThat(fastconnectContract6.getStartDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_STARTDATE, Constants.CONTRACT6_STARTDATE, Constants.CONTRACT7_STARTDATE, Constants.CONTRACT8_STARTDATE, Constants.CONTRACT9_STARTDATE, Constants.CONTRACT10_STARTDATE)));
		assertThat(fastconnectContract6.getEndDate(), Matchers.is(Matchers.oneOf(Constants.CONTRACT5_ENDDATE, Constants.CONTRACT6_ENDDATE, Constants.CONTRACT7_ENDDATE, Constants.CONTRACT8_ENDDATE, Constants.CONTRACT9_ENDDATE, Constants.CONTRACT10_ENDDATE)));

		
		/**Validate SupplyContract(s) for Supplier ALPHATRESS */
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);		
		assertEquals(2, alphatress.getSupplyContracts().size());				
		/**Validate SupplyContract for Supplier ALPHATRESS-AMT */		
		Contract belfiusAlphatressContract = contractRepo.getContractByName(CONTRACT13_NAME);
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		SupplyContract amtBelfiusAlphatressSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(belfiusAlphatressContract, alphatress, amt);
		assertEquals(Constants.CONTRACT13_STARTDATE, amtBelfiusAlphatressSupplyContract.getStartDate());
		assertNull(amtBelfiusAlphatressSupplyContract.getEndDate());		
		/**Validate SuppluContract ALPHATRESS-JOHN*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		SupplyContract johnBelfiusAlphatressSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(belfiusAlphatressContract, alphatress, john);
		assertEquals(Constants.CONTRACT14_STARTDATE, johnBelfiusAlphatressSupplyContract.getStartDate());
		assertEquals(Constants.CONTRACT14_ENDDATE, johnBelfiusAlphatressSupplyContract.getEndDate());
		/**Validate Contracts for Supplier ALHAPRES **/
		assertEquals(CONTRACT13_NAME, amtBelfiusAlphatressSupplyContract.getContract().getName());		
		assertEquals(CONTRACT13_NAME, johnBelfiusAlphatressSupplyContract.getContract().getName());
		/** John & AMT have the same Contract*/
		assertEquals(amtBelfiusAlphatressSupplyContract.getContract(), johnBelfiusAlphatressSupplyContract.getContract());
	
		/**Validate SupplyContract(s) for Supplier ALTERNA*/
		Supplier alterna = supplierRepo.getSupplierByName(ALTERNA);
		assertEquals(2, alterna.getSupplyContracts().size()); 
		/**Validate SupplyContract ALTERNA-ARVAL*/
		Contract arvalAlternaContract = contractRepo.getContractByName(CONTRACT11_NAME);
		SupplyContract arvalAlternaSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(arvalAlternaContract, alterna, amt);
		assertEquals(Constants.CONTRACT11_STARTDATE,arvalAlternaSupplyContract.getStartDate());
		assertEquals(Constants.CONTRACT11_ENDDATE,arvalAlternaSupplyContract.getEndDate());
		/**Validate SupplyContract ALTERNA-HERMES*/
		Contract hermesAlternaContract = contractRepo.getContractByName(CONTRACT12_NAME);
		SupplyContract hermesAlternaSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(hermesAlternaContract, alterna, amt);
		assertEquals(Constants.CONTRACT12_STARTDATE,hermesAlternaSupplyContract.getStartDate());
		assertEquals(Constants.CONTRACT12_ENDDATE,hermesAlternaSupplyContract.getEndDate());
		/**Validate ALTERNA-ARVAL &  ALTERNA-HERMES don't have the same contract*/
		assertNotEquals(arvalAlternaSupplyContract, hermesAlternaSupplyContract);
		
			
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteSupplier() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));		
		Supplier tempSupplier = SupplierTest.insertASupplier(AMESYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		supplierRepo.delete(tempSupplier);
		assertNull(supplierRepo.getSupplierByName(AMESYS));
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Supplier> suppliers = supplierRepo.findAll();
		assertEquals(5, suppliers.size());
	}
}
