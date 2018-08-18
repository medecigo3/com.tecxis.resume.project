package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
	
	@Query("select s from Staff s where s.name LIKE %?1")
	public Staff getStaffByName(String name);
	
	@Query("select s from Staff s where s.lastname LIKE %?1")
	public Staff getStaffByLastname(String lastname);

}
