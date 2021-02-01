package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tecxis.resume.domain.Interest;

public class JpaInterestDao implements InterestDao {

	@Override
	public void save(Interest k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Interest k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Interest k) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Interest> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Interest> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
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
