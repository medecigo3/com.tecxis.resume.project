package com.tecxis.resume.persistence;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Dao <K> {

	public void save(K k);

	public void add(K k);

	public void delete(K k);

	public List <K> findAll();

	public Page <K> findAll(Pageable pageable);

}
