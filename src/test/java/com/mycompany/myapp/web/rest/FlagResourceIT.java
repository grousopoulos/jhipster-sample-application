package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Flag;
import com.mycompany.myapp.repository.FlagRepository;
import com.mycompany.myapp.service.dto.FlagDTO;
import com.mycompany.myapp.service.mapper.FlagMapper;
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
 * Integration tests for the {@link FlagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlagResourceIT {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/flags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FlagRepository flagRepository;

    @Autowired
    private FlagMapper flagMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlagMockMvc;

    private Flag flag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flag createEntity(EntityManager em) {
        Flag flag = new Flag().code(DEFAULT_CODE).name(DEFAULT_NAME);
        return flag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flag createUpdatedEntity(EntityManager em) {
        Flag flag = new Flag().code(UPDATED_CODE).name(UPDATED_NAME);
        return flag;
    }

    @BeforeEach
    public void initTest() {
        flag = createEntity(em);
    }

    @Test
    @Transactional
    void createFlag() throws Exception {
        int databaseSizeBeforeCreate = flagRepository.findAll().size();
        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);
        restFlagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flagDTO)))
            .andExpect(status().isCreated());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeCreate + 1);
        Flag testFlag = flagList.get(flagList.size() - 1);
        assertThat(testFlag.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFlag.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createFlagWithExistingId() throws Exception {
        // Create the Flag with an existing ID
        flag.setId(1L);
        FlagDTO flagDTO = flagMapper.toDto(flag);

        int databaseSizeBeforeCreate = flagRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = flagRepository.findAll().size();
        // set the field null
        flag.setCode(null);

        // Create the Flag, which fails.
        FlagDTO flagDTO = flagMapper.toDto(flag);

        restFlagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flagDTO)))
            .andExpect(status().isBadRequest());

        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = flagRepository.findAll().size();
        // set the field null
        flag.setName(null);

        // Create the Flag, which fails.
        FlagDTO flagDTO = flagMapper.toDto(flag);

        restFlagMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flagDTO)))
            .andExpect(status().isBadRequest());

        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFlags() throws Exception {
        // Initialize the database
        flagRepository.saveAndFlush(flag);

        // Get all the flagList
        restFlagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flag.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getFlag() throws Exception {
        // Initialize the database
        flagRepository.saveAndFlush(flag);

        // Get the flag
        restFlagMockMvc
            .perform(get(ENTITY_API_URL_ID, flag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flag.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingFlag() throws Exception {
        // Get the flag
        restFlagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFlag() throws Exception {
        // Initialize the database
        flagRepository.saveAndFlush(flag);

        int databaseSizeBeforeUpdate = flagRepository.findAll().size();

        // Update the flag
        Flag updatedFlag = flagRepository.findById(flag.getId()).get();
        // Disconnect from session so that the updates on updatedFlag are not directly saved in db
        em.detach(updatedFlag);
        updatedFlag.code(UPDATED_CODE).name(UPDATED_NAME);
        FlagDTO flagDTO = flagMapper.toDto(updatedFlag);

        restFlagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flagDTO))
            )
            .andExpect(status().isOk());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
        Flag testFlag = flagList.get(flagList.size() - 1);
        assertThat(testFlag.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFlag.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingFlag() throws Exception {
        int databaseSizeBeforeUpdate = flagRepository.findAll().size();
        flag.setId(count.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flagDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlag() throws Exception {
        int databaseSizeBeforeUpdate = flagRepository.findAll().size();
        flag.setId(count.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlag() throws Exception {
        int databaseSizeBeforeUpdate = flagRepository.findAll().size();
        flag.setId(count.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlagWithPatch() throws Exception {
        // Initialize the database
        flagRepository.saveAndFlush(flag);

        int databaseSizeBeforeUpdate = flagRepository.findAll().size();

        // Update the flag using partial update
        Flag partialUpdatedFlag = new Flag();
        partialUpdatedFlag.setId(flag.getId());

        partialUpdatedFlag.code(UPDATED_CODE);

        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlag))
            )
            .andExpect(status().isOk());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
        Flag testFlag = flagList.get(flagList.size() - 1);
        assertThat(testFlag.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFlag.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateFlagWithPatch() throws Exception {
        // Initialize the database
        flagRepository.saveAndFlush(flag);

        int databaseSizeBeforeUpdate = flagRepository.findAll().size();

        // Update the flag using partial update
        Flag partialUpdatedFlag = new Flag();
        partialUpdatedFlag.setId(flag.getId());

        partialUpdatedFlag.code(UPDATED_CODE).name(UPDATED_NAME);

        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlag.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlag))
            )
            .andExpect(status().isOk());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
        Flag testFlag = flagList.get(flagList.size() - 1);
        assertThat(testFlag.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFlag.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingFlag() throws Exception {
        int databaseSizeBeforeUpdate = flagRepository.findAll().size();
        flag.setId(count.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flagDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlag() throws Exception {
        int databaseSizeBeforeUpdate = flagRepository.findAll().size();
        flag.setId(count.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlag() throws Exception {
        int databaseSizeBeforeUpdate = flagRepository.findAll().size();
        flag.setId(count.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(flagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flag in the database
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlag() throws Exception {
        // Initialize the database
        flagRepository.saveAndFlush(flag);

        int databaseSizeBeforeDelete = flagRepository.findAll().size();

        // Delete the flag
        restFlagMockMvc
            .perform(delete(ENTITY_API_URL_ID, flag.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Flag> flagList = flagRepository.findAll();
        assertThat(flagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
