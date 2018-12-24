package com.tecxis.resume.persistence;

import static com.tecxis.resume.StaffTest.insertAStaff;
import static com.tecxis.resume.persistence.ClientRepositoryTest.BARCLAYS;
import static com.tecxis.resume.persistence.ClientRepositoryTest.EULER_HERMES;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT4_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT4_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CURRENT_DATE;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALPHATRESS;
import static com.tecxis.resume.persistence.SupplierRepositoryTest.ALTERNA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
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
public class ServiceRepositoryTest {

	public static String SERVICE_TABLE = "Service";
	public static String SCM_ASSOCIATE_DEVELOPPER = "Associate Software Configuration Management";
	public static String JAVA_INTEGRATION_DEVELOPPER = "Java Integration Developer";
	public static String LIFERAY_DEVELOPPER = "Liferay Developer";
	public static String J2EE_DEVELOPPER = "J2EE Developer";
	public static String MULE_ESB_CONSULTANT = "Mule ESB Consultant";
	public static String TIBCO_BW_CONSULTANT = "TIBCO Business Works Developer";
	public static String DEVELOPER_WILDCARD = "%Developer";
	public static String BUSINESS_WORKS_WILDCARD = "%Business Works%";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceRepository serviceRepo;
		
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(EULER_HERMES, entityManager);
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALTERNA, entityManager);
		Contract alphatressBarclaysContract = ContractTest.insertAContract(barclays, alphatress,  amt, CURRENT_DATE, null,  entityManager);
		Service scmAssoc = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, alphatressBarclaysContract, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getServiceId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedService() {
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(BARCLAYS, entityManager);		
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALPHATRESS, entityManager);
		Contract accentureBarclaysContract = ContractTest.insertAContract(barclays, alphatress, amt, CONTRACT12_STARTDATE, CONTRACT12_ENDDATE, entityManager);
		Service serviceIn = ServiceTest.insertAService(MULE_ESB_CONSULTANT, accentureBarclaysContract, entityManager);
		List <Service> serviceList = serviceRepo.getServiceLikeName(MULE_ESB_CONSULTANT);
		assertEquals(1, serviceList.size());
		Service serviceOut = serviceList.get(0);		
		assertEquals(serviceIn, serviceOut);		
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindServiceLikeName() {		
		List <Service> serviceList = serviceRepo.getServiceLikeName(TIBCO_BW_CONSULTANT);
		assertNotNull(serviceList);
		assertEquals(9, serviceList.size());
		Service tibcoBwConsultant = serviceList.get(0);
		assertEquals(TIBCO_BW_CONSULTANT, tibcoBwConsultant.getName());
		/**Test query by name with LIKE expression*/
		List <Service> javaConsultantList  =  serviceRepo.getServiceLikeName(DEVELOPER_WILDCARD);
		assertNotNull(javaConsultantList);
		assertEquals(12, javaConsultantList.size());
		assertThat(javaConsultantList.get(0).getName(), Matchers.is(Matchers.oneOf(JAVA_INTEGRATION_DEVELOPPER, 
				LIFERAY_DEVELOPPER,
				J2EE_DEVELOPPER,
				TIBCO_BW_CONSULTANT
				)));
		assertThat(javaConsultantList.get(1).getName(), Matchers.is(Matchers.oneOf(JAVA_INTEGRATION_DEVELOPPER,
				LIFERAY_DEVELOPPER,
				J2EE_DEVELOPPER,
				LIFERAY_DEVELOPPER
				)));
		/** Test query with LIKE expression */
		List <Service> bwServiceList = serviceRepo.getServiceLikeName(BUSINESS_WORKS_WILDCARD);
		assertNotNull(bwServiceList);
		assertEquals(9, bwServiceList.size());
		Service tibcoBwConsultantShort = bwServiceList.get(0);
		assertEquals(TIBCO_BW_CONSULTANT, tibcoBwConsultantShort.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindServiceContract() {
		List <Service> j2eeDevelopperServices = serviceRepo.getServiceLikeName(J2EE_DEVELOPPER);		
		assertEquals(1, j2eeDevelopperServices.size());
		Service j2eeDevelopperService = j2eeDevelopperServices.get(0);		
		Contract j2eeDevelopperContract = j2eeDevelopperService.getContract();
		assertNotNull(j2eeDevelopperContract);
		assertEquals(CONTRACT4_STARTDATE, j2eeDevelopperContract.getStartDate());
		assertEquals(CONTRACT4_ENDDATE, j2eeDevelopperContract.getEndDate());
		
		
		List <Service> muleEsbServices = serviceRepo.getServiceLikeName(MULE_ESB_CONSULTANT);
		assertNotNull(muleEsbServices);
		assertEquals(1, muleEsbServices.size());
		Service muleEsbService = muleEsbServices.get(0);		
		Contract muleEsbServiceContract = muleEsbService.getContract();
		assertNotNull(muleEsbServiceContract);
		assertEquals(CONTRACT5_STARTDATE ,muleEsbServiceContract.getStartDate());
		assertEquals(CONTRACT5_ENDDATE ,muleEsbServiceContract.getEndDate());
				
	}
		

	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteService() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Staff amt = insertAStaff(AMT_NAME, AMT_LASTNAME, entityManager);
		Client barclays = ClientTest.insertAClient(EULER_HERMES, entityManager);
		Supplier alphatress = SupplierTest.insertASupplier(amt, ALTERNA, entityManager);
		Contract alphatressBarclaysContract = ContractTest.insertAContract(barclays, alphatress,  amt, CURRENT_DATE, null,  entityManager);
		Service tempService = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, alphatressBarclaysContract, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		serviceRepo.delete(tempService);
		assertEquals(0, serviceRepo.getServiceLikeName(SCM_ASSOCIATE_DEVELOPPER).size());
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(14, serviceRepo.count());
		List <Service> services = serviceRepo.findAll();
		assertEquals(14, services.size());
	}
}
