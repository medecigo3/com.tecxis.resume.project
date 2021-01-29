package com.tecxis.resume.persistence;

import java.util.List;

import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;

public interface StaffDao extends Dao<Staff> {

	public Staff getStaffLikeFirstName(String firstName);
	
	public Staff getStaffLikeLastName(String lastName);
	
	public Staff getStaffByFirstNameAndLastName(String firstName, String lastName);
	
	public List<Project> getStaffProjects(Staff staff);

}
