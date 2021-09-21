package com.tecxis.resume.domain.id;

import static org.assertj.core.api.Assertions.assertThat;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import org.junit.Test;


import com.tecxis.resume.domain.id.EnrolmentId;

public class EnrolmentIdTest {

	@Test
	public void testToString() {
		EnrolmentId newEnrolmentId = new EnrolmentId();
		assertThat(newEnrolmentId.toString()).matches(DEFAULT_ID_REGEX);;
	}

}
