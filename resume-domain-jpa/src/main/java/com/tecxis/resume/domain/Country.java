package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.CustomSequenceGenerator;


/**
 * The persistent class for the COUNTRY database table.
 * 
 */
@Entity
public class Country implements Serializable, StrongEntity  {
	private static final String UNSUPPORTED_COUNTRY_CITY_OPERATION = "Country -> City association managed by association owner City.";
	public static final String COUNTRY_TABLE = "COUNTRY";

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CustomSequenceGenerator", name="COUNTRY_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COUNTRY_SEQ")
	@Column(name="COUNTRY_ID")
	private long id;

	@NotNull
	private String name;

	/**
	 * bi-directional one-to-many association to City
	 * In SQL terms, City is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this Country "has" Cities
	 */
	@OneToMany(mappedBy="country", fetch=FetchType.LAZY)
	private List<City> cities;

	public Country() {
		this.cities = new ArrayList <> ();
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public void setId(long id) {
		this.id = id;
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
	
	public void setCities(List <City> cities) {
		throw new UnsupportedOperationException(UNSUPPORTED_COUNTRY_CITY_OPERATION);
	}

	public City addCity(City city) {
		throw new UnsupportedOperationException(UNSUPPORTED_COUNTRY_CITY_OPERATION);
	}
	
	public void removeCity(City city) {
		throw new UnsupportedOperationException(UNSUPPORTED_COUNTRY_CITY_OPERATION);
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
			(this.getId() == castOther.getId());
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() +
				", name=" +this.getName() + 
				"[id=" + this.getId() +
				 "]]";
	}

}