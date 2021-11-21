package com.tecxis.resume.persistence;

import static com.tecxis.resume.domain.Constants.ADIR;
import static com.tecxis.resume.domain.Constants.AMT_LASTNAME;
import static com.tecxis.resume.domain.Constants.AMT_NAME;
import static com.tecxis.resume.domain.Constants.BARCLAYS;
import static com.tecxis.resume.domain.Constants.BIRTHDATE;
import static com.tecxis.resume.domain.Constants.TASK1;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.util.Utils.insertAssignmentInJpa;
import static com.tecxis.resume.domain.util.Utils.isAssignmentValid;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
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

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;
import com.tecxis.resume.domain.Task;
import com.tecxis.resume.domain.id.AssignmentId;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.repository.AssignmentRepository;
import com.tecxis.resume.domain.repository.ClientRepository;
import com.tecxis.resume.domain.repository.ProjectRepository;
import com.tecxis.resume.domain.repository.StaffRepository;
import com.tecxis.resume.domain.repository.TaskRepository;
import com.tecxis.resume.domain.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig (locations = { 
		"classpath:spring-context/test-context.xml" })
@Transactional(transactionManager = "txManagerProxy", isolation = Isolation.READ_COMMITTED)//this test suite is @Transactional but flushes changes manually
@SqlConfig(dataSource="dataSourceHelper")
public class JpaAssignmentDaoTest {

	@PersistenceContext //Wires in EntityManagerFactoryProxy primary bean
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplateProxy;
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired 
	private StaffRepository staffRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private ClientRepository clientRepo;	
	
	@Autowired
	private AssignmentDao assignmentDao;	

	@Sql(
		scripts = {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
	    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
		)
	@Test
	public void testSave() {
		
	Assert.fail("TODO");
		
	}
	
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"}, 
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD
	)
	public void testAdd() {
		final long clientId = 1L;
		final long projectId = 1L;
		final long staffId = 1L;
		final long taskId = 1L;
		
		/**Insert Client*/
		Client barclays = Utils.insertClient(BARCLAYS, clientRepo);
		assertEquals(clientId, barclays.getId().longValue());	
		
		/**Insert project*/
		Project adir = Utils.insertProject(ADIR, VERSION_1, barclays, projectRepo);
		assertEquals(projectId, adir.getId().getProjectId());	
		
		/**Insert staff*/		
		Staff amt = Utils.insertStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE, staffRepo);		
		assertEquals(staffId, amt.getId().longValue());
		
		/**Insert Task*/				
		Task task1 = Utils.insertTask(TASK1, taskRepo);
		assertEquals(taskId, task1.getId().longValue());		
		
		/**Insert Assignment*/
		insertAssignmentInJpa(insertAssignmentFunction->{
			Assignment amtAssignment  = new Assignment(adir, amt, task1);
			assignmentDao.add(amtAssignment);
			assignmentRepo.flush();	 //manually commit transaction
		}, assignmentRepo, jdbcTemplateProxy);		
	
		/**Detach managed entities from persistence context*/
		entityManager.clear();
		
		/**Validate Assignment is inserted*/
		AssignmentId newAssignmentId = new AssignmentId();
		newAssignmentId.setProjectId(new ProjectId(projectId, clientId));
		newAssignmentId.setStaffId(staffId);
		newAssignmentId.setTaskId(taskId);
		Assignment newAssignment = assignmentRepo.findById(newAssignmentId).get();
		assertEquals(SUCCESS, isAssignmentValid(newAssignment, ADIR, VERSION_1, BARCLAYS, AMT_NAME, AMT_LASTNAME, TASK1));
		
		/**Validate Project has new Assignment*/
		List <Assignment> newProjectAssignments = projectRepo.findByNameAndVersion(ADIR, VERSION_1).getAssignments();
		assertThat(newProjectAssignments).contains(newAssignment);
		/**Validate Staff has new Assignment*/
		List <Assignment> newStaffAssignemnts = staffRepo.getStaffByFirstNameAndLastName(AMT_NAME, AMT_LASTNAME).getAssignments();
		assertThat(newStaffAssignemnts).contains(newAssignment);
		/**Validate Task has new Assignment*/
		List <Assignment> newTaskAssignments = taskRepo.getTaskByDesc(TASK1).getAssignments();
		assertThat(newTaskAssignments).contains(newAssignment);
	}
	
	
	@Test
	@Sql(scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql"})
	public void testDelete() {
		Assert.fail("TODO");
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAll(){
		Assert.fail("TODO");
	}
	
	@Test
	@Sql(
		scripts= {"classpath:SQL/H2/DropResumeSchema.sql", "classpath:SQL/H2/CreateResumeSchema.sql", "classpath:SQL/InsertResumeData.sql" },
		executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindAllPagable(){
		Assert.fail("TODO");
	}
}
