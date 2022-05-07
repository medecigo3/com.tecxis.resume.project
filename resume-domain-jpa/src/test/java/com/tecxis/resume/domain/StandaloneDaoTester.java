package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AXELTIS;
import static com.tecxis.resume.domain.util.Utils.deleteClientInJpa;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecxis.resume.domain.repository.ClientRepository;

@Component
public class StandaloneDaoTester {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneDaoTester.class);
	
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ClientRepository clientRepo; //TODO maybe this repo bean isn't constructed with entityManagerFactoryProxy bean. Do research and use the correct spring way to commit this transaction tather than manually commit.
	
	@Autowired
	@Qualifier("jdbcTemplateProxy")	
	private JdbcTemplate jdbcTemplateProxy;

	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:spring-context/test-context.xml");
		StandaloneDaoTester tester = appContext.getBean(StandaloneDaoTester.class);
		tester.start(args);
	}
	
	private void start (String[] args) {	
		EntityTransaction txn = null;
		try {

			/** Init transaction */
			txn = em.getTransaction();
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
				LOGGER.info("************************ Managed 'axeltis' enity: " + em.contains(axeltis));				
				em.remove(axeltis);
				/** Will remove the Client's orphans -> Project, Contract, etc */
							

			}, em, jdbcTemplateProxy);
			
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
			if (em != null)
				em.close();
		}
		/** Validate client doesn't exist */
		assertNull(clientRepo.getClientByName(AXELTIS));
		LOGGER.debug("########## Completed!");
	}
}
