package com.tecxis.resume.persistence;

import static com.tecxis.resume.StaffTest.insertAStaff;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.EULER_HERMES;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT4_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT4_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CURRENT_DATE;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.J2EE_DEVELOPPER;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.persistence.ServiceRepositoryTest.SERVICE_TABLE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import com.tecxis.resume.Client;
import com.tecxis.resume.ClientTest;
import com.tecxis.resume.Contract;
import com.tecxis.resume.ContractServiceAgreement;
import com.tecxis.resume.ContractServiceAgreement.ContractServiceAgreementId;
import com.tecxis.resume.ContractTest;
import com.tecxis.resume.Service;
import com.tecxis.resume.ServiceTest;
import com.tecxis.resume.Staff;
import com.tecxis.resume.Supplier;
import com.tecxis.resume.SupplierTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class ContractServiceAgreementRepositoryTest {
	public static final String CONTRACT_SERVICE_AGREEMENT_TABLE = "CONTRACT_SERVICE_AGREEMENT";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	private ContractServiceAgreementRepository contractServiceAgreementRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertServiceWithContrastServiceAgreementsRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getServiceId());
		/**Insert Contract*/
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(EULER_HERMES, entityManager);
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALTERNA, entityManager);
		Contract alphatressBarclaysContract = ContractTest.insertAContract(barclays, alphatress, CURRENT_DATE, null,  entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement contractServiceAgreement = ContractServiceAgreementTest.insertAContractServiceAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(contractServiceAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedContractServiceAgreement() {
		/**Insert service*/
		Service muleEsbCons = ServiceTest.insertAService(MULE_ESB_CONSULTANT, entityManager);
		
		
		/**Insert Contract*/
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALPHATRESS, entityManager);
		Contract accentureBarclaysContract = ContractTest.insertAContract(barclays, alphatress, CONTRACT12_STARTDATE, CONTRACT12_ENDDATE, entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement contractServiceAgreementIn = ContractServiceAgreementTest.insertAContractServiceAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertNotNull(contractServiceAgreementIn);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/** Build ContraServiceAgreement Id*/
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(accentureBarclaysContract);
		contractServiceAgreementId.setService(muleEsbCons);
		
		ContractServiceAgreement contractServiceAgreementOut =contractServiceAgreementRepo.findById(contractServiceAgreementId).get();		
		assertEquals(contractServiceAgreementIn, contractServiceAgreementOut);		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindContractServiceAgreement() {
		/**Get Service*/
		List <Service> j2eeDevelopperServices = serviceRepo.getServiceLikeName(J2EE_DEVELOPPER);		
		assertEquals(1, j2eeDevelopperServices.size());
		Service j2eeDevelopperService = j2eeDevelopperServices.get(0);	
		
		/**Get Contract*/
		List <ContractServiceAgreement> j2eeDevelopperContractServiceAgreements = j2eeDevelopperService.getContractServiceAgreements();
		assertNotNull(j2eeDevelopperContractServiceAgreements);
		assertEquals(1, j2eeDevelopperContractServiceAgreements.size());
		Contract j2eeDevelopperContract = j2eeDevelopperContractServiceAgreements.get(0).getContractServiceAgreementId().getContract();
		assertEquals(CONTRACT4_STARTDATE, j2eeDevelopperContract.getStartDate());
		assertEquals(CONTRACT4_ENDDATE, j2eeDevelopperContract.getEndDate());
		
		/**Find ContractServiceAgreement*/
		ContractServiceAgreementId contractServiceAgreementId = new ContractServiceAgreementId();
		contractServiceAgreementId.setContract(j2eeDevelopperContract);
		contractServiceAgreementId.setService(j2eeDevelopperService);
		ContractServiceAgreement contractServiceAgreement = contractServiceAgreementRepo.findById(contractServiceAgreementId).get();
		assertEquals(j2eeDevelopperContract, contractServiceAgreement.getContractServiceAgreementId().getContract());
		assertEquals(j2eeDevelopperService, contractServiceAgreement.getContractServiceAgreementId().getService());
		assertNotNull(contractServiceAgreement);
				
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteServiceContractAgreement() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		/**Insert service*/
		Service scmAssoc = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getServiceId());
		/**Insert Contract*/
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(EULER_HERMES, entityManager);
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALTERNA, entityManager);
		Contract alphatressBarclaysContract = ContractTest.insertAContract(barclays, alphatress, CURRENT_DATE, null,  entityManager);
		
		/**Insert ContraServiceAgreement */
		ContractServiceAgreement tempContractServiceAgreement = ContractServiceAgreementTest.insertAContractServiceAgreement(alphatressBarclaysContract, scmAssoc, entityManager);
		assertNotNull(tempContractServiceAgreement);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
		
		/**Delete ContraServiceAgreement and test*/
		entityManager.remove(tempContractServiceAgreement);	
		entityManager.flush();
		entityManager.clear();
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_SERVICE_AGREEMENT_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(14, contractServiceAgreementRepo.count());
		List <ContractServiceAgreement> contractServiceAgreements = contractServiceAgreementRepo.findAll();
		assertEquals(14, contractServiceAgreements.size());
	}

}
