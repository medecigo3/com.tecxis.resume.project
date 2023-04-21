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
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.AgreementId;
import com.tecxis.resume.domain.repository.AgreementRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED) //this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class ServiceTest {
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private ServiceRepository serviceRepo;

	@Autowired
	private AgreementRepository agreementRepo;
	
	@Autowired
	private  ContractRepository contractRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Service service = Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		assertThat(service.getId(), Matchers.greaterThan((long)0));
		
	}
	
	@Test
	public void testSetId() {
		Service service = new Service();
		assertEquals(0L, service.getId().longValue());
		service.setId(1L);
		assertEquals(1L, service.getId().longValue());
		
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Add_Agreement() throws EntityExistsException {
		/**Find a Service*/
		Service scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		
		
		/**Validate service to test*/
		assertNotNull(scmDevService);
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
				
		/**Get contract to insert*/	
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);			
		assertNotNull(fastconnectMicropoleContract);
		
		/**Validate Contract to insert*/
		assertEquals(1, fastconnectMicropoleContract.getAgreements().size());		
		assertEquals(MICROPOLE, fastconnectMicropoleContract.getClient().getName());
		
		/**Validate SupplyContract*/	
		assertEquals(1, fastconnectMicropoleContract.getSupplyContracts().size());
		SupplyContract fastconnectMicropoleSupplyContract = fastconnectMicropoleContract.getSupplyContracts().get(0);		
		assertEquals(FASTCONNECT,fastconnectMicropoleSupplyContract.getSupplier().getName());	
		
		
		/**Validate  table pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
		AgreementId agreementId = new AgreementId();
		agreementId.setContractId(fastconnectMicropoleContract.getId());
		agreementId.setServiceId(scmDevService.getId());
		assertFalse(agreementRepo.findById(agreementId).isPresent());
		
		/**Validate state of current Service s*/
		List <Agreement>  scmDevServiceAgreements = scmDevService.getAgreements();
		assertEquals(1, scmDevServiceAgreements.size());
		Contract barclaysAccentureContract = scmDevServiceAgreements.get(0).getContract();
		assertEquals(BARCLAYS,barclaysAccentureContract.getClient().getName());
		assertEquals(1, barclaysAccentureContract.getSupplyContracts().size());
		
		/**Validate Contract -> SupplyContract */
		SupplyContract barclaysAccentureSupplyContract = barclaysAccentureContract.getSupplyContracts().get(0);		
		assertEquals(ACCENTURE_SUPPLIER,barclaysAccentureSupplyContract.getSupplier().getName());

		/**Create new */
		Agreement newAgreement = new Agreement(fastconnectMicropoleContract, scmDevService);	
		
		/**Test  table pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE)); 	
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));	
		
		/**Add new  to contract*/
		scmDevService.addAgreement(newAgreement);		
		/**Add new ContractServiceAgrement to the inverse association*/	
		fastconnectMicropoleContract.addAgreement(newAgreement);	
		entityManager.persist(newAgreement);
		entityManager.merge(scmDevService);	
		entityManager.merge(fastconnectMicropoleContract);
		entityManager.flush();
		
		/**Test tables post test state*/
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE)); 	
		assertEquals(14, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));	
	
		/**Validate Service -> s*/
		scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		 
		assertEquals(2, scmDevService.getAgreements().size());
		
		/**Validate Contract -> s*/
		fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);		
		assertEquals(2, fastconnectMicropoleContract.getAgreements().size());
		assertTrue(agreementRepo.findById(agreementId).isPresent());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_OneToMany_Add_Existing_Agreement() throws EntityExistsException {
		/**Find a Service to test*/
		Service tibcoEsbConsultant = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		
		/**Validate the service to test*/
		assertEquals(TIBCO_BW_CONSULTANT, tibcoEsbConsultant.getName());
		
		/**Validate contracts of the service to test*/		
		assertEquals(13, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
		List <Agreement> tibcoEsbAgreements = tibcoEsbConsultant.getAgreements();
		assertEquals(8, tibcoEsbAgreements.size());		
		
		/**Find duplicate Contract to insert*/		
		Contract alternaArvalContract = contractRepo.getContractByName(CONTRACT11_NAME);		
		assertEquals(ARVAL,  alternaArvalContract.getClient().getName());
		assertEquals(1, alternaArvalContract.getSupplyContracts().size());
		
		/**Validate Contract -> SupplyContract asoc. in the duplicate*/
		SupplyContract alternaArvalSupplyContract = alternaArvalContract.getSupplyContracts().get(0);
		assertEquals(ALTERNA, alternaArvalSupplyContract.getSupplier().getName());
		
		/**Test that alternaArvalContract's Agreement exists in the list of Tibco-ESB Agreements*/
		assertEquals(1, alternaArvalContract.getAgreements().size());
		Agreement alternaArvalAgreement = alternaArvalContract.getAgreements().get(0);
		org.junit.Assert.assertTrue(tibcoEsbAgreements.contains(alternaArvalAgreement));
					
		
		/**Create new Agreement*/
		Agreement duplicateAgreement = new Agreement(alternaArvalContract, tibcoEsbConsultant);	
		
		/**Add Contract duplicate: expect error*/
		tibcoEsbConsultant.addAgreement(duplicateAgreement);
				
	}
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_RemoveAgreement_by_Service_And_RemoveOrphansWithOrm() {
		/**Find a contract*/				
		Contract alphatressContract = contractRepo.getContractByName(CONTRACT13_NAME);		
		assertEquals(Constants.BELFIUS, alphatressContract.getClient().getName());
		assertEquals(Constants.CONTRACT13_NAME, alphatressContract.getName());

		/**validate Contract -> Agreements*/
		assertEquals(1, alphatressContract.getAgreements().size());
		
		/**Find the Service  to test -> Agreements*/
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertNotNull(bwService);
		assertEquals(TIBCO_BW_CONSULTANT, bwService.getName());
		assertEquals(8, bwService.getAgreements().size());
		
		/**Find the Agreement to remove*/
		Agreement alphatressBwAgreement = agreementRepo.findByContractAndService(alphatressContract, bwService);		
		
		/**Validate Service has the Agreement we want to remove*/
		assertThat(bwService.getAgreements(), Matchers.hasItem(alphatressBwAgreement));
	
		
		/**Detach entities*/
		entityManager.clear();			
		bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Remove the Agreement from the Service */
		assertTrue(bwService.removeAgreement(alphatressBwAgreement));
		assertEquals(7, bwService.getAgreements().size());
		entityManager.merge(bwService);		
		entityManager.flush();	
		SchemaUtils.testStateAfter_ServiceBw_Update_Agreement(jdbcTemplateProxy);
		
		/**validate Contract -> Agreements*/
		alphatressContract = contractRepo.getContractByName(CONTRACT13_NAME);
		assertEquals(0, alphatressContract.getAgreements().size());
		
		/**Validate the Service  to test -> Agreements*/
		bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertEquals(7, bwService.getAgreements().size());
		
		
		alphatressBwAgreement = agreementRepo.findByContractAndService(alphatressContract, bwService);		
		assertNull(alphatressBwAgreement);

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void test_OneToMany_Remove_Agreement_by_Contract_And_RemoveOrphansWithOrm() {
		/**Find a contract*/		
		Contract alternaArvalContract = contractRepo.getContractByName(CONTRACT11_NAME);
		
		/**validate Contract -> Agreements*/
		assertEquals(1, alternaArvalContract.getAgreements().size());
		
		/**Find the Service  to test -> Agreements*/
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertNotNull(bwService);
		assertEquals(TIBCO_BW_CONSULTANT, bwService.getName());
		assertEquals(8, bwService.getAgreements().size());	
				
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Remove Agreement*/
		assertTrue(alternaArvalContract.removeAgreement(bwService));
		assertTrue(bwService.removeAgreement(alternaArvalContract));		
		entityManager.merge(alternaArvalContract);
		entityManager.merge(bwService);
		entityManager.flush();	
		SchemaUtils.testStateAfter_ServiceBw_Delete_Agreements(jdbcTemplateProxy);
				
		/**Validate the Agreement was removed*/
		AgreementId agreementId = new AgreementId();
		agreementId.setContractId(alternaArvalContract.getId());
		agreementId.setServiceId(bwService.getId());
		assertFalse(agreementRepo.findById(agreementId).isPresent());
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
		assertThat(service.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}
		
	public void test_OneToMany_Update_Agreements_And_RemoveOrphansWithOrm() { 
		//TODO
	}
	
	public void test_OneToMany_Update_Agreements_And_RemoveOrphansWithOrm_NullSet() { 
		//TODO
	}
}
