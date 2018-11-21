package com.tecxis.resume;

import static com.tecxis.resume.persistence.ClientRepositoryTest.AGEAS;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_STARTDATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.tecxis.resume.persistence.ClientRepository;
import com.tecxis.resume.persistence.ContractRepository;

public class ClientTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private  ContractRepository contractRepo;

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
		/**Get client*/
		Client ageas = clientRepo.getClientByName(AGEAS);
		/**Get client contracts*/
		List<Contract> ageasContracts = ageas.getContracts();
		assertEquals(1, ageasContracts.size());
		/**Compare with fetched contract*/
		Contract ageasContract = contractRepo.getContractByStartDate(CONTRACT12_STARTDATE);
		assertNotNull(ageasContract);
		assertEquals(ageasContract, ageasContracts.get(0));
	}

	@Test
	public void testSetContracts() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddContract() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveContract() {
		fail("Not yet implemented");
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
