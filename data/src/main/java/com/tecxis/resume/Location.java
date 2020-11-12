package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.LocationId;

/**
 * 
 * Persistence class for LOCATION table
 * 
 * */
@Entity
@Table(name="LOCATION")
@IdClass(LocationId.class)
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**Bi-direccional many-to-one association to City*/
	@Id
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="CITY_ID", referencedColumnName="CITY_ID")
	@JoinColumn(name="COUNTRY_ID", referencedColumnName="COUNTRY_ID")		
	private City city;
	
	/**Bi-direccional many-to-one association to Project*/
	@Id
	@ManyToOne(cascade = CascadeType.ALL)		
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Project project;
	
	public Location() {
		super();
	}
		
	public Location(City city, Project project) {
		super();
		this.city = city;
		this.project = project;
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
		if (!(other instanceof Location)) {
			return false;
		}
		Location castOther = (Location)other;
		
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
			hash = hash * prime + this.getProject().hashCode();
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
				client = this.getProject().getClient();
			
			return "["+ this.getClass().getName() +
					"[cityId=" + (city != null ? city.getId() : "null") + 
					", countryId=" + (country != null ? country.getId() : "null")   +
					", projectId=" + (project != null ? project.getId() : "null")   +
					", clientId=" + (client != null ? client.getId() : "null")  +
					"]]";
			
		}	

}
