package net.vyky.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MessageRecipientAttrsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MessageRecipientAttrs getMessageRecipientAttrsSample1() {
        return new MessageRecipientAttrs().id(1L);
    }

    public static MessageRecipientAttrs getMessageRecipientAttrsSample2() {
        return new MessageRecipientAttrs().id(2L);
    }

    public static MessageRecipientAttrs getMessageRecipientAttrsRandomSampleGenerator() {
        return new MessageRecipientAttrs().id(longCount.incrementAndGet());
    }
}
