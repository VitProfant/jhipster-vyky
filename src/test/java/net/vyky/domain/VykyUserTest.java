package net.vyky.domain;

import static net.vyky.domain.VykyUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VykyUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VykyUser.class);
        VykyUser vykyUser1 = getVykyUserSample1();
        VykyUser vykyUser2 = new VykyUser();
        assertThat(vykyUser1).isNotEqualTo(vykyUser2);

        vykyUser2.setId(vykyUser1.getId());
        assertThat(vykyUser1).isEqualTo(vykyUser2);

        vykyUser2 = getVykyUserSample2();
        assertThat(vykyUser1).isNotEqualTo(vykyUser2);
    }
}
