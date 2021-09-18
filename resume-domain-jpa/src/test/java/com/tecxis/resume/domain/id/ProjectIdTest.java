package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ProjectIdTest {

	@Test
	public void testToString() {
		ProjectId projectId = new ProjectId();
		assertThat(projectId.toString()).matches(DEFAULT_ID);
	}

}
