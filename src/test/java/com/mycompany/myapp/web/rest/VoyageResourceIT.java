package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Voyage;
import com.mycompany.myapp.repository.VoyageRepository;
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
 * Integration tests for the {@link VoyageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoyageResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voyages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoyageMockMvc;

    private Voyage voyage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyage createEntity(EntityManager em) {
        Voyage voyage = new Voyage().number(DEFAULT_NUMBER);
        return voyage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyage createUpdatedEntity(EntityManager em) {
        Voyage voyage = new Voyage().number(UPDATED_NUMBER);
        return voyage;
    }

    @BeforeEach
    public void initTest() {
        voyage = createEntity(em);
    }

    @Test
    @Transactional
    void createVoyage() throws Exception {
        int databaseSizeBeforeCreate = voyageRepository.findAll().size();
        // Create the Voyage
        restVoyageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isCreated());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeCreate + 1);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    void createVoyageWithExistingId() throws Exception {
        // Create the Voyage with an existing ID
        voyage.setId(1L);

        int databaseSizeBeforeCreate = voyageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoyageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = voyageRepository.findAll().size();
        // set the field null
        voyage.setNumber(null);

        // Create the Voyage, which fails.

        restVoyageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isBadRequest());

        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVoyages() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    void getVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get the voyage
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL_ID, voyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voyage.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingVoyage() throws Exception {
        // Get the voyage
        restVoyageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage
        Voyage updatedVoyage = voyageRepository.findById(voyage.getId()).get();
        // Disconnect from session so that the updates on updatedVoyage are not directly saved in db
        em.detach(updatedVoyage);
        updatedVoyage.number(UPDATED_NUMBER);

        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoyage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voyage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoyageWithPatch() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage using partial update
        Voyage partialUpdatedVoyage = new Voyage();
        partialUpdatedVoyage.setId(voyage.getId());

        partialUpdatedVoyage.number(UPDATED_NUMBER);

        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateVoyageWithPatch() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage using partial update
        Voyage partialUpdatedVoyage = new Voyage();
        partialUpdatedVoyage.setId(voyage.getId());

        partialUpdatedVoyage.number(UPDATED_NUMBER);

        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyageList.get(voyageList.size() - 1);
        assertThat(testVoyage.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voyage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voyage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoyage() throws Exception {
        int databaseSizeBeforeUpdate = voyageRepository.findAll().size();
        voyage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voyage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyage in the database
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        int databaseSizeBeforeDelete = voyageRepository.findAll().size();

        // Delete the voyage
        restVoyageMockMvc
            .perform(delete(ENTITY_API_URL_ID, voyage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voyage> voyageList = voyageRepository.findAll();
        assertThat(voyageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
