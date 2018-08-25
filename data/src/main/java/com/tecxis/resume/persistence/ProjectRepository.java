package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	public List<Project> findByProjectPk_Name(String name);
	
	public Project findByProjectPk_NameAndProjectPk_Version(String name, String version);
}
