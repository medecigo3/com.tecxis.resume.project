package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.util.Utils.deleteClientInJpa;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tecxis.resume.domain.repository.ClientRepository;

public class StandaloneTester {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneTester.class);

	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:spring-context/test-context.xml");
		EntityManagerFactory entityManagerFactory = appContext.getBean("entityManagerFactoryProxy", EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		ClientRepository clientRepo = appContext.getBean(ClientRepository.class); //TODO maybe this repo bean isn't constructed with entityManagerFactoryProxy bean. Do research and use the correct spring way to commit this transaction tather than manually commit. 
		JdbcTemplate jdbcTemplateProxy = appContext.getBean("jdbcTemplateProxy", JdbcTemplate.class);
		EntityTransaction txn = null;
		try {

			/** Init transaction */
			txn = entityManager.getTransaction();
			txn.begin();
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
				LOGGER.info("************************ Managed 'axeltis' enity: " + entityManager.contains(axeltis));				
				entityManager.remove(axeltis);
				/** Will remove the Client's orphans -> Project, Contract, etc */
							

			}, entityManager, jdbcTemplateProxy);
			
			/** Commit transaction */	
			LOGGER.info("************************ Committing transaction");
			txn.commit();
			LOGGER.info("************************ Transaction commited!");
		

		} catch (Throwable t) {
			if (txn != null && txn.isActive()) {
				try {
					txn.rollback();
					LOGGER.warn("!!!!!!!!!!!!!! Transaction rollbacked!", t);
				} catch (Exception e) {
					LOGGER.error("!!!!!!!!!!!!! Rollback failure", e);
				}
			}
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		/** Validate client doesn't exist */
		assertNull(clientRepo.getClientByName(AXELTIS));
		LOGGER.debug("########## Completed!");
	}
}
