package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.*;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.Utils.buildSupplyContract;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.*;

public class ContractValidatorTest {
    private Client barclays;
    private Contract barclaysContract1;
    private Agreement barclaysContractv1MuleAgreement;
    private Agreement barclaysContractv1ScmAgreement;
    private Agreement arvalContract11TibcoAgreement;
    private Agreement arvalContract11J2eeAgreement;
    private SupplyContract barclaysContract1AmtAccentureContractSupplier;
    private SupplyContract barclaysContract1AmtFastConnectContractSupplier;
    private SupplyContract barclaysContract1AmtAmesysContractSupplier;
    private SupplyContract barclaysContract1AmtAlternaContractSupplier;

    @Before
    public void buildProtoContract(){
        /**Build Client*/
        barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
        /**Build target Contract*/
        barclaysContract1 = buildContract(CONTRACT_BARCLAYS_ID, barclays, CONTRACT1_NAME);
        barclays.setContracts(List.of(barclaysContract1));
        /**Build Services*/
        Service muleService = buildService(MULE_ESB_CONSULTANT);
        Service scmService = buildService(SCM_ASSOCIATE_DEVELOPPER);
        /**Build Agreements*/
        barclaysContractv1MuleAgreement = new Agreement(barclaysContract1, muleService);
        barclaysContractv1ScmAgreement = new Agreement(barclaysContract1, scmService);
        barclaysContract1.setAgreements(List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement));
        /**Build Supplier*/
        Supplier accenture = buildSupplier(SUPPLIER_ACCENTURE_ID, ACCENTURE_SUPPLIER);
        Supplier fastconnect = buildSupplier(SUPPLIER_FASTCONNECT_ID, FASTCONNECT);
        /**Build Staff*/
        Staff amt = buildStaff(STAFF_AMT_ID, AMT_NAME, AMT_LASTNAME, null);
        /**Build ContractSupplier*/
        barclaysContract1AmtAccentureContractSupplier = buildSupplyContract(barclaysContract1,amt, accenture);
        barclaysContract1AmtFastConnectContractSupplier =  buildSupplyContract(barclaysContract1, amt, fastconnect);
        barclaysContract1.setSupplyContracts(List.of(barclaysContract1AmtFastConnectContractSupplier, barclaysContract1AmtAccentureContractSupplier));       


        /**Build test Client*/
        Client arval = buildClient(ARVAL, CLIENT_ARVAL_ID);
        /**Build test contract*/
        Contract arvalContract11 = buildContract(CONTRACT_ARVAL_ID, arval, CONTRACT11_NAME);
        /**Build test Services*/
        Service tibcoService = buildService(TIBCO_BW_CONSULTANT);
        Service j2eeService = buildService(J2EE_DEVELOPPER);
        /**Build test Agreements*/
        arvalContract11TibcoAgreement = new Agreement(arvalContract11, tibcoService);
        arvalContract11J2eeAgreement = new Agreement(arvalContract11, j2eeService);
        /**Build test Suppliers*/
        Supplier amesys = buildSupplier(SUPPLIER_AMESYS_ID, AMESYS);
        Supplier alterna = buildSupplier(SUPPLIER_ALTERNA_ID, ALTERNA);
        /**Build test SupplyContracts*/
        barclaysContract1AmtAmesysContractSupplier = buildSupplyContract(arvalContract11, amt, amesys);
        barclaysContract1AmtAlternaContractSupplier = buildSupplyContract(arvalContract11, amt, alterna);
        arvalContract11.setSupplyContracts(List.of(barclaysContract1AmtAmesysContractSupplier, barclaysContract1AmtAlternaContractSupplier));
    }

    @Test
    public void isContractIdValid() {
        assertEquals(SUCCESS, ContractValidator.isContractIdValid(CONTRACT_BARCLAYS_ID).apply(barclaysContract1));
    }

    @Test
    public void isClientValid() {
        assertEquals(SUCCESS, ContractValidator.isClientValid(barclays).apply(barclaysContract1));
    }

    @Test
    public void areAgreementsValid() {
        //Tests Contract -> Agreements assoc. are equal
        assertEquals(SUCCESS, ContractValidator.areAgreementsValid(List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement)).apply(barclaysContract1));

        //Tests Contract -> Agreements assoc. not valid
        assertEquals(CONTRACT_AGREEMENTS_ARE_NOT_VALID, ContractValidator.areAgreementsValid(List.of(arvalContract11TibcoAgreement, arvalContract11J2eeAgreement)).apply(barclaysContract1));

        //Tests Contract -> Agreements assoc. is null
        assertThat(barclaysContract1.getAgreements(), Matchers.not(Matchers.empty()));
        assertEquals(CONTRACT_AGREEMENTS_ARE_NULL, ContractValidator.areAgreementsValid(null).apply(barclaysContract1));

        //Tests Contract -> Agreements assoc. not equals size
        barclaysContract1.setAgreements(List.of());
        assertNotNull(barclaysContract1.getAgreements());
        assertEquals(CONTRACT_SIZE_AGREEMENTS_ARE_DIFFERENT, ContractValidator.areAgreementsValid(List.of(barclaysContractv1MuleAgreement)).apply(barclaysContract1));

        //Tests Contract -> Agreements assoc. not equals in size
        barclaysContract1.setAgreements(null);
        assertThat(barclaysContract1.getAgreements(), Matchers.empty());
        assertEquals(CONTRACT_SIZE_AGREEMENTS_ARE_DIFFERENT, ContractValidator.areAgreementsValid(List.of(barclaysContractv1MuleAgreement, barclaysContractv1ScmAgreement)).apply(barclaysContract1));
    }

    @Test
    public void areSupplyContractsValid() {
        //Tests Contract -> SupplyContracts assoc. are equal
        assertEquals(SUCCESS, ContractValidator.areSupplyContractsValid(List.of(barclaysContract1AmtAccentureContractSupplier, barclaysContract1AmtFastConnectContractSupplier)).apply(barclaysContract1));

        //Tests Contract -> SupplyContracts assoc. not valid
        assertEquals(CONTRACT_SUPPLYCONTRACTS_ARE_NOT_VALID, ContractValidator.areSupplyContractsValid(List.of(barclaysContract1AmtAmesysContractSupplier, barclaysContract1AmtAlternaContractSupplier)).apply(barclaysContract1));

        //Tests Contract -> SupplyContracts assoc. is null
        assertThat(barclaysContract1.getSupplyContracts(), Matchers.not(Matchers.empty()));
        assertEquals(CONTRACT_SUPPLYCONTRACTS_ARE_NULL, ContractValidator.areSupplyContractsValid(null).apply(barclaysContract1));

        //Tests Contract -> SupplyContracts assoc. not equals size
        barclaysContract1.setSupplyContracts(List.of());
        assertNotNull(barclaysContract1.getSupplyContracts());
        assertEquals(CONTRACT_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT, ContractValidator.areSupplyContractsValid(List.of(barclaysContract1AmtAccentureContractSupplier)).apply(barclaysContract1));

        //Tests Contract -> SupplyContracts assoc. not equals in size
        barclaysContract1.setSupplyContracts(null);
        assertThat(barclaysContract1.getSupplyContracts(), Matchers.empty());
        assertEquals(CONTRACT_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT, ContractValidator.areSupplyContractsValid(List.of(barclaysContract1AmtAccentureContractSupplier, barclaysContract1AmtFastConnectContractSupplier)).apply(barclaysContract1));
    }
}