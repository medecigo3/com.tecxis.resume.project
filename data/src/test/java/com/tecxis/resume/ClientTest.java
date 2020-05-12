package com.tecxis.resume;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AGEAS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.CLIENT_TABLE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT2_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.LocationRepositoryTest.LOCATION_TABLE;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.MORNINGSTAR;
import static com.tecxis.resume.persistence.ProjectRepositoryTest.PROJECT_TABLE;
import static com.tecxis.resume.persistence.StaffProjectAssignmentRepositoryTest.STAFF_PROJECT_ASSIGNMENT_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.tecxis.resume.persistence.ContractRepository;
import com.tecxis.resume.persistence.ProjectRepository;
import com.tecxis.resume.persistence.SupplierRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ClientTest {
	
	private static Logger log = LogManager.getLogger();
	
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
	private ProjectRepository projectRepo;
	
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetContracts() {		
		Client ageas = clientRepo.getClientByName(AGEAS);
		assertEquals(AGEAS, ageas.getName());
		
		
		/**Get client contracts*/
		List<Contract> ageasContracts = ageas.getContracts();
		assertEquals(1, ageasContracts.size());
		/**Compare with fetched contract*/
		/**Get Supplier*/
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());		
		Contract ageasContract = contractRepo.getContractByName(CONTRACT2_NAME);
		assertNotNull(ageasContract);
		assertEquals(ageasContract, ageasContracts.get(0));
	}
	
	@Test
	public void testSetContracts() {		
		log.info("Client -> Contract association is managed through of the relationship owner (Contract).");
		//To update a Contract's Client see ContractTest.testSetClient()
	}
	
	@Test
	public void testAddContract() {
		log.info("Client -> Contract association is managed through of the relationship owner (Contract).");	
		//To update a Contract's Client see ContractTest.testSetClient()
	}

	@Test
	public void testRemoveContract() {
		log.info("Client -> Contract association is managed through of the relationship owner (Contract).");	
		//To remove a Contract's Client see ContractTest.testSetClient()		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetProjects() {
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
				
		List <Project> axeltisProjects = axeltis.getProjects();
		assertEquals(2, axeltisProjects.size());
		
		/**Retrieve projects to test axeltis projects*/
		List <Project> morningstartProjects = projectRepo.findByName(MORNINGSTAR);
		assertEquals(2, morningstartProjects.size());
		assertThat(axeltisProjects.get(0), Matchers.oneOf(morningstartProjects.get(0), morningstartProjects.get(1)));
		assertThat(axeltisProjects.get(1), Matchers.oneOf(morningstartProjects.get(0), morningstartProjects.get(1)));
		
	}

	@Test
	public void testSetProjects() {
		log.info("Client -> Project association is managed through of the relationship owner (Project).");	
		//To set a Client's Project see ProjectTest.testSetClient()		
			
	}

	@Test
	public void testAddProject() {
		log.info("Client -> Project association is managed through of the relationship owner (Project).");
		//To add a Client's Project see ProjectTest.testSetClient()		
	}

	@Test
	public void testRemoveProject() {
		log.info("Client -> Project association is managed through of the relationship owner (Project).");	
		//To remove a Client's Project see ProjectTest.testSetClient()		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveClient() {
	
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		
		/**Test Client -> Project*/
		assertEquals(2, axeltis.getProjects().size());
		
		/**Test Client -> Contract*/
		assertEquals(2, axeltis.getContracts().size());
		
		assertEquals(14, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(13	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		
		/**Remove client*/
		entityManager.remove(axeltis);
		/**Will remove the Client's orphans -> Project, Contract, etc*/
		entityManager.flush();
			
		/**Detach entities*/		
		entityManager.clear();
		
		/**Validate client doesn't exist*/
		assertNull(clientRepo.getClientByName(AXELTIS));

		assertEquals(12, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		assertEquals(47, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(11, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE)); 
		assertEquals(11, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 
		assertEquals(11	, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		assertEquals(11, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		
	}

	public static Client insertAClient(String name, EntityManager entityManager) {
		Client client = new Client();
		client.setName(name);
		assertEquals(0, client.getId());
		entityManager.persist(client);		
		entityManager.flush();
		assertThat(client.getId(), Matchers.greaterThan((long)0));
		return client;
		
	}

}
