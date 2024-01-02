package net.vyky.repository;

import net.vyky.domain.Avatar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Avatar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {}
