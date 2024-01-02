package net.vyky.web.rest;

import static net.vyky.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import net.vyky.IntegrationTest;
import net.vyky.domain.Thumb;
import net.vyky.repository.ThumbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ThumbResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThumbResourceIT {

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Boolean DEFAULT_UP = false;
    private static final Boolean UPDATED_UP = true;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/thumbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThumbRepository thumbRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThumbMockMvc;

    private Thumb thumb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thumb createEntity(EntityManager em) {
        Thumb thumb = new Thumb()
            .version(DEFAULT_VERSION)
            .up(DEFAULT_UP)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return thumb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thumb createUpdatedEntity(EntityManager em) {
        Thumb thumb = new Thumb()
            .version(UPDATED_VERSION)
            .up(UPDATED_UP)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return thumb;
    }

    @BeforeEach
    public void initTest() {
        thumb = createEntity(em);
    }

    @Test
    @Transactional
    void createThumb() throws Exception {
        int databaseSizeBeforeCreate = thumbRepository.findAll().size();
        // Create the Thumb
        restThumbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumb)))
            .andExpect(status().isCreated());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeCreate + 1);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testThumb.getUp()).isEqualTo(DEFAULT_UP);
        assertThat(testThumb.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testThumb.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testThumb.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    void createThumbWithExistingId() throws Exception {
        // Create the Thumb with an existing ID
        thumb.setId(1L);

        int databaseSizeBeforeCreate = thumbRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThumbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumb)))
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbRepository.findAll().size();
        // set the field null
        thumb.setVersion(null);

        // Create the Thumb, which fails.

        restThumbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumb)))
            .andExpect(status().isBadRequest());

        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbRepository.findAll().size();
        // set the field null
        thumb.setUp(null);

        // Create the Thumb, which fails.

        restThumbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumb)))
            .andExpect(status().isBadRequest());

        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbRepository.findAll().size();
        // set the field null
        thumb.setCreatedAt(null);

        // Create the Thumb, which fails.

        restThumbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumb)))
            .andExpect(status().isBadRequest());

        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThumbs() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList
        restThumbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thumb.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].up").value(hasItem(DEFAULT_UP.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(sameInstant(DEFAULT_DELETED_AT))));
    }

    @Test
    @Transactional
    void getThumb() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get the thumb
        restThumbMockMvc
            .perform(get(ENTITY_API_URL_ID, thumb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thumb.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.up").value(DEFAULT_UP.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.deletedAt").value(sameInstant(DEFAULT_DELETED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingThumb() throws Exception {
        // Get the thumb
        restThumbMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThumb() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb
        Thumb updatedThumb = thumbRepository.findById(thumb.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedThumb are not directly saved in db
        em.detach(updatedThumb);
        updatedThumb
            .version(UPDATED_VERSION)
            .up(UPDATED_UP)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restThumbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThumb.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedThumb))
            )
            .andExpect(status().isOk());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testThumb.getUp()).isEqualTo(UPDATED_UP);
        assertThat(testThumb.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testThumb.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testThumb.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void putNonExistingThumb() throws Exception {
        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();
        thumb.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThumbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thumb.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumb))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThumb() throws Exception {
        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();
        thumb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThumbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumb))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThumb() throws Exception {
        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();
        thumb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThumbMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumb)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThumbWithPatch() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb using partial update
        Thumb partialUpdatedThumb = new Thumb();
        partialUpdatedThumb.setId(thumb.getId());

        partialUpdatedThumb.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT).deletedAt(UPDATED_DELETED_AT);

        restThumbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThumb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThumb))
            )
            .andExpect(status().isOk());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testThumb.getUp()).isEqualTo(DEFAULT_UP);
        assertThat(testThumb.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testThumb.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testThumb.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void fullUpdateThumbWithPatch() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb using partial update
        Thumb partialUpdatedThumb = new Thumb();
        partialUpdatedThumb.setId(thumb.getId());

        partialUpdatedThumb
            .version(UPDATED_VERSION)
            .up(UPDATED_UP)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restThumbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThumb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThumb))
            )
            .andExpect(status().isOk());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testThumb.getUp()).isEqualTo(UPDATED_UP);
        assertThat(testThumb.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testThumb.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testThumb.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingThumb() throws Exception {
        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();
        thumb.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThumbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thumb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thumb))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThumb() throws Exception {
        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();
        thumb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThumbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thumb))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThumb() throws Exception {
        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();
        thumb.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThumbMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(thumb)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThumb() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeDelete = thumbRepository.findAll().size();

        // Delete the thumb
        restThumbMockMvc
            .perform(delete(ENTITY_API_URL_ID, thumb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
