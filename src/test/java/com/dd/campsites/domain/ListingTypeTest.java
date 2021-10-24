package com.dd.campsites.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.campsites.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListingTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListingType.class);
        ListingType listingType1 = new ListingType();
        listingType1.setId(1L);
        ListingType listingType2 = new ListingType();
        listingType2.setId(listingType1.getId());
        assertThat(listingType1).isEqualTo(listingType2);
        listingType2.setId(2L);
        assertThat(listingType1).isNotEqualTo(listingType2);
        listingType1.setId(null);
        assertThat(listingType1).isNotEqualTo(listingType2);
    }
}
