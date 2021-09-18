package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.tecxis.resume.domain.id.StaffSkillId;

public class StaffSkillIdTest {

	@Test
	public void testToStringStaffSkillId() {
		StaffSkillId staffSkillId = new StaffSkillId();
		assertThat(staffSkillId.toString()).matches(DEFAULT_ID);
	}

}
