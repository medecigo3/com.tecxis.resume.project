package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CityId implements Serializable, Sequence <Long, Long> {
	private static final long serialVersionUID = 1L;


	@Column(name="CITY_ID")
	private long cityId;

	
	@Column(name="COUNTRY_ID")
	private long countryId;
		
	public CityId(long cityId, long countryId) {
		this();
		this.cityId = cityId;
		this.countryId = countryId;
	}
	
	public CityId() {
	}
	
	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}
	
	@Override
	public Long getSequentialValue() {		
		return this.getCityId();
	}

	@Override
	public  Long setSequentialValue(Long... id) {
		this.setCityId(id[0]);
		return this.getCityId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cityId ^ (cityId >>> 32));
		result = prime * result + (int) (countryId ^ (countryId >>> 32));
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
		CityId other = (CityId) obj;
		if (cityId != other.cityId)
			return false;
		if (countryId != other.countryId)
			return false;
		return true;
	}

	@Override
	public String toString() {		
		return 	CityId.class.getName()+ "@" + this.hashCode() + 
				"[cityId=" + this.getCityId() +
				", countryId=" + this.getCountryId() + "]";
				
	}
}