package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.*;
import org.junit.Before;
import org.junit.Test;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.Utils.buildSupplyContract;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.*;

public class SupplyContractValidatorTest {//RES-60


   private SupplyContract barclaysContract1AmtAccentureContractSupplier;
   private Supplier accenture;
   private Contract barclaysContract1;
   private Contract testContract;
   private Supplier fastconnect;

    @Before
    public void before(){
        /**Build Clients*/
        Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
        /**BuildContracts*/
        barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
        testContract = buildContract(CONTRACT_ARVAL_ID, barclays, CONTRACT1_NAME);
        /**Build Staff*/
        Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, null);
        /**Build Suppliers*/
        fastconnect = buildSupplier(SUPPLIER_FASTCONNECT_ID, FASTCONNECT);
        accenture = buildSupplier(SUPPLIER_ACCENTURE_ID, ACCENTURE_SUPPLIER);
        /**Build ContractSuppliers*/
        barclaysContract1AmtAccentureContractSupplier = buildSupplyContract(barclaysContract1,amt, accenture);

    }

    @Test
    public void isSupplyContractValid() {
        assertEquals(SUCCESS, SupplyContractValidator.isSupplyContractValid(accenture, barclaysContract1).apply(barclaysContract1AmtAccentureContractSupplier));
        assertEquals(SUPPLYCONTRACT_CONTRACT_IS_NOT_VALID, SupplyContractValidator.isSupplyContractValid(accenture, testContract).apply(barclaysContract1AmtAccentureContractSupplier));
        assertEquals(SUPPLYCONTRACT_SUPPLIER_IS_NOT_VALID, SupplyContractValidator.isSupplyContractValid(fastconnect, barclaysContract1).apply(barclaysContract1AmtAccentureContractSupplier));

    }

    @Test
    public void isStartDateValid() {
        assertEquals(SUCCESS, SupplyContractValidator.isStartDateValid(null).apply(barclaysContract1AmtAccentureContractSupplier));
        barclaysContract1AmtAccentureContractSupplier.setStartDate(CONTRACT1_STARTDATE);
        assertEquals(SUPPLYCONTRACT_STARTDATE_NOT_VALID, SupplyContractValidator.isStartDateValid(CONTRACT2_STARTDATE).apply(barclaysContract1AmtAccentureContractSupplier));


    }

    @Test
    public void isEndDateValid() {
        assertEquals(SUCCESS, SupplyContractValidator.isEndDateValid(null).apply(barclaysContract1AmtAccentureContractSupplier));
        barclaysContract1AmtAccentureContractSupplier.setEndDate(CONTRACT2_ENDDATE);
        assertEquals(SUCCESS, SupplyContractValidator.isEndDateValid(CONTRACT2_ENDDATE).apply(barclaysContract1AmtAccentureContractSupplier));
        assertEquals(SUPPLYCONTRACT_ENDDATE_NOT_VALID, SupplyContractValidator.isEndDateValid(CONTRACT3_ENDDATE).apply(barclaysContract1AmtAccentureContractSupplier));
    }
}