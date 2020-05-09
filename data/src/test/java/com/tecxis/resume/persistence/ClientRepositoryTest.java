package com.tecxis.resume.persistence;


import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_NAME;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT13_STARTDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT1_ENDDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT1_STARTDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT2_ENDDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT2_STARTDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT5_ENDDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT5_STARTDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT7_ENDDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT7_STARTDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT9_ENDDATE;
import static com.tecxis.resume.persistence.SupplyContractRepositoryTest.CONTRACT9_STARTDATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Contract;
import com.tecxis.resume.SupplyContract;

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
	public static String ARVAL = "Arval";
	public static String LA_BANQUE_POSTALE = "La Banque Postale";
	public static String SG = "Societe Generale Investment Banking";
	
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
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, barclays.getId());
		
		Client ageas = ClientTest.insertAClient(AGEAS, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(2, ageas.getId());
		
		Client accenture = ClientTest.insertAClient(ACCENTURE, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(3, accenture.getId());
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void findInsertedClient() {
		Client clientIn = ClientTest.insertAClient(BARCLAYS, entityManager);
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
		List <Contract> accentureBarclaysContractList = barclays.getContracts();
		assertNotNull(accentureBarclaysContractList);
		assertEquals(1, accentureBarclaysContractList.size());
		Contract accentureBarclaysContract = accentureBarclaysContractList.get(0);
		List <SupplyContract> accentureBarclaysSupplyContracts = accentureBarclaysContract.getSupplyContracts();
		assertEquals(1, accentureBarclaysSupplyContracts.size());
		SupplyContract barclaysSupplyContract =  accentureBarclaysSupplyContracts.get(0);
		assertEquals(CONTRACT1_STARTDATE, barclaysSupplyContract.getStartDate());
		assertEquals(CONTRACT1_ENDDATE, barclaysSupplyContract.getEndDate());		
		Client ageas = clientRepo.getClientByName(AGEAS);
		assertNotNull(ageas);
		assertEquals(AGEAS, ageas.getName());
		assertNotNull(ageas.getContracts());
		assertEquals(1, ageas.getContracts().size());
		Contract accentureAgeasContract = ageas.getContracts().get(0);
		List <SupplyContract> accentureAgeasSupplyContracts = accentureAgeasContract.getSupplyContracts();
		assertEquals(1, accentureAgeasSupplyContracts.size());
		SupplyContract accentureAgeasSupplyContract = accentureAgeasSupplyContracts.get(0);		
		assertEquals(CONTRACT2_STARTDATE, accentureAgeasSupplyContract.getStartDate());
		assertEquals(CONTRACT2_ENDDATE, accentureAgeasSupplyContract.getEndDate());
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertNotNull(micropole);
		assertEquals(MICROPOLE, micropole.getName());
		assertNotNull(micropole.getContracts());
		assertEquals(1, micropole.getContracts().size());
		Contract fastconnectMicropoleContract = micropole.getContracts().get(0);
		List <SupplyContract> fastconnectMicropoleContracts = fastconnectMicropoleContract.getSupplyContracts();
		assertEquals(1, fastconnectMicropoleContracts.size());
		SupplyContract fastconnectMicropoleSupplyContract = fastconnectMicropoleContracts.get(0);
		assertEquals(CONTRACT5_STARTDATE, fastconnectMicropoleSupplyContract.getStartDate());
		assertEquals(CONTRACT5_ENDDATE, fastconnectMicropoleSupplyContract.getEndDate());
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertNotNull(axeltis.getContracts());
		List <Contract> fasctconnectAxeltisContracts = axeltis.getContracts();
		assertEquals(2, fasctconnectAxeltisContracts.size());
		Contract fastconnectAxeltisContract1 = fasctconnectAxeltisContracts.get(0);
		List <SupplyContract>  fastconnectAxeltisSupplyContracts1 = fastconnectAxeltisContract1.getSupplyContracts();
		assertEquals(1, fastconnectAxeltisSupplyContracts1.size());
		SupplyContract fastconnectAxeltisSupplyContract1 = fastconnectAxeltisSupplyContracts1.get(0);
		assertThat(fastconnectAxeltisSupplyContract1.getStartDate(), Matchers.is(Matchers.oneOf(CONTRACT7_STARTDATE, CONTRACT9_STARTDATE)));
		assertThat(fastconnectAxeltisSupplyContract1.getEndDate(), Matchers.is(Matchers.oneOf(CONTRACT7_ENDDATE, CONTRACT9_ENDDATE)));
		Client belfius = clientRepo.getClientByName(BELFIUS);
		assertNotNull(belfius.getContracts());
		List <Contract> alphatressBelfiusContracts = belfius.getContracts();
		assertEquals(1, alphatressBelfiusContracts.size());
		Contract belfiusContract = alphatressBelfiusContracts.get(0);
		assertEquals(CONTRACT13_NAME, belfiusContract.getName());
		List <SupplyContract> alphatressBelfiusSupplyContracts = belfiusContract.getSupplyContracts();
		assertEquals(2, alphatressBelfiusSupplyContracts.size());
		SupplyContract alphatressBelfiusSupplyContract =  alphatressBelfiusSupplyContracts.get(0);		
		assertEquals(alphatressBelfiusSupplyContract.getStartDate(), CONTRACT13_STARTDATE);
		assertNull(alphatressBelfiusSupplyContract.getEndDate());
		
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteClientByName() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Client tempClient = ClientTest.insertAClient(BARCLAYS, entityManager);
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
		List <Client> clients = clientRepo.findAll();
		assertEquals(12, clients.size());
	}

}
