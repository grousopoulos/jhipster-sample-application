package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ship;
import com.mycompany.myapp.repository.ShipRepository;
import com.mycompany.myapp.service.dto.ShipDTO;
import com.mycompany.myapp.service.mapper.ShipMapper;
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
 * Integration tests for the {@link ShipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION_SOCIETY = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_SOCIETY = "BBBBBBBBBB";

    private static final String DEFAULT_ICE_CLASS_POLAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ICE_CLASS_POLAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_EFFICIENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_EFFICIENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SHIP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ShipMapper shipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipMockMvc;

    private Ship ship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ship createEntity(EntityManager em) {
        Ship ship = new Ship()
            .name(DEFAULT_NAME)
            .classificationSociety(DEFAULT_CLASSIFICATION_SOCIETY)
            .iceClassPolarCode(DEFAULT_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(DEFAULT_TECHNICAL_EFFICIENCY_CODE)
            .shipType(DEFAULT_SHIP_TYPE);
        return ship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ship createUpdatedEntity(EntityManager em) {
        Ship ship = new Ship()
            .name(UPDATED_NAME)
            .classificationSociety(UPDATED_CLASSIFICATION_SOCIETY)
            .iceClassPolarCode(UPDATED_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(UPDATED_TECHNICAL_EFFICIENCY_CODE)
            .shipType(UPDATED_SHIP_TYPE);
        return ship;
    }

    @BeforeEach
    public void initTest() {
        ship = createEntity(em);
    }

    @Test
    @Transactional
    void createShip() throws Exception {
        int databaseSizeBeforeCreate = shipRepository.findAll().size();
        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);
        restShipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipDTO)))
            .andExpect(status().isCreated());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeCreate + 1);
        Ship testShip = shipList.get(shipList.size() - 1);
        assertThat(testShip.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShip.getClassificationSociety()).isEqualTo(DEFAULT_CLASSIFICATION_SOCIETY);
        assertThat(testShip.getIceClassPolarCode()).isEqualTo(DEFAULT_ICE_CLASS_POLAR_CODE);
        assertThat(testShip.getTechnicalEfficiencyCode()).isEqualTo(DEFAULT_TECHNICAL_EFFICIENCY_CODE);
        assertThat(testShip.getShipType()).isEqualTo(DEFAULT_SHIP_TYPE);
    }

    @Test
    @Transactional
    void createShipWithExistingId() throws Exception {
        // Create the Ship with an existing ID
        ship.setId(1L);
        ShipDTO shipDTO = shipMapper.toDto(ship);

        int databaseSizeBeforeCreate = shipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipRepository.findAll().size();
        // set the field null
        ship.setName(null);

        // Create the Ship, which fails.
        ShipDTO shipDTO = shipMapper.toDto(ship);

        restShipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipDTO)))
            .andExpect(status().isBadRequest());

        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShips() throws Exception {
        // Initialize the database
        shipRepository.saveAndFlush(ship);

        // Get all the shipList
        restShipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ship.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].classificationSociety").value(hasItem(DEFAULT_CLASSIFICATION_SOCIETY)))
            .andExpect(jsonPath("$.[*].iceClassPolarCode").value(hasItem(DEFAULT_ICE_CLASS_POLAR_CODE)))
            .andExpect(jsonPath("$.[*].technicalEfficiencyCode").value(hasItem(DEFAULT_TECHNICAL_EFFICIENCY_CODE)))
            .andExpect(jsonPath("$.[*].shipType").value(hasItem(DEFAULT_SHIP_TYPE)));
    }

    @Test
    @Transactional
    void getShip() throws Exception {
        // Initialize the database
        shipRepository.saveAndFlush(ship);

        // Get the ship
        restShipMockMvc
            .perform(get(ENTITY_API_URL_ID, ship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ship.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.classificationSociety").value(DEFAULT_CLASSIFICATION_SOCIETY))
            .andExpect(jsonPath("$.iceClassPolarCode").value(DEFAULT_ICE_CLASS_POLAR_CODE))
            .andExpect(jsonPath("$.technicalEfficiencyCode").value(DEFAULT_TECHNICAL_EFFICIENCY_CODE))
            .andExpect(jsonPath("$.shipType").value(DEFAULT_SHIP_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingShip() throws Exception {
        // Get the ship
        restShipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShip() throws Exception {
        // Initialize the database
        shipRepository.saveAndFlush(ship);

        int databaseSizeBeforeUpdate = shipRepository.findAll().size();

        // Update the ship
        Ship updatedShip = shipRepository.findById(ship.getId()).get();
        // Disconnect from session so that the updates on updatedShip are not directly saved in db
        em.detach(updatedShip);
        updatedShip
            .name(UPDATED_NAME)
            .classificationSociety(UPDATED_CLASSIFICATION_SOCIETY)
            .iceClassPolarCode(UPDATED_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(UPDATED_TECHNICAL_EFFICIENCY_CODE)
            .shipType(UPDATED_SHIP_TYPE);
        ShipDTO shipDTO = shipMapper.toDto(updatedShip);

        restShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
        Ship testShip = shipList.get(shipList.size() - 1);
        assertThat(testShip.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShip.getClassificationSociety()).isEqualTo(UPDATED_CLASSIFICATION_SOCIETY);
        assertThat(testShip.getIceClassPolarCode()).isEqualTo(UPDATED_ICE_CLASS_POLAR_CODE);
        assertThat(testShip.getTechnicalEfficiencyCode()).isEqualTo(UPDATED_TECHNICAL_EFFICIENCY_CODE);
        assertThat(testShip.getShipType()).isEqualTo(UPDATED_SHIP_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingShip() throws Exception {
        int databaseSizeBeforeUpdate = shipRepository.findAll().size();
        ship.setId(count.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShip() throws Exception {
        int databaseSizeBeforeUpdate = shipRepository.findAll().size();
        ship.setId(count.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShip() throws Exception {
        int databaseSizeBeforeUpdate = shipRepository.findAll().size();
        ship.setId(count.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShipWithPatch() throws Exception {
        // Initialize the database
        shipRepository.saveAndFlush(ship);

        int databaseSizeBeforeUpdate = shipRepository.findAll().size();

        // Update the ship using partial update
        Ship partialUpdatedShip = new Ship();
        partialUpdatedShip.setId(ship.getId());

        partialUpdatedShip.classificationSociety(UPDATED_CLASSIFICATION_SOCIETY);

        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShip))
            )
            .andExpect(status().isOk());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
        Ship testShip = shipList.get(shipList.size() - 1);
        assertThat(testShip.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShip.getClassificationSociety()).isEqualTo(UPDATED_CLASSIFICATION_SOCIETY);
        assertThat(testShip.getIceClassPolarCode()).isEqualTo(DEFAULT_ICE_CLASS_POLAR_CODE);
        assertThat(testShip.getTechnicalEfficiencyCode()).isEqualTo(DEFAULT_TECHNICAL_EFFICIENCY_CODE);
        assertThat(testShip.getShipType()).isEqualTo(DEFAULT_SHIP_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateShipWithPatch() throws Exception {
        // Initialize the database
        shipRepository.saveAndFlush(ship);

        int databaseSizeBeforeUpdate = shipRepository.findAll().size();

        // Update the ship using partial update
        Ship partialUpdatedShip = new Ship();
        partialUpdatedShip.setId(ship.getId());

        partialUpdatedShip
            .name(UPDATED_NAME)
            .classificationSociety(UPDATED_CLASSIFICATION_SOCIETY)
            .iceClassPolarCode(UPDATED_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(UPDATED_TECHNICAL_EFFICIENCY_CODE)
            .shipType(UPDATED_SHIP_TYPE);

        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShip))
            )
            .andExpect(status().isOk());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
        Ship testShip = shipList.get(shipList.size() - 1);
        assertThat(testShip.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShip.getClassificationSociety()).isEqualTo(UPDATED_CLASSIFICATION_SOCIETY);
        assertThat(testShip.getIceClassPolarCode()).isEqualTo(UPDATED_ICE_CLASS_POLAR_CODE);
        assertThat(testShip.getTechnicalEfficiencyCode()).isEqualTo(UPDATED_TECHNICAL_EFFICIENCY_CODE);
        assertThat(testShip.getShipType()).isEqualTo(UPDATED_SHIP_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingShip() throws Exception {
        int databaseSizeBeforeUpdate = shipRepository.findAll().size();
        ship.setId(count.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShip() throws Exception {
        int databaseSizeBeforeUpdate = shipRepository.findAll().size();
        ship.setId(count.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShip() throws Exception {
        int databaseSizeBeforeUpdate = shipRepository.findAll().size();
        ship.setId(count.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shipDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ship in the database
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShip() throws Exception {
        // Initialize the database
        shipRepository.saveAndFlush(ship);

        int databaseSizeBeforeDelete = shipRepository.findAll().size();

        // Delete the ship
        restShipMockMvc
            .perform(delete(ENTITY_API_URL_ID, ship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ship> shipList = shipRepository.findAll();
        assertThat(shipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
