package net.vyky.repository;

import net.vyky.domain.Area;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Area entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {}
