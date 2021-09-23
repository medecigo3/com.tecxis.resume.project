package com.tecxis.resume.domain;

import static com.tecxis.resume.domain.Assignment.ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT1;
import static com.tecxis.resume.domain.Constants.ASSIGNMENT14;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.FORTIS;
import static com.tecxis.resume.domain.Constants.PARCOURS;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.Contract.CONTRACT_TABLE;
import static com.tecxis.resume.domain.EmploymentContract.EMPLOYMENT_CONTRACT_TABLE;
import static com.tecxis.resume.domain.Enrolment.ENROLMENT_TABLE;
import static com.tecxis.resume.domain.Interest.INTEREST_TABLE;
import static com.tecxis.resume.domain.Project.PROJECT_TABLE;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ENTITY_WITH_COMPOSITE_ID_REGEX;
import static com.tecxis.resume.domain.Skill.SKILL_TABLE;
import static com.tecxis.resume.domain.Staff.STAFF_TABLE;
import static com.tecxis.resume.domain.StaffProjectAssignment.STAFF_PROJECT_ASSIGNMENT_TABLE;
import static com.tecxis.resume.domain.Supplier.SUPPLIER_TABLE;
import static com.tecxis.resume.domain.SupplyContract.SUPPLY_CONTRACT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.tecxis.resume.domain.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffProjectAssignmentRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:test-context.xml" })
@Commit
@Transactional(transactionManager = "txManager", isolation = Isolation.READ_UNCOMMITTED)
@SqlConfig(dataSource="dataSource")
public class StaffProjectAssignmentTest {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private StaffProjectAssignmentRepository staffProjectAssignmentRepo;
		
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
		

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testInsertStaffProjectAssignment() {
		/**Prepare project*/
		assertEquals(0, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		Client barclays = Utils.insertAClient(BARCLAYS, entityManager);		
		Project adir = Utils.insertAProject(ADIR, VERSION_1, barclays, entityManager);
		assertEquals(1, adir.getId().getProjectId());
		assertEquals(1, countRowsInTable(jdbcTemplate, PROJECT_TABLE));
		
		/**Prepare staff*/
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		Staff amt = Utils.insertAStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, entityManager);
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		assertEquals(1L, amt.getId().longValue());
		
		/**Prepare assignment*/
		assertEquals(0, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));		
		Assignment assignment1 = Utils.insertAssignment(ASSIGNMENT1, entityManager);
		assertEquals(1L, assignment1.getId().longValue());
		assertEquals(1, countRowsInTable(jdbcTemplate, ASSIGNMENT_TABLE));
		
		/**Prepare staff assignments*/	
		assertEquals(0, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment amtStaffProjectAssignment = Utils.insertAStaffProjectAssignment(adir, amt, assignment1, entityManager);		
		List <StaffProjectAssignment> amtStaffProjectAssignments = new ArrayList <> ();		
		amtStaffProjectAssignments.add(amtStaffProjectAssignment);				
		entityManager.merge(adir);
		entityManager.flush();
		
		/**Validate staff assignments*/
		assertEquals(1, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testRemoveStaffProjectAssignment() {
		Project  parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		Assignment assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);		
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(parcours, amt, assignment14);	
		assertEquals(62, amt.getStaffProjectAssignments().size());		
		assertEquals(6, parcours.getStaffProjectAssignments().size());
		assertEquals(1, assignment14.getStaffProjectAssignments().size());
		
		/**Detach entities*/
		entityManager.clear();

		/**Validate staff -> assignments*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		StaffProjectAssignment staffProjectAssignment1 = staffProjectAssignmentRepo.findById(id).get();
		assertNotNull(staffProjectAssignment1);
		
		/**Remove staff -> assignment*/
		/**Tests initial state parent table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));		
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE)); 	
	
		/**StaffProjectAssignment has to be removed as it is the owner of the ternary relationship between Staff <-> Project <-> Assignment */		
		entityManager.remove(staffProjectAssignment1);
		entityManager.flush();
		entityManager.clear();
		
		/**Tests post state parent table*/
		assertEquals(2, countRowsInTable(jdbcTemplate, STAFF_TABLE));
		/**Tests initial state children tables*/
		assertEquals(2, countRowsInTable(jdbcTemplate, INTEREST_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, StaffSkill.STAFF_SKILL_TABLE));
		assertEquals(1, countRowsInTable(jdbcTemplate, ENROLMENT_TABLE));
		assertEquals(6, countRowsInTable(jdbcTemplate, EMPLOYMENT_CONTRACT_TABLE));		
		assertEquals(14, countRowsInTable(jdbcTemplate, SUPPLY_CONTRACT_TABLE));
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));		
		/**Test other parents for control*/ 
		assertEquals(7, countRowsInTable(jdbcTemplate, SKILL_TABLE));
		assertEquals(5, countRowsInTable(jdbcTemplate, SUPPLIER_TABLE));			
		assertEquals(13, countRowsInTable(jdbcTemplate, CONTRACT_TABLE));
		
		/**Validate staff -> assignments*/		
		assertNull(entityManager.find(StaffProjectAssignment.class, id));
		parcours = projectRepo.findByNameAndVersion(PARCOURS, VERSION_1);
		amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		assignment14 = assignmentRepo.getAssignmentByDesc(ASSIGNMENT14);	
		assertEquals(61, amt.getStaffProjectAssignments().size());		
		assertEquals(5, parcours.getStaffProjectAssignments().size());
		assertEquals(0, assignment14.getStaffProjectAssignments().size());
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testCascadedDeletion() {
		/**Retrieve staff*/
		Staff amt = staffRepo.getStaffLikeFirstName(AMT_NAME);
		
		/**Retrieve from project*/
		Project fortis = projectRepo.findByNameAndVersion(FORTIS, VERSION_1);
		assertNotNull(fortis);
		assertEquals(FORTIS, fortis.getName());
		assertEquals(VERSION_1, fortis.getVersion());
		
		/**Here persist operation will be cascaded from fortis (parent) to StaffAssignments (child) 
		 * When we delete the staff assignment instance referenced by fortis which is also loaded in the persistence context 
		 * the staff association will not be removed from DB
		 * Comment out these two lines above to prove otherwise*/		
		List <StaffProjectAssignment>  fortisStaffAssignments = fortis.getStaffProjectAssignments();
		assertEquals(3, fortisStaffAssignments.size());

		StaffProjectAssignmentId id2 = new StaffProjectAssignmentId(fortis, amt, entityManager.find(Assignment.class, 6L));	
		StaffProjectAssignment staffAssignment2 =	entityManager.find(StaffProjectAssignment.class, id2);
		StaffProjectAssignment staffAssignment3 = staffProjectAssignmentRepo.findById(id2).get();
		assertTrue(staffAssignment2.equals(staffAssignment3));
		assertNotNull(staffAssignment3);
			
		/**The the removed staff assignment is referenced by fortis hence the deletion is unscheduled --> see in hibernate trace level logs*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		entityManager.remove(staffAssignment2);
		/**Entity deletion is un-scheduled when entity isn't detached from persistence context*/
		entityManager.flush();		
		/**Test entity was not removed*/
		assertEquals(63, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
		assertNotNull(entityManager.find(StaffProjectAssignment.class, id2));
		
		/**Detach entities from persistent context*/
		entityManager.clear();
		staffAssignment3 = staffProjectAssignmentRepo.findById(id2).get();
		assertNotNull(staffAssignment3);
		entityManager.remove(staffAssignment3);
		entityManager.flush();
		assertNull(entityManager.find(StaffProjectAssignment.class, id2));
		/**Test entity was removed*/
		assertEquals(62, countRowsInTable(jdbcTemplate, STAFF_PROJECT_ASSIGNMENT_TABLE));
				

	}
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Test
	public void testToString() {
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment();
		LOG.debug(staffProjectAssignment.toString());
		assertThat(staffProjectAssignment.toString()).matches(DEFAULT_ENTITY_WITH_NESTED_ID_REGEX);
		
	}

}
