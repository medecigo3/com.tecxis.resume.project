package com.tecxis.resume.domain.id;

import com.tecxis.resume.domain.*;
import org.junit.Test;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.RegexConstants.DEFAULT_ID_REGEX;
import static com.tecxis.resume.domain.util.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SupplyContractIdTest {

	@Test
	public void testToString() {
		SupplyContractId supplyContractId = new SupplyContractId();
		assertThat(supplyContractId.toString()).matches(DEFAULT_ID_REGEX);
	}

	@Test
	public void testEquals(){ //RES-63
		/**Build Clients*/
		Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
		/**BuildContracts*/
		Contract barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
		Contract testContract = buildContract(CONTRACT_ARVAL_ID, barclays, CONTRACT1_NAME);
		/**Build Staff*/
		Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, null);
		/**Build Suppliers*/
		Supplier fastconnect = buildSupplier(SUPPLIER_FASTCONNECT_ID, FASTCONNECT);
		Supplier accenture = buildSupplier(SUPPLIER_ACCENTURE_ID, ACCENTURE_SUPPLIER);
		/**Build ContractSuppliers*/
		SupplyContract barclaysContract1AmtAccentureContractSupplier = buildSupplyContract(barclaysContract1,amt, accenture);
		SupplyContract barclaysContract1AmtAccentureContractSupplierDuplicate = buildSupplyContract(barclaysContract1,amt, accenture);
		SupplyContract barclaysContract1AmtFastConnectContractSupplier =  buildSupplyContract(barclaysContract1, amt, fastconnect);
		SupplyContract testContractSupplier =  buildSupplyContract(testContract, amt, fastconnect);

		assertTrue(barclaysContract1AmtAccentureContractSupplier.equals(barclaysContract1AmtAccentureContractSupplier));
		assertTrue(barclaysContract1AmtAccentureContractSupplier.equals(barclaysContract1AmtAccentureContractSupplierDuplicate));
		assertTrue(barclaysContract1AmtFastConnectContractSupplier.equals(barclaysContract1AmtFastConnectContractSupplier));
		assertFalse(barclaysContract1AmtAccentureContractSupplier.equals(testContractSupplier));
		assertFalse(barclaysContract1AmtFastConnectContractSupplier.equals(barclaysContract1AmtAccentureContractSupplier));
		assertFalse(barclaysContract1AmtFastConnectContractSupplier.equals(testContractSupplier));
		assertFalse(testContractSupplier.equals(barclaysContract1AmtAccentureContractSupplier));
		assertFalse(testContractSupplier.equals(barclaysContract1AmtFastConnectContractSupplier));
	}

}
