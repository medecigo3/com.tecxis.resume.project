package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.junit.Assert.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tecxis.resume.domain.*;
import com.tecxis.resume.domain.id.ContractId;
import com.tecxis.resume.domain.id.SupplyContractId;
import com.tecxis.resume.domain.repository.*;
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

import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaSupplierDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
		
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private SupplyContractRepository supplyContractRepo;

	@Autowired
	private EmploymentContractRepository employmentContractRepo;

	@Autowired
	private ClientRepository clientRepo;
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
		
		Supplier accenture = Utils.insertSupplier(ACCENTURE_SUPPLIER,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.STAFF_TABLE));
		assertEquals(1, accenture.getId().longValue());
		
	}
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testAdd() {		
		Supplier supplierIn = Utils.insertSupplier(ALPHATRESS, entityManager);
		Supplier supplierOut = supplierRepo.getSupplierByName(ALPHATRESS);
		assertEquals(supplierIn, supplierOut);
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));		
		Supplier tempSupplier = Utils.insertSupplier(AMESYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
		supplierRepo.delete(tempSupplier);
		assertNull(supplierRepo.getSupplierByName(AMESYS));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SUPPLIER_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Supplier> suppliers = supplierRepo.findAll();
		assertEquals(5, suppliers.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Supplier> pageableSupplier = supplierRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableSupplier.getSize());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplierByName() {
		Supplier accenture= supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertEquals(ACCENTURE_SUPPLIER, accenture.getName());
		
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());
		
		Supplier alterna = supplierRepo.getSupplierByName(ALTERNA);
		assertEquals(ALTERNA, alterna.getName());
		
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);		
		assertEquals(ALPHATRESS, alphatress.getName());	
		
	}
	
	@Test
	public void test_OneToMany_Update_EmploymentContracts_And_RemoveOrphansWithOrm() {
		org.junit.Assert.fail("TODO");
	}

	@Test
	public void test_OneToMany_Update_EmploymentContracts_And_RemoveOrphansWithOrm_NullSet() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	public void test_OneToMany_Update_SupplyContracts_And_RemoveOrphansWithOrm() {//RES-52
		/**Find and verify the Supplier*/
		final Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertEquals(ACCENTURE_SUPPLIER, accenture.getName());

		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ACCENTURE_CLIENT);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ACCENTURE_CLIENT, accentureClient.getName());

		/**Verify Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());
		EmploymentContract accentureEmploymentContract = employmentContractRepo.findById(1L).get();

		/**Verify Supplier -> SupplyContracts*/
		List <SupplyContract> accentureSupplyContracts = accenture.getSupplyContracts();
		assertEquals(3, accentureSupplyContracts.size());
		SupplyContract accentureBarclaysAmtSupplyContract = supplyContractRepo.findById(new SupplyContractId(SUPPLIER_ACCENTURE_ID, new ContractId(CONTRACT_BARCLAYS_ID, CLIENT_BARCLAYS_ID), STAFF_AMT_ID)).get();
		SupplyContract accentureAgeasAmtSupplyContract = supplyContractRepo.findById(new SupplyContractId(SUPPLIER_ACCENTURE_ID, new ContractId(CONTRACT_AGEAS_ID, CLIENT_AGEAS_ID), STAFF_AMT_ID)).get();
		SupplyContract accentureAccentureAmtSupplyContract = supplyContractRepo.findById(new SupplyContractId(SUPPLIER_ACCENTURE_ID, new ContractId(CONTRACT_ACCENTURE_ID, CLIENT_ACCENTURE_ID), STAFF_AMT_ID)).get();

		/**Validate Supplier*/
		assertEquals(SUCCESS, isSupplierValid(accenture, ACCENTURE_SUPPLIER, List.of(accentureBarclaysAmtSupplyContract, accentureAgeasAmtSupplyContract, accentureAccentureAmtSupplyContract), List.of(accentureEmploymentContract)));

		/**Create the new SupplyContract to set to parent Supplier*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);
		SupplyContract newSupplyContract = new SupplyContract(accenture, belfiusContract, john);
		newSupplyContract.setStartDate(new Date());
		List <SupplyContract> newSupplyContracts = List.of(newSupplyContract);

		update_SupplierAccenture_With_SupplyContracts_InJpa( supplierRepo -> {
			/**Set the new SupplyContract(s) to the parent Supplier and leaves orphans*/
			accenture.setSupplyContracts(newSupplyContracts);
			supplierRepo.save(accenture);
			supplierRepo.flush();
		},supplierRepo, jdbcTemplateProxy);
		entityManager.clear();
		/**Fetch new SupplyContracts */
		Supplier newAccenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		Staff newJohn = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		SupplyContract johnBelfiusAccentureSupplyContract = supplyContractRepo.findByStaffAndSupplierAndContract(newJohn, newAccenture, belfiusContract);
		/**Fetch EmploymentContracts*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		List <EmploymentContract> amtAccentureEmploymentContracts =  employmentContractRepo.findByStaffAndSupplier(amt, newAccenture);
		/**Validate  Supplier -with new -> SupplyContracts assoc. */
		assertEquals(SUCCESS, isSupplierValid(newAccenture, ACCENTURE_SUPPLIER, List.of(johnBelfiusAccentureSupplyContract), amtAccentureEmploymentContracts));
	}
	@Test
	public void test_OneToMany_Update_SupplyContracts_And_RemoveOrphansWithOrm_NullSet() {//RES-52
		/**Find and verify the Supplier*/
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertEquals(ACCENTURE_SUPPLIER, accenture.getName());

		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ACCENTURE_CLIENT);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ACCENTURE_CLIENT, accentureClient.getName());

		/**Verify Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());
		EmploymentContract accentureEmploymentContract = employmentContractRepo.findById(1L).get();

		/**Verify Supplier -> SupplyContracts*/
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();
		assertEquals(3, accentureSupplycontracts.size());
		SupplyContract accentureBarclaysAmtSupplyContract = supplyContractRepo.findById(new SupplyContractId(SUPPLIER_ACCENTURE_ID, new ContractId(CONTRACT_BARCLAYS_ID, CLIENT_BARCLAYS_ID), STAFF_AMT_ID)).get();
		SupplyContract accentureAgeasAmtSupplyContract = supplyContractRepo.findById(new SupplyContractId(SUPPLIER_ACCENTURE_ID, new ContractId(CONTRACT_AGEAS_ID, CLIENT_AGEAS_ID), STAFF_AMT_ID)).get();
		SupplyContract accentureAccentureAmtSupplyContract = supplyContractRepo.findById(new SupplyContractId(SUPPLIER_ACCENTURE_ID, new ContractId(CONTRACT_ACCENTURE_ID, CLIENT_ACCENTURE_ID), STAFF_AMT_ID)).get();

		assertEquals(SUCCESS, isSupplierValid(accenture, ACCENTURE_SUPPLIER, List.of(accentureBarclaysAmtSupplyContract, accentureAgeasAmtSupplyContract, accentureAccentureAmtSupplyContract), List.of(accentureEmploymentContract)));

		/**Detach entities*/
		entityManager.clear();
		final Supplier tempAccenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);

		update_SupplierAccenture_With_NullSupplyContracts_InJpa(supplierRepo -> {
			/**Sets currents Accenture's SupplyContracts as orphans*/
			tempAccenture.setSupplyContracts(null);
			supplierRepo.save(tempAccenture);
			supplierRepo.flush();

		}, supplierRepo, jdbcTemplateProxy);
		entityManager.clear();
		/**Test parent Supplier has no SupplyContract(s)*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertNotNull(accenture);
		assertEquals(0, accenture.getSupplyContracts().size());
		assertEquals(SUCCESS, isSupplierValid(accenture, ACCENTURE_SUPPLIER, List.of(), List.of(accentureEmploymentContract)));
	}
}
