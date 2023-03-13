package com.tecxis.resume.domain.util.function;


import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_ASSIGNMENTS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_ASSIGNMENTS_ARE_NULL;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_CLIENT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_LOCATIONS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_LOCATIONS_ARE_NULL;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_SIZE_ASSIGNMENTS_ARE_DIFFERENT;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_SIZE_LOCATIONS_ARE_DIFFERENT;
import static com.tecxis.resume.domain.util.function.ValidationResult.PROJECT_VERSION_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.List;
import java.util.function.Function;

import javax.validation.constraints.Null;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Location;
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
	
	static ProjectValidator isProjectClientValid(Client client) {
		return project ->  	project.getClient().equals(client)
				? SUCCESS : PROJECT_CLIENT_IS_NOT_VALID; 
	}

	static ProjectValidator areProjectAssignmentsValid(@Null List<Assignment> assignments) {//RESB-6 accepts null param
		return project -> {
			if (assignments == null || project.getAssignments() == null)
				return PROJECT_ASSIGNMENTS_ARE_NULL;
			else 
			if (assignments.size() !=  project.getAssignments().size())
				return PROJECT_SIZE_ASSIGNMENTS_ARE_DIFFERENT;
			else 	
				return assignments.containsAll(project.getAssignments()) ? SUCCESS : PROJECT_ASSIGNMENTS_ARE_NOT_VALID;		
		};
	}

	static ProjectValidator areProjectLocationsValid(@Null List<Location> locations) {//RESB-6 fix accepts null param
		return project -> {
			if (locations == null || project.getLocations() == null)
				return PROJECT_LOCATIONS_ARE_NULL;
			else 
			if (locations.size() != project.getLocations().size())  
				return PROJECT_SIZE_LOCATIONS_ARE_DIFFERENT;
			else 
				return  locations.containsAll(project.getLocations()) ? SUCCESS : PROJECT_LOCATIONS_ARE_NOT_VALID;			
		};
	}
}
