package com.tecxis.resume.persistence;


import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT1_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT1_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT2_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT2_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_STARTDATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

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

import com.tecxis.resume.Client;
import com.tecxis.resume.Contract;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ClientRepositoryTest {
	
	public static String CLIENT_TABLE =	"Client";
	public static String BARCLAYS = "Barclays";
	public static String AGEAS = "Ageas (Formerly Fortis)";
	public static String AGEAS_SHORT = "Ageas%";
	public static String ACCENTURE = "Accenture";
	public static String SAGEMCOM = "Sagemcom";
	public static String MICROPOLE = "Micropole";
	public static String BELFIUS = "Belfius Insurance";
	public static String AXELTIS = "Axeltis (Natixis group)";
	public static String EULER_HERMES = "Euler Hermes";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ClientRepository clientRepo;

	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Client barclays = insertAClient(BARCLAYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, barclays.getClientId());
		
		Client ageas = insertAClient(AGEAS, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(2, ageas.getClientId());
		
		Client accenture = insertAClient(ACCENTURE, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(3, accenture.getClientId());
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void findInsertedClient() {
		Client clientIn = insertAClient(BARCLAYS, entityManager);
		Client clientOut = clientRepo.getClientByName(clientIn.getName());
		assertEquals(clientIn, clientOut);
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
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
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetClientContracts() {
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		assertNotNull(barclays);
		assertEquals(BARCLAYS, barclays.getName());
		List <Contract> barclaysContractList = barclays.getContracts();
		assertNotNull(barclaysContractList);
		assertEquals(1, barclaysContractList.size());
		Contract barclaysContract = barclaysContractList.get(0);
		assertEquals(CONTRACT1_STARTDATE, barclaysContract.getStartDate());
		assertEquals(CONTRACT1_ENDDATE, barclaysContract.getEndDate());		
		Client ageas = clientRepo.getClientByName(AGEAS);
		assertNotNull(ageas);
		assertEquals(AGEAS, ageas.getName());
		assertNotNull(ageas.getContracts());
		assertEquals(1, ageas.getContracts().size());
		Contract ageasContract = ageas.getContracts().get(0);
		assertEquals(CONTRACT2_STARTDATE, ageasContract.getStartDate());
		assertEquals(CONTRACT2_ENDDATE, ageasContract.getEndDate());
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertNotNull(micropole);
		assertEquals(MICROPOLE, micropole.getName());
		assertNotNull(micropole.getContracts());
		assertEquals(1, micropole.getContracts().size());
		Contract micropoleContract = micropole.getContracts().get(0);
		assertEquals(CONTRACT5_STARTDATE, micropoleContract.getStartDate());
		assertEquals(CONTRACT5_ENDDATE, micropoleContract.getEndDate());
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertNotNull(axeltis.getContracts());
		List <Contract> axeltisContracts = axeltis.getContracts();
		assertEquals(2, axeltisContracts.size());
		Contract axeltisContract1 = axeltisContracts.get(0);
		assertThat(axeltisContract1.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT7_STARTDATE, CONTRACT9_STARTDATE)));
		assertThat(axeltisContract1.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT7_ENDDATE, CONTRACT9_ENDDATE)));
		Client belfius = clientRepo.getClientByName(BELFIUS);
		assertNotNull(belfius.getContracts());
		List <Contract> belfiusContracts = belfius.getContracts();
		assertEquals(1, belfiusContracts.size());
		Contract belfiusContract = belfiusContracts.get(0);
		assertEquals(belfiusContract.getStartDate(), CONTRACT13_STARTDATE);
		assertNull(belfiusContract.getEndDate());
		
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteClientByName() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Client tempClient = insertAClient(BARCLAYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		clientRepo.delete(tempClient);
		assertNull(clientRepo.getClientByName(SAGEMCOM));
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		fail("TODO");
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
