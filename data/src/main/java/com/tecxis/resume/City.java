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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;
import com.tecxis.resume.City.CityPK;
import com.tecxis.resume.Location.LocationId;


/**
 * The persistent class for the CITY database table.
 * 
 */
@Entity
@IdClass(CityPK.class)
public class City implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;
	
	public static class CityPK implements Serializable {
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

		@Id
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="COUNTRY_ID")
		private Country country;
			
		public CityPK(long id, Country country) {
			this();
			this.id = id;
			this.country = country;
		}
		
		private CityPK() {
			super();
		}
		public long getId() {
			return this.id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public Country getCountry() {
			return this.country;
		}
		public void setCountry(Country country) {
			this.country = country;
		}

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof CityPK)) {
				return false;
			}
			CityPK castOther = (CityPK)other;
			return 
				(this.id == castOther.id)
				&& (this.getCountry().getId() == castOther.getCountry().getId());
		}

		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
			hash = hash * prime + ((int) (this.getCountry().getId() ^ (this.getCountry().getId() >>> 32)));
			
			return hash;
		}
		
		@Override
		public String toString() {		
			return 	"["+ City.CityPK.class.getName()+
					"[id=" + this.getId() +
					", countryId=" + this.getCountry().getId()  + "]]";
					
		}
	}

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
		this.locations.clear();
		for(Location location : locations) {
			this.locations.add(location);
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
		return 
			(this.id == castOther.id)
			&& (this.getCountry().getId() == castOther.getCountry().getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
		hash = hash * prime + ((int) (this.getCountry().getId() ^ (this.getCountry().getId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() +   
				", name=" +this.getName() +
				"["+ City.CityPK.class.getName()+
				"[id=" + this.getId() +
				", countryId=" + this.getCountry().getId() + "]]]";
	}

}