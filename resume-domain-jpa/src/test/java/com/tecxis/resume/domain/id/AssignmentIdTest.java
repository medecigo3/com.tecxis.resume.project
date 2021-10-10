package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.tecxis.resume.domain.id.AssignmentId;

public class AssignmentIdTest {

	@Test
	public void testToString() {
		AssignmentId assignmentId = new AssignmentId();
		assertThat(assignmentId.toString()).matches(DEFAULT_ID_REGEX);
	}

}
