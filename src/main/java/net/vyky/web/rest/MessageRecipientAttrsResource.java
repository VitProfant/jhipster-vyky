package net.vyky.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.vyky.domain.MessageRecipientAttrs;
import net.vyky.repository.MessageRecipientAttrsRepository;
import net.vyky.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link net.vyky.domain.MessageRecipientAttrs}.
 */
@RestController
@RequestMapping("/api/message-recipient-attrs")
@Transactional
public class MessageRecipientAttrsResource {

    private final Logger log = LoggerFactory.getLogger(MessageRecipientAttrsResource.class);

    private static final String ENTITY_NAME = "messageRecipientAttrs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageRecipientAttrsRepository messageRecipientAttrsRepository;

    public MessageRecipientAttrsResource(MessageRecipientAttrsRepository messageRecipientAttrsRepository) {
        this.messageRecipientAttrsRepository = messageRecipientAttrsRepository;
    }

    /**
     * {@code POST  /message-recipient-attrs} : Create a new messageRecipientAttrs.
     *
     * @param messageRecipientAttrs the messageRecipientAttrs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageRecipientAttrs, or with status {@code 400 (Bad Request)} if the messageRecipientAttrs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MessageRecipientAttrs> createMessageRecipientAttrs(
        @Valid @RequestBody MessageRecipientAttrs messageRecipientAttrs
    ) throws URISyntaxException {
        log.debug("REST request to save MessageRecipientAttrs : {}", messageRecipientAttrs);
        if (messageRecipientAttrs.getId() != null) {
            throw new BadRequestAlertException("A new messageRecipientAttrs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageRecipientAttrs result = messageRecipientAttrsRepository.save(messageRecipientAttrs);
        return ResponseEntity
            .created(new URI("/api/message-recipient-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-recipient-attrs/:id} : Updates an existing messageRecipientAttrs.
     *
     * @param id the id of the messageRecipientAttrs to save.
     * @param messageRecipientAttrs the messageRecipientAttrs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageRecipientAttrs,
     * or with status {@code 400 (Bad Request)} if the messageRecipientAttrs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageRecipientAttrs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MessageRecipientAttrs> updateMessageRecipientAttrs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MessageRecipientAttrs messageRecipientAttrs
    ) throws URISyntaxException {
        log.debug("REST request to update MessageRecipientAttrs : {}, {}", id, messageRecipientAttrs);
        if (messageRecipientAttrs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageRecipientAttrs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageRecipientAttrsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MessageRecipientAttrs result = messageRecipientAttrsRepository.save(messageRecipientAttrs);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageRecipientAttrs.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /message-recipient-attrs/:id} : Partial updates given fields of an existing messageRecipientAttrs, field will ignore if it is null
     *
     * @param id the id of the messageRecipientAttrs to save.
     * @param messageRecipientAttrs the messageRecipientAttrs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageRecipientAttrs,
     * or with status {@code 400 (Bad Request)} if the messageRecipientAttrs is not valid,
     * or with status {@code 404 (Not Found)} if the messageRecipientAttrs is not found,
     * or with status {@code 500 (Internal Server Error)} if the messageRecipientAttrs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MessageRecipientAttrs> partialUpdateMessageRecipientAttrs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MessageRecipientAttrs messageRecipientAttrs
    ) throws URISyntaxException {
        log.debug("REST request to partial update MessageRecipientAttrs partially : {}, {}", id, messageRecipientAttrs);
        if (messageRecipientAttrs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageRecipientAttrs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageRecipientAttrsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MessageRecipientAttrs> result = messageRecipientAttrsRepository
            .findById(messageRecipientAttrs.getId())
            .map(existingMessageRecipientAttrs -> {
                if (messageRecipientAttrs.getIsRead() != null) {
                    existingMessageRecipientAttrs.setIsRead(messageRecipientAttrs.getIsRead());
                }
                if (messageRecipientAttrs.getIsDeleted() != null) {
                    existingMessageRecipientAttrs.setIsDeleted(messageRecipientAttrs.getIsDeleted());
                }

                return existingMessageRecipientAttrs;
            })
            .map(messageRecipientAttrsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageRecipientAttrs.getId().toString())
        );
    }

    /**
     * {@code GET  /message-recipient-attrs} : get all the messageRecipientAttrs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageRecipientAttrs in body.
     */
    @GetMapping("")
    public List<MessageRecipientAttrs> getAllMessageRecipientAttrs() {
        log.debug("REST request to get all MessageRecipientAttrs");
        return messageRecipientAttrsRepository.findAll();
    }

    /**
     * {@code GET  /message-recipient-attrs/:id} : get the "id" messageRecipientAttrs.
     *
     * @param id the id of the messageRecipientAttrs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageRecipientAttrs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageRecipientAttrs> getMessageRecipientAttrs(@PathVariable("id") Long id) {
        log.debug("REST request to get MessageRecipientAttrs : {}", id);
        Optional<MessageRecipientAttrs> messageRecipientAttrs = messageRecipientAttrsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(messageRecipientAttrs);
    }

    /**
     * {@code DELETE  /message-recipient-attrs/:id} : delete the "id" messageRecipientAttrs.
     *
     * @param id the id of the messageRecipientAttrs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageRecipientAttrs(@PathVariable("id") Long id) {
        log.debug("REST request to delete MessageRecipientAttrs : {}", id);
        messageRecipientAttrsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
