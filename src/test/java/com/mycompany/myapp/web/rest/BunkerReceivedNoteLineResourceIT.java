package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BunkerReceivedNoteLine;
import com.mycompany.myapp.repository.BunkerReceivedNoteLineRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link BunkerReceivedNoteLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BunkerReceivedNoteLineResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/bunker-received-note-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBunkerReceivedNoteLineMockMvc;

    private BunkerReceivedNoteLine bunkerReceivedNoteLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNoteLine createEntity(EntityManager em) {
        BunkerReceivedNoteLine bunkerReceivedNoteLine = new BunkerReceivedNoteLine().quantity(DEFAULT_QUANTITY);
        return bunkerReceivedNoteLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNoteLine createUpdatedEntity(EntityManager em) {
        BunkerReceivedNoteLine bunkerReceivedNoteLine = new BunkerReceivedNoteLine().quantity(UPDATED_QUANTITY);
        return bunkerReceivedNoteLine;
    }

    @BeforeEach
    public void initTest() {
        bunkerReceivedNoteLine = createEntity(em);
    }

    @Test
    @Transactional
    void createBunkerReceivedNoteLine() throws Exception {
        int databaseSizeBeforeCreate = bunkerReceivedNoteLineRepository.findAll().size();
        // Create the BunkerReceivedNoteLine
        restBunkerReceivedNoteLineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isCreated());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeCreate + 1);
        BunkerReceivedNoteLine testBunkerReceivedNoteLine = bunkerReceivedNoteLineList.get(bunkerReceivedNoteLineList.size() - 1);
        assertThat(testBunkerReceivedNoteLine.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createBunkerReceivedNoteLineWithExistingId() throws Exception {
        // Create the BunkerReceivedNoteLine with an existing ID
        bunkerReceivedNoteLine.setId(1L);

        int databaseSizeBeforeCreate = bunkerReceivedNoteLineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBunkerReceivedNoteLineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLines() throws Exception {
        // Initialize the database
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bunkerReceivedNoteLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))));
    }

    @Test
    @Transactional
    void getBunkerReceivedNoteLine() throws Exception {
        // Initialize the database
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get the bunkerReceivedNoteLine
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL_ID, bunkerReceivedNoteLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bunkerReceivedNoteLine.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    void getNonExistingBunkerReceivedNoteLine() throws Exception {
        // Get the bunkerReceivedNoteLine
        restBunkerReceivedNoteLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBunkerReceivedNoteLine() throws Exception {
        // Initialize the database
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();

        // Update the bunkerReceivedNoteLine
        BunkerReceivedNoteLine updatedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository
            .findById(bunkerReceivedNoteLine.getId())
            .get();
        // Disconnect from session so that the updates on updatedBunkerReceivedNoteLine are not directly saved in db
        em.detach(updatedBunkerReceivedNoteLine);
        updatedBunkerReceivedNoteLine.quantity(UPDATED_QUANTITY);

        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBunkerReceivedNoteLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBunkerReceivedNoteLine))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
        BunkerReceivedNoteLine testBunkerReceivedNoteLine = bunkerReceivedNoteLineList.get(bunkerReceivedNoteLineList.size() - 1);
        assertThat(testBunkerReceivedNoteLine.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingBunkerReceivedNoteLine() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();
        bunkerReceivedNoteLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bunkerReceivedNoteLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBunkerReceivedNoteLine() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();
        bunkerReceivedNoteLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBunkerReceivedNoteLine() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();
        bunkerReceivedNoteLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBunkerReceivedNoteLineWithPatch() throws Exception {
        // Initialize the database
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();

        // Update the bunkerReceivedNoteLine using partial update
        BunkerReceivedNoteLine partialUpdatedBunkerReceivedNoteLine = new BunkerReceivedNoteLine();
        partialUpdatedBunkerReceivedNoteLine.setId(bunkerReceivedNoteLine.getId());

        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNoteLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBunkerReceivedNoteLine))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
        BunkerReceivedNoteLine testBunkerReceivedNoteLine = bunkerReceivedNoteLineList.get(bunkerReceivedNoteLineList.size() - 1);
        assertThat(testBunkerReceivedNoteLine.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateBunkerReceivedNoteLineWithPatch() throws Exception {
        // Initialize the database
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();

        // Update the bunkerReceivedNoteLine using partial update
        BunkerReceivedNoteLine partialUpdatedBunkerReceivedNoteLine = new BunkerReceivedNoteLine();
        partialUpdatedBunkerReceivedNoteLine.setId(bunkerReceivedNoteLine.getId());

        partialUpdatedBunkerReceivedNoteLine.quantity(UPDATED_QUANTITY);

        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNoteLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBunkerReceivedNoteLine))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
        BunkerReceivedNoteLine testBunkerReceivedNoteLine = bunkerReceivedNoteLineList.get(bunkerReceivedNoteLineList.size() - 1);
        assertThat(testBunkerReceivedNoteLine.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingBunkerReceivedNoteLine() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();
        bunkerReceivedNoteLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bunkerReceivedNoteLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBunkerReceivedNoteLine() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();
        bunkerReceivedNoteLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBunkerReceivedNoteLine() throws Exception {
        int databaseSizeBeforeUpdate = bunkerReceivedNoteLineRepository.findAll().size();
        bunkerReceivedNoteLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bunkerReceivedNoteLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNoteLine in the database
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBunkerReceivedNoteLine() throws Exception {
        // Initialize the database
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        int databaseSizeBeforeDelete = bunkerReceivedNoteLineRepository.findAll().size();

        // Delete the bunkerReceivedNoteLine
        restBunkerReceivedNoteLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, bunkerReceivedNoteLine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BunkerReceivedNoteLine> bunkerReceivedNoteLineList = bunkerReceivedNoteLineRepository.findAll();
        assertThat(bunkerReceivedNoteLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
