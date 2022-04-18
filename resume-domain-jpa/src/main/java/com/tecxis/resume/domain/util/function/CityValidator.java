package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.CITY_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.CITY_LOCATIONS_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.List;
import java.util.function.Function;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Location;

@FunctionalInterface
public interface CityValidator extends Function<City, ValidationResult>  {
	
	static CityValidator isNameValid(String cityName) {
		return city -> city.getName().equals(cityName)? SUCCESS : CITY_IS_NOT_VALID;
	}
	
	static CityValidator isCountryValid(String countryName) {
		return city -> city.getCountry().getName().equals(countryName)? SUCCESS : CITY_IS_NOT_VALID;
	}

	static CityValidator areLocationsValid(List <Location> locations) {
		return city -> locations.containsAll(city.getLocations()) ? SUCCESS : CITY_LOCATIONS_ARE_NOT_VALID;
	}
}
