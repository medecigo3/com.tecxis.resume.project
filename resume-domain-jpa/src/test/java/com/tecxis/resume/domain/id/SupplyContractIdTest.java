package com.tecxis.resume.domain.id;

import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID;
import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;

import com.tecxis.resume.domain.id.SupplyContractId;

public class SupplyContractIdTest {

	@Test
	public void testToString() {
		SupplyContractId supplyContractId = new SupplyContractId();
		assertThat(supplyContractId.toString()).matches(DEFAULT_ID);
	}

}
