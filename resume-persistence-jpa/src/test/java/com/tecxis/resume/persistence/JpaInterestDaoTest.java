package com.tecxis.resume.persistence;


import static com.tecxis.resume.domain.Constants.HOBBY;
import static com.tecxis.resume.domain.Constants.RUNNING;
import static com.tecxis.resume.domain.Constants.SWIMMING;
import static com.tecxis.resume.domain.Interest.INTEREST_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class JpaInterestDaoTest {

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
		Interest hobby = Utils.insertAnInterest(HOBBY, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(1, hobby.getId());
		
		Interest running = Utils.insertAnInterest(RUNNING, entityManager);
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(2, running.getId());
		
		Interest swimming = Utils.insertAnInterest(SWIMMING, entityManager);
		assertEquals(3, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(3, swimming.getId());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedInterest() {
		Interest hobbyIn = Utils.insertAnInterest(HOBBY, entityManager);
		List<Interest> hobbyOutList = interestRepo.getInterestLikeDesc("%bike%");
		assertEquals(1, hobbyOutList.size());
		Interest hobbyOut = hobbyOutList.get(0);
		assertEquals(hobbyIn, hobbyOut);
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetInterestLikeDesc() {
		Utils.insertAnInterest(RUNNING, entityManager);		
		Utils.insertAnInterest(SWIMMING, entityManager);
		assertEquals(4, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		List <Interest> hobbyList = interestRepo.getInterestLikeDesc(HOBBY);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());
		hobbyList = interestRepo.getInterestLikeDesc(RUNNING);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(RUNNING, hobbyList.get(0).getDesc());
		hobbyList = interestRepo.getInterestLikeDesc(SWIMMING);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(SWIMMING, hobbyList.get(0).getDesc());
		hobbyList = interestRepo.getInterestLikeDesc("%bike%");
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetInterestByDesc() {
		Utils.insertAnInterest(RUNNING, entityManager);		
		Utils.insertAnInterest(SWIMMING, entityManager);
		assertEquals(4, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobby);		
		assertEquals(HOBBY, hobby.getDesc());
		hobby = interestRepo.getInterestByDesc(RUNNING);
		assertNotNull(hobby);		
		assertEquals(RUNNING, hobby.getDesc());
		hobby = interestRepo.getInterestByDesc(SWIMMING);
		assertNotNull(hobby);		
		assertEquals(SWIMMING, hobby.getDesc());
		
	}
	
	
	@Test
	@Sql(scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"})
	public void testDeleteInterest() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Interest tempInterest = Utils.insertAnInterest(HOBBY, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		interestRepo.delete(tempInterest);
		assertEquals(0, interestRepo.getInterestLikeDesc(HOBBY).size());
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		List <Interest> interests = interestRepo.findAll();
		assertEquals(2, interests.size());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Page <Interest> pageableInterest = interestRepo.findAll(PageRequest.of(1, 1));
		assertEquals(1, pageableInterest.getSize());
	}
}
