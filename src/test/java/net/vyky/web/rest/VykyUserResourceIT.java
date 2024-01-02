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
import net.vyky.domain.VykyUser;
import net.vyky.domain.enumeration.Role;
import net.vyky.repository.VykyUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VykyUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VykyUserResourceIT {

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Role DEFAULT_ROLE = Role.ADMIN;
    private static final Role UPDATED_ROLE = Role.MODERATOR;

    private static final ZonedDateTime DEFAULT_MESSAGES_READ_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MESSAGES_READ_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_REACTIONS_READ_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REACTIONS_READ_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_ACTIVITY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_ACTIVITY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/vyky-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VykyUserRepository vykyUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVykyUserMockMvc;

    private VykyUser vykyUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VykyUser createEntity(EntityManager em) {
        VykyUser vykyUser = new VykyUser()
            .version(DEFAULT_VERSION)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .role(DEFAULT_ROLE)
            .messagesReadAt(DEFAULT_MESSAGES_READ_AT)
            .reactionsReadAt(DEFAULT_REACTIONS_READ_AT)
            .lastActivity(DEFAULT_LAST_ACTIVITY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return vykyUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VykyUser createUpdatedEntity(EntityManager em) {
        VykyUser vykyUser = new VykyUser()
            .version(UPDATED_VERSION)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .role(UPDATED_ROLE)
            .messagesReadAt(UPDATED_MESSAGES_READ_AT)
            .reactionsReadAt(UPDATED_REACTIONS_READ_AT)
            .lastActivity(UPDATED_LAST_ACTIVITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return vykyUser;
    }

    @BeforeEach
    public void initTest() {
        vykyUser = createEntity(em);
    }

    @Test
    @Transactional
    void createVykyUser() throws Exception {
        int databaseSizeBeforeCreate = vykyUserRepository.findAll().size();
        // Create the VykyUser
        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isCreated());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeCreate + 1);
        VykyUser testVykyUser = vykyUserList.get(vykyUserList.size() - 1);
        assertThat(testVykyUser.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testVykyUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testVykyUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testVykyUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVykyUser.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testVykyUser.getMessagesReadAt()).isEqualTo(DEFAULT_MESSAGES_READ_AT);
        assertThat(testVykyUser.getReactionsReadAt()).isEqualTo(DEFAULT_REACTIONS_READ_AT);
        assertThat(testVykyUser.getLastActivity()).isEqualTo(DEFAULT_LAST_ACTIVITY);
        assertThat(testVykyUser.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testVykyUser.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testVykyUser.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    void createVykyUserWithExistingId() throws Exception {
        // Create the VykyUser with an existing ID
        vykyUser.setId(1L);

        int databaseSizeBeforeCreate = vykyUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isBadRequest());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vykyUserRepository.findAll().size();
        // set the field null
        vykyUser.setVersion(null);

        // Create the VykyUser, which fails.

        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isBadRequest());

        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = vykyUserRepository.findAll().size();
        // set the field null
        vykyUser.setLogin(null);

        // Create the VykyUser, which fails.

        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isBadRequest());

        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = vykyUserRepository.findAll().size();
        // set the field null
        vykyUser.setPassword(null);

        // Create the VykyUser, which fails.

        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isBadRequest());

        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = vykyUserRepository.findAll().size();
        // set the field null
        vykyUser.setEmail(null);

        // Create the VykyUser, which fails.

        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isBadRequest());

        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = vykyUserRepository.findAll().size();
        // set the field null
        vykyUser.setRole(null);

        // Create the VykyUser, which fails.

        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isBadRequest());

        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = vykyUserRepository.findAll().size();
        // set the field null
        vykyUser.setCreatedAt(null);

        // Create the VykyUser, which fails.

        restVykyUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isBadRequest());

        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVykyUsers() throws Exception {
        // Initialize the database
        vykyUserRepository.saveAndFlush(vykyUser);

        // Get all the vykyUserList
        restVykyUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vykyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].messagesReadAt").value(hasItem(sameInstant(DEFAULT_MESSAGES_READ_AT))))
            .andExpect(jsonPath("$.[*].reactionsReadAt").value(hasItem(sameInstant(DEFAULT_REACTIONS_READ_AT))))
            .andExpect(jsonPath("$.[*].lastActivity").value(hasItem(sameInstant(DEFAULT_LAST_ACTIVITY))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(sameInstant(DEFAULT_DELETED_AT))));
    }

    @Test
    @Transactional
    void getVykyUser() throws Exception {
        // Initialize the database
        vykyUserRepository.saveAndFlush(vykyUser);

        // Get the vykyUser
        restVykyUserMockMvc
            .perform(get(ENTITY_API_URL_ID, vykyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vykyUser.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.messagesReadAt").value(sameInstant(DEFAULT_MESSAGES_READ_AT)))
            .andExpect(jsonPath("$.reactionsReadAt").value(sameInstant(DEFAULT_REACTIONS_READ_AT)))
            .andExpect(jsonPath("$.lastActivity").value(sameInstant(DEFAULT_LAST_ACTIVITY)))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.deletedAt").value(sameInstant(DEFAULT_DELETED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingVykyUser() throws Exception {
        // Get the vykyUser
        restVykyUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVykyUser() throws Exception {
        // Initialize the database
        vykyUserRepository.saveAndFlush(vykyUser);

        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();

        // Update the vykyUser
        VykyUser updatedVykyUser = vykyUserRepository.findById(vykyUser.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVykyUser are not directly saved in db
        em.detach(updatedVykyUser);
        updatedVykyUser
            .version(UPDATED_VERSION)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .role(UPDATED_ROLE)
            .messagesReadAt(UPDATED_MESSAGES_READ_AT)
            .reactionsReadAt(UPDATED_REACTIONS_READ_AT)
            .lastActivity(UPDATED_LAST_ACTIVITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restVykyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVykyUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVykyUser))
            )
            .andExpect(status().isOk());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
        VykyUser testVykyUser = vykyUserList.get(vykyUserList.size() - 1);
        assertThat(testVykyUser.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testVykyUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testVykyUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testVykyUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVykyUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testVykyUser.getMessagesReadAt()).isEqualTo(UPDATED_MESSAGES_READ_AT);
        assertThat(testVykyUser.getReactionsReadAt()).isEqualTo(UPDATED_REACTIONS_READ_AT);
        assertThat(testVykyUser.getLastActivity()).isEqualTo(UPDATED_LAST_ACTIVITY);
        assertThat(testVykyUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testVykyUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testVykyUser.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void putNonExistingVykyUser() throws Exception {
        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();
        vykyUser.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVykyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vykyUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vykyUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVykyUser() throws Exception {
        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();
        vykyUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVykyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vykyUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVykyUser() throws Exception {
        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();
        vykyUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVykyUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVykyUserWithPatch() throws Exception {
        // Initialize the database
        vykyUserRepository.saveAndFlush(vykyUser);

        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();

        // Update the vykyUser using partial update
        VykyUser partialUpdatedVykyUser = new VykyUser();
        partialUpdatedVykyUser.setId(vykyUser.getId());

        partialUpdatedVykyUser
            .version(UPDATED_VERSION)
            .login(UPDATED_LOGIN)
            .role(UPDATED_ROLE)
            .reactionsReadAt(UPDATED_REACTIONS_READ_AT)
            .lastActivity(UPDATED_LAST_ACTIVITY);

        restVykyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVykyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVykyUser))
            )
            .andExpect(status().isOk());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
        VykyUser testVykyUser = vykyUserList.get(vykyUserList.size() - 1);
        assertThat(testVykyUser.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testVykyUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testVykyUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testVykyUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVykyUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testVykyUser.getMessagesReadAt()).isEqualTo(DEFAULT_MESSAGES_READ_AT);
        assertThat(testVykyUser.getReactionsReadAt()).isEqualTo(UPDATED_REACTIONS_READ_AT);
        assertThat(testVykyUser.getLastActivity()).isEqualTo(UPDATED_LAST_ACTIVITY);
        assertThat(testVykyUser.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testVykyUser.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testVykyUser.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    void fullUpdateVykyUserWithPatch() throws Exception {
        // Initialize the database
        vykyUserRepository.saveAndFlush(vykyUser);

        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();

        // Update the vykyUser using partial update
        VykyUser partialUpdatedVykyUser = new VykyUser();
        partialUpdatedVykyUser.setId(vykyUser.getId());

        partialUpdatedVykyUser
            .version(UPDATED_VERSION)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .role(UPDATED_ROLE)
            .messagesReadAt(UPDATED_MESSAGES_READ_AT)
            .reactionsReadAt(UPDATED_REACTIONS_READ_AT)
            .lastActivity(UPDATED_LAST_ACTIVITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restVykyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVykyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVykyUser))
            )
            .andExpect(status().isOk());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
        VykyUser testVykyUser = vykyUserList.get(vykyUserList.size() - 1);
        assertThat(testVykyUser.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testVykyUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testVykyUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testVykyUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVykyUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testVykyUser.getMessagesReadAt()).isEqualTo(UPDATED_MESSAGES_READ_AT);
        assertThat(testVykyUser.getReactionsReadAt()).isEqualTo(UPDATED_REACTIONS_READ_AT);
        assertThat(testVykyUser.getLastActivity()).isEqualTo(UPDATED_LAST_ACTIVITY);
        assertThat(testVykyUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testVykyUser.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testVykyUser.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingVykyUser() throws Exception {
        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();
        vykyUser.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVykyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vykyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vykyUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVykyUser() throws Exception {
        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();
        vykyUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVykyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vykyUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVykyUser() throws Exception {
        int databaseSizeBeforeUpdate = vykyUserRepository.findAll().size();
        vykyUser.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVykyUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vykyUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VykyUser in the database
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVykyUser() throws Exception {
        // Initialize the database
        vykyUserRepository.saveAndFlush(vykyUser);

        int databaseSizeBeforeDelete = vykyUserRepository.findAll().size();

        // Delete the vykyUser
        restVykyUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, vykyUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VykyUser> vykyUserList = vykyUserRepository.findAll();
        assertThat(vykyUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
