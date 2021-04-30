package com.tecxis.resume.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.domain.constants.Tables;
import com.tecxis.resume.domain.id.ContractServiceAgreementId;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ContractRepository;
import com.tecxis.resume.domain.repository.ContractServiceAgreementRepository;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.repository.SupplierRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveContractServiceAgreement() {
		/**Find Client*/
		Client axeltis = clientRepo.getClientByName(Constants.AXELTIS);
		assertEquals(Constants.AXELTIS, axeltis.getName());
		
		/**Find supplier*/
		Supplier fastconnect = supplierRepo.getSupplierByName(Constants.FASTCONNECT);
		assertEquals(Constants.FASTCONNECT, fastconnect.getName());
				
		/**Find Contract*/
		Contract axeltisFastConnectcontract = contractRepo.getContractByName(Constants.CONTRACT7_NAME);
		
		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(Constants.TIBCO_BW_CONSULTANT);

		/**Find ContractServiceAgreement to remove*/
		ContractServiceAgreement axeltisFastConnectContractServiceAgreement = contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontract, tibcoCons)).get();
				
		/**Do not detach and remove entity directly*/		
				
		/**Remove ContractServiceAgreement*/
		assertEquals(13, countRowsInTable(jdbcTemplate, Tables.CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.remove(axeltisFastConnectContractServiceAgreement);
		entityManager.flush();
		entityManager.clear();
		assertEquals(12, countRowsInTable(jdbcTemplate, Tables.CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/**Test ContractServiceAgreement was removed */
		/**Find Client*/
		axeltis = clientRepo.getClientByName(Constants.AXELTIS);
		fastconnect = supplierRepo.getSupplierByName(Constants.FASTCONNECT);		
		axeltisFastConnectcontract = contractRepo.getContractByName(Constants.CONTRACT7_NAME);
		tibcoCons = serviceRepo.getServiceByName(Constants.TIBCO_BW_CONSULTANT);

		/**Find ContractServiceAgreement to remove*/
		assertFalse(contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontract, tibcoCons)).isPresent());
			
		
	}
	
	@Test
	public void testToString() {
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement();
		contractServiceAgreement.toString();
	}
	

	public static ContractServiceAgreement insertAContractServiceAgreement(Contract contract, Service service, EntityManager entityManager) {
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement(contract, service);		
		entityManager.persist(contractServiceAgreement);
		entityManager.flush();
		return contractServiceAgreement;
	}

}
