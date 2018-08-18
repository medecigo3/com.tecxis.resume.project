package com.tecxis.resume.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import com.tecxis.resume.Staff;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:persistence-context.xml", 
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml" })
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class StaffRepositoryTest {
	
	public static final String STAFF_TABLE = "STAFF";
	public static final String AMT_NAME = "Arturo";
	public static final String AMT_LASTNAME = "Medecigo Tress";
	public static final Date BIRTHDATE = new GregorianCalendar(1982, 10, 06).getTime();
		
			
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepo;
	


	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertAStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testFindInsertedStaff() {
		Staff staffIn = insertAStaff(AMT_NAME, AMT_LASTNAME, staffRepo, entityManager);
		Staff staffOut = staffRepo.getStaffByName(AMT_NAME);
		assertEquals(staffIn, staffOut);		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffByName() {
		Staff amt = staffRepo.getStaffByName(AMT_NAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffByName("Art%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql", "classpath:SQL/CreateResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetStaffByLastname() {
		Staff amt = staffRepo.getStaffByLastname(AMT_LASTNAME);
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
		/**Test query LiKE expression*/
		amt = staffRepo.getStaffByLastname("Medecigo%");
		assertNotNull(amt);
		assertEquals(AMT_NAME, amt.getName());
		assertEquals(AMT_LASTNAME , amt.getLastname());
	}
	

	
	public static Staff insertAStaff(String firstName, String lastName, StaffRepository staffRepo, EntityManager entityManager) {
		Staff staff = new Staff();
		staff.setName(firstName);
		staff.setLastname(lastName);
		assertEquals(0, staff.getStaffId());
		staffRepo.save(staff);
		assertNotNull(staff.getStaffId());
		entityManager.flush();
		return staff;
		
	}

}
