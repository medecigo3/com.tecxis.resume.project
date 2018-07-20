package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
