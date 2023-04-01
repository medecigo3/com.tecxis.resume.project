package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.Client;
import com.tecxis.resume.domain.Contract;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.buildClient;
import static com.tecxis.resume.domain.util.Utils.buildContract;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class ClientValidatorTest {

    private Client barclays;
    private Contract barclaysContract1;
    private Contract arvalContract11;


    @Before
    public void buildProtoClient(){
        barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);
        barclaysContract1 = buildContract(barclays, CONTRACT1_NAME);
        barclays.setContracts(List.of(barclaysContract1));

        Client arval = buildClient(ARVAL, CLIENT_ARVAL_ID);
        arvalContract11 = buildContract(arval, CONTRACT11_NAME);

    }

    @Test
    public void isClientNameValid() {
        assertEquals(SUCCESS, ClientValidator.isClientNameValid(BARCLAYS).apply(barclays));
    }

    @Test
    public void areContractsValid() {
        //Tests Client -> Contracts assoc. are equal
        assertEquals(SUCCESS, ClientValidator.areContractsValid(List.of(barclaysContract1)).apply(barclays));

        //Tests Client -> Contracts assoc. not valid
        assertEquals(CLIENT_CONTRACTS_ARE_NOT_VALID, ClientValidator.areContractsValid(List.of(arvalContract11)).apply(barclays));

        //Tests Client -> Contracts assoc. is null
        assertThat(barclays.getContracts(), Matchers.not(Matchers.empty()));
        assertEquals(CLIENT_CONTRACTS_ARE_NULL, ClientValidator.areContractsValid(null).apply(barclays));

        //Tests Client -> Contracts assoc. not equals size
        barclays.setContracts(List.of());
        assertNotNull(barclays.getContracts());
        assertEquals(CLIENT_SIZE_CONTRACTS_ARE_DIFFERENT, ClientValidator.areContractsValid(List.of(arvalContract11, barclaysContract1)).apply(barclays));

        //Tests Client -> Contracts assoc. not equals in size
        barclays.setContracts(null);
        assertThat(barclays.getContracts(), Matchers.empty());
        assertEquals(CLIENT_SIZE_CONTRACTS_ARE_DIFFERENT, ClientValidator.areContractsValid(List.of(arvalContract11)).apply(barclays));
    }
}