package com.tecxis.resume.persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	public void testInsertAClient() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		insertAClient(BARCLAYS, clientRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		insertAClient(AGEAS, clientRepo, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		insertAClient(ACCENTURE, clientRepo, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void findInsertedClient() {
		Client clientIn = insertAClient(BARCLAYS, clientRepo, entityManager);
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
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteClientByName() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Client tempClient = insertAClient(BARCLAYS, clientRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		clientRepo.delete(tempClient);
		assertNull(clientRepo.getClientByName(SAGEMCOM));
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}

	public static Client insertAClient(String name, ClientRepository clientRepo, EntityManager entityManager) {
		Client client = new Client();
		client.setName(name);
		assertEquals(0, client.getClientId());
		clientRepo.save(client);
		assertNotNull(client.getClientId());
		entityManager.flush();
		return client;
		
	}

}
