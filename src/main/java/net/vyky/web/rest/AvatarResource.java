package net.vyky.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.vyky.domain.Avatar;
import net.vyky.repository.AvatarRepository;
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
 * REST controller for managing {@link net.vyky.domain.Avatar}.
 */
@RestController
@RequestMapping("/api/avatars")
@Transactional
public class AvatarResource {

    private final Logger log = LoggerFactory.getLogger(AvatarResource.class);

    private static final String ENTITY_NAME = "avatar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvatarRepository avatarRepository;

    public AvatarResource(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * {@code POST  /avatars} : Create a new avatar.
     *
     * @param avatar the avatar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avatar, or with status {@code 400 (Bad Request)} if the avatar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Avatar> createAvatar(@Valid @RequestBody Avatar avatar) throws URISyntaxException {
        log.debug("REST request to save Avatar : {}", avatar);
        if (avatar.getId() != null) {
            throw new BadRequestAlertException("A new avatar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Avatar result = avatarRepository.save(avatar);
        return ResponseEntity
            .created(new URI("/api/avatars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avatars/:id} : Updates an existing avatar.
     *
     * @param id the id of the avatar to save.
     * @param avatar the avatar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avatar,
     * or with status {@code 400 (Bad Request)} if the avatar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avatar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Avatar> updateAvatar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Avatar avatar
    ) throws URISyntaxException {
        log.debug("REST request to update Avatar : {}, {}", id, avatar);
        if (avatar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avatar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avatarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Avatar result = avatarRepository.save(avatar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avatar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avatars/:id} : Partial updates given fields of an existing avatar, field will ignore if it is null
     *
     * @param id the id of the avatar to save.
     * @param avatar the avatar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avatar,
     * or with status {@code 400 (Bad Request)} if the avatar is not valid,
     * or with status {@code 404 (Not Found)} if the avatar is not found,
     * or with status {@code 500 (Internal Server Error)} if the avatar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Avatar> partialUpdateAvatar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Avatar avatar
    ) throws URISyntaxException {
        log.debug("REST request to partial update Avatar partially : {}, {}", id, avatar);
        if (avatar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avatar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avatarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Avatar> result = avatarRepository
            .findById(avatar.getId())
            .map(existingAvatar -> {
                if (avatar.getMime() != null) {
                    existingAvatar.setMime(avatar.getMime());
                }
                if (avatar.getImg() != null) {
                    existingAvatar.setImg(avatar.getImg());
                }
                if (avatar.getImgContentType() != null) {
                    existingAvatar.setImgContentType(avatar.getImgContentType());
                }
                if (avatar.getActive() != null) {
                    existingAvatar.setActive(avatar.getActive());
                }
                if (avatar.getActivationAt() != null) {
                    existingAvatar.setActivationAt(avatar.getActivationAt());
                }
                if (avatar.getCreatedAt() != null) {
                    existingAvatar.setCreatedAt(avatar.getCreatedAt());
                }
                if (avatar.getDeletedAt() != null) {
                    existingAvatar.setDeletedAt(avatar.getDeletedAt());
                }

                return existingAvatar;
            })
            .map(avatarRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avatar.getId().toString())
        );
    }

    /**
     * {@code GET  /avatars} : get all the avatars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avatars in body.
     */
    @GetMapping("")
    public List<Avatar> getAllAvatars() {
        log.debug("REST request to get all Avatars");
        return avatarRepository.findAll();
    }

    /**
     * {@code GET  /avatars/:id} : get the "id" avatar.
     *
     * @param id the id of the avatar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avatar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Avatar> getAvatar(@PathVariable("id") Long id) {
        log.debug("REST request to get Avatar : {}", id);
        Optional<Avatar> avatar = avatarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(avatar);
    }

    /**
     * {@code DELETE  /avatars/:id} : delete the "id" avatar.
     *
     * @param id the id of the avatar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable("id") Long id) {
        log.debug("REST request to delete Avatar : {}", id);
        avatarRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
