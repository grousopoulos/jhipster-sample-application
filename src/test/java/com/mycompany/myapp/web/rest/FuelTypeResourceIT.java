package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FuelType;
import com.mycompany.myapp.domain.enumeration.FuelTypeCode;
import com.mycompany.myapp.repository.FuelTypeRepository;
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
 * Integration tests for the {@link FuelTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final FuelTypeCode DEFAULT_FUEL_TYPE_CODE = FuelTypeCode.MDO;
    private static final FuelTypeCode UPDATED_FUEL_TYPE_CODE = FuelTypeCode.ETHANOL;

    private static final BigDecimal DEFAULT_CARBON_FACTORY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CARBON_FACTORY = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/fuel-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelTypeMockMvc;

    private FuelType fuelType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelType createEntity(EntityManager em) {
        FuelType fuelType = new FuelType().name(DEFAULT_NAME).fuelTypeCode(DEFAULT_FUEL_TYPE_CODE).carbonFactory(DEFAULT_CARBON_FACTORY);
        return fuelType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelType createUpdatedEntity(EntityManager em) {
        FuelType fuelType = new FuelType().name(UPDATED_NAME).fuelTypeCode(UPDATED_FUEL_TYPE_CODE).carbonFactory(UPDATED_CARBON_FACTORY);
        return fuelType;
    }

    @BeforeEach
    public void initTest() {
        fuelType = createEntity(em);
    }

    @Test
    @Transactional
    void createFuelType() throws Exception {
        int databaseSizeBeforeCreate = fuelTypeRepository.findAll().size();
        // Create the FuelType
        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isCreated());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FuelType testFuelType = fuelTypeList.get(fuelTypeList.size() - 1);
        assertThat(testFuelType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFuelType.getFuelTypeCode()).isEqualTo(DEFAULT_FUEL_TYPE_CODE);
        assertThat(testFuelType.getCarbonFactory()).isEqualByComparingTo(DEFAULT_CARBON_FACTORY);
    }

    @Test
    @Transactional
    void createFuelTypeWithExistingId() throws Exception {
        // Create the FuelType with an existing ID
        fuelType.setId(1L);

        int databaseSizeBeforeCreate = fuelTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fuelTypeRepository.findAll().size();
        // set the field null
        fuelType.setName(null);

        // Create the FuelType, which fails.

        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isBadRequest());

        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFuelTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fuelTypeRepository.findAll().size();
        // set the field null
        fuelType.setFuelTypeCode(null);

        // Create the FuelType, which fails.

        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isBadRequest());

        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCarbonFactoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = fuelTypeRepository.findAll().size();
        // set the field null
        fuelType.setCarbonFactory(null);

        // Create the FuelType, which fails.

        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isBadRequest());

        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFuelTypes() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fuelTypeCode").value(hasItem(DEFAULT_FUEL_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].carbonFactory").value(hasItem(sameNumber(DEFAULT_CARBON_FACTORY))));
    }

    @Test
    @Transactional
    void getFuelType() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        // Get the fuelType
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fuelTypeCode").value(DEFAULT_FUEL_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.carbonFactory").value(sameNumber(DEFAULT_CARBON_FACTORY)));
    }

    @Test
    @Transactional
    void getNonExistingFuelType() throws Exception {
        // Get the fuelType
        restFuelTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelType() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();

        // Update the fuelType
        FuelType updatedFuelType = fuelTypeRepository.findById(fuelType.getId()).get();
        // Disconnect from session so that the updates on updatedFuelType are not directly saved in db
        em.detach(updatedFuelType);
        updatedFuelType.name(UPDATED_NAME).fuelTypeCode(UPDATED_FUEL_TYPE_CODE).carbonFactory(UPDATED_CARBON_FACTORY);

        restFuelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuelType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFuelType))
            )
            .andExpect(status().isOk());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
        FuelType testFuelType = fuelTypeList.get(fuelTypeList.size() - 1);
        assertThat(testFuelType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFuelType.getFuelTypeCode()).isEqualTo(UPDATED_FUEL_TYPE_CODE);
        assertThat(testFuelType.getCarbonFactory()).isEqualByComparingTo(UPDATED_CARBON_FACTORY);
    }

    @Test
    @Transactional
    void putNonExistingFuelType() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();
        fuelType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelType() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();
        fuelType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelType() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();
        fuelType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelTypeWithPatch() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();

        // Update the fuelType using partial update
        FuelType partialUpdatedFuelType = new FuelType();
        partialUpdatedFuelType.setId(fuelType.getId());

        partialUpdatedFuelType.name(UPDATED_NAME);

        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelType))
            )
            .andExpect(status().isOk());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
        FuelType testFuelType = fuelTypeList.get(fuelTypeList.size() - 1);
        assertThat(testFuelType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFuelType.getFuelTypeCode()).isEqualTo(DEFAULT_FUEL_TYPE_CODE);
        assertThat(testFuelType.getCarbonFactory()).isEqualByComparingTo(DEFAULT_CARBON_FACTORY);
    }

    @Test
    @Transactional
    void fullUpdateFuelTypeWithPatch() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();

        // Update the fuelType using partial update
        FuelType partialUpdatedFuelType = new FuelType();
        partialUpdatedFuelType.setId(fuelType.getId());

        partialUpdatedFuelType.name(UPDATED_NAME).fuelTypeCode(UPDATED_FUEL_TYPE_CODE).carbonFactory(UPDATED_CARBON_FACTORY);

        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelType))
            )
            .andExpect(status().isOk());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
        FuelType testFuelType = fuelTypeList.get(fuelTypeList.size() - 1);
        assertThat(testFuelType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFuelType.getFuelTypeCode()).isEqualTo(UPDATED_FUEL_TYPE_CODE);
        assertThat(testFuelType.getCarbonFactory()).isEqualByComparingTo(UPDATED_CARBON_FACTORY);
    }

    @Test
    @Transactional
    void patchNonExistingFuelType() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();
        fuelType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelType() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();
        fuelType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelType() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();
        fuelType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelType() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        int databaseSizeBeforeDelete = fuelTypeRepository.findAll().size();

        // Delete the fuelType
        restFuelTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
