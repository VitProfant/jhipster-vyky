package net.vyky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AreaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Area getAreaSample1() {
        return new Area().id(1L).version(1).level(1).name("name1").parentId(1L);
    }

    public static Area getAreaSample2() {
        return new Area().id(2L).version(2).level(2).name("name2").parentId(2L);
    }

    public static Area getAreaRandomSampleGenerator() {
        return new Area()
            .id(longCount.incrementAndGet())
            .version(intCount.incrementAndGet())
            .level(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .parentId(longCount.incrementAndGet());
    }
}
