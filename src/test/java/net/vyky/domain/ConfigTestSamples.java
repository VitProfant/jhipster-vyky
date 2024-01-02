package net.vyky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Config getConfigSample1() {
        return new Config().id(1L).version(1).value("value1");
    }

    public static Config getConfigSample2() {
        return new Config().id(2L).version(2).value("value2");
    }

    public static Config getConfigRandomSampleGenerator() {
        return new Config().id(longCount.incrementAndGet()).version(intCount.incrementAndGet()).value(UUID.randomUUID().toString());
    }
}
