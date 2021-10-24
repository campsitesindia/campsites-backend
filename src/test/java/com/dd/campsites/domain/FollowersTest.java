package com.dd.campsites.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dd.campsites.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FollowersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Followers.class);
        Followers followers1 = new Followers();
        followers1.setId(1L);
        Followers followers2 = new Followers();
        followers2.setId(followers1.getId());
        assertThat(followers1).isEqualTo(followers2);
        followers2.setId(2L);
        assertThat(followers1).isNotEqualTo(followers2);
        followers1.setId(null);
        assertThat(followers1).isNotEqualTo(followers2);
    }
}
