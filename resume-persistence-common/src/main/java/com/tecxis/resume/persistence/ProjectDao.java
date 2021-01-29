package com.tecxis.resume.persistence;

import java.util.List;

import com.tecxis.resume.domain.Project;

public interface ProjectDao extends Dao<Project> {

	public List <Project> findByName(String name);

	public Project findByNameAndVersion(String name, String version);

}
