package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.CityId;
import com.tecxis.resume.domain.id.CompositeIdentifiable;
import com.tecxis.resume.domain.id.SequenceKeyGenerator;


/**
 * The persistent class for the CITY database table.
 * 
 */
@Entity
public class City implements Serializable, CompositeIdentifiable <CityId>{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceCompositeKeyGenerator", name="CITY_SEQ", 
	 parameters = {
	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ")
	private CityId id;

	/**
	 * bi-directional many-to-one association to Country
	 * In SQL terms, City is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this City "belongs" to a Country
	 */
	@MapsId("countryId")
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
	@OneToMany(mappedBy="city", cascade = {CascadeType.ALL}, orphanRemoval=true)
	private List <Location> locations; 
	
	public City() {
		this.projects = new ArrayList <> ();
		this.id = new CityId();
		this.locations = new ArrayList <> ();
	}
	
	@Override
	public CityId getId() {
		return this.id;
	}
	
	@Override
	public void setId(CityId id) {
		this.id = id;
	}
	
	public Country getCountry() {
		return this.country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
		this.getId().setCountryId(country.getId());
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

	
	public void addLocation(Location location) {
		/**Check if 'location' isn't in this city -> locations*/
		if ( this.getLocations().contains(location))
			throw new EntityExistsException("Project already exists in this City -> locations: " + location.toString());
		
		this.getLocations().add(location);
	}
	
	public boolean removeLocation(Location location) {
		return this.getLocations().remove(location);
	}
	
	public boolean removeLocation(Project project) {
		Iterator <Location> locationIt = this.getLocations().iterator();
		
		while(locationIt.hasNext()) {
			Location tempLocation = locationIt.next();
			Project tempProject = tempLocation.getProject();
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public String toString() {
		return this.getClass().getName() + "[" +
				"name=" +this.getName() +
				 "[" + this.getId() + 
				 "]]";
	}

}