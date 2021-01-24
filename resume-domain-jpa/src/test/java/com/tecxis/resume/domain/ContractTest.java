package com.tecxis.resume.domain;
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.sdf;
import static com.tecxis.resume.domain.repository.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.id.ContractServiceAgreementId;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml",
		"classpath:validation-api-context.xml"})
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

	@Autowired
	private SupplyContractRepository supplyContractRepo;
	
	@Autowired
	private Validator validator;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Client axeltis = ClientTest.insertAClient(Constants.AXELTIS, entityManager);	
		Contract contract = ContractTest.insertAContract(axeltis, Constants.CONTRACT9_NAME, entityManager);
		assertThat(contract.getId(), Matchers.greaterThan((long)0));
	}
	
	@Test
	public void testSetId() {
		Contract contract = new Contract();
		assertEquals(0, contract.getId());
		contract.setId(1);
		assertEquals(1, contract.getId());
	}


	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetClient() {
		/**Find a contract*/		
		Contract alphatressBelfiusContract = contractRepo.getContractByName(Constants.CONTRACT13_NAME);
		
		/**Validate Contract-> Client */
		assertEquals(Constants.BELFIUS, alphatressBelfiusContract.getClient().getName());
		
		/**Validate Contract-> SupplyContract */
		List <SupplyContract> alphatressBelfiusContracts = alphatressBelfiusContract.getSupplyContracts();
		assertEquals(2, alphatressBelfiusContracts.size());
		
		/**Validate Contract-> ContractServiceAgreement */
		assertEquals(1, alphatressBelfiusContract.getContractServiceAgreements().size());
		
	}
	
	/**See equivalent unit test in CityTest.testSetCountryWithOrmOrhpanRemoval*/
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetClientWithOrmOrhpanRemoval() {
		/**Find a Contract*/			
		Contract currentSagemContract = contractRepo.getContractByName(Constants.CONTRACT4_NAME);
		final long sagemContractId = currentSagemContract.getId();
				
		/**Validate Contract-> Client */
		Client sagem = currentSagemContract.getClient();
		assertEquals(Constants.SAGEMCOM, sagem.getName());				
		
		/**Validate Contract -> SupplyContract*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertNotNull(amt);		
		Supplier amesys = supplierRepo.getSupplierByName(Constants.AMESYS);
		assertEquals(1, currentSagemContract.getSupplyContracts().size());
		List <SupplyContract> existingAmesysSagemSupplyContracts = currentSagemContract.getSupplyContracts();
		SupplyContract amesysSagemSupplyContract =  existingAmesysSagemSupplyContracts.get(0);
		assertEquals(amesysSagemSupplyContract, supplyContractRepo.findByContractAndSupplierAndStaff(currentSagemContract, amesys, amt));
		assertEquals(Constants.CONTRACT4_ENDDATE, amesysSagemSupplyContract.getEndDate());
		assertEquals(Constants.CONTRACT4_STARTDATE, amesysSagemSupplyContract.getStartDate());
		
		/**Validate Supplier -> SupplyContract -> Contract*/		
		assertEquals(amesys, amesysSagemSupplyContract.getSupplier());
		
		/**Find new Client to set*/
		Client micropole = clientRepo.getClientByName(Constants.MICROPOLE);
		assertEquals(1, micropole.getContracts().size());
				
		/**Create new Contract with new Client*/
		Contract newMicropoleContract = new Contract();
		newMicropoleContract.setId(sagemContractId);
		newMicropoleContract.setClient(micropole);
		newMicropoleContract.setName(Constants.CONTRACT4_NAME);
		/**Set the new Contract with the SupplyContract (with new Client)*/	
		SupplyContract amesysMicropoleSupplyContract = new SupplyContract(amesys, newMicropoleContract, amt);
		amesysMicropoleSupplyContract.setStartDate(new Date());
		List <SupplyContract> amesysMicropoleSupplyContracts = new ArrayList<>();
		amesysMicropoleSupplyContracts.add(amesysMicropoleSupplyContract);
		newMicropoleContract.setSupplyContracts(amesysMicropoleSupplyContracts);

		/**These steps will update the Parent (non-owner of this relation)*/ 
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));		
		/**Firstly remove the Child (Owner)*/
		entityManager.remove(currentSagemContract);
		entityManager.flush();
		assertEquals(12, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));//1 orphan removed
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE)); //1 orphan removed
		/**Finally insert Child with new Parent (non-owner)*/
		entityManager.persist(newMicropoleContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));		
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		
		/**Validate new Contract*/
		newMicropoleContract = contractRepo.getContractByName(Constants.CONTRACT4_NAME);					
		assertEquals(sagemContractId, newMicropoleContract.getId());
		
		/**Validate new Contract ->  Client*/
		micropole = clientRepo.getClientByName(Constants.MICROPOLE);
		assertEquals(micropole, newMicropoleContract.getClient());	
		
		/**Validate old Contract -> Client*/
		Contract fcMicropoleContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		assertEquals(micropole, fcMicropoleContract.getClient());
		
		/**Now Client -> Contracts has 2 */
		assertEquals(2, micropole.getContracts().size());
		assertThat(micropole.getContracts(), Matchers.hasItems(newMicropoleContract, fcMicropoleContract));
		
		/**New Contract ->  ContractServiceAgreements has 0 */
		assertEquals(0, newMicropoleContract.getContractServiceAgreements().size());
		
		/**New Contract ->  SupplyContract has 1 */
		assertEquals(1, newMicropoleContract.getSupplyContracts().size());
		
		/**New SupplyContract -> Contract*/
		assertNotNull(supplyContractRepo.findByContractAndSupplierAndStaff(newMicropoleContract, amesys, amt));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplier() {
		/**Find a contract*/	
		Supplier amesys = supplierRepo.getSupplierByName(Constants.AMESYS);		
		Contract amesysSagemContract = contractRepo.getContractByName(Constants.CONTRACT4_NAME);
		
		/**Validate Contract-> Client */				
		assertEquals(Constants.SAGEMCOM, amesysSagemContract.getClient().getName());
		
		/**Validate Contract -> SupplyContract*/
		assertEquals(1, amesysSagemContract.getSupplyContracts().size());
		SupplyContract amesysSagemSupplyContract = amesysSagemContract.getSupplyContracts().get(0);
		assertEquals(Constants.AMESYS,amesysSagemSupplyContract.getSupplier().getName());	
		assertEquals(Constants.CONTRACT4_ENDDATE, amesysSagemSupplyContract.getEndDate());
		assertEquals(Constants.CONTRACT4_STARTDATE, amesysSagemSupplyContract.getStartDate());
		assertEquals(amesys, amesysSagemSupplyContract.getSupplier());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetSuppyContractWithNewStaffWithOrmOrphanRemoval() throws ParseException {
		final Date startDate = sdf.parse("12/30/2019");
		final Date endDate = sdf.parse("13/30/2019");
		
		/**Find a contract*/			
		Contract currentAmesysSagemContract = contractRepo.getContractByName(Constants.CONTRACT4_NAME);
		
		/**Validate Contract-> Client */
		assertEquals(Constants.SAGEMCOM, currentAmesysSagemContract.getClient().getName());		
		Client sagemcom = currentAmesysSagemContract.getClient();
		final long amesysContractId = currentAmesysSagemContract.getId();
		
		/**Validate Contract -> SupplyContract*/
		assertEquals(1, currentAmesysSagemContract.getSupplyContracts().size());
		SupplyContract amesysSagemSupplyContract =  currentAmesysSagemContract.getSupplyContracts().get(0);
		assertEquals(Constants.CONTRACT4_ENDDATE, amesysSagemSupplyContract.getEndDate());
		assertEquals(Constants.CONTRACT4_STARTDATE, amesysSagemSupplyContract.getStartDate());
		Supplier amesys = supplierRepo.getSupplierByName(Constants.AMESYS);
		assertEquals(amesys, amesysSagemSupplyContract.getSupplier());
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(amt, amesysSagemSupplyContract.getStaff());
		
		/**Create the new SupplyContract for the current Client -> Contract different Staff*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		SupplyContract newAmesysSagemSupplyContract = new SupplyContract(amesys, currentAmesysSagemContract, john);
		/**Set the new dates of the SuppyContract*/
		newAmesysSagemSupplyContract.setStartDate(startDate);
		newAmesysSagemSupplyContract.setEndDate(endDate);
		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));  
		
		/**Set Contract -> new SupplyContract*/
		List <SupplyContract> newAmesysSagemSupplyContracts = new ArrayList <>();
		newAmesysSagemSupplyContracts.add(newAmesysSagemSupplyContract);
		currentAmesysSagemContract.setSupplyContracts(newAmesysSagemSupplyContracts);
		entityManager.merge(currentAmesysSagemContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));				//1 orphan removed and 1 new child created in SUPPLY_CONTRACT table. Other tables remain unchanged.
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 						
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));  
		
		/**Validate the new Contract*/		
		currentAmesysSagemContract = contractRepo.getContractByName(Constants.CONTRACT4_NAME);
		assertNotNull(currentAmesysSagemContract);
		assertEquals(amesysContractId, currentAmesysSagemContract.getId());
		
		/**Validate the Contract -> Client*/
		sagemcom = clientRepo.getClientByName(Constants.SAGEMCOM);
		assertEquals(sagemcom, currentAmesysSagemContract.getClient());
		
		/**Validate the Client -> Contract*/		
		List <Contract> sagemcomcontracts = sagemcom.getContracts();
		assertEquals(1, sagemcomcontracts.size());
		assertEquals(currentAmesysSagemContract, sagemcomcontracts.get(0));

		/**Validate Contract -> SupplyContract*/		
		List <SupplyContract>  amesysSagemSupplyContracts =  currentAmesysSagemContract.getSupplyContracts();
		/**SupplyContract relation has 1 element updated with same contract id,  same Client (Sagem) & new Staff (John)*/		
		assertEquals(1, amesysSagemSupplyContracts.size());
		SupplyContract amesysSagemJohnSupplyContract = amesysSagemSupplyContracts.get(0);
		assertEquals(amesys, amesysSagemJohnSupplyContract.getSupplier());
		assertEquals(john,  amesysSagemJohnSupplyContract.getStaff());
		assertEquals(currentAmesysSagemContract, amesysSagemJohnSupplyContract.getContract());
		/**Validate SupplyContract -> Contract*/		
		amesysSagemJohnSupplyContract = supplyContractRepo.findByContractAndSupplierAndStaff(currentAmesysSagemContract, amesys, john);
		assertNotNull(amesysSagemJohnSupplyContract);		
		assertEquals(amesys, amesysSagemJohnSupplyContract.getSupplier());
		assertEquals(john,  amesysSagemJohnSupplyContract.getStaff());
		assertEquals(currentAmesysSagemContract, amesysSagemJohnSupplyContract.getContract());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContracts() {		
		/**Find target contract*/			
		Contract belfiusAlphatressContract = contractRepo.getContractByName(Constants.CONTRACT13_NAME);
		assertNotNull(belfiusAlphatressContract);
		
		/**Find target SupplyContracts*/
		Supplier alphatress = supplierRepo.getSupplierByName(ALPHATRESS);		
		assertNotNull(alphatress);
		List <SupplyContract> belfiusAlphatressSupplyContracts = supplyContractRepo.findByContractAndSupplierOrderByStartDateAsc(belfiusAlphatressContract, alphatress);
		assertEquals(2, belfiusAlphatressContract.getSupplyContracts().size());
		SupplyContract belfiusAlphatressSupplyContract1 = belfiusAlphatressSupplyContracts.get(0);
		SupplyContract belfiusAlphatressSupplyContract2 = belfiusAlphatressSupplyContracts.get(0);
		
		
		/**Validate Contract -> SupplyContract*/
		List<SupplyContract> belfiusSupplyContracts = belfiusAlphatressContract.getSupplyContracts();
		assertEquals(2, belfiusSupplyContracts.size());
		assertThat(belfiusSupplyContracts, Matchers.hasItems(belfiusAlphatressSupplyContract1, belfiusAlphatressSupplyContract2));		
	}
		
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddContractServiceAgreement() throws EntityExistsException {
		/**Find a contract*/				
		Contract micropoleFastconnectContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		assertEquals(1, micropoleFastconnectContract.getContractServiceAgreements().size());
			
		/**Validate contract to test*/		
		Client micropole = clientRepo.getClientByName(Constants.MICROPOLE);		
		assertEquals(micropole, micropoleFastconnectContract.getClient());		
		
		/**Get Service to insert*/
		Service scmDevService = serviceRepo.getServiceByName(Constants.SCM_ASSOCIATE_DEVELOPPER);		
		assertNotNull(scmDevService);
		
		/**Validate service to insert**/
		assertEquals(Constants.SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
		assertEquals(1, scmDevService.getContractServiceAgreements().size());
		
		/**Validate ContractServiceAgreement table pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(micropoleFastconnectContract);
		contractServiceAgreementId.setService(scmDevService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
		
		/**Validate state of current Contract -> ContractServiceAgreements*/
		List <ContractServiceAgreement> fastconnectContractServiceAgreements = micropoleFastconnectContract.getContractServiceAgreements();
		assertEquals(1, fastconnectContractServiceAgreements.size());
		Service muleEsbService = fastconnectContractServiceAgreements.get(0).getService();
		assertEquals(Constants.MULE_ESB_CONSULTANT, muleEsbService.getName());
		
		/**Create new ContractServiceAgreement*/
		ContractServiceAgreement newContractServiceAgreement = new ContractServiceAgreement(micropoleFastconnectContract, scmDevService);	
		
		/**Test tables pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.SERVICE_TABLE));				

	
		/**Add new ContractServiceAgreement -> Contract*/	
		micropoleFastconnectContract.addContractServiceAgreement(newContractServiceAgreement);
		/**Add new ContractServiceAgrement to the inverse association*/	
		scmDevService.addContractServiceAgreement(newContractServiceAgreement);
		entityManager.persist(newContractServiceAgreement);
		entityManager.merge(micropoleFastconnectContract);	
		entityManager.merge(scmDevService);
		entityManager.flush();
				
		/**Test tables post test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 	
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.SERVICE_TABLE));				
		
		/**Validate the Contract -> ContractServiceAgreements*/
		micropoleFastconnectContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		assertEquals(2, micropoleFastconnectContract.getContractServiceAgreements().size());
		
		/**Validate the Service -> ContractServiceAgreements */
		scmDevService = serviceRepo.getServiceByName(Constants.SCM_ASSOCIATE_DEVELOPPER);	
		assertEquals(2, scmDevService.getContractServiceAgreements().size());
		assertTrue(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
		
	}
	
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingContractServiceAgreementToContract() throws EntityExistsException {		
			
		/**Find contracts*/
		Contract micropoleFastconnectContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		
		/**Validate contract to test*/		
		Client micropole = clientRepo.getClientByName(Constants.MICROPOLE);		
		assertEquals(micropole, micropoleFastconnectContract.getClient());		
		
		
		
		/**Validate Services -> contract to test*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		assertEquals(1, micropoleFastconnectContract.getContractServiceAgreements().size());
		Service currentService = micropoleFastconnectContract.getContractServiceAgreements().get(0).getService();
		assertNotNull(currentService);
		assertEquals(Constants.MULE_ESB_CONSULTANT, currentService.getName());		
		
		/**Find duplicate Service to insert*/
		Service duplicateMuleEsbService = serviceRepo.getServiceByName(Constants.MULE_ESB_CONSULTANT);		
		assertNotNull(duplicateMuleEsbService);
		assertEquals(Constants.MULE_ESB_CONSULTANT, duplicateMuleEsbService.getName());	
		assertEquals(1, duplicateMuleEsbService.getContractServiceAgreements().size());
	
		
		/**Test that duplicateMuleEsbService exists in the list of Fastconnect-Micropole ContractServiceAgreements*/
		List <ContractServiceAgreement> micropoleFastconnectContractServiceAgreements = micropoleFastconnectContract.getContractServiceAgreements();
		ContractServiceAgreement duplicateMuleEsbContractServiceAgreement = duplicateMuleEsbService.getContractServiceAgreements().get(0);
		org.junit.Assert.assertTrue(micropoleFastconnectContractServiceAgreements.contains(duplicateMuleEsbContractServiceAgreement));
		
		/**Create duplicate ContractServiceAgreement*/
		ContractServiceAgreement duplicateContractServiceAgreement = new ContractServiceAgreement(micropoleFastconnectContract, duplicateMuleEsbService);
		
		/**Add Service duplicate to the contract: expect error*/
		assertEquals(duplicateMuleEsbService,  currentService);
		micropoleFastconnectContract.addContractServiceAgreement(duplicateContractServiceAgreement);
				
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetContractServiceAgreements() {
		/**Find a contract*/				
		Contract micropoleFastconnectContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
			
		/**Validate Contract to test*/		
		Client micropole = clientRepo.getClientByName(Constants.MICROPOLE);		
		assertEquals(micropole, micropoleFastconnectContract.getClient());		
		
		/**Validate Contract -> ContractServiceAgreements*/
		List <ContractServiceAgreement> micropoleFastconnectContractServiceAgreements = micropoleFastconnectContract.getContractServiceAgreements();
		assertEquals(1, micropoleFastconnectContractServiceAgreements.size());
		ContractServiceAgreement micropolefastconnectContractServiceAgreement = micropoleFastconnectContractServiceAgreements.get(0);
		
		/*** Validate Contract -> SupplyContract */		
		List <SupplyContract> micropoleFastconnectContracts = micropolefastconnectContractServiceAgreement.getContract().getSupplyContracts();
		assertEquals(1, micropoleFastconnectContracts.size());
		SupplyContract micropoleFastconnectSupplyContract = micropoleFastconnectContracts.get(0);
		/*** Isn't too bad to do a extra checks in the SupplyContract*/
		LocalDate startDate = LocalDate.of(2010, 7, 1);
	    LocalDate endDate 	= LocalDate.of(2010, 8, 1);        
        LocalDate contractStartDate = Instant.ofEpochMilli(micropoleFastconnectSupplyContract.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate contractEndDate 	= Instant.ofEpochMilli(micropoleFastconnectSupplyContract.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        assertTrue(startDate.isEqual(contractStartDate));
		assertTrue(endDate.isEqual(contractEndDate));
		/**Validate Contract ->  Client*/
		assertEquals(Constants.MICROPOLE, micropoleFastconnectContract.getClient().getName());
		/**Validate SupplyContract-> Supplier*/
		assertEquals(Constants.FASTCONNECT, micropoleFastconnectSupplyContract.getSupplier().getName());
		/**Validate SupplyContract-> Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(amt, micropoleFastconnectSupplyContract.getStaff());		
		/**Validate ContractServiceAgreement -> Service*/
		assertEquals(Constants.MULE_ESB_CONSULTANT, micropolefastconnectContractServiceAgreement.getService().getName());
			
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetContractServiceAgreementsWithOrmOrphanRemove() {
		/**Find a Contract*/		
		Contract arvalContract = contractRepo.getContractByName(Constants.CONTRACT11_NAME);
		
		/**Validate contract to test*/
		Client arval= clientRepo.getClientByName(Constants.ARVAL);
		assertEquals(arval, arvalContract.getClient());		
		
		
		/*** Validate Contract -> SupplyContract */
		List <SupplyContract> alternaArvalContracts = arvalContract.getSupplyContracts();
		assertEquals(1, alternaArvalContracts.size());			
		SupplyContract alternaArvalSupplyContract =  alternaArvalContracts.get(0);
		assertEquals(Constants.CONTRACT11_STARTDATE, alternaArvalSupplyContract.getStartDate());
		assertEquals(Constants.CONTRACT11_ENDDATE, alternaArvalSupplyContract.getEndDate());
		Supplier alterna = supplierRepo.getSupplierByName(Constants.ALTERNA);
		assertEquals(alterna, alternaArvalSupplyContract.getSupplier());
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(amt, alternaArvalSupplyContract.getStaff());
		
		
           
		/**Find & validate Services to test */		
		Service muleService = serviceRepo.getServiceByName(Constants.MULE_ESB_CONSULTANT);		
		Service scmService = serviceRepo.getServiceByName(Constants.SCM_ASSOCIATE_DEVELOPPER); 
		assertEquals(Constants.MULE_ESB_CONSULTANT, muleService.getName());
		assertEquals(Constants.SCM_ASSOCIATE_DEVELOPPER, scmService.getName());
		
		/***Validate the state of the current ContractServiceAgreeements*/		
		assertEquals(1, arvalContract.getContractServiceAgreements().size());
		ContractServiceAgreement  alternaArvalContractServiceAgreement = arvalContract.getContractServiceAgreements().get(0);
		assertEquals(arvalContract, alternaArvalContractServiceAgreement.getContract());
		Service bwService = serviceRepo.getServiceByName(Constants.TIBCO_BW_CONSULTANT);
		/**Validate opposite associations - Arval Contract has BW Service*/
		assertEquals(bwService, alternaArvalContractServiceAgreement.getService());
				
		/**Build new ContractServiceAgreements*/		
		ContractServiceAgreement alternaMuleContractServiceAgreement = new ContractServiceAgreement(arvalContract, muleService);
		ContractServiceAgreement alternaScmContractServiceAgreement = new ContractServiceAgreement(arvalContract, scmService);
		List <ContractServiceAgreement> newContractServiceAgreements = new ArrayList <> ();
		newContractServiceAgreements.add(alternaMuleContractServiceAgreement);
		newContractServiceAgreements.add(alternaScmContractServiceAgreement);
			
		/**Set ContractServiceAgreements*/	
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));  
		/**This sets new Arval's ContractServiceAgreements and leaves orphans */
		arvalContract.setContractServiceAgreements(newContractServiceAgreements);			
		assertEquals(2, arvalContract.getContractServiceAgreements().size());
		entityManager.merge(arvalContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE)); //1 orphan removed and 1 new child created in EMPLOYMENT_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));  
		
		/**Validate test*/			
		arvalContract = contractRepo.getContractByName(Constants.CONTRACT11_NAME);
		assertEquals(2, arvalContract.getContractServiceAgreements().size());
		
		ContractServiceAgreement alternaContractServiceAgreement1 = arvalContract.getContractServiceAgreements().get(0);
		ContractServiceAgreement alternaContractServiceAgreement2 = arvalContract.getContractServiceAgreements().get(1);
		
		/**Prepare & test that SupplyContracts have same dates*/
		List <SupplyContract> alternaSupplyContracts1 = alternaContractServiceAgreement1.getContract().getSupplyContracts();
		List <SupplyContract> alternaSupplyContracts2 = alternaContractServiceAgreement2.getContract().getSupplyContracts();
		assertEquals(1, alternaSupplyContracts1.size());
		assertEquals(1, alternaSupplyContracts2.size());
				
		 
		Date contract1StartDate 	= alternaSupplyContracts1.get(0).getStartDate();
        Date contract1EndDate 		= alternaSupplyContracts1.get(0).getEndDate();   
        Date contract2StartDate 	= alternaSupplyContracts2.get(0).getStartDate();
        Date contract2EndDate 		= alternaSupplyContracts2.get(0).getEndDate();  
        
        assertEquals(Constants.CONTRACT11_STARTDATE, contract1StartDate);
        assertEquals(Constants.CONTRACT11_ENDDATE, contract1EndDate);
        assertEquals(Constants.CONTRACT11_STARTDATE, contract2StartDate);
        assertEquals(Constants.CONTRACT11_ENDDATE, contract2EndDate);
        
        /**Test Services*/
        assertThat(alternaContractServiceAgreement1.getService(), Matchers.oneOf(muleService, scmService));
        assertThat(alternaContractServiceAgreement2.getService(), Matchers.oneOf(muleService, scmService));
        
        /**Test the opposite association*/
        muleService = serviceRepo.getServiceByName(Constants.MULE_ESB_CONSULTANT);
		scmService = serviceRepo.getServiceByName(Constants.SCM_ASSOCIATE_DEVELOPPER);
		/**Test mule Service has all contracts*/
		assertEquals(2, muleService.getContractServiceAgreements().size());				
		Contract micropoleContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);		
		assertThat(muleService.getContractServiceAgreements().get(0).getContract(), Matchers.oneOf(arvalContract,  micropoleContract));
		assertThat(muleService.getContractServiceAgreements().get(1).getContract(), Matchers.oneOf(arvalContract,  micropoleContract));		
		/**Now test scm Service */
		assertEquals(2, scmService.getContractServiceAgreements().size());
		Contract barclaysContract =  contractRepo.getContractByName(Constants.CONTRACT1_NAME);	
		assertNotNull(barclaysContract);
		/**Retreive 2nd scm Contract & test*/		
		assertThat(scmService.getContractServiceAgreements().get(0).getContract(), Matchers.oneOf(arvalContract, barclaysContract));
		assertThat(scmService.getContractServiceAgreements().get(1).getContract(), Matchers.oneOf(arvalContract,  barclaysContract));
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContractServiceAgreementByService() {
		/**Find  Contract*/		
		Contract barclaysAccentureContract = contractRepo.getContractByName(Constants.CONTRACT1_NAME);		
		
		/**Get Service & validate */
		Service scmDevService = serviceRepo.getServiceByName(Constants.SCM_ASSOCIATE_DEVELOPPER);		
		assertNotNull(scmDevService);
		assertEquals(Constants.SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
		
				
		/**Find ContractServiceAgreement */		
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));  
		/**Remove ContractServiceAgreement*/
		assertTrue(barclaysAccentureContract.removeContractServiceAgreement(scmDevService));
		assertTrue(scmDevService.removeContractServiceAgreement(barclaysAccentureContract));
		entityManager.merge(barclaysAccentureContract);
		entityManager.merge(scmDevService);
		entityManager.flush();	
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Validate the ContractServiceAgreement was removed*/
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(barclaysAccentureContract);
		contractServiceAgreementId.setService(scmDevService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveContractServiceAgreement() {			
		/**Get Service & validate */
		Service muleService = serviceRepo.getServiceByName(Constants.MULE_ESB_CONSULTANT);		
		assertNotNull(muleService);
		assertEquals(Constants.MULE_ESB_CONSULTANT, muleService.getName());
		assertEquals(1, muleService.getContractServiceAgreements().size());
		assertEquals(1, muleService.getContractServiceAgreements().get(0).getContract().getSupplyContracts().size());		
		assertEquals(Constants.FASTCONNECT, muleService.getContractServiceAgreements().get(0).getContract().getSupplyContracts().get(0).getSupplier().getName());
		assertEquals(Constants.MICROPOLE, muleService.getContractServiceAgreements().get(0).getContract().getClient().getName());
		
		/**Find target Contract & validate*/			
		Contract micropoleContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		assertEquals(1, micropoleContract.getContractServiceAgreements().size());				
	
		
		/**Find the ContractServiceAgreement to remove*/		
		ContractServiceAgreement staleContractServiceAgreement = contractServiceAgreementRepo.findById(new ContractServiceAgreementId(micropoleContract, muleService)).get();
		assertNotNull(staleContractServiceAgreement);

		/**Detach entities*/
		entityManager.clear();	
		micropoleContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
			

		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));  
		/**Remove the ContractServiceAgreement*/		
		assertTrue(micropoleContract.removeContractServiceAgreement(staleContractServiceAgreement));		
		entityManager.merge(micropoleContract);
		entityManager.flush();	
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));  
		
		/**Test that  ContractServiceAgreement was removed */
		micropoleContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		assertEquals(0, micropoleContract.getContractServiceAgreements().size());
		muleService = serviceRepo.getServiceByName(Constants.MULE_ESB_CONSULTANT);
		assertEquals(0, muleService.getContractServiceAgreements().size());
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(micropoleContract);
		contractServiceAgreementId.setService(muleService);
		assertFalse(contractServiceAgreementRepo.findById(contractServiceAgreementId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testDbRemoveContractwithCascadings() {
		/**Find and validate Contract to test*/
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		assertNotNull(fastconnectMicropoleContract);
				
		/**Verify Contract -> Client*/
		Client micropole = clientRepo.getClientByName(Constants.MICROPOLE);
		assertEquals(micropole, fastconnectMicropoleContract.getClient());
		
		/**Verify Contract -> SupplySontract(s)*/
		assertEquals(1, fastconnectMicropoleContract.getSupplyContracts().size());
		
		/**Detach entities*/
		entityManager.clear();
		 
		fastconnectMicropoleContract = contractRepo.getContractByName(Constants.CONTRACT5_NAME);
		
		
		/**Tests initial state of Contracts table (the parent)*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE));	// CONTRACT_ID='5'
		/**Tests the initial state of the children table(s) from the target Parent table*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));	
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/	
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE)); 
		/**Tests the initial state of the children table(s) from previous Parent table(s)*/
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));		
		/**Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.*/			
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));	
		/**Remove contract*/			
		entityManager.remove(fastconnectMicropoleContract);
		entityManager.flush();
		entityManager.clear();
		
		/**See SQL cascadings applied to one-to-many relations*/
		/**CONTRACT 	-> 		SUPPLY_CONTRACT					CascadeType.ALL*/
		/**CONTRACT 	-> 		CONTRACT_SERVICE_AGREEMENT		CascadeType.ALL*/
		
		/*** Cascadings in this sequence*/
		/**  CONTRACT (P) -> SUPPLY_CONTRACT (c) */	
		/**      |                               */
		/**      |                      		 */
		/**      v                               */
		/** CONTRACT_SERVICE_AGREEMENT (c)       */
		
		/**Tests the cascaded parent of the OneToMany association between Contract -> SupplyContract*/		
		assertEquals(12, countRowsInTable(jdbcTemplate, Constants.CONTRACT_TABLE)); //Parent is removed
		/**Tests the cascaded children of the OneToMany association between Supplier -> SupplyContract*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Constants.SUPPLY_CONTRACT_TABLE));	//1 child with CONTRACT_ID='5' removed from the SUPPLY_CONTRACT table.
		/**Tests the cascaded children of the OneToMany association between Contract -> ContractServiceAgreement */
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE)); //1 child with CONTRACT_ID='5' removed from the CONTRACT_SERVICE_AGREEMENT table. 		
		/**Tests post state of Suppliers table (the parent)*/
		assertEquals(5, countRowsInTable(jdbcTemplate, Constants.SUPPLIER_TABLE));  //1 child with CONTRACT_ID='5' previously removed from SUPPLY_CONTRACT table. That cascades to 0 parent being removed from the SUPPLIER table. 
		/**Tests the cascaded children of the OneToMany association between Supplier -> EmploymentContract*/
		assertEquals(6, countRowsInTable(jdbcTemplate, Constants.EMPLOYMENT_CONTRACT_TABLE));	//0 children previously removed from SUPPLIER table. That cascades to 0 children being removed from the EMPLOYMENT_CONTRACT table.		
		/**Tests the cascaded parent of the OneToMany association between  Staff -> EmploymentContract */
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE)); //0 children previously removed from EMPLOYMENT_CONTRACT table. That cascades to 0 parent being removed from the STAFF table.
		
	}
	
	@Test
	public void testNameIsNotNull() {
		Contract contract = new Contract();
		Set<ConstraintViolation<Contract>> violations = validator.validate(contract);
        assertFalse(violations.isEmpty());
		
	}
	
	@Test
	public void testToString() {
		Contract contract = new Contract();
		contract.toString();
	}

	public static Contract insertAContract(Client client, String name, EntityManager entityManager) {
		Contract contract  = new Contract();
		contract.setName(name);
		contract.setClient(client);
		entityManager.persist(contract);
		entityManager.flush();
		assertThat(contract.getId(), Matchers.greaterThan((long)0));
		return contract;
		
	}

}
