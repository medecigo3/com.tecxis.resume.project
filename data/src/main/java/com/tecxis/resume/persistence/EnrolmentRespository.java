package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Enrolment;

public interface EnrolmentRespository extends JpaRepository<Enrolment, Long> {

}
