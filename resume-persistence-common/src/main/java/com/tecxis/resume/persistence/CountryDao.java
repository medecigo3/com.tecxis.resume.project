package com.tecxis.resume.persistence;

import com.tecxis.resume.domain.Country;

public interface CountryDao extends Dao <Country>  {

	public Country getCountryById(int id);

	public Country getCountryByName(String name);

}
