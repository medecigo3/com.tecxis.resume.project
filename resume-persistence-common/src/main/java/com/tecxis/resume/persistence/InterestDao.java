package com.tecxis.resume.persistence;

import java.util.List;

import com.tecxis.resume.domain.Interest;

public interface InterestDao extends Dao<Interest> {
	
	public List<Interest> getInterestLikeDesc(String desc);
	
	public Interest getInterestByDesc(String desc);

}
