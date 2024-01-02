package net.vyky.repository;

import net.vyky.domain.VykyUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VykyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VykyUserRepository extends JpaRepository<VykyUser, Long> {}
