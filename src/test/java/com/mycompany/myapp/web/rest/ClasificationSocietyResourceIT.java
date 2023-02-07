package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ClasificationSociety;
import com.mycompany.myapp.repository.ClasificationSocietyRepository;
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
 * Integration tests for the {@link ClasificationSocietyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClasificationSocietyResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clasification-societies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClasificationSocietyRepository clasificationSocietyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasificationSocietyMockMvc;

    private ClasificationSociety clasificationSociety;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasificationSociety createEntity(EntityManager em) {
        ClasificationSociety clasificationSociety = new ClasificationSociety().code(DEFAULT_CODE).name(DEFAULT_NAME);
        return clasificationSociety;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasificationSociety createUpdatedEntity(EntityManager em) {
        ClasificationSociety clasificationSociety = new ClasificationSociety().code(UPDATED_CODE).name(UPDATED_NAME);
        return clasificationSociety;
    }

    @BeforeEach
    public void initTest() {
        clasificationSociety = createEntity(em);
    }

    @Test
    @Transactional
    void createClasificationSociety() throws Exception {
        int databaseSizeBeforeCreate = clasificationSocietyRepository.findAll().size();
        // Create the ClasificationSociety
        restClasificationSocietyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isCreated());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeCreate + 1);
        ClasificationSociety testClasificationSociety = clasificationSocietyList.get(clasificationSocietyList.size() - 1);
        assertThat(testClasificationSociety.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testClasificationSociety.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createClasificationSocietyWithExistingId() throws Exception {
        // Create the ClasificationSociety with an existing ID
        clasificationSociety.setId(1L);

        int databaseSizeBeforeCreate = clasificationSocietyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasificationSocietyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificationSocietyRepository.findAll().size();
        // set the field null
        clasificationSociety.setCode(null);

        // Create the ClasificationSociety, which fails.

        restClasificationSocietyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isBadRequest());

        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificationSocietyRepository.findAll().size();
        // set the field null
        clasificationSociety.setName(null);

        // Create the ClasificationSociety, which fails.

        restClasificationSocietyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isBadRequest());

        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClasificationSocieties() throws Exception {
        // Initialize the database
        clasificationSocietyRepository.saveAndFlush(clasificationSociety);

        // Get all the clasificationSocietyList
        restClasificationSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificationSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getClasificationSociety() throws Exception {
        // Initialize the database
        clasificationSocietyRepository.saveAndFlush(clasificationSociety);

        // Get the clasificationSociety
        restClasificationSocietyMockMvc
            .perform(get(ENTITY_API_URL_ID, clasificationSociety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clasificationSociety.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingClasificationSociety() throws Exception {
        // Get the clasificationSociety
        restClasificationSocietyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClasificationSociety() throws Exception {
        // Initialize the database
        clasificationSocietyRepository.saveAndFlush(clasificationSociety);

        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();

        // Update the clasificationSociety
        ClasificationSociety updatedClasificationSociety = clasificationSocietyRepository.findById(clasificationSociety.getId()).get();
        // Disconnect from session so that the updates on updatedClasificationSociety are not directly saved in db
        em.detach(updatedClasificationSociety);
        updatedClasificationSociety.code(UPDATED_CODE).name(UPDATED_NAME);

        restClasificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClasificationSociety.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClasificationSociety))
            )
            .andExpect(status().isOk());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
        ClasificationSociety testClasificationSociety = clasificationSocietyList.get(clasificationSocietyList.size() - 1);
        assertThat(testClasificationSociety.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testClasificationSociety.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingClasificationSociety() throws Exception {
        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();
        clasificationSociety.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clasificationSociety.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClasificationSociety() throws Exception {
        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();
        clasificationSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClasificationSociety() throws Exception {
        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();
        clasificationSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClasificationSocietyWithPatch() throws Exception {
        // Initialize the database
        clasificationSocietyRepository.saveAndFlush(clasificationSociety);

        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();

        // Update the clasificationSociety using partial update
        ClasificationSociety partialUpdatedClasificationSociety = new ClasificationSociety();
        partialUpdatedClasificationSociety.setId(clasificationSociety.getId());

        partialUpdatedClasificationSociety.name(UPDATED_NAME);

        restClasificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasificationSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClasificationSociety))
            )
            .andExpect(status().isOk());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
        ClasificationSociety testClasificationSociety = clasificationSocietyList.get(clasificationSocietyList.size() - 1);
        assertThat(testClasificationSociety.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testClasificationSociety.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateClasificationSocietyWithPatch() throws Exception {
        // Initialize the database
        clasificationSocietyRepository.saveAndFlush(clasificationSociety);

        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();

        // Update the clasificationSociety using partial update
        ClasificationSociety partialUpdatedClasificationSociety = new ClasificationSociety();
        partialUpdatedClasificationSociety.setId(clasificationSociety.getId());

        partialUpdatedClasificationSociety.code(UPDATED_CODE).name(UPDATED_NAME);

        restClasificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasificationSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClasificationSociety))
            )
            .andExpect(status().isOk());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
        ClasificationSociety testClasificationSociety = clasificationSocietyList.get(clasificationSocietyList.size() - 1);
        assertThat(testClasificationSociety.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testClasificationSociety.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingClasificationSociety() throws Exception {
        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();
        clasificationSociety.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clasificationSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClasificationSociety() throws Exception {
        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();
        clasificationSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClasificationSociety() throws Exception {
        int databaseSizeBeforeUpdate = clasificationSocietyRepository.findAll().size();
        clasificationSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clasificationSociety))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClasificationSociety in the database
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClasificationSociety() throws Exception {
        // Initialize the database
        clasificationSocietyRepository.saveAndFlush(clasificationSociety);

        int databaseSizeBeforeDelete = clasificationSocietyRepository.findAll().size();

        // Delete the clasificationSociety
        restClasificationSocietyMockMvc
            .perform(delete(ENTITY_API_URL_ID, clasificationSociety.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClasificationSociety> clasificationSocietyList = clasificationSocietyRepository.findAll();
        assertThat(clasificationSocietyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
