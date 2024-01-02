package net.vyky.domain;

import static net.vyky.domain.CandidateTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.vyky.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidate.class);
        Candidate candidate1 = getCandidateSample1();
        Candidate candidate2 = new Candidate();
        assertThat(candidate1).isNotEqualTo(candidate2);

        candidate2.setId(candidate1.getId());
        assertThat(candidate1).isEqualTo(candidate2);

        candidate2 = getCandidateSample2();
        assertThat(candidate1).isNotEqualTo(candidate2);
    }
}
