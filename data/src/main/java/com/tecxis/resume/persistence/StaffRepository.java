package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.Project;
import com.tecxis.resume.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	@Query("select s from Staff s where s.name LIKE %?1")
	public Staff getStaffLikeName(String name);

	@Query("select s from Staff s where s.lastname LIKE %?1")
	public Staff getStaffLikeLastname(String lastname);

	@Query("select p from Staff s JOIN s.projects p WHERE s = ?1  GROUP BY p.name, p.id ORDER BY p.name")
	public List<Project> getStaffProjects(Staff staff);

}
