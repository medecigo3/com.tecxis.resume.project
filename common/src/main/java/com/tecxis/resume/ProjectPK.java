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

	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;

	private String name;

	@Column(name="\"VERSION\"")
	private String version;

	public ProjectPK() {
	}
	public long getClientId() {
		return this.clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public long getStaffId() {
		return this.staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProjectPK)) {
			return false;
		}
		ProjectPK castOther = (ProjectPK)other;
		return 
			(this.clientId == castOther.clientId)
			&& (this.staffId == castOther.staffId)
			&& this.name.equals(castOther.name)
			&& this.version.equals(castOther.version);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.clientId ^ (this.clientId >>> 32)));
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		hash = hash * prime + this.name.hashCode();
		hash = hash * prime + this.version.hashCode();
		
		return hash;
	}
}