package com.tecxis.resume.domain.utils;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.tecxis.resume.domain.Course;
import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.Location;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Service;
import com.tecxis.resume.domain.Skill;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.StaffProjectAssignment;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml"})
@Commit
@Transactional(transactionManager = "transactionManager", isolation = Isolation.READ_UNCOMMITTED)
public class UtilsTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAssignment() {
		Assignment assignment12 = Utils.insertAssignment(Constants.ASSIGNMENT12, entityManager);		
		assertThat(assignment12.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACity() {
		Country uk = Utils.insertACountry(Constants.UNITED_KINGDOM, entityManager);
		City london = Utils.insertACity(Constants.LONDON, uk, entityManager);		
		assertThat(london.getId(), Matchers.greaterThan((long)0));		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAClient() {
		Client client = Utils.insertAClient(Constants.SAGEMCOM, entityManager);	
		assertThat(client.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContractServiceAgreement() {
		Service muleEsbCons = Utils.insertAService(Constants.MULE_ESB_CONSULTANT, entityManager);
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);
		Contract accentureBarclaysContract = Utils.insertAContract(barclays, Constants.CONTRACT1_NAME, entityManager);
		ContractServiceAgreement contractServiceAgreementIn = Utils.insertAContractServiceAgreement(accentureBarclaysContract, muleEsbCons, entityManager);		
		fail("TODO");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAContract() {
		Client axeltis = Utils.insertAClient(Constants.AXELTIS, entityManager);	
		Contract contract = Utils.insertAContract(axeltis, Constants.CONTRACT9_NAME, entityManager);
		assertThat(contract.getId(), Matchers.greaterThan((long)0));
	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACountry() {
		Country uk = Utils.insertACountry(Constants.UNITED_KINGDOM, entityManager);
		assertThat(uk.getId(), Matchers.greaterThanOrEqualTo((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertACourse() {
		Course course = Utils.insertACourse(Constants.BW_6_COURSE, entityManager);
		assertThat(course.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertEmploymentContract() {
		Supplier alterna = Utils.insertASupplier(Constants.ALTERNA,  entityManager);			
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		EmploymentContract alternaAmtEmploymentContract = Utils.insertEmploymentContract(alterna, amt, entityManager);	
		assertThat(alternaAmtEmploymentContract.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAnInterest() {
		Interest interest = Utils.insertAnInterest(Constants.HOBBY, entityManager);
		assertThat(interest.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertLocation() {
		Country france = Utils.insertACountry(Constants.FRANCE, entityManager);
		City paris = Utils.insertACity(Constants.PARIS, france, entityManager);		
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);
		Location location = Utils.insertLocation(paris, adirProject, entityManager);
		//TODO test LOCATION table has changed
		assertThat(location.getCity().getId(), Matchers.greaterThan((long)0));
		assertThat(location.getProject().getId(), Matchers.greaterThan((long)0));
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAProject() {
		Client barclays = Utils.insertAClient(Constants.BARCLAYS, entityManager);		
		Project adirProject = Utils.insertAProject(Constants.ADIR, Constants.VERSION_1, barclays, entityManager);	
		assertThat(adirProject.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAService() {
		Service service = Utils.insertAService(Constants.MULE_ESB_CONSULTANT, entityManager);	
		assertThat(service.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASkill() {
		Skill skill = Utils.insertASkill(Constants.TIBCO, entityManager);
		assertThat(skill.getId(), Matchers.greaterThan((long)0));	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffProjectAssignment() {
		Client sagemcom = Utils.insertAClient(Constants.SAGEMCOM, entityManager);		
		Project ted = Utils.insertAProject(Constants.TED, Constants.VERSION_1, sagemcom, entityManager);
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		Assignment assignment12 = Utils.insertAssignment(Constants.ASSIGNMENT12, entityManager);
		StaffProjectAssignment amtStaffProjectAssignment = Utils.insertAStaffProjectAssignment(ted, amt, assignment12, entityManager);	
		//TODO
		fail("TODO test StaffProjectAssignment table ");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaffSkill() {
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		Skill tibco = Utils.insertASkill(Constants.TIBCO, entityManager);
		Utils.insertAStaffSkill(amt, tibco, entityManager);
		//TODO
		fail("TODO test StaffSkill table ");
		
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertAStaff() {
		Staff staff = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);
		assertThat(staff.getId(), Matchers.greaterThan((long)0));	
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplier() {
		Supplier supplierIn = Utils.insertASupplier(Constants.ALPHATRESS, entityManager);	
		assertThat(supplierIn.getId(), Matchers.greaterThan((long)0));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/DropResumeSchema.sql", "classpath:SQL/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertASupplyContract() {
		Client axeltis = Utils.insertAClient(Constants.AXELTIS, entityManager);		
		Contract accentureContract = Utils.insertAContract(axeltis, Constants.CONTRACT1_NAME, entityManager);
		Supplier alterna = Utils.insertASupplier(Constants.ALTERNA,  entityManager);		
		Staff amt = Utils.insertAStaff(Constants.AMT_NAME, Constants.AMT_LASTNAME, Constants.BIRTHDATE, entityManager);	
		SupplyContract alternaAccentureContract = Utils.insertASupplyContract(alterna, accentureContract, amt, Constants.CONTRACT1_STARTDATE, Constants.CONTRACT1_ENDDATE, entityManager);
		//TODO
		fail("TODO test SupplyContract table ");
	
	}

}
