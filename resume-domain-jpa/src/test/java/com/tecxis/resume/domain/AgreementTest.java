package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.Constants.LIFERAY_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.doInJpa;
import static com.tecxis.resume.domain.util.Utils.isAgreementValid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class AgreementTest {
	
	@PersistenceContext  //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired 
	private ClientRepository clientRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private AgreementRepository agreementRepo;
	
	@Test()
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetService() {
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
		newAxeltisFastConnectAgreementId.setServiceId(liferayDev.getId()); // set new service id
		Agreement newAxeltisFastConnectAgreement = new Agreement();
		newAxeltisFastConnectAgreement.setId(newAxeltisFastConnectAgreementId);
		newAxeltisFastConnectAgreement.setContract(axeltisFastConnectcontract);
		newAxeltisFastConnectAgreement.setService(liferayDev); // set new service
		
		/**Verify initial state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		
		/**Remove old and create new Agreement*/
		entityManager.remove(axeltisFastConnectAgreement);
		entityManager.persist(newAxeltisFastConnectAgreement);
		entityManager.flush();
		entityManager.clear();	
		
		/**Verify post state*/
		SchemaUtils.testInitialState(jdbcTemplateProxy);
		/**Test changes*/
		/**Find new Agreement*/
		assertNotNull(agreementRepo.findByContractAndService(axeltisFastConnectcontract, liferayDev));
		/**Find old Enrolment*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());	
	}
	
	@Test()
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testSetContract() {		
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
		doInJpa(setContractAgreementFunction-> {
			/***Create new Agreement*/
			AgreementId newAxeltisFastConnectAgreementId = new AgreementId();
			newAxeltisFastConnectAgreementId.setContractId(accentureBarclaysContract.getId()); //set new contract id
			newAxeltisFastConnectAgreementId.setServiceId(tibcoCons.getId());
			Agreement newAxeltisFastConnectAgreement = new Agreement();
			newAxeltisFastConnectAgreement.setId(newAxeltisFastConnectAgreementId);
			newAxeltisFastConnectAgreement.setContract(accentureBarclaysContract); // set new contract
			newAxeltisFastConnectAgreement.setService(tibcoCons);
						
			/**Remove old and create new Agreement*/
			entityManager.remove(axeltisFastConnectAgreement);
			entityManager.persist(newAxeltisFastConnectAgreement);
			entityManager.flush();
			entityManager.clear();	
			
		}, entityManager, jdbcTemplateProxy);
		
		/**Test find old Enrolment*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
		/**Test new Agreement changes*/
		Agreement newAxeltisFastConnectAgreement = agreementRepo.findByContractAndService(accentureBarclaysContract, tibcoCons);				
		/**Validates new Agreement*/		
		assertTrue(isAgreementValid(newAxeltisFastConnectAgreement, CONTRACT1_NAME, TIBCO_BW_CONSULTANT));
	}
	
	@Test()
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveAgreement() {
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

		/**Find Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
				
		/**Do not detach and remove entity directly*/		
				
		/**Remove Agreement*/
		SchemaUtils.testInitialState(jdbcTemplateProxy); 
		/**Remove the Agreement from the Service */
		entityManager.remove(axeltisFastConnectAgreement);
		entityManager.flush();
		entityManager.clear();
		SchemaUtils.testStateAfterAxeltisFastconnectAgreementDelete(jdbcTemplateProxy);
		
		/**Test Agreement was removed */
		/**Find Client*/
		axeltis = clientRepo.getClientByName(AXELTIS);
		fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);		
		axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);

		/**Find Agreement to remove*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
			
		
	}

	@Test
	public void testToString() {
		Agreement agreement = new Agreement();
		assertThat(agreement.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}

}
