package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.COUNTRY_CITIES_ARE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.COUNTRY_NAME_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;

import java.util.List;
import java.util.function.Function;

import com.tecxis.resume.domain.City;
import com.tecxis.resume.domain.Country;

@FunctionalInterface
public interface CountryValidator extends Function<Country, ValidationResult> {
	
	static CountryValidator isNameValid(String countryName) {
		return country -> country.getName().equals(countryName)? SUCCESS : COUNTRY_NAME_IS_NOT_VALID;
	}

	static CountryValidator areCitiesValid(List<City> cities) {
		return country ->  cities.containsAll(country.getCities()) ? SUCCESS : COUNTRY_CITIES_ARE_NOT_VALID;
	}

}
