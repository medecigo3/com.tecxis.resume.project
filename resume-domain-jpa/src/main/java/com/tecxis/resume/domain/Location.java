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
@Table(name=SchemaConstants.LOCATION_TABLE)
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {			
		return  this.getClass().getName() + "[" +
				this.getId() +
				 "]";
			
	}	

}
