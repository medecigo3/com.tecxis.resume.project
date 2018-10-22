package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the CITY database table.
 * 
 */
@Entity
@IdClass(CityPK.class)
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CITY_CITYID_GENERATOR", sequenceName="CITY_SEQ", allocationSize=1, initialValue=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_CITYID_GENERATOR")
	@Column(name="CITY_ID")
	private long cityId;

	@Id
	@Column(name="COUNTRY_ID", insertable=false, updatable=false)
	private long countryId;

	private String name;

	//bi-directional many-to-one association to Country
//	@ManyToOne
//	@JoinColumn(name="COUNTRY_ID")
//	private Country country;

//	bi-directional many-to-many association to Project
	@ManyToMany(mappedBy="cities", cascade = CascadeType.ALL)
	private List<Project> projects;

	public City() {
		this.projects = new ArrayList <> ();
	}

	public long getCityId() {
		return this.cityId;
	}
	
	public void setCityId(long cityId) {
		this.cityId = cityId;
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
		for (Project proj : projects) {
			proj.addCity(this);
		}			
		this.projects = projects;
	}	
	
	public boolean addProject(Project project){
		if (project.addCity(this) == true)
			return this.getProjects().add(project);
		return false;
		
		
	}
	
	public boolean removeProject(Project project) {
		if (project.removeCity(this) == true)
			return this.getProjects().remove(project);
		else 
			return false;
		
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