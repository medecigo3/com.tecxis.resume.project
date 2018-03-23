package com.habuma.spitter.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

public interface SpitterDao extends JpaRepository<Spitter, Long>{
//  void addSpitter(Spitter spitter);

//  void saveSpitter(Spitter spitter);

  Spitter getSpitterById(long id);

//  List<Spittle> getRecentSpittle();
  
//  void saveSpittle(Spittle spittle);
  
//  List<Spittle> getSpittlesForSpitter(Spitter spitter);

  Spitter getSpitterByUsername(String username);
  
//  void deleteSpittle(long id);
  
  Spittle getSpittleById(long id);
  
//  List<Spitter> findAllSpitters();
}
