package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.ACCENTURE_CLIENT;
import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.AGEAS;
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.EmploymentContractRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.util.Utils;
import com.tecxis.resume.domain.util.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"} )
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
public class SupplierTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private SupplyContractRepository supplyContractRepo;
	
	@Autowired
	private EmploymentContractRepository employmentContractRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private Validator validator;

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Supplier supplierIn = Utils.insertSupplier(ALPHATRESS, entityManager);
		assertThat(supplierIn.getId(), Matchers.greaterThan((long)0));
		
	}
	
	@Test	
	public void testSetId() {
		Supplier supplier = new Supplier();
		assertEquals(0L, supplier.getId().longValue());		
		supplier.setId(100L);				
		assertEquals(100L, supplier.getId().longValue());
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetName() {
		Supplier supplierIn = Utils.insertSupplier(ALPHATRESS, entityManager);
		assertEquals(supplierIn.getName(), supplierIn.getName());
	}

	@Test	
	public void testSetName() {
		Supplier supplier = new Supplier();
		assertNull(supplier.getName());		
		supplier.setName(ALPHATRESS);				
		assertEquals(ALPHATRESS, supplier.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContracts() {
		/**Find a Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		
		/**Find Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ACCENTURE_CLIENT);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ACCENTURE_CLIENT, accentureClient.getName());
					
		/**Verify the Supplier*/
		assertEquals(ACCENTURE_SUPPLIER, accenture.getName());				
		
		/**Check SupplyContracts*/
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());		
		
		List <SupplyContract> accentureBarclaysContracts = supplyContractRepo.findByClientAndSupplierOrderByStartDateAsc(barclays, accenture);
		List <SupplyContract> accentureAgeasContracts = supplyContractRepo.findByClientAndSupplierOrderByStartDateAsc(ageas, accenture);
		List <SupplyContract> accentureAccentureContracts = supplyContractRepo.findByClientAndSupplierOrderByStartDateAsc(accentureClient, accenture);		
		assertEquals(1, accentureBarclaysContracts.size());				
		assertEquals(1, accentureAgeasContracts.size());		
		assertEquals(1, accentureAccentureContracts.size());
		SupplyContract accentureBarclaysContract = accentureBarclaysContracts.get(0);
		SupplyContract accentureAgeasContract =   accentureAgeasContracts.get(0);
		SupplyContract accentureAccentureContract = accentureAccentureContracts.get(0);
		
		assertThat(accentureSupplycontracts,  Matchers.containsInAnyOrder(accentureBarclaysContract,  accentureAgeasContract,  accentureAccentureContract));
	}
		
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddSupplyContract() {
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
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());	
		
		/**Create the new SupplyContract*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);		
		assertNotNull(belfiusContract);
		SupplyContract newSupplyContract = new SupplyContract(accenture, belfiusContract, john);
		newSupplyContract.setStartDate(new Date());
	
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
		/**Amend the new SupplyContract */
		accenture.addSupplyContract(newSupplyContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(15, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); 	//New child created
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  
		
		/**Validate parent Supplier has all SupplyContracts*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertEquals(4, accenture.getSupplyContracts().size());		
		/**Validate the opposite association SupplyContract -> Supplier*/
		SupplyContract johnBelfiusAccentureSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(belfiusContract, accenture, john);
		assertNotNull(johnBelfiusAccentureSupplyContract);
		assertEquals(newSupplyContract, johnBelfiusAccentureSupplyContract);
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSupplyContract() {
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
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());	
		
		/**Chose a SupplyContract to remove*/
		SupplyContract staleSupplyContract = accentureSupplycontracts.get(0);
		
		/**Detach entities*/
		entityManager.clear();	
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);

		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
		/**Remove the SupplyContract */
		accenture.removeSupplyContract(staleSupplyContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); 	//child removed
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveSupplyContractsWithNullSet() {					
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
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());	

		/**Detach entities*/
		entityManager.clear();		
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	// Target orphan in SUPPLY_CONTRACT table
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
		/**Sets currents Accenture's SupplyContracts as orphans*/
		accenture.setSupplyContracts(null);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();					
		
		assertEquals(11, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); //3 orphans removed in SUPPLY_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  
		
		/**Test parent Supplier has no SupplyContract(s)*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertNotNull(accenture);
		assertEquals(0, accenture.getSupplyContracts().size());
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetSupplyContractsWithOrmOrphanRemove() {	
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
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Create the new SupplyContract to set to parent Supplier*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);		
		SupplyContract newSupplyContract = new SupplyContract(accenture, belfiusContract, john);
		newSupplyContract.setStartDate(new Date());
		List <SupplyContract> newSupplyContracts = new ArrayList<>();
		newSupplyContracts.add(newSupplyContract);
					
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	// Target orphan in SUPPLY_CONTRACT table
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
		/**Set the new SupplyContract(s) to the parent Supplier and leaves orphans*/
		accenture.setSupplyContracts(newSupplyContracts);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(12, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); //3 orphans removed and 1 new child created in SUPPLY_CONTRACT table.  Other tables remain unchanged. 	
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  
	
	}
	
	@Test
	public void testGetEmploymentContracts() {
		/**Find target Supplier to test*/
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertNotNull(accenture);
		
		/**Test Supplier -> EmploymentContract*/
		List <EmploymentContract> accentureEmploymentContracts =  accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());
		/**Find target EmploymentContract*/
		List <EmploymentContract> targetAccentureEmploymentContracts = employmentContractRepo.findBySupplier(accenture);
		assertEquals (1, targetAccentureEmploymentContracts.size());
		EmploymentContract targetAccentureEmploymentContract = targetAccentureEmploymentContracts.get(0);  
		/**Validate Supplier -> EmploymentContract has the target*/
		assertThat(accentureEmploymentContracts, Matchers.contains(targetAccentureEmploymentContract));		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddEmploymentContract() {
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
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());	
		
		/**Create the new EmploymentContract*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);		
		EmploymentContract newEmploymentContract = new EmploymentContract(john, accenture);
		newEmploymentContract.setStartDate(new Date());
	
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
		/**Amend the new EmploymentContract */
		accenture.addEmploymentContract(newEmploymentContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(7, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	//New child created
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); 			
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  
		
		/**Validate parent Supplier has all EmploymentContracts*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertEquals(2, accenture.getEmploymentContracts().size());		
		/**Validate the opposite association EmploymentContract -> Supplier*/
		List <EmploymentContract> johnAccentureEmploymentContracts = employmentContractRepo.findByStaffAndSupplier(john, accenture);
		assertEquals(1, johnAccentureEmploymentContracts.size());
		EmploymentContract johnAccentureEmploymentContract = johnAccentureEmploymentContracts.get(0);
		assertEquals(john, johnAccentureEmploymentContract.getStaff());
		assertEquals(accenture, johnAccentureEmploymentContract.getSupplier());
		assertThat(johnAccentureEmploymentContract.getId(), Matchers.greaterThan((long)0));
	}
	 
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEmploymentContract() {
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
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());	
		
		/**Chose a EmploymentContract to remove*/
		EmploymentContract staleEmploymentContract = accentureEmploymentContracts.get(0);
		
		/**Detach entities*/
		entityManager.clear();	
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);

		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));			
		/**Remove the SupplyContract */
		accenture.removeEmploymentContract(staleEmploymentContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	//child removed
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE)); 			
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE)); 
	
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveEmploymentContractsWithNullSet() {
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
							
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Verify parent Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());		
			
		/**Detach entities*/
		entityManager.clear();		
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	// Target orphan in  EMPLOYMENT_CONTRACT table
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));
		/**This sets current Accenture's EmploymenContracts as orphans*/
		accenture.setEmploymentContracts(null);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();				
		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	 //1 orphan removed in EMPLOYMENT_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  
		
		/**Test parent Supplier has no EmploymentContract(s)*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertNotNull(accenture);
		assertEquals(0, accenture.getEmploymentContracts().size());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetEmploymentContractsWithOrmOrphanRemove() {		
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
							
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Verify parent Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());	
				
		/**Create the new EmploymentContract to set to parent Supplier*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		EmploymentContract newEmploymentContract = new EmploymentContract(john, accenture);
		newEmploymentContract.setStartDate(new Date());
		List <EmploymentContract> newEmploymentContracts = new ArrayList<>();
		newEmploymentContracts.add(newEmploymentContract);
		
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	// Target orphan in  EMPLOYMENT_CONTRACT table
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));
		/**This sets new Accenture's EmploymenContracts and leaves orphans*/
		accenture.setEmploymentContracts(newEmploymentContracts);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();		
		
		assertEquals(6, countRowsInTable(jdbcTemplate, SchemaConstants.EMPLOYMENT_CONTRACT_TABLE));	 //1 orphan removed and 1 new child created in EMPLOYMENT_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(14, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, SchemaConstants.AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SchemaConstants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));  
		
		/**Validate parent Supplier has new EmploymentContract(s)*/
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		newEmploymentContracts = employmentContractRepo.findByStaffAndSupplier(john, accenture);
		assertEquals(1, newEmploymentContracts.size());
		newEmploymentContract = newEmploymentContracts.get(0);
		assertEquals(newEmploymentContract, accenture.getEmploymentContracts().get(0));		
		assertEquals(1, accenture.getEmploymentContracts().size());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDbRemoveSupplierWithCascadings() {
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
							
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Verify Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());
		
		/**Detach entities*/
		entityManager.clear();
		
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		
		/**Tests initial state of Suppliers table (the parent)
		* SUPPLIER_TABLE //ACCENTURE SUPPLIER_ID='1'
		* Tests the initial state of the children table(s) from the target Parent table
		* SUPPLY_CONTRACT_TABLE
		* EMPLOYMENT_CONTRACT_TABLE
		* Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.
		* CONTRACT_TABLE
		* STAFF_TABLE
		* Tests the initial state of the children table(s) from previous Parent table(s)
		* CONTRACT_SERVICE_AGREEMENT_TABLE
		*/
		UtilsTest.testSchemaInitialState(jdbcTemplate);
		entityManager.remove(accenture);
		entityManager.flush();
		entityManager.clear();
		
		UtilsTest.testStateAfterAccentureSupplierDelete(jdbcTemplate);
			
		/**Test Suppliers*/
		assertEquals(4, supplierRepo.findAll().size());
		accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertNull(accenture);
				
		/**Test  EmploymentContract children were cleared*/
		/**Can't really search anything here as all EmploymentContacts with target Supplier were cleared*/
		
		/**Test  Staff parents aren't cascaded*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertNotNull(amt);		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);	
		assertNotNull(john);
		
		/**Test SupplyContract children were cleared*/		
		/**Can't really search anything here as all SupplyContacts with target Supplier were cleared*/
		
		/**Test Contract children were cleared*/		
		/**Can't really search anything here as all Contacts with target Supplier were cleared*/
		
		/**Test Agreement children were cleared*/		
		/**Can't really search anything else here as all ContactServiceAgreements with target Supplier were cleared*/
		
	}
	
	@Test
	public void testNameIsNotNull() {
		Supplier supplier = new Supplier();
		Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
        assertFalse(violations.isEmpty());
		
	}

	@Test
	public void testToString() {
		Supplier supplier = new Supplier();
		assertThat(supplier.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}

}
