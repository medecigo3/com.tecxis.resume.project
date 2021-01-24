package com.tecxis.resume.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.domain.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

	@Query("select a from Assignment a where a.desc LIKE %?1")
	public List <Assignment> getAssignmentLikeDesc(String name);
	
	public Assignment getAssignmentByDesc(String name);
}