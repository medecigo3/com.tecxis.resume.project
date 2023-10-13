package com.tecxis.resume.domain.util.function;



import com.tecxis.resume.domain.*;
import com.tecxis.resume.domain.util.Utils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.*;

public class TaskValidatorTest {//RES-57
    private Task task12;
    private Task task13;
    private Assignment task12Assignment;
    private Assignment task13Assignment;

    @Before
    public void before(){
        //Build Tasks
        task12 = Utils.buildTask(TASK12_ID, TASK12, TASK12_PRIORITY);//RES-72
        task13 = Utils.buildTask(TASK13_ID, TASK13, TASK13_PRIORITY);//RES-72

        //Build Client, Staff, Project and Assignment
        Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
        Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, BIRTHDATE);
        Project adir = buildProject(PROJECT_ADIR_V1_ID, ADIR, VERSION_1, barclays, null, null);//RES-11
        task12Assignment = buildAssignment(adir, amt, task12);
        task13Assignment = buildAssignment(adir, amt, task13);
        //Build Task
        task12.setAssignments(List.of(task12Assignment));
        task13.setAssignments(List.of(task13Assignment));
    }
    @Test
    public void isTaskDescValid() {
        assertEquals(SUCCESS, TaskValidator.isTaskDescValid(TASK12).apply(task12));
        assertEquals(TASK_DESC_IS_NOT_VALID, TaskValidator.isTaskDescValid(TASK13).apply(task12));
    }

    @Test
    public void isTaskPriorityValid() {
        assertEquals(SUCCESS, TaskValidator.isTaskPriorityValid(TASK12_PRIORITY).apply(task12));
        assertEquals(TASK_PRIORITY_IS_NOT_VALID, TaskValidator.isTaskPriorityValid(TASK13_PRIORITY).apply(task12));
    }

    @Test
    public void areAssignmentsValid() {
        //Test Task -> Assignments assoc. are equal
        assertEquals(SUCCESS, TaskValidator.areAssignmentsValid(List.of(task12Assignment)).apply(task12));

        //Test Task -> Assignments assoc. not valid
        assertEquals(TASK_ASSIGNMENTS_ARE_NOT_VALID, TaskValidator.areAssignmentsValid(List.of(task13Assignment)).apply(task12));

        //Test Task -> Assignments assoc. is null
        assertEquals(TASK_ASSIGNMENTS_ARE_NULL, TaskValidator.areAssignmentsValid(null).apply(task12));

        //Test Task -> Assignments assoc.not equal in size
        task12.setAssignments(List.of());
        assertNotNull(task12.getAssignments());
        assertEquals(TASK_SIZE_ASSIGNMENTS_ARE_DIFFERENT, TaskValidator.areAssignmentsValid(List.of(task12Assignment)).apply(task12));

        //Test Task -> Assignments assoc.not equal in size
        task12.setAssignments(null);
        assertThat(task12.getAssignments(), Matchers.empty());
        assertEquals(TASK_SIZE_ASSIGNMENTS_ARE_DIFFERENT, TaskValidator.areAssignmentsValid(List.of(task12Assignment)).apply(task12));

    }
}