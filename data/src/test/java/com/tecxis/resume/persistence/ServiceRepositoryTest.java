package com.tecxis.resume.persistence;

import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT10_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT10_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT11_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT11_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT12_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT13_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT1_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT1_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT5_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT6_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT6_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT7_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT8_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT8_STARTDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_ENDDATE;
import static com.tecxis.resume.persistence.ContractRepositoryTest.CONTRACT9_STARTDATE;
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

import com.tecxis.resume.Contract;
import com.tecxis.resume.Service;


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
	public static String J2EE_DEVELOPPER = "Java J2EE Developer";
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
		Service scmAssoc = insertAService(SCM_ASSOCIATE_DEVELOPPER,  entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getServiceId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedService() {
		Service serviceIn = insertAService(MULE_ESB_CONSULTANT, entityManager);
		List <Service> serviceList = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);
		assertEquals(1, serviceList.size());
		Service serviceOut = serviceList.get(0);		
		assertEquals(serviceIn, serviceOut);		
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindServiceByName() {
		List <Service> serviceList = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		assertNotNull(serviceList);
		assertEquals(1, serviceList.size());
		Service tibcoBwConsultant = serviceList.get(0);
		assertEquals(TIBCO_BW_CONSULTANT, tibcoBwConsultant.getName());
		/**Test query by name with LIKE expression*/
		List <Service> javaConsultantList  =  serviceRepo.getServiceByName(DEVELOPER_WILDCARD);
		assertNotNull(javaConsultantList);
		assertEquals(4, javaConsultantList.size());
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
		List <Service> bwServiceList = serviceRepo.getServiceByName(BUSINESS_WORKS_WILDCARD);
		assertNotNull(bwServiceList);
		assertEquals(1, bwServiceList.size());
		Service tibcoBwConsultantShort = bwServiceList.get(0);
		assertEquals(TIBCO_BW_CONSULTANT, tibcoBwConsultantShort.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindServiceContracts() {
		List <Service> tibcoBwServices = serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);
		assertNotNull(tibcoBwServices);
		assertEquals(1, tibcoBwServices.size());
		Service tibcoBwService = tibcoBwServices.get(0);
		assertNotNull(tibcoBwService.getContracts());
		List <Contract> tibcoBwServiceContracts = tibcoBwService.getContracts();
		assertEquals(8, tibcoBwServiceContracts.size());
		Contract tibcoBwServiceContract1 = tibcoBwServiceContracts.get(0);
		assertThat(tibcoBwServiceContract1.getStartDate(), Matchers.is(Matchers.oneOf(
				CONTRACT6_STARTDATE, CONTRACT7_STARTDATE, 
				CONTRACT8_STARTDATE, CONTRACT9_STARTDATE, 
				CONTRACT10_STARTDATE, CONTRACT11_STARTDATE, 
				CONTRACT12_STARTDATE, CONTRACT13_STARTDATE
				)));
		assertThat(tibcoBwServiceContract1.getEndDate(), Matchers.is(Matchers.oneOf(
				CONTRACT6_ENDDATE, CONTRACT7_ENDDATE, 
				CONTRACT8_ENDDATE, CONTRACT9_ENDDATE, 
				CONTRACT10_ENDDATE, CONTRACT11_ENDDATE, 
				CONTRACT12_ENDDATE, CONTRACT13_ENDDATE
				)));
		
		List <Service> muleServices = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);
		assertNotNull(muleServices);
		assertEquals(1, muleServices.size());
		Service muleService = muleServices.get(0);
		assertNotNull(muleService.getContracts());
		List <Contract> muleServiceContracts = muleService.getContracts();
		assertEquals(1, muleServiceContracts.size());
		Contract muleServiceContract = muleServiceContracts.get(0);
		assertEquals(CONTRACT5_STARTDATE, muleServiceContract.getStartDate());
		assertEquals(CONTRACT5_ENDDATE, muleServiceContract.getEndDate());
		
		List <Service> scmServices = serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER);
		assertNotNull(scmServices);
		assertEquals(1, scmServices.size());
		Service scmService = scmServices.get(0);
		assertNotNull(scmService.getContracts());
		List <Contract> scmServiceContracts = scmService.getContracts();
		assertEquals(1, scmServiceContracts.size());
		Contract scmServiceContract = scmServiceContracts.get(0);
		assertEquals(CONTRACT1_STARTDATE, scmServiceContract.getStartDate());
		assertEquals(CONTRACT1_ENDDATE, scmServiceContract.getEndDate());
	}

	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteService() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Service tempService = insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		serviceRepo.delete(tempService);
		assertEquals(0, serviceRepo.getServiceByName(SCM_ASSOCIATE_DEVELOPPER).size());
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}
	
	public static Service insertAService(String name, EntityManager entityManager) {
		Service service = new Service();
		service.setName(name);
		assertEquals(0, service.getServiceId());
		entityManager.persist(service);
		entityManager.flush();
		assertThat(service.getServiceId(), Matchers.greaterThan((long)0));
		return service;
	}
}
