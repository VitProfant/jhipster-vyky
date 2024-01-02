package net.vyky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PostTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Post getPostSample1() {
        return new Post().id(1L).version(1).level(1).title("title1").content("content1").parentId(1L).upvotes(1).downvotes(1);
    }

    public static Post getPostSample2() {
        return new Post().id(2L).version(2).level(2).title("title2").content("content2").parentId(2L).upvotes(2).downvotes(2);
    }

    public static Post getPostRandomSampleGenerator() {
        return new Post()
            .id(longCount.incrementAndGet())
            .version(intCount.incrementAndGet())
            .level(intCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .content(UUID.randomUUID().toString())
            .parentId(longCount.incrementAndGet())
            .upvotes(intCount.incrementAndGet())
            .downvotes(intCount.incrementAndGet());
    }
}
