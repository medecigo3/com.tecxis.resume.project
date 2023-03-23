package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Location;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.function.Function;

import static com.tecxis.resume.domain.util.function.ValidationResult.*;

@FunctionalInterface
public interface CityValidator extends Function<City, ValidationResult>  {
	
	static CityValidator isNameValid(String cityName) {
		return city -> city.getName().equals(cityName)? SUCCESS : CITY_IS_NOT_VALID;
	}
	
	static CityValidator isCountryValid(String countryName) {
		return city -> city.getCountry().getName().equals(countryName)? SUCCESS : CITY_IS_NOT_VALID;
	}

	static CityValidator areLocationsValid(@Null List <Location> locations) {//RESB-18 fix
		return city -> {
			if(locations == null || city.getLocations() == null)
				return CITY_LOCATIONS_ARE_NULL;
			else
			if (locations.size() != city.getLocations().size())
				return CITY_SIZE_LOCATIONS_ARE_DIFFERENT;
			else
				return locations.containsAll(city.getLocations()) ? SUCCESS : CITY_LOCATIONS_ARE_NOT_VALID;
		};
	}
}
