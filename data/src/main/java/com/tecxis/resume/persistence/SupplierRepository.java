package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecxis.resume.ver2.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
