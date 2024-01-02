package net.vyky.domain;

import static net.vyky.domain.MessageTestSamples.*;
import static net.vyky.domain.MessageTestSamples.*;
import static net.vyky.domain.VykyUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = getMessageSample1();
        Message message2 = new Message();
        assertThat(message1).isNotEqualTo(message2);

        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);

        message2 = getMessageSample2();
        assertThat(message1).isNotEqualTo(message2);
    }

    @Test
    void parentTest() throws Exception {
        Message message = getMessageRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        message.setParent(messageBack);
        assertThat(message.getParent()).isEqualTo(messageBack);

        message.parent(null);
        assertThat(message.getParent()).isNull();
    }

    @Test
    void authorTest() throws Exception {
        Message message = getMessageRandomSampleGenerator();
        VykyUser vykyUserBack = getVykyUserRandomSampleGenerator();

        message.setAuthor(vykyUserBack);
        assertThat(message.getAuthor()).isEqualTo(vykyUserBack);

        message.author(null);
        assertThat(message.getAuthor()).isNull();
    }
}
