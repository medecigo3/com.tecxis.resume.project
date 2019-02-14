package com.tecxis.resume;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.MICROPOLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.SAGEMCOM;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.AMESYS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.FASTCONNECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.Date;
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
import com.tecxis.resume.persistence.SupplierRepositoryTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ContractTest {
	
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
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetClient() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier alphatress = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, amt);
		Client belfius = clientRepo.getClientByName(BELFIUS);
		List <Contract> alphatressContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(belfius, alphatress);
		
		/**Validate Contract-> Supplier */
		assertEquals(1, alphatressContracts.size());
		Contract amesysContract = alphatressContracts.get(0);
		assertEquals(BELFIUS, amesysContract.getClient().getName());
		assertEquals(ALPHATRESS,amesysContract.getSupplier().getName());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetClient() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier amesys = supplierRepo.getSupplierByNameAndStaff(AMESYS, amt);
		Client sagemcom = clientRepo.getClientByName(SAGEMCOM);
		List <Contract> amesysContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(sagemcom, amesys);
		
		/**Validate Contract-> Client */
		assertEquals(1, amesysContracts.size());
		Contract amesysContract = amesysContracts.get(0);
		assertEquals(SAGEMCOM, amesysContract.getClient().getName());
		assertEquals(AMESYS,amesysContract.getSupplier().getName());
		final long amesysContractId = amesysContract.getId();
		
		/**Find Client to set*/
		Client micropole = clientRepo.getClientByName(MICROPOLE);
				
		/**Set new Contract ->Client*/
		Contract newContract = new Contract();
		newContract.setId(amesysContract.getId());
		newContract.setClient(micropole);	
		newContract.setSupplier(amesysContract.getSupplier());
		newContract.setStartDate(amesysContract.getStartDate());
		newContract.setEndDate(amesysContract.getEndDate());
		micropole.addContract(newContract);
		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		entityManager.remove(amesysContract);
		entityManager.persist(newContract);
		entityManager.merge(micropole);
		

		entityManager.flush();
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
		/**Validate Contract-> Supplier*/
		List <Contract> alphatressNewContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, amesys);
		assertEquals(1, alphatressNewContracts.size());
		Contract amesysNewContract = alphatressNewContracts.get(0);
		assertEquals(MICROPOLE, amesysNewContract.getClient().getName());
		assertEquals(AMESYS, amesysNewContract.getSupplier().getName());	
		assertEquals(amesysContractId, amesysNewContract.getId());
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplier() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier amesys = supplierRepo.getSupplierByNameAndStaff(AMESYS, amt);
		Client sagemcom = clientRepo.getClientByName(SAGEMCOM);
		List <Contract> amesysContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(sagemcom, amesys);
		
		/**Validate Contract-> Supplier */
		assertEquals(1, amesysContracts.size());
		Contract amesysContract = amesysContracts.get(0);
		assertEquals(SAGEMCOM, amesysContract.getClient().getName());
		assertEquals(AMESYS,amesysContract.getSupplier().getName());	
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetSupplier() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier amesys = supplierRepo.getSupplierByNameAndStaff(AMESYS, amt);
		Client sagemcom = clientRepo.getClientByName(SAGEMCOM);
		List <Contract> amesysContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(sagemcom, amesys);
		
		/**Validate Contract-> Supplier */
		assertEquals(1, amesysContracts.size());
		Contract amesysContract = amesysContracts.get(0);
		assertEquals(SAGEMCOM, amesysContract.getClient().getName());
		assertEquals(AMESYS,amesysContract.getSupplier().getName());	
		final long amesysContractId = amesysContract.getId();
		
		/**Find Supplier to set*/
		Supplier alphatress = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, amt);
				
		/**Set new Contract ->Supplier*/	
		Contract alphatressContract = new Contract();
		alphatressContract.setId(amesysContract.getId());
		alphatressContract.setClient(sagemcom);	
		alphatressContract.setSupplier(alphatress);
		alphatressContract.setStartDate(amesysContract.getStartDate());
		alphatressContract.setEndDate(amesysContract.getEndDate());
		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		entityManager.remove(amesysContract);	
		entityManager.persist(alphatressContract);
		entityManager.flush();
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
		/**Validate Contract-> Supplier*/
		List <Contract> alphatressContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(sagemcom, alphatress);
		assertEquals(1, alphatressContracts.size());
		alphatressContract = alphatressContracts.get(0);
		assertEquals(SAGEMCOM, alphatressContract.getClient().getName());
		assertEquals(ALPHATRESS,alphatressContract.getSupplier().getName());	
		assertEquals(amesysContractId, alphatressContract.getId());
		
	}

	@Test
	public void testGetServiceId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContractId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStaffId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEndDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStartDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetServices() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetServices() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddService() {
		fail("Not yet implemented");
	}
		
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddContractServiceAgreement() throws EntityExistsException {		
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		List <Contract> fastconnectContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, fastconnect);
		
		/**Validate contract to test*/
		assertEquals(1, fastconnectContracts.size());
		Contract fastconnectContract = fastconnectContracts.get(0);
		assertEquals(MICROPOLE, fastconnectContract.getClient().getName());
		assertEquals(FASTCONNECT,fastconnectContract.getSupplier().getName());	
		
		/**Get Service to insert*/
		Service scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		
		assertNotNull(scmDevService);
		
		/**Validate ContractServiceAgreement table pre test state*/
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(fastconnectContract);
		contractServiceAgreementId.setService(scmDevService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
		
		/**Validate state of current Contract ContractServiceAgreements*/
		List <ContractServiceAgreement> fastconnectContractServiceAgreements = fastconnectContract.getContractServiceAgreements();
		assertEquals(1, fastconnectContractServiceAgreements.size());
		Service muleEsbService = fastconnectContractServiceAgreements.get(0).getContractServiceAgreementId().getService();
		assertEquals(MULE_ESB_CONSULTANT, muleEsbService.getName());
		
		/**Validate service to insert**/
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
		assertEquals(1, scmDevService.getContractServiceAgreements().size());
				
		/**Add new ContractServiceAgreement to contract*/
		fastconnectContract.addContractServiceAgreement(scmDevService);
		/**Add new ContractServiceAgrement to the inverse association*/	
		scmDevService.addContractServiceAgreement(fastconnectContract);
		entityManager.merge(fastconnectContract);	
		entityManager.merge(scmDevService);
		entityManager.flush();
				
		/**Test ContractServiceAgreement table post test state*/
		assertEquals(15, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		 		
		assertEquals(2, fastconnectContract.getContractServiceAgreements().size());
		assertEquals(2, scmDevService.getContractServiceAgreements().size());
		assertTrue(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
		
	}
	
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingContractServiceAgreementToContract() throws EntityExistsException {		
			
		/**Find contracts*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		List <Contract> fastconnectMicropoleContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, fastconnect);
		
		/**Validate contract to test*/
		assertEquals(1, fastconnectMicropoleContracts.size());
		Contract fastconnectMicropoleContract = fastconnectMicropoleContracts.get(0);
		assertEquals(MICROPOLE, fastconnectMicropoleContract.getClient().getName());
		assertEquals(FASTCONNECT,fastconnectMicropoleContract.getSupplier().getName());	
		
		
		/**Validate services of the contract to test*/
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		assertEquals(1, fastconnectMicropoleContract.getContractServiceAgreements().size());
		Service currentService = fastconnectMicropoleContract.getContractServiceAgreements().get(0).getContractServiceAgreementId().getService();
		assertNotNull(currentService);
		assertEquals(MULE_ESB_CONSULTANT, currentService.getName());		
		
		/**Find duplicate Service to insert*/
		Service duplicateMuleEsbService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		assertNotNull(duplicateMuleEsbService);
		assertEquals(MULE_ESB_CONSULTANT, duplicateMuleEsbService.getName());	
		assertEquals(1, duplicateMuleEsbService.getContractServiceAgreements().size());
	
		
		/**Test that duplicateMuleEsbService exists in the list of Fastconnect-Micropole ContractServiceAgreements*/
		List <ContractServiceAgreement> fastconnectMicropoleContractServiceAgreements = fastconnectMicropoleContract.getContractServiceAgreements();
		ContractServiceAgreement duplicateMuleEsbContractServiceAgreement = duplicateMuleEsbService.getContractServiceAgreements().get(0);
		org.junit.Assert.assertTrue(fastconnectMicropoleContractServiceAgreements.contains(duplicateMuleEsbContractServiceAgreement));
		
		/**Add Service duplicate to the contract: expect error*/
		assertEquals(duplicateMuleEsbService,  currentService);
		fastconnectMicropoleContract.addContractServiceAgreement(duplicateMuleEsbService);
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContractServiceAgreement() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		List <Contract> fastconnectContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, fastconnect);
		
		/**Get & validate contract */
		assertEquals(1, fastconnectContracts.size());
		Contract fastconnectContract = fastconnectContracts.get(0);
		assertEquals(MICROPOLE, fastconnectContract.getClient().getName());
		assertEquals(FASTCONNECT,fastconnectContract.getSupplier().getName());	
		
		/**Get Service & validate */
		Service muleService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		assertNotNull(muleService);
		assertEquals(MULE_ESB_CONSULTANT, muleService.getName());
		assertEquals(1, muleService.getContractServiceAgreements().size());
		assertEquals(FASTCONNECT, muleService.getContractServiceAgreements().get(0).getContractServiceAgreementId().getContract().getSupplier().getName());
		assertEquals(MICROPOLE, muleService.getContractServiceAgreements().get(0).getContractServiceAgreementId().getContract().getClient().getName());
		
		/**Remove ContractServiceAgreement*/
		ContractServiceAgreement fasctconnectContractServiceAgreement = fastconnectContract.getContractServiceAgreements().get(0);
		assertTrue(fastconnectContract.removeContractServiceAgreement(fasctconnectContractServiceAgreement));
		assertTrue(muleService.removeContractServiceAgreement(fasctconnectContractServiceAgreement));
				
		/**Find ContractServiceAgreement */		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.merge(fastconnectContract);
		entityManager.merge(muleService);
		entityManager.flush();	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		assertEquals(0, fastconnectContract.getContractServiceAgreements().size());
		assertEquals(0, muleService.getContractServiceAgreements().size());
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(fastconnectContract);
		contractServiceAgreementId.setService(muleService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveService() {
		/**Find a contract*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier accenture = supplierRepo.getSupplierByNameAndStaff(SupplierRepositoryTest.ACCENTURE, amt);
		Client barclays = clientRepo.getClientByName(BARCLAYS);
		List <Contract> accentureContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(barclays, accenture);
		
		/**Get & validate contract */
		assertEquals(1, accentureContracts.size());
		Contract accentureContract = accentureContracts.get(0);
		assertEquals(BARCLAYS, accentureContract.getClient().getName());
		assertEquals(SupplierRepositoryTest.ACCENTURE,accentureContract.getSupplier().getName());	
		
		/**Get Service & validate */
		Service scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		
		assertNotNull(scmDevService);
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
		
		/**Remove ContractServiceAgreement*/
		assertTrue(accentureContract.removeContractServiceAgreement(scmDevService));
		assertTrue(scmDevService.removeContractServiceAgreement(accentureContract));
				
		/**Find ContractServiceAgreement */		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.merge(accentureContract);
		entityManager.merge(scmDevService);
		entityManager.flush();	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(accentureContract);
		contractServiceAgreementId.setService(scmDevService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}

	public static Contract insertAContract(Client client, Supplier supplier, Date startDate, Date endDate, EntityManager entityManager) {
		Contract contract  = new Contract();
		contract.setClient(client);	
		contract.setSupplier(supplier);
		contract.setStartDate(startDate);
		contract.setEndDate(endDate);
		entityManager.persist(contract);
		entityManager.flush();
		assertThat(contract.getId(), Matchers.greaterThan((long)0));
		return contract;
		
	}

}
