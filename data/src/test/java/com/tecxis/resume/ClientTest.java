package com.tecxis.resume;

import static com.tecxis.resume.StaffTest.insertAStaff;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AGEAS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.MICROPOLE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JHON_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JHON_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.FASTCONNECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import com.tecxis.resume.persistence.ClientRepository;
import com.tecxis.resume.persistence.ContractRepository;
import com.tecxis.resume.persistence.StaffRepository;
import com.tecxis.resume.persistence.SupplierRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ClientTest {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private  ContractRepository contractRepo;

	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Test
	public void testGetClientId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWebsite() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetContracts() {		
		Client ageas = clientRepo.getClientByName(AGEAS);
		assertEquals(AGEAS, ageas.getName());
		
		
		/**Get client contracts*/
		List<Contract> ageasContracts = ageas.getContracts();
		assertEquals(1, ageasContracts.size());
		/**Compare with fetched contract*/
		/**Get Staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		assertEquals(AMT_NAME, amt.getName());
		/**Get Supplier*/
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		assertEquals(ACCENTURE, accenture.getName());
		Contract.ContractPK contractPk = new Contract.ContractPK(2, ageas, accenture);
		Contract ageasContract = contractRepo.findById(contractPk).get();
		assertNotNull(ageasContract);
		assertEquals(ageasContract, ageasContracts.get(0));
	}
	
	@Test
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testSetContracts() {		
		/**Prepare staff, client and supplier*/
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALPHATRESS, entityManager);
		Contract barclaysContract = ContractTest.insertAContract(barclays, alphatress, CONTRACT12_STARTDATE, CONTRACT12_ENDDATE, entityManager);
		Staff john = insertAStaff(JHON_NAME, JHON_LASTNAME, entityManager);
		Client belfius = ClientTest.insertAClient(BELFIUS, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(john, ALTERNA,  entityManager);
		/**Create Contracts*/		
		Contract alternaContract = ContractTest.insertAContract(belfius, alterna, CONTRACT12_STARTDATE, null, entityManager);
		List <Contract> contracts = new ArrayList <> ();
		contracts.add(barclaysContract);
		contracts.add(alternaContract);
		
		/**Set client contracts*/		
		barclays.setContracts(contracts);
		
		
		/**Test client contracts*/
		assertThat(barclays.getContracts(), Matchers.hasItems(barclaysContract, alternaContract));
		assertEquals(2, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
	}
	
	@Test
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testAddContract() {
		/**Prepare staff, client and supplier*/	
		Staff john = insertAStaff(JHON_NAME, JHON_LASTNAME, entityManager);
		Client belfius = ClientTest.insertAClient(BELFIUS, entityManager);
		Supplier alterna = SupplierTest.insertASupplier(john, ALTERNA,  entityManager);
		/**Create Contracts*/
		
		/**Add contract*/
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Contract alternaContract = ContractTest.insertAContract(belfius, alterna, CONTRACT12_STARTDATE, null, entityManager);
		assertEquals(0, belfius.getContracts().size());		
		belfius.addContract(alternaContract);
		/**Test contract*/
		assertEquals(1, belfius.getContracts().size());
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveContract() {
		/**Prepare contract associations*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
	
		/**Detach entities*/
		entityManager.clear();
		
		/**Fetch and validate contract to test*/
		Contract.ContractPK contactPK = new Contract.ContractPK(5, micropole, fastconnect);
		Contract fastconnectContract = contractRepo.findById(contactPK).get();
		assertNotNull(fastconnectContract);
		
		/**Remove contract*/
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		entityManager.remove(fastconnectContract);
		entityManager.flush();
		entityManager.clear();

		/**Test contract is removed*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(0, micropole.getContracts().size());	
		
	}

	@Test
	public void testGetProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveProject() {
		fail("Not yet implemented");
	}

	public static Client insertAClient(String name, EntityManager entityManager) {
		Client client = new Client();
		client.setName(name);
		assertEquals(0, client.getClientId());
		entityManager.persist(client);		
		entityManager.flush();
		assertThat(client.getClientId(), Matchers.greaterThan((long)0));
		return client;
		
	}

}
