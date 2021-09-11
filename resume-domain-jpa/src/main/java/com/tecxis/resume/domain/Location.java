package com.tecxis.resume.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tecxis.resume.domain.id.LocationId;

/**
 * 
 * Persistence class for LOCATION table
 * 
 * */
@Entity
@Table(name=Location.LOCATION_TABLE)
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	final public static String LOCATION_TABLE = "LOCATION";	
	
	@EmbeddedId
	private LocationId id;
	
	/**Bi-direccional many-to-one association to City*/
	@MapsId("cityId")	
	@ManyToOne(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to City 
	@JoinColumn(name="CITY_ID", referencedColumnName="CITY_ID")
	@JoinColumn(name="COUNTRY_ID", referencedColumnName="COUNTRY_ID")		
	private City city;
	
	/**Bi-direccional many-to-one association to Project*/
	@MapsId("projectId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Project
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")		
	private Project project;
	
	public Location() {
		super();
		this.id = new LocationId();
	}		

	public Location(City city, Project project) {
		this();
		this.getId().setCityId(city.getId());
		this.getId().setProjectId(project.getId());
		this.setCity(city);
		this.setProject(project);
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
		this.getId().setCityId(city.getId());
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
		this.getId().setProjectId(project.getId());
	}	

	public LocationId getId() {
		return id;
	}

	public void setId(LocationId id) {
		this.id = id;
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
		return "["+ this.getClass().getName() +
				this.getId()+
				"]";
			
	}	

}
