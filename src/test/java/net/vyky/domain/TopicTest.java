package net.vyky.domain;

import static net.vyky.domain.AreaTestSamples.*;
import static net.vyky.domain.TopicTestSamples.*;
import static net.vyky.domain.VykyUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TopicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Topic.class);
        Topic topic1 = getTopicSample1();
        Topic topic2 = new Topic();
        assertThat(topic1).isNotEqualTo(topic2);

        topic2.setId(topic1.getId());
        assertThat(topic1).isEqualTo(topic2);

        topic2 = getTopicSample2();
        assertThat(topic1).isNotEqualTo(topic2);
    }

    @Test
    void areaTest() throws Exception {
        Topic topic = getTopicRandomSampleGenerator();
        Area areaBack = getAreaRandomSampleGenerator();

        topic.setArea(areaBack);
        assertThat(topic.getArea()).isEqualTo(areaBack);

        topic.area(null);
        assertThat(topic.getArea()).isNull();
    }

    @Test
    void vykyUserTest() throws Exception {
        Topic topic = getTopicRandomSampleGenerator();
        VykyUser vykyUserBack = getVykyUserRandomSampleGenerator();

        topic.setVykyUser(vykyUserBack);
        assertThat(topic.getVykyUser()).isEqualTo(vykyUserBack);

        topic.vykyUser(null);
        assertThat(topic.getVykyUser()).isNull();
    }
}
