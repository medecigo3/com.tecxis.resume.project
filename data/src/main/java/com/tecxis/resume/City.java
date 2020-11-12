package com.tecxis.resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CityId;
import com.tecxis.commons.persistence.id.CustomSequenceGenerator;
import com.tecxis.commons.persistence.id.LocationId;


/**
 * The persistent class for the CITY database table.
 * 
 */
@Entity
@IdClass(CityId.class)
public class City implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="CITY_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ")
	@Column(name="CITY_ID")
	private long id;

	/**
	 * bi-directional many-to-one association to Country
	 * In SQL terms, City is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this City "belongs" to a Country
	 */
	@Id
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="COUNTRY_ID", insertable=false, updatable=false)
	private Country country;

	@NotNull
	private String name;

	/** bi-directional many-to-many association to Project */
	@ManyToMany(mappedBy="cities", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Project> projects;
	
	/**
	 * bi-directional one-to-many association to Location
	 * In OO terms, this City "establishes a" Location
	 */
	@OneToMany(mappedBy="locationId.city", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <Location> locations; 
	
	public City() {
		this.projects = new ArrayList <> ();
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public void setId(long id) {
		this.id = id;
	}
	
	public Country getCountry() {
		return this.country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	
	public void addLocation(Project project) {
		/**Check if 'location' isn't in this city -> locations*/
		if ( !Collections.disjoint(this.getLocations(), project.getLocations()))
			throw new EntityExistsException("Project already exists in this City -> locations: " + project.getId());
		
		LocationId locationId = new LocationId(this, project);
		Location newLocation = new Location(locationId);
		this.getLocations().add(newLocation);
	}
	
	public boolean removeLocation(Location location) {
		return this.getLocations().remove(location);
	}
	
	public boolean removeLocation(Project project) {
		Iterator <Location> locationIt = this.getLocations().iterator();
		
		while(locationIt.hasNext()) {
			Location tempLocation = locationIt.next();
			Project tempProject = tempLocation.getLocationId().getProject();
			if (tempProject.equals(project))
				return this.getLocations().remove(tempLocation);
		}
		
		return false;
	}
	
	public void setLocations(List<Location> locations) {
		if (locations != null) {
			this.locations.clear();
			for(Location location : locations) {
				this.locations.add(location);
			}		
		} else {
			this.locations.clear();
		}
	}

	public List<Location> getLocations() {
		return locations;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof City)) {
			return false;
		}
		City castOther = (City)other;
		
		if (this.getCountry() != null && castOther.getCountry()!= null)
			return 	this.getId() == castOther.getId()  && 
					this.getCountry().equals(castOther.getCountry());
		else
			return 	this.getId() == castOther.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));
		
		if (this.getCountry() != null)
			hash = hash * prime + this.getCountry().hashCode();
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() +   
				", name=" +this.getName() +
				"["+ CityId.class.getName()+
				"[id=" + this.getId() +
				", countryId=" + (this.getCountry() != null ? this.getCountry().getId() : "null") + "]]]";
	}

}