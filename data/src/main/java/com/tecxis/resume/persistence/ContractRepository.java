package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
