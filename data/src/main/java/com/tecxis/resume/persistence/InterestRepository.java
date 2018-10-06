package com.tecxis.resume.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecxis.resume.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {
	
	@Query("select i from Interest i where i.desc LIKE %?1")
	public List<Interest> getInterestLikeDesc(String desc);

}
