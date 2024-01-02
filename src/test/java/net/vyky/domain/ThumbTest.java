package net.vyky.domain;

import static net.vyky.domain.PostTestSamples.*;
import static net.vyky.domain.ThumbTestSamples.*;
import static net.vyky.domain.VykyUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThumbTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thumb.class);
        Thumb thumb1 = getThumbSample1();
        Thumb thumb2 = new Thumb();
        assertThat(thumb1).isNotEqualTo(thumb2);

        thumb2.setId(thumb1.getId());
        assertThat(thumb1).isEqualTo(thumb2);

        thumb2 = getThumbSample2();
        assertThat(thumb1).isNotEqualTo(thumb2);
    }

    @Test
    void postTest() throws Exception {
        Thumb thumb = getThumbRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        thumb.setPost(postBack);
        assertThat(thumb.getPost()).isEqualTo(postBack);

        thumb.post(null);
        assertThat(thumb.getPost()).isNull();
    }

    @Test
    void vykyUserTest() throws Exception {
        Thumb thumb = getThumbRandomSampleGenerator();
        VykyUser vykyUserBack = getVykyUserRandomSampleGenerator();

        thumb.setVykyUser(vykyUserBack);
        assertThat(thumb.getVykyUser()).isEqualTo(vykyUserBack);

        thumb.vykyUser(null);
        assertThat(thumb.getVykyUser()).isNull();
    }
}
