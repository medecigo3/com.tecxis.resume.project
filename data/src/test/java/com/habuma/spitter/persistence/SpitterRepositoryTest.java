package com.habuma.spitter.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.habuma.spitter.domain.Spitter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })


//@Rollback
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SpitterRepositoryTest {
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SpitterRepository spitterRepo;




	@BeforeTransaction
	void verifyInitialDatabaseState() {
		// logic to verify the initial state before a transaction is started
//		assertEquals(2, countRowsInTable(jdbcTemplate, "spitter"));
//		assertEquals(3, countRowsInTable(jdbcTemplate, "spittle"));
		 
	}

	@Before
	public void setUpTestDataWithinTransaction() {
//		JdbcTestUtils.deleteFromTables(jdbcTemplate, "spitter");
//		JdbcTestUtils.deleteFromTables(jdbcTemplate, "spittle");
	}



	@After
	public void cleanup() {
//		JdbcTestUtils.deleteFromTables(jdbcTemplate, "spitter");
//		JdbcTestUtils.deleteFromTables(jdbcTemplate, "spittle");
	}

	@Test
	@Sql("classpath:schema.sql")
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
		Spitter spitterOut = spitterRepo.getSpitterById(spitterIn.getId());		
		assertEquals(spitterIn, spitterOut);
	}
	
	@Test 
	@Sql("classpath:schema.sql")
	@Sql(scripts="classpath:data.sql" , executionPhase = BEFORE_TEST_METHOD)

	public void testGetSpitterById() {
		Spitter habuma = spitterRepo.getSpitterByUsername("habuma");
		assertNotNull(habuma);
		assertEquals("habuma", habuma.getUsername());
		assertEquals("password", habuma.getPassword());
		assertEquals("Craig Walls", habuma.getFullName());
		assertEquals("craig@habuma.com", habuma.getEmail());
		
		Spitter artnames = spitterRepo.getSpitterById(2);
		assertNotNull(artnames);
		assertEquals("artnames", artnames.getUsername());
		assertEquals("password", artnames.getPassword());
		assertEquals("Art Names", artnames.getFullName());
		assertEquals("artnames@habuma.com", artnames.getEmail());
			
	}
	
	@Test
	@Sql("classpath:schema.sql")
	public void testDeleteSpitterById() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "spitter"));
		Spitter tempSpitter = insertASpitter("temp", "password", "fullname", "email", false);
		assertEquals(1, countRowsInTable(jdbcTemplate, "spitter"));
		spitterRepo.delete(tempSpitter.getId());
		assertNull(spitterRepo.getSpitterByUsername("temp"));
		assertEquals(0, countRowsInTable(jdbcTemplate, "spitter"));
		
	}
	
	public void testGetSpitterByName() {
		Spitter habuma = spitterRepo.getSpitterByUsername("habuma");
		assertEquals("habuma", habuma.getUsername());
		assertEquals("password", habuma.getPassword());
		assertEquals("Craig Walls", habuma.getFullName());
		assertEquals("craig@habuma.com", habuma.getEmail());
	}

	private Spitter insertASpitter(String username, String password, String fullname, String email,	boolean updateByEmail) {
		Spitter spitter = new Spitter();
		spitter.setUsername(username);
		spitter.setPassword(password);
		spitter.setFullName(fullname);
		spitter.setEmail(email);
		spitter.setUpdateByEmail(updateByEmail);
		assertNull(spitter.getId());
		spitterRepo.save(spitter);
		assertNotNull(spitter.getId());
		updateWithEntityManagerFlush();
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
	
	
	@Transactional
	public void updateWithEntityManagerFlush() {
		// Manual flush is required to avoid false positive in test
		entityManager.flush();
	}

	
}
