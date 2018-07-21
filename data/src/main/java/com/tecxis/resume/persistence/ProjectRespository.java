package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Project;

public interface ProjectRespository extends JpaRepository<Project, Long> {

}
