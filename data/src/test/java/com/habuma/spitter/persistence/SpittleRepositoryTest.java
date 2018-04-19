package com.habuma.spitter.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.Date;

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
import com.habuma.spitter.domain.Spittle;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })


//@Rollback
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class SpittleRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SpittleRepository spittleRepo;
	
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
	
	@Test
	@Sql("classpath:schema.sql")
	@Sql(scripts="classpath:data.sql" , executionPhase = BEFORE_TEST_METHOD)
	public void shouldCreateRowsAndSetIds() {
		assertEquals(2, countRowsInTable(jdbcTemplate, "spitter"));
		Spitter artnames = spitterRepo.getSpitterByUsername("artnames");
		insertASpittle(artnames, "This is a test comment", new Date());		
		assertEquals(4, countRowsInTable(jdbcTemplate, "spittle"));		
		insertASpittle(artnames, "This is a new test comment", new Date());
		assertEquals(5, countRowsInTable(jdbcTemplate, "spittle"));
	}
	
	@Test
	@Sql("classpath:schema.sql")
	@Sql(scripts="classpath:data.sql" , executionPhase = BEFORE_TEST_METHOD)
	public void shouldBeAbleToFindInsertedSpittle() {
		Spitter artnames = spitterRepo.getSpitterByUsername("artnames");
		Spittle spittleIn = insertASpittle(artnames, "This is a new test comment", new Date());		
		Spittle spitterOut = spittleRepo.getSpittleById(spittleIn.getId());		
		assertEquals(spittleIn, spitterOut);
	}


	@Test 
	@Sql("classpath:schema.sql")
	@Sql(scripts="classpath:data.sql" , executionPhase = BEFORE_TEST_METHOD)
	public void testGetSpittleById() {	
		
		Spittle spittle = spittleRepo.getSpittleById(1);
		assertNotNull(spittle);
		assertEquals("Have you read Spring in Action 3? I hear it is awesome!" , spittle.getText());
		Spitter habuma = spitterRepo.getSpitterByUsername("habuma");
		assertEquals(habuma , spittle.getSpitter());
		
		Spitter artnames = spitterRepo.getSpitterByUsername("artnames");
		spittle = spittleRepo.getSpittleById(2);
		assertEquals(artnames , spittle.getSpitter());
		
	
	}
		
	@Test 
	@Sql("classpath:schema.sql")
	@Sql(scripts="classpath:data.sql" , executionPhase = BEFORE_TEST_METHOD)
	public void testDeleteSpittleById() {
		assertEquals(3, countRowsInTable(jdbcTemplate, "spittle"));
		Spitter artnames = spitterRepo.getSpitterByUsername("artnames");
		Long spittleId = insertASpittle(artnames, "This is a test comment", new Date()).getId();
		assertEquals(4, countRowsInTable(jdbcTemplate, "spittle"));
		spittleRepo.delete(spittleId);
		assertNull(spittleRepo.getSpittleById(spittleId));
		assertEquals(3, countRowsInTable(jdbcTemplate, "spittle"));
		
	}
	
	private Spittle insertASpittle(Spitter spitter, String text, Date when) {
		Spittle spittle = new Spittle();		
		spittle.setText(text);
		spittle.setWhen(when);
		spittle.setSpitter(spitter);
		assertNull(spittle.getId());
		spittleRepo.save(spittle);
		assertNotNull(spittle.getId());
		updateWithEntityManagerFlush();
		return spittle;
		
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
