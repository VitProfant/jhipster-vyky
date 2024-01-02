package net.vyky.repository;

import net.vyky.domain.Config;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Config entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {}
