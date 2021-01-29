package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.Client;

public interface ClientDao extends Dao<Client> {

	public Client getClientByName(String name);

}
