package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.*;
import com.tecxis.resume.domain.id.CityId;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.*;

public class CityValidatorTest {

	private City paris;

	private Client barclays;
	private Location manchesterLocation;
	private Location parisLocation;

	@Before
	public void buildProtoCity(){
		/**Build Barclays client*/
		barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);

		/**Build ADIR project*/
		Project adir = buildProject(PROJECT_ADIR_V1_ID, ADIR, VERSION_1, barclays, null, null);//RES-11

		/**Build Paris city*/
		paris = buildCity(buildCityId(PARIS_ID, FRANCE_ID), PARIS);
		Country france = buildCountry(FRANCE_ID, FRANCE);
		paris.setCountry(france);
		parisLocation = buildLocation(paris, adir);
		paris.setLocations(List.of(parisLocation));

		/**Build Manchester city*/
		City manchester = buildCity(new CityId(MANCHESTER_ID, UNITED_KINGDOM_ID), MANCHESTER);

		manchesterLocation = buildLocation(manchester, adir);
		adir.setLocations(List.of(manchesterLocation));
	}


	@Test
	public void testIsNameValid() {
		assertEquals(SUCCESS, CityValidator.isNameValid(PARIS).apply(paris));
	}

	@Test
	public void testIsCountryValid() {
		assertEquals(SUCCESS, CityValidator.isCountryValid(FRANCE).apply(paris));
	}

	@Test
	public void testAreLocationsValid() {
		//Test City -> Locations assoc. are equal
		assertEquals(SUCCESS, CityValidator.areLocationsValid(List.of(parisLocation)).apply(paris));

		//Tests City -> Locations assoc. not valid
		assertEquals(CITY_LOCATIONS_ARE_NOT_VALID, CityValidator.areLocationsValid(List.of(manchesterLocation)).apply(paris));

		//Tests City -> Locations assoc. is null	
		assertThat(paris.getLocations(), Matchers.not(Matchers.empty()));
		assertEquals(CITY_LOCATIONS_ARE_NULL, CityValidator.areLocationsValid(null).apply(paris));

		//Tests  City -> Locations assoc. not equals size
		paris.setLocations(List.of());
		assertNotNull(paris.getLocations());
		assertEquals(CITY_SIZE_LOCATIONS_ARE_DIFFERENT, CityValidator.areLocationsValid(List.of(manchesterLocation, parisLocation)).apply(paris));

		//Tests City -> Locations assoc. not equals in size
		paris.setLocations(null);
		assertThat(paris.getLocations(), Matchers.empty());
		assertEquals(CITY_SIZE_LOCATIONS_ARE_DIFFERENT, CityValidator.areLocationsValid(List.of(manchesterLocation)).apply(paris));
	}

}

