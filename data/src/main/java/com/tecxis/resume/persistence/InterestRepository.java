package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {

}
