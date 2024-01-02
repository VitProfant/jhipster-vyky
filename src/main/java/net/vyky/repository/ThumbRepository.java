package net.vyky.repository;

import net.vyky.domain.Thumb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Thumb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThumbRepository extends JpaRepository<Thumb, Long> {}
