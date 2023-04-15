package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tecxis.resume.domain.Constants.*;
import static com.tecxis.resume.domain.util.Utils.*;
import static com.tecxis.resume.domain.util.function.ValidationResult.*;
import static org.junit.Assert.*;

public class CountryValidatorTest {

    private Client barclays;
    private Country france;
    private City paris;
    private City london;

    @Before
    public void buildProtoCountry(){
        /**Build Barclays client*/
        barclays = buildClient(BARCLAYS, CLIENT_BARCLAYS_ID);

        /**Build ADIR project*/
        Project adir = buildProject(ADIR, VERSION_1, barclays, null, null);

        /**Build London city*/
        london = buildCity(buildCityId(LONDON_ID, UNITED_KINGDOM_ID), LONDON);
        Country uk = buildCountry(UNITED_KINGDOM_ID, UNITED_KINGDOM);
        london.setCountry(uk);
        Location londonLocation = buildLocation(london, adir);
        london.setLocations(List.of(londonLocation));

        /**Build Paris city*/
        paris = buildCity(buildCityId(PARIS_ID, FRANCE_ID), PARIS);
        france = buildCountry(FRANCE_ID, FRANCE);
        france.setCities(List.of(paris));
        paris.setCountry(france);
        Location parisLocation = buildLocation(paris, adir);
        paris.setLocations(List.of(parisLocation));
    }

    @Test
    public void testIsNameValid(){
        Assert.assertEquals(SUCCESS, CountryValidator.isNameValid(FRANCE).apply(france));
    }
    @Test
    public void testAreCitiesValid() {
        //Test Country -> Cities assoc. are equal
        assertEquals(SUCCESS, CountryValidator.areCitiesValid(List.of(paris)).apply(france));

        //Tests Country -> Cities assoc. not valid
        assertEquals(COUNTRY_CITIES_ARE_NOT_VALID, CountryValidator.areCitiesValid(List.of(london)).apply(france));

        //Tests Country -> Cities assoc. is null
        assertThat(france.getCities(), Matchers.not(Matchers.empty()));
        assertEquals(COUNTRY_CITIES_ARE_NULL, CountryValidator.areCitiesValid(null).apply(france));

        //Tests  Country -> Cities assoc. not equals size
        france.setCities(List.of());
        assertNotNull(france.getCities());
        assertEquals(COUNTRY_SIZE_CITIES_ARE_DIFFERENT, CountryValidator.areCitiesValid(List.of(paris, london)).apply(france));

        //Tests Country -> Cities assoc. not equals in size
        france.setCities(null);
        assertThat(france.getCities(), Matchers.empty());
        assertEquals(COUNTRY_SIZE_CITIES_ARE_DIFFERENT, CountryValidator.areCitiesValid(List.of(london)).apply(france));
    }
}