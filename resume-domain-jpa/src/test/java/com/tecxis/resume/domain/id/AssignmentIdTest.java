package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.Constants.VERSION_1;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.tecxis.resume.domain.*;
import org.junit.Test;

public class AssignmentIdTest {

	@Test
	public void testToString() {
		AssignmentId assignmentId = new AssignmentId();
		assertThat(assignmentId.toString()).matches(DEFAULT_ID_REGEX);
	}

	@Test
	public void testEquals(){//RES-61
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, BIRTHDATE);//RES-13
		Task task1 = buildTask(TASK1_ID, TASK1);
		Task task2 = buildTask(TASK2_ID, TASK2);
		Task testTask1 = buildTask(TASK3_ID, TASK3);
		Task testTask2 = buildTask(TASK4_ID, TASK4);
		Project adir = buildProject(PROJECT_ADIR_V1_ID, ADIR, VERSION_1, barclays, null, null);//RES-11
		Assignment testAssignment1 = buildAssignment(adir, amt, task1);
		Assignment testAssignment2 = buildAssignment(adir, amt, task2);
		Assignment testAssignment3 = buildAssignment(adir, amt, testTask1);
		Assignment testAssignment4 = buildAssignment(adir, amt, testTask2);

		AssignmentId testAssignmentId1 = testAssignment1.getId();
		AssignmentId testAssignmentId2 = testAssignment2.getId();
		AssignmentId testAssignmentId3 = testAssignment3.getId();
		AssignmentId testAssignmentId4 = testAssignment4.getId();

		assertFalse(testAssignmentId1.equals(testAssignmentId2));
		assertFalse(testAssignmentId2.equals(testAssignmentId1));
		assertFalse(testAssignmentId2.equals(testAssignmentId3));
		assertFalse(testAssignmentId3.equals(testAssignmentId2));
		assertFalse(testAssignmentId3.equals(testAssignmentId4));
		assertFalse(testAssignmentId4.equals(testAssignmentId3));

		Assignment testAssignment5 = buildAssignment(adir, amt, task1);
		Assignment testAssignment6 = buildAssignment(adir, amt, task1);
		AssignmentId testAssignmentId5 = testAssignment5.getId();
		AssignmentId testAssignmentId6 = testAssignment6.getId();

		assertTrue(testAssignmentId5.equals(testAssignmentId6));


	}

}
