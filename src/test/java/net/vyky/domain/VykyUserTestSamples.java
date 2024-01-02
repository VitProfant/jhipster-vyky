package net.vyky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VykyUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static VykyUser getVykyUserSample1() {
        return new VykyUser().id(1L).version(1).login("login1").password("password1").email("email1");
    }

    public static VykyUser getVykyUserSample2() {
        return new VykyUser().id(2L).version(2).login("login2").password("password2").email("email2");
    }

    public static VykyUser getVykyUserRandomSampleGenerator() {
        return new VykyUser()
            .id(longCount.incrementAndGet())
            .version(intCount.incrementAndGet())
            .login(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
