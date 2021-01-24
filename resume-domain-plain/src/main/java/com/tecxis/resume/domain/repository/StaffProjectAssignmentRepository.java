package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.StaffProjectAssignment;

public interface StaffProjectAssignmentRepository extends JpaRepository<StaffProjectAssignment, StaffProjectAssignmentId> {

	
}
