package com.tecxis.resume.domain;
import static com.tecxis.resume.domain.Constants.ALPHATRESS;
import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMESYS;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.ARVAL;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.CONTRACT11_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT11_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT11_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT4_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT4_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT4_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT5_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT9_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.Constants.MICROPOLE;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Constants.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.Constants.sdf;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static com.tecxis.resume.domain.Service.SERVICE_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.ContractId;
import com.tecxis.resume.domain.id.AgreementId;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.AgreementRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.util.Utils;
import com.tecxis.resume.domain.util.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml"})
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
public class ContractTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired
	private AgreementRepository agreementRepo;
	
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);	
		Contract contract = Utils.insertContract(axeltis, CONTRACT9_NAME, entityManager);
		org.assertj.core.api.Assertions.assertThat(contract.getId().getContractId()).isGreaterThan((long)0);
	}
	
	@Test
	public void testSetId() {
		Contract contract = new Contract();
		assertEquals(0L, contract.getId().getContractId());
		contract.getId().setContractId(1L);
		assertEquals(1L, contract.getId().getContractId());
	}


	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetClient() {
		/**Find a contract*/		
		Contract alphatressBelfiusContract = contractRepo.getContractByName(CONTRACT13_NAME);
		
		/**Validate Contract-> Client */
		assertEquals(BELFIUS, alphatressBelfiusContract.getClient().getName());
		
		/**Validate Contract-> SupplyContract */
		List <SupplyContract> alphatressBelfiusContracts = alphatressBelfiusContract.getSupplyContracts();
		assertEquals(2, alphatressBelfiusContracts.size());
		
		/**Validate Contract-> agreement */
		assertEquals(1, alphatressBelfiusContract.getAgreements().size());
		
	}
	
	/**See equivalent unit test in CityTest.testSetCountryWithOrmOrhpanRemoval*/
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetClientWithOrmOrhpanRemoval() {
		/**Find a Contract*/			
		Contract currentSagemContract = contractRepo.getContractByName(CONTRACT4_NAME);
		final long sagemContractId = currentSagemContract.getId().getContractId();
				
		/**Validate Contract-> Client */
		Client sagem = currentSagemContract.getClient();
		assertEquals(SAGEMCOM, sagem.getName());				
		
		/**Validate Contract -> SupplyContract*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertNotNull(amt);		
		Supplier amesys = supplierRepo.getSupplierByName(AMESYS);
		assertEquals(1, currentSagemContract.getSupplyContracts().size());
		List <SupplyContract> existingAmesysSagemSupplyContracts = currentSagemContract.getSupplyContracts();
		SupplyContract amesysSagemSupplyContract =  existingAmesysSagemSupplyContracts.get(0);
		assertEquals(amesysSagemSupplyContract, supplyContractRepo.findByContractAndSupplierAndStaff(currentSagemContract, amesys, amt));
		assertEquals(CONTRACT4_ENDDATE, amesysSagemSupplyContract.getEndDate());
		assertEquals(CONTRACT4_STARTDATE, amesysSagemSupplyContract.getStartDate());
		
		/**Validate Supplier -> SupplyContract -> Contract*/		
		assertEquals(amesys, amesysSagemSupplyContract.getSupplier());
		
		/**Find new Client to set*/
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(1, micropole.getContracts().size());
				
		/**Create new Contract with new Client*/
		Contract newMicropoleContract = new Contract();
		ContractId newMicropoleContractId = newMicropoleContract.getId();
		newMicropoleContractId.setContractId(sagemContractId);
		newMicropoleContract.setClient(micropole);
		newMicropoleContract.setName(CONTRACT4_NAME);
		/**Set the new Contract with the SupplyContract (with new Client)*/	
		SupplyContract amesysMicropoleSupplyContract = new SupplyContract(amesys, newMicropoleContract, amt);
		amesysMicropoleSupplyContract.setStartDate(new Date());
		List <SupplyContract> amesysMicropoleSupplyContracts = new ArrayList<>();
		amesysMicropoleSupplyContracts.add(amesysMicropoleSupplyContract);
		newMicropoleContract.setSupplyContracts(amesysMicropoleSupplyContracts);

		/**These steps will update the Parent (non-owner of this relation)*/ 
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		/**Firstly remove the Child (Owner)*/
		entityManager.remove(currentSagemContract);
		entityManager.flush();
		assertEquals(12, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(12, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));//1 orphan removed
		assertEquals(13, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE)); //1 orphan removed
		/**Finally insert Child with new Parent (non-owner)*/
		entityManager.persist(newMicropoleContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));		
		assertEquals(12, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		
		
		/**Validate new Contract*/
		newMicropoleContract = contractRepo.getContractByName(CONTRACT4_NAME);					
		assertEquals(sagemContractId, newMicropoleContract.getId().getContractId());
		
		/**Validate new Contract ->  Client*/
		micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(micropole, newMicropoleContract.getClient());	
		
		/**Validate old Contract -> Client*/
		Contract fcMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(micropole, fcMicropoleContract.getClient());
		
		/**Now Client -> Contracts has 2 */
		assertEquals(2, micropole.getContracts().size());
		org.assertj.core.api.Assertions.assertThat(micropole.getContracts()).contains(newMicropoleContract, fcMicropoleContract);
		
		/**New Contract ->  agreements has 0 */
		assertEquals(0, newMicropoleContract.getAgreements().size());
		
		/**New Contract ->  SupplyContract has 1 */
		assertEquals(1, newMicropoleContract.getSupplyContracts().size());
		
		/**New SupplyContract -> Contract*/
		assertNotNull(supplyContractRepo.findByContractAndSupplierAndStaff(newMicropoleContract, amesys, amt));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplier() {
		/**Find a contract*/	
		Supplier amesys = supplierRepo.getSupplierByName(AMESYS);		
		Contract amesysSagemContract = contractRepo.getContractByName(CONTRACT4_NAME);
		
		/**Validate Contract-> Client */				
		assertEquals(SAGEMCOM, amesysSagemContract.getClient().getName());
		
		/**Validate Contract -> SupplyContract*/
		assertEquals(1, amesysSagemContract.getSupplyContracts().size());
		SupplyContract amesysSagemSupplyContract = amesysSagemContract.getSupplyContracts().get(0);
		assertEquals(AMESYS,amesysSagemSupplyContract.getSupplier().getName());	
		assertEquals(CONTRACT4_ENDDATE, amesysSagemSupplyContract.getEndDate());
		assertEquals(CONTRACT4_STARTDATE, amesysSagemSupplyContract.getStartDate());
		assertEquals(amesys, amesysSagemSupplyContract.getSupplier());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetSuppyContractWithNewStaffWithOrmOrphanRemoval() throws ParseException {
		final Date startDate = sdf.parse("12/30/2019");
		final Date endDate = sdf.parse("13/30/2019");
		
		/**Find a contract*/			
		Contract currentAmesysSagemContract = contractRepo.getContractByName(CONTRACT4_NAME);
		
		/**Validate Contract-> Client */
		assertEquals(SAGEMCOM, currentAmesysSagemContract.getClient().getName());		
		Client sagemcom = currentAmesysSagemContract.getClient();
		final long amesysContractId = currentAmesysSagemContract.getId().getContractId();
		
		/**Validate Contract -> SupplyContract*/
		assertEquals(1, currentAmesysSagemContract.getSupplyContracts().size());
		SupplyContract amesysSagemSupplyContract =  currentAmesysSagemContract.getSupplyContracts().get(0);
		assertEquals(CONTRACT4_ENDDATE, amesysSagemSupplyContract.getEndDate());
		assertEquals(CONTRACT4_STARTDATE, amesysSagemSupplyContract.getStartDate());
		Supplier amesys = supplierRepo.getSupplierByName(AMESYS);
		assertEquals(amesys, amesysSagemSupplyContract.getSupplier());
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(amt, amesysSagemSupplyContract.getStaff());
		
		/**Create the new SupplyContract for the current Client -> Contract different Staff*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		SupplyContract newAmesysSagemSupplyContract = new SupplyContract(amesys, currentAmesysSagemContract, john);
		/**Set the new dates of the SuppyContract*/
		newAmesysSagemSupplyContract.setStartDate(startDate);
		newAmesysSagemSupplyContract.setEndDate(endDate);
		
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Set Contract -> new SupplyContract*/
		List <SupplyContract> newAmesysSagemSupplyContracts = new ArrayList <>();
		newAmesysSagemSupplyContracts.add(newAmesysSagemSupplyContract);
		currentAmesysSagemContract.setSupplyContracts(newAmesysSagemSupplyContracts);
		entityManager.merge(currentAmesysSagemContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));				//1 orphan removed and 1 new child created in SUPPLY_CONTRACT table. Other tables remain unchanged.
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 						
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Validate the new Contract*/		
		currentAmesysSagemContract = contractRepo.getContractByName(CONTRACT4_NAME);
		assertNotNull(currentAmesysSagemContract);
		assertEquals(amesysContractId, currentAmesysSagemContract.getId().getContractId());
		
		/**Validate the Contract -> Client*/
		sagemcom = clientRepo.getClientByName(SAGEMCOM);
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetSupplyContracts() {		
		/**Find target contract*/			
		Contract belfiusAlphatressContract = contractRepo.getContractByName(CONTRACT13_NAME);
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
		org.assertj.core.api.Assertions.assertThat(belfiusSupplyContracts).contains(belfiusAlphatressSupplyContract1, belfiusAlphatressSupplyContract2);		
	}
		
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAddAgreement() throws EntityExistsException {
		/**Find a contract*/				
		Contract micropoleFastconnectContract = contractRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(1, micropoleFastconnectContract.getAgreements().size());
			
		/**Validate contract to test*/		
		Client micropole = clientRepo.getClientByName(MICROPOLE);		
		assertEquals(micropole, micropoleFastconnectContract.getClient());		
		
		/**Get Service to insert*/
		Service scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		
		assertNotNull(scmDevService);
		
		/**Validate service to insert**/
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
		assertEquals(1, scmDevService.getAgreements().size());
		
		/**Validate agreement table pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		AgreementId agreementId = new AgreementId();
		agreementId.setContractId(micropoleFastconnectContract.getId());
		agreementId.setServiceId(scmDevService.getId());
		assertFalse(agreementRepo.findById(agreementId).isPresent());
		
		/**Validate state of current Contract -> agreements*/
		List <Agreement> fastconnectAgreements = micropoleFastconnectContract.getAgreements();
		assertEquals(1, fastconnectAgreements.size());
		Service muleEsbService = fastconnectAgreements.get(0).getService();
		assertEquals(MULE_ESB_CONSULTANT, muleEsbService.getName());
		
		/**Create new agreement*/
		Agreement newAgreement = new Agreement(micropoleFastconnectContract, scmDevService);	
		
		/**Test tables pre test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 	
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, SERVICE_TABLE));				

	
		/**Add new agreement -> Contract*/	
		micropoleFastconnectContract.addAgreement(newAgreement);
		/**Add new ContractServiceAgrement to the inverse association*/	
		scmDevService.addAgreement(newAgreement);
		entityManager.persist(newAgreement);
		entityManager.merge(micropoleFastconnectContract);	
		entityManager.merge(scmDevService);
		entityManager.flush();
				
		/**Test tables post test state*/
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 	
		assertEquals(14, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, SERVICE_TABLE));				
		
		/**Validate the Contract -> agreements*/
		micropoleFastconnectContract = contractRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(2, micropoleFastconnectContract.getAgreements().size());
		
		/**Validate the Service -> agreements */
		scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);	
		assertEquals(2, scmDevService.getAgreements().size());
		assertTrue(agreementRepo.findById(agreementId).isPresent());
		
	}
	
	
	@Test(expected=EntityExistsException.class)
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testAddExistingAgreementToContract() throws EntityExistsException {		
			
		/**Find contracts*/
		Contract micropoleFastconnectContract = contractRepo.getContractByName(CONTRACT5_NAME);
		
		/**Validate contract to test*/		
		Client micropole = clientRepo.getClientByName(MICROPOLE);		
		assertEquals(micropole, micropoleFastconnectContract.getClient());		
		
		
		
		/**Validate Services -> contract to test*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		assertEquals(1, micropoleFastconnectContract.getAgreements().size());
		Service currentService = micropoleFastconnectContract.getAgreements().get(0).getService();
		assertNotNull(currentService);
		assertEquals(MULE_ESB_CONSULTANT, currentService.getName());		
		
		/**Find duplicate Service to insert*/
		Service duplicateMuleEsbService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		assertNotNull(duplicateMuleEsbService);
		assertEquals(MULE_ESB_CONSULTANT, duplicateMuleEsbService.getName());	
		assertEquals(1, duplicateMuleEsbService.getAgreements().size());
	
		
		/**Test that duplicateMuleEsbService exists in the list of Fastconnect-Micropole agreements*/
		List <Agreement> micropoleFastconnectAgreements = micropoleFastconnectContract.getAgreements();
		Agreement duplicateMuleEsbAgreement = duplicateMuleEsbService.getAgreements().get(0);
		org.junit.Assert.assertTrue(micropoleFastconnectAgreements.contains(duplicateMuleEsbAgreement));
		
		/**Create duplicate agreement*/
		Agreement duplicateAgreement = new Agreement(micropoleFastconnectContract, duplicateMuleEsbService);
		
		/**Add Service duplicate to the contract: expect error*/
		assertEquals(duplicateMuleEsbService,  currentService);
		micropoleFastconnectContract.addAgreement(duplicateAgreement);
				
	}
	

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetAgreements() {
		/**Find a contract*/				
		Contract micropoleFastconnectContract = contractRepo.getContractByName(CONTRACT5_NAME);
			
		/**Validate Contract to test*/		
		Client micropole = clientRepo.getClientByName(MICROPOLE);		
		assertEquals(micropole, micropoleFastconnectContract.getClient());		
		
		/**Validate Contract -> agreements*/
		List <Agreement> micropoleFastconnectAgreements = micropoleFastconnectContract.getAgreements();
		assertEquals(1, micropoleFastconnectAgreements.size());
		Agreement micropolefastconnectAgreement = micropoleFastconnectAgreements.get(0);
		
		/*** Validate Contract -> SupplyContract */		
		List <SupplyContract> micropoleFastconnectContracts = micropolefastconnectAgreement.getContract().getSupplyContracts();
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
		assertEquals(MICROPOLE, micropoleFastconnectContract.getClient().getName());
		/**Validate SupplyContract-> Supplier*/
		assertEquals(FASTCONNECT, micropoleFastconnectSupplyContract.getSupplier().getName());
		/**Validate SupplyContract-> Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(amt, micropoleFastconnectSupplyContract.getStaff());		
		/**Validate Agreement -> Service*/
		assertEquals(MULE_ESB_CONSULTANT, micropolefastconnectAgreement.getService().getName());
			
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetAgreementsWithOrmOrphanRemove() {
		/**Find a Contract*/		
		Contract arvalContract = contractRepo.getContractByName(CONTRACT11_NAME);
		
		/**Validate contract to test*/
		Client arval= clientRepo.getClientByName(ARVAL);
		assertEquals(arval, arvalContract.getClient());		
		
		
		/*** Validate Contract -> SupplyContract */
		List <SupplyContract> alternaArvalContracts = arvalContract.getSupplyContracts();
		assertEquals(1, alternaArvalContracts.size());			
		SupplyContract alternaArvalSupplyContract =  alternaArvalContracts.get(0);
		assertEquals(CONTRACT11_STARTDATE, alternaArvalSupplyContract.getStartDate());
		assertEquals(CONTRACT11_ENDDATE, alternaArvalSupplyContract.getEndDate());
		Supplier alterna = supplierRepo.getSupplierByName(ALTERNA);
		assertEquals(alterna, alternaArvalSupplyContract.getSupplier());
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(amt, alternaArvalSupplyContract.getStaff());
		
		
           
		/**Find & validate Services to test */		
		Service muleService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		Service scmService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER); 
		assertEquals(MULE_ESB_CONSULTANT, muleService.getName());
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmService.getName());
		
		/***Validate the state of the current ContractServiceAgreeements*/		
		assertEquals(1, arvalContract.getAgreements().size());
		Agreement  alternaArvalAgreement = arvalContract.getAgreements().get(0);
		assertEquals(arvalContract, alternaArvalAgreement.getContract());
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		/**Validate opposite associations - Arval Contract has BW Service*/
		assertEquals(bwService, alternaArvalAgreement.getService());
				
		/**Build new Agreements*/		
		Agreement alternaMuleAgreement = new Agreement(arvalContract, muleService);
		Agreement alternaScmAgreement = new Agreement(arvalContract, scmService);
		List <Agreement> newAgreements = new ArrayList <> ();
		newAgreements.add(alternaMuleAgreement);
		newAgreements.add(alternaScmAgreement);
			
		/**Set Agreements*/	
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));		
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		/**This sets new Arval's Agreements and leaves orphans */
		arvalContract.setAgreements(newAgreements);			
		assertEquals(2, arvalContract.getAgreements().size());
		entityManager.merge(arvalContract);
		entityManager.flush();
		entityManager.clear();
		assertEquals(14, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE)); //1 orphan removed and 1 new child created in EMPLOYMENT_CONTRACT table. Other tables remain unchanged. 	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Validate test*/			
		arvalContract = contractRepo.getContractByName(CONTRACT11_NAME);
		assertEquals(2, arvalContract.getAgreements().size());
		
		Agreement alternaAgreement1 = arvalContract.getAgreements().get(0);
		Agreement alternaAgreement2 = arvalContract.getAgreements().get(1);
		
		/**Prepare & test that SupplyContracts have same dates*/
		List <SupplyContract> alternaSupplyContracts1 = alternaAgreement1.getContract().getSupplyContracts();
		List <SupplyContract> alternaSupplyContracts2 = alternaAgreement2.getContract().getSupplyContracts();
		assertEquals(1, alternaSupplyContracts1.size());
		assertEquals(1, alternaSupplyContracts2.size());
				
		 
		Date contract1StartDate 	= alternaSupplyContracts1.get(0).getStartDate();
        Date contract1EndDate 		= alternaSupplyContracts1.get(0).getEndDate();   
        Date contract2StartDate 	= alternaSupplyContracts2.get(0).getStartDate();
        Date contract2EndDate 		= alternaSupplyContracts2.get(0).getEndDate();  
        
        assertEquals(CONTRACT11_STARTDATE, contract1StartDate);
        assertEquals(CONTRACT11_ENDDATE, contract1EndDate);
        assertEquals(CONTRACT11_STARTDATE, contract2StartDate);
        assertEquals(CONTRACT11_ENDDATE, contract2EndDate);
        
        /**Test Services*/
        assertThat(alternaAgreement1.getService(), Matchers.oneOf(muleService, scmService));
        assertThat(alternaAgreement2.getService(), Matchers.oneOf(muleService, scmService));
        
        /**Test the opposite association*/
        muleService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);
		scmService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);
		/**Test mule Service has all contracts*/
		assertEquals(2, muleService.getAgreements().size());				
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);		
		assertThat(muleService.getAgreements().get(0).getContract(), Matchers.oneOf(arvalContract,  micropoleContract));
		assertThat(muleService.getAgreements().get(1).getContract(), Matchers.oneOf(arvalContract,  micropoleContract));		
		/**Now test scm Service */
		assertEquals(2, scmService.getAgreements().size());
		Contract barclaysContract =  contractRepo.getContractByName(CONTRACT1_NAME);	
		assertNotNull(barclaysContract);
		/**Retreive 2nd scm Contract & test*/		
		assertThat(scmService.getAgreements().get(0).getContract(), Matchers.oneOf(arvalContract, barclaysContract));
		assertThat(scmService.getAgreements().get(1).getContract(), Matchers.oneOf(arvalContract,  barclaysContract));
				
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveAgreementByService() {
		/**Find  Contract*/		
		Contract barclaysAccentureContract = contractRepo.getContractByName(CONTRACT1_NAME);		
		
		/**Get Service & validate */
		Service scmDevService = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);		
		assertNotNull(scmDevService);
		assertEquals(SCM_ASSOCIATE_DEVELOPPER, scmDevService.getName());
		
				
		/**Find Agreement */		
		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		/**Remove Agreement*/
		assertTrue(barclaysAccentureContract.removeAgreement(scmDevService));
		assertTrue(scmDevService.removeAgreement(barclaysAccentureContract));
		entityManager.merge(barclaysAccentureContract);
		entityManager.merge(scmDevService);
		entityManager.flush();	
		assertEquals(12, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		/**Validate the Agreement was removed*/
		AgreementId agreementId = new AgreementId();
		agreementId.setContractId(barclaysAccentureContract.getId());
		agreementId.setServiceId(scmDevService.getId());
		assertFalse(agreementRepo.findById(agreementId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveAgreement() {			
		/**Get Service & validate */
		Service muleService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		assertNotNull(muleService);
		assertEquals(MULE_ESB_CONSULTANT, muleService.getName());
		assertEquals(1, muleService.getAgreements().size());
		assertEquals(1, muleService.getAgreements().get(0).getContract().getSupplyContracts().size());		
		assertEquals(FASTCONNECT, muleService.getAgreements().get(0).getContract().getSupplyContracts().get(0).getSupplier().getName());
		assertEquals(MICROPOLE, muleService.getAgreements().get(0).getContract().getClient().getName());
		
		/**Find target Contract & validate*/			
		Contract micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(1, micropoleContract.getAgreements().size());				
	
		
		/**Find the Agreement to remove*/		
		Agreement staleAgreement = agreementRepo.findById(new AgreementId(micropoleContract.getId(), muleService.getId())).get();
		assertNotNull(staleAgreement);

		/**Detach entities*/
		entityManager.clear();	
		micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
			

		assertEquals(13, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		/**Remove the Agreement*/		
		assertTrue(micropoleContract.removeAgreement(staleAgreement));		
		entityManager.merge(micropoleContract);
		entityManager.flush();	
		assertEquals(12, countRowsInTable(jdbcTemplate, Agreement.AGREEMENT_TABLE));	
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));	 
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 			
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));				
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));  
		
		/**Test that  Agreement was removed */
		micropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		assertEquals(0, micropoleContract.getAgreements().size());
		muleService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);
		assertEquals(0, muleService.getAgreements().size());
		AgreementId agreementId = new AgreementId();
		agreementId.setContractId(micropoleContract.getId());
		agreementId.setServiceId(muleService.getId());
		assertFalse(agreementRepo.findById(agreementId).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testDbRemoveContractwithCascadings() {
		/**Find and validate Contract to test*/
		Contract fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		assertNotNull(fastconnectMicropoleContract);
				
		/**Verify Contract -> Client*/
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(micropole, fastconnectMicropoleContract.getClient());
		
		/**Verify Contract -> SupplySontract(s)*/
		assertEquals(1, fastconnectMicropoleContract.getSupplyContracts().size());
		
		/**Detach entities*/
		entityManager.clear();
		 
		fastconnectMicropoleContract = contractRepo.getContractByName(CONTRACT5_NAME);
		
		
		/**Tests initial state of the parent table. Target is: CONTRACT_ID='5'
		*  CONTRACT_TABLE
		*  Tests the initial state of the children tables from the target Parent table
		*  CONTRACT_SERVICE_AGREEMENT_TABLE
		*  SUPPLY_CONTRACT_TABLE
		*  Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.
		*  SUPPLIER_TABLE
		*  Tests the initial state of the children table(s) from previous Parent table(s)
		*  EMPLOYMENT_CONTRACT_TABLE
		*  Test the initial state of remaining Parent table(s) with cascading.REMOVE strategy belonging to the previous children.
		*  STAFF_TABLE*/
		UtilsTest.testStateBeforeDelete(jdbcTemplate);
		/**Remove contract*/			
		entityManager.remove(fastconnectMicropoleContract);
		entityManager.flush();
		entityManager.clear();
		
		UtilsTest.testStateAfterContract5Delete(jdbcTemplate);
		
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
		assertThat(contract.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}

}
