package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tecxis.resume.Project.ProjectPK;


/**
 * The persistent class for the PROJECT database table.
 * 
 */
@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames= { "VERSION" , "NAME" }))
@IdClass(ProjectPK.class)
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class ProjectPK implements Serializable {
		private static final long serialVersionUID = 1L;

		private long projectId;
		
		private long clientId;

		
		public ProjectPK(long projectId, long clientId) {
			this();
			this.projectId = projectId;
			this.clientId = clientId;
		}

		/**Hibernate default constructor*/
		private ProjectPK() {
			super();
		}
		
			
		public long getProjectId() {
			return projectId;
		}

		public void setProjectId(long projectId) {
			this.projectId = projectId;
		}
		public long getClientId() {
			return this.clientId;
		}
		public void setClientId(long clientId) {
			this.clientId = clientId;
		}
		
		@Override
		public String toString() {
			return reflectionToString(this);
		}

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof ProjectPK)) {
				return false;
			}
			ProjectPK castOther = (ProjectPK)other;
			return 
				(this.clientId == castOther.clientId) &&
				(this.projectId == castOther.projectId);

		}

		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.projectId ^ (this.projectId >>> 32)));
			hash = hash * prime + ((int) (this.clientId ^ (this.clientId >>> 32)));
			
			return hash;
		}
	}
	
	@Id
	@Column(name="PROJECT_ID")	
	@SequenceGenerator(name="PROJECT_PROJECTID_GENERATOR", sequenceName="PROJECT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_PROJECTID_GENERATOR")
	private long projectId;
	
	@Id
	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;
	
	@Column(name="\"DESC\"")
	private String desc;


	/**
	 * bi-directional one-to-many association to StaffProjectAssignment.
	 * In SQL terms, StaffProjectAssignment is the "owner" of this relationship with Project as it contains the relationship's foreign key
	 * In OO terms, this Project "is composed of" StaffAssignments
	 * 
	 */	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="PROJECT_ID", referencedColumnName="PROJECT_ID")
	@JoinColumn(name="CLIENT_ID", referencedColumnName="CLIENT_ID")
	private List<StaffProjectAssignment> staffProjectAssignments;

	/**
	 * bi-directional many-to-many association to City
	 */
	@ManyToMany (cascade = CascadeType.ALL)
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

	private String version;

	private String name;

	public Project() {
		this.cities = new ArrayList <> ();
		this.staffProjectAssignments = new ArrayList<>();
	}

	public String getDesc() {
		return this.desc;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public StaffProjectAssignment removeStaffProjectAssignment(StaffProjectAssignment staffProjectAssignment) {
		getStaffProjectAssignments().remove(staffProjectAssignment);
		return staffProjectAssignment;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		for (City city : cities) {
			city.getProjects().add(this);
		}
		this.cities = cities;
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
	
	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}