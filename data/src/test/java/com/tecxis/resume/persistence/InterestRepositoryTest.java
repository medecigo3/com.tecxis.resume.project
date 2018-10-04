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

import com.tecxis.resume.Interest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class InterestRepositoryTest {

	public static final String INTEREST_TABLE = "Interest";
	public static final String HOBBY = "Apart from being an integration consultant, I enjoy practicing endurance sports. I am an avid short distance runner but after a recent knee injury I've found a new passion in road bike riding.";
	public static final String RUNNING = "Running";
	public static final String SWIMMING = "Swimming";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private InterestRepository interestRepo;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertRowsAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Interest hobby = insertAnInterest(HOBBY, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(1, hobby.getInterestId());
		
		Interest running = insertAnInterest(RUNNING, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(2, running.getInterestId());
		
		Interest swimming = insertAnInterest(SWIMMING, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(3, swimming.getInterestId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedInterest() {
		Interest hobbyIn = insertAnInterest(HOBBY, entityManager);
		List<Interest> hobbyOutList = interestRepo.getInterestByDesc("%bike%");
		assertEquals(1, hobbyOutList.size());
		Interest hobbyOut = hobbyOutList.get(0);
		assertEquals(hobbyIn, hobbyOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetInterestByDesc() {
		insertAnInterest(RUNNING, entityManager);		
		insertAnInterest(SWIMMING, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		List <Interest> hobbyList = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());
		hobbyList = interestRepo.getInterestByDesc(RUNNING);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(RUNNING, hobbyList.get(0).getDesc());
		hobbyList = interestRepo.getInterestByDesc(SWIMMING);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(SWIMMING, hobbyList.get(0).getDesc());
		hobbyList = interestRepo.getInterestByDesc("%bike%");
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());
		
	}
	
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteInterest() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Interest tempInterest = insertAnInterest(HOBBY, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		interestRepo.delete(tempInterest);
		assertEquals(0, interestRepo.getInterestByDesc(HOBBY).size());
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
	}
	
	public static Interest insertAnInterest(String desc, EntityManager entityManager) {
		Interest interest = new Interest();
		interest.setDesc(desc);
		assertEquals(0, interest.getInterestId());
		entityManager.persist(interest);
		entityManager.flush();
		assertThat(interest.getInterestId(), Matchers.greaterThan((long)0));
		return interest;
	}
}
