package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

}
