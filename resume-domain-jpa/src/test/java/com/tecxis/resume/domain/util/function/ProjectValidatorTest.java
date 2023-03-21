package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.*;
import com.tecxis.resume.domain.id.CityId;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.*;

public class ProjectValidatorTest {
	
	private static final String TEST_STRING = "123ABC";
	
	private Project adir;
	private Client barclays;
	private Client sagemcom = buildClient(SAGEMCOM, CLIENT_SAGEMCOM_ID);
	private Assignment assignment1; 
	private Assignment assignment2;
	private Location manchesterLocation;	
	private City paris = buildCity(new CityId(PARIS_ID, FRANCE_ID), PARIS);
	private Location parisLocation;
	
	@Before
	public void buildProtoProject() {
		barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);				
		Staff amt = buildStaff(AMT_NAME, AMT_LASTNAME, BIRTHDATE);
		Task task1 = buildTask(TASK1);
		Task task2 = buildTask(TASK2);
		adir = buildProject(ADIR, VERSION_1, barclays, null, null);
		assignment1 = buildAssignment(adir, amt, task1);
		assignment2 = buildAssignment(adir, amt, task2);
		adir.setAssignments(List.of(assignment1, assignment2));	
		City manchester = buildCity(new CityId(MANCHESTER_ID, UNITED_KINGDOM_ID), MANCHESTER);
		manchesterLocation = buildLocation(manchester, adir);		
		adir.setLocations(List.of(manchesterLocation));
		parisLocation = buildLocation(paris, adir);
		
	}

	@Test
	public void testIsProjectNameValid() {
		assertEquals(SUCCESS, ProjectValidator.isProjectNameValid(ADIR).apply(adir));
		assertEquals(PROJECT_NAME_IS_NOT_VALID, ProjectValidator.isProjectNameValid(TEST_STRING).apply(adir));

	}

	@Test
	public void testIsProjectVersionValid() {
		assertEquals(SUCCESS, ProjectValidator.isProjectVersionValid(VERSION_1).apply(adir));
		assertEquals(PROJECT_VERSION_IS_NOT_VALID, ProjectValidator.isProjectVersionValid(TEST_STRING).apply(adir));
	}

	@Test
	public void testIsProjectClientValidString() {
		assertEquals(SUCCESS, ProjectValidator.isProjectClientValid(BARCLAYS).apply(adir));
		assertEquals(PROJECT_CLIENT_IS_NOT_VALID, ProjectValidator.isProjectClientValid(TEST_STRING).apply(adir));

	}

	@Test
	public void testIsProjectClientValidClient() {
		assertEquals(SUCCESS, ProjectValidator.isProjectClientValid(barclays).apply(adir));
		assertNotEquals(barclays, sagemcom);
		assertEquals(PROJECT_CLIENT_IS_NOT_VALID, ProjectValidator.isProjectClientValid(sagemcom).apply(adir));
	}

	@Test
	public void testAreProjectAssignmentsValid() {
		//Tests Project -> Assignments assoc. are equal
		assertEquals(SUCCESS, ProjectValidator.areProjectAssignmentsValid(List.of(assignment1, assignment2)).apply(adir));
		
		//Tests Project -> Assignments assoc. is null 
		assertThat(adir.getAssignments(), Matchers.not(Matchers.empty()));
		assertEquals(PROJECT_ASSIGNMENTS_ARE_NULL, ProjectValidator.areProjectAssignmentsValid(null).apply(adir));
		
		//Tests Project -> Assignments assoc. is null
		adir.setAssignments(List.of());
		assertNotNull(adir.getAssignments());		
		assertEquals(PROJECT_SIZE_ASSIGNMENTS_ARE_DIFFERENT, ProjectValidator.areProjectAssignmentsValid(List.of(assignment1, assignment2)).apply(adir));
				
		//Tests  Project -> Assignments assoc. not equals size
		adir.setAssignments(null);
		assertThat(adir.getAssignments(), Matchers.empty());
		assertEquals(PROJECT_SIZE_ASSIGNMENTS_ARE_DIFFERENT, ProjectValidator.areProjectAssignmentsValid(List.of(assignment1)).apply(adir));				
		
	}

	@Test
	public void testAreProjectLocationsValid() { //TODO continue testing RESB-17 here
		//Test Project -> Locations assoc. are equal
		assertEquals(SUCCESS, ProjectValidator.areProjectLocationsValid(List.of(manchesterLocation)).apply(adir));

		//Tests Project -> Locations assoc. not not valid
		assertEquals(PROJECT_LOCATIONS_ARE_NOT_VALID, ProjectValidator.areProjectLocationsValid(List.of(parisLocation)).apply(adir));

		//Tests Project -> Locations assoc. is null	
		assertThat(adir.getLocations(), Matchers.not(Matchers.empty()));
		assertEquals(PROJECT_LOCATIONS_ARE_NULL, ProjectValidator.areProjectLocationsValid(null).apply(adir));

		//Tests  Project -> Locations assoc. not equals size
		adir.setLocations(List.of());
		assertNotNull(adir.getLocations());
		assertEquals(PROJECT_SIZE_LOCATIONS_ARE_DIFFERENT, ProjectValidator.areProjectLocationsValid(List.of(manchesterLocation, parisLocation)).apply(adir));
		
		//Tests Project -> Locations assoc. not equals in size
		adir.setLocations(null);
		assertThat(adir.getLocations(), Matchers.empty());
		assertEquals(PROJECT_SIZE_LOCATIONS_ARE_DIFFERENT, ProjectValidator.areProjectLocationsValid(List.of(manchesterLocation)).apply(adir));

	}

}
