package com.tecxis.resume.domain.util.function;


import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.function.Function;

import com.tecxis.resume.domain.Project;

@FunctionalInterface
public interface ProjectValidator extends Function<Project, ValidationResult> {

	static ProjectValidator isProjectValid(String projectName, String projectVersion, String clientName) {
		return project -> project.getName().equals(projectName) && 
				project.getClient().getName().equals(clientName) &&
				project.getVersion().equals(projectVersion) 
				? SUCCESS : PROJECT_IS_NOT_VALID; 
	}
}
