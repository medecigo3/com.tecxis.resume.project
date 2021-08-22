package com.tecxis.resume.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.tecxis.resume.domain.id.CustomSequenceGenerator;
import com.tecxis.resume.domain.id.ProjectId;


/**
 * The persistent class for the PROJECT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "VERSION" , "NAME" }))
public class Project implements Serializable, StrongEntity <ProjectId>{
	private static final long serialVersionUID = 1L;
	public static final String PROJECT_TABLE = "PROJECT";

	@EmbeddedId
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.EmbeddedSequenceGenerator", name="PROJECT_SEQ"	 
	, parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
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
	 * bi-directional one-to-many association to StaffProjectAssignment.
	 * In SQL terms, StaffProjectAssignment is the "owner" of this relationship with Project as it contains the relationship's foreign key
	 * In OO terms, this Project "is composed of" StaffAssignments
	 * 
	 */	
	@OneToMany( mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<StaffProjectAssignment> staffProjectAssignments;

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
	@ManyToMany (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
		name="STAFF_PROJECT_ASSIGNMENT", joinColumns= {
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
	@OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval=false)
	private List <Location> locations;

	public Project() {
		this.cities = new ArrayList <> ();
		this.staffProjectAssignments = new ArrayList<>();
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

	public List<StaffProjectAssignment> getStaffProjectAssignments() {
		return this.staffProjectAssignments;
	}

	public void setStaffProjectAssignment(List<StaffProjectAssignment> staffProjectAssignment) {
		this.staffProjectAssignments = staffProjectAssignment;
	}

	public StaffProjectAssignment addStaffProjectAssignment(StaffProjectAssignment staffProjectAssignment) {
		/**check if 'staff' and 'assignment' aren't in staffProjectAgreements*/
		if ( this.getStaffProjectAssignments().contains(staffProjectAssignment))			
			throw new EntityExistsException("staffProjectAssignment already exist in this Project -> staffProjectAssignments: " + staffProjectAssignment.toString());
				
		getStaffProjectAssignments().add(staffProjectAssignment);
		return staffProjectAssignment;
	}
	
	public boolean removeStaffProjectAssignment(StaffProjectAssignment staffProjectAssignment) {
		boolean ret = this.getStaffProjectAssignments().remove(staffProjectAssignment);
		staffProjectAssignment.setStaff(null);
		staffProjectAssignment.setProject(null);
		staffProjectAssignment.setAssignment(null);
		return ret;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		if (cities != null) {
			this.cities.clear();
			for (City city : cities) {
				this.getCities().add(city);			
			}
		} else {
			this.cities.clear();
		}		
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
		Iterator <Location> locationIt = this.getLocations().iterator();
		
		while(locationIt.hasNext()) {
			Location tempLocation = locationIt.next();
			City tempCity = tempLocation.getCity();
			if (tempCity.equals(city))
				return this.getLocations().remove(tempLocation);
		}
		
		return false;
	}
	
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	
	public List<Location> getLocations() {
		return this.locations;
	}

	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Project)) {
			return false;
		}
		Project castOther = (Project)other;
		
	
		return this.getId().equals(castOther.getId());
	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public String toString() {
		return 	"[" +this.getClass().getName()+ "@" + this.hashCode() +
				"["+ ProjectId.class.getName() +
				"[id=" + this.getId() + 
				", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + "]]";
	}

}