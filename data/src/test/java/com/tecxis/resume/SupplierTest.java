package com.tecxis.resume;

import static com.tecxis.resume.persistence.ClientRepositoryTest.AGEAS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.SUPPLIER_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.tecxis.resume.persistence.ClientRepository;
import com.tecxis.resume.persistence.ClientRepositoryTest;
import com.tecxis.resume.persistence.ContractRepository;
import com.tecxis.resume.persistence.ContractServiceAgreementRepository;
import com.tecxis.resume.persistence.StaffRepository;
import com.tecxis.resume.persistence.SupplierRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SupplierTest {
	
	private static Logger log = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private  ContractRepository contractRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;

	@Test
	public void testGetSupplierId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStaffId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetContracts() {
		/**Find a Supplier*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		
		/**Find Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());
					
		/**Verify the Supplier*/
		assertEquals(ACCENTURE, accenture.getName());				
		
		/**Check Contracts*/
		List <Contract> accentureContracts = accenture.getContracts();
		assertEquals(3, accentureContracts.size());		
		
		List <Contract> accentureBarclaysContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(barclays, accenture);
		List <Contract> accentureAgeasContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(ageas, accenture);
		List <Contract> accentureAccentureContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(accentureClient, accenture);		
		assertEquals(1, accentureBarclaysContracts.size());				
		assertEquals(1, accentureAgeasContracts.size());		
		assertEquals(1, accentureAccentureContracts.size());
		Contract accentureBarclaysContract = accentureBarclaysContracts.get(0);
		Contract accentureAgeasContract =   accentureAgeasContracts.get(0);
		Contract accentureAccentureContract = accentureAccentureContracts.get(0);
		
		assertThat(accentureContracts,  Matchers.containsInAnyOrder(accentureBarclaysContract,  accentureAgeasContract,  accentureAccentureContract));
	}

	@Test
	public void testSetContracts() {
		log.info("Supplier -> Contract association is managed through of the relationship owner Contract.");
		//To update the Contract's Supplier see ContractTest.testSetSupplier
	}

	@Test
	public void testAddContract() {
		log.info("Supplier -> Contract association is managed through of the relationship owner Contract.");
		//To update the Contract's Supplier see ContractTest.testSetSupplier
		
	}

	@Test
	public void testRemoveContract() {
		log.info("Supplier -> Contract association is managed through of the relationship owner Contract.");
		//To remove the Contract's Supplier see ContractTest.testRemoveContract
		//The mappedBy is set in the inverse side of the association (non-owing). Removing the Supplier (non-owing) works when setting the cascading strategy is set to to ALL (in the Supplier) 
		// Delete operation cascades to the owing association, in this case Contract. 
		//Whilst cascading strategy is not set to ALL, delete of a Supplier will throw a ConstraintViolationException: could not execute statement
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveSupplier() {
		/**Find a Supplier*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		
		/**Find Clients*/
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		Client ageas = clientRepo.getClientByName(AGEAS);
		Client accentureClient = clientRepo.getClientByName(ClientRepositoryTest.ACCENTURE);
		assertEquals(BARCLAYS, barclays.getName());
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ClientRepositoryTest.ACCENTURE, accentureClient.getName());
					
		/**Verify the Supplier*/
		assertEquals(ACCENTURE, accenture.getName());	
		
		/**Verify Supplier -> Contract*/
		assertEquals(3, accenture.getContracts().size());	
		
		/**Detach entities*/
		entityManager.clear();
		
		accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		
		/**Remove supplier*/
		assertEquals(6, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		/**Test cascading */
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Test orphans initial state*/
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.remove(accenture);
		entityManager.flush();
		entityManager.clear();
		
		/**Test Supplier -> Staff cascading */
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Only staff avail. is John*/
		List <Staff> allStaff = staffRepo.findAll();
		assertEquals(1, allStaff.size());		
		Staff john = staffRepo.getStaffByNameAndLastname(JOHN_NAME, JOHN_LASTNAME);		
		assertNotNull(john);
		assertEquals(john, allStaff.get(0));
		
		/**Test Supplier -> Staff orphans were removed */		
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		List <Supplier> allSuppliers = supplierRepo.findAll();
		assertEquals(1, allSuppliers.size());
		Supplier johnAlphatress = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, john);
		assertNotNull(johnAlphatress);
		assertEquals (johnAlphatress, allSuppliers.get(0));

		/**Test Supplier -> Contract orphans were removed*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		List <Contract> allContracts  = contractRepo.findAll();
		assertEquals(1, allContracts.size());
		Client belfius = clientRepo.getClientByName(BELFIUS);
		List <Contract> alphatressJohnContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(belfius, johnAlphatress);
		assertEquals(1, alphatressJohnContracts.size());
		assertEquals(johnAlphatress, alphatressJohnContracts.get(0).getSupplier());
		
		/**Test Supplier -> Contract_Service_Agreement orphans were removed*/		
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		List <ContractServiceAgreement> allContractServiceAgreements = contractServiceAgreementRepo.findAll();
		assertEquals(1, allContractServiceAgreements.size());
		Contract alphatressJohnContractServiceAgreement = allContractServiceAgreements.get(0).getContractServiceAgreementId().getContract();
		Contract alphatressJohnContract = alphatressJohnContracts.get(0);
		assertEquals(alphatressJohnContract, alphatressJohnContractServiceAgreement);
		
	}

	public static Supplier insertASupplier(Staff staff, String name, EntityManager entityManager) {
		Supplier supplier = new Supplier();
		supplier.setName(name);
		supplier.setStaff(staff);
		entityManager.persist(supplier);
		entityManager.flush();
		assertThat(supplier.getId(), Matchers.greaterThan((long)0));
		return supplier;
	}

}
