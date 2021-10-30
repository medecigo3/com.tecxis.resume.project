package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BELFIUS;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT4_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.Constants.J2EE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.LIFERAY_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.util.Utils.isAgreementValid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

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
import com.tecxis.resume.domain.SchemaUtils;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.id.AgreementId;
import com.tecxis.resume.domain.repository.AgreementRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaAgreementDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplateProxy;		
	@Autowired
	private AgreementRepository agreementRepo;	
	@Autowired
	private ServiceRepository serviceRepo;	
	@Autowired
	private  ContractRepository contractRepo;	
	@Autowired
	private SupplierRepository supplierRepo;	
	@Autowired 
	private ClientRepository clientRepo;
	@Autowired
	private AgreementDao agreementDao;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" }, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave_UpdateService() {
		/**Find Client*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());		
		
		/**Find supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);
		assertEquals(FASTCONNECT, fastconnect.getName());	
		
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		
		/**Find target Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
				
		/**Find new service to set in Agreement*/
		Service liferayDev = serviceRepo.getServiceByName(LIFERAY_DEVELOPPER);
		
		/***Create new Agreement*/
		AgreementId newAxeltisFastConnectAgreementId = new AgreementId();
		newAxeltisFastConnectAgreementId.setContractId(axeltisFastConnectcontract.getId());
		newAxeltisFastConnectAgreementId.setServiceId(liferayDev.getId());
		Agreement newAxeltisFastConnectAgreement = new Agreement();
		newAxeltisFastConnectAgreement.setId(newAxeltisFastConnectAgreementId);
		newAxeltisFastConnectAgreement.setContract(axeltisFastConnectcontract);
		newAxeltisFastConnectAgreement.setService(liferayDev);
		
		/**Verify initial state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		
		/**Remove old and create new Agreement*/
		agreementDao.delete(axeltisFastConnectAgreement);
		agreementDao.save(newAxeltisFastConnectAgreement);
		agreementRepo.flush();
		
		/**Verify post state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Test changes*/
		/**Find old Agreement*/
		assertNotNull(agreementRepo.findByContractAndService(axeltisFastConnectcontract, liferayDev));
		/**Find new Enrolment*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());	
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" }, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave_UpdateContract() {
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		/**Find target Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
		
		/**Validates Agreement to test*/		
		assertTrue(isAgreementValid(axeltisFastConnectAgreement, CONTRACT7_NAME, TIBCO_BW_CONSULTANT));
		
		/**Find new Contract to set in Agreement*/
		Contract accentureBarclaysContract = contractRepo.getContractByName(CONTRACT1_NAME);
		Utils.doInJpaRepository(setContractAgreementFunction-> {
			/***Create new Agreement*/
			AgreementId newAxeltisFastConnectAgreementId = new AgreementId();
			newAxeltisFastConnectAgreementId.setContractId(accentureBarclaysContract.getId()); //set new contract id
			newAxeltisFastConnectAgreementId.setServiceId(tibcoCons.getId());
			Agreement newAxeltisFastConnectAgreement = new Agreement();
			newAxeltisFastConnectAgreement.setId(newAxeltisFastConnectAgreementId);
			newAxeltisFastConnectAgreement.setContract(accentureBarclaysContract); // set new contract
			newAxeltisFastConnectAgreement.setService(tibcoCons);
						
			/**Remove old and create new Agreement*/
			agreementRepo.delete(axeltisFastConnectAgreement);
			agreementRepo.save(newAxeltisFastConnectAgreement);
			agreementRepo.flush();	
			
		}, agreementRepo, jdbcTemplateProxy);
		
		/**Test find old Enrolment*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
		/**Test new Agreement changes*/
		Agreement newAxeltisFastConnectAgreement = agreementRepo.findByContractAndService(accentureBarclaysContract, tibcoCons);				
		/**Validates new Agreement*/		
		assertTrue(isAgreementValid(newAxeltisFastConnectAgreement, CONTRACT1_NAME, TIBCO_BW_CONSULTANT));

	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		/**Insert service*/
		Service muleEsbCons = Utils.insertService(MULE_ESB_CONSULTANT, serviceRepo);
		
		
		/**Insert Contract*/
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);		
		Contract accentureBarclaysContract = Utils.insertContract(barclays, CONTRACT1_NAME, contractRepo);
		
		/**Insert Agreement */
		Agreement AgreementIn = Utils.insertAgreement(accentureBarclaysContract, muleEsbCons, agreementRepo);
		agreementDao.add(AgreementIn);
		agreementRepo.flush();
		assertNotNull(AgreementIn);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
		
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
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = Utils.insertService(SCM_ASSOCIATE_DEVELOPPER, serviceRepo);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId().longValue());
		/**Insert Contract*/
		Client belfius = Utils.insertClient(BELFIUS, clientRepo);			
		Contract alphatressBarclaysContract = Utils.insertContract(belfius, CONTRACT13_NAME, contractRepo);
		
		/**Insert Agreement */
		Agreement tempAgreement = Utils.insertAgreement(alphatressBarclaysContract, scmAssoc, agreementRepo);
		assertNotNull(tempAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
		
		/**Delete Agreement and test*/
		agreementDao.delete(tempAgreement);	
		agreementRepo.flush();
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.AGREEMENT_TABLE));
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
