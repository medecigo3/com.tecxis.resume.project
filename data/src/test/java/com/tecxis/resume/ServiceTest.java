package com.tecxis.resume;

import static com.tecxis.resume.persistence.ClientRepositoryTest.ARVAL;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.MICROPOLE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ACCENTURE;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.FASTCONNECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityExistsException;
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

import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;
import com.tecxis.resume.persistence.ClientRepository;
import com.tecxis.resume.persistence.ContractRepository;
import com.tecxis.resume.persistence.ContractServiceAgreementRepository;
import com.tecxis.resume.persistence.ServiceRepository;
import com.tecxis.resume.persistence.StaffRepository;
import com.tecxis.resume.persistence.SupplierRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
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
	private StaffRepository staffRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private  ContractRepository contractRepo;
	
	@Autowired
	private ClientRepository clientRepo;

	@Test
	public void testGetServiceId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddContractServiceAgreement() throws EntityExistsException {		
		/**Find a Service*/
		Service scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		
		
		/**Validate service to test*/
		assertNotNull(scmDevService);
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
				
		/**Get contract to insert*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		List <Contract> fastconnectContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, fastconnect);	
		assertEquals(1, fastconnectContracts.size());
		Contract fastconnectContract = fastconnectContracts.get(0);
		assertNotNull(fastconnectContract);
		
		/**Validate contract to insert*/
		assertEquals(1, fastconnectContract.getContractServiceAgreements().size());		
		assertEquals(MICROPOLE, fastconnectContract.getClient().getName());
		assertEquals(FASTCONNECT,fastconnectContract.getSupplier().getName());	
		
		/**Validate ContractServiceAgreement table pre test state*/
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(fastconnectContract);
		contractServiceAgreementId.setService(scmDevService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
		
		/**Validate state of current Service ContractServiceAgreements*/
		List <ContractServiceAgreement>  scmDevServiceContractServiceAgreements = scmDevService.getContractServiceAgreements();
		assertEquals(1, scmDevServiceContractServiceAgreements.size());
		Contract barclaysAccentureContract = scmDevServiceContractServiceAgreements.get(0).getContractServiceAgreementId().getContract();
		assertEquals(BARCLAYS,barclaysAccentureContract.getClient().getName());
		assertEquals(ACCENTURE,barclaysAccentureContract.getSupplier().getName());

		/**Add new ContractServiceAgreement to contract*/
		scmDevService.addContractServiceAgreement(fastconnectContract);
		/**Add new ContractServiceAgrement to the inverse association*/	
		fastconnectContract.addContractServiceAgreement(scmDevService);	
		entityManager.merge(scmDevService);	
		entityManager.merge(fastconnectContract);
		entityManager.flush();
	
		/**Test ContractServiceAgreement table post test state*/
		assertEquals(15, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		 		
		assertEquals(2, scmDevService.getContractServiceAgreements().size());
		assertEquals(2, fastconnectContract.getContractServiceAgreements().size());
		assertTrue(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingContractServiceAgreementToService() throws EntityExistsException {				
		/**Find a Service to test*/
		Service tibcoEsbConsultant = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		
		/**Validate the service to test*/
		assertEquals(TIBCO_BW_CONSULTANT, tibcoEsbConsultant.getName());
		
		/**Validate contracts of the service to test*/		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		List <ContractServiceAgreement> tibcoEsbContractServiceAgreements = tibcoEsbConsultant.getContractServiceAgreements();
		assertEquals(9, tibcoEsbContractServiceAgreements.size());		
		
		/**Find duplicate Contract to insert*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);		
		Supplier alterna = supplierRepo.getSupplierByNameAndStaff(ALTERNA, amt);
		Client arval = clientRepo.getClientByName(ARVAL);		
		List <Contract> alternaArvalContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(arval, alterna);
		assertEquals(1, alternaArvalContracts.size());
		Contract alternaArvalContract = alternaArvalContracts.get(0);
		assertEquals(ARVAL,  alternaArvalContract.getClient().getName());
		assertEquals(ALTERNA, alternaArvalContract.getSupplier().getName());
		
		/**Test that alternaArvalContract's ContractServiceAgreement exists in the list of Tibco-ESB ContractServiceAgreements*/
		assertEquals(1, alternaArvalContract.getContractServiceAgreements().size());
		ContractServiceAgreement alternaArvalContractServiceAgreement = alternaArvalContract.getContractServiceAgreements().get(0);
		org.junit.Assert.assertTrue(tibcoEsbContractServiceAgreements.contains(alternaArvalContractServiceAgreement));
					
		/**Add Contract duplicate: expect error*/
		tibcoEsbConsultant.addContractServiceAgreement(alternaArvalContract);
				
	}
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContractServiceAgreement() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier alphatress = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, amt);
		Client belfius = clientRepo.getClientByName(BELFIUS);
		List <Contract> alphatressContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(belfius, alphatress);
		
		/**Get & validate contract */
		assertEquals(1, alphatressContracts.size());
		Contract alphatressContract = alphatressContracts.get(0);
		assertEquals(BELFIUS, alphatressContract.getClient().getName());
		assertEquals(ALPHATRESS, alphatressContract.getSupplier().getName());	
		
		/**Get Service & validate */
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertNotNull(bwService);
		assertEquals(TIBCO_BW_CONSULTANT, bwService.getName());
		assertEquals(9, bwService.getContractServiceAgreements().size());
	
		/**Remove the ContractServiceAgreement*/
		ContractServiceAgreement fasctconnectContractServiceAgreement = alphatressContract.getContractServiceAgreements().get(0);
		assertTrue(alphatressContract.removeContractServiceAgreement(fasctconnectContractServiceAgreement));
		assertTrue(bwService.removeContractServiceAgreement(fasctconnectContractServiceAgreement));
				
		/**Find the ContractServiceAgreement */		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.merge(alphatressContract);
		entityManager.merge(bwService);
		entityManager.flush();	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		assertEquals(0, alphatressContract.getContractServiceAgreements().size());
		assertEquals(8, bwService.getContractServiceAgreements().size());
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(alphatressContract);
		contractServiceAgreementId.setService(bwService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContract() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier alterna = supplierRepo.getSupplierByNameAndStaff(ALTERNA, amt);
		Client arval = clientRepo.getClientByName(ARVAL);
		List <Contract> alternaContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(arval, alterna);
		
		/**Get & validate contract */
		assertEquals(1, alternaContracts.size());
		Contract alternaContract = alternaContracts.get(0);
		assertEquals(ARVAL, alternaContract.getClient().getName());
		assertEquals(ALTERNA,alternaContract.getSupplier().getName());	
		
		/**Get Service & validate */
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertNotNull(bwService);
		assertEquals(TIBCO_BW_CONSULTANT, bwService.getName());
		
		/**Remove ContractServiceAgreement*/
		assertTrue(alternaContract.removeContractServiceAgreement(bwService));
		assertTrue(bwService.removeContractServiceAgreement(alternaContract));
				
		/**Find ContractServiceAgreement */		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.merge(alternaContract);
		entityManager.merge(bwService);
		entityManager.flush();	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(alternaContract);
		contractServiceAgreementId.setService(bwService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}

	public static Service insertAService(String name, EntityManager entityManager) {
		Service service = new Service();
		service.setName(name);		
		assertEquals(0, service.getServiceId());
		entityManager.persist(service);
		entityManager.flush();
		assertThat(service.getServiceId(), Matchers.greaterThan((long)0));
		return service;
	}

}
