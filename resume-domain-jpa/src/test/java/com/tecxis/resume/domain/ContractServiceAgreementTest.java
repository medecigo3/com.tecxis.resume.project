package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.Constants.CONTRACT7_NAME;
import static com.tecxis.resume.domain.Constants.FASTCONNECT;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_NESTED_ID_REGEX;
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

import com.tecxis.resume.domain.id.ContractServiceAgreementId;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;
import com.tecxis.resume.domain.util.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
public class ContractServiceAgreementTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired 
	private ClientRepository clientRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;
	
	
	
	@Test()
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveContractServiceAgreement() {
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

		/**Find ContractServiceAgreement to remove*/
		ContractServiceAgreement axeltisFastConnectContractServiceAgreement = contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).get();
				
		/**Do not detach and remove entity directly*/		
				
		/**Remove ContractServiceAgreement*/
		UtilsTest.testStateBeforeDelete(jdbcTemplate); 
		/**Remove the ContractServiceAgreement from the Service */
		entityManager.remove(axeltisFastConnectContractServiceAgreement);
		entityManager.flush();
		entityManager.clear();
		UtilsTest.testStateAfterAxeltisFastconnectContractServiceAgreementDelete(jdbcTemplate);
		
		/**Test ContractServiceAgreement was removed */
		/**Find Client*/
		axeltis = clientRepo.getClientByName(AXELTIS);
		fastconnect = supplierRepo.getSupplierByName(FASTCONNECT);		
		axeltisFastConnectcontract = contractRepo.getContractByName(CONTRACT7_NAME);
		tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);

		/**Find ContractServiceAgreement to remove*/
		assertFalse(contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontract.getId(), tibcoCons.getId())).isPresent());
			
		
	}

	@Test
	public void testToString() {
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement();
		assertThat(contractServiceAgreement.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
	}

}
