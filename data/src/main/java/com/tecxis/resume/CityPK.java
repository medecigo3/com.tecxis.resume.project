package com.tecxis.resume;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;

/**
 * The primary key class for the CITY database table.
 * 
 */
class CityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long cityId;

	private long countryId;
		
	public CityPK(long cityId, long countryId) {
		this();
		this.cityId = cityId;
		this.countryId = countryId;
	}
	
	private CityPK() {
		super();
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
	
	@Override
	public String toString() {		
		return reflectionToString(this);
	}
}