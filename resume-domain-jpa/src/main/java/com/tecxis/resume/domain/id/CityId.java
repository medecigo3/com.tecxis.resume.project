package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.resume.domain.Country;

public class CityId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CustomSequenceGenerator", name="CITY_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ")
	@Column(name="CITY_ID")
	private long id;

	@Id
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="COUNTRY_ID")
	private Country country;
		
	public CityId(long id, Country country) {
		this();
		this.id = id;
		this.country = country;
	}
	
	public CityId() {
		super();
	}
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Country getCountry() {
		return this.country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CityId)) {
			return false;
		}
		CityId castOther = (CityId)other;
		
		if (this.getCountry() != null && castOther.getCountry() != null)
			return 	this.getId() == castOther.getId()  && 
					this.getCountry().equals(castOther.getCountry());
		else
			return 	this.getId() == castOther.getId();
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		hash = hash * prime + ((int) (this.getId() ^ (this.getId() >>> 32)));
		
		if (this.getCountry() != null)
			hash = hash * prime + this.getCountry().hashCode();
		
		return hash;
	}
	
	@Override
	public String toString() {		
		return 	"["+ CityId.class.getName()+
				"[id=" + this.getId() +
				", countryId=" + (this.getCountry() != null ? this.getCountry().getId() : "null" )  + "]]";
				
	}
}