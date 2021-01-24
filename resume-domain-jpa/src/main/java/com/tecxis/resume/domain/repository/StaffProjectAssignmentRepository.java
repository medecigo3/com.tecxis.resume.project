package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.StaffProjectAssignment;
import com.tecxis.resume.domain.id.StaffProjectAssignmentId;

public interface StaffProjectAssignmentRepository extends JpaRepository<StaffProjectAssignment, StaffProjectAssignmentId> {

	
}
