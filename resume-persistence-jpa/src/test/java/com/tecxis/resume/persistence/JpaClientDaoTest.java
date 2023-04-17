package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaClientDaoTest {
		
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private ContractRepository contractRepo;

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Contracts_And_RemoveOrhpansWithOrm(){//In the scope of RES-19, impl. RES-42
		/***Find and validate AGEAS Client to test*/
		final Client ageas = clientRepo.getClientByName(AGEAS);
		/**Find AGEAS Client contracts*/
		Contract ageasContract2 = contractRepo.getContractByName(CONTRACT2_NAME);
		/**Validate current Client -> Contract*/
		isClientValid(ageas, AGEAS, List.of(ageasContract2));


		/**Create new Client with new contract*/
		set_ClientAgeas_With_NewContracts_InJpa(
				contractRepo -> {
					/**Build new AGEAS contract*/
					Contract newAgeasContract = Utils.buildContract(ageas, NEW_AGEAS_CONTRACT_NAME);
					contractRepo.save(newAgeasContract);
					contractRepo.flush();
				} ,
				(clientRepo, contractRepo) -> {
					Contract newAgeasContract = contractRepo.getContractByName(NEW_AGEAS_CONTRACT_NAME);
					ageas.setContracts(List.of(newAgeasContract));
					clientRepo.save(ageas);
					clientRepo.flush();


				}, clientRepo, contractRepo, jdbcTemplateProxy);
		entityManager.clear();
		/**Validate Client with new contract*/
		Contract newAgeasContract = contractRepo.getContractByName(NEW_AGEAS_CONTRACT_NAME);
		isClientValid(ageas, AGEAS, List.of(newAgeasContract));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Update_Contracts_And_RemoveOrhpansWithOrm_NullSet(){//In the scope of RES-19, impl. RES-42
		/***Find and validate AGEAS Client to test*/
		final Client ageas = clientRepo.getClientByName(AGEAS);
		/**Find AGEAS Client contracts*/
		Contract ageasContract2 = contractRepo.getContractByName(CONTRACT2_NAME);
		/**Validate current Client -> Contract*/
		isClientValid(ageas, AGEAS, List.of(ageasContract2));


		/**Create new Client with new contract*/
		set_ClientAgeas_With_NullContracts_InJpa(
				contractRepo -> {
					/**Do nothing here*/
				} ,
				(clientRepo, contractRepo)-> {
					ageas.setContracts(null);
					clientRepo.save(ageas);
					clientRepo.flush();
				}, clientRepo, contractRepo, jdbcTemplateProxy);
		entityManager.clear();
		/**Validate orphans are removed*/
		Client newAgeas = clientRepo.getClientByName(AGEAS);
		isClientValid(newAgeas, AGEAS, null);
	}
	
	@Test
	public void test_OneToMany_SaveProjects() {
		logger.warn("To update a Projects's Client see JpaProjectDaoTest.test_ManyToOne_SaveClient()");
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testAdd() {
		Long id =  insertClientInJpa(clientRepo ->{
			Client micropole = new Client();
			micropole.setName(MICROPOLE);
			clientRepo.save(micropole);
			clientRepo.flush();
			return micropole.getId();
		}, 				
		clientRepo, jdbcTemplateProxy);		
		assertThat(id).isGreaterThan(0L);
		
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(SUCCESS, Utils.isClientValid(micropole, MICROPOLE, new ArrayList<>()));
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"})
	public void testDelete() {
		/**Find a Client to remove*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		
		/**Test Client -> Project*/
		assertEquals(2, axeltis.getProjects().size());
		
		/**Test Client -> Contract*/
		assertEquals(2, axeltis.getContracts().size());
		
		
		deleteClientInJpa(clientRepo-> {
			/**Remove client*/
			clientRepo.delete(axeltis);
			clientRepo.flush();	//manually commit the transaction
		}, clientRepo, jdbcTemplateProxy);

		entityManager.clear(); //Detach managed entities from persistence context to reload new change
		/**Validate client doesn't exist*/
		assertNull(clientRepo.getClientByName(AXELTIS));	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Client> clients = clientRepo.findAll();
		assertEquals(12, clients.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Client> pageableClient = clientRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableClient.getSize());
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetClientByName() {
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		assertNotNull(barclays);
		assertEquals(BARCLAYS, barclays.getName());		
		/**Tests query by name with LIKE expression*/
		Client ageasShort = clientRepo.getClientByName(AGEAS_SHORT);
		assertNotNull(ageasShort);
		assertTrue(ageasShort.getName().startsWith("Ageas"));
		Client ageas = clientRepo.getClientByName(AGEAS);
		assertNotNull(ageas);
		assertEquals(AGEAS, ageas.getName());
		assertEquals(ageas, ageasShort);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertNotNull(micropole);
		assertEquals(MICROPOLE, micropole.getName());
			
	}	
}
