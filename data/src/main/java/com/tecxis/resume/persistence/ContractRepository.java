package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
