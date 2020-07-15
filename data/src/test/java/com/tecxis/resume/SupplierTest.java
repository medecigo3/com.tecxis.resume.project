package com.tecxis.resume;

import static com.tecxis.resume.persistence.ClientRepositoryTest.AGEAS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.EmploymentContractRepositoryTest.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.SUPPLIER_TABLE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.SUPPLY_CONTRACT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
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

import com.tecxis.resume.SupplyContract.SupplyContractId;
import com.tecxis.resume.persistence.ClientRepository;
import com.tecxis.resume.persistence.ClientRepositoryTest;
import com.tecxis.resume.persistence.ContractRepository;
import com.tecxis.resume.persistence.EmploymentContractRepository;
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

	@Test
	public void testGetSupplierId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContracts() {
		/**Find a Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		
		/**Find Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());
					
		/**Verify the Supplier*/
		assertEquals(ACCENTURE, accenture.getName());				
		
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
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddSupplyContract() {
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());		
								
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
		SupplyContract newSupplyContract = new SupplyContract(new SupplyContractId(accenture, belfiusContract, john));
	
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));			
		/**Amend the new SupplyContract */
		accenture.addSupplyContract(newSupplyContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(15, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); 	//New child created
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Validate parent Supplier has all SupplyContracts*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(4, accenture.getSupplyContracts().size());		
		/**Validate the opposite association SupplyContract -> Supplier*/
		SupplyContract johnBelfiusAccentureSupplyContract = supplyContractRepo.findBySupplyContractId_ContractAndSupplyContractId_SupplierAndSupplyContractId_Staff(belfiusContract, accenture, john);
		assertNotNull(johnBelfiusAccentureSupplyContract);
		assertEquals(newSupplyContract, johnBelfiusAccentureSupplyContract);
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSupplyContract() {
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());		
								
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
		accenture = supplierRepo.getSupplierByName(ACCENTURE);

		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));			
		/**Remove the SupplyContract */
		accenture.removeSupplyContract(staleSupplyContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); 	//child removed
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetSupplyContractsNullWithOrmOrphanRemove() {					
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());		
								
		/**Verify Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());		
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());	

		/**Detach entities*/
		entityManager.clear();		
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
		
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	// Target orphan in SUPPLY_CONTRACT table
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));			
		/**Sets currents Accenture's SupplyContracts as orphans*/
		accenture.setSupplyContracts(null);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();					
		
		assertEquals(11, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); //3 orphans removed in SUPPLY_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Test parent Supplier has no SupplyContract(s)*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertNotNull(accenture);
		assertEquals(0, accenture.getSupplyContracts().size());
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetSupplyContractsWithOrmOrphanRemove() {	
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());
										
		/**Verify Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());		
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Create the new SupplyContract to set to parent Supplier*/		
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		Contract belfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);		
		SupplyContract newSupplyContract = new SupplyContract(new SupplyContractId(accenture, belfiusContract, john));
		List <SupplyContract> newSupplyContracts = new ArrayList<>();
		newSupplyContracts.add(newSupplyContract);
					
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	// Target orphan in SUPPLY_CONTRACT table
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));			
		/**Set the new SupplyContract(s) to the parent Supplier and leaves orphans*/
		accenture.setSupplyContracts(newSupplyContracts);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(12, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); //3 orphans removed and 1 new child created in SUPPLY_CONTRACT table.  Other tables remain unchanged. 	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
	
	}
	
	@Test
	public void testGetEmploymentContracts() {
		fail("TODO");
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddEmploymentContract() {
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());		
								
		/**Verify Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());		
		
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());	
		
		/**Create the new EmploymentContract*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);		
		EmploymentContract newEmploymentContract = new EmploymentContract(john, accenture);
	
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));			
		/**Amend the new EmploymentContract */
		accenture.addEmploymentContract(newEmploymentContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(7, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//New child created
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); 			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Validate parent Supplier has all EmploymentContracts*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveEmploymentContract() {
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());		
								
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
		accenture = supplierRepo.getSupplierByName(ACCENTURE);

		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));			
		/**Remove the SupplyContract */
		accenture.removeEmploymentContract(staleEmploymentContract);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();	
		
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//child removed
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); 			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
	
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testEmploymentContractsNullWithOrmOrphanRemove() {
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());
							
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Verify parent Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());		
			
		/**Detach entities*/
		entityManager.clear();		
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
		
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	// Target orphan in  EMPLOYMENT_CONTRACT table
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**This sets current Accenture's EmploymenContracts as orphans*/
		accenture.setEmploymentContracts(null);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();				
		
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 //1 orphan removed in EMPLOYMENT_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Test parent Supplier has no EmploymentContract(s)*/
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertNotNull(accenture);
		assertEquals(0, accenture.getEmploymentContracts().size());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetEmploymentContractsWithOrmOrphanRemove() {		
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());
							
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Verify parent Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());	
				
		/**Create the new EmploymentContract to set to parent Supplier*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		EmploymentContract newEmploymentContract = new EmploymentContract(john, accenture);
		List <EmploymentContract> newEmploymentContracts = new ArrayList<>();
		newEmploymentContracts.add(newEmploymentContract);
		
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	// Target orphan in  EMPLOYMENT_CONTRACT table
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**This sets new Accenture's EmploymenContracts and leaves orphans*/
		accenture.setEmploymentContracts(newEmploymentContracts);
		entityManager.merge(accenture);
		entityManager.flush();
		entityManager.clear();		
		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 //1 orphan removed and 1 new child created in EMPLOYMENT_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));  		
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Validate parent Supplier has new EmploymentContract(s)*/
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
		newEmploymentContracts = employmentContractRepo.findByStaffAndSupplier(john, accenture);
		assertEquals(1, newEmploymentContracts.size());
		newEmploymentContract = newEmploymentContracts.get(0);
		assertEquals(newEmploymentContract, accenture.getEmploymentContracts().get(0));		
		assertEquals(1, accenture.getEmploymentContracts().size());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDbRemoveSupplierWithCascadings() {
		/**Find and verify the Supplier*/		
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier's Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());
							
		/**Verify Supplier -> SupplyContracts*/		
		List <SupplyContract> accentureSupplycontracts = accenture.getSupplyContracts();			
		assertEquals(3, accentureSupplycontracts.size());
		
		/**Verify Supplier -> EmploymentContracts*/
		List <EmploymentContract> accentureEmploymentContracts = accenture.getEmploymentContracts();
		assertEquals(1, accentureEmploymentContracts.size());
		
		/**Detach entities*/
		entityManager.clear();
		
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
		
		/**Tests initial state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //ACCENTURE SUPPLIER_ID='1'
		/**Tests the initial state of the children table(s) from the target Parent table*/
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.remove(accenture);
		entityManager.flush();
		entityManager.clear();
		
		/**See SQL cascadings applied to one-to-many relations*/
		/**SUPPLIER 	-> SUPPLY_CONTRACT 				Cascade.REMOVE*/
		/**SUPPLIER 	-> EMPLOYMENT_CONTRACT 			Cascade.REMOVE*/

			
		/**Cascadings in this sequence*/
		/**  SUPPLIER (P) -> SUPPLY_CONTRACT (c) */	
		/**      |                               */
		/**      |                      		 */
		/**      v                               */
		/** EMPLOYMENT_CONTRACT (c)              */
		
		/**Tests post state of Suppliers table (the parent)*/
		assertEquals(4, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(11, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));	//3 children with SUPPLIER_ID = '1' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(5, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	//1 child with SUPPLIER_ID = '1' removed from the EMPLOYMENT_CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between Contract -> SupplyContract*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); //3 children with SUPPLIER_ID = '1' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the CONTRACT table. 
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); //1 child with with SUPPLIER_ID = '1' previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.
		/**Tests the cascaded children of the OneToMany association between Contract -> ContractServiceAgreement */
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE)); //0 parents previously removed from CONTRACT table. That cascades to 0 children removed from the CONTRACT_SERVICE_AGREEMENT table. 
		
		/**Test Suppliers*/
		assertEquals(4, supplierRepo.findAll().size());
		accenture = supplierRepo.getSupplierByName(ACCENTURE);
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
		
		/**Test Contract_Service_Agreement children were cleared*/		
		/**Can't really search anything else here as all ContactServiceAgreements with target Supplier were cleared*/
		
	}

	public static Supplier insertASupplier(String name, EntityManager entityManager) {
		Supplier supplier = new Supplier();
		supplier.setName(name);
		entityManager.persist(supplier);
		entityManager.flush();
		assertThat(supplier.getId(), Matchers.greaterThan((long)0));
		return supplier;
	}

}