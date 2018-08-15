package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CITY database table.
 * 
 */
@Entity
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CityPK id;

	private String name;

	//bi-directional many-to-one association to Country
//	@ManyToOne
//	@JoinColumn(name="COUNTRY_ID")
//	private Country country;

//	bi-directional many-to-many association to Project
	@ManyToMany(mappedBy="cities")
	private List<Project> projects;

	public City() {
	}

	public CityPK getId() {
		return this.id;
	}

	public void setId(CityPK id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Country getCountry() {
//		return this.country;
//	}
//
//	public void setCountry(Country country) {
//		this.country = country;
//	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
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