package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Service;

public interface ServiceRespository extends JpaRepository<Service, Long> {

}
