package com.tecxis.resume.domain.util.function;

import java.util.List;
import java.util.function.Function;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Task;

import javax.validation.constraints.NotNull;

import static com.tecxis.resume.domain.util.function.ValidationResult.*;

@FunctionalInterface
public interface TaskValidator extends Function<Task, ValidationResult> {

	static TaskValidator isTaskDescValid(String taskDesc) { //RES-57
		return task -> task.getDesc().equals(taskDesc) ? SUCCESS : TASK_DESC_IS_NOT_VALID;
	}
	
	static TaskValidator isTaskPriorityValid(@NotNull final Integer priority) {//RES-57
		TaskValidator ret = task -> {
			if (task.getPriority() == null && priority == null)
				return SUCCESS;

			if (task.getPriority() != null && priority == null || task.getPriority() == null && priority != null)
				return TASK_PRIORITY_IS_NOT_VALID;

			return task.getPriority().equals(priority) ? SUCCESS : TASK_PRIORITY_IS_NOT_VALID;
		};
		return ret;
	}

	static TaskValidator areAssignmentsValid(List<Assignment> assignments){//RES-57
		TaskValidator ret = task -> {
			if (task.getAssignments() == null || assignments == null)
				return TASK_ASSIGNMENTS_ARE_NULL;

			if (task.getAssignments().size()  !=  assignments.size())
				return TASK_SIZE_ASSIGNMENTS_ARE_DIFFERENT;
			else
				return task.getAssignments().containsAll(assignments) ? SUCCESS : TASK_ASSIGNMENTS_ARE_NOT_VALID;
		};
		return ret;
	}

}
