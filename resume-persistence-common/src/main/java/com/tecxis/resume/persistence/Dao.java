package com.tecxis.resume.persistence;

import java.util.List;

public interface Dao <K> {

	public void save(K k);

	public void add(K k);

	public void delete(K k);

	public List <K> findAll();

}
