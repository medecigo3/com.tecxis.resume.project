package com.habuma.spitter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.habuma.spitter.domain.Spitter;

public interface SpitterRepository extends JpaRepository<Spitter, Long>{
//  void addSpitter(Spitter spitter);

//  void saveSpitter(Spitter spitter);

  Spitter getSpitterById(long id);

  Spitter getSpitterByUsername(String username);
  
//  List<Spitter> findAllSpitters();
}
