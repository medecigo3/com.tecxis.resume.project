package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectId implements Serializable, SequencedEntityId <Long> {
	private static final long serialVersionUID = 1L;

	@Column(name = "PROJECT_ID")
	private long projectId;
	
	@Column(name="CLIENT_ID")
	private long clientId; // corresponds to PK type of Client Example 1(b) : Simple -- EmbeddedId

	
	public ProjectId(long projectId, long clientId) {
		this();
		this.projectId = projectId;
		this.clientId = clientId;
	}

	/**Hibernate default constructor*/
	public ProjectId() {
		super();
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
	
	@Override
	public Long getSequenceValue() {
		return this.getProjectId();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (clientId ^ (clientId >>> 32));
		result = prime * result + (int) (projectId ^ (projectId >>> 32));
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
		ProjectId other = (ProjectId) obj;
		if (clientId != other.clientId)
			return false;
		if (projectId != other.projectId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "@" + this.hashCode() +
				"[projectId=" + this.getProjectId() +
				 ", clientId=" + this.getClientId() + 
				"]";
	
	}
}