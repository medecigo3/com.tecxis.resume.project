package com.tecxis.resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;


/**
 * The persistent class for the COUNTRY database table.
 * 
 */
@Entity
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="COUNTRY_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COUNTRY_SEQ")
	@Column(name="COUNTRY_ID")
	private long countryId;

	private String name;

	/**
	 * bi-directional association to City
	 * In SQL terms, City is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this Country "has" Cities
	 */
	@OneToMany(mappedBy="country")
	private List<City> cities;

	public Country() {
		this.cities = new ArrayList <> ();
	}

	public long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public City addCity(City city) {
		getCities().add(city);
		return city;
	}

	public City removeCity(City city) {
		getCities().remove(city);
		return city;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Country)) {
			return false;
		}
		Country castOther = (Country)other;
		return 
			(this.getCountryId() == castOther.getCountryId());
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getCountryId() ^ (this.getCountryId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() +
				", name=" +this.getName() + 
				"[countryId=" + this.getCountryId() +
				 "]]";
	}

}