package com.tecxis.resume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.tecxis.commons.persistence.id.CustomSequenceGenerator;
import com.tecxis.resume.City.CityPK;


/**
 * The persistent class for the CITY database table.
 * 
 */
@Entity
@IdClass(CityPK.class)
public class City implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static class CityPK implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="CITY_SEQ", 
		 parameters = {
		            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
		            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
		)
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ")
		@Column(name="CITY_ID")
		private long cityId;

		@Id
		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name="COUNTRY_ID", insertable=false, updatable=false)
		private Country country;
			
		public CityPK(long cityId, Country country) {
			this();
			this.cityId = cityId;
			this.country = country;
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
			if (!(other instanceof CityPK)) {
				return false;
			}
			CityPK castOther = (CityPK)other;
			return 
				(this.cityId == castOther.cityId)
				&& (this.getCountry().getCountryId() == castOther.getCountry().getCountryId());
		}

		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + ((int) (this.cityId ^ (this.cityId >>> 32)));
			hash = hash * prime + ((int) (this.getCountry().getCountryId() ^ (this.getCountry().getCountryId() >>> 32)));
			
			return hash;
		}
		
		@Override
		public String toString() {		
			return 	"["+ City.CityPK.class.getName()+
					"[cityId=" + this.getCityId() +
					", countryId=" + this.getCountry().getCountryId()  + "]]";
					
		}
	}

	@Id
	@GenericGenerator(strategy="com.tecxis.commons.persistence.id.CustomSequenceGenerator", name="CITY_SEQ", 
	 parameters = {
	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
	)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ")
	@Column(name="CITY_ID")
	private long cityId;

	/**
	 * bi-directional association to Country
	 * In SQL terms, City is the "owner" of this relationship as it contains the relationship's foreign key
	 * In OO terms, this City "belongs" to a Country
	 */
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="COUNTRY_ID", insertable=false, updatable=false)
	private Country country;

	private String name;

	/** bi-directional many-to-many association to Project */
	@ManyToMany(mappedBy="cities", cascade = CascadeType.ALL)
	private List<Project> projects;

	public City() {
		this.projects = new ArrayList <> ();
	}

	public long getCityId() {
		return this.cityId;
	}
	
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
	public Country getCountry() {
		return this.country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof City)) {
			return false;
		}
		City castOther = (City)other;
		return 
			(this.cityId == castOther.cityId)
			&& (this.getCountry().getCountryId() == castOther.getCountry().getCountryId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cityId ^ (this.cityId >>> 32)));
		hash = hash * prime + ((int) (this.getCountry().getCountryId() ^ (this.getCountry().getCountryId() >>> 32)));
		
		return hash;
	}

	@Override
	public String toString() {
		return "[" +this.getClass().getName()+ "@" + this.hashCode() +   
				", name=" +this.getName() +
				"["+ City.CityPK.class.getName()+
				"[cityId=" + this.getCityId() +
				", countryId=" + this.getCountry().getCountryId() + "]]]";
	}

}