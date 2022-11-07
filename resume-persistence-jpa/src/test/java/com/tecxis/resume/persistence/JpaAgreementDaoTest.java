package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.CONTRACT13_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT4_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.J2EE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.LIFERAY_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.util.Utils.deleteAgreementInJpa;
import static com.tecxis.resume.domain.util.Utils.insertAgreementInJpa;
import static com.tecxis.resume.domain.util.Utils.isAgreementValid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.id.AgreementId;
import com.tecxis.resume.domain.repository.AgreementRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.util.Utils;
import com.tecxis.resume.domain.util.function.ValidationResult;

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
	private ClientRepository clientRepo;
	@Autowired
	private AgreementDao agreementDao;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" }, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void test_ManyToOne_SaveService() {		
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		
		/**Find target Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
				
		/**Find new service to set in Agreement*/
		Service liferayDev = serviceRepo.getServiceByName(LIFERAY_DEVELOPPER);
		Utils.updateAgreementServiceInJpa(updateAgreementService-> {
			/***Create new Agreement*/
			AgreementId newAxeltisFastConnectAgreementId = new AgreementId();
			newAxeltisFastConnectAgreementId.setContractId(axeltisFastConnectcontract.getId()); // set new service id
			newAxeltisFastConnectAgreementId.setServiceId(liferayDev.getId());
			Agreement newAxeltisFastConnectAgreement = new Agreement();
			newAxeltisFastConnectAgreement.setId(newAxeltisFastConnectAgreementId);
			newAxeltisFastConnectAgreement.setContract(axeltisFastConnectcontract);
			newAxeltisFastConnectAgreement.setService(liferayDev);  // set new service
		
			/**Remove old and create new Agreement*/
			agreementDao.delete(axeltisFastConnectAgreement);
			agreementDao.save(newAxeltisFastConnectAgreement);
			agreementRepo.flush(); //Manually commit the transaction
		}, agreementRepo, jdbcTemplateProxy);
		
		/**Find old Agreement*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
		/**Find new Enrolment*/
		Agreement newLiferayAgreement = agreementRepo.findByContractAndService(axeltisFastConnectcontract, liferayDev);
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(newLiferayAgreement, CONTRACT7_NAME, LIFERAY_DEVELOPPER));	
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" }, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void test_ManyToOne_SaveContract() {
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		/**Find target Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
		
		/**Validates Agreement to test*/		
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(axeltisFastConnectAgreement, CONTRACT7_NAME, TIBCO_BW_CONSULTANT));
		
		/**Find new Contract to set in Agreement*/
		Contract accentureBarclaysContract = contractRepo.getContractByName(CONTRACT1_NAME);
		Utils.setAgreementContractInJpa(UpdateAgreementContractFunction-> {
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
			agreementRepo.flush();	//Manually commit the transaction
			
		}, agreementRepo, jdbcTemplateProxy);
		
		/**Test find old Enrolment*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
		/**Test new Agreement changes*/
		Agreement newAxeltisFastConnectAgreement = agreementRepo.findByContractAndService(accentureBarclaysContract, tibcoCons);				
		/**Validates new Agreement*/		
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(newAxeltisFastConnectAgreement, CONTRACT1_NAME, TIBCO_BW_CONSULTANT));

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
		
		/**Insert Agreement*/		
		insertAgreementInJpa(setContractAgreementFunction-> {			
			Agreement agreementIn = new Agreement(accentureBarclaysContract, muleEsbCons);
			agreementDao.add(agreementIn);
			agreementRepo.flush(); //Manually commit the transaction
			
		}, agreementRepo, jdbcTemplateProxy);
		
		/** Find new Agreement*/
		AgreementId AgreementId = new AgreementId();
		AgreementId.setContractId(accentureBarclaysContract.getId());
		AgreementId.setServiceId(muleEsbCons.getId());
		/**Validate new Agreement*/
		Agreement AgreementOut =agreementRepo.findById(AgreementId).get();		
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(AgreementOut, CONTRACT1_NAME, MULE_ESB_CONSULTANT));		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" }, 
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDelete() {
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		
		/**Find Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();			
		deleteAgreementInJpa(deleteAgreementFunction -> {
			
			/**Do not detach and remove entity directly*/
			/**Remove the Agreement from the Service */
			agreementRepo.delete(axeltisFastConnectAgreement);
			agreementRepo.flush(); //Manually commit the transaction
			
		}, agreementRepo, jdbcTemplateProxy);
		
		/**Test Agreement was removed */		
		axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		/**Find Agreement to remove*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(13, agreementRepo.count());
		List <Agreement> agreements = agreementRepo.findAll();
		assertEquals(13, agreements.size());
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
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
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
