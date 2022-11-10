package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ALTERNA;
import static com.tecxis.resume.domain.Constants.AMESYS;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.ARVAL;
import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.CONTRACT11_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT11_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT11_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT2_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT4_ENDDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT4_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT4_STARTDATE;
import static com.tecxis.resume.domain.Constants.CONTRACT5_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT9_NAME;
import static com.tecxis.resume.domain.Constants.EULER_HERMES;
import static com.tecxis.resume.domain.Constants.MICROPOLE;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.SAGEMCOM;
import static com.tecxis.resume.domain.Constants.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.util.Utils.setSagemContractWithMicropoleClientInJpa;
import static com.tecxis.resume.domain.util.Utils.setArvalContractAgreementsAndRemoveOphansInJpa;
import static com.tecxis.resume.domain.util.Utils.setArvalContractAgreementsInJpa;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
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
import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.id.ContractId;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.repository.SupplyContractRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaContractDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private  ContractRepository contractRepo;

	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private SupplyContractRepository supplyContractRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		Client axeltis = Utils.insertClient(AXELTIS, entityManager);		
		Contract accentureContract = Utils.insertContract(axeltis, CONTRACT1_NAME, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		assertEquals(1, accentureContract.getId().getContractId());
	}
	
	@Sql(
		    scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	public void testAdd() {
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);			
		Contract contractIn = Utils.insertContract(barclays, CONTRACT1_NAME, entityManager);
		Contract contractOut = contractRepo.getContractByName(CONTRACT1_NAME);
		assertNotNull(contractOut);
		assertEquals(contractIn, contractOut);		
	
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		Client eh = Utils.insertClient(EULER_HERMES, entityManager);	
		Contract tempContract = Utils.insertContract(eh, CONTRACT1_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		contractRepo.delete(tempContract);
		assertNull(contractRepo.getContractByName(CONTRACT1_NAME));
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.CONTRACT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Contract> contracts = contractRepo.findAll();
		assertEquals(13, contracts.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Contract> pageableContract = contractRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableContract.getSize());
	}	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void findByClientOrderByIdAsc() {						
		/**Find contracts*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);	
		List <Contract> axeltisContracts = contractRepo.findByClientOrderByIdAsc(axeltis);
		
		/**Validate contracts*/
		assertEquals(2, axeltisContracts.size());
		
		/**Find target Contract(s) to validate*/
		Contract fastconnectAxeltisContract1 = contractRepo.getContractByName(CONTRACT7_NAME);
		Contract fastconnectAxeltisContract2 = contractRepo.getContractByName(CONTRACT9_NAME);
		
		/**Test found Contract(s)*/
		assertThat(axeltisContracts, Matchers.containsInRelativeOrder(fastconnectAxeltisContract1, fastconnectAxeltisContract2));
			
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetContractByName() {
		Contract contract2 = contractRepo.getContractByName(CONTRACT2_NAME);
		assertEquals(CONTRACT2_NAME, contract2.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindById() {		
		Client micropole = clientRepo.getClientByName(MICROPOLE);
		assertEquals(MICROPOLE, micropole.getName());					
		Contract fastconnectMicropoleContract = contractRepo.findById(new ContractId(5L, micropole.getId())).get();
		assertNotNull(fastconnectMicropoleContract);
		assertEquals(micropole, fastconnectMicropoleContract.getClient());		
		assertEquals(5L, fastconnectMicropoleContract.getId().getContractId());
		
	}
	
	@Test
	public void test_ManyToOne_SaveClient() {
		/**Find target Contract*/			
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
		
						
		/**Create new Contract with new Client*/		
		setSagemContractWithMicropoleClientInJpa(
			DeleteContractFunction -> {	
				/**These steps will update the Parent (non-owner of this relation)*/		
				entityManager.remove(currentSagemContract);//Firstly remove the Child (Owner)
				entityManager.flush();
				
			},
			SetContractClientFunction -> {
				/**Find new Client to set*/
				Client micropole = clientRepo.getClientByName(MICROPOLE);
				assertEquals(1, micropole.getContracts().size());				
				Contract newMicropoleContract = new Contract();
				ContractId newMicropoleContractId = newMicropoleContract.getId();
				newMicropoleContractId.setContractId(sagemContractId);
				newMicropoleContract.setClient(micropole);
				newMicropoleContract.setName(CONTRACT4_NAME);
				/**Set the new Contract with the SupplyContract (with new Client)*/	
				SupplyContract amesysMicropoleSupplyContract = new SupplyContract(amesys, newMicropoleContract, amt);
				amesysMicropoleSupplyContract.setStartDate(new Date());
				List <SupplyContract> amesysMicropoleSupplyContracts = new ArrayList<>();//TODO refactor use declarative approach
				amesysMicropoleSupplyContracts.add(amesysMicropoleSupplyContract);
				newMicropoleContract.setSupplyContracts(amesysMicropoleSupplyContracts);
				
				entityManager.persist(newMicropoleContract); //Finally insert Child with new Parent (non-owner)
				entityManager.flush();
				entityManager.clear();				
			
			}, contractRepo, jdbcTemplateProxy);
		
		//TODO continue refactoring Validator code here	
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	public void test_ManyToOne_SaveAgreement() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	public void test_ManyToOne_SupplyContract() {
		org.junit.Assert.fail("TODO");
	}
	
	@Test
	public void test_OneToMany_Update_Agreements_And_RemoveOrphansWithOrm() {
		/**Find a Contract*/		
		final Contract arvalContract = contractRepo.getContractByName(CONTRACT11_NAME);
		
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
		
		/***Validate the state of the current Agreements*/		
		assertEquals(1, arvalContract.getAgreements().size());
		Agreement  alternaArvalAgreement = arvalContract.getAgreements().get(0);
		assertEquals(arvalContract, alternaArvalAgreement.getContract());
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		/**Validate opposite associations - Arval Contract has BW Service*/
		assertEquals(bwService, alternaArvalAgreement.getService());
				
		/**Build new Agreements*/		
		Agreement alternaMuleAgreement = new Agreement(arvalContract, muleService);
		Agreement alternaScmAgreement = new Agreement(arvalContract, scmService);
		List <Agreement> newAgreements = List.of(alternaMuleAgreement, alternaScmAgreement);
			
		/**Set Agreements*/	
		/**This sets new Arval's Agreements and leaves orphans */ 
		setArvalContractAgreementsInJpa( SetContractAgreementsFunction -> {
			arvalContract.setAgreements(newAgreements);			
			assertEquals(2, arvalContract.getAgreements().size());
			contractRepo.save(arvalContract);
			contractRepo.flush();			
		},
		contractRepo, jdbcTemplateProxy);		
		
		entityManager.clear();
		
		/**Validate test*/			
		Contract newArvalContract = contractRepo.getContractByName(CONTRACT11_NAME);
		assertEquals(2, newArvalContract.getAgreements().size());
		
		Agreement alternaAgreement1 = newArvalContract.getAgreements().get(0);
		Agreement alternaAgreement2 = newArvalContract.getAgreements().get(1);
		
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
		assertThat(muleService.getAgreements().get(0).getContract(), Matchers.oneOf(newArvalContract,  micropoleContract));
		assertThat(muleService.getAgreements().get(1).getContract(), Matchers.oneOf(newArvalContract,  micropoleContract));		
		/**Now test scm Service */
		assertEquals(2, scmService.getAgreements().size());
		Contract barclaysContract =  contractRepo.getContractByName(CONTRACT1_NAME);	
		assertNotNull(barclaysContract);
		/**Retreive 2nd scm Contract & test*/		
		assertThat(scmService.getAgreements().get(0).getContract(), Matchers.oneOf(newArvalContract, barclaysContract));
		assertThat(scmService.getAgreements().get(1).getContract(), Matchers.oneOf(newArvalContract,  barclaysContract));
	}
	
	@Test
	public void test_OneToMany_Update_Agreements_And_RemoveOrphansWithOrm_NullSet() {
		/**Find a Contract*/		
		final Contract arvalContract = contractRepo.getContractByName(CONTRACT11_NAME);
		
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
		
		/***Validate the state of the current Agreements*/		
		assertEquals(1, arvalContract.getAgreements().size());
		Agreement  alternaArvalAgreement = arvalContract.getAgreements().get(0);
		assertEquals(arvalContract, alternaArvalAgreement.getContract());
		Service bwService = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		/**Validate opposite associations - Arval Contract has BW Service*/
		assertEquals(bwService, alternaArvalAgreement.getService());
				
		/**Set Agreements*/	
		/**This sets new Arval's Agreements and leaves orphans */ 
		setArvalContractAgreementsAndRemoveOphansInJpa( SetContractAgreementsWithNullFunction -> {
			arvalContract.setAgreements(null);					
			entityManager.merge(arvalContract);
			entityManager.flush();
			entityManager.clear();
		},
		contractRepo, jdbcTemplateProxy);	
	}
		
}
