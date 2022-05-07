package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.util.Utils.deleteClientInJpa;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.repository.ClientRepository;

@Component
public class StandaloneDaoTester {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneDaoTester.class);
	
	@PersistenceContext()
	private EntityManager em;
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	@Qualifier("jdbcTemplateProxy")	
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	@Qualifier("jdbcTemplateHelper")	
	private JdbcTemplate jdbcTemplateHelper;

	public static void main(String[] args) throws IOException {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:spring-context/test-context.xml");
		StandaloneDaoTester tester = appContext.getBean(StandaloneDaoTester.class);
		tester.init();
		tester.start(args);
		/** Validate client doesn't exist */
		ClientRepository clientRepo = appContext.getBean(ClientRepository.class); 
		assertNull(clientRepo.getClientByName(AXELTIS));
		LOGGER.debug("########## Completed!");
	}
	
	@Transactional(transactionManager = "txManagerHelper", isolation = Isolation.READ_COMMITTED)
	public void init() throws IOException {
		File dropSchemaFile = new ClassPathResource("SQL/H2/DropResumeSchema.sql").getFile();	    
	    String dropSchemaScript = FileUtils.readFileToString(dropSchemaFile, "UTF-8");
	    jdbcTemplateHelper.execute(dropSchemaScript);
	    
	    File createSchemaFile = new ClassPathResource("SQL/H2/CreateResumeSchema.sql").getFile();
	    String createSchemaScript = FileUtils.readFileToString(createSchemaFile, "UTF-8");
	    jdbcTemplateHelper.execute(createSchemaScript);
	    
	    File insertSchemaFile = new ClassPathResource("SQL/InsertResumeData.sql").getFile();
	    String insertSchemaScript = FileUtils.readFileToString(insertSchemaFile, "UTF-8");
	    jdbcTemplateHelper.execute(insertSchemaScript);
	    
	}
	
	@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)
	public void start (String[] args) {//Method should be public otherwise the following error occurs: Exception in thread "main" javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
		
			assertNotNull(jdbcTemplateProxy);
			assertNotNull(jdbcTemplateHelper);
			assertNotNull(em);
			assertNotNull(clientRepo);
			LOGGER.info("************************ Transaction started!");
			/** Execute transaction */
			deleteClientInJpa(deleteClientFunction -> {
				Client axeltis = clientRepo.getClientByName(AXELTIS);
//				Client axeltis =entityManager.createQuery(
//					"select c " +
//					"from Client c " +
//					"where c.name = :clientName ", Client.class)
//				.setParameter("clientName", AXELTIS)
//				.getSingleResult();
				/** Validate client doesn't exist */
				assertNotNull(axeltis);
				/** Remove client */
				LOGGER.info("************************ Managed 'axeltis' enity: " + em.contains(axeltis));				
				em.remove(axeltis);
				em.flush();	//manually commit the transaction	
				em.clear(); //Detach managed entities from persistence context to reload new change
							

			}, em, jdbcTemplateProxy);
			
			/** Commit transaction */	
			LOGGER.info("************************ Committing transaction");		
	
	}
}
