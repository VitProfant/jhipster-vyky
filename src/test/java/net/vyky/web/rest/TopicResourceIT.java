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
import net.vyky.domain.Topic;
import net.vyky.domain.enumeration.PostStatus;
import net.vyky.domain.enumeration.Role;
import net.vyky.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TopicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TopicResourceIT {

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Role DEFAULT_MINIMAL_ROLE = Role.ADMIN;
    private static final Role UPDATED_MINIMAL_ROLE = Role.MODERATOR;

    private static final PostStatus DEFAULT_STATUS = PostStatus.REGULAR;
    private static final PostStatus UPDATED_STATUS = PostStatus.HIDDEN;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELETED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/topics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopicMockMvc;

    private Topic topic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topic createEntity(EntityManager em) {
        Topic topic = new Topic()
            .version(DEFAULT_VERSION)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .minimalRole(DEFAULT_MINIMAL_ROLE)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .deletedAt(DEFAULT_DELETED_AT);
        return topic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topic createUpdatedEntity(EntityManager em) {
        Topic topic = new Topic()
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .minimalRole(UPDATED_MINIMAL_ROLE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);
        return topic;
    }

    @BeforeEach
    public void initTest() {
        topic = createEntity(em);
    }

    @Test
    @Transactional
    void createTopic() throws Exception {
        int databaseSizeBeforeCreate = topicRepository.findAll().size();
        // Create the Topic
        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isCreated());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeCreate + 1);
        Topic testTopic = topicList.get(topicList.size() - 1);
        assertThat(testTopic.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testTopic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTopic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTopic.getMinimalRole()).isEqualTo(DEFAULT_MINIMAL_ROLE);
        assertThat(testTopic.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTopic.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTopic.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testTopic.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    void createTopicWithExistingId() throws Exception {
        // Create the Topic with an existing ID
        topic.setId(1L);

        int databaseSizeBeforeCreate = topicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicRepository.findAll().size();
        // set the field null
        topic.setVersion(null);

        // Create the Topic, which fails.

        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isBadRequest());

        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicRepository.findAll().size();
        // set the field null
        topic.setName(null);

        // Create the Topic, which fails.

        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isBadRequest());

        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicRepository.findAll().size();
        // set the field null
        topic.setDescription(null);

        // Create the Topic, which fails.

        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isBadRequest());

        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicRepository.findAll().size();
        // set the field null
        topic.setStatus(null);

        // Create the Topic, which fails.

        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isBadRequest());

        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = topicRepository.findAll().size();
        // set the field null
        topic.setCreatedAt(null);

        // Create the Topic, which fails.

        restTopicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isBadRequest());

        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTopics() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        // Get all the topicList
        restTopicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topic.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].minimalRole").value(hasItem(DEFAULT_MINIMAL_ROLE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(sameInstant(DEFAULT_DELETED_AT))));
    }

    @Test
    @Transactional
    void getTopic() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        // Get the topic
        restTopicMockMvc
            .perform(get(ENTITY_API_URL_ID, topic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topic.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.minimalRole").value(DEFAULT_MINIMAL_ROLE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.deletedAt").value(sameInstant(DEFAULT_DELETED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingTopic() throws Exception {
        // Get the topic
        restTopicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTopic() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        int databaseSizeBeforeUpdate = topicRepository.findAll().size();

        // Update the topic
        Topic updatedTopic = topicRepository.findById(topic.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTopic are not directly saved in db
        em.detach(updatedTopic);
        updatedTopic
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .minimalRole(UPDATED_MINIMAL_ROLE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTopic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTopic))
            )
            .andExpect(status().isOk());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
        Topic testTopic = topicList.get(topicList.size() - 1);
        assertThat(testTopic.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTopic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTopic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTopic.getMinimalRole()).isEqualTo(UPDATED_MINIMAL_ROLE);
        assertThat(testTopic.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTopic.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTopic.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testTopic.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTopic() throws Exception {
        int databaseSizeBeforeUpdate = topicRepository.findAll().size();
        topic.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, topic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTopic() throws Exception {
        int databaseSizeBeforeUpdate = topicRepository.findAll().size();
        topic.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTopic() throws Exception {
        int databaseSizeBeforeUpdate = topicRepository.findAll().size();
        topic.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTopicWithPatch() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        int databaseSizeBeforeUpdate = topicRepository.findAll().size();

        // Update the topic using partial update
        Topic partialUpdatedTopic = new Topic();
        partialUpdatedTopic.setId(topic.getId());

        partialUpdatedTopic
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .minimalRole(UPDATED_MINIMAL_ROLE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTopic))
            )
            .andExpect(status().isOk());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
        Topic testTopic = topicList.get(topicList.size() - 1);
        assertThat(testTopic.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTopic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTopic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTopic.getMinimalRole()).isEqualTo(UPDATED_MINIMAL_ROLE);
        assertThat(testTopic.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTopic.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTopic.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testTopic.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTopicWithPatch() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        int databaseSizeBeforeUpdate = topicRepository.findAll().size();

        // Update the topic using partial update
        Topic partialUpdatedTopic = new Topic();
        partialUpdatedTopic.setId(topic.getId());

        partialUpdatedTopic
            .version(UPDATED_VERSION)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .minimalRole(UPDATED_MINIMAL_ROLE)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .deletedAt(UPDATED_DELETED_AT);

        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTopic))
            )
            .andExpect(status().isOk());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
        Topic testTopic = topicList.get(topicList.size() - 1);
        assertThat(testTopic.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testTopic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTopic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTopic.getMinimalRole()).isEqualTo(UPDATED_MINIMAL_ROLE);
        assertThat(testTopic.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTopic.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTopic.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testTopic.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTopic() throws Exception {
        int databaseSizeBeforeUpdate = topicRepository.findAll().size();
        topic.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, topic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(topic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTopic() throws Exception {
        int databaseSizeBeforeUpdate = topicRepository.findAll().size();
        topic.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(topic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTopic() throws Exception {
        int databaseSizeBeforeUpdate = topicRepository.findAll().size();
        topic.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopicMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(topic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Topic in the database
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTopic() throws Exception {
        // Initialize the database
        topicRepository.saveAndFlush(topic);

        int databaseSizeBeforeDelete = topicRepository.findAll().size();

        // Delete the topic
        restTopicMockMvc
            .perform(delete(ENTITY_API_URL_ID, topic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Topic> topicList = topicRepository.findAll();
        assertThat(topicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
