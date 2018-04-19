package com.habuma.spitter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.habuma.spitter.domain.Spittle;

public interface SpittleRepository extends JpaRepository<Spittle, Long> {
//  List<Spittle> getRecentSpittle();
	  
//  void saveSpittle(Spittle spittle);
  
//  List<Spittle> getSpittlesForSpitter(Spitter spitter);

//  void deleteSpittle(long id);
	  
  Spittle getSpittleById(long id);
}
