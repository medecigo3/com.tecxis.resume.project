package com.tecxis.resume;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
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
		"classpath:test-transaction-context.xml",
		"classpath:validation-api-context.xml"} )
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
	private ProjectRepository projectRepo;
	
	@Autowired
	private Validator validator;
	
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
		Client ageas = clientRepo.getClientByName(Constants.AGEAS);
		assertEquals(Constants.AGEAS, ageas.getName());
		
		
		/**Get client contracts*/
		List<Contract> ageasContracts = ageas.getContracts();
		assertEquals(1, ageasContracts.size());
		/**Compare with fetched contract*/
		/**Get Supplier*/
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE);
		assertEquals(ACCENTURE, accenture.getName());		
		Contract ageasContract = contractRepo.getContractByName(Constants.CONTRACT2_NAME);
		assertNotNull(ageasContract);
		assertEquals(ageasContract, ageasContracts.get(0));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetContracts() {		
		Client ageas = clientRepo.getClientByName(Constants.AGEAS);
		ageas.setContracts(new ArrayList<Contract> ());
		//To update a Contract's Client see ContractTest.testSetClient()
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddContract() {
		Client ageas = clientRepo.getClientByName(Constants.AGEAS);
		ageas.addContract(new Contract());	
		//To update a Contract's Client see ContractTest.testSetClient()
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContract() {
		Client ageas = clientRepo.getClientByName(Constants.AGEAS);
		ageas.removeContract(new Contract());		
		//To remove a Contract's Client see ContractTest.testSetClient()		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetProjects() {
		Client axeltis = clientRepo.getClientByName(Constants.AXELTIS);
		assertEquals(Constants.AXELTIS, axeltis.getName());
				
		List <Project> axeltisProjects = axeltis.getProjects();
		assertEquals(2, axeltisProjects.size());
		
		/**Retrieve projects to test axeltis projects*/
		List <Project> morningstartProjects = projectRepo.findByName(Constants.MORNINGSTAR);
		assertEquals(2, morningstartProjects.size());
		assertThat(axeltisProjects.get(0), Matchers.oneOf(morningstartProjects.get(0), morningstartProjects.get(1)));
		assertThat(axeltisProjects.get(1), Matchers.oneOf(morningstartProjects.get(0), morningstartProjects.get(1)));
		
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetProjects() {
		Client ageas = clientRepo.getClientByName(Constants.AGEAS);
		ageas.setProjects(new ArrayList<Project> ());
		//To set a Client's Project see ProjectTest.testSetClient()		
			
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddProject() {
		Client ageas = clientRepo.getClientByName(Constants.AGEAS);
		ageas.addProject(new Project());
		//To add a Client's Project see ProjectTest.testSetClient()		
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveProject() {
		Client ageas = clientRepo.getClientByName(Constants.AGEAS);
		ageas.removeProject(new Project());
		//To remove a Client's Project see ProjectTest.testSetClient()		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveClient() {
	
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(Constants.AXELTIS);
		assertEquals(Constants.AXELTIS, axeltis.getName());
		
		/**Test Client -> Project*/
		assertEquals(2, axeltis.getProjects().size());
		
		/**Test Client -> Contract*/
		assertEquals(2, axeltis.getContracts().size());
		
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE)); 
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 
		assertEquals(13	, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		
		/**Remove client*/
		entityManager.remove(axeltis);
		/**Will remove the Client's orphans -> Project, Contract, etc*/
		entityManager.flush();
			
		/**Detach entities*/		
		entityManager.clear();
		
		/**Validate client doesn't exist*/
		assertNull(clientRepo.getClientByName(Constants.AXELTIS));

		assertEquals(12, countRowsInTable(jdbcTemplate, Constants.LOCATION_TABLE));
		assertEquals(47, countRowsInTable(jdbcTemplate, Constants.STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertEquals(11, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE)); 
		assertEquals(11, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 
		assertEquals(11	, countRowsInTable(jdbcTemplate, Constants.PROJECT_TABLE));
		assertEquals(11, countRowsInTable(jdbcTemplate, Constants.CLIENT_TABLE));
		
	}
	
	@Test
	public void testNameIsNotNull() {
		Client client = new Client();
		Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertFalse(violations.isEmpty());
		
	}
	
	@Test
	public void testToString() {
		fail("TODO");
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
