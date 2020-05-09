package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT1_NAME;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT4_NAME;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.J2EE_DEVELOPPER;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.SERVICE_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

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
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractServiceAgreement;
import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;
import com.tecxis.resume.ContractServiceAgreementTest;
import com.tecxis.resume.ContractTest;
import com.tecxis.resume.Service;
import com.tecxis.resume.ServiceTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ContractServiceAgreementRepositoryTest {
	public static final String CONTRACT_SERVICE_AGREEMENT_TABLE = "CONTRACT_SERVICE_AGREEMENT";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertServiceWithContrastServiceAgreementsRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId());
		/**Insert Contract*/
		Client belfius = ClientTest.insertAClient(BELFIUS, entityManager);			
		Contract alphatressBarclaysContract = ContractTest.insertAContract(belfius, CONTRACT13_NAME, entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement contractServiceAgreement = ContractServiceAgreementTest.insertAContractServiceAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(contractServiceAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedContractServiceAgreement() {
		/**Insert service*/
		Service muleEsbCons = ServiceTest.insertAService(MULE_ESB_CONSULTANT, entityManager);
		
		
		/**Insert Contract*/
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Contract accentureBarclaysContract = ContractTest.insertAContract(barclays, CONTRACT1_NAME, entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement contractServiceAgreementIn = ContractServiceAgreementTest.insertAContractServiceAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertNotNull(contractServiceAgreementIn);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/** Build ContraServiceAgreement Id*/
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(accentureBarclaysContract);
		contractServiceAgreementId.setService(muleEsbCons);
		
		ContractServiceAgreement contractServiceAgreementOut =contractServiceAgreementRepo.findById(contractServiceAgreementId).get();		
		assertNotNull(contractServiceAgreementOut);
		assertEquals(contractServiceAgreementIn, contractServiceAgreementOut);		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindContractServiceAgreement() {
		/**Get Service*/
		List <Service> j2eeDevelopperServices = serviceRepo.getServiceLikeName(J2EE_DEVELOPPER);		
		assertEquals(1, j2eeDevelopperServices.size());
		Service j2eeDevelopperService = j2eeDevelopperServices.get(0);	
		
		/**Get Contract*/
		List <ContractServiceAgreement> j2eeDevelopperContractServiceAgreements = j2eeDevelopperService.getContractServiceAgreements();
		assertNotNull(j2eeDevelopperContractServiceAgreements);
		assertEquals(1, j2eeDevelopperContractServiceAgreements.size());
		Contract j2eeDevelopperContract = j2eeDevelopperContractServiceAgreements.get(0).getContractServiceAgreementId().getContract();
		assertEquals(CONTRACT4_NAME, j2eeDevelopperContract.getName());
		
		
		/**Find ContractServiceAgreement*/
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(j2eeDevelopperContract);
		contractServiceAgreementId.setService(j2eeDevelopperService);
		ContractServiceAgreement contractServiceAgreement = contractServiceAgreementRepo.findById(contractServiceAgreementId).get();
		assertNotNull(contractServiceAgreement);
		assertEquals(j2eeDevelopperContract, contractServiceAgreement.getContractServiceAgreementId().getContract());
		assertEquals(j2eeDevelopperService, contractServiceAgreement.getContractServiceAgreementId().getService());
		
				
	}
	
	@Test
	public void testFindByContractServiceAgreementId_contractAndContractServiceAgreementId_Service() {
		fail("TODO");
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteServiceContractAgreement() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId());
		/**Insert Contract*/
		Client belfius = ClientTest.insertAClient(BELFIUS, entityManager);			
		Contract alphatressBarclaysContract = ContractTest.insertAContract(belfius, CONTRACT13_NAME, entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement tempContractServiceAgreement = ContractServiceAgreementTest.insertAContractServiceAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(tempContractServiceAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/**Delete ContraServiceAgreement and test*/
		entityManager.remove(tempContractServiceAgreement);	
		entityManager.flush();
		entityManager.clear();
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(13, contractServiceAgreementRepo.count());
		List <ContractServiceAgreement> contractServiceAgreements = contractServiceAgreementRepo.findAll();
		assertEquals(13, contractServiceAgreements.size());
	}

}
