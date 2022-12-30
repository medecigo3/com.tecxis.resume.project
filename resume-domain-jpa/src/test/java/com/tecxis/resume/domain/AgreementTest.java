package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.CONTRACT1_NAME;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.LIFERAY_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.deleteAgreementInJpa;
import static com.tecxis.resume.domain.util.Utils.insertAgreementInJpa;
import static com.tecxis.resume.domain.util.Utils.isAgreementValid;
import static com.tecxis.resume.domain.util.Utils.setAgreementServiceInJpa;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.util.Utils;
import com.tecxis.resume.domain.util.function.ValidationResult;


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
	private ContractRepository contractRepo;
	
	@Autowired
	private AgreementRepository agreementRepo;
	
	@Test()
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_ManyToOne_SetService() {
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		/**Find target Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
		
		/**Validates Agreement to test*/		
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(axeltisFastConnectAgreement, CONTRACT7_NAME, TIBCO_BW_CONSULTANT));
								
		/**Find new service to set in Agreement*/
		Service liferayDev = serviceRepo.getServiceByName(LIFERAY_DEVELOPPER);
		setAgreementServiceInJpa(em ->{
			/***Create new Agreement*/
			AgreementId newAxeltisFastConnectAgreementId = new AgreementId();
			newAxeltisFastConnectAgreementId.setContractId(axeltisFastConnectcontract.getId());
			newAxeltisFastConnectAgreementId.setServiceId(liferayDev.getId()); // set new service id
			Agreement newAxeltisFastConnectAgreement = new Agreement();
			newAxeltisFastConnectAgreement.setId(newAxeltisFastConnectAgreementId);
			newAxeltisFastConnectAgreement.setContract(axeltisFastConnectcontract);
			newAxeltisFastConnectAgreement.setService(liferayDev); // set new service
			
			/**Remove old and create new Agreement*/
			em.remove(axeltisFastConnectAgreement);
			em.persist(newAxeltisFastConnectAgreement);
			em.flush();  //Manually commit the transaction
			em.clear();	//Detach managed entities from persistence context to reload new changes
		}, entityManager, jdbcTemplateProxy);
		
		/**Find old Enrolment*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());		
		/**Find new Agreement*/
		Agreement newLiferayAgreement = agreementRepo.findByContractAndService(axeltisFastConnectcontract, liferayDev);
		/**Validates new Agreement*/		
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(newLiferayAgreement, CONTRACT7_NAME, LIFERAY_DEVELOPPER));
			
	}
	
	@Test()
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void test_ManyToOne_SetContract() {		
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
		Utils.setAgreementContractInJpa(em-> {
			/***Create new Agreement*/
			AgreementId newAxeltisFastConnectAgreementId = new AgreementId();
			newAxeltisFastConnectAgreementId.setContractId(accentureBarclaysContract.getId()); //set new contract id
			newAxeltisFastConnectAgreementId.setServiceId(tibcoCons.getId());
			Agreement newAxeltisFastConnectAgreement = new Agreement();
			newAxeltisFastConnectAgreement.setId(newAxeltisFastConnectAgreementId);
			newAxeltisFastConnectAgreement.setContract(accentureBarclaysContract); // set new contract
			newAxeltisFastConnectAgreement.setService(tibcoCons);
						
			/**Remove old and create new Agreement*/
			em.remove(axeltisFastConnectAgreement);
			em.persist(newAxeltisFastConnectAgreement);
			em.flush(); //Manually commit the transaction			
			em.clear(); //Detach managed entities from persistence context to reload new changes
			
		}, entityManager, jdbcTemplateProxy);
		
		/**Test find old Enrolment*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
		/**Test new Agreement changes*/
		Agreement newAxeltisFastConnectAgreement = agreementRepo.findByContractAndService(accentureBarclaysContract, tibcoCons);				
		/**Validates new Agreement*/		
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(newAxeltisFastConnectAgreement, CONTRACT1_NAME, TIBCO_BW_CONSULTANT));
	}
	
	@Test()
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveAgreement() {
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		
		/**Find Agreement to remove*/
		Agreement axeltisFastConnectAgreement = agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();			
		deleteAgreementInJpa(em -> {
			
			/**Do not detach and remove entity directly*/
			/**Remove the Agreement from the Service */
			em.remove(axeltisFastConnectAgreement);
			em.flush(); //Manually commit the transaction			
			em.clear(); //Detach managed entities from persistence context to reload new changes
			
		}, entityManager, jdbcTemplateProxy);
		
		/**Test Agreement was removed */		
		axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		/**Find Agreement to remove*/
		assertFalse(agreementRepo.findById(new AgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertAgreement() {
		/**Insert service*/
		Service muleEsbCons = Utils.insertService(MULE_ESB_CONSULTANT, entityManager);	
		
		/**Insert Contract*/
		Client barclays = Utils.insertClient(BARCLAYS, entityManager);		
		Contract accentureBarclaysContract = Utils.insertContract(barclays, CONTRACT1_NAME, entityManager);		
		
		/**Insert Agreement */		
		insertAgreementInJpa(em-> {
			Agreement agreementIn = new Agreement(accentureBarclaysContract, muleEsbCons);
			em.persist(agreementIn);
			em.flush(); //Manually commit the transaction
			
		}, entityManager, jdbcTemplateProxy);
		
		/** Find new Agreement*/
		AgreementId AgreementId = new AgreementId();
		AgreementId.setContractId(accentureBarclaysContract.getId());
		AgreementId.setServiceId(muleEsbCons.getId());
		/**Validate new Agreement*/
		Agreement AgreementOut =agreementRepo.findById(AgreementId).get();		
		assertEquals(ValidationResult.SUCCESS, isAgreementValid(AgreementOut, CONTRACT1_NAME, MULE_ESB_CONSULTANT));
	}
	
	

	@Test
	public void testToString() {
		Agreement agreement = new Agreement();
		assertThat(agreement.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}

}
