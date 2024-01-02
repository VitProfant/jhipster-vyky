package net.vyky.domain;

import static net.vyky.domain.PostTestSamples.*;
import static net.vyky.domain.PostTestSamples.*;
import static net.vyky.domain.TopicTestSamples.*;
import static net.vyky.domain.VykyUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = getPostSample1();
        Post post2 = new Post();
        assertThat(post1).isNotEqualTo(post2);

        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);

        post2 = getPostSample2();
        assertThat(post1).isNotEqualTo(post2);
    }

    @Test
    void topicTest() throws Exception {
        Post post = getPostRandomSampleGenerator();
        Topic topicBack = getTopicRandomSampleGenerator();

        post.setTopic(topicBack);
        assertThat(post.getTopic()).isEqualTo(topicBack);

        post.topic(null);
        assertThat(post.getTopic()).isNull();
    }

    @Test
    void parentTest() throws Exception {
        Post post = getPostRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        post.setParent(postBack);
        assertThat(post.getParent()).isEqualTo(postBack);

        post.parent(null);
        assertThat(post.getParent()).isNull();
    }

    @Test
    void authorTest() throws Exception {
        Post post = getPostRandomSampleGenerator();
        VykyUser vykyUserBack = getVykyUserRandomSampleGenerator();

        post.setAuthor(vykyUserBack);
        assertThat(post.getAuthor()).isEqualTo(vykyUserBack);

        post.author(null);
        assertThat(post.getAuthor()).isNull();
    }
}
