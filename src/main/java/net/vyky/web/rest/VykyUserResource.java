package net.vyky.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.vyky.domain.VykyUser;
import net.vyky.repository.VykyUserRepository;
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
 * REST controller for managing {@link net.vyky.domain.VykyUser}.
 */
@RestController
@RequestMapping("/api/vyky-users")
@Transactional
public class VykyUserResource {

    private final Logger log = LoggerFactory.getLogger(VykyUserResource.class);

    private static final String ENTITY_NAME = "vykyUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VykyUserRepository vykyUserRepository;

    public VykyUserResource(VykyUserRepository vykyUserRepository) {
        this.vykyUserRepository = vykyUserRepository;
    }

    /**
     * {@code POST  /vyky-users} : Create a new vykyUser.
     *
     * @param vykyUser the vykyUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vykyUser, or with status {@code 400 (Bad Request)} if the vykyUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VykyUser> createVykyUser(@Valid @RequestBody VykyUser vykyUser) throws URISyntaxException {
        log.debug("REST request to save VykyUser : {}", vykyUser);
        if (vykyUser.getId() != null) {
            throw new BadRequestAlertException("A new vykyUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VykyUser result = vykyUserRepository.save(vykyUser);
        return ResponseEntity
            .created(new URI("/api/vyky-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vyky-users/:id} : Updates an existing vykyUser.
     *
     * @param id the id of the vykyUser to save.
     * @param vykyUser the vykyUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vykyUser,
     * or with status {@code 400 (Bad Request)} if the vykyUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vykyUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VykyUser> updateVykyUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VykyUser vykyUser
    ) throws URISyntaxException {
        log.debug("REST request to update VykyUser : {}, {}", id, vykyUser);
        if (vykyUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vykyUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vykyUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VykyUser result = vykyUserRepository.save(vykyUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vykyUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vyky-users/:id} : Partial updates given fields of an existing vykyUser, field will ignore if it is null
     *
     * @param id the id of the vykyUser to save.
     * @param vykyUser the vykyUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vykyUser,
     * or with status {@code 400 (Bad Request)} if the vykyUser is not valid,
     * or with status {@code 404 (Not Found)} if the vykyUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the vykyUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VykyUser> partialUpdateVykyUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VykyUser vykyUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update VykyUser partially : {}, {}", id, vykyUser);
        if (vykyUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vykyUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vykyUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VykyUser> result = vykyUserRepository
            .findById(vykyUser.getId())
            .map(existingVykyUser -> {
                if (vykyUser.getVersion() != null) {
                    existingVykyUser.setVersion(vykyUser.getVersion());
                }
                if (vykyUser.getLogin() != null) {
                    existingVykyUser.setLogin(vykyUser.getLogin());
                }
                if (vykyUser.getPassword() != null) {
                    existingVykyUser.setPassword(vykyUser.getPassword());
                }
                if (vykyUser.getEmail() != null) {
                    existingVykyUser.setEmail(vykyUser.getEmail());
                }
                if (vykyUser.getRole() != null) {
                    existingVykyUser.setRole(vykyUser.getRole());
                }
                if (vykyUser.getMessagesReadAt() != null) {
                    existingVykyUser.setMessagesReadAt(vykyUser.getMessagesReadAt());
                }
                if (vykyUser.getReactionsReadAt() != null) {
                    existingVykyUser.setReactionsReadAt(vykyUser.getReactionsReadAt());
                }
                if (vykyUser.getLastActivity() != null) {
                    existingVykyUser.setLastActivity(vykyUser.getLastActivity());
                }
                if (vykyUser.getCreatedAt() != null) {
                    existingVykyUser.setCreatedAt(vykyUser.getCreatedAt());
                }
                if (vykyUser.getUpdatedAt() != null) {
                    existingVykyUser.setUpdatedAt(vykyUser.getUpdatedAt());
                }
                if (vykyUser.getDeletedAt() != null) {
                    existingVykyUser.setDeletedAt(vykyUser.getDeletedAt());
                }

                return existingVykyUser;
            })
            .map(vykyUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vykyUser.getId().toString())
        );
    }

    /**
     * {@code GET  /vyky-users} : get all the vykyUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vykyUsers in body.
     */
    @GetMapping("")
    public List<VykyUser> getAllVykyUsers() {
        log.debug("REST request to get all VykyUsers");
        return vykyUserRepository.findAll();
    }

    /**
     * {@code GET  /vyky-users/:id} : get the "id" vykyUser.
     *
     * @param id the id of the vykyUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vykyUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VykyUser> getVykyUser(@PathVariable("id") Long id) {
        log.debug("REST request to get VykyUser : {}", id);
        Optional<VykyUser> vykyUser = vykyUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vykyUser);
    }

    /**
     * {@code DELETE  /vyky-users/:id} : delete the "id" vykyUser.
     *
     * @param id the id of the vykyUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVykyUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete VykyUser : {}", id);
        vykyUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
