package com.tecxis.resume.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import com.tecxis.resume.Assignment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class AssignmentRepositoryTest {
	
	public static final String ASSIGNMENT_TABLE = "Assignment";
	public static final String ASSIGNMENT1 = "Contributing to the build, deployment and software configuration lifecycle phases of a three tier Java banking system.";
	public static final String ASSIGNMENT2 = "Working on with Rational multi-site Clear Case tools to control and manage software code evolution.";
	public static final String ASSIGNMENT3 = "Acting on as a conduit between offshore and onshore development teams.";
	public static final String ASSIGNMENT4 = "Implementation of a new deployment tool significantly reducing deployment time costs.";
	public static final String ASSIGNMENT5 = "Mentoring and guiding of two new team joiners.";
	public static final String ASSIGNMENT6 = "Assisting in the integration of enterprise modules by complying with Enterprise Integration standard patterns.";
	public static final String ASSIGNMENT7 = "Assisting in the configuration of maven pom modules for unit testing and building of releases.";
	public static final String ASSIGNMENT8 = "Scripting to help in the build and deployment tasks.";
	public static final String ASSIGNMENT9 = "Coding and configuring a web based portal based on Liferay technology to make Accenture''s software assets available for all collaborators across the UKDC delivery center.";
	public static final String ASSIGNMENT10 = "Contributing to develop a product for an important company in the French energy sector, to monitor and manage energy consumption for the household market.";
	public static final String ASSIGNMENT11 = "Coding an authentication security system for the main Java enterprise service.";
	public static final String ASSIGNMENT12 = "Coding a JMX technology based system to monitor across the main Java enterprise services.";
	public static final String ASSIGNMENT13 = "Working with UML to code generation tools to conceive and develop a three tier Java architecture enterprise service.";
	public static final String ASSIGNMENT14 = "Provide technical expertise and assistance to migrate an older version of the Mule ESB code in production to later version 2.5.";
	public static final String ASSIGNMENT15 = "Configuration of multiple routes to direct the traffic in the ESB against multiple endpoints.";
	public static final String ASSIGNMENT16 = "Migration of XSLT style sheets from version 1.0 to 2.0 to comply with the new Saxon 2.0 XSLT processor embedded in the Mule ESB.";
	public static final String ASSIGNMENT17 = "Coding of XPath expressions to route SOAP traffic across different endpoints";
	public static final String ASSIGNMENT18 = "Coding of Java custom components adapted to the client business functional requirements.";
	public static final String ASSIGNMENT19 = "Involvement in the configuration of web service endpoints to comply with client security policies.";
	public static final String ASSIGNMENT20 = "Coding of a RGVML to RGV data transformation module to be integrated into a system based on the Calypso trading platform.";
	public static final String ASSIGNMENT21 = "Setup of TIBCO web services to expose data transformation services.";
	public static final String ASSIGNMENT22 = "Coding Business Works processes to retrieve, transform and integrate XML investment funds into a corporate database.";
	public static final String ASSIGNMENT23 = "Running unit tests to validate code against functional specifications.";
	public static final String ASSIGNMENT24 = "Coding of Business Works processes to automate the extraction, transformation, and transfer of client data.";
	public static final String ASSIGNMENT25 = "Coding Java business components to adapt Business Works code to business needs.";
	public static final String ASSIGNMENT26 = "Coding of XSLT style sheets for transformation of raw XML data into a canonical format.";
	public static final String ASSIGNMENT27 = "Setup of TIBCO web services to expose financial business data services.";
	public static final String ASSIGNMENT28 = "Automation of reporting tasks through the deployment of Business Works processing jobs.";
	public static final String ASSIGNMENT29 = "Deployment and configuration of Business Works components in production environment.";
	public static final String ASSIGNMENT30 = "Acting as a conduit between the business and developers.";
	public static final String ASSIGNMENT31 = "Writing of functional and technical documentation.";
	public static final String ASSIGNMENT32 = "Setup of TIBCO web service interfaces to integrate an ESB with other business components in a frontend-backend architecture.";
	public static final String ASSIGNMENT33 = "Analysis and estimation of code change requests through Quality Center";
	public static final String ASSIGNMENT34 = "Coding of WSDL and XSD schema files.";
	public static final String ASSIGNMENT37 = "Coding of XSLT style sheets for transformation of XML raw data to canonical data.";
	public static final String ASSIGNMENT39 = "Advising on TIBCO Business Works coding patterns and best practice support for Paris region and abroad.";
	public static final String ASSIGNMENT40 = "Coding of a Java API to simplify the CORBA abstraction layer for enterprise client and server applications.";
	public static final String ASSIGNMENT41 = "Architecting and designing the new CORBA client/server architecture for Paris region and abroad.";
	public static final String ASSIGNMENT42 = "Acting as a conduit between application managers and operations.";
	public static final String ASSIGNMENT43 = "Liaising with software editors (TIBCO and Microfocus) and onshore and offshore development teams.";
	public static final String ASSIGNMENT44 = "Design, development and testing of LO1, 2 and 3 of a rebranded car leasing website lying in architecture with a J2EE system as backend, Tibco as middleware and liferay and AngularJS as the frontend.";
	public static final String ASSIGNMENT45 = "Development and testing of LOT1 purchase order to delivery for the UK.";
	public static final String ASSIGNMENT46 = "Development and testing of LOT2 salary exchange for the UK.";
	public static final String ASSIGNMENT47 = "Development and testing of LOT3 incident management and booking maintenance service for Italy.";
	public static final String ASSIGNMENT48 = "Deployment of packages in DEV/UAT and goLive support assistance.";
	public static final String ASSIGNMENT49 = "Workshops at Swindon and Stevenage UK with the business to define Tibco technical specifications for LOT3.";
	public static final String ASSIGNMENT50 = "Development and testing of input and output half flows for the update and handling of product retrieval, supplier receipts and spare parts into MS Dynamics.";
	public static final String ASSIGNMENT51 = "Development of flows in batch mode to send new stockroom and facilities data into MS Dynamics.";
	public static final String ASSIGNMENT52 = "Deployment of packages in DEV, TST and PRD.";
	public static final String ASSIGNMENT53 = "Technical designs of flows including architecture diagram, sequence diagram, and container diagram.";
	public static final String ASSIGNMENT54 = "Development of flows using a publish event pattern";
	public static final String ASSIGNMENT55 = "Development of flows using TIBCO Active Spaces technology to retrieve and store transco values in the cache.";
	public static final String ASSIGNMENT56 = "Development of flows involving, proposal, policies quotes, claims documents.";
	public static final String ASSIGNMENT57 = "Improvements in the TIBCO maven framework to generate code with new standards and improvement of unit testing tool.";
	public static final String DEV_ASSIGNMENT_WILDCARD = "Development%";
		
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1, assignment1.getAssignmentId());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
						
		Assignment assignment2 = insertAssignment(ASSIGNMENT2, entityManager);
		assertEquals(2, assignment2.getAssignmentId());
		assertEquals(2, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedAssingmnet() {		
		Assignment assignmentIn = insertAssignment(ASSIGNMENT1, entityManager);
		Assignment assignmentOut = assignmentRepo.getAssignmentByDesc(ASSIGNMENT1);
		assertEquals(assignmentIn, assignmentOut);
	}
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAssignmentByLikeDesc() {
		/**Test query by name with LIKE expression*/
		List <Assignment> assignments1 = assignmentRepo.getAssignmentLikeDesc(DEV_ASSIGNMENT_WILDCARD);
		assertNotNull(assignments1);
		assertEquals(8, assignments1.size());
		Assignment assignment1 = assignments1.get(0);		
		assertThat(assignment1.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
		Assignment assignment2 = assignments1.get(1);
		assertThat(assignment2.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
		Assignment assignment3 = assignments1.get(2);
		assertThat(assignment3.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
		Assignment assignment4 = assignments1.get(3);
		assertThat(assignment4.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
		Assignment assignment5 = assignments1.get(4);
		assertThat(assignment5.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
		Assignment assignment6 = assignments1.get(5);
		assertThat(assignment6.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
		Assignment assignment7 = assignments1.get(6);
		assertThat(assignment7.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
		Assignment assignment8 = assignments1.get(7);
		assertThat(assignment8.getDesc(), Matchers.is(Matchers.oneOf(ASSIGNMENT45, ASSIGNMENT46, ASSIGNMENT47, ASSIGNMENT50, ASSIGNMENT51, ASSIGNMENT54, ASSIGNMENT55, ASSIGNMENT56)));
	}
	
	
	@Test
	@Sql(
			scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
			executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetAssignmentByDesc() {
		List <Assignment> assignments = assignmentRepo.findAll();
		assertEquals(55, assignments.size());
		Assignment assignment1 =  assignmentRepo.getAssignmentByDesc(ASSIGNMENT1);
		assertNotNull(assignment1);	
		assertEquals(ASSIGNMENT1, assignment1.getDesc());	
		Assignment assignment2 =  assignmentRepo.getAssignmentByDesc(ASSIGNMENT2);
		assertNotNull(assignment2);	
		assertEquals(ASSIGNMENT2, assignment2.getDesc());	
		Assignment assignment20 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT20);
		assertNotNull(assignment20);
		assertEquals(ASSIGNMENT20, assignment20.getDesc());
	}
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDelete() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		Assignment tempAssignment = insertAssignment(ASSIGNMENT57, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		assignmentRepo.delete(tempAssignment);
		assertNull(assignmentRepo.getAssignmentByDesc(ASSIGNMENT57));
		
	}
	
	public static Assignment insertAssignment(String desc, EntityManager entityManager) {
		Assignment assignment = new Assignment();
		assignment.setDesc(desc);
		assertEquals(0, assignment.getAssignmentId());
		entityManager.persist(assignment);
		assertThat(assignment.getAssignmentId(), Matchers.greaterThan((long)0));
		entityManager.flush();
		return assignment;
	}

}
