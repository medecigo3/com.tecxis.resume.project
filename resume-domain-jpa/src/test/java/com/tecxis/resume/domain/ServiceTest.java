package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.ACCENTURE_SUPPLIER;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.ARVAL;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.CONTRACT11_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT5_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.Constants.MICROPOLE;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.TEST_DESCRIPTION;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.Service.SERVICE_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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

import com.tecxis.resume.domain.id.ContractServiceAgreementId;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ServiceTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceRepository serviceRepo;

	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;
	
	@Autowired
	private  ContractRepository contractRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Service service = Utils.insertAService(MULE_ESB_CONSULTANT, entityManager);
		assertThat(service.getId(), Matchers.greaterThan((long)0));
		
	}
	
	@Test
	public void testSetId() {
		Service service = new Service();
		assertEquals(0, service.getId());
		service.setId(1);
		assertEquals(1, service.getId());
		
	}

	@Test
	public void testGetDesc() {
		Service service = new Service();	
		assertNull(service.getDesc()); 		
	}
	
	@Test
	public void testSetDesc() {
		Service service = new Service();	
		assertNull(service.getDesc());
		service.setDesc(TEST_DESCRIPTION);
		assertEquals(TEST_DESCRIPTION, service.getDesc());
	}
	

	@Test
	public void testGetName() {
		Service service = new Service();	
		assertNull(service.getName());		
	}
	
	@Test
	public void testSetName() {
		Service service = new Service();	
		assertNull(service.getName());
		service.setDesc(MULE_ESB_CONSULTANT);
		assertEquals(MULE_ESB_CONSULTANT, service.getDesc());		
	}

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddContractServiceAgreement() throws EntityExistsException {		
		/**Find a Service*/
		Service scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		
		
		/**Validate service to test*/
		assertNotNull(scmDevService);
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
				
		/**Get contract to insert*/	
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);			
		assertNotNull(fastconnectMicropoleContract);
		
		/**Validate Contract to insert*/
		assertEquals(1, fastconnectMicropoleContract.getContractServiceAgreements().size());		
		assertEquals(MICROPOLE, fastconnectMicropoleContract.getClient().getName());
		
		/**Validate SupplyContract*/	
		assertEquals(1, fastconnectMicropoleContract.getSupplyContracts().size());
		SupplyContract fastconnectMicropoleSupplyContract = fastconnectMicropoleContract.getSupplyContracts().get(0);		
		assertEquals(FASTCONNECT,fastconnectMicropoleSupplyContract.getSupplier().getName());	
		
		
		/**Validate ContractServiceAgreement table pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(fastconnectMicropoleContract);
		contractServiceAgreementId.setService(scmDevService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
		
		/**Validate state of current Service ContractServiceAgreements*/
		List <ContractServiceAgreement>  scmDevServiceContractServiceAgreements = scmDevService.getContractServiceAgreements();
		assertEquals(1, scmDevServiceContractServiceAgreements.size());
		Contract barclaysAccentureContract = scmDevServiceContractServiceAgreements.get(0).getContract();
		assertEquals(BARCLAYS,barclaysAccentureContract.getClient().getName());
		assertEquals(1, barclaysAccentureContract.getSupplyContracts().size());
		
		/**Validate Contract -> SupplyContract */
		SupplyContract barclaysAccentureSupplyContract = barclaysAccentureContract.getSupplyContracts().get(0);		
		assertEquals(ACCENTURE_SUPPLIER,barclaysAccentureSupplyContract.getSupplier().getName());

		/**Create new ContractServiceAgreement*/
		ContractServiceAgreement newContractServiceAgreement = new ContractServiceAgreement(fastconnectMicropoleContract, scmDevService);	
		
		/**Test ContractServiceAgreement table pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 	
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
		
		/**Add new ContractServiceAgreement to contract*/
		scmDevService.addContractServiceAgreement(newContractServiceAgreement);		
		/**Add new ContractServiceAgrement to the inverse association*/	
		fastconnectMicropoleContract.addContractServiceAgreement(newContractServiceAgreement);	
		entityManager.persist(newContractServiceAgreement);
		entityManager.merge(scmDevService);	
		entityManager.merge(fastconnectMicropoleContract);
		entityManager.flush();
		
		/**Test tables post test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 	
		assertEquals(14, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, SERVICE_TABLE));	
	
		/**Validate Service -> ContractServiceAgreements*/
		scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		 
		assertEquals(2, scmDevService.getContractServiceAgreements().size());
		
		/**Validate Contract -> ContractServiceAgreements*/
		fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);		
		assertEquals(2, fastconnectMicropoleContract.getContractServiceAgreements().size());
		assertTrue(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingContractServiceAgreementToService() throws EntityExistsException {				
		/**Find a Service to test*/
		Service tibcoEsbConsultant = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		
		/**Validate the service to test*/
		assertEquals(TIBCO_BW_CONSULTANT, tibcoEsbConsultant.getName());
		
		/**Validate contracts of the service to test*/		
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		List <ContractServiceAgreement> tibcoEsbContractServiceAgreements = tibcoEsbConsultant.getContractServiceAgreements();
		assertEquals(8, tibcoEsbContractServiceAgreements.size());		
		
		/**Find duplicate Contract to insert*/		
		Contract alternaArvalContract = contractRepo.getContractByName(CONTRACT11_NAME);		
		assertEquals(ARVAL,  alternaArvalContract.getClient().getName());
		assertEquals(1, alternaArvalContract.getSupplyContracts().size());
		
		/**Validate Contract -> SupplyContract asoc. in the duplicate*/
		SupplyContract alternaArvalSupplyContract = alternaArvalContract.getSupplyContracts().get(0);
		assertEquals(ALTERNA, alternaArvalSupplyContract.getSupplier().getName());
		
		/**Test that alternaArvalContract's ContractServiceAgreement exists in the list of Tibco-ESB ContractServiceAgreements*/
		assertEquals(1, alternaArvalContract.getContractServiceAgreements().size());
		ContractServiceAgreement alternaArvalContractServiceAgreement = alternaArvalContract.getContractServiceAgreements().get(0);
		org.junit.Assert.assertTrue(tibcoEsbContractServiceAgreements.contains(alternaArvalContractServiceAgreement));
					
		
		/**Create new ContractServiceAgreement*/
		ContractServiceAgreement duplicateContractServiceAgreement = new ContractServiceAgreement(alternaArvalContract, tibcoEsbConsultant);	
		
		/**Add Contract duplicate: expect error*/
		tibcoEsbConsultant.addContractServiceAgreement(duplicateContractServiceAgreement);
				
	}
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContractServiceAgreement1() {
		/**Find a contract*/				
		Contract alphatressContract = contractRepo.getContractByName(CONTRACT13_NAME);		

		/**validate Contract -> ContractServiceAgreements*/
		assertEquals(1, alphatressContract.getContractServiceAgreements().size());
		
		/**Find the Service  to test -> ContractServiceAgreements*/
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertNotNull(bwService);
		assertEquals(TIBCO_BW_CONSULTANT, bwService.getName());
		assertEquals(8, bwService.getContractServiceAgreements().size());
		
		/**Find the ContractServiceAgreement to remove*/
		ContractServiceAgreement alphatressBwContractServiceAgreement = contractServiceAgreementRepo.findByContractAndService(alphatressContract, bwService);		
		
		/**Validate Service has the ContractServiceAgreement we want to remove*/
		assertThat(bwService.getContractServiceAgreements(), Matchers.hasItem(alphatressBwContractServiceAgreement));
	
		
		/**Detach entities*/
		entityManager.clear();			
		bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		/**Remove the ContractServiceAgreement from the Service */
		assertTrue(bwService.removeContractServiceAgreement(alphatressBwContractServiceAgreement));
		entityManager.merge(bwService);		
		entityManager.flush();	
		assertEquals(12, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		
		/**validate Contract -> ContractServiceAgreements*/
		alphatressContract = contractRepo.getContractByName(CONTRACT13_NAME);
		assertEquals(0, alphatressContract.getContractServiceAgreements().size());
		
		/**Validate the Service  to test -> ContractServiceAgreements*/
		bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertEquals(7, bwService.getContractServiceAgreements().size());
		
		
		alphatressBwContractServiceAgreement = contractServiceAgreementRepo.findByContractAndService(alphatressContract, bwService);		
		assertNull(alphatressBwContractServiceAgreement);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContractServiceAgreementByContract() {
		/**Find a contract*/		
		Contract alternaArvalContract = contractRepo.getContractByName(CONTRACT11_NAME);
		
		/**validate Contract -> ContractServiceAgreements*/
		assertEquals(1, alternaArvalContract.getContractServiceAgreements().size());
		
		/**Find the Service  to test -> ContractServiceAgreements*/
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertNotNull(bwService);
		assertEquals(TIBCO_BW_CONSULTANT, bwService.getName());
		assertEquals(8, bwService.getContractServiceAgreements().size());	

				
		assertEquals(13, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 		
		/**Remove ContractServiceAgreement*/
		assertTrue(alternaArvalContract.removeContractServiceAgreement(bwService));
		assertTrue(bwService.removeContractServiceAgreement(alternaArvalContract));		
		entityManager.merge(alternaArvalContract);
		entityManager.merge(bwService);
		entityManager.flush();	
		assertEquals(12, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE)); 
		
		/**Validate the ContractServiceAgreement was removed*/
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(alternaArvalContract);
		contractServiceAgreementId.setService(bwService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}
	
	@Test
	public void testNameIsNotNull() {
		Service service = new Service();
		Set<ConstraintViolation<Service>> violations = validator.validate(service);
        assertFalse(violations.isEmpty());
		
	}
	
	@Test
	public void testToString() {
		Service service = new Service();
		service.toString();
	}

}
