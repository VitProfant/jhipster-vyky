package net.vyky.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.vyky.domain.Thumb;
import net.vyky.repository.ThumbRepository;
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
 * REST controller for managing {@link net.vyky.domain.Thumb}.
 */
@RestController
@RequestMapping("/api/thumbs")
@Transactional
public class ThumbResource {

    private final Logger log = LoggerFactory.getLogger(ThumbResource.class);

    private static final String ENTITY_NAME = "thumb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThumbRepository thumbRepository;

    public ThumbResource(ThumbRepository thumbRepository) {
        this.thumbRepository = thumbRepository;
    }

    /**
     * {@code POST  /thumbs} : Create a new thumb.
     *
     * @param thumb the thumb to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thumb, or with status {@code 400 (Bad Request)} if the thumb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Thumb> createThumb(@Valid @RequestBody Thumb thumb) throws URISyntaxException {
        log.debug("REST request to save Thumb : {}", thumb);
        if (thumb.getId() != null) {
            throw new BadRequestAlertException("A new thumb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thumb result = thumbRepository.save(thumb);
        return ResponseEntity
            .created(new URI("/api/thumbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thumbs/:id} : Updates an existing thumb.
     *
     * @param id the id of the thumb to save.
     * @param thumb the thumb to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thumb,
     * or with status {@code 400 (Bad Request)} if the thumb is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thumb couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Thumb> updateThumb(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Thumb thumb)
        throws URISyntaxException {
        log.debug("REST request to update Thumb : {}, {}", id, thumb);
        if (thumb.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thumb.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thumbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Thumb result = thumbRepository.save(thumb);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thumb.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thumbs/:id} : Partial updates given fields of an existing thumb, field will ignore if it is null
     *
     * @param id the id of the thumb to save.
     * @param thumb the thumb to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thumb,
     * or with status {@code 400 (Bad Request)} if the thumb is not valid,
     * or with status {@code 404 (Not Found)} if the thumb is not found,
     * or with status {@code 500 (Internal Server Error)} if the thumb couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Thumb> partialUpdateThumb(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Thumb thumb
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thumb partially : {}, {}", id, thumb);
        if (thumb.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thumb.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thumbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thumb> result = thumbRepository
            .findById(thumb.getId())
            .map(existingThumb -> {
                if (thumb.getVersion() != null) {
                    existingThumb.setVersion(thumb.getVersion());
                }
                if (thumb.getUp() != null) {
                    existingThumb.setUp(thumb.getUp());
                }
                if (thumb.getCreatedAt() != null) {
                    existingThumb.setCreatedAt(thumb.getCreatedAt());
                }
                if (thumb.getUpdatedAt() != null) {
                    existingThumb.setUpdatedAt(thumb.getUpdatedAt());
                }
                if (thumb.getDeletedAt() != null) {
                    existingThumb.setDeletedAt(thumb.getDeletedAt());
                }

                return existingThumb;
            })
            .map(thumbRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thumb.getId().toString())
        );
    }

    /**
     * {@code GET  /thumbs} : get all the thumbs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thumbs in body.
     */
    @GetMapping("")
    public List<Thumb> getAllThumbs() {
        log.debug("REST request to get all Thumbs");
        return thumbRepository.findAll();
    }

    /**
     * {@code GET  /thumbs/:id} : get the "id" thumb.
     *
     * @param id the id of the thumb to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thumb, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Thumb> getThumb(@PathVariable("id") Long id) {
        log.debug("REST request to get Thumb : {}", id);
        Optional<Thumb> thumb = thumbRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thumb);
    }

    /**
     * {@code DELETE  /thumbs/:id} : delete the "id" thumb.
     *
     * @param id the id of the thumb to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThumb(@PathVariable("id") Long id) {
        log.debug("REST request to delete Thumb : {}", id);
        thumbRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
