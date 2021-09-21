package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LocationIdTest {


	@Test
	public void testLocationIdToString() {
		LocationId locationId = new LocationId();
		locationId.toString();
		assertThat(locationId.toString()).matches(DEFAULT_ID_REGEX);
	}

}
