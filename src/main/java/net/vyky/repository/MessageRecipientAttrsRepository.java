package net.vyky.repository;

import net.vyky.domain.MessageRecipientAttrs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MessageRecipientAttrs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRecipientAttrsRepository extends JpaRepository<MessageRecipientAttrs, Long> {}
