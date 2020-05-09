package com.tecxis.resume.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("select c from Client c where c.name LIKE %?1")
	public Client getClientByName(String name);

}
