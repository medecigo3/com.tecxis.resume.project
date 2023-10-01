package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.Constants.TASK12_ID;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.Utils.isTaskValid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tecxis.resume.domain.*;
import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED) //this test suite is @Transactional but flushes changes manually (see the blog: https://www.marcobehler.com/2014/06/25/should-my-tests-be-transactional)
@SqlConfig(dataSource="dataSourceHelper")
public class JpaTaskDaoTest {
	
	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private TaskDao taskDao;
	@Autowired
	TaskRepository taskRepo;
	@Autowired
	AssignmentRepository assignmentRepo;
	@Autowired
	ProjectRepository projectRepo;
	@Autowired
	StaffRepository staffRepo;

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"},
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testAdd() {
		assertEquals(0, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
		Task task = new Task();
		task.setDesc(TASK12);		
		taskDao.add(task);
		entityManager.flush();
		assertEquals(1, countRowsInTable(jdbcTemplateProxy, SchemaConstants.ASSIGNMENT_TABLE));
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllPageable() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTaskLikeDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTaskByDesc() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test_OneToMany_Update_Assignments_And_RemoveOrphansWithOrm() {//RES-7
		Task task12 = taskRepo.findById(TASK12_ID).get();
		//Fetch task12 assignments
		Assignment task12Assignment = assignmentRepo.findById(new AssignmentId(new ProjectId( PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID), STAFF_AMT_ID, TASK12_ID)).get();
		//TODO RES-70 Impl. AssignmentValidator here
		//Test task is valid
		isTaskValid(task12, TASK12, null, List.of(task12Assignment));

		//Build new Assignment to set
		Project morningstartV1Project = projectRepo.findByNameAndVersion(MORNINGSTAR, VERSION_1);
		Staff amt = staffRepo.findById(STAFF_AMT_ID).get();
		Assignment morningStartv1ProjectAmtTask12Assignment = buildAssignment(morningstartV1Project, amt, task12);

		update_Task12_With_Assignments_InJpa( taskRepo -> {
					//0 orphan(s) removed, add 1 new assignment.
					task12.setAssignments(List.of(morningStartv1ProjectAmtTask12Assignment));
					taskRepo.save(task12);
					taskRepo.flush();
				},
				taskRepo, jdbcTemplateProxy);
		entityManager.clear();
		//Validate new Task
		Task newTask12 = taskRepo.findById(TASK12_ID).get();
		Assignment newTask12Assignment = assignmentRepo.findById(new AssignmentId(new ProjectId( PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID), STAFF_AMT_ID, TASK12_ID)).get();
		isTaskValid(newTask12, TASK12, null, List.of(newTask12Assignment));
	}
	@Test
	public void test_OneToMany_Update_Assignments_And_RemoveOrphansWithOrm_NullSet() {//RES-7
		Task task12 = taskRepo.findById(TASK12_ID).get();
		//Fetch task12 assignments
		Assignment task12Assignment = assignmentRepo.findById(new AssignmentId(new ProjectId( PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID), STAFF_AMT_ID, TASK12_ID)).get();
		//TODO RES-70 Impl. AssignmentValidator here
		//Test task is valid
		isTaskValid(task12, TASK12, null, List.of(task12Assignment));

		update_Task12_With_NullAssignments_InJpa( taskRepo -> {
					//0 orphan(s) removed
					task12.setAssignments(null);
					taskRepo.save(task12);
					taskRepo.flush();
				},
				taskRepo, jdbcTemplateProxy);

		entityManager.clear();
		//Validate new Task
		Task newTask12 = taskRepo.findById(TASK12_ID).get();
		Assignment newTask12Assignment = assignmentRepo.findById(new AssignmentId(new ProjectId( PROJECT_TED_V1_ID, CLIENT_SAGEMCOM_ID), STAFF_AMT_ID, TASK12_ID)).get();
		isTaskValid(newTask12, TASK12, null, List.of(newTask12Assignment));
	}
}
