package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;

import java.util.List;
import java.util.function.Function;

import static com.tecxis.resume.domain.util.function.ValidationResult.*;

@FunctionalInterface
public interface CountryValidator extends Function<Country, ValidationResult> {
	
	static CountryValidator isNameValid(String countryName) {
		return country -> country.getName().equals(countryName)? SUCCESS : COUNTRY_NAME_IS_NOT_VALID;
	}

	static CountryValidator areCitiesValid(List<City> cities) {//RES-20 fix
		return country -> {
			if(cities == null || country.getCities() == null)
				return COUNTRY_CITIES_ARE_NULL;
			else
			if (cities.size() != country.getCities().size())
				return COUNTRY_SIZE_CITIES_ARE_DIFFERENT;
			else
				return cities.containsAll(country.getCities()) ? SUCCESS : COUNTRY_CITIES_ARE_NOT_VALID;
		};
	}

}
