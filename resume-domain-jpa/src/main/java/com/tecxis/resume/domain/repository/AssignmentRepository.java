package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Assignment;
import com.tecxis.resume.domain.id.AssignmentId;

public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentId> {

	
}
