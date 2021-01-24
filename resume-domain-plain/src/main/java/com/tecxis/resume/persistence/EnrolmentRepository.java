package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.commons.persistence.id.EnrolmentId;
import com.tecxis.resume.domain.Enrolment;

public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {

}
