package com.tecxis.resume.persistence;


import static com.tecxis.resume.domain.Constants.BUSINESS_WORKS_WILDCARD;
import static com.tecxis.resume.domain.Constants.DEVELOPER_WILDCARD;
import static com.tecxis.resume.domain.Constants.J2EE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.JAVA_INTEGRATION_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.LIFERAY_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.MULE_ESB_CONSULTANT;
import static com.tecxis.resume.domain.Constants.SCM_ASSOCIATE_DEVELOPPER;
import static com.tecxis.resume.domain.Constants.TIBCO_BW_CONSULTANT;
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

import com.tecxis.resume.domain.SchemaConstants;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.repository.ServiceRepository;
import com.tecxis.resume.domain.util.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaServiceDaoTest {

	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private ServiceRepository serviceRepo;
		
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
		Service scmAssoc = Utils.insertService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
		assertEquals(1, scmAssoc.getId().longValue());		
	}

	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {	
		Service serviceIn = Utils.insertService(MULE_ESB_CONSULTANT, entityManager);
		Service serviceOut= serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);		
		assertEquals(serviceIn, serviceOut);		
		
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));		
		Service tempService = Utils.insertService(SCM_ASSOCIATE_DEVELOPPER, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
		serviceRepo.delete(tempService);
		assertEquals(0, serviceRepo.getServiceLikeName(SCM_ASSOCIATE_DEVELOPPER).size());
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.SERVICE_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){		
		assertEquals(6, serviceRepo.count());
		List <Service> services = serviceRepo.findAll();
		assertEquals(6, services.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Service> pageableService = serviceRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableService.getSize());
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetServiceLikeName() {		
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
			scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetServiceByName() {		
		Service serviceOut= serviceRepo.getServiceByName(TIBCO_BW_CONSULTANT);		
		assertEquals(TIBCO_BW_CONSULTANT, serviceOut.getName());		
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFind() {		
		Service muleEsbService = serviceRepo.getServiceByName(MULE_ESB_CONSULTANT);
		assertNotNull(muleEsbService);
		assertEquals(MULE_ESB_CONSULTANT, muleEsbService.getName());
		
				
	}
		


	

}
