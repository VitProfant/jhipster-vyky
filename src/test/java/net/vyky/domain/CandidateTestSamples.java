package net.vyky.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CandidateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Candidate getCandidateSample1() {
        return new Candidate().id(1L).login("login1").password("password1").email("email1").token("token1");
    }

    public static Candidate getCandidateSample2() {
        return new Candidate().id(2L).login("login2").password("password2").email("email2").token("token2");
    }

    public static Candidate getCandidateRandomSampleGenerator() {
        return new Candidate()
            .id(longCount.incrementAndGet())
            .login(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .token(UUID.randomUUID().toString());
    }
}
