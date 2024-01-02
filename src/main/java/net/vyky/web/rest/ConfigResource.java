package net.vyky.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.vyky.domain.Config;
import net.vyky.repository.ConfigRepository;
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
 * REST controller for managing {@link net.vyky.domain.Config}.
 */
@RestController
@RequestMapping("/api/configs")
@Transactional
public class ConfigResource {

    private final Logger log = LoggerFactory.getLogger(ConfigResource.class);

    private static final String ENTITY_NAME = "config";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigRepository configRepository;

    public ConfigResource(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    /**
     * {@code POST  /configs} : Create a new config.
     *
     * @param config the config to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new config, or with status {@code 400 (Bad Request)} if the config has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Config> createConfig(@Valid @RequestBody Config config) throws URISyntaxException {
        log.debug("REST request to save Config : {}", config);
        if (config.getId() != null) {
            throw new BadRequestAlertException("A new config cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Config result = configRepository.save(config);
        return ResponseEntity
            .created(new URI("/api/configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /configs/:id} : Updates an existing config.
     *
     * @param id the id of the config to save.
     * @param config the config to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated config,
     * or with status {@code 400 (Bad Request)} if the config is not valid,
     * or with status {@code 500 (Internal Server Error)} if the config couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Config> updateConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Config config
    ) throws URISyntaxException {
        log.debug("REST request to update Config : {}, {}", id, config);
        if (config.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, config.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Config result = configRepository.save(config);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, config.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /configs/:id} : Partial updates given fields of an existing config, field will ignore if it is null
     *
     * @param id the id of the config to save.
     * @param config the config to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated config,
     * or with status {@code 400 (Bad Request)} if the config is not valid,
     * or with status {@code 404 (Not Found)} if the config is not found,
     * or with status {@code 500 (Internal Server Error)} if the config couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Config> partialUpdateConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Config config
    ) throws URISyntaxException {
        log.debug("REST request to partial update Config partially : {}, {}", id, config);
        if (config.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, config.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Config> result = configRepository
            .findById(config.getId())
            .map(existingConfig -> {
                if (config.getConfigItem() != null) {
                    existingConfig.setConfigItem(config.getConfigItem());
                }
                if (config.getVersion() != null) {
                    existingConfig.setVersion(config.getVersion());
                }
                if (config.getValue() != null) {
                    existingConfig.setValue(config.getValue());
                }
                if (config.getCreatedAt() != null) {
                    existingConfig.setCreatedAt(config.getCreatedAt());
                }
                if (config.getUpdatedAt() != null) {
                    existingConfig.setUpdatedAt(config.getUpdatedAt());
                }
                if (config.getDeletedAt() != null) {
                    existingConfig.setDeletedAt(config.getDeletedAt());
                }

                return existingConfig;
            })
            .map(configRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, config.getId().toString())
        );
    }

    /**
     * {@code GET  /configs} : get all the configs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configs in body.
     */
    @GetMapping("")
    public List<Config> getAllConfigs() {
        log.debug("REST request to get all Configs");
        return configRepository.findAll();
    }

    /**
     * {@code GET  /configs/:id} : get the "id" config.
     *
     * @param id the id of the config to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the config, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Config> getConfig(@PathVariable("id") Long id) {
        log.debug("REST request to get Config : {}", id);
        Optional<Config> config = configRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(config);
    }

    /**
     * {@code DELETE  /configs/:id} : delete the "id" config.
     *
     * @param id the id of the config to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable("id") Long id) {
        log.debug("REST request to delete Config : {}", id);
        configRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
