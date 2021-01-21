package com.tecxis.commons.persistence.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tecxis.resume.City;
import com.tecxis.resume.Client;
import com.tecxis.resume.Country;
import com.tecxis.resume.Project;

public class LocationId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CITY_ID", referencedColumnName="CITY_ID")
	@JoinColumn(name="COUNTRY_ID", referencedColumnName="COUNTRY_ID")		
	private City city;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})		
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Project project;

	public LocationId(City city, Project project) {
		super();
		this.city = city;
		this.project = project;
	}
	
	public LocationId() {
		super();
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}		
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LocationId)) {
			return false;
		}
		LocationId castOther = (LocationId)other;
		
		if(this.getCity() != null && castOther.getCity() != null) {
			if (this.getProject() != null && castOther.getProject() != null) {
				
				 return 	this.getCity().equals(castOther.getCity()) &&
						 	this.getProject().equals(castOther.getProject());
				 
			} else return false;
		} else return false;
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		if (this.getCity() != null) {
			hash = hash * prime + this.getCity().hashCode();
		}
		
		if (this.getProject() != null) {
			hash = hash * prime +  this.getProject().hashCode();
		}
		
		return hash;
	}
	
	@Override
	public String toString() {
		City city = null;			
		Country country = null;
		Project project = null;
		Client client = null;
					
		city = this.getCity();
		if (city != null)
			country = this.getCity().getCountry();
		
		project = this.getProject();
		if (project != null)
			client = project.getClient();
		
		
		return "["+ this.getClass().getName() +
				"[cityId=" + (city != null ? this.city.getId() : "null" )  + 
				", countryId= " + (country != null ? country.getId() : "null")  +
				", projectId=" + (project != null ? project.getId() : "null" )   +
				", clientId=" + (client != null ?  client.getId() : "null")  +
				"]]";
	
	}

}
