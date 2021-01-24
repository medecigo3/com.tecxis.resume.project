package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	//Old
//	public List<Project> findByProjectPk_Name(String name);	
	public List<Project> findByName(String name);
	
	//Old
//	public Project findByProjectPk_NameAndProjectPk_Version(String name, String version);		
	public Project findByNameAndVersion(String name, String version);
}
