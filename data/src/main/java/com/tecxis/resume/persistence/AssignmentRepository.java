package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
