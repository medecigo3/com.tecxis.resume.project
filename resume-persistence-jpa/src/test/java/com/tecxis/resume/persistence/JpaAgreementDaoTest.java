package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Agreement.AGREEMENT_TABLE;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.id.AgreementId;
import com.tecxis.resume.domain.repository.AgreementRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
public class JpaAgreementDaoTest {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	private AgreementRepository agreementRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired
	private  ContractRepository contractRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" }, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = Utils.insertService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId().longValue());
		/**Insert Contract*/
		Client belfius = Utils.insertClient(BELFIUS, entityManager);			
		Contract alphatressBarclaysContract = Utils.insertContract(belfius, CONTRACT13_NAME, entityManager);
		
		/**Insert Agreement */
		Agreement Agreement = Utils.insertAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(Agreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		/**Insert service*/
		Service muleEsbCons = Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		
		
		/**Insert Contract*/
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Contract accentureBarclaysContract = Utils.insertContract(barclays, CONTRACT1_NAME, entityManager);
		
		/**Insert Agreement */
		Agreement AgreementIn = Utils.insertAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertNotNull(AgreementIn);
		assertEquals(1, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		
		/** Build Agreement Id*/
		AgreementId AgreementId = new AgreementId();
		AgreementId.setContractId(accentureBarclaysContract.getId());
		AgreementId.setServiceId(muleEsbCons.getId());
		
		Agreement AgreementOut =agreementRepo.findById(AgreementId).get();		
		assertNotNull(AgreementOut);
		assertEquals(AgreementIn, AgreementOut);		
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = Utils.insertService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId().longValue());
		/**Insert Contract*/
		Client belfius = Utils.insertClient(BELFIUS, entityManager);			
		Contract alphatressBarclaysContract = Utils.insertContract(belfius, CONTRACT13_NAME, entityManager);
		
		/**Insert Agreement */
		Agreement tempAgreement = Utils.insertAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(tempAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
		
		/**Delete Agreement and test*/
		entityManager.remove(tempAgreement);	
		entityManager.flush();
		entityManager.clear();
		assertEquals(0, countRowsInTable(jdbcTemplate, AGREEMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(13, agreementRepo.count());
		List <Agreement> Agreements = agreementRepo.findAll();
		assertEquals(13, Agreements.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Agreement> pageableAgreement = agreementRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableAgreement.getSize());
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
		Agreement alphatressBelfiusBwService = agreementRepo.findByContractAndService(alphatressBelfiusContract, bwService);
		/**Validate the ConstractServiceAgreement*/
		assertNotNull(alphatressBelfiusBwService);
		/**Validate Contract  association*/
		assertEquals(alphatressBelfiusBwService.getContract(), alphatressBelfiusContract);
		/**Validate the Service association*/
		assertEquals(alphatressBelfiusBwService.getService(), bwService);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAgreement() {
		/**Get Service*/
		List <Service> j2eeDevelopperServices = serviceRepo.getServiceLikeName(J2EE_DEVELOPPER);		
		assertEquals(1, j2eeDevelopperServices.size());
		Service j2eeDevelopperService = j2eeDevelopperServices.get(0);	
		
		/**Get Contract*/
		List <Agreement> j2eeDevelopperAgreements = j2eeDevelopperService.getAgreements();
		assertNotNull(j2eeDevelopperAgreements);
		assertEquals(1, j2eeDevelopperAgreements.size());
		Contract j2eeDevelopperContract = j2eeDevelopperAgreements.get(0).getContract();
		assertEquals(CONTRACT4_NAME, j2eeDevelopperContract.getName());
		
		
		/**Find Agreement*/
		AgreementId AgreementId = new AgreementId();
		AgreementId.setContractId(j2eeDevelopperContract.getId());
		AgreementId.setServiceId(j2eeDevelopperService.getId());
		Agreement agreement = agreementRepo.findById(AgreementId).get();
		assertNotNull(agreement);
		assertEquals(j2eeDevelopperContract, agreement.getContract());
		assertEquals(j2eeDevelopperService, agreement.getService());
		
				
	}
}
