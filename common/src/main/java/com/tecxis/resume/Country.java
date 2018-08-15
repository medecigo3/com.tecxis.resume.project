package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the COUNTRY database table.
 * 
 */
@Entity
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COUNTRY_SEQ" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COUNTRY_COUNTRYID_GENERATOR")
	@Column(name="COUNTRY_ID")
	private long id;

	private String name;

//	bi-directional many-to-one association to City
//	DB terms: City is the owner of the relatoinship as it contains a foreign key to this Country 
//	@OneToMany(mappedBy="country")
	/**
	 * uni-directional association to City
	 * OO terms: this Country "has a" City
	 */
	@OneToMany
	private List<City> cities;

	public Country() {
	}

	public long getId() {
		return this.id;
	}

	public void setiId(long id) {
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

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public City addCity(City city) {
		getCities().add(city);
//		city.setCountry(this);

		return city;
	}

	public City removeCity(City city) {
		getCities().remove(city);
//		city.setCountry(null);

		return city;
	}
	
	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}