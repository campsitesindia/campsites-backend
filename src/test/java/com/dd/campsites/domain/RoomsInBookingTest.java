package com.dd.campsites.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.campsites.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomsInBookingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomsInBooking.class);
        RoomsInBooking roomsInBooking1 = new RoomsInBooking();
        roomsInBooking1.setId(1L);
        RoomsInBooking roomsInBooking2 = new RoomsInBooking();
        roomsInBooking2.setId(roomsInBooking1.getId());
        assertThat(roomsInBooking1).isEqualTo(roomsInBooking2);
        roomsInBooking2.setId(2L);
        assertThat(roomsInBooking1).isNotEqualTo(roomsInBooking2);
        roomsInBooking1.setId(null);
        assertThat(roomsInBooking1).isNotEqualTo(roomsInBooking2);
    }
}
