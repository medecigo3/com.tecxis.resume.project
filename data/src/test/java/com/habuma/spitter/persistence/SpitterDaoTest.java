package com.habuma.spitter.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.habuma.spitter.domain.Spitter;

@RunWith(SpringJUnit4ClassRunner.class)
// TODO Annotate with @SpringJUnitConfig(TestConfig.class)
@ContextConfiguration(locations = { "classpath:persistence-context.xml", "classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })

//@Rollback
@Commit
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional(transactionManager = "txMgr", isolation = Isolation.READ_UNCOMMITTED)
public class SpitterDaoTest {
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SpitterDao dao;

	@BeforeTransaction
	void verifyInitialDatabaseState() {
		// logic to verify the initial state before a transaction is started
	}

	@Before
	public void setUpTestDataWithinTransaction() {
		deleteFromTables(jdbcTemplate, "spitter");
	}



	@After
	public void cleanup() {
		deleteFromTables(jdbcTemplate, "spitter");
	}

	@Test
	@Commit
	public void shouldCreateRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "spitter"));
		insertASpitter("username", "password", "fullname", "email", false);
		assertEquals(1, countRowsInTable(jdbcTemplate, "spitter"));
		insertASpitter("username2", "password2", "fullname2", "email2", false);
		assertEquals(2, countRowsInTable(jdbcTemplate, "spitter"));
	}

	@Test
	public void shouldBeAbleToFindInsertedSpitter() {
		Spitter spitterIn = insertASpitter("username", "password", "fullname", "email", false);

		Spitter spitterOut = dao.getSpitterById(spitterIn.getId());
		
		assertEquals(spitterIn, spitterOut);
	}

	private Spitter insertASpitter(String username, String password, String fullname, String email,
			boolean updateByEmail) {
		Spitter spitter = new Spitter();
		spitter.setUsername(username);
		spitter.setPassword(password);
		spitter.setFullName(fullname);
		spitter.setEmail(email);
		spitter.setUpdateByEmail(updateByEmail);
		assertNull(spitter.getId());
		dao.addSpitter(spitter);
		assertNotNull(spitter.getId());
		return spitter;
	}

	@After
	public void tearDownWithinTransaction() {
		// execute "tear down" logic within the transaction
	}

	@AfterTransaction
	void verifyFinalDatabaseState() {
		// logic to verify the final state after transaction has rolled back
	}
}
