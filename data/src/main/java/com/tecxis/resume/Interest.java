package com.tecxis.resume;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;


/**
 * The persistent class for the INTEREST database table.
 * 
 */
@Entity
public class Interest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="INTEREST_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTEREST_SEQ")
	@Column(name="INTEREST_ID")
	private long interestId;

	@Column(name="\"DESC\"")
	private String desc;
	
	/**
	 * bi-directional association to Staff
	 * In SQL terms, Interest is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this Interest "is assigned" to a Staff
	 */
	@ManyToOne
	@JoinColumn(name="STAFF_ID")
	private Staff staff;

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
	
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Interest)) {
			return false;
		}
		Interest castOther = (Interest)other;
		return 
			(this.getInterestId() == castOther.getInterestId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.getInterestId() ^ (this.getInterestId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() + 
				"[interestId=" + this.getInterestId() + "]]";
	}
	
}