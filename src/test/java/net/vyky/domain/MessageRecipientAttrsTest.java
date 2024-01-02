package net.vyky.domain;

import static net.vyky.domain.MessageRecipientAttrsTestSamples.*;
import static net.vyky.domain.MessageTestSamples.*;
import static net.vyky.domain.VykyUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MessageRecipientAttrsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageRecipientAttrs.class);
        MessageRecipientAttrs messageRecipientAttrs1 = getMessageRecipientAttrsSample1();
        MessageRecipientAttrs messageRecipientAttrs2 = new MessageRecipientAttrs();
        assertThat(messageRecipientAttrs1).isNotEqualTo(messageRecipientAttrs2);

        messageRecipientAttrs2.setId(messageRecipientAttrs1.getId());
        assertThat(messageRecipientAttrs1).isEqualTo(messageRecipientAttrs2);

        messageRecipientAttrs2 = getMessageRecipientAttrsSample2();
        assertThat(messageRecipientAttrs1).isNotEqualTo(messageRecipientAttrs2);
    }

    @Test
    void messageTest() throws Exception {
        MessageRecipientAttrs messageRecipientAttrs = getMessageRecipientAttrsRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        messageRecipientAttrs.setMessage(messageBack);
        assertThat(messageRecipientAttrs.getMessage()).isEqualTo(messageBack);

        messageRecipientAttrs.message(null);
        assertThat(messageRecipientAttrs.getMessage()).isNull();
    }

    @Test
    void recipientTest() throws Exception {
        MessageRecipientAttrs messageRecipientAttrs = getMessageRecipientAttrsRandomSampleGenerator();
        VykyUser vykyUserBack = getVykyUserRandomSampleGenerator();

        messageRecipientAttrs.setRecipient(vykyUserBack);
        assertThat(messageRecipientAttrs.getRecipient()).isEqualTo(vykyUserBack);

        messageRecipientAttrs.recipient(null);
        assertThat(messageRecipientAttrs.getRecipient()).isNull();
    }
}
