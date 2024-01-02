package net.vyky.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import net.vyky.IntegrationTest;
import net.vyky.domain.MessageRecipientAttrs;
import net.vyky.repository.MessageRecipientAttrsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MessageRecipientAttrsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MessageRecipientAttrsResourceIT {

    private static final Boolean DEFAULT_IS_READ = false;
    private static final Boolean UPDATED_IS_READ = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String ENTITY_API_URL = "/api/message-recipient-attrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MessageRecipientAttrsRepository messageRecipientAttrsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageRecipientAttrsMockMvc;

    private MessageRecipientAttrs messageRecipientAttrs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageRecipientAttrs createEntity(EntityManager em) {
        MessageRecipientAttrs messageRecipientAttrs = new MessageRecipientAttrs().isRead(DEFAULT_IS_READ).isDeleted(DEFAULT_IS_DELETED);
        return messageRecipientAttrs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageRecipientAttrs createUpdatedEntity(EntityManager em) {
        MessageRecipientAttrs messageRecipientAttrs = new MessageRecipientAttrs().isRead(UPDATED_IS_READ).isDeleted(UPDATED_IS_DELETED);
        return messageRecipientAttrs;
    }

    @BeforeEach
    public void initTest() {
        messageRecipientAttrs = createEntity(em);
    }

    @Test
    @Transactional
    void createMessageRecipientAttrs() throws Exception {
        int databaseSizeBeforeCreate = messageRecipientAttrsRepository.findAll().size();
        // Create the MessageRecipientAttrs
        restMessageRecipientAttrsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isCreated());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeCreate + 1);
        MessageRecipientAttrs testMessageRecipientAttrs = messageRecipientAttrsList.get(messageRecipientAttrsList.size() - 1);
        assertThat(testMessageRecipientAttrs.getIsRead()).isEqualTo(DEFAULT_IS_READ);
        assertThat(testMessageRecipientAttrs.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createMessageRecipientAttrsWithExistingId() throws Exception {
        // Create the MessageRecipientAttrs with an existing ID
        messageRecipientAttrs.setId(1L);

        int databaseSizeBeforeCreate = messageRecipientAttrsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageRecipientAttrsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMessageRecipientAttrs() throws Exception {
        // Initialize the database
        messageRecipientAttrsRepository.saveAndFlush(messageRecipientAttrs);

        // Get all the messageRecipientAttrsList
        restMessageRecipientAttrsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageRecipientAttrs.getId().intValue())))
            .andExpect(jsonPath("$.[*].isRead").value(hasItem(DEFAULT_IS_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getMessageRecipientAttrs() throws Exception {
        // Initialize the database
        messageRecipientAttrsRepository.saveAndFlush(messageRecipientAttrs);

        // Get the messageRecipientAttrs
        restMessageRecipientAttrsMockMvc
            .perform(get(ENTITY_API_URL_ID, messageRecipientAttrs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageRecipientAttrs.getId().intValue()))
            .andExpect(jsonPath("$.isRead").value(DEFAULT_IS_READ.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMessageRecipientAttrs() throws Exception {
        // Get the messageRecipientAttrs
        restMessageRecipientAttrsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMessageRecipientAttrs() throws Exception {
        // Initialize the database
        messageRecipientAttrsRepository.saveAndFlush(messageRecipientAttrs);

        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();

        // Update the messageRecipientAttrs
        MessageRecipientAttrs updatedMessageRecipientAttrs = messageRecipientAttrsRepository
            .findById(messageRecipientAttrs.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMessageRecipientAttrs are not directly saved in db
        em.detach(updatedMessageRecipientAttrs);
        updatedMessageRecipientAttrs.isRead(UPDATED_IS_READ).isDeleted(UPDATED_IS_DELETED);

        restMessageRecipientAttrsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMessageRecipientAttrs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMessageRecipientAttrs))
            )
            .andExpect(status().isOk());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
        MessageRecipientAttrs testMessageRecipientAttrs = messageRecipientAttrsList.get(messageRecipientAttrsList.size() - 1);
        assertThat(testMessageRecipientAttrs.getIsRead()).isEqualTo(UPDATED_IS_READ);
        assertThat(testMessageRecipientAttrs.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingMessageRecipientAttrs() throws Exception {
        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();
        messageRecipientAttrs.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageRecipientAttrsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageRecipientAttrs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMessageRecipientAttrs() throws Exception {
        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();
        messageRecipientAttrs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageRecipientAttrsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMessageRecipientAttrs() throws Exception {
        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();
        messageRecipientAttrs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageRecipientAttrsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMessageRecipientAttrsWithPatch() throws Exception {
        // Initialize the database
        messageRecipientAttrsRepository.saveAndFlush(messageRecipientAttrs);

        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();

        // Update the messageRecipientAttrs using partial update
        MessageRecipientAttrs partialUpdatedMessageRecipientAttrs = new MessageRecipientAttrs();
        partialUpdatedMessageRecipientAttrs.setId(messageRecipientAttrs.getId());

        restMessageRecipientAttrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageRecipientAttrs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageRecipientAttrs))
            )
            .andExpect(status().isOk());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
        MessageRecipientAttrs testMessageRecipientAttrs = messageRecipientAttrsList.get(messageRecipientAttrsList.size() - 1);
        assertThat(testMessageRecipientAttrs.getIsRead()).isEqualTo(DEFAULT_IS_READ);
        assertThat(testMessageRecipientAttrs.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateMessageRecipientAttrsWithPatch() throws Exception {
        // Initialize the database
        messageRecipientAttrsRepository.saveAndFlush(messageRecipientAttrs);

        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();

        // Update the messageRecipientAttrs using partial update
        MessageRecipientAttrs partialUpdatedMessageRecipientAttrs = new MessageRecipientAttrs();
        partialUpdatedMessageRecipientAttrs.setId(messageRecipientAttrs.getId());

        partialUpdatedMessageRecipientAttrs.isRead(UPDATED_IS_READ).isDeleted(UPDATED_IS_DELETED);

        restMessageRecipientAttrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageRecipientAttrs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageRecipientAttrs))
            )
            .andExpect(status().isOk());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
        MessageRecipientAttrs testMessageRecipientAttrs = messageRecipientAttrsList.get(messageRecipientAttrsList.size() - 1);
        assertThat(testMessageRecipientAttrs.getIsRead()).isEqualTo(UPDATED_IS_READ);
        assertThat(testMessageRecipientAttrs.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingMessageRecipientAttrs() throws Exception {
        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();
        messageRecipientAttrs.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageRecipientAttrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageRecipientAttrs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMessageRecipientAttrs() throws Exception {
        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();
        messageRecipientAttrs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageRecipientAttrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMessageRecipientAttrs() throws Exception {
        int databaseSizeBeforeUpdate = messageRecipientAttrsRepository.findAll().size();
        messageRecipientAttrs.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageRecipientAttrsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageRecipientAttrs))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageRecipientAttrs in the database
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMessageRecipientAttrs() throws Exception {
        // Initialize the database
        messageRecipientAttrsRepository.saveAndFlush(messageRecipientAttrs);

        int databaseSizeBeforeDelete = messageRecipientAttrsRepository.findAll().size();

        // Delete the messageRecipientAttrs
        restMessageRecipientAttrsMockMvc
            .perform(delete(ENTITY_API_URL_ID, messageRecipientAttrs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageRecipientAttrs> messageRecipientAttrsList = messageRecipientAttrsRepository.findAll();
        assertThat(messageRecipientAttrsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
