package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ASSIGNMENT database table.
 * 
 */
@Embeddable
public class AssignmentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ASSIGNMENT_ID")
	private long assignmentId;

	@Column(name="CLIENT_ID", insertable=false, updatable=false)
	private long clientId;

	@Column(name="STAFF_ID", insertable=false, updatable=false)
	private long staffId;

	@Column(insertable=false, updatable=false)
	private String name;

	@Column(name="\"VERSION\"", insertable=false, updatable=false)
	private String version;

	public AssignmentPK() {
	}
	public long getAssignmentId() {
		return this.assignmentId;
	}
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
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
		if (!(other instanceof AssignmentPK)) {
			return false;
		}
		AssignmentPK castOther = (AssignmentPK)other;
		return 
			(this.assignmentId == castOther.assignmentId)
			&& (this.clientId == castOther.clientId)
			&& (this.staffId == castOther.staffId)
			&& this.name.equals(castOther.name)
			&& this.version.equals(castOther.version);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.assignmentId ^ (this.assignmentId >>> 32)));
		hash = hash * prime + ((int) (this.clientId ^ (this.clientId >>> 32)));
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		hash = hash * prime + this.name.hashCode();
		hash = hash * prime + this.version.hashCode();
		
		return hash;
	}
}