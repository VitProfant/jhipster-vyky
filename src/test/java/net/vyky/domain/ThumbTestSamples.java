package net.vyky.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThumbTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Thumb getThumbSample1() {
        return new Thumb().id(1L).version(1);
    }

    public static Thumb getThumbSample2() {
        return new Thumb().id(2L).version(2);
    }

    public static Thumb getThumbRandomSampleGenerator() {
        return new Thumb().id(longCount.incrementAndGet()).version(intCount.incrementAndGet());
    }
}
