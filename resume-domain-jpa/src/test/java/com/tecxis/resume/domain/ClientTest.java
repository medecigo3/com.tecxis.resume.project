package com.tecxis.resume.domain;

import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.repository.*;
import com.tecxis.resume.domain.util.Utils;
import org.hamcrest.Matchers;
import org.junit.Assert;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

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
		Long id =  insertClientInJpa(em ->{
			Client micropole = new Client();
			micropole.setName(MICROPOLE);
			em.persist(micropole);
			em.flush();
			return micropole.getId();
		}, 				
		entityManager, jdbcTemplateProxy);		
		assertThat(id).isGreaterThan(0L);
		
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(SUCCESS, isClientValid(micropole, MICROPOLE, new ArrayList<Contract> ()));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {			
		Client client = Utils.insertClient(SAGEMCOM, entityManager);
		Assert.assertThat(client.getId(), Matchers.greaterThan((long)0));
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
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Contracts_And_RemoveOrphansWithOrm() {//In the scope of RES-19, impl. RES-42

		/***Find and validate AGEAS Client to test*/
		final Client ageas = clientRepo.getClientByName(AGEAS);
		/**Find AGEAS Client contracts*/
		Contract ageasContract2 = contractRepo.getContractByName(CONTRACT2_NAME);
		/**Validate current Client -> Contract*/
		isClientValid(ageas, AGEAS, List.of(ageasContract2));


		/**Create new Client with new contract*/
		set_ClientAgeas_With_NewContracts_InJpa(
				em -> {
					/**Build new AGEAS contract*/
					Contract newAgeasContract = Utils.buildContract(ageas, NEW_AGEAS_CONTRACT_NAME);
					em.persist(newAgeasContract);
					em.flush();
				} ,
				em -> {
					Contract newAgeasContract = contractRepo.getContractByName(NEW_AGEAS_CONTRACT_NAME);
					ageas.setContracts(List.of(newAgeasContract));
					em.merge(ageas);
					em.flush();
					em.clear();

				}, entityManager, jdbcTemplateProxy);

		/**Validate Client with new contract*/
		Contract newAgeasContract = contractRepo.getContractByName(NEW_AGEAS_CONTRACT_NAME);
		isClientValid(ageas, AGEAS, List.of(newAgeasContract));
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Contracts_And_RemoveOrphansWithOrm_NullSet() {//In the scope of RES-19, impl. RES-42
		/***Find and validate AGEAS Client to test*/
		final Client ageas = clientRepo.getClientByName(AGEAS);
		/**Find AGEAS Client contracts*/
		Contract ageasContract2 = contractRepo.getContractByName(CONTRACT2_NAME);
		/**Validate current Client -> Contract*/
		isClientValid(ageas, AGEAS, List.of(ageasContract2));

		/**Create new Client with new contract*/
		set_ClientAgeas_With_NullContracts_InJpa(
				em -> {
					/**Nothing to do here*/
				} ,
				em -> {
					ageas.setContracts(null);
					em.merge(ageas);
					em.flush();
					em.clear();

				}, entityManager, jdbcTemplateProxy);

		/**Validate orphans are removed*/
		Client newAgeas = clientRepo.getClientByName(AGEAS);
		isClientValid(newAgeas, AGEAS, null);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveContract() {
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.removeContract(new Contract());		
		logger.warn("To remove a Contract's Client see ContractTest.test_ManyToOne_Update_Client_And_RemoveOrphansWithOrm");		
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
		Assert.assertThat(axeltisProjects.get(0), Matchers.oneOf(morningstartProjects.get(0), morningstartProjects.get(1)));
		Assert.assertThat(axeltisProjects.get(1), Matchers.oneOf(morningstartProjects.get(0), morningstartProjects.get(1)));
		
	}

	@Test(expected = UnsupportedOperationException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_UpdateProjects() {
		Client ageas = clientRepo.getClientByName(AGEAS);
		ageas.setProjects(new ArrayList<Project> ());
		logger.warn("To set a Client's Project see ProjectTest.test_ManyToOne_SetClient()");			
			
	}
	
	@Test
	public void test_OneToMany_Update_Projects_And_RemoveOrphansWithOrm() {
		logger.warn("To update a Contract's Client see ContractTest.test_ManyToOne_SetClientWithOrmOrhpanRemoval");
	}
	
	@Test
	public void test_OneToMany_Update_Projects_And_RemoveOrphansWithOrm_NullSet() {
		logger.warn("To update a Contract's Client see ContractTest.test_ManyToOne_SetClientWithOrmOrhpanRemoval");
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
		logger.warn("To remove a Client's Project see ProjectTest.test_ManyToOne_Update_Client_And_CascadeDelete()");
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
		
		
		deleteClientInJpa(em-> {
			/**Remove client*/
			em.remove(axeltis);
			em.flush();	//manually commit the transaction
			em.clear(); //Detach managed entities from persistence context to reload new change
			
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
	
	@Test
	public void testEquals() {
		Client sagemcom = buildClient(SAGEMCOM, CLIENT_SAGEMCOM_ID);
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		City paris = Utils.buildCity(new CityId(PARIS_ID, FRANCE_ID), PARIS);
			
		assertEquals(sagemcom, sagemcom);
		assertNotEquals(sagemcom,paris);
		assertNotEquals(sagemcom, null);
		assertNotEquals(sagemcom, "");		
		assertNotEquals(sagemcom,barclays);
		
	}

}
