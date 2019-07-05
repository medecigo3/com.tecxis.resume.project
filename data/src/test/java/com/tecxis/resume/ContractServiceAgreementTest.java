package com.tecxis.resume;

import static com.tecxis.resume.persistence.ClientRepositoryTest.AXELTIS;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_STARTDATE;
import static com.tecxis.resume.persistence.ContractServiceAgreementRepositoryTest.CONTRACT_SERVICE_AGREEMENT_TABLE;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.TIBCO_BW_CONSULTANT;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.FASTCONNECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

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

import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;
import com.tecxis.resume.persistence.ClientRepository;
import com.tecxis.resume.persistence.ContractRepository;
import com.tecxis.resume.persistence.ContractServiceAgreementRepository;
import com.tecxis.resume.persistence.ServiceRepository;
import com.tecxis.resume.persistence.SupplierRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
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
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)	
	public void testRemoveContractServiceAgreement() {
		/**Find Client*/
		Client axeltis = clientRepo.getClientByName(AXELTIS);
		assertEquals(AXELTIS, axeltis.getName());
		
		/**Find supplier*/
		Supplier fastconnect = supplierRepo.getSuppliersByName(FASTCONNECT).get(0);
		assertEquals(FASTCONNECT, fastconnect.getName());
				
		/**Find Contract*/
		List <Contract> axeltisFastConnectcontracts = contractRepo.findByClientAndSupplierAndStartDateAndEndDateOrderByStartDateAsc(axeltis, fastconnect, CONTRACT7_STARTDATE, CONTRACT7_ENDDATE);
		assertEquals(1, axeltisFastConnectcontracts .size());
		
		/**Find Service*/
		Service tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);

		/**Find ContractServiceAgreement to remove*/
		ContractServiceAgreement axeltisFastConnectContractServiceAgreement = contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontracts.get(0), tibcoCons)).get();
				
		/**Do not detach and remove entity directly*/		
				
		/**Remove ContractServiceAgreement*/
		assertEquals(14, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		entityManager.remove(axeltisFastConnectContractServiceAgreement);
		entityManager.flush();
		entityManager.clear();
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/**Test ContractServiceAgreement was removed */
		/**Find Client*/
		axeltis = clientRepo.getClientByName(AXELTIS);
		fastconnect = supplierRepo.getSuppliersByName(FASTCONNECT).get(0);		
		axeltisFastConnectcontracts = contractRepo.findByClientAndSupplierAndStartDateAndEndDateOrderByStartDateAsc(axeltis, fastconnect, CONTRACT7_STARTDATE, CONTRACT7_ENDDATE);
		tibcoCons = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);

		/**Find ContractServiceAgreement to remove*/
		assertFalse(contractServiceAgreementRepo.findById(new ContractServiceAgreementId(axeltisFastConnectcontracts.get(0), tibcoCons)).isPresent());
			
		
	}
	

	public static ContractServiceAgreement insertAContractServiceAgreement(Contract contract, Service service, EntityManager entityManager) {
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(contract);
		contractServiceAgreementId.setService(service);
		ContractServiceAgreement contractServiceAgreement = new ContractServiceAgreement();
		contractServiceAgreement.setContractServiceAgreementId(contractServiceAgreementId);
		entityManager.persist(contractServiceAgreement);
		entityManager.flush();
		return contractServiceAgreement;
	}

}
