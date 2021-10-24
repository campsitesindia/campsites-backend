package com.dd.campsites.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.campsites.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeaturesListingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeaturesListing.class);
        FeaturesListing featuresListing1 = new FeaturesListing();
        featuresListing1.setId(1L);
        FeaturesListing featuresListing2 = new FeaturesListing();
        featuresListing2.setId(featuresListing1.getId());
        assertThat(featuresListing1).isEqualTo(featuresListing2);
        featuresListing2.setId(2L);
        assertThat(featuresListing1).isNotEqualTo(featuresListing2);
        featuresListing1.setId(null);
        assertThat(featuresListing1).isNotEqualTo(featuresListing2);
    }
}
