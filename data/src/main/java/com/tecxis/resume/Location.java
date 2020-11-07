package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tecxis.commons.persistence.id.LocationId;

/**
 * 
 * Persistence class for LOCATION table
 * 
 * */
@Entity
@Table(name="LOCATION")
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
		
		if(this.getLocationId().getCity() != null && castOther.getLocationId().getCity() != null) {
			if (this.getLocationId().getProject() != null && castOther.getLocationId().getProject() != null) {
				
				 return 	this.getLocationId().getCity().equals(castOther.getLocationId().getCity()) &&
						 	this.getLocationId().getProject().equals(castOther.getLocationId().getProject());
				 
			} else return false;
		} else return false;
			
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		if (this.getLocationId().getCity() != null) {
			hash = hash * prime + this.getLocationId().getCity().hashCode();
		}
		
		if (this.getLocationId().getProject() != null) {
			hash = hash * prime + this.getLocationId().getProject().hashCode();
		}		
		return hash;
	}
	
	@Override
	public String toString() {
		LocationId locationId = this.getLocationId();
		City city = null;
		Country country = null;
		Project project = null;
		Client client = null;
		
		if (locationId != null) {
			city = this.getLocationId().getCity();
			
			if (city != null)
				country = this.getLocationId().getCity().getCountry();
			
			project = this.getLocationId().getProject();
			
			if (project != null)
				client = this.getLocationId().getProject().getClient();
			
			return "["+ this.getClass().getName() +
					"[cityId=" + (city != null ? city.getId() : "null") + 
					", countryId=" + (country != null ? country.getId() : "null")   +
					", projectId=" + (project != null ? project.getId() : "null")   +
					", clientId=" + (client != null ? client.getId() : "null")  +
					"]]";
			
		} else {
			return "["+ this.getClass().getName() +
					"[cityId= null" + 
					", countryId= null"  +
					", projectId= null"   +
					", clientId= null"  +
					"]]";
		}

	
	}
	

}
