package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.repository.ClientRepository;

public class JpaClientDao implements ClientDao{
	
	@Autowired
	private ClientRepository clientRepo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Client client) {
		em.merge(client);
		
	}

	@Override
	public void add(Client client) {
		em.persist(client);
		
	}

	@Override
	public void delete(Client client) {
		em.remove(client);
		
	}

	@Override
	public List<Client> findAll() {
		return clientRepo.findAll();
	}

	@Override
	public Page<Client> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public Client getClientByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
