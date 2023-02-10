package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BunkerReceivedNote;
import com.mycompany.myapp.repository.BunkerReceivedNoteRepository;
import com.mycompany.myapp.service.dto.BunkerReceivedNoteDTO;
import com.mycompany.myapp.service.mapper.BunkerReceivedNoteMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BunkerReceivedNoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BunkerReceivedNoteResourceIT {

    private static final ZonedDateTime DEFAULT_DOCUMENT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOCUMENT_DATE_AND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DOCUMENT_DISPLAY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_DISPLAY_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bunker-received-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BunkerReceivedNoteRepository bunkerReceivedNoteRepository;

    @Autowired
    private BunkerReceivedNoteMapper bunkerReceivedNoteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBunkerReceivedNoteMockMvc;

    private BunkerReceivedNote bunkerReceivedNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNote createEntity(EntityManager em) {
        BunkerReceivedNote bunkerReceivedNote = new BunkerReceivedNote()
            .documentDateAndTime(DEFAULT_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(DEFAULT_DOCUMENT_DISPLAY_NUMBER);
        return bunkerReceivedNote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNote createUpdatedEntity(EntityManager em) {
        BunkerReceivedNote bunkerReceivedNote = new BunkerReceivedNote()
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);
        return bunkerReceivedNote;
    }

    @BeforeEach
    public void initTest() {
        bunkerReceivedNote = createEntity(em);
    }

    @Test
    @Transactional
    void createBunkerReceivedNote() throws Exception {
        int databaseSizeBeforeCreate = bunkerReceivedNoteRepository.findAll().size();
        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);
        restBunkerReceivedNoteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeCreate + 1);
        BunkerReceivedNote testBunkerReceivedNote = bunkerReceivedNoteList.get(bunkerReceivedNoteList.size() - 1);
        assertThat(testBunkerReceivedNote.getDocumentDateAndTime()).isEqualTo(DEFAULT_DOCUMENT_DATE_AND_TIME);
        assertThat(testBunkerReceivedNote.getDocumentDisplayNumber()).isEqualTo(DEFAULT_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void createBunkerReceivedNoteWithExistingId() throws Exception {
        // Create the BunkerReceivedNote with an existing ID
        bunkerReceivedNote.setId(1L);
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        int databaseSizeBeforeCreate = bunkerReceivedNoteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBunkerReceivedNoteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentDateAndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bunkerReceivedNoteRepository.findAll().size();
        // set the field null
        bunkerReceivedNote.setDocumentDateAndTime(null);

        // Create the BunkerReceivedNote, which fails.
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        restBunkerReceivedNoteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotes() throws Exception {
        // Initialize the database
        bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bunkerReceivedNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDateAndTime").value(hasItem(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME))))
            .andExpect(jsonPath("$.[*].documentDisplayNumber").value(hasItem(DEFAULT_DOCUMENT_DISPLAY_NUMBER)));
    }

    @Test
    @Transactional
    void getBunkerReceivedNote() throws Exception {
        // Initialize the database
        bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get the bunkerReceivedNote
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, bunkerReceivedNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bunkerReceivedNote.getId().intValue()))
            .andExpect(jsonPath("$.documentDateAndTime").value(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME)))
            .andExpect(jsonPath("$.documentDisplayNumber").value(DEFAULT_DOCUMENT_DISPLAY_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingBunkerReceivedNote() throws Exception {
        // Get the bunkerReceivedNote
        restBunkerReceivedNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBunkerReceivedNote() throws Exception {
        // Initialize the database
        bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();

        // Update the bunkerReceivedNote
        BunkerReceivedNote updatedBunkerReceivedNote = bunkerReceivedNoteRepository.findById(bunkerReceivedNote.getId()).get();
        // Disconnect from session so that the updates on updatedBunkerReceivedNote are not directly saved in db
        em.detach(updatedBunkerReceivedNote);
        updatedBunkerReceivedNote
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(updatedBunkerReceivedNote);

        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bunkerReceivedNoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
        BunkerReceivedNote testBunkerReceivedNote = bunkerReceivedNoteList.get(bunkerReceivedNoteList.size() - 1);
        assertThat(testBunkerReceivedNote.getDocumentDateAndTime()).isEqualTo(UPDATED_DOCUMENT_DATE_AND_TIME);
        assertThat(testBunkerReceivedNote.getDocumentDisplayNumber()).isEqualTo(UPDATED_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingBunkerReceivedNote() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();
        bunkerReceivedNote.setId(count.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bunkerReceivedNoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBunkerReceivedNote() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();
        bunkerReceivedNote.setId(count.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBunkerReceivedNote() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();
        bunkerReceivedNote.setId(count.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBunkerReceivedNoteWithPatch() throws Exception {
        // Initialize the database
        bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();

        // Update the bunkerReceivedNote using partial update
        BunkerReceivedNote partialUpdatedBunkerReceivedNote = new BunkerReceivedNote();
        partialUpdatedBunkerReceivedNote.setId(bunkerReceivedNote.getId());

        partialUpdatedBunkerReceivedNote.documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);

        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBunkerReceivedNote))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
        BunkerReceivedNote testBunkerReceivedNote = bunkerReceivedNoteList.get(bunkerReceivedNoteList.size() - 1);
        assertThat(testBunkerReceivedNote.getDocumentDateAndTime()).isEqualTo(DEFAULT_DOCUMENT_DATE_AND_TIME);
        assertThat(testBunkerReceivedNote.getDocumentDisplayNumber()).isEqualTo(UPDATED_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateBunkerReceivedNoteWithPatch() throws Exception {
        // Initialize the database
        bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();

        // Update the bunkerReceivedNote using partial update
        BunkerReceivedNote partialUpdatedBunkerReceivedNote = new BunkerReceivedNote();
        partialUpdatedBunkerReceivedNote.setId(bunkerReceivedNote.getId());

        partialUpdatedBunkerReceivedNote
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);

        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBunkerReceivedNote))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
        BunkerReceivedNote testBunkerReceivedNote = bunkerReceivedNoteList.get(bunkerReceivedNoteList.size() - 1);
        assertThat(testBunkerReceivedNote.getDocumentDateAndTime()).isEqualTo(UPDATED_DOCUMENT_DATE_AND_TIME);
        assertThat(testBunkerReceivedNote.getDocumentDisplayNumber()).isEqualTo(UPDATED_DOCUMENT_DISPLAY_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingBunkerReceivedNote() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();
        bunkerReceivedNote.setId(count.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bunkerReceivedNoteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBunkerReceivedNote() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();
        bunkerReceivedNote.setId(count.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBunkerReceivedNote() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteRepository.findAll().size();
        bunkerReceivedNote.setId(count.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNote in the database
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBunkerReceivedNote() throws Exception {
        // Initialize the database
        bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        int databaseSizeBeforeDelete = bunkerReceivedNoteRepository.findAll().size();

        // Delete the bunkerReceivedNote
        restBunkerReceivedNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, bunkerReceivedNote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BunkerReceivedNote> bunkerReceivedNoteList = bunkerReceivedNoteRepository.findAll();
        assertThat(bunkerReceivedNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
