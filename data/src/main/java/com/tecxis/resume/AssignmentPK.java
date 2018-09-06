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

	@Column(name="NAME", insertable=false, updatable=false)
	private String projectName;

	@Column(name="VERSION", insertable=false, updatable=false)
	private String projectVersion;

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
	public String getProjectName() {
		return this.projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectVersion() {
		return this.projectVersion;
	}
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
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
			&& this.projectName.equals(castOther.projectName)
			&& this.projectVersion.equals(castOther.projectVersion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.assignmentId ^ (this.assignmentId >>> 32)));
		hash = hash * prime + ((int) (this.clientId ^ (this.clientId >>> 32)));
		hash = hash * prime + ((int) (this.staffId ^ (this.staffId >>> 32)));
		hash = hash * prime + this.projectName.hashCode();
		hash = hash * prime + this.projectVersion.hashCode();
		
		return hash;
	}
}