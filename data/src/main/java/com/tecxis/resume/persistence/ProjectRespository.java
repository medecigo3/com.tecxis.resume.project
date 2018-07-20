package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Project;

public interface ProjectRespository extends JpaRepository<Project, Long> {

}
