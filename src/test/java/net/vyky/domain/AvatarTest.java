package net.vyky.domain;

import static net.vyky.domain.AvatarTestSamples.*;
import static net.vyky.domain.VykyUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvatarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avatar.class);
        Avatar avatar1 = getAvatarSample1();
        Avatar avatar2 = new Avatar();
        assertThat(avatar1).isNotEqualTo(avatar2);

        avatar2.setId(avatar1.getId());
        assertThat(avatar1).isEqualTo(avatar2);

        avatar2 = getAvatarSample2();
        assertThat(avatar1).isNotEqualTo(avatar2);
    }

    @Test
    void vykyUserTest() throws Exception {
        Avatar avatar = getAvatarRandomSampleGenerator();
        VykyUser vykyUserBack = getVykyUserRandomSampleGenerator();

        avatar.setVykyUser(vykyUserBack);
        assertThat(avatar.getVykyUser()).isEqualTo(vykyUserBack);

        avatar.vykyUser(null);
        assertThat(avatar.getVykyUser()).isNull();
    }
}
