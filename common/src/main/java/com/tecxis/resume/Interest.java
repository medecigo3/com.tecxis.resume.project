package com.tecxis.resume;

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
	@SequenceGenerator(name="INTEREST_SEQ" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INTEREST_INTERESTID_GENERATOR")
	@Column(name="INTEREST_ID")
	private long interestId;

	@Column(name="\"DESC\"")
	private String desc;

	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name="STAFF_ID")
//	private Staff staff;

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

//	public Staff getStaff() {
//		return this.staff;
//	}

//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}

}