package com.tecxis.resume.persistence;

import java.util.List;

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
	
	@Override
	public void save(Interest interest) {
		interestRepo.save(interest);

	}

	@Override
	public void add(Interest interest) {
		interestRepo.save(interest);

	}

	@Override
	public void delete(Interest interest) {
		interestRepo.delete(interest);

	}

	@Override
	public List<Interest> findAll() {
		return interestRepo.findAll();
	}

	@Override
	public Page<Interest> findAll(Pageable pageable) {
		return interestRepo.findAll(pageable);
	}

	@Override
	public List<Interest> getInterestLikeDesc(String desc) {
		return interestRepo.getInterestLikeDesc(desc);
	}

	@Override
	public Interest getInterestByDesc(String desc) {
		return interestRepo.getInterestByDesc(desc);
	}

}
