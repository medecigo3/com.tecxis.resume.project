package com.tecxis.resume.persistence;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.commons.persistence.id.ContractId;
import com.tecxis.resume.Client;
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Constants;
import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ContractRepositoryTest {
	
	public static final String CONTRACT_TABLE = "Contract";	
	public static final String CONTRACT1_NAME = "BarclaysContract";
	public static final String CONTRACT2_NAME = "AgeasContract";
	public static final String CONTRACT3_NAME = "AccentureContract";
	public static final String CONTRACT4_NAME = "SagemcomContract";
	public static final String CONTRACT5_NAME = "MicropoleContract";
	public static final String CONTRACT6_NAME = "LbpContract";
	public static final String CONTRACT7_NAME = "AxeltisContract1";
	public static final String CONTRACT8_NAME = "EhContract";
	public static final String CONTRACT9_NAME = "AxeltisContract2";
	public static final String CONTRACT10_NAME = "SGContract";
	public static final String CONTRACT11_NAME = "ArvalContract";
	public static final String CONTRACT12_NAME = "HermesContract";
	public static final String CONTRACT13_NAME = "BelfiusContract";
	
	
	public static final int CONTRACT2_ID = 2;
	

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private  ContractRepository contractRepo;

	@Autowired
	private ClientRepository clientRepo;
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Client accenture = ClientTest.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = ContractTest.insertAContract(accenture, CONTRACT1_NAME, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(1, accentureContract.getId());
	}
	
	@Sql(
		    scripts = {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testFindInsertedContract() {
		Client barclays = ClientTest.insertAClient(Constants.BARCLAYS, entityManager);			
		Contract contractIn = ContractTest.insertAContract(barclays, CONTRACT1_NAME, entityManager);
		Contract contractOut = contractRepo.getContractByName(CONTRACT1_NAME);
		assertNotNull(contractOut);
		assertEquals(contractIn, contractOut);		
	
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteContract() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Client barclays = ClientTest.insertAClient(Constants.EULER_HERMES, entityManager);	
		Contract tempContract = ContractTest.insertAContract(barclays, CONTRACT1_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		contractRepo.delete(tempContract);
		assertNull(contractRepo.getContractByName(CONTRACT1_NAME));
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindById() {		
		Client micropole = clientRepo.getClientByName(Constants.MICROPOLE);
		assertEquals(Constants.MICROPOLE, micropole.getName());					
		Contract fastconnectMicropoleContract = contractRepo.findById(new ContractId(5L, micropole)).get();
		assertNotNull(fastconnectMicropoleContract);
		assertEquals(micropole, fastconnectMicropoleContract.getClient());		
		assertEquals(5L, fastconnectMicropoleContract.getId());
		
	}
		
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetContractByName() {
		Contract contract2 = contractRepo.getContractByName(CONTRACT2_NAME);
		assertEquals(CONTRACT2_NAME, contract2.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void findByClientOrderByIdAsc() {						
		/**Find contracts*/
		Client axeltis = clientRepo.getClientByName(Constants.AXELTIS);	
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Contract> contracts = contractRepo.findAll();
		assertEquals(13, contracts.size());
	}
	
	
}
