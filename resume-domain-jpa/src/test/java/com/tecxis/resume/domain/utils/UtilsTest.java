package com.tecxis.resume.domain.utils;

import static com.tecxis.resume.domain.Course.COURSE_TABLE;
import static com.tecxis.resume.domain.Country.COUNTRY_TABLE;
import static com.tecxis.resume.domain.City.CITY_TABLE;
import static com.tecxis.resume.domain.Client.CLIENT_TABLE;
import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Interest.INTEREST_TABLE;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static com.tecxis.resume.domain.Service.SERVICE_TABLE;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static com.tecxis.resume.domain.StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.Location.LOCATION_TABLE;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

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

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Constants;
import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.ContractServiceAgreement;
import com.tecxis.resume.domain.Country;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffSkill;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class UtilsTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment() {
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Utils.insertAssignment(Constants.ASSIGNMENT12, entityManager);		
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
				
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACity() {
		/**Prepare test*/
		Country uk = Utils.insertACountry(Constants.UNITED_KINGDOM, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CITY_TABLE));
		Utils.insertACity(Constants.LONDON, uk, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CITY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAClient() {
		assertEquals(0, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
		Utils.insertAClient(Constants.SAGEMCOM, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, CLIENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContractServiceAgreement() {
		/**Prepare test*/
		Service muleEsbCons = Utils.insertAService(Constants.MULE_ESB_CONSULTANT, entityManager);
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);
		Contract accentureBarclaysContract = Utils.insertAContract(barclays, Constants.CONTRACT1_NAME, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));
		Utils.insertAContractServiceAgreement(accentureBarclaysContract, muleEsbCons, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, ContractServiceAgreement.CONTRACT_SERVICE_AGREEMENT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertAClient(Constants.AXELTIS, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		Utils.insertAContract(axeltis, Constants.CONTRACT9_NAME, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACountry() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
		Utils.insertACountry(Constants.UNITED_KINGDOM, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COUNTRY_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACourse() {
		assertEquals(0, countRowsInTable(jdbcTemplate, COURSE_TABLE));
		Utils.insertACourse(Constants.BW_6_COURSE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, COURSE_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract() {
		/**Prepare test*/
		Supplier alterna = Utils.insertASupplier(Constants.ALTERNA,  entityManager);			
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
		Utils.insertEmploymentContract(alterna, amt, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAnInterest() {
		assertEquals(0, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		Utils.insertAnInterest(Constants.HOBBY, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertLocation() {
		/**Prepare test*/
		Country france = Utils.insertACountry(Constants.FRANCE, entityManager);
		City paris = Utils.insertACity(Constants.PARIS, france, entityManager);		
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, LOCATION_TABLE));
		Utils.insertLocation(paris, adirProject, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, LOCATION_TABLE));		

	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAProject() {
		/**Prepare test*/
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);		
		
		
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Utils.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAService() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
		Utils.insertAService(Constants.MULE_ESB_CONSULTANT, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SERVICE_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASkill() {
		assertEquals(0, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		Utils.insertASkill(Constants.TIBCO, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SKILL_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffProjectAssignment() {
		/**Prepare test*/
		Client sagemcom = Utils.insertAClient(Constants.SAGEMCOM, entityManager);		
		Project ted = Utils.insertAProject(Constants.TED, Constants.VERSION_1, sagemcom, entityManager);
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		Assignment assignment12 = Utils.insertAssignment(Constants.ASSIGNMENT12, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		Utils.insertAStaffProjectAssignment(ted, amt, assignment12, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffSkill() {
		/**Prepare test*/
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		Skill tibco = Utils.insertASkill(Constants.TIBCO, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		Utils.insertAStaffSkill(amt, tibco, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaff() {
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplier() {		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
		Utils.insertASupplier(Constants.ALPHATRESS, entityManager);	
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplyContract() {
		/**Prepare test*/
		Client axeltis = Utils.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = Utils.insertAContract(axeltis, Constants.CONTRACT1_NAME, entityManager);
		Supplier alterna = Utils.insertASupplier(Constants.ALTERNA,  entityManager);		
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		
		assertEquals(0, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		Utils.insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
	}

}
