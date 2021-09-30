package com.tecxis.resume.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tecxis.resume.domain.Interest;
import com.tecxis.resume.domain.repository.InterestRepository;

@Repository("interestDao")
public class JpaInterestDao implements InterestDao {
	
	@Autowired
	private InterestRepository interestRepo;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(Interest interest) {
		em.merge(interest);

	}

	@Override
	public void add(Interest interest) {
		em.persist(interest);

	}

	@Override
	public void delete(Interest interest) {
		em.remove(interest);

	}

	@Override
	public List<Interest> findAll() {
		return interestRepo.findAll();
	}

	@Override
	public Page<Interest> findAll(Pageable pageable) {
		return findAll(pageable);
	}

	@Override
	public List<Interest> getInterestLikeDesc(String desc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interest getInterestByDesc(String desc) {
		// TODO Auto-generated method stub
		return null;
	}

}
