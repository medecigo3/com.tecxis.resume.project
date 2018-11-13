package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.StaffAssignment;
import com.tecxis.resume.StaffAssignmentId;

public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment, StaffAssignmentId> {

	
}
