package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static com.tecxis.resume.domain.util.function.ValidationResult.TASK_IS_NOT_VALID;

import java.util.function.Function;

import com.tecxis.resume.domain.Task;

@FunctionalInterface
public interface TaskValidator extends Function<Task, ValidationResult> {

	static TaskValidator isTaskValid(String taskDesc) {
		return task -> task.getDesc().equals(taskDesc) ? SUCCESS : TASK_IS_NOT_VALID;
	}
	
	

}
