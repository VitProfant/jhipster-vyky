package net.vyky.repository;

import net.vyky.domain.Candidate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Candidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {}
