package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.tecxis.resume.domain.id.StaffProjectAssignmentId;

public class StaffProjectAssignmentIdTest {

	@Test
	public void testToString() {
		StaffProjectAssignmentId staffProjectAssignmentId = new StaffProjectAssignmentId();
		assertThat(staffProjectAssignmentId.toString()).matches(DEFAULT_ID);
	}

}
