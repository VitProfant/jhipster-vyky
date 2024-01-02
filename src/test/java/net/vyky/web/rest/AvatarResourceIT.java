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
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import net.vyky.IntegrationTest;
import net.vyky.domain.Avatar;
import net.vyky.repository.AvatarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AvatarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvatarResourceIT {

    private static final String DEFAULT_MIME = "AAAAAAAAAA";
    private static final String UPDATED_MIME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_ACTIVATION_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTIVATION_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/avatars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvatarMockMvc;

    private Avatar avatar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avatar createEntity(EntityManager em) {
        Avatar avatar = new Avatar()
            .mime(DEFAULT_MIME)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .active(DEFAULT_ACTIVE)
            .activationAt(DEFAULT_ACTIVATION_AT)
            .createdAt(DEFAULT_CREATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return avatar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avatar createUpdatedEntity(EntityManager em) {
        Avatar avatar = new Avatar()
            .mime(UPDATED_MIME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .active(UPDATED_ACTIVE)
            .activationAt(UPDATED_ACTIVATION_AT)
            .createdAt(UPDATED_CREATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return avatar;
    }

    @BeforeEach
    public void initTest() {
        avatar = createEntity(em);
    }

    @Test
    @Transactional
    void createAvatar() throws Exception {
        int databaseSizeBeforeCreate = avatarRepository.findAll().size();
        // Create the Avatar
        restAvatarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isCreated());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeCreate + 1);
        Avatar testAvatar = avatarList.get(avatarList.size() - 1);
        assertThat(testAvatar.getMime()).isEqualTo(DEFAULT_MIME);
        assertThat(testAvatar.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testAvatar.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testAvatar.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAvatar.getActivationAt()).isEqualTo(DEFAULT_ACTIVATION_AT);
        assertThat(testAvatar.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAvatar.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    void createAvatarWithExistingId() throws Exception {
        // Create the Avatar with an existing ID
        avatar.setId(1L);

        int databaseSizeBeforeCreate = avatarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvatarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isBadRequest());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avatarRepository.findAll().size();
        // set the field null
        avatar.setMime(null);

        // Create the Avatar, which fails.

        restAvatarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isBadRequest());

        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = avatarRepository.findAll().size();
        // set the field null
        avatar.setCreatedAt(null);

        // Create the Avatar, which fails.

        restAvatarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isBadRequest());

        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAvatars() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        // Get all the avatarList
        restAvatarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avatar.getId().intValue())))
            .andExpect(jsonPath("$.[*].mime").value(hasItem(DEFAULT_MIME)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].activationAt").value(hasItem(sameInstant(DEFAULT_ACTIVATION_AT))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(sameInstant(DEFAULT_DELETED_AT))));
    }

    @Test
    @Transactional
    void getAvatar() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        // Get the avatar
        restAvatarMockMvc
            .perform(get(ENTITY_API_URL_ID, avatar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avatar.getId().intValue()))
            .andExpect(jsonPath("$.mime").value(DEFAULT_MIME))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64.getEncoder().encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.activationAt").value(sameInstant(DEFAULT_ACTIVATION_AT)))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.deletedAt").value(sameInstant(DEFAULT_DELETED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingAvatar() throws Exception {
        // Get the avatar
        restAvatarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAvatar() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();

        // Update the avatar
        Avatar updatedAvatar = avatarRepository.findById(avatar.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAvatar are not directly saved in db
        em.detach(updatedAvatar);
        updatedAvatar
            .mime(UPDATED_MIME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .active(UPDATED_ACTIVE)
            .activationAt(UPDATED_ACTIVATION_AT)
            .createdAt(UPDATED_CREATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restAvatarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAvatar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAvatar))
            )
            .andExpect(status().isOk());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
        Avatar testAvatar = avatarList.get(avatarList.size() - 1);
        assertThat(testAvatar.getMime()).isEqualTo(UPDATED_MIME);
        assertThat(testAvatar.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testAvatar.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testAvatar.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAvatar.getActivationAt()).isEqualTo(UPDATED_ACTIVATION_AT);
        assertThat(testAvatar.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAvatar.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void putNonExistingAvatar() throws Exception {
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();
        avatar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvatarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avatar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvatar() throws Exception {
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();
        avatar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvatarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvatar() throws Exception {
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();
        avatar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvatarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvatarWithPatch() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();

        // Update the avatar using partial update
        Avatar partialUpdatedAvatar = new Avatar();
        partialUpdatedAvatar.setId(avatar.getId());

        partialUpdatedAvatar.mime(UPDATED_MIME).active(UPDATED_ACTIVE).createdAt(UPDATED_CREATED_AT);

        restAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvatar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvatar))
            )
            .andExpect(status().isOk());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
        Avatar testAvatar = avatarList.get(avatarList.size() - 1);
        assertThat(testAvatar.getMime()).isEqualTo(UPDATED_MIME);
        assertThat(testAvatar.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testAvatar.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testAvatar.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAvatar.getActivationAt()).isEqualTo(DEFAULT_ACTIVATION_AT);
        assertThat(testAvatar.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAvatar.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    void fullUpdateAvatarWithPatch() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();

        // Update the avatar using partial update
        Avatar partialUpdatedAvatar = new Avatar();
        partialUpdatedAvatar.setId(avatar.getId());

        partialUpdatedAvatar
            .mime(UPDATED_MIME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .active(UPDATED_ACTIVE)
            .activationAt(UPDATED_ACTIVATION_AT)
            .createdAt(UPDATED_CREATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvatar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvatar))
            )
            .andExpect(status().isOk());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
        Avatar testAvatar = avatarList.get(avatarList.size() - 1);
        assertThat(testAvatar.getMime()).isEqualTo(UPDATED_MIME);
        assertThat(testAvatar.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testAvatar.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testAvatar.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAvatar.getActivationAt()).isEqualTo(UPDATED_ACTIVATION_AT);
        assertThat(testAvatar.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAvatar.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingAvatar() throws Exception {
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();
        avatar.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avatar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvatar() throws Exception {
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();
        avatar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvatarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avatar))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvatar() throws Exception {
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();
        avatar.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvatarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvatar() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        int databaseSizeBeforeDelete = avatarRepository.findAll().size();

        // Delete the avatar
        restAvatarMockMvc
            .perform(delete(ENTITY_API_URL_ID, avatar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
