package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class LocationId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private CityId cityId; // corresponds to PK type of Client
	
	private ProjectId projectId; // corresponds to PK type of Project

	public LocationId(CityId cityId, ProjectId projectId) {
		super();
		this.cityId = cityId;
		this.projectId = projectId;
	}
	
	public LocationId() {
		super();
	}

	public CityId getCityId() {
		return cityId;
	}

	public void setCityId(CityId city) {
		this.cityId = city;
	}

	public ProjectId getProjectId() {
		return projectId;
	}

	public void setProjectId(ProjectId project) {
		this.projectId = project;
	}		
	
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
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
		LocationId other = (LocationId) obj;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  this.getClass().getName() +
				"[" + (this.getCityId() != null ? this.cityId.getCityId() : "cityId=null" )  + 
				  (this.getProjectId() != null ? this.getProjectId() : "projectId=null") +
				"]";
	
	}

}
