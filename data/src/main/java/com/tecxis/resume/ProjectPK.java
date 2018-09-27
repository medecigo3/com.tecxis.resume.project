package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PROJECT database table.
 * 
 */
@Embeddable
public class ProjectPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PROJECT_ID")
	private long projectId;
	
	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;


	public ProjectPK() {
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