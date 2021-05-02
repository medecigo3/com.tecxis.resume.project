package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT4_NAME;
import static com.tecxis.resume.domain.Constants.J2EE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.Service.SERVICE_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.id.ContractServiceAgreementId;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class JpaContractServiceAgreementDaoTest {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired
	private  ContractRepository contractRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertServiceWithContrastServiceAgreementsRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = Utils.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId());
		/**Insert Contract*/
		Client belfius = Utils.insertAClient(BELFIUS, entityManager);			
		Contract alphatressBarclaysContract = Utils.insertAContract(belfius, CONTRACT13_NAME, entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement contractServiceAgreement = Utils.insertAContractServiceAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(contractServiceAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedContractServiceAgreement() {
		/**Insert service*/
		Service muleEsbCons = Utils.insertAService(MULE_ESB_CONSULTANT, entityManager);
		
		
		/**Insert Contract*/
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);		
		Contract accentureBarclaysContract = Utils.insertAContract(barclays, CONTRACT1_NAME, entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement contractServiceAgreementIn = Utils.insertAContractServiceAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertNotNull(contractServiceAgreementIn);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
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
		Contract j2eeDevelopperContract = j2eeDevelopperContractServiceAgreements.get(0).getContract();
		assertEquals(CONTRACT4_NAME, j2eeDevelopperContract.getName());
		
		
		/**Find ContractServiceAgreement*/
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(j2eeDevelopperContract);
		contractServiceAgreementId.setService(j2eeDevelopperService);
		ContractServiceAgreement contractServiceAgreement = contractServiceAgreementRepo.findById(contractServiceAgreementId).get();
		assertNotNull(contractServiceAgreement);
		assertEquals(j2eeDevelopperContract, contractServiceAgreement.getContract());
		assertEquals(j2eeDevelopperService, contractServiceAgreement.getService());
		
				
	}
	
	@Test
	public void testFindByContractAndService() {		
		/**Fetch  Contract*/
		Contract alphatressBelfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);
		assertNotNull(alphatressBelfiusContract); 
		
		/**Fetch Service*/
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertNotNull(bwService);
		
		/**Fetch the ConstractServiceAgreement*/
		ContractServiceAgreement alphatressBelfiusBwService = contractServiceAgreementRepo.findByContractAndService(alphatressBelfiusContract, bwService);
		/**Validate the ConstractServiceAgreement*/
		assertNotNull(alphatressBelfiusBwService);
		/**Validate Contract  association*/
		assertEquals(alphatressBelfiusBwService.getContract(), alphatressBelfiusContract);
		/**Validate the Service association*/
		assertEquals(alphatressBelfiusBwService.getService(), bwService);
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteServiceContractAgreement() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = Utils.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId());
		/**Insert Contract*/
		Client belfius = Utils.insertAClient(BELFIUS, entityManager);			
		Contract alphatressBarclaysContract = Utils.insertAContract(belfius, CONTRACT13_NAME, entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement tempContractServiceAgreement = Utils.insertAContractServiceAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(tempContractServiceAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/**Delete ContraServiceAgreement and test*/
		entityManager.remove(tempContractServiceAgreement);	
		entityManager.flush();
		entityManager.clear();
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(13, contractServiceAgreementRepo.count());
		List <ContractServiceAgreement> contractServiceAgreements = contractServiceAgreementRepo.findAll();
		assertEquals(13, contractServiceAgreements.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <ContractServiceAgreement> pageableContractServiceAgreement = contractServiceAgreementRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableContractServiceAgreement.getSize());
	}
}