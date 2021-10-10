package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.repository.ClientRepository;

@Repository("clientDao")
public class JpaClientDao implements ClientDao{
	
	@Autowired
	private ClientRepository clientRepo;

	@Override
	public void save(Client client) {
		clientRepo.save(client);
		
	}

	@Override
	public void add(Client client) {
		clientRepo.save(client);
		
	}

	@Override
	public void delete(Client client) {
		clientRepo.save(client);
		
	}

	@Override
	public List<Client> findAll() {
		return clientRepo.findAll();
	}

	@Override
	public Page<Client> findAll(Pageable pageable) {
		return clientRepo.findAll(pageable);
	}

	@Override
	public Client getClientByName(String name) {
		return clientRepo.getClientByName(name);
	}

}
