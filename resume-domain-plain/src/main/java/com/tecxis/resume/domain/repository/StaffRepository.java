package com.tecxis.resume.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query("select s from Staff s where s.firstName LIKE %?1")
	public Staff getStaffLikeFirstName(String firstName);

	@Query("select s from Staff s where s.lastName LIKE %?1")
	public Staff getStaffLikeLastName(String lastName);
	
	public Staff getStaffByFirstNameAndLastName(String firstName, String lastName);

	@Query("select p from Staff s JOIN s.projects p WHERE s = ?1  GROUP BY p.name, p.id ORDER BY p.name")
	public List<Project> getStaffProjects(Staff staff);

}
