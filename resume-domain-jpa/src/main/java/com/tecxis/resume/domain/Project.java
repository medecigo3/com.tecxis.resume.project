package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.id.CompositeIdentifiable;
import com.tecxis.resume.domain.id.ProjectId;
import com.tecxis.resume.domain.id.SequenceKeyGenerator;


/**
 * The persistent class for the PROJECT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "VERSION" , "NAME" }))
public class Project implements Serializable, CompositeIdentifiable <ProjectId>{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceCompositeKeyGenerator", name="PROJECT_SEQ"	 
	, parameters = {
	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_SEQ")
	private ProjectId id;
	
	/**
	 * bi-directional many-to-one association to Client.
	 * In SQL terms, Project is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this project "is controlled " by a client
	 */
	@MapsId("clientId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="CLIENT_ID")
	private Client client;
	
	@Column(name="\"DESC\"")
	private String desc;


	/**
	 * bi-directional one-to-many association to Assignment.
	 * In SQL terms, Assignment is the "owner" of this relationship with Project as it contains the relationship's foreign key
	 * In OO terms, this Project "is composed of" StaffAssignments
	 * 
	 */	
	@OneToMany( mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Assignment> assignments;

	/**
	 * bi-directional many-to-many association to City
	 */
	@ManyToMany (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
		name="LOCATION", joinColumns={
			@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID"),
			@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CITY_ID", referencedColumnName="CITY_ID"),
			@JoinColumn(name="COUNTRY_ID", referencedColumnName="COUNTRY_ID")
			}
		)
	private List<City> cities;

	@NotNull
	private String version;

	@NotNull
	private String name;
	
	/**
	 * bi-directional one-to-many association to Project
	 */
	//To cascade operations to Enrolment entity efficiently "mappedBy" isn't allowed here.  
	@ManyToMany (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //Do not cascade REMOVE to Staff, nevertheless it cascades REMOVE to ASSIGNMENT due to identifying relationship
	@JoinTable(
		name="ASSIGNMENT", joinColumns= {
			@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID"),
			@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")			
		}, inverseJoinColumns = {
			@JoinColumn(name="STAFF_ID", referencedColumnName="STAFF_ID")
		}
	)
	private List <Staff> staff;
	
	/**
	* bi-directional one-to-many association to Location.
	* In OO terms, this Project "is based" in these Locations
	*/	
	@OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval=false)//Do not cascade remove to CITY, nevertheless it cascades REMOVE to LOCATION due to identifying relationship
	private List <Location> locations;

	public Project() {
		this.cities = new ArrayList <> ();
		this.assignments = new ArrayList<>();
		this.locations = new ArrayList<>();
		this.staff = new ArrayList<>();
		this.id = new ProjectId();
	}

	public String getDesc() {
		return this.desc;
	}
	
	@Override
	public ProjectId getId() {
		return id;
	}

	@Override
	public void setId(ProjectId id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
		this.getId().setClientId(client.getId());
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Staff> getStaff() {
		return staff;
	}

	public List<Assignment> getAssignments() {
		return this.assignments;
	}

	/**
	 * 
	 * @param assignments orphans are removed when set to null.
	 */
	public void setAssignments(List<Assignment> assignments) {
		this.assignments.clear();
		if (assignments != null)
			this.assignments.addAll(assignments);
	}

	public Assignment addAssignment(Assignment assignment) {
		/**check if 'staff' and 'task' aren't in Assignments*/
		if ( this.getAssignments().contains(assignment))			
			throw new EntityExistsException("Assignment already exists in this association: " + assignment.toString());
				
		getAssignments().add(assignment);
		return assignment;
	}
	
	public boolean removeAssignment(Assignment assignment) {
		boolean ret = this.getAssignments().remove(assignment);
		assignment.setStaff(null);
		assignment.setProject(null);
		assignment.setAssignment(null);
		return ret;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		this.cities.clear();
		this.cities.addAll(cities); //TODO add null check
	}

	public boolean addCity(City city) {
		city.getProjects().add(this);
		return this.getCities().add(city);
		
	}
	
	public boolean removeCity(City city) {
		city.getProjects().remove(this);
		return this.cities.remove(city);
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public void addLocation(Location location) {
		/**Check if 'location' isn't in this project -> locations*/
		if ( this.getLocations().contains(location))
			throw new EntityExistsException("City already exists in this Project -> locations: " + location.toString());
	
		this.getLocations().add(location);
	}
	
	public boolean removeLocation(Location location) {
		return this.getLocations().remove(location);
	}
	
	public boolean removeLocation(City city) {
		return this.getLocations().remove(getLocations()
				.stream()
				.findFirst()
				.filter(location -> location.getCity().equals(city))
				.orElse(null));
	}
	
	public void setLocations(List<Location> locations) {
		this.locations.clear();
		if (locations != null)//fixes RES-17, consequently fixes RES-16 
			this.locations.addAll(locations); 
	}
	
	public List<Location> getLocations() {
		return this.locations;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
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
		return 	this.getClass().getName()+ "[" +
				this.getId().toString() +
				"]";
	}

}