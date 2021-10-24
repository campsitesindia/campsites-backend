package com.dd.campsites.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.campsites.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomsForListingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomsForListing.class);
        RoomsForListing roomsForListing1 = new RoomsForListing();
        roomsForListing1.setId(1L);
        RoomsForListing roomsForListing2 = new RoomsForListing();
        roomsForListing2.setId(roomsForListing1.getId());
        assertThat(roomsForListing1).isEqualTo(roomsForListing2);
        roomsForListing2.setId(2L);
        assertThat(roomsForListing1).isNotEqualTo(roomsForListing2);
        roomsForListing1.setId(null);
        assertThat(roomsForListing1).isNotEqualTo(roomsForListing2);
    }
}
