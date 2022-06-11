package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT2_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT9_NAME;
import static com.tecxis.resume.domain.Constants.EULER_HERMES;
import static com.tecxis.resume.domain.Constants.MICROPOLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.id.ContractId;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaContractDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private  ContractRepository contractRepo;

	@Autowired
	private ClientRepository clientRepo;
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);		
		Contract accentureContract = Utils.insertContract(axeltis, CONTRACT1_NAME, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		assertEquals(1, accentureContract.getId().getContractId());
	}
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testAdd() {
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);			
		Contract contractIn = Utils.insertContract(barclays, CONTRACT1_NAME, entityManager);
		Contract contractOut = contractRepo.getContractByName(CONTRACT1_NAME);
		assertNotNull(contractOut);
		assertEquals(contractIn, contractOut);		
	
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		Client eh = Utils.insertClient(EULER_HERMES, entityManager);	
		Contract tempContract = Utils.insertContract(eh, CONTRACT1_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		contractRepo.delete(tempContract);
		assertNull(contractRepo.getContractByName(CONTRACT1_NAME));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Contract> contracts = contractRepo.findAll();
		assertEquals(13, contracts.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Contract> pageableContract = contractRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableContract.getSize());
	}	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void findByClientOrderByIdAsc() {						
		/**Find contracts*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);	
		List <Contract> axeltisContracts = contractRepo.findByClientOrderByIdAsc(axeltis);
		
		/**Validate contracts*/
		assertEquals(2, axeltisContracts.size());
		
		/**Find target Contract(s) to validate*/
		Contract fastconnectAxeltisContract1 = contractRepo.getContractByName(CONTRACT7_NAME);
		Contract fastconnectAxeltisContract2 = contractRepo.getContractByName(CONTRACT9_NAME);
		
		/**Test found Contract(s)*/
		assertThat(axeltisContracts, Matchers.containsInRelativeOrder(fastconnectAxeltisContract1, fastconnectAxeltisContract2));
			
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetContractByName() {
		Contract contract2 = contractRepo.getContractByName(CONTRACT2_NAME);
		assertEquals(CONTRACT2_NAME, contract2.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindById() {		
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(MICROPOLE, micropole.getName());					
		Contract fastconnectMicropoleContract = contractRepo.findById(new ContractId(5L, micropole.getId())).get();
		assertNotNull(fastconnectMicropoleContract);
		assertEquals(micropole, fastconnectMicropoleContract.getClient());		
		assertEquals(5L, fastconnectMicropoleContract.getId().getContractId());
		
	}
	
	@Test
	public void test_ManyToOne_SaveClient() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	public void test_ManyToOne_SaveAgreement() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	public void test_ManyToOne_SupplyContract() {
		org.junit.Assert.fail("TODO");
	}
		
}
