package com.tecxis.resume;
import static com.tecxis.resume.persistence.ClientRepositoryTest.ARVAL;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BELFIUS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.MICROPOLE;
import static com.tecxis.resume.persistence.ClientRepositoryTest.SAGEMCOM;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT_TABLE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.AMESYS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
		Contract alphatressContract = alphatressContracts.get(0);
		assertEquals(BELFIUS, alphatressContract.getClient().getName());
		assertEquals(ALPHATRESS,alphatressContract.getSupplier().getName());
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
		assertEquals(1, micropole.getContracts().size());
				
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
		entityManager.clear();
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
		/**Validate Contract association with Client*/
		List <Contract> alphatressNewContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, amesys);
		assertEquals(1, alphatressNewContracts.size());
		Contract amesysNewContract = alphatressNewContracts.get(0);
		assertEquals(MICROPOLE, amesysNewContract.getClient().getName());
		assertEquals(AMESYS, amesysNewContract.getSupplier().getName());	
		assertEquals(amesysContractId, amesysNewContract.getId());
		/**Validate the Client association with Contract*/
		micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(2, micropole.getContracts().size());
		assertThat(micropole.getContracts(), Matchers.hasItem(newContract));		
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
		sagemcom.addContract(alphatressContract);
		
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		entityManager.remove(amesysContract);	
		entityManager.persist(alphatressContract);
		entityManager.merge(sagemcom);
		entityManager.flush();
		entityManager.clear();
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
		/**Validate Contract-> Supplier*/
		List <Contract> alphatressContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(sagemcom, alphatress);
		assertEquals(1, alphatressContracts.size());
		alphatressContract = alphatressContracts.get(0);
		assertEquals(SAGEMCOM, alphatressContract.getClient().getName());
		assertEquals(ALPHATRESS,alphatressContract.getSupplier().getName());	
		assertEquals(amesysContractId, alphatressContract.getId());
		/**Validate the Supplier association*/
		alphatress = supplierRepo.getSupplierByNameAndStaff(ALPHATRESS, amt);
		assertEquals(2, alphatress.getContracts().size());
		assertThat(alphatress.getContracts(), Matchers.hasItem(alphatressContract));
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
		
		/**Validate service to insert**/
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
		assertEquals(1, scmDevService.getContractServiceAgreements().size());
		
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
		
		/**Remove the ContractServiceAgreement*/
		ContractServiceAgreement fasctconnectContractServiceAgreement = fastconnectContract.getContractServiceAgreements().get(0);
		assertTrue(fastconnectContract.removeContractServiceAgreement(fasctconnectContractServiceAgreement));
		assertTrue(muleService.removeContractServiceAgreement(fasctconnectContractServiceAgreement));
				
		/**Find the ContractServiceAgreement */		
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
	public void testGetContractServiceAgreements() {
		/**Find contracts*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		List <Contract> fastconnectMicropoleContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, fastconnect);
		
		/**Validate Contract*/
		assertEquals(1, fastconnectMicropoleContracts.size());
		
		/**Validate ContractServiceAgreements*/
		List <ContractServiceAgreement> fastconnectMicropoleContractServiceAgreements = fastconnectMicropoleContracts.get(0).getContractServiceAgreements();
		assertEquals(1, fastconnectMicropoleContractServiceAgreements.size());
		ContractServiceAgreement fastconnectMicropoleContractServiceAgreement = fastconnectMicropoleContractServiceAgreements.get(0);
		
		/*** Validate Contract */
		Contract fastconnectMicropoleContract = fastconnectMicropoleContractServiceAgreement.getContractServiceAgreementId().getContract();
        LocalDate startDate = LocalDate.of(2010, 7, 1);
        LocalDate endDate 	= LocalDate.of(2010, 8, 1);
        LocalDate contractStartDate = Instant.ofEpochMilli(fastconnectMicropoleContract.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate contractEndDate 	= Instant.ofEpochMilli(fastconnectMicropoleContract.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        assertTrue(startDate.isEqual(contractStartDate));
		assertTrue(endDate.isEqual(contractEndDate));
		/**Validate Client*/
		assertEquals(MICROPOLE, fastconnectMicropoleContract.getClient().getName());
		/**Validate Supplier*/
		assertEquals(FASTCONNECT, fastconnectMicropoleContract.getSupplier().getName());
		/**Validate Staff*/
		assertEquals(AMT_NAME, fastconnectMicropoleContract.getSupplier().getStaff().getName());
		/**Validate Service*/
		assertEquals(MULE_ESB_CONSULTANT, fastconnectMicropoleContractServiceAgreement.getContractServiceAgreementId().getService().getName());
			
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetContractServiceAgreements() {
		/**Find staff*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);

		/**Find & validate contract to test*/	
		Supplier alterna = supplierRepo.getSupplierByNameAndStaff(ALTERNA, amt);
		Client arval= clientRepo.getClientByName(ARVAL);
		List <Contract> alternaArvalContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(arval, alterna);
		assertEquals(1, alternaArvalContracts.size());	
		Contract alternaArvalContract = alternaArvalContracts.get(0);
        final LocalDate alternaArvalContractStartDate 	= LocalDate.of(2015, 6, 1);
        final LocalDate alternaArvalContractEndDate 		= LocalDate.of(2016, 3, 1);
        LocalDate contractStartDate = Instant.ofEpochMilli(alternaArvalContract.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate contractEndDate 	 =  Instant.ofEpochMilli(alternaArvalContract.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(alternaArvalContractStartDate, contractStartDate);
        assertEquals(alternaArvalContractEndDate, contractEndDate);
              
		/**Find & validate Services to test */
		Service muleService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		Service scmService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER); 
		assertEquals(MULE_ESB_CONSULTANT, muleService.getName());
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmService.getName());
		
		/***Validate the state of the current ContractServiceAgreeements*/
		assertEquals(1, alternaArvalContract.getContractServiceAgreements().size());
		ContractServiceAgreement  alternaArvalContractServiceAgreement = alternaArvalContract.getContractServiceAgreements().get(0);
		assertEquals(alternaArvalContract, alternaArvalContractServiceAgreement.getContractServiceAgreementId().getContract());
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		/**Validate opposite associations - Arval Contract has BW Service*/
		assertEquals(bwService, alternaArvalContractServiceAgreement.getContractServiceAgreementId().getService());
				
		/**Build new ContractServiceAgreements*/		
		ContractServiceAgreement alternaMuleContractServiceAgreement = new ContractServiceAgreement(new ContractServiceAgreementId(alternaArvalContract, muleService));
		ContractServiceAgreement alternaScmContractServiceAgreement = new ContractServiceAgreement(new ContractServiceAgreementId(alternaArvalContract, scmService));
		List <ContractServiceAgreement> newContractServiceAgreements = new ArrayList <> ();
		newContractServiceAgreements.add(alternaMuleContractServiceAgreement);
		newContractServiceAgreements.add(alternaScmContractServiceAgreement);
			
		/**Set ContractServiceAgreements*/	
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		alternaArvalContract.setContractServiceAgreements(newContractServiceAgreements);			
		assertEquals(2, alternaArvalContract.getContractServiceAgreements().size());
		entityManager.merge(alternaArvalContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(15, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/**Validate test*/
		alternaArvalContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(arval, alterna);
		assertEquals(1, alternaArvalContracts.size());	
		alternaArvalContract = alternaArvalContracts.get(0);
		assertEquals(2, alternaArvalContract.getContractServiceAgreements().size());
		
		ContractServiceAgreement alternaArvalContractServiceAgreement1 = alternaArvalContract.getContractServiceAgreements().get(0);
		ContractServiceAgreement alternaArvalContractServiceAgreement2 = alternaArvalContract.getContractServiceAgreements().get(1);
		
		/**Prepare & test contract dates*/
		LocalDate contract1StartDate 	= Instant.ofEpochMilli(alternaArvalContractServiceAgreement1.getContractServiceAgreementId().getContract().getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate contract1EndDate 		= Instant.ofEpochMilli(alternaArvalContractServiceAgreement1.getContractServiceAgreementId().getContract().getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();   
        LocalDate contract2StartDate 	= Instant.ofEpochMilli(alternaArvalContractServiceAgreement2.getContractServiceAgreementId().getContract().getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate contract2EndDate 		= Instant.ofEpochMilli(alternaArvalContractServiceAgreement2.getContractServiceAgreementId().getContract().getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();  
        
        assertEquals(alternaArvalContractStartDate, contract1StartDate);
        assertEquals(alternaArvalContractStartDate, contract2StartDate);
        assertEquals(alternaArvalContractEndDate, contract1EndDate);
        assertEquals(alternaArvalContractEndDate, contract2EndDate);
        
        /**Test Services*/
        assertThat(alternaArvalContractServiceAgreement1.getContractServiceAgreementId().getService(), Matchers.oneOf(muleService, scmService));
        assertThat(alternaArvalContractServiceAgreement2.getContractServiceAgreementId().getService(), Matchers.oneOf(muleService, scmService));
        
        /**Test the opposite association*/
        muleService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);
		scmService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);
		/**Test mule Service has all contracts*/
		assertEquals(2, muleService.getContractServiceAgreements().size());		
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		List <Contract> fastconnectMicropoleContracts = contractRepo.findByClientAndSupplierOrderByStartDateAsc(micropole, fastconnect);
		assertEquals(1, fastconnectMicropoleContracts.size());		
		Contract fastconnectMicropoleContract = fastconnectMicropoleContracts.get(0); 
		assertThat(muleService.getContractServiceAgreements().get(0).getContractServiceAgreementId().getContract(), Matchers.oneOf(alternaArvalContract,  fastconnectMicropoleContract));
		assertThat(muleService.getContractServiceAgreements().get(1).getContractServiceAgreementId().getContract(), Matchers.oneOf(alternaArvalContract,  fastconnectMicropoleContract));		
		/**Now test scm Service */
		assertEquals(2, scmService.getContractServiceAgreements().size());
		Supplier accenture =  supplierRepo.getSupplierByNameAndStaff(ACCENTURE, amt);
		Client barclays =  clientRepo.getClientByName(BARCLAYS);
		List <Contract> accentureBarclaysContracts =  contractRepo.findByClientAndSupplierOrderByStartDateAsc(barclays, accenture);
		assertEquals(1, accentureBarclaysContracts.size());
		/**Retreive 2nd scm Contract & test*/
		Contract accentureBarclaysContract = accentureBarclaysContracts.get(0);
		assertThat(scmService.getContractServiceAgreements().get(0).getContractServiceAgreementId().getContract(), Matchers.oneOf(alternaArvalContract, accentureBarclaysContract ));
		assertThat(scmService.getContractServiceAgreements().get(1).getContractServiceAgreementId().getContract(), Matchers.oneOf(alternaArvalContract,  accentureBarclaysContract));
				
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
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveContract() {
		/**Prepare contract associations*/
		Staff amt = staffRepo.getStaffLikeName(AMT_NAME);
		Supplier fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		
		/**Validate intital state of  Supplier and Client*/
		assertEquals(6, fastconnect.getContracts().size());
		assertEquals(1, micropole.getContracts().size());
	
		/**Detach entities*/
		entityManager.clear();
		
		/**Fetch and validate contract to test*/
		Contract.ContractPK contactPK = new Contract.ContractPK(5, micropole, fastconnect);
		Contract fastconnectContract = contractRepo.findById(contactPK).get();
		assertNotNull(fastconnectContract);
		
		/**Remove contract*/
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));		
		entityManager.remove(fastconnectContract);
		entityManager.flush();
		entityManager.clear();

		/**Test contract is removed*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(0, micropole.getContracts().size());	
		fastconnect = supplierRepo.getSupplierByNameAndStaff(FASTCONNECT, amt);
		assertEquals(5, fastconnect.getContracts().size());		
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
