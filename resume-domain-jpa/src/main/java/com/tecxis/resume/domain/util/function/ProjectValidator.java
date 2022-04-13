package com.tecxis.resume.domain.util.function;


import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_CLIENT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_VERSION_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.function.Function;

import com.tecxis.resume.domain.Project;

@FunctionalInterface
public interface ProjectValidator extends Function<Project, ValidationResult> {
	
	static ProjectValidator isProjectNameValid(String projectName) {
		return project -> project.getName().equals(projectName) 
				? SUCCESS : PROJECT_NAME_IS_NOT_VALID; 
	}
	
	static ProjectValidator isProjectVersionValid( String projectVersion) {
		return project ->	project.getVersion().equals(projectVersion) 
				? SUCCESS : PROJECT_VERSION_IS_NOT_VALID; 
	}
	
	static ProjectValidator isProjectClientValid(String clientName) {
		return project ->  	project.getClient().getName().equals(clientName)
				? SUCCESS : PROJECT_CLIENT_IS_NOT_VALID; 
	}
}
