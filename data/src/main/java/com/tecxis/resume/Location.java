package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Persistence class for LOCATION table
 * 
 * */
@Entity
@Table(name="LOCATION")
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static class LocationId implements Serializable{
	
		private static final long serialVersionUID = 1L;
		
		/**Bi-direccional many-to-one association to City*/
		@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
		@JoinColumn(name="CITY_ID", referencedColumnName="CITY_ID")
		@JoinColumn(name="COUNTRY_ID", referencedColumnName="COUNTRY_ID")		
		private City city;
		
		/**Bi-direccional many-to-one association to Project*/
		@ManyToOne(cascade = CascadeType.ALL)		
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
			return
				(this.city.getId() == castOther.getCity().getId())
				&& (this.city.getCountry().getCountryId() == castOther.getCity().getCountry().getCountryId())				
				&& (this.project.getProjectId()  == castOther.getProject().getProjectId())	
				&& (this.project.getClient().getClientId() == castOther.getProject().getClient().getClientId());
				
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.city.getId() ^ (this.city.getId() >>> 32)));
			hash = hash * prime + ((int) (this.city.getCountry().getCountryId()  ^ (this.city.getCountry().getCountryId() >>> 32)));
			hash = hash * prime + ((int) (this.project.getProjectId()   ^ (this.project.getProjectId()  >>> 32)));
			hash = hash * prime + ((int) (this.project.getClient().getClientId()   ^ (this.project.getClient().getClientId()  >>> 32)));
			
			return hash;
		}
		
		@Override
		public String toString() {
			return "["+ this.getClass().getName() +
					"[cityId=" + this.city.getId() + 
					", countryId=" + this.city.getCountry().getCountryId()  +
					", projectId=" + this.project.getProjectId()   +
					", clientId=" + this.project.getClient().getClientId()  +
					"]]";
		
		}
				
	}
	
	@EmbeddedId
	private LocationId locationId;
	
	public Location() {
		super();
	}
	
	public Location (LocationId locationId) {
		this.locationId = locationId;
	}

	public LocationId getLocationId() {
		return locationId;
	}

	public void setLocationId(LocationId locationId) {
		this.locationId = locationId;
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
		return
			(this.getLocationId().getCity().getId() == castOther.getLocationId().getCity().getId())
			&& (this.getLocationId().getCity().getCountry().getCountryId() == castOther.getLocationId().getCity().getCountry().getCountryId())				
			&& (this.getLocationId().getProject().getProjectId()  == castOther.getLocationId().getProject().getProjectId())	
			&& (this.getLocationId().getProject().getClient().getClientId() == castOther.getLocationId().getProject().getClient().getClientId());
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getLocationId().getCity().getId() ^ (this.getLocationId().getCity().getId() >>> 32)));
		hash = hash * prime + ((int) (this.getLocationId().getCity().getCountry().getCountryId()  ^ (this.getLocationId().getCity().getCountry().getCountryId() >>> 32)));
		hash = hash * prime + ((int) (this.getLocationId().getProject().getProjectId()   ^ (this.getLocationId().getProject().getProjectId()  >>> 32)));
		hash = hash * prime + ((int) (this.getLocationId().getProject().getClient().getClientId()   ^ (this.getLocationId().getProject().getClient().getClientId()  >>> 32)));
		
		return hash;
	}
	
	@Override
	public String toString() {
		return "["+ this.getClass().getName() +
				"[cityId=" + this.getLocationId().getCity().getId() + 
				", countryId=" + this.getLocationId().getCity().getCountry().getCountryId()  +
				", projectId=" + this.getLocationId().getProject().getProjectId()   +
				", clientId=" + this.getLocationId().getProject().getClient().getClientId()  +
				"]]";
	
	}
	

}
