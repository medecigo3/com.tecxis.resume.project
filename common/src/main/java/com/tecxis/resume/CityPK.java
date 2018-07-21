package com.tecxis.resume;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CITY database table.
 * 
 */
@Embeddable
public class CityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CITY_ID")
	private long cityId;

	@Column(name="COUNTRY_ID", insertable=false, updatable=false)
	private long countryId;

	public CityPK() {
	}
	public long getCityId() {
		return this.cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getCountryId() {
		return this.countryId;
	}
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CityPK)) {
			return false;
		}
		CityPK castOther = (CityPK)other;
		return 
			(this.cityId == castOther.cityId)
			&& (this.countryId == castOther.countryId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cityId ^ (this.cityId >>> 32)));
		hash = hash * prime + ((int) (this.countryId ^ (this.countryId >>> 32)));
		
		return hash;
	}
}