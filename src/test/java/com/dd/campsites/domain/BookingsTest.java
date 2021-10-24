package com.dd.campsites.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.campsites.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bookings.class);
        Bookings bookings1 = new Bookings();
        bookings1.setId(1L);
        Bookings bookings2 = new Bookings();
        bookings2.setId(bookings1.getId());
        assertThat(bookings1).isEqualTo(bookings2);
        bookings2.setId(2L);
        assertThat(bookings1).isNotEqualTo(bookings2);
        bookings1.setId(null);
        assertThat(bookings1).isNotEqualTo(bookings2);
    }
}
