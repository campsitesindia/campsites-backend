package com.dd.campsites.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.campsites.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeaturesInRoomTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeaturesInRoom.class);
        FeaturesInRoom featuresInRoom1 = new FeaturesInRoom();
        featuresInRoom1.setId(1L);
        FeaturesInRoom featuresInRoom2 = new FeaturesInRoom();
        featuresInRoom2.setId(featuresInRoom1.getId());
        assertThat(featuresInRoom1).isEqualTo(featuresInRoom2);
        featuresInRoom2.setId(2L);
        assertThat(featuresInRoom1).isNotEqualTo(featuresInRoom2);
        featuresInRoom1.setId(null);
        assertThat(featuresInRoom1).isNotEqualTo(featuresInRoom2);
    }
}
