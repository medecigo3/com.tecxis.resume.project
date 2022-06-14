package com.tecxis.resume.domain;
import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.AGEAS;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.CONTRACT2_NAME;
import static com.tecxis.resume.domain.Constants.MICROPOLE;
import static com.tecxis.resume.domain.Constants.MORNINGSTAR;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Constants.SG_WEBSITE;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.deleteClientInJpa;
import static com.tecxis.resume.domain.util.Utils.insertClientInJpa;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"} )
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class ClientTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
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
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertClient() {		
		Long id =  insertClientInJpa(insertClientFunction ->{
			Client micropole = new Client();
			micropole.setName(MICROPOLE);
			entityManager.persist(micropole);
			entityManager.flush();
			return micropole.getId();
		}, 				
		entityManager, jdbcTemplateProxy);		
		assertThat(id).isGreaterThan(0L);
		
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(SUCCESS, Utils.isClientValid(micropole, MICROPOLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {			
		Client client = Utils.insertClient(SAGEMCOM, entityManager);
		assertThat(client.getId(), Matchers.greaterThan((long)0));		
	}
	
	@Test
	public void testSetId() {
		Client client = new Client();
		assertEquals(0L, client.getId().longValue());
		client.setId(1L);
		assertEquals(1L, client.getId().longValue());		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testGetName() {		
		Client client = clientRepo.getClientByName(BELFIUS);
		assertEquals(BELFIUS, client.getName());		
	}
	
	@Test
	public void testSetName() {
		Client client = new Client();
		assertNull(client.getName());
		client.setName(BELFIUS);
		assertEquals(BELFIUS, client.getName());		
		
	}

	@Test
	public void testGetWebsite() {
		Client client = new Client();
		assertNull(client.getWebsite());
		client.setWebsite(SG_WEBSITE);
		assertEquals(SG_WEBSITE, client.getWebsite());	
	}
	
	@Test
	public void testSetWebsite() {
		Client client = new Client();
		assertNull(client.getWebsite());
		client.setWebsite(SG_WEBSITE);
		assertEquals(SG_WEBSITE, client.getWebsite());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_GetContracts() {		
		Client ageas = clientRepo.getClientByName(AGEAS);
		assertEquals(AGEAS, ageas.getName());
		
		
		/**Get client contracts*/
		List<Contract> ageasContracts = ageas.getContracts();
		assertEquals(1, ageasContracts.size());
		/**Compare with fetched contract*/
		/**Get Supplier*/
		Supplier accenture = supplierRepo.getSupplierByName(ACCENTURE_SUPPLIER);
		assertEquals(ACCENTURE_SUPPLIER, accenture.getName());		
		Contract ageasContract = contractRepo.getContractByName(CONTRACT2_NAME);
		assertNotNull(ageasContract);
		assertEquals(ageasContract, ageasContracts.get(0));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_SetContracts() {		
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.setContracts(new ArrayList<Contract> ());
		logger.warn("To update a Contract's Client see ContractTest.test_ManyToOne_SetClientWithOrmOrhpanRemoval");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_AddContract() {
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.addContract(new Contract());	
		logger.warn("To add a Contract's Client see ContractTest.test_ManyToOne_SetClientWithOrmOrhpanRemoval");
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveContract() {
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.removeContract(new Contract());		
		logger.warn("To remove a Contract's Client see ContractTest.test_ManyToOne_SetClientWithOrmOrhpanRemoval");		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_GetProjects() {
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

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_SetProjects() {
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.setProjects(new ArrayList<Project> ());
		logger.warn("To set a Client's Project see ProjectTest.test_ManyToOne_SetClient()");			
			
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_AddProject() {
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.addProject(new Project());
		logger.warn("To add a Client's Project see ProjectTest.test_ManyToOne_SetClient()");		
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveProject() {
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.removeProject(new Project());
		logger.warn("To remove a Client's Project see ProjectTest.test_ManyToOne_SetClient()");		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveClient() {
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		
		/**Test Client -> Project*/
		assertEquals(2, axeltis.getProjects().size());
		
		/**Test Client -> Contract*/
		assertEquals(2, axeltis.getContracts().size());
		
		
		deleteClientInJpa(deleteClientFunction-> {
			/**Remove client*/
			entityManager.remove(axeltis);
			entityManager.flush();	//manually commit the transaction	
			entityManager.clear(); //Detach managed entities from persistence context to reload new change
			
		}, entityManager, jdbcTemplateProxy);	
		
		/**Validate client doesn't exist*/
		assertNull(clientRepo.getClientByName(AXELTIS));	
	}
	
	@Test
	public void testNameIsNotNull() {
		Client client = new Client();
		Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertFalse(violations.isEmpty());
		
	}

	@Test
	public void testToString() {
		Client client = new Client();		
		assertThat(client.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}

}
