package com.tecxis.resume;

import static com.tecxis.resume.persistence.InterestRepositoryTest.HOBBY;
import static com.tecxis.resume.persistence.InterestRepositoryTest.INTEREST_TABLE;
import static com.tecxis.resume.persistence.InterestRepositoryTest.JOHN_INTEREST;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.AMT_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_LASTNAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.JOHN_NAME;
import static com.tecxis.resume.persistence.StaffRepositoryTest.STAFF_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;
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

import com.tecxis.resume.persistence.InterestRepository;
import com.tecxis.resume.persistence.StaffRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
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
	public void testGetInterestId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDesc() {
		fail("Not yet implemented");
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaff() {
		/**Find Interest to test*/
		List <Interest> hobbyList = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());		
		Interest hobby = hobbyList.get(0);
		hobby.getStaff();
		
		/**Find Staff target*/
		Staff amt = staffRepo.getStaffByNameAndLastname(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME, amt.getLastname());
		
		/**Test Interest*/
		assertEquals(amt, hobby.getStaff());
		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSetStaff() {		
		/**Find Interest to test*/
		List <Interest> hobbyList = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());		
		Interest hobby = hobbyList.get(0);
		long hobbyId = hobby.getId();
		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffByNameAndLastname(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME, amt.getLastname());
		
		/**Validate Interest -> Staff*/
		assertEquals(amt, hobby.getStaff());
		
		/**Find new Staff to set*/
		Staff john = staffRepo.getStaffByNameAndLastname(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(JOHN_NAME, john.getName());
		assertEquals(JOHN_LASTNAME, john.getLastname());
		
		/**Validate Staff -> Interest*/
		assertEquals(1, john.getInterests().size());
		assertEquals(JOHN_INTEREST, john.getInterests().get(0).getDesc());
		
		/** Set new Staff*/		
		hobby.setStaff(john);
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));		
		entityManager.merge(hobby);
		entityManager.merge(john);
		entityManager.flush();		
		entityManager.clear();
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		
		/**Validate Interest -> Staff association*/
		/**Find Interest*/
		hobbyList = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());		
		hobby = hobbyList.get(0);
		assertEquals(hobbyId, hobby.getId());
		/**Find Staff*/		
		john = staffRepo.getStaffByNameAndLastname(JOHN_NAME, JOHN_LASTNAME);
		assertEquals(JOHN_NAME, john.getName());
		assertEquals(JOHN_LASTNAME, john.getLastname());
		/**Validate Interest -> Staff*/
		assertEquals(john, hobby.getStaff());
		/** Validate Staff -> Interest */
		assertEquals(2, john.getInterests().size());
		assertThat(john.getInterests(), Matchers.hasItem(hobby));		
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaff() {
		/**Find Interest to test*/
		List <Interest> hobbyList = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());		
		Interest hobby = hobbyList.get(0);
		
		
		/**Find Staff*/
		Staff amt = staffRepo.getStaffByNameAndLastname(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME, amt.getLastname());
		
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
 		hobbyList = interestRepo.getInterestByDesc(HOBBY);
		assertNotNull(hobbyList);
		assertEquals(1, hobbyList.size());
		assertEquals(HOBBY, hobbyList.get(0).getDesc());		
		hobby = hobbyList.get(0);
		assertNull(hobby.getStaff());
		
 		/**Validate Staff -> Interest*/
		amt = staffRepo.getStaffByNameAndLastname(AMT_NAME, AMT_LASTNAME);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME, amt.getLastname());
		assertEquals(0, amt.getInterests().size());
		
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
