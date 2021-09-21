package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ContractIdTest {

	@Test
	public void testToString() {
		ContractId contractId = new ContractId();
		assertThat(contractId.toString()).matches(DEFAULT_ID_REGEX);
		
	}

}
