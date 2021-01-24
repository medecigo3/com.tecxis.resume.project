package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.StaffProjectAssignmentId;
import com.tecxis.resume.domain.StaffProjectAssignment;

public interface StaffProjectAssignmentRepository extends JpaRepository<StaffProjectAssignment, StaffProjectAssignmentId> {

	
}
