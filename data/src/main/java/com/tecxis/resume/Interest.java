package com.tecxis.resume;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the INTEREST database table.
 * 
 */
@Entity
public class Interest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INTEREST_INTERESTID_GENERATOR", sequenceName="INTEREST_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTEREST_INTERESTID_GENERATOR")
	@Column(name="INTEREST_ID")
	private long interestId;

	@Column(name="\"DESC\"")
	private String desc;

	public Interest() {
	}

	public long getInterestId() {
		return this.interestId;
	}

	public void setInterestId(long interestId) {
		this.interestId = interestId;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
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