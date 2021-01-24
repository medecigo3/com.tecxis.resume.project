package com.tecxis.resume.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.domain.Enrolment;
import com.tecxis.resume.domain.id.EnrolmentId;

public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {

}
