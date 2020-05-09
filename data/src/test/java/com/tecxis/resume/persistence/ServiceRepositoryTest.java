package com.tecxis.resume.persistence;

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

import com.tecxis.resume.Service;
import com.tecxis.resume.ServiceTest;


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
	public void testInsertServiceRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Service scmAssoc = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId());		
	}

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void findInsertedService() {	
		Service serviceIn = ServiceTest.insertAService(MULE_ESB_CONSULTANT, entityManager);
		Service serviceOut= serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		assertEquals(serviceIn, serviceOut);		
		
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindServiceByName() {		
		Service serviceOut= serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertEquals(TIBCO_BW_CONSULTANT, serviceOut.getName());		
		
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindServiceLikeName() {		
		List <Service> serviceList = serviceRepo.getServiceLikeName(TIBCO_BW_CONSULTANT);
		assertNotNull(serviceList);
		assertEquals(1, serviceList.size());
		Service tibcoBwConsultant = serviceList.get(0);
		assertEquals(TIBCO_BW_CONSULTANT, tibcoBwConsultant.getName());
		/**Test query by name with LIKE expression*/
		List <Service> javaConsultantList  =  serviceRepo.getServiceLikeName(DEVELOPER_WILDCARD);
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
		List <Service> bwServiceList = serviceRepo.getServiceLikeName(BUSINESS_WORKS_WILDCARD);
		assertNotNull(bwServiceList);
		assertEquals(1, bwServiceList.size());
		Service tibcoBwConsultantShort = bwServiceList.get(0);
		assertEquals(TIBCO_BW_CONSULTANT, tibcoBwConsultantShort.getName());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindService() {		
		Service muleEsbService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);
		assertNotNull(muleEsbService);
		assertEquals(MULE_ESB_CONSULTANT, muleEsbService.getName());
		
				
	}
		

	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteService() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));		
		Service tempService = ServiceTest.insertAService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		serviceRepo.delete(tempService);
		assertEquals(0, serviceRepo.getServiceLikeName(SCM_ASSOCIATE_DEVELOPPER).size());
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(6, serviceRepo.count());
		List <Service> services = serviceRepo.findAll();
		assertEquals(6, services.size());
	}
}
