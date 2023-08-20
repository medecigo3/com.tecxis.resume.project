package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.Utils.buildSupplyContract;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.*;

public class SupplierValidatorTest { //RES-58

    private Supplier accenture;
    private SupplyContract barclaysContract1AmtAccentureContractSupplier;
    private SupplyContract barclaysContract1AmtFastConnectContractSupplier;
    private SupplyContract testSupplyContract1;
    private SupplyContract testSupplyContract2;
    private EmploymentContract amtAccentureEmploymentContract;
    private EmploymentContract johnFastconnectEmploymentContract;

    @Before
    public void buildProtoSupplier(){
        accenture = buildSupplier(SUPPLIER_ACCENTURE_ID, ACCENTURE_SUPPLIER);
        Supplier fastconnect = buildSupplier(SUPPLIER_FASTCONNECT_ID, FASTCONNECT);//Test supplier
        /**Build Staff*/
        Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, null);
        Staff testStaff = buildStaff(STAFF_JOHN_ID, JOHN_NAME, JOHN_LASTNAME, null);//Test Staff
        /**Build Client*/
        Client barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
        Client testClient = buildClient(AXELTIS, CLIENT_AXELTIS_ID);
        /**Build Contract*/
        Contract barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
        Contract testContract1= buildContract(CONTRACT_AXELTIS_ID1, testClient, CONTRACT7_NAME);//Test Contract
        /**Build ContractSupplier*/
        barclaysContract1AmtAccentureContractSupplier = buildSupplyContract(barclaysContract1, amt, accenture);
        barclaysContract1AmtFastConnectContractSupplier = buildSupplyContract(barclaysContract1, amt, fastconnect);
        accenture.setSupplyContracts(List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier));
        testSupplyContract1 =  buildSupplyContract(barclaysContract1, testStaff, fastconnect); //Test SupplyContract
        testSupplyContract2 =  buildSupplyContract(testContract1, testStaff, fastconnect);//Test SupplyContract
        /**Build EmploymentContract*/
        amtAccentureEmploymentContract = buildEmploymentContract(AMT_ACCENTURE_EMPLOYMENT_CONTRACTID, accenture, amt, AMT_ACCENTURE_EMPLOYMENT_STARTDATE);
        accenture.setEmploymentContracts(List.of(amtAccentureEmploymentContract));
        johnFastconnectEmploymentContract = buildEmploymentContract(AMT_ALTERNA_EMPLOYMENT_CONTRACT_ID, fastconnect, testStaff, JOHN_ALPHATRESS_EMPLOYMENT_ENDDATE); //Test EmploymentContract

    }
    @Test
    public void isSupplierNameValid() {
        Assert.assertEquals(SUCCESS, SupplierValidator.isSupplierNameValid(ACCENTURE_SUPPLIER).apply(accenture));
    }

    @Test
    public void areSupplyContractsValid() {
        //Tests Supplier -> SupplyContracts assoc. are equal
        assertEquals(SUCCESS, SupplierValidator.areSupplyContractsValid(List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier)).apply(accenture));

        //Tests Supplier -> SupplyContracts assoc. not valid
        assertEquals(SUPPLIER_SUPPLYCONTRACTS_ARE_NOT_VALID, SupplierValidator.areSupplyContractsValid(List.of(testSupplyContract1, testSupplyContract2)).apply(accenture));

        //Tests Supplier -> SupplyContracts assoc. is null
        assertThat(accenture.getSupplyContracts(), Matchers.not(Matchers.empty()));
        assertEquals(SUPPLIER_SUPPLYCONTRACTS_ARE_NULL, SupplierValidator.areSupplyContractsValid(null).apply(accenture));

        //Tests Supplier -> SupplyContracts assoc. not equals size
        accenture.setSupplyContracts(List.of());
        assertNotNull(accenture.getSupplyContracts());
        assertEquals(SUPPLIER_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT, SupplierValidator.areSupplyContractsValid(List.of(barclaysContract1AmtAccentureContractSupplier)).apply(accenture));

        //Tests Supplier -> SupplyContracts assoc. not equals in size
        accenture.setSupplyContracts(null);
        assertThat(accenture.getSupplyContracts(), Matchers.empty());
        assertEquals(SUPPLIER_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT, SupplierValidator.areSupplyContractsValid(List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier, testSupplyContract1)).apply(accenture));
    }

    @Test
    public void areEmploymentContractsValid() {
        //Tests Supplier -> EmploymentContracts assoc. are equal
        assertEquals(SUCCESS, SupplierValidator.areEmploymentContractsValid(List.of(amtAccentureEmploymentContract)).apply(accenture));

        //Tests Supplier -> EmploymentContracts assoc. not valid
        assertEquals(SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NOT_VALID, SupplierValidator.areEmploymentContractsValid(List.of(johnFastconnectEmploymentContract)).apply(accenture));

        //Tests Supplier -> EmploymentContracts assoc. is null
        assertThat(accenture.getEmploymentContracts(), Matchers.not(Matchers.empty()));
        assertEquals(SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NULL, SupplierValidator.areEmploymentContractsValid(null).apply(accenture));

        //Tests Supplier -> EmploymentContracts assoc. not equals size
        accenture.setEmploymentContracts(List.of());
        assertNotNull(accenture.getEmploymentContracts());
        assertEquals(SUPPLIER_SIZE_EMPLOYMENTCONTRACTS_ARE_DIFFERENT, SupplierValidator.areEmploymentContractsValid(List.of(amtAccentureEmploymentContract, johnFastconnectEmploymentContract)).apply(accenture));
    }
}