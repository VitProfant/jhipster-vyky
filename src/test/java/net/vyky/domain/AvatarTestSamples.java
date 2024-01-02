package net.vyky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AvatarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Avatar getAvatarSample1() {
        return new Avatar().id(1L).mime("mime1");
    }

    public static Avatar getAvatarSample2() {
        return new Avatar().id(2L).mime("mime2");
    }

    public static Avatar getAvatarRandomSampleGenerator() {
        return new Avatar().id(longCount.incrementAndGet()).mime(UUID.randomUUID().toString());
    }
}
