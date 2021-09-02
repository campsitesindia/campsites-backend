package com.dd.ttippernest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.ttippernest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomFeaturesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomFeatures.class);
        RoomFeatures roomFeatures1 = new RoomFeatures();
        roomFeatures1.setId(1L);
        RoomFeatures roomFeatures2 = new RoomFeatures();
        roomFeatures2.setId(roomFeatures1.getId());
        assertThat(roomFeatures1).isEqualTo(roomFeatures2);
        roomFeatures2.setId(2L);
        assertThat(roomFeatures1).isNotEqualTo(roomFeatures2);
        roomFeatures1.setId(null);
        assertThat(roomFeatures1).isNotEqualTo(roomFeatures2);
    }
}
