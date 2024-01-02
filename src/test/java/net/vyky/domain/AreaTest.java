package net.vyky.domain;

import static net.vyky.domain.AreaTestSamples.*;
import static net.vyky.domain.AreaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Area.class);
        Area area1 = getAreaSample1();
        Area area2 = new Area();
        assertThat(area1).isNotEqualTo(area2);

        area2.setId(area1.getId());
        assertThat(area1).isEqualTo(area2);

        area2 = getAreaSample2();
        assertThat(area1).isNotEqualTo(area2);
    }

    @Test
    void parentTest() throws Exception {
        Area area = getAreaRandomSampleGenerator();
        Area areaBack = getAreaRandomSampleGenerator();

        area.setParent(areaBack);
        assertThat(area.getParent()).isEqualTo(areaBack);

        area.parent(null);
        assertThat(area.getParent()).isNull();
    }
}
