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
	public static final String AMT_FIRSTNAME = "Arturo";
	public static final String AMT_LASTNAME = "Medecigo Tress";
	public static final Date BIRTHDATE = new GregorianCalendar(1982, 10, 06).getTime();
		
			
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StaffRepository staffRepository;
	


	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testInsertAStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		insertAStaff(AMT_FIRSTNAME, AMT_LASTNAME, staffRepository, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
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
