package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.HOBBY;
import static com.tecxis.resume.domain.Constants.INTEREST_DESC;
import static com.tecxis.resume.domain.Constants.JOHN_INTEREST;
import static com.tecxis.resume.domain.Constants.JOHN_LASTNAME;
import static com.tecxis.resume.domain.Constants.JOHN_NAME;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.repository.InterestRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSource")
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
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetId() {
		Interest interest = Utils.insertInterest(HOBBY, entityManager);
		assertThat(interest.getId(), Matchers.greaterThan((long)0));
	}
	
	@Test
	public void testSetId() {
		Interest interest = new Interest();
		assertEquals(0L, interest.getId().longValue());
		interest.setId(1L);
		assertEquals(1L, interest.getId().longValue());		
	}
	
	@Test
	public void testSetDesc() {
		Interest interest = new Interest();
		assertNull(interest.getDesc());
		interest.setDesc(INTEREST_DESC);
		assertEquals(INTEREST_DESC, interest.getDesc());	
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetDesc() {
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobby);
		assertEquals(HOBBY, hobby.getDesc());		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaff() {
		/**Find Interest to test*/
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobby);
		hobby.getStaff();
		
		/**Find Staff target*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());
		
		/**Test Interest*/
		assertEquals(amt, hobby.getStaff());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaff() {		
		/**Find Interest to test*/
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);		
		assertNotNull(hobby);
		long hobbyId = hobby.getId();
		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());
		
		/**Validate Interest -> Staff*/
		assertEquals(amt, hobby.getStaff());
		
		/**Find new Staff to set*/
		Staff john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(JOHN_NAME, john.getFirstName());
		assertEquals(JOHN_LASTNAME, john.getLastName());
		
		/**Validate Staff -> Interest*/
		assertEquals(1, john.getInterests().size());
		assertEquals(JOHN_INTEREST, john.getInterests().get(0).getDesc());
		
		/** Set new Staff*/		
		hobby.setStaff(john);
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));		
		entityManager.merge(hobby);
		entityManager.merge(john);
		entityManager.flush();		
		entityManager.clear();
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.INTEREST_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, SchemaConstants.STAFF_TABLE));
		
		/**Validate Interest -> Staff association*/
		/**Find Interest*/
		hobby = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobby);
		assertEquals(hobbyId, hobby.getId().longValue());
		/**Find Staff*/		
		john = staffRepo.getStaffByFirstNameAndLastName(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(JOHN_NAME, john.getFirstName());
		assertEquals(JOHN_LASTNAME, john.getLastName());
		/**Validate Interest -> Staff*/
		assertEquals(john, hobby.getStaff());
		/** Validate Staff -> Interest */
		assertEquals(2, john.getInterests().size());
		assertThat(john.getInterests(), Matchers.hasItem(hobby));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaff() {
		/**Find Interest to test*/
		Interest hobby = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobby);
		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());
		
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
 		hobby = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobby);		
		assertNull(hobby.getStaff());
		
 		/**Validate Staff -> Interest*/
		amt = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getFirstName());
		assertEquals(AMT_LASTNAME, amt.getLastName());
		assertEquals(0, amt.getInterests().size());
		
	}
	
	@Test
	public void testToString() {
		Interest interest = new Interest();		
		assertThat(interest.toString()).matches(DEFAULT_ENTITY_WITH_SIMPLE_ID_REGEX);
	}
}
