package com.tecxis.resume.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

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

import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class InterestTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private InterestRepository interestRepo;
	
	@Autowired
	private StaffRepository staffRepo;

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Interest interest = insertAnInterest(Constants.HOBBY, entityManager);
		assertThat(interest.getId(), Matchers.greaterThan((long)0));
	}
	
	@Test
	public void testSetId() {
		Interest interest = new Interest();
		assertEquals(0, interest.getId());
		interest.setId(1);
		assertEquals(1, interest.getId());		
	}
	
	@Test
	public void testSetDesc() {
		Interest interest = new Interest();
		assertNull(interest.getDesc());
		interest.setDesc(Constants.INTEREST_DESC);
		assertEquals(Constants.INTEREST_DESC, interest.getDesc());	
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetDesc() {
		Interest hobby = interestRepo.getInterestByDesc(Constants.HOBBY);
		assertNotNull(hobby);
		assertEquals(Constants.HOBBY, hobby.getDesc());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaff() {
		/**Find Interest to test*/
		Interest hobby = interestRepo.getInterestByDesc(Constants.HOBBY);
		assertNotNull(hobby);
		hobby.getStaff();
		
		/**Find Staff target*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME, amt.getLastName());
		
		/**Test Interest*/
		assertEquals(amt, hobby.getStaff());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaff() {		
		/**Find Interest to test*/
		Interest hobby = interestRepo.getInterestByDesc(Constants.HOBBY);		
		assertNotNull(hobby);
		long hobbyId = hobby.getId();
		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME, amt.getLastName());
		
		/**Validate Interest -> Staff*/
		assertEquals(amt, hobby.getStaff());
		
		/**Find new Staff to set*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		assertEquals(Constants.JOHN_NAME, john.getFirstName());
		assertEquals(Constants.JOHN_LASTNAME, john.getLastName());
		
		/**Validate Staff -> Interest*/
		assertEquals(1, john.getInterests().size());
		assertEquals(Constants.JOHN_INTEREST, john.getInterests().get(0).getDesc());
		
		/** Set new Staff*/		
		hobby.setStaff(john);
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.INTEREST_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));		
		entityManager.merge(hobby);
		entityManager.merge(john);
		entityManager.flush();		
		entityManager.clear();
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.INTEREST_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, Constants.STAFF_TABLE));
		
		/**Validate Interest -> Staff association*/
		/**Find Interest*/
		hobby = interestRepo.getInterestByDesc(Constants.HOBBY);
		assertNotNull(hobby);
		assertEquals(hobbyId, hobby.getId());
		/**Find Staff*/		
		john = staffRepo.getStaffByFirstNameAndLastName(Constants.JOHN_NAME, Constants.JOHN_LASTNAME);
		assertEquals(Constants.JOHN_NAME, john.getFirstName());
		assertEquals(Constants.JOHN_LASTNAME, john.getLastName());
		/**Validate Interest -> Staff*/
		assertEquals(john, hobby.getStaff());
		/** Validate Staff -> Interest */
		assertEquals(2, john.getInterests().size());
		assertThat(john.getInterests(), Matchers.hasItem(hobby));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaff() {
		/**Find Interest to test*/
		Interest hobby = interestRepo.getInterestByDesc(Constants.HOBBY);
		assertNotNull(hobby);
		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME, amt.getLastName());
		
		/**Validate Staff-> Interest*/
		assertEquals(1, amt.getInterests().size());
		
		/**Validate Interest -> Staff*/
		assertEquals(amt, hobby.getStaff());
		
		/**Remove Staff*/
		hobby.setStaff(null);
		entityManager.merge(hobby);
 		entityManager.flush();
 		entityManager.clear();
 		
 		
 		/**Validate Interest -> Staff*/
 		hobby = interestRepo.getInterestByDesc(Constants.HOBBY);
		assertNotNull(hobby);		
		assertNull(hobby.getStaff());
		
 		/**Validate Staff -> Interest*/
		amt = staffRepo.getStaffByFirstNameAndLastName(Constants.AMT_NAME, Constants.AMT_LASTNAME);
		assertEquals(Constants.AMT_NAME, amt.getFirstName());
		assertEquals(Constants.AMT_LASTNAME, amt.getLastName());
		assertEquals(0, amt.getInterests().size());
		
	}
	
	@Test
	public void testToString() {
		Interest interest = new Interest();
		interest.toString();
	}
	
	public static Interest insertAnInterest(String desc, EntityManager entityManager) {
		Interest interest = new Interest();
		interest.setDesc(desc);
		assertEquals(0, interest.getId());
		entityManager.persist(interest);
		entityManager.flush();
		assertThat(interest.getId(), Matchers.greaterThan((long)0));
		return interest;
	}
}
