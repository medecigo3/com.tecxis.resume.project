package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ACCENTURE_CLIENT;
import static com.tecxis.resume.domain.Constants.AGEAS;
import static com.tecxis.resume.domain.Constants.AGEAS_SHORT;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT13_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT2_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT2_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT5_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT5_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT7_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT7_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT9_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT9_STARTDATE;
import static com.tecxis.resume.domain.Constants.MICROPOLE;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Client.CLIENT_TABLE;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class JpaClientDaoTest {
	
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
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(1, barclays.getId());
		
		Client ageas = Utils.insertAClient(AGEAS, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(2, ageas.getId());
		
		Client accenture = Utils.insertAClient(ACCENTURE_CLIENT, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		assertEquals(3, accenture.getId());
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void findInsertedClient() {
		Client clientIn = Utils.insertAClient(BARCLAYS, entityManager);
		Client clientOut = clientRepo.getClientByName(clientIn.getName());
		assertEquals(clientIn, clientOut);
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		Client tempClient = Utils.insertAClient(BARCLAYS, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		clientRepo.delete(tempClient);
		assertNull(clientRepo.getClientByName(SAGEMCOM));
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Client> clients = clientRepo.findAll();
		assertEquals(12, clients.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Client> pageableClient = clientRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableClient.getSize());
	}
}
