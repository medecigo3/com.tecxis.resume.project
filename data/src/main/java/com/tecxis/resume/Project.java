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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;
import com.tecxis.commons.persistence.id.LocationId;
import com.tecxis.resume.Project.ProjectPK;


/**
 * The persistent class for the PROJECT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "VERSION" , "NAME" }))
@IdClass(ProjectPK.class)
public class Project implements Serializable, StrongEntity {
	private static final long serialVersionUID = 1L;

	public static class ProjectPK implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="PROJECT_ID")	
		@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="PROJECT_SEQ", 
		 parameters = {
		            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
		            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
		)
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_SEQ")
		private long id;
		
		@Id
		@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
		@JoinColumn(name="CLIENT_ID")
		private Client client;

		
		public ProjectPK(long projectId, Client client) {
			this();
			this.id = projectId;
			this.client = client;
		}

		/**Hibernate default constructor*/
		public ProjectPK() {
			super();
		}
		
			
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
		public Client getClient() {
			return this.client;
		}
		public void setClientId(Client client) {
			this.client = client;
		}
		
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof ProjectPK)) {
				return false;
			}
			ProjectPK castOther = (ProjectPK)other;
			
			if (this.getClient() != null && castOther.getClient() != null)	
				return 	this.getId() == castOther.getId() && 
						this.getClient().equals(castOther.getClient());
			else
				return this.getId() == castOther.getId();
		}

		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));
			
			if (this.getClient() != null)
				hash = hash * prime + this.getClient().hashCode();
			
			return hash;
		}
		
		@Override
		public String toString() {
			return "["+ this.getClass().getName() +
					"[id=" + this.getId() + 
					", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + "]]";
		
		}
	}
	
	@Id
	@Column(name="PROJECT_ID")	
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="PROJECT_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_SEQ")
	private long id;
	
	/**
	 * bi-directional many-to-one association to Client.
	 * In SQL terms, Project is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this project "is controlled " by a client
	 */
	@Id
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
	@OneToMany( mappedBy = "staffProjectAssignmentId.project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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
	@OneToMany(mappedBy = "locationId.project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval=false)
	private List <Location> locations;

	public Project() {
		this.cities = new ArrayList <> ();
		this.staffProjectAssignments = new ArrayList<>();
		this.locations = new ArrayList<>();
		this.staff = new ArrayList<>();
	}

	public String getDesc() {
		return this.desc;
	}
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public StaffProjectAssignment addStaffProjectAssignment(Staff staff, Assignment assignment) {
		/**check if 'staff' and 'assignment' aren't in staffProjectAgreements*/
		if ( !Collections.disjoint(this.getStaffProjectAssignments(), staff.getStaffProjectAssignments()) )
			if ( !Collections.disjoint(this.getStaffProjectAssignments(), assignment.getStaffProjectAssignments()) )
				throw new EntityExistsException("Entities already exist in 'IS COMPOSED OF' association: [" + staff + ", " + assignment + "]");
				
		
		StaffProjectAssignment staffProjectAssignment = new StaffProjectAssignment();
		StaffProjectAssignmentId id = new StaffProjectAssignmentId(this, staff, assignment);
		staffProjectAssignment.setStaffAssignmentId(id);		
		getStaffProjectAssignments().add(staffProjectAssignment);
		return staffProjectAssignment;
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
	
	public void addLocation(City city) {
		/**Check if 'location' isn't in this project -> locations*/
		if ( !Collections.disjoint(this.getLocations(), city.getLocations()))
			throw new EntityExistsException("City already exists in this Project -> locations: " + city.getId());
		
		LocationId locationId = new LocationId(city, this);
		Location newLocation = new Location(locationId);
		this.getLocations().add(newLocation);
	}
	
	public boolean removeLocation(Location location) {
		return this.getLocations().remove(location);
	}
	
	public boolean removeLocation(City city) {
		Iterator <Location> locationIt = this.getLocations().iterator();
		
		while(locationIt.hasNext()) {
			Location tempLocation = locationIt.next();
			City tempCity = tempLocation.getLocationId().getCity();
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
		
		if (this.getClient() != null && castOther.getClient() != null)			
			return 	this.getId() == castOther.getId() && 
					this.getClient().equals(castOther.getClient());
		else
			return this.getId() == castOther.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));
		
		if (this.getClient() != null)
			hash = hash * prime + this.getClient().hashCode();
		
		return hash;
	}

	@Override
	public String toString() {
		return 	"[" +this.getClass().getName()+ "@" + this.hashCode() +
				"["+ Project.ProjectPK.class.getName() +
				"[id=" + this.getId() + 
				", clientId=" + (this.getClient() != null ? this.getClient().getId() : "null") + "]]";
	}

}